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
			<el-form-item label="实例名称：" prop="title">
				<el-input
					v-model="searchForm.title"
					clearable
					placeholder="请输入关键词"
				></el-input>
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
				ref="historyToolbar"
				:refresh="{ query: refreshList }"
				export
				print
				custom
			>
				<template #buttons>
					<el-button
						type="danger"
						icon="del-filled"
						@click="del()"
						v-show="
							$refs.historyTable &&
							$refs.historyTable.getCheckboxRecords().length > 0
						"
						>删除
					</el-button>
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
					ref="historyTable"
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
					<vxe-column title="实例名称" field="vars.title">
					</vxe-column>
					<vxe-column title="流程状态" field="status">
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
					<vxe-column title="发起时间 " field="startTime">
					</vxe-column>
					<vxe-column title="结束时间" field="endTime"> </vxe-column>
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
								>历史
							</el-button>
							<el-button
								type="primary"
								text
								@click="trace(scope.row)"
								>流程图
							</el-button>
							<el-button
								type="danger"
								text
								@click="del(scope.row.id)"
								>删除
							</el-button>
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
				<v-dialog
					title="查看进度"
					:close-on-click-modal="true"
					v-model="visible"
					width="70%"
					height="600px"
				>
					<flow-chart
						ref="preview"
						:processInstanceId="processInstanceId"
					></flow-chart>
				</v-dialog>
			</div>
		</div>
	</div>
</template>

<script>
// import FlowChart from '../modeler/FlowChart'
import pick from "lodash.pick";
import taskService from "@/api/flowable/taskService";
import processService from "@/api/flowable/processService";
export default {
	data() {
		return {
			searchVisible: true,
			searchForm: {
				title: "",
			},
			tablePage: {
				total: 0,
				currentPage: 1,
				pageSize: 10,
				orders: [],
			},
			dataList: [],
			loading: false,
			visible: false,
			processInstanceId: "",
		};
	},
	mounted() {
		this.$nextTick(() => {
			// 将表格和工具栏进行关联
			const $table = this.$refs.historyTable;
			const $toolbar = this.$refs.historyToolbar;
			$table.connect($toolbar);
		});
	},
	activated() {
		this.refreshList();
	},
	methods: {
		// 获取数据列表
		refreshList() {
			this.loading = true;
			processService
				.historyListData({
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
			this.processInstanceId = row.processInstanceId;
			this.visible = true;
			this.$nextTick(() => {
				this.$refs.preview.init();
			});
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
		// 撤销申请
		del(id) {
			let ids =
				id ||
				this.$refs.historyTable
					.getCheckboxRecords()
					.map((item) => {
						return item.id;
					})
					.join(",");
			this.$confirm(`确定要删除历史流程吗?`, "提示", {
				confirmButtonText: "确定",
				cancelButtonText: "取消",
				type: "warning",
			}).then(() => {
				this.loading = true;
				processService.deleteAllProcIns(ids).then((data) => {
					this.refreshList();
					this.$message.success({
						dangerouslyUseHTMLString: true,
						message: data,
					});
				});
			});
		},
		resetSearch() {
			this.$refs.searchForm.resetFields();
			this.$nextTick(() => {
				this.refreshList();
			});
		},
	},
};
</script>
