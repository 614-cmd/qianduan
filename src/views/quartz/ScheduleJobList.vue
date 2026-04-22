<template>
	<div class="page">
		<el-form
			:inline="true"
			class="query-form m-b-10"
			v-if="searchVisible"
			ref="searchForm"
			:model="searchForm"
			@keyup.enter="refreshList()"
			@submit.prevent
		>
			<!-- 搜索框-->
			<el-form-item prop="name">
				<el-input
					v-model="searchForm.name"
					placeholder="任务名"
					clearable
				></el-input>
			</el-form-item>
			<el-form-item>
				<el-button
					type="primary"
					@click="refreshList()"
					icon="el-icon-search"
					>查询</el-button
				>
				<el-button
					type="default"
					@click="resetSearch()"
					icon="el-icon-refresh-right"
					>重置</el-button
				>
			</el-form-item>
		</el-form>
		<div class="jp-table">
			<vxe-toolbar
				ref="jobToolbar"
				:refresh="{ query: refreshList }"
				print
				custom
			>
				<template #buttons>
					<el-button
						v-if="hasPermission('quartz:scheduleJob:add')"
						type="primary"
						icon="el-icon-plus"
						@click="add()"
						>新建</el-button
					>
					<el-button
						v-if="hasPermission('quartz:scheduleJob:edit')"
						type="warning"
						icon="edit-filled"
						@click="edit()"
						v-show="
							$refs.jobTable &&
							$refs.jobTable.getCheckboxRecords().length == 1
						"
						plain
						>修改</el-button
					>
					<el-button
						v-if="hasPermission('quartz:scheduleJob:del')"
						type="danger"
						icon="del-filled"
						@click="del()"
						v-show="
							$refs.jobTable &&
							$refs.jobTable.getCheckboxRecords().length > 0
						"
						plain
						>删除</el-button
					>
					<el-button
						v-if="hasPermission('quartz:scheduleJob:startNow')"
						type="success"
						icon="el-icon-edit-outline"
						@click="startNow()"
						v-show="
							$refs.jobTable &&
							$refs.jobTable.getCheckboxRecords().length === 1
						"
						plain
						>立即执行一次</el-button
					>
				</template>
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
					ref="jobTable"
					show-header-overflow
					show-overflow
					highlight-hover-row
					:menu-config="{}"
					:print-config="{}"
					:import-config="{}"
					:export-config="{}"
					@sort-change="sortChangeHandle"
					:sort-config="{ remote: true }"
					:data="dataList"
					:checkbox-config="{}"
				>
					<vxe-column type="seq" width="40"></vxe-column>
					<vxe-column type="checkbox" width="40px"></vxe-column>
					<vxe-column title="任务名" field="name" sortable>
						<template #default="{ row }">
							<el-link
								type="primary"
								:underline="false"
								v-if="hasPermission('quartz:scheduleJob:edit')"
								@click="edit(row.id)"
								>{{ row.name }}</el-link
							>
							<span v-else>{{ scope.row.name }}</span>
						</template>
					</vxe-column>
					<vxe-column title="任务组" field="jobGroup" sortable>
						<template #default="{ row }">
							{{
								$dictUtils.getDictLabel(
									"schedule_task_group",
									row.jobGroup,
									"-"
								)
							}}
						</template>
					</vxe-column>
					<vxe-column
						title="定时规则"
						field="cronExpression"
						sortable
					></vxe-column>
					<vxe-column title="启用状态" field="status" sortable>
						<template #default="{ row }">
							<el-tag v-if="row.status === '0'" type="danger">
								{{
									$dictUtils.getDictLabel(
										"yes_no",
										row.status,
										"-"
									)
								}}
							</el-tag>
							<el-tag v-else type="success">
								{{
									$dictUtils.getDictLabel(
										"yes_no",
										row.status,
										"-"
									)
								}}
							</el-tag>
						</template>
					</vxe-column>
					<vxe-column
						title="任务类"
						field="className"
						sortable
					></vxe-column>
					<vxe-column
						title="描述"
						field="remarks"
						sortable
					></vxe-column>
					<vxe-column title="操作">
						<template #default="{ row }">
							<el-button
								v-if="
									hasPermission('quartz:scheduleJob:resume')
								"
								text
								type="primary"
								icon="el-icon-video-play"
								@click="start(row.id)"
								>启动</el-button
							>
							<el-button
								v-if="hasPermission('quartz:scheduleJob:stop')"
								text
								type="danger"
								icon="el-icon-video-pause"
								@click="stop(row.id)"
								>暂停</el-button
							>
						</template>
					</vxe-column>
				</vxe-table>
				<vxe-pager
					background
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
		<!-- 弹窗, 新增 / 修改 -->
		<ScheduleJobForm
			ref="scheduleJobForm"
			@refreshDataList="refreshList"
		></ScheduleJobForm>
	</div>
</template>

<script>
import ScheduleJobForm from "./ScheduleJobForm";
import quartzService from "@/api/quartz/quartzService";
export default {
	data() {
		return {
			searchForm: {
				name: "",
			},
			searchVisible: true,
			dataList: [],
			tablePage: {
				total: 0,
				currentPage: 1,
				pageSize: 10,
				orders: [],
			},
			loading: false,
		};
	},
	components: {
		ScheduleJobForm,
	},
	mounted() {
		this.$nextTick(() => {
			// 将表格和工具栏进行关联
			const $table = this.$refs.jobTable;
			const $toolbar = this.$refs.jobToolbar;
			$table.connect($toolbar);
		});
		this.refreshList();
	},

	methods: {
		// 获取数据列表
		refreshList() {
			this.loading = true;
			quartzService
				.list({
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
		// 排序
		sortChangeHandle(column) {
			this.tablePage.orders = [];
			if (column.order != null) {
				this.tablePage.orders.push({
					column: this.$utils.toLine(column.property),
					asc: column.order === "asc",
				});
			}
			this.refreshList();
		},
		// 新增
		add() {
			this.$refs.scheduleJobForm.init("add", "");
		},
		// 修改
		edit(id) {
			id =
				id ||
				this.$refs.jobTable.getCheckboxRecords().map((item) => {
					return item.id;
				})[0];
			this.$refs.scheduleJobForm.init("edit", id);
		},
		// 查看
		view(id) {
			this.$refs.scheduleJobForm.init("view", id);
		},
		// 删除
		del(id) {
			let ids =
				id ||
				this.$refs.jobTable
					.getCheckboxRecords()
					.map((item) => {
						return item.id;
					})
					.join(",");
			this.$confirm(`确定删除所选项吗?`, "提示", {
				confirmButtonText: "确定",
				cancelButtonText: "取消",
				type: "warning",
			}).then(() => {
				this.loading = true;
				quartzService.delete(ids).then((data) => {
					this.$message.success(data);
					this.refreshList();
					this.loading = false;
				});
			});
		},
		// 启动
		start(id) {
			id =
				id ||
				this.$refs.jobTable.getCheckboxRecords().map((item) => {
					return item.id;
				})[0];
			this.$confirm(`确定要启动任务吗?`, "提示", {
				confirmButtonText: "确定",
				cancelButtonText: "取消",
				type: "warning",
			}).then(() => {
				this.loading = true;
				quartzService.resume(id).then((data) => {
					this.$message.success(data);
					this.refreshList();
					this.loading = false;
				});
			});
		},
		// 暂停
		stop(id) {
			id =
				id ||
				this.$refs.jobTable.getCheckboxRecords().map((item) => {
					return item.id;
				})[0];
			this.$confirm(`确定要暂停任务吗?`, "提示", {
				confirmButtonText: "确定",
				cancelButtonText: "取消",
				type: "warning",
			}).then(() => {
				this.loading = true;
				quartzService.stop(id).then((data) => {
					this.$message.success(data);
					this.refreshList();
					this.loading = false;
				});
			});
		},
		// 立即执行一次
		startNow(id) {
			id =
				id ||
				this.$refs.jobTable.getCheckboxRecords().map((item) => {
					return item.id;
				})[0];
			this.$confirm(`确定要立即执行一次该任务吗?`, "提示", {
				confirmButtonText: "确定",
				cancelButtonText: "取消",
				type: "warning",
			}).then(() => {
				this.loading = true;
				quartzService.startNow(id).then((data) => {
					this.$message.success(data);
					this.refreshList();
					this.loading = false;
				});
			});
		},
		resetSearch() {
			this.$refs.searchForm.resetFields();
			this.refreshList();
		},
	},
};
</script>
