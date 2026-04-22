<template>
	<el-form
		:model="inputForm"
		ref="inputForm"
		v-loading="loading"
		:disabled="formReadOnly"
		label-width="120px"
	>
		<el-row :gutter="15">
			<el-col :span="24">
				<el-form-item label="请假类型" prop="leaveType" :rules="[]">
					<el-select
						v-model="inputForm.leaveType"
						placeholder="请选择"
						style="width: 100%"
					>
						<el-option
							v-for="item in $dictUtils.getDictList(
								'oa_leave_type'
							)"
							:key="item.value"
							:label="item.label"
							:value="item.value"
						>
						</el-option>
					</el-select>
				</el-form-item>
			</el-col>
			<el-col :span="24">
				<el-form-item label="请假开始时间" prop="startTime" :rules="[]">
					<el-date-picker
						style="width: 100%"
						v-model="inputForm.startTime"
						type="datetime"
						value-format="YYYY-MM-DD HH:mm:ss"
						placeholder="选择日期时间"
					>
					</el-date-picker>
				</el-form-item>
			</el-col>
			<el-col :span="24">
				<el-form-item label="请假结束时间" prop="endTime" :rules="[]">
					<el-date-picker
						style="width: 100%"
						v-model="inputForm.endTime"
						type="datetime"
						value-format="YYYY-MM-DD HH:mm:ss"
						placeholder="选择日期时间"
					>
					</el-date-picker>
				</el-form-item>
			</el-col>
			<el-col :span="24">
				<el-form-item label="请假事由" prop="reason" :rules="[]">
					<el-input
						type="textarea"
						v-model="inputForm.reason"
						placeholder="请填写请假事由"
					></el-input>
				</el-form-item>
			</el-col>
		</el-row>
	</el-form>
</template>

<script>
import testActivitiLeaveService from "@/api/test/activiti/testActivitiLeaveService";
export default {
	data() {
		return {
			title: "",
			method: "",
			visible: false,
			loading: false,
			inputForm: {
				id: "",
				leaveType: "",
				startTime: "",
				endTime: "",
				reason: "",
			},
		};
	},
	components: {},
	created() {},
	props: {
		businessId: {
			type: String,
			default: "",
		},
		formReadOnly: {
			type: Boolean,
			default: false,
		},
	},
	watch: {
		businessId: {
			handler() {
				if (this.businessId) {
					this.init(this.businessId);
				} else {
					this.$nextTick(() => {
						this.$refs.inputForm.resetFields();
					});
				}
			},
			immediate: true,
			deep: false,
		},
	},
	methods: {
		init(id) {
			if (id) {
				this.loading = true;
				this.inputForm.id = id;
				this.$nextTick(() => {
					this.$refs.inputForm.resetFields();
					testActivitiLeaveService
						.queryById(this.inputForm.id)
						.then((data) => {
							this.inputForm = this.recover(this.inputForm, data);
							this.loading = false;
						});
				});
			}
		},
		// 表单提交
		saveForm(callback) {
			this.$refs["inputForm"].validate((valid) => {
				if (valid) {
					this.loading = true;
					testActivitiLeaveService
						.save(this.inputForm)
						.then((data) => {
							callback(
								data.businessTable,
								data.businessId,
								this.inputForm
							);
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
