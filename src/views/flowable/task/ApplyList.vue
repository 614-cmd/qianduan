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
			<el-form-item label="创建时间：" prop="searchDates">
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
				ref="applyToolbar"
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
					ref="applyTable"
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
					<vxe-column title="流程标题" field="vars.title">
					</vxe-column>
					<vxe-column title="流程名称" field="processDefinitionName">
					</vxe-column>
					<vxe-column title="当前节点" field="taskName"></vxe-column>
					<vxe-column title="流程状态" field="status">
						<template #default="scope">
							<el-tag :type="scope.row.level"
								>{{ scope.row.status }}
							</el-tag>
						</template>
					</vxe-column>
					<vxe-column field="startTime" type="html" title="发起时间">
					</vxe-column>
					<vxe-column field="endTime" type="html" title="结束时间">
					</vxe-column>
					<vxe-column
						title="操作"
						width="150px"
						type="html"
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
							<el-dropdown
								v-if="
									scope.row.code === 1 ||
									scope.row.code === 3 ||
									scope.row.code === 4
								"
								style="margin-left: 10px"
							>
								<el-button type="primary" text>
									更多<el-icon class="el-icon--right"
										><arrow-down
									/></el-icon>
								</el-button>
								<template #dropdown>
									<el-dropdown-menu>
										<el-dropdown-item
											v-if="scope.row.code === 1"
											><el-button
												text
												type="primary"
												@click="urge(scope.row)"
												>催办</el-button
											></el-dropdown-item
										>
										<el-dropdown-item
											v-if="scope.row.code === 1"
											><el-button
												text
												type="primary"
												@click="revoke(scope.row)"
												>撤销</el-button
											></el-dropdown-item
										>
										<el-dropdown-item
											v-if="
												scope.row.code === 3 ||
												scope.row.code === 4
											"
											><el-button
												text
												type="primary"
												@click="restart(scope.row)"
												>编辑</el-button
											></el-dropdown-item
										>
									</el-dropdown-menu>
								</template>
							</el-dropdown>
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
		<v-dialog
			title="查看流程历史"
			:close-on-click-modal="true"
			v-model="visible"
			height="600px"
		>
			<iframe
				:src="processPhotoUrl"
				frameborder="0"
				scrolling="auto"
				width="100%"
				height="600px"
			></iframe>
		</v-dialog>
		<urge-form ref="urgeForm"></urge-form>
	</div>
</template>

<script>
// import FlowChart from '../modeler/FlowChart'
import UrgeForm from "./UrgeForm";
import taskService from "@/api/flowable/taskService";
import processService from "@/api/flowable/processService";
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
			processPhotoUrl: "",
		};
	},
	mounted() {
		this.$nextTick(() => {
			// 将表格和工具栏进行关联
			const $table = this.$refs.applyTable;
			const $toolbar = this.$refs.applyToolbar;
			$table.connect($toolbar);
		});
	},
	activated() {
		this.refreshList();
	},
	components: {
		UrgeForm,
		// FlowChart
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
				.myAppliedList({
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
		trace(row) {
			this.processPhotoUrl = `${this.$http.BASE_URL}/flowable/task/trace/photo/${row.processInstanceId}`;
			this.visible = true;
		},
		detail(row) {
			this.$router.push({
				path: "/flowable/task/TaskFormDetail",
				query: {
					readOnly: true,
					title: row.processDefinitionName,
					procInsId: row.processInstanceId,
					procDefId: row.processDefinitionId,
				},
			});
		},
		// 重新填写
		restart(row) {
			// 读取流程表单
			this.$router.push({
				path: "/flowable/task/TaskFormEdit",
				query: {
					status: "start",
					title: row.vars.title,
					procInsId: row.processInstanceId,
					procDefId: row.processDefinitionId,
				},
			});
		},
		// 撤销申请
		revoke(row) {
			this.$confirm(`确定要撤销该流程吗?`, "提示", {
				confirmButtonText: "确定",
				cancelButtonText: "取消",
				type: "warning",
			}).then(() => {
				processService
					.revokeProcIns(row.processInstanceId)
					.then((data) => {
						this.$message.success(data);
						this.refreshList();
					});
			});
		},
		urge(row) {
			this.$refs.urgeForm.init(row.task.id);
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
