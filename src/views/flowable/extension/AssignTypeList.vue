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
				ref="assignTypeToolbar"
				:refresh="{ query: refreshList }"
				export
				print
				custom
			>
				<template #buttons>
					<el-button
						v-if="hasPermission('extension:assignType:add')"
						type="primary"
						icon="plus"
						@click="add()"
						>新建</el-button
					>
					<el-button
						v-if="hasPermission('extension:assignType:edit')"
						type="warning"
						icon="edit-filled"
						@click="edit()"
						v-show="
							$refs.assignType &&
							$refs.assignType.getCheckboxRecords().length == 1
						"
						>修改</el-button
					>
					<el-button
						v-if="hasPermission('extension:assignType:del')"
						type="danger"
						icon="del-filled"
						@click="del()"
						v-show="
							$refs.assignType &&
							$refs.assignType.getCheckboxRecords().length > 0
						"
						>删除</el-button
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
					ref="assignTypeTable"
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
					<vxe-column field="code" sortable title="待办人编码">
						<template #default="scope">
							<el-link
								type="primary"
								:underline="false"
								v-if="
									hasPermission('extension:assignType:edit')
								"
								@click="edit(scope.row.id)"
								>{{ scope.row.code }}</el-link
							>
							<el-link
								type="primary"
								:underline="false"
								v-else-if="
									hasPermission('extension:assignType:view')
								"
								@click="view(scope.row.id)"
								>{{ scope.row.code }}</el-link
							>
							<span v-else>{{ scope.row.code }}</span>
						</template>
					</vxe-column>
					<vxe-column field="name" sortable title="待办人名称">
					</vxe-column>
					<vxe-column field="sort" sortable title="排序">
					</vxe-column>
					<vxe-column field="remarks" sortable title="说明">
					</vxe-column>
					<vxe-column
						fixed="right"
						align="center"
						width="200"
						title="操作"
					>
						<template #default="scope">
							<el-button
								v-if="
									hasPermission('extension:assignType:view')
								"
								type="primary"
								text
								icon="view-filled"
								@click="view(scope.row.id)"
								>查看</el-button
							>
							<el-button
								v-if="
									hasPermission('extension:assignType:edit')
								"
								type="primary"
								text
								icon="edit-filled"
								@click="edit(scope.row.id)"
								>修改</el-button
							>
							<el-button
								v-if="hasPermission('extension:assignType:del')"
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
		<!-- 弹窗, 新增 / 修改 -->
		<AssignTypeForm
			ref="assignTypeForm"
			@refreshDataList="refreshList"
		></AssignTypeForm>
	</div>
</template>

<script>
import AssignTypeForm from "./AssignTypeForm";
import assignTypeService from "@/api/flowable/assignTypeService";
export default {
	data() {
		return {
			searchVisible: true,
			searchForm: {},
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
		AssignTypeForm,
	},
	mounted() {
		this.$nextTick(() => {
			// 将表格和工具栏进行关联
			const $table = this.$refs.assignTypeTable;
			const $toolbar = this.$refs.assignTypeToolbar;
			$table.connect($toolbar);
		});
		this.refreshList();
	},
	methods: {
		// 获取数据列表
		refreshList() {
			this.loading = true;
			assignTypeService
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
			this.$refs.assignTypeForm.init("add", "");
		},
		// 修改
		edit(id) {
			id =
				id ||
				this.$refs.assignTypeTable.getCheckboxRecords().map((item) => {
					return item.id;
				})[0];
			this.$refs.assignTypeForm.init("edit", id);
		},
		// 查看
		view(id) {
			this.$refs.assignTypeForm.init("view", id);
		},
		// 删除
		del(id) {
			let ids =
				id ||
				this.$refs.assignTypeTable
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
				assignTypeService.delete(ids).then((data) => {
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
