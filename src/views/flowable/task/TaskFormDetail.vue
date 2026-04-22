<template>
	<div class="jp-center">
		<h2 class="title">{{ title }}</h2>
		<img
			v-if="code === 1"
			class="process-status-img"
			src="../../../assets/img/flowable/1.png"
		/>
		<img
			v-if="code === 2"
			class="process-status-img"
			src="../../../assets/img/flowable/2.png"
		/>
		<img
			v-if="code === 3"
			class="process-status-img"
			src="../../../assets/img/flowable/3.png"
		/>
		<img
			v-if="code === 4"
			class="process-status-img"
			src="../../../assets/img/flowable/4.png"
		/>
		<img
			v-if="code === 5"
			class="process-status-img"
			src="../../../assets/img/flowable/5.png"
		/>
		<img
			v-if="code === 6"
			class="process-status-img"
			src="../../../assets/img/flowable/6.png"
		/>
		<el-tabs type="border-card" v-model="selectedTab">
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
					:edit="false"
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
	</div>
</template>

<script>
// import FlowChart from '../modeler/FlowChart'
import UserSelect from "@/components/userSelect/index.vue";
import PreviewForm from "@/views/flowable/form/GenerateFlowableForm.vue";
import FlowStep from "@/views/flowable/components/FlowStep.vue";
import FlowTimeLine from "@/views/flowable/components/FlowTimeLine.vue";
import taskService from "@/api/flowable/taskService.js";
import formService from "@/api/flowable/formService.js";
import processService from "@/api/flowable/processService.js";
export default {
	async activated() {
		await this.init();
		// 读取流程表单
		if (this.formType === "2") {
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
			// 读取流程表单
			if (this.formUrl === "/404") {
				this.$refs.form.createForm("");
			} else {
				this.$refs.form.createForm(this.formUrl);
			}
			formService
				.getHistoryTaskFormData({
					processInstanceId: this.procInsId,
					procDefId: this.procDefId,
					taskDefKey: this.taskDefKey,
				})
				.then((data) => {
					this.taskFormData = data;
				});
		}

		// 读取流程状态
		processService
			.queryProcessStatus(this.procDefId, this.procInsId)
			.then((data) => {
				this.code = data;
			});
		taskService.historicTaskList(this.procInsId).then((data) => {
			this.historicTaskList = data;
		});
	},
	components: {
		UserSelect,
		PreviewForm,
		FlowStep,
		FlowTimeLine,
		// FlowChart
	},
	watch: {
		selectedTab(val) {
			if (val === "form-third") {
				if (this.procInsId) {
					this.$refs.chart1.init();
				} else {
					this.$refs.chart2.init();
				}
			}
		},
	},
	methods: {
		async init() {
			this.selectedTab = "form-first";
			this.procDefId = this.$route.query.procDefId;
			this.procDefKey = this.$route.query.procDefKey;
			this.taskId = this.$route.query.taskId;
			this.taskDefKey = this.$route.query.taskDefKey;
			this.status = this.$route.query.status;
			this.procInsId = this.$route.query.procInsId;
			this.formReadOnly = true;

			let data = await taskService.getTaskDef({
				taskId: this.taskId,
				taskDefKey: this.taskDefKey,
				procInsId: this.procInsId,
				procDefId: this.procDefId,
				procDefKey: this.procDefKey,
			});
			this.formType = data.formType;
			this.formUrl = data.formUrl;
			this.businessId = data.businessId;
			if (this.status === "start") {
				this.title = `${this.userName} 在 ${this.moment(
					new Date()
				).format("YYYY-MM-DD HH:mm")} 发起了 [${data.procDefName}]`;
			} else {
				this.title = data.title;
			}
		},
	},
	data() {
		return {
			form: null,
			formType: "",
			formUrl: "",
			selectedTab: "frist",
			historicTaskList: [],
			procDefId: "",
			code: "1",
			procInsId: "",
			readOnly: false,
			procDefKey: "",
			taskId: "",
			taskFormData: [],
			taskDefKey: "",
			status: "",
			title: "",
			businessId: "",
		};
	},
};
</script>
<style scoped>
.jp-center {
	height: calc(100% - 34px);
	position: relative;
	padding: 10px;
}
.title {
	padding: 15px;
	text-align: center;
}
.process-status-img {
	height: 180px;
	position: absolute;
	z-index: 999;
	top: 1px;
	right: 1px;
}
</style>
