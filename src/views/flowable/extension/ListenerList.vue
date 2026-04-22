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
			<!-- 搜索框-->
			<el-form-item prop="name" label="名称：">
				<el-input
					v-model="searchForm.name"
					placeholder="请输入名称"
					clearable
				></el-input>
			</el-form-item>
			<el-form-item prop="listenerType">
				<el-radio-group v-model="searchForm.listenerType">
					<el-radio
						v-for="item in $dictUtils.getDictList('')"
						:value="item.value"
						:key="item.id"
						>{{ item.label }}</el-radio
					>
				</el-radio-group>
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
				ref="listenerToolbar"
				:refresh="{ query: refreshList }"
				export
				print
				custom
			>
				<template #buttons>
					<el-button
						v-if="hasPermission('extension:listener:add')"
						type="primary"
						icon="plus"
						@click="add()"
						>新建</el-button
					>
					<el-button
						v-if="hasPermission('extension:listener:edit')"
						type="warning"
						icon="edit-filled"
						@click="edit()"
						v-show="
							$refs.listenerTable &&
							$refs.listenerTable.getCheckboxRecords().length == 1
						"
						>修改</el-button
					>
					<el-button
						v-if="hasPermission('extension:listener:del')"
						type="danger"
						icon="del-filled"
						@click="del()"
						v-show="
							$refs.listenerTable &&
							$refs.listenerTable.getCheckboxRecords().length > 0
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
					ref="listenerTable"
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
					<vxe-column title="名称" field="name" sortable>
						<template #default="scope">
							<el-link
								type="primary"
								:underline="false"
								v-if="hasPermission('extension:listener:edit')"
								@click="edit(scope.row.id)"
								>{{ scope.row.name }}</el-link
							>
							<el-link
								type="primary"
								:underline="false"
								v-else-if="
									hasPermission('extension:listener:view')
								"
								@click="view(scope.row.id)"
								>{{ scope.row.name }}</el-link
							>
							<span v-else>{{ scope.row.name }}</span>
						</template>
					</vxe-column>
					<vxe-column
						title="监听器类型"
						field="listenerType"
						sortable
					>
						<template #default="scope">
							{{
								scope.row.listenerType === "1"
									? "执行监听器"
									: "任务监听器"
							}}
						</template>
					</vxe-column>
					<vxe-column title="事件" field="event" sortable>
					</vxe-column>
					<vxe-column title="值类型" field="valueType" sortable>
						<template #default="scope">
							{{
								{ "1": "类", "2": "表达式", "3": "委托表达式" }[
									scope.row.valueType
								]
							}}
						</template>
					</vxe-column>
					<vxe-column title="值" field="value" sortable> </vxe-column>
					<vxe-column
						title="操作"
						width="200px"
						fixed="right"
						align="center"
					>
						<template #default="scope">
							<el-button
								v-if="hasPermission('extension:listener:view')"
								type="primary"
								text
								icon="view-filled"
								@click="view(scope.row.id)"
							>
								查看
							</el-button>
							<el-button
								v-if="hasPermission('extension:listener:edit')"
								type="primary"
								text
								icon="edit-filled"
								@click="edit(scope.row.id)"
							>
								修改
							</el-button>
							<el-button
								v-if="hasPermission('extension:listener:del')"
								type="danger"
								text
								icon="del-filled"
								@click="del(scope.row.id)"
							>
								删除
							</el-button>
						</template>
					</vxe-column>
				</vxe-table>
			</div>
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
		<!-- 弹窗, 新增 / 修改 -->
		<ListenerForm
			ref="listenerForm"
			@refreshDataList="refreshList"
		></ListenerForm>
	</div>
</template>

<script>
import ListenerForm from "./ListenerForm";
import listenerService from "@/api/flowable/listenerService";
export default {
	data() {
		return {
			searchForm: {
				name: "",
				listenerType: "",
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
		ListenerForm,
	},
	mounted() {
		this.$nextTick(() => {
			// 将表格和工具栏进行关联
			const $table = this.$refs.listenerTable;
			const $toolbar = this.$refs.listenerToolbar;
			$table.connect($toolbar);
		});
		this.refreshList();
	},

	methods: {
		// 获取数据列表
		refreshList() {
			this.loading = true;
			listenerService
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
			this.$refs.listenerForm.init("add", "");
		},
		// 修改
		edit(id) {
			id =
				id ||
				this.$refs.listenerTable.getCheckboxRecords().map((item) => {
					return item.id;
				})[0];
			this.$refs.listenerForm.init("edit", id);
		},
		// 查看
		view(id) {
			this.$refs.listenerForm.init("view", id);
		},
		// 删除
		del(id) {
			let ids =
				id ||
				this.$refs.listenerTable
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
				listenerService.delete(ids).then((data) => {
					this.loading = false;
					this.$message.success(data);
					this.refreshList();
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
