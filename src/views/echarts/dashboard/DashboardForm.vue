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

					<el-col :span="24">
						<el-form-item label="备注" prop="remarks" :rules="[]">
							<el-input
								type="textarea"
								v-model="inputForm.remarks"
								placeholder="请填写备注"
							></el-input>
						</el-form-item>
					</el-col>
				</el-row>
			</el-form>
			<template #footer>
				<el-button @click="visible = false">关闭</el-button>
				<el-button
					type="primary"
					v-if="method != 'view'"
					@click="doSubmit()"
					v-noMoreClick
					>确定</el-button
				>
			</template>
		</v-dialog>
	</div>
</template>

<script>
import dashboardService from "@/api/echarts/dashboardService";
export default {
	data() {
		return {
			title: "",
			method: "",
			visible: false,
			loading: false,
			inputForm: {
				id: "",
				name: "",
				remarks: "",
			},
		};
	},
	methods: {
		init(method, id) {
			this.method = method;
			this.inputForm.id = id;
			if (method === "add") {
				this.title = `新建图表看板`;
			} else if (method === "edit") {
				this.title = "修改图表看板";
			} else if (method === "view") {
				this.title = "查看图表看板";
			}
			this.visible = true;
			this.loading = false;
			this.$nextTick(() => {
				this.$refs.inputForm.resetFields();
				if (method === "edit" || method === "view") {
					// 修改或者查看
					this.loading = true;
					dashboardService
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
					dashboardService
						.save(this.inputForm)
						.then((data) => {
							this.visible = false;
							this.$message.success("保存图表看板成功!");
							this.$emit("refreshDataList");
							this.loading = false;
							if (this.method === "add") {
								this.$confirm(
									"是否立即打开图表看板设计器?",
									"提示",
									{
										confirmButtonText: "是",
										cancelButtonText: "否",
										type: "warning",
									}
								).then(() => {
									this.$router.push(
										"/echarts/dashboard/Design?id=" +
											data.id
									);
								});
							}
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
