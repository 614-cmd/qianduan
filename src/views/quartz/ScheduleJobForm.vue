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
				@keyup.enter="doSubmit()"
				label-width="120px"
				@submit.prevent
			>
				<el-row :gutter="15">
					<el-col :span="24">
						<el-form-item
							label="任务名"
							prop="name"
							:rules="[
								{
									required: true,
									message: '任务名不能为空',
									trigger: 'blur',
								},
							]"
						>
							<el-input
								v-model="inputForm.name"
								placeholder="请填写任务名"
							></el-input>
						</el-form-item>
					</el-col>
					<el-col :span="24">
						<el-form-item
							label="任务组"
							prop="jobGroup"
							:rules="[
								{
									required: true,
									message: '任务组不能为空',
									trigger: 'blur',
								},
							]"
						>
							<el-select
								v-model="inputForm.jobGroup"
								placeholder="请选择"
								style="width: 100%"
							>
								<el-option
									v-for="item in $dictUtils.getDictList(
										'schedule_task_group'
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
						<el-form-item
							label="定时规则"
							prop="cronExpression"
							:rules="[
								{
									required: true,
									message: '定时规则不能为空',
									trigger: 'blur',
								},
							]"
						>
							<cron
								v-model="inputForm.cronExpression"
								placeholder="请输入Cron定时规则"
								clearable
								:shortcuts="shortcuts"
							></cron>
						</el-form-item>
					</el-col>
					<el-col :span="24">
						<el-form-item
							label="启用状态"
							prop="status"
							:rules="[
								{
									required: true,
									message: '启用状态不能为空',
									trigger: 'blur',
								},
							]"
						>
							<el-select
								v-model="inputForm.status"
								placeholder="请选择"
								style="width: 100%"
							>
								<el-option
									v-for="item in $dictUtils.getDictList(
										'yes_no'
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
						<el-form-item
							label="任务类"
							prop="className"
							:rules="[
								{
									required: true,
									message: '任务类不能为空',
									trigger: 'blur',
								},
								{
									validator: validateClassName,
									trigger: 'blur',
								},
							]"
						>
							<el-input
								v-model="inputForm.className"
								placeholder="请填写任务类"
							></el-input>
						</el-form-item>
					</el-col>
					<el-col :span="24">
						<el-form-item label="描述" prop="remarks" :rules="[]">
							<el-input
								type="textarea"
								v-model="inputForm.remarks"
								placeholder="请填写描述"
							></el-input>
						</el-form-item>
					</el-col>
				</el-row>
			</el-form>
			<template #footer>
				<span class="dialog-footer">
					<el-button
						@click="visible = false"
						icon="el-icon-circle-close"
						>关闭</el-button
					>
					<el-button
						type="primary"
						v-if="method != 'view'"
						@click="doSubmit()"
						icon="el-icon-circle-check"
						v-noMoreClick
						>确定</el-button
					>
				</span>
			</template>
		</v-dialog>
	</div>
</template>

<script>
import cron from "@/components/cron";
import quartzService from "@/api/quartz/quartzService";

export default {
	data() {
		return {
			dialogFormVisible: false,
			dialogTitle: "配置时间",
			timeType: "",
			title: "",
			method: "",
			visible: false,
			loading: false,
			shortcuts: [
				{
					text: "每天8点和12点 (自定义追加)",
					value: "0 0 8,12 * * ?",
				},
			],
			inputForm: {
				id: "",
				name: "",
				jobGroup: "",
				cronExpression: "",
				status: "",
				isInfo: "",
				className: "",
				remarks: "",
			},
			validateClassName: (rule, value, callback) => {
				quartzService
					.existsClass(value)
					.then(() => {
						callback();
					})
					.catch(() => {
						callback(new Error("类不存在!"));
					});
			},
		};
	},
	components: {
		cron,
	},
	methods: {
		togglePopover(state) {
			this.cronPopover = state;
		},
		changeCron(val) {
			if (typeof val !== "string") return false;
			this.inputForm.cronExpression = val;
		},

		init(method, id) {
			this.method = method;
			this.inputForm.id = id;
			if (method === "add") {
				this.title = `新建定时任务`;
			} else if (method === "edit") {
				this.title = "修改定时任务";
			} else if (method === "view") {
				this.title = "查看定时任务";
			}
			this.visible = true;
			this.loading = false;
			this.$nextTick(() => {
				this.$refs.inputForm.resetFields();
				if (method === "edit" || method === "view") {
					// 修改或者查看
					this.loading = true;
					quartzService.queryById(this.inputForm.id).then((data) => {
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
					quartzService
						.save(this.inputForm)
						.then((data) => {
							this.loading = false;
							this.visible = false;
							this.$message.success(data);
							this.$emit("refreshDataList");
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
