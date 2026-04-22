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
							label="名称"
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
import formCategoryService from "@/api/flowable/formCategoryService";
export default {
	data() {
		return {
			title: "",
			method: "",
			visible: false,
			loading: false,
			formCategoryTreeData: [],
			inputForm: {
				id: "",
				parent: {
					id: "",
				},
				name: "",
				sort: 0,
			},
		};
	},
	methods: {
		init(method, obj) {
			this.method = method;
			this.inputForm.id = obj.id;
			if (method === "add") {
				this.title = "新建流程分类";
			} else if (method === "addChild") {
				this.title = "添加下级流程分类";
			} else if (method === "edit") {
				this.title = "修改流程分类";
			} else if (method === "view") {
				this.title = "查看流程分类";
			}
			formCategoryService.treeData(this.inputForm.id).then((data) => {
				this.formCategoryTreeData = data;
			});
			this.visible = true;
			this.loading = false;
			this.$nextTick(() => {
				this.$refs.inputForm.resetFields();
				this.inputForm.parent.id = obj.parent.id;
				this.inputForm.sort = obj.sort;
				if (method === "edit" || method === "view") {
					// 修改或者查看
					this.loading = true;
					formCategoryService
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
					formCategoryService.save(this.inputForm).then((data) => {
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
