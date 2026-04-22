<template>
	<v-dialog :title="title" :close-on-click-modal="false" v-model="visible">
		<el-form
			:model="inputForm"
			ref="inputForm"
			v-loading="loading"
			:class="method === 'view' ? 'readonly' : ''"
			:disabled="method === 'view'"
			label-width="120px"
		>
			<el-row :gutter="15">
				<el-col :span="12">
					<el-form-item
						label="归属部门"
						prop="office.id"
						:rules="[
							{
								required: true,
								message: '归属部门不能为空',
								trigger: 'blur',
							},
						]"
					>
						<office-select v-model="inputForm.office.id" />
					</el-form-item>
				</el-col>
				<el-col :span="12">
					<el-form-item label="员工" prop="user.id" :rules="[]">
						<user-select
							:limit="1"
							v-model="inputForm.user.id"
						></user-select>
					</el-form-item>
				</el-col>
				<el-col :span="12">
					<el-form-item label="地区" prop="area" :rules="[]">
						<area-select v-model="inputForm.area" />
					</el-form-item>
				</el-col>
				<el-col :span="12">
					<el-form-item
						label="请假开始日期"
						prop="beginDate"
						:rules="[]"
					>
						<el-date-picker
							style="width: 100%"
							v-model="inputForm.beginDate"
							type="datetime"
							value-format="YYYY-MM-DD HH:mm:ss"
							placeholder="选择日期时间"
						>
						</el-date-picker>
					</el-form-item>
				</el-col>
				<el-col :span="12">
					<el-form-item
						label="请假结束日期"
						prop="endDate"
						:rules="[]"
					>
						<el-date-picker
							style="width: 100%"
							v-model="inputForm.endDate"
							type="datetime"
							value-format="YYYY-MM-DD HH:mm:ss"
							placeholder="选择日期时间"
						>
						</el-date-picker>
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
</template>

<script>
import OfficeSelect from "@/components/officeSelect";
import UserSelect from "@/components/userSelect";
import AreaSelect from "@/components/areaSelect";
import testFormLeaveService from "@/api/test/one/testFormLeaveService";
export default {
	data() {
		return {
			title: "",
			method: "",
			visible: false,
			loading: false,
			inputForm: {
				id: "",
				office: {
					id: "",
				},
				user: {
					id: "",
				},
				area: "",
				beginDate: "",
				endDate: "",
			},
		};
	},
	components: {
		OfficeSelect,
		UserSelect,
		AreaSelect,
	},
	created() {},
	methods: {
		init(method, id) {
			this.method = method;
			this.inputForm.id = id;
			if (method === "add") {
				this.title = `新建请假表单`;
			} else if (method === "edit") {
				this.title = "修改请假表单";
			} else if (method === "view") {
				this.title = "查看请假表单";
			}
			this.visible = true;
			this.loading = false;
			this.$nextTick(() => {
				this.$refs.inputForm.resetFields();
				if (method === "edit" || method === "view") {
					// 修改或者查看
					this.loading = true;
					testFormLeaveService
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
					testFormLeaveService
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
