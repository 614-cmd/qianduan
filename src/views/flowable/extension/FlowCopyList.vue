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
			<el-form-item prop="procInsName" label="标题：">
				<el-input
					v-model="searchForm.procInsName"
					placeholder="请输入标题"
					clearable
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
				ref="flowCopyToolbar"
				:refresh="{ query: refreshList }"
				export
				print
				custom
			>
				<template #buttons>
					<el-button
						type="danger"
						v-if="hasPermission('extension:flowCopy:del')"
						icon="del-filled"
						@click="del()"
						v-show="
							$refs.flowCopyTable &&
							$refs.flowCopyTable.getCheckboxRecords().length > 0
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
					ref="flowCopyTable"
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
					<vxe-column
						title="实例标题"
						field="procInsName"
						sortable
					></vxe-column>
					<vxe-column title="抄送日期" field="createTime" sortable>
					</vxe-column>
					<vxe-column
						title="抄送发起人"
						field="createBy.name"
						sortable
					></vxe-column>
					<vxe-column
						title="操作"
						width="180px"
						type="html"
						fixed="right"
						align="center"
					>
						<template #default="scope">
							<el-button
								v-if="hasPermission('extension:flowCopy:view')"
								type="primary"
								text
								icon="view-filled"
								@click="detail(scope.row)"
								>查阅审批</el-button
							>
							<el-button
								v-if="hasPermission('extension:flowCopy:del')"
								type="danger"
								text
								icon="del-filled"
								@click="del(scope.row.id)"
								>删除</el-button
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
import flowCopyService from "@/api/flowable/flowCopyService";
export default {
	data() {
		return {
			searchVisible: true,
			searchForm: {
				procInsName: "",
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
		};
	},
	mounted() {
		this.$nextTick(() => {
			// 将表格和工具栏进行关联
			const $table = this.$refs.flowCopyTable;
			const $toolbar = this.$refs.flowCopyToolbar;
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
			flowCopyService
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
					title: "抄送事项 / " + row.procDefName,
					procInsId: row.procInsId,
					procDefId: row.procDefId,
				},
			});
		},
		// 删除
		del(id) {
			let ids =
				id ||
				this.$refs.flowCopyTable
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
				flowCopyService.delete(ids).then((data) => {
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
			this.refreshList();
		},
	},
};
</script>
