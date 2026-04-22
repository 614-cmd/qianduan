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
							label="类型名称"
							prop="name"
							:rules="[
								{
									required: true,
									message: '名称不能为空',
									trigger: 'blur',
								},
							]"
						>
							<el-input
								v-model="inputForm.name"
								placeholder="请填写名称"
							></el-input>
						</el-form-item>
					</el-col>
					<el-col :span="24">
						<el-form-item
							label="备注信息"
							prop="remarks"
							:rules="[]"
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
import docCategoryService from "@/api/wps/docCategoryService";
export default {
	data() {
		return {
			title: "",
			method: "",
			visible: false,
			loading: false,
			docCategoryTreeData: [],
			inputForm: {
				id: "",
				parent: {
					id: "",
				},
				name: "",
				remarks: "",
			},
		};
	},
	methods: {
		init(method, obj) {
			this.method = method;
			this.inputForm.id = obj.id;
			if (method === "add") {
				this.title = "新建文档类型";
			} else if (method === "addChild") {
				this.title = "添加下级文档类型";
			} else if (method === "edit") {
				this.title = "修改文档类型";
			} else if (method === "view") {
				this.title = "查看文档类型";
			}
			this.visible = true;
			this.loading = false;
			docCategoryService.treeData(this.inputForm.id).then((data) => {
				this.docCategoryTreeData = data;
			});
			this.$nextTick(() => {
				this.$refs.inputForm.resetFields();
				this.inputForm.parent.id = obj.parent.id;
				this.inputForm.sort = obj.sort;
				if (method === "edit" || method === "view") {
					// 修改或者查看
					this.loading = true;
					docCategoryService
						.queryById(this.inputForm.id)
						.then((data) => {
							this.inputForm = this.recover(this.inputForm, data);
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
					docCategoryService.save(this.inputForm).then((data) => {
						this.loading = false;
						this.visible = false;
						this.$message.success(data);
						this.$emit("refreshTree");
					});
				}
			});
		},
	},
};
</script>
