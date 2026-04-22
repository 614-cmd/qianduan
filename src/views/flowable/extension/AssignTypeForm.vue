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
							label="待办人编码"
							prop="code"
							:rules="[
								{
									required: true,
									message: '待办人编码不能为空',
									trigger: 'blur',
								},
							]"
						>
							<el-input
								v-model="inputForm.code"
								placeholder="请填写待办人编码"
							></el-input>
						</el-form-item>
					</el-col>
					<el-col :span="24">
						<el-form-item
							label="待办人名称"
							prop="name"
							:rules="[
								{
									required: true,
									message: '待办人名称不能为空',
									trigger: 'blur',
								},
							]"
						>
							<el-input
								v-model="inputForm.name"
								placeholder="请填写待办人名称"
							></el-input>
						</el-form-item>
					</el-col>
					<el-col :span="24">
						<el-form-item
							label="排序"
							prop="sort"
							:rules="[
								{
									required: true,
									message: '待办人排序号不能为空',
									trigger: 'blur',
								},
							]"
						>
							<el-input-number
								:step="10"
								style="width: 100%"
								v-model="inputForm.sort"
								placeholder="请填写待办人排序号"
							></el-input-number>
						</el-form-item>
					</el-col>
					<el-col :span="24">
						<el-form-item label="说明" prop="remarks" :rules="[]">
							<el-input
								v-model="inputForm.remarks"
								placeholder="请填写说明"
							></el-input>
						</el-form-item>
					</el-col>
				</el-row>
			</el-form>
			<template #footer>
				<span class="dialog-footer">
					<el-button @click="visible = false">关闭</el-button>
					<el-button
						type="primary"
						v-if="method != 'view'"
						@click="doSubmit()"
						v-noMoreClick
						>确定</el-button
					>
				</span>
			</template>
		</v-dialog>
	</div>
</template>

<script>
import assignTypeService from "@/api/flowable/assignTypeService";
export default {
	data() {
		return {
			title: "",
			method: "",
			visible: false,
			loading: false,
			inputForm: {
				id: "",
				code: "",
				name: "",
				sort: 10,
				remarks: "",
			},
		};
	},
	methods: {
		init(method, id) {
			this.method = method;
			this.inputForm.id = id;
			if (method === "add") {
				this.title = `新建待办人类型`;
			} else if (method === "edit") {
				this.title = "修改待办人类型";
			} else if (method === "view") {
				this.title = "查看待办人类型";
			}
			this.visible = true;
			this.loading = false;
			this.$nextTick(() => {
				this.$refs.inputForm.resetFields();
				if (method === "edit" || method === "view") {
					// 修改或者查看
					this.loading = true;
					assignTypeService
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
					assignTypeService
						.save(this.inputForm)
						.then((data) => {
							this.visible = false;
							this.$message.success(data);
							this.$emit("refreshDataList");
							this.loading = false;
						})
						.catch(() => {
							this.loading = false;
						});
				}
			});
		},
	},
};
</script>
