<template>
	<el-card class="box-card">
		<template #header>
			<div class="card-header">
				<span>我的待办</span>
			</div>
		</template>
		<el-table :data="dataList" style="width: 100%" height="480">
			<el-table-column
				label="实例标题"
				prop="vars.title"
			></el-table-column>
			<el-table-column
				width="100px"
				label="流程名称"
				show-overflow-tooltip
				prop="processDefinitionName"
			>
			</el-table-column>
			<el-table-column
				width="100px"
				label="当前环节"
				show-overflow-tooltip
				prop="task.name"
			>
				<template #default="scope">
					<el-tag>{{ scope.row.task.name }}</el-tag>
				</template>
			</el-table-column>
			<el-table-column
				width="100px"
				label="流程发起人"
				show-overflow-tooltip
				prop="vars.userName"
			></el-table-column>
			<el-table-column
				width="120px"
				prop="task.createTime"
				show-overflow-tooltip
				label="发起时间"
			>
			</el-table-column>
			<el-table-column
				label="操作"
				width="100px"
				fixed="right"
				align="center"
			>
				<template #default="scope">
					<el-button type="primary" text @click="todo(scope.row)"
						>办理</el-button
					>
					<el-button type="primary" text @click="trace(scope.row)"
						>进度</el-button
					>
				</template>
			</el-table-column>
		</el-table>
		<vxe-pager
			background
			size="small"
			:current-page="tablePage.currentPage"
			:page-size="tablePage.pageSize"
			:total="tablePage.total"
			:page-sizes="[
				10,
				20,
				100,
				1000,
				{ label: '全量数据', value: 1000000 },
			]"
			:layouts="[
				'PrevPage',
				'JumpNumber',
				'NextPage',
				'FullJump',
				'Sizes',
				'Total',
			]"
			@page-change="currentChangeHandle"
		>
		</vxe-pager>
	</el-card>
	<v-dialog
		title="查看进度"
		:close-on-click-modal="true"
		append-to-body
		v-model="visible"
		width="70%"
		height="600px"
	>
		<flow-chart
			ref="preview"
			:processInstanceId="processInstanceId"
		></flow-chart>
	</v-dialog>
</template>

<script>
// import FlowChart from '../modeler/FlowChart'
import pick from "lodash.pick";
import UserSelect from "@/components/userSelect/UserSelectDialog";
import taskService from "@/api/flowable/taskService";
export default {
	title: "我的待办",
	icon: "wodedaiban",
	description: "我的待办任务",
	layout: {
		w: 6,
		h: 15,
	},
	data() {
		return {
			dataList: [],
			tablePage: {
				total: 0,
				currentPage: 1,
				pageSize: 10,
				orders: [],
			},
			loading: false,
			visible: false,
			currentTask: null,
			processInstanceId: "",
		};
	},
	mounted() {
		this.refreshList();
	},
	activated() {
		this.refreshList();
	},
	components: {
		UserSelect,
		// FlowChart
	},
	methods: {
		// 获取数据列表
		refreshList() {
			this.loading = true;
			taskService
				.todoList({
					current: this.tablePage.currentPage,
					size: this.tablePage.pageSize,
					orders: this.tablePage.orders,
					...this.searchForm,
				})
				.then((data) => {
					this.dataList = data.records;
					this.tablePage.total = data.total;
					this.loading = false;
				});
		},
		// 当前页
		currentChangeHandle({ currentPage, pageSize }) {
			this.tablePage.currentPage = currentPage;
			this.tablePage.pageSize = pageSize;
			this.refreshList();
		},
		todo(row) {
			this.$router.push({
				path: "/flowable/task/TaskForm",
				query: {
					formTitle: `${row.vars.title}`,
					title: `审批【${row.task.name || ""}】`,
					taskId: row.task.id,
					taskName: row.task.name,
					taskDefKey: row.task.taskDefinitionKey,
					procInsId: row.task.processInstanceId,
					procDefId: row.task.processDefinitionId,
					procDefKey: row.task.processDefKey,
				},
			});
		},
		trace(row) {
			this.processInstanceId = row.task.processInstanceId;
			this.visible = true;
			this.$nextTick(() => {
				this.$refs.preview.init();
			});
		},
	},
};
</script>
<style scoped>
.box-card {
	height: 100%;
}
</style>
