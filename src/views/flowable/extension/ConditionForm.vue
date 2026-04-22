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
				:class="method === 'view' ? 'readonly' : ''"
				:disabled="method === 'view'"
				v-loading="loading"
				@keyup.enter="doSubmit()"
				label-width="120px"
				@submit.prevent
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
						<el-form-item
							label="表达式"
							prop="expression"
							:rules="[
								{
									required: true,
									message: '表达式不能为空',
									trigger: 'blur',
								},
							]"
						>
							<el-input
								v-model="inputForm.expression"
								placeholder="请填写表达式"
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
import conditionService from "@/api/flowable/conditionService";
export default {
	data() {
		return {
			title: "",
			method: "",
			visible: false,
			loading: false,
			conditionTab: "0",
			inputForm: {
				id: "",
				name: "",
				expression: "",
				remarks: "",
			},
		};
	},
	methods: {
		init(method, id) {
			this.method = method;
			this.inputForm.id = id;
			if (method === "add") {
				this.title = `新建流程表达式`;
			} else if (method === "edit") {
				this.title = "修改流程表达式";
			} else if (method === "view") {
				this.title = "查看流程表达式";
			}
			this.visible = true;
			this.$nextTick(() => {
				this.$refs.inputForm.resetFields();
				this.conditionTab = "0";
				if (method === "edit" || method === "view") {
					// 修改或者查看
					conditionService
						.queryById(this.inputForm.id)
						.then((data) => {
							this.inputForm = this.recover(this.inputForm, data);
						});
				}
			});
		},
		// 表单提交
		doSubmit() {
			this.$refs["inputForm"].validate((valid) => {
				if (valid) {
					this.loading = true;
					conditionService.save(this.inputForm).then((data) => {
						this.loading = false;
						this.visible = false;
						this.$message.success(data);
						this.$emit("refreshDataList");
					});
				}
			});
		},
	},
};
</script>
