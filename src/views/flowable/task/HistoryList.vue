<template>
	<div class="page">
		<el-form
			:inline="true"
			v-if="searchVisible"
			class="query-form m-b-10"
			ref="searchForm"
			:model="searchForm"
			@keyup.enter="refreshList()"
			@submit.prevent
		>
			<el-form-item label="完成时间：" prop="searchDates">
				<el-date-picker
					v-model="searchDates"
					type="datetimerange"
					value-format="YYYY-MM-DD HH:mm:ss"
					range-separator="至"
					start-placeholder="开始日期"
					end-placeholder="结束日期"
				/>
			</el-form-item>
			<el-form-item>
				<el-button type="primary" @click="refreshList()" icon="search"
					>查询</el-button
				>
				<el-button
					type="default"
					@click="resetSearch()"
					icon="refresh-right"
					>重置</el-button
				>
			</el-form-item>
		</el-form>
		<div class="jp-table">
			<vxe-toolbar
				ref="todoToolbar"
				:refresh="{ query: refreshList }"
				export
				print
				custom
			>
				<template #tools>
					<vxe-button
						type="text"
						:title="searchVisible ? '收起检索' : '展开检索'"
						icon="vxe-icon-search"
						class="tool-btn"
						@click="searchVisible = !searchVisible"
					></vxe-button>
				</template>
			</vxe-toolbar>
			<div class="jp-table-body">
				<vxe-table
					border="inner"
					auto-resize
					resizable
					height="auto"
					:loading="loading"
					size="small"
					ref="todoTable"
					show-header-overflow
					show-overflow
					highlight-hover-row
					:menu-config="{}"
					:print-config="{}"
					:import-config="{}"
					:export-config="{}"
					:data="dataList"
					:checkbox-config="{}"
				>
					<vxe-column type="seq" width="40"></vxe-column>
					<vxe-column type="checkbox" width="40px"></vxe-column>
					<vxe-column title="任务" field="name">
						<template #default="scope">
							{{ scope.row.name }}
							<el-button
								v-if="scope.row.back"
								type="warning"
								size="small"
								@click="callback(scope.row)"
								>撤销</el-button
							>
						</template>
					</vxe-column>
					<vxe-column title="实例标题" field="vars.title">
					</vxe-column>
					<vxe-column title="流程名称" field="processDefinitionName">
					</vxe-column>
					<vxe-column title="办理状态" field="status">
						<template #default="scope">
							<el-tag :type="scope.row.level"
								>{{ scope.row.status }}
							</el-tag>
						</template>
					</vxe-column>
					<vxe-column
						title="流程发起人"
						field="vars.userName"
					></vxe-column>
					<vxe-column field="endTime" title="完成时间"> </vxe-column>
					<vxe-column
						title="操作"
						width="100px"
						fixed="right"
						align="center"
					>
						<template #default="scope">
							<el-button
								type="primary"
								text
								@click="detail(scope.row)"
								>历史</el-button
							>
						</template>
					</vxe-column>
				</vxe-table>
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
			</div>
		</div>
	</div>
</template>

<script>
// import FlowChart from '../modeler/FlowChart'
import pick from "lodash.pick";
import taskService from "@/api/flowable/taskService";
export default {
	data() {
		return {
			searchForm: {
				beginDate: "",
				endDate: "",
			},
			searchVisible: true,
			searchDates: "",
			dataList: [],
			tablePage: {
				total: 0,
				currentPage: 1,
				pageSize: 10,
				orders: [],
			},
			loading: false,
			visible: false,
		};
	},
	mounted() {
		this.$nextTick(() => {
			// 将表格和工具栏进行关联
			const $table = this.$refs.todoTable;
			const $toolbar = this.$refs.todoToolbar;
			$table.connect($toolbar);
		});
	},
	activated() {
		this.refreshList();
	},
	watch: {
		searchDates() {
			if (this.searchDates) {
				this.searchForm.beginDate = this.searchDates[0];
				this.searchForm.endDate = this.searchDates[1];
			} else {
				this.searchForm.beginDate = "";
				this.searchForm.endDate = "";
			}
		},
	},
	methods: {
		// 获取数据列表
		refreshList() {
			this.loading = true;
			taskService
				.historicList({
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
		detail(row) {
			this.$router.push({
				path: "/flowable/task/TaskFormDetail",
				query: {
					taskDefKey: row.taskDefinitionKey,
					procInsId: row.processInstanceId,
					procDefId: row.processDefinitionId,
					readOnly: true,
					// taskId: row.currentTask.id,
					title: `${row.processDefinitionName}【${row.name}】`,
				},
			});
		},
		// 取回
		callback(row) {
			console.log(row);
			this.$confirm(`确定撤销该已办任务吗?`, "提示", {
				confirmButtonText: "确定",
				cancelButtonText: "取消",
				type: "warning",
			}).then(() => {
				this.loading = true;
				taskService
					.callback({
						processInstanceId: row.processInstanceId,
						preTaskDefKey: row.taskDefinitionKey,
						preTaskId: row.id,
						currentTaskId: row.currentTask.id,
						currentTaskDefKey: row.currentTask.taskDefinitionKey,
					})
					.then((data) => {
						this.loading = false;
						this.$message.success(data);
						this.refreshList();
					});
			});
		},
		resetSearch() {
			this.searchDates = "";
			this.$refs.searchForm.resetFields();
			this.$nextTick(() => {
				this.refreshList();
			});
		},
	},
};
</script>
