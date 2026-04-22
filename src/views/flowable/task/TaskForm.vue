<template>
	<div class="jp-center" v-loading="loading">
		<h2 class="title">{{ title }}</h2>

		<el-tabs type="border-card" v-model="taskSelectedTab">
			<el-tab-pane label="表单信息" name="form-first">
				<component
					:formReadOnly="formReadOnly"
					v-if="formType === '2'"
					:class="formReadOnly ? 'readonly' : ''"
					ref="form"
					:businessId="businessId"
					:is="form"
				></component>

				<PreviewForm
					v-if="formType !== '2'"
					:processDefinitionId="procDefId"
					:edit="true"
					:taskFormData="taskFormData"
					ref="form"
				/>
			</el-tab-pane>
			<el-tab-pane label="流程信息" v-if="procInsId" name="form-second">
				<flow-time-line :historicTaskList="historicTaskList" />
			</el-tab-pane>
			<el-tab-pane label="流程图" name="form-third">
				<el-card class="box-card" shadow="hover">
					<template #header>
						<div class="card-header">
							<span>流程图</span>
						</div>
					</template>
					<flow-chart
						ref="chart1"
						v-if="procInsId"
						:processInstanceId="procInsId"
					/>
					<flow-chart
						ref="chart2"
						v-if="!procInsId"
						:processDefId="procDefId"
					/>
				</el-card>
			</el-tab-pane>
			<el-tab-pane label="流转记录" v-if="procInsId" name="form-forth">
				<flow-step :historicTaskList="historicTaskList" />
			</el-tab-pane>
		</el-tabs>

		<el-card
			style="margin-top: 10px; padding-bottom: 66px"
			v-if="!procInsId || taskId"
		>
			<el-form :model="auditForm" ref="auditForm" label-width="120px">
				<el-row>
					<el-col :span="16">
						<el-form-item
							v-if="!procInsId"
							label="流程标题"
							prop="title"
						>
							<el-input
								placeholder="请输入流程标题"
								v-model="title"
							>
							</el-input>
						</el-form-item>
						<el-form-item
							v-if="taskId"
							label="审批信息"
							prop="message"
						>
							<el-input
								type="textarea"
								:rows="3"
								placeholder="请输入审批意见"
								v-model="auditForm.message"
							>
							</el-input>
						</el-form-item>
					</el-col>
					<el-col :span="16">
						<el-form-item>
							<el-checkbox v-model="isCC">是否抄送</el-checkbox>
						</el-form-item>
					</el-col>
					<el-col :span="16">
						<el-form-item
							v-if="isCC"
							:rules="[
								{
									required: true,
									message: '用户不能为空',
									trigger: 'blur',
								},
							]"
							prop="userIds"
							label="抄送给"
						>
							<user-select
								v-model="auditForm.userIds"
							></user-select>
						</el-form-item>
					</el-col>
					<el-col :span="16">
						<el-form-item>
							<el-checkbox v-model="isAssign"
								>指定下一步处理者(不设置就使用默认处理人)</el-checkbox
							>
						</el-form-item>
					</el-col>
					<el-col :span="16">
						<el-form-item
							v-if="isAssign"
							:rules="[
								{
									required: true,
									message: '用户不能为空',
									trigger: 'blur',
								},
							]"
							prop="assignee"
							label="指定"
						>
							<user-select
								:limit="1"
								v-model="auditForm.assignee"
							></user-select>
						</el-form-item>
					</el-col>
				</el-row>
			</el-form>
		</el-card>

		<div class="FlowFormFooter">
			<template v-for="(button, index) in buttons">
				<template v-if="button.isHide === '0'">
					<el-button
						type="primary"
						v-if="button.code !== '_flow_print'"
						:key="index"
						@click="submit(button, buttons)"
						v-noMoreClick
						>{{ button.name }}</el-button
					>
					<el-button
						type="primary"
						v-if="button.code === '_flow_print'"
						@click="print"
						:key="index"
						v-noMoreClick
						>{{ button.name }}</el-button
					>
				</template>
			</template>
		</div>
		<task-back-nodes ref="taskBackNodes" @getBackTaskDefKey="back" />
		<add-sign-task-dialog
			ref="addSignTaskDialog"
			@addSign="selectUsersToAddSignTask"
		></add-sign-task-dialog>
		<del-sign-task-dialog
			ref="delSignTaskDialog"
			@delSign="delSignTask"
		></del-sign-task-dialog>
		<user-select-dialog
			title="选择转办用户"
			ref="transferUserSelectDialog"
			:limit="1"
			@doSubmit="selectUsersToTransferTask"
		></user-select-dialog>
		<user-select-dialog
			title="选择委派用户"
			ref="delegateUserSelectDialog"
			:limit="1"
			@doSubmit="selectUsersToDelateTask"
		></user-select-dialog>
	</div>
</template>

<script>
// import FlowChart from '../modeler/FlowChart'
import useTabs from "@/utils/useTabs";
import UserSelect from "@/components/userSelect/index.vue";
import PreviewForm from "@/views/flowable/form/GenerateFlowableForm.vue";
import TaskBackNodes from "@/views/flowable/components/TaskBackNodes.vue";
import AddSignTaskDialog from "@/views/flowable/components/AddSignTaskDialog.vue";
import DelSignTaskDialog from "@/views/flowable/components/DelSignTaskDialog.vue";
import FlowStep from "@/views/flowable/components/FlowStep.vue";
import FlowTimeLine from "@/views/flowable/components/FlowTimeLine.vue";
import UserSelectDialog from "@/components/userSelect/UserSelectDialog.vue";
import taskService from "@/api/flowable/taskService.js";
import taskDefExtensionService from "@/api/flowable/taskDefExtensionService.js";
import formService from "@/api/flowable/formService.js";
import flowCopyService from "@/api/flowable/flowCopyService.js";
import processService from "@/api/flowable/processService.js";
import print2 from "@/utils/print.js";
export default {
	data() {
		return {
			form: null,
			formType: "",
			formUrl: "",
			taskSelectedTab: "frist",
			historicTaskList: [],
			procDefId: "",
			procInsId: "",
			formReadOnly: false,
			procDefKey: "",
			procDefName: "",
			taskId: "",
			taskFormData: [],
			taskDefKey: "",
			status: "",
			title: "",
			businessId: "",
			buttons: [],
			isCC: false,
			isAssign: false,
			loading: false,
			printObj: {
				id: "printForm",
				popTitle: "",
				extraCss: "",
				extraHead:
					'<meta http-equiv="Content-Language" content="zh-cn"/>',
			},
			auditForm: {
				message: "",
				type: "",
				status: "",
				userIds: null,
				assignee: null,
			},
		};
	},
	async activated() {
		await this.init();
		if (this.formType === "2") {
			// 读取外置表单
			if (this.formUrl === "/404") {
				this.form = null;
				this.$message.info("没有关联流程表单!");
			} else {
				const modules = import.meta.globEager("@/views/**/*.vue");
				Object.keys(modules).forEach((path) => {
					const dir = path.split("views")[1].split(".vue")[0];
					if (dir === this.formUrl) {
						this.form = modules[path].default;
					}
				});
			}
		} else {
			// 读取动态表单
			this.$nextTick(() => {
				if (this.formUrl === "/404") {
					this.$refs.form.createForm("");
				} else {
					this.$refs.form.createForm(this.formUrl);
				}
			});
			if (this.status === "start") {
				// 读取启动表单配置
				formService
					.getStartFormData({ processDefinitionId: this.procDefId })
					.then((data) => {
						this.taskFormData = data;
					});
			} else {
				// 读取任务表单配置
				formService
					.getTaskFormData({ taskId: this.taskId })
					.then((data) => {
						this.taskFormData = data;
					});
			}
		}
		// 读取按钮配置
		if (this.status === "start") {
			this.buttons = [{ code: "_flow_start", name: "启动", isHide: "0" }];
		} else if (this.procDefKey && this.taskDefKey) {
			// 读取按钮
			taskDefExtensionService
				.queryByDefIdAndTaskId({
					processDefId: this.procDefKey,
					taskDefId: this.taskDefKey,
				})
				.then((data) => {
					this.buttons = data.flowButtonList;
				});
		}
		// 读取历史任务列表
		if (this.procInsId) {
			taskService.historicTaskList(this.procInsId).then((data) => {
				this.historicTaskList = data;
			});
		}
	},
	components: {
		UserSelect,
		UserSelectDialog,
		PreviewForm,
		TaskBackNodes,
		FlowStep,
		FlowTimeLine,
		AddSignTaskDialog,
		DelSignTaskDialog,
	},
	watch: {
		isAssign(val) {
			if (!val) {
				this.assignee = null;
			}
		},
		taskSelectedTab(val) {
			if (val === "form-third") {
				if (this.procInsId) {
					this.$refs.chart1.init();
				} else {
					this.$refs.chart2.init();
				}
			}
		},
	},
	computed: {
		userName() {
			return JSON.parse(localStorage.getItem("USER_INFO")).name;
		},
	},
	methods: {
		async init() {
			this.taskSelectedTab = "form-first";
			this.procDefId = this.$route.query.procDefId;
			this.procInsId = this.$route.query.procInsId;
			this.taskDefKey = this.$route.query.taskDefKey;
			this.taskId = this.$route.query.taskId;
			this.status = this.$route.query.status;
			this.printObj.popTitle = this.$route.query.title;
			this.isCC = false;
			this.isAssign = false;
			this.auditForm.assignee = null;
			this.auditForm.userIds = null;
			this.auditForm.message = "";
			let data = await taskService.getTaskDef({
				taskId: this.taskId,
				taskDefKey: this.taskDefKey,
				procInsId: this.procInsId,
				procDefId: this.procDefId
			});
			this.formType = data.formType;
			this.formUrl = data.formUrl;
			this.businessId = data.businessId;
			this.procDefName = data.procDefName;
			this.procDefKey = data.procDefKey;
			this.formReadOnly =
				data.formReadOnly !== undefined &&
				data.formReadOnly !== "false" &&
				data.formReadOnly !== false;
			if (this.status === "start") {
				this.title = `${this.userName} 在 ${this.moment(
					new Date()
				).format("YYYY-MM-DD HH:mm")} 发起了 [${data.procDefName}]`;
			} else {
				this.title = data.title;
			}
		},
		cc(procInsId) {
			if (this.isCC && this.auditForm.userIds) {
				this.$refs["auditForm"].validate((valid) => {
					if (valid) {
						flowCopyService.save({
							userIds: this.auditForm.userIds,
							procDefId: this.procDefId,
							procInsId: procInsId,
							procDefName: this.procDefName,
							procInsName: this.title,
							taskName: "",
						});
					}
				});
			}
		},
		// 暂存草稿
		save() {},
		print() {
			//直接传入REF或者querySelector
			print2(this.$refs.form);
		},
		// 启动流程
		start(vars) {
			if (this.formType === "2") {
				// 外置表单
				this.$refs.form.saveForm(
					(businessTable, businessId, inputForm) => {
						vars = { ...vars, ...inputForm };
						taskService
							.start({
								procDefKey: this.procDefKey,
								businessTable: businessTable,
								businessId: businessId,
								...vars,
								title: this.title,
								assignee: this.auditForm.assignee,
							})
							.then((data) => {
								this.$message.success("启动成功");
								useTabs.close();
								this.$router.push("/flowable/task/TodoList");
								this.cc(data);
							});
					}
				);
			} else {
				// 动态表单
				this.$refs.form.submitStartFormData(
					{
						processDefinitionId: this.procDefId,
						...vars,
						title: this.title,
						assignee: this.auditForm.assignee,
					},
					(data) => {
						useTabs.close();
						this.$router.push("/flowable/task/TodoList");
						this.cc(data);
					}
				);
			}
		},
		// 同意
		agree() {
			this.commit(); // 同意
		},
		// 驳回
		reject() {
			this.$confirm(`确定驳回流程吗?`, "提示", {
				confirmButtonText: "确定",
				cancelButtonText: "取消",
				type: "warning",
			}).then(() => {
				taskService.backNodes(this.taskId).then((data) => {
					let backNodes = data;
					if (backNodes.length > 0) {
						let backTaskDefKey =
							backNodes[backNodes.length - 1].taskDefKey;
						this.back(backTaskDefKey);
					}
				});
			});
		},
		// 驳回到任意节点
		turnBack() {
			this.$refs.taskBackNodes.init(this.taskId);
		},
		// 回退到任意节点
		back(backTaskDefKey) {
			taskService
				.back({
					taskId: this.taskId,
					backTaskDefKey: backTaskDefKey,
					...this.auditForm,
				})
				.then((data) => {
					this.$message.success("回退成功");
					useTabs.close();
					this.$router.push("/flowable/task/TodoList");
					this.cc(data);
				});
		},
		// 加签
		addMultiInstance() {
			this.$refs.addSignTaskDialog.open();
		},
		selectUsersToAddSignTask(addSignForm) {
			this.loading = true;
			taskService
				.addSignTask({
					taskId: this.taskId,
					assigneeIds: addSignForm.userIds.split(","),
					reason: addSignForm.message,
					type: addSignForm.type,
				})
				.then((message) => {
					if (this.procInsId) {
						taskService
							.historicTaskList(this.procInsId)
							.then((data) => {
								this.historicTaskList = data;
								this.loading = false;
								this.$message.success(message);
							});
					}
				})
				.catch(() => {
					this.loading = false;
				});
		},
		delSignTask(delSignForm) {
			this.loading = true;
			taskService
				.delSignTask(delSignForm)
				.then((message) => {
					if (this.procInsId) {
						taskService
							.historicTaskList(this.procInsId)
							.then((data) => {
								this.historicTaskList = data;
								this.loading = false;
								this.$message.success(message);
							});
					}
				})
				.catch(() => {
					this.loading = false;
				});
		},
		// 减签
		delMultiInstance() {
			this.$refs.delSignTaskDialog.open(this.taskId);
		},
		// 转办
		transfer() {
			this.$refs.transferUserSelectDialog.open();
		},
		selectUsersToTransferTask(user) {
			taskService.transfer(this.taskId, user[0].id).then((data) => {
				this.$message.success(data);
				this.$router.push("/flowable/task/TodoList");
			});
		},
		// 委托
		delegate() {
			this.$refs.delegateUserSelectDialog.open();
		},
		selectUsersToDelateTask(user) {
			taskService.delegate(this.taskId, user[0].id).then((data) => {
				this.$message.success(data);
				this.$router.push("/flowable/task/TodoList");
			});
		},
		// 终止
		stop() {
			this.$confirm(`确定终止流程吗?`, "提示", {
				confirmButtonText: "确定",
				cancelButtonText: "取消",
				type: "warning",
			}).then(() => {
				processService
					.stop(this.procInsId, this.auditForm.message)
					.then((data) => {
						this.$message.success(data);
						this.$router.push("/flowable/task/TodoList");
					});
			});
		},
		// 自定义按钮提交
		commit(vars) {
			if (this.formType === "2") {
				// 外置表单
				// eslint-disable-next-line no-unused-vars
				this.$refs.form.saveForm(
					(businessTable, businessId, inputForm) => {
						vars = { ...vars, ...inputForm };
						taskService
							.audit({
								taskId: this.taskId,
								taskDefKey: this.taskDefKey,
								procInsId: this.procInsId,
								procDefId: this.procDefId,
								vars: vars,
								comment: this.auditForm,
								assignee: this.auditForm.assignee,
							})
							.then((data) => {
								this.$message.success("提交成功");
								useTabs.close();
								this.$router.push("/flowable/task/TodoList");
								this.cc(data);
							});
					}
				);
			} else {
				// 动态表单
				this.$refs.form.submitTaskFormData(
					vars,
					this.procInsId,
					this.taskId,
					this.auditForm.assignee,
					this.auditForm,
					(data) => {
						useTabs.close();
						this.$router.push("/flowable/task/TodoList");
						this.cc(data);
					}
				);
			}
		},

		submit(currentBtn, buttons) {
			let vars = {}; // 存储流程变量

			// 把当前操作对应的自定义按钮(以_flow_开头的是系统按钮，排除在外）的编码，存储为对应的流程变量，值设置为true，其余自定义按钮编码对应的流程变量值为false。
			buttons.forEach((btn) => {
				if (btn.code && !btn.code.startsWith("_flow_")) {
					vars[btn.code] = false;
				}
			});
			if (currentBtn.code && !currentBtn.code.startsWith("_flow_")) {
				vars[currentBtn.code] = true;
			}
			vars.title = this.title; // 标题
			vars.assignee = this.auditForm.assignee; // 指定的下一步骤处理人
			this.auditForm.type = currentBtn.code; // 提交类型
			this.auditForm.status = currentBtn.name; // 按钮文字
			switch (currentBtn.code) {
				case "_flow_start": // 自动流程
					this.start(vars);
					break;
				case "_flow_save": // 保存草稿
					this.save();
					break;
				case "_flow_agree": // 同意
					this.agree();
					break;
				case "_flow_reject": // 驳回
					this.reject();
					break;
				case "_flow_back": // 驳回到任意步骤
					this.turnBack();
					break;
				case "_flow_add_multi_instance": // 加签
					this.addMultiInstance();
					break;
				case "_flow_del_multi_instance": // 减签
					this.delMultiInstance();
					break;
				case "_flow_transfer": // 转办
					this.transfer();
					break;
				case "_flow_delegate": // 外派
					this.delegate();
					break;
				case "_flow_stop": // 终止
					this.stop();
					break;
				case "_flow_print": // 打印
					this.print();
					break;
				default:
					this.commit(vars); // 自定义按钮提交
			}
		},
	},
};
</script>
<style lang="less" scoped>
.jp-center {
	height: calc(100% - 34px);
	position: relative;
	padding: 10px;
}
.title {
	padding: 15px;
	text-align: center;
}
.FlowFormFooter {
	position: fixed;
	right: 0;
	bottom: 0;
	height: 66px;
	z-index: 999;
	/* margin-right: -20px;
    margin-left: -20px; */
	background: #fff;
	-webkit-box-shadow: 0 -3px 5px 0 rgba(0, 0, 0, 0.12);
	box-shadow: 0 -3px 5px 0 rgba(0, 0, 0, 0.12);
	flex-flow: row nowrap;
	justify-content: center;
	align-items: center;
	left: 275px;
	display: flex;
	-webkit-transition: inline-block 0.3s, left 0.3s, width 0.3s,
		margin-left 0.3s, font-size 0.3s;
	transition: inline-block 0.3s, left 0.3s, width 0.3s, margin-left 0.3s,
		font-size 0.3s;
	/* right: 20.125px; */
}
.aminui-side-split + .aminui-side + .aminui-body.el-container {
	.FlowFormFooter {
		left: 275px;
	}
}
.aminui-side-split + .aminui-side.isCollapse + .aminui-body.el-container {
	.FlowFormFooter {
		left: 130px;
	}
}
.aminui-side + .aminui-body.el-container {
	.FlowFormFooter {
		left: 200px;
	}
}
.aminui-side.isCollapse + .aminui-body.el-container {
	.FlowFormFooter {
		left: 65px;
	}
}
</style>
