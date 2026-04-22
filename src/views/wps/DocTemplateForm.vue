<template>
	<div>
		<v-dialog
			:title="title"
			:close-on-click-modal="false"
			v-model="visible"
		>
			<el-form
				:model="inputForm"
				ref="inputForm"
				v-loading="loading"
				:class="method === 'view' ? 'readonly' : ''"
				:disabled="method === 'view'"
				label-width="120px"
			>
				<el-row :gutter="15">
					<el-col :span="24">
						<el-form-item
							label="文档名称"
							prop="name"
							:rules="[
								{
									required: true,
									message: '文档名称不能为空',
									trigger: 'blur',
								},
							]"
						>
							<el-input
								v-model="inputForm.name"
								placeholder="请填写文档名称"
							></el-input>
						</el-form-item>
					</el-col>
					<el-col :span="24">
						<el-form-item
							label="存储路径"
							prop="path"
							:rules="[
								{
									required: true,
									message: '存储路径不能为空',
									trigger: 'blur',
								},
							]"
						>
							<file-upload
								v-model="inputForm.path"
								:limit="1"
								tip="最多上传1个文件,单个文件不要超过10M,请上传文档格式文件"
							></file-upload>
						</el-form-item>
					</el-col>

					<el-col :span="24">
						<el-form-item
							label="类型"
							prop="docCategoryDTO.id"
							:rules="[
								{
									required: true,
									message: '类型不能为空',
									trigger: 'blur',
								},
							]"
						>
							<el-tree-select
								ref="category"
								value-key="id"
								:props="{
									label: 'name', // 显示名称
									children: 'children', // 子级字段名
								}"
								:data="docCategoryTreeData"
								v-model="inputForm.docCategoryDTO.id"
								check-strictly
								style="width: 100%"
								:clearable="true"
								:accordion="true"
							/>
						</el-form-item>
					</el-col>
					<el-col :span="24">
						<el-form-item
							label="备注信息"
							prop="remarks"
							:rules="[
								{
									required: true,
									message: '备注信息不能为空',
									trigger: 'blur',
								},
							]"
						>
							<el-input
								type="textarea"
								v-model="inputForm.remarks"
								placeholder="请填写备注信息"
							></el-input>
						</el-form-item>
					</el-col>
				</el-row>
			</el-form>
			<template #footer>
				<span class="dialog-footer">
					<el-button @click="visible = false" icon="circle-close"
						>关闭</el-button
					>
					<el-button
						type="primary"
						v-if="method != 'view'"
						@click="doSubmit()"
						icon="circle-check"
						v-noMoreClick
						>确定</el-button
					>
				</span>
			</template>
		</v-dialog>
	</div>
</template>
<script>
import docTemplateService from "@/api/wps/docTemplateService";
import docCategoryService from "@/api/wps/docCategoryService";
export default {
	data() {
		return {
			title: "",
			method: "",
			visible: false,
			loading: false,
			pathArra: [],
			docCategoryTreeData: [],
			inputForm: {
				id: "",
				name: "",
				path: "",
				version: "",
				docCategoryDTO: {
					id: "",
				},
				remarks: "",
			},
		};
	},
	created() {
		docCategoryService.treeData().then((data) => {
			this.docCategoryTreeData = data;
		});
	},
	methods: {
		init(method, id) {
			this.method = method;
			this.inputForm.id = id;
			if (method === "add") {
				this.title = `新建文档`;
			} else if (method === "edit") {
				this.title = "修改文档";
			} else if (method === "view") {
				this.title = "查看文档";
			}
			this.pathArra = [];
			this.visible = true;
			this.loading = false;
			this.$nextTick(() => {
				this.$refs.inputForm.resetFields();
				if (method === "edit" || method === "view") {
					// 修改或者查看
					this.loading = true;
					docTemplateService
						.queryById(this.inputForm.id)
						.then((data) => {
							this.inputForm = this.recover(this.inputForm, data);
							this.inputForm.path.split("|").forEach((item) => {
								if (item.trim().length > 0) {
									this.pathArra.push({
										name: decodeURIComponent(
											item.substring(
												item.lastIndexOf("/") + 1
											)
										),
										url: item,
									});
								}
							});
							this.loading = false;
						});
				}
			});
		},
		// 表单提交
		doSubmit() {
			this.$refs["inputForm"].validate((valid) => {
				if (valid) {
					this.loading = true;
					docTemplateService.save(this.inputForm).then((data) => {
						this.visible = false;
						this.$message.success(data);
						this.loading = false;
						this.$emit("refreshDataList");
					});
				}
			});
		},
	},
};
</script>
