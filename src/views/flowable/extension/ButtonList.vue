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
			<el-form-item prop="name" label="名称：">
				<el-input
					v-model="searchForm.name"
					placeholder="请输入名称"
					clearable
				></el-input>
			</el-form-item>
			<el-form-item prop="code" label="编码：">
				<el-input
					v-model="searchForm.code"
					placeholder="请输入编码"
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
				ref="buttonToolbar"
				:refresh="{ query: refreshList }"
				export
				print
				custom
			>
				<template #buttons>
					<el-button
						v-if="hasPermission('extension:button:add')"
						type="primary"
						icon="plus"
						@click="add()"
						>新建</el-button
					>
					<el-button
						v-if="hasPermission('extension:button:edit')"
						type="warning"
						icon="edit-filled"
						@click="edit()"
						v-show="
							$refs.buttonTable &&
							$refs.buttonTable.getCheckboxRecords().length == 1
						"
						>修改</el-button
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
					ref="buttonTable"
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
								v-if="hasPermission('extension:button:edit')"
								@click="edit(scope.row.id)"
								>{{ scope.row.name }}</el-link
							>
							<el-link
								type="primary"
								:underline="false"
								v-else-if="
									hasPermission('extension:button:view')
								"
								@click="view(scope.row.id)"
								>{{ scope.row.name }}</el-link
							>
							<span v-else>{{ scope.row.name }}</span>
						</template>
					</vxe-column>
					<vxe-column title="编码" field="code" sortable>
					</vxe-column>
					<vxe-column title="排序" field="sort" sortable>
					</vxe-column>
					<vxe-column
						title="操作"
						width="230px"
						fixed="right"
						align="center"
					>
						<template #default="scope">
							<el-button
								v-if="hasPermission('extension:button:view')"
								type="primary"
								text
								icon="view-filled"
								@click="view(scope.row.id)"
								>查看</el-button
							>
							<el-button
								v-if="hasPermission('extension:button:edit')"
								type="primary"
								text
								icon="edit-filled"
								@click="edit(scope.row.id)"
								>修改</el-button
							>
							<el-button
								v-if="hasPermission('extension:button:del')"
								type="danger"
								text
								icon="del-filled"
								@click="del(scope.row)"
								>删除</el-button
							>
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
		<ButtonForm
			ref="buttonForm"
			@refreshDataList="refreshList"
		></ButtonForm>
	</div>
</template>

<script>
import ButtonForm from "./ButtonForm";
import buttonService from "@/api/flowable/buttonService";
export default {
	data() {
		return {
			searchVisible: true,
			searchForm: {
				name: "",
				code: "",
			},
			dataList: [],
			tablePage: {
				total: 0,
				currentPage: 1,
				pageSize: 10,
				orders: [{ column: "sort", asc: true }],
			},
			loading: false,
		};
	},
	components: {
		ButtonForm,
	},
	mounted() {
		this.$nextTick(() => {
			// 将表格和工具栏进行关联
			const $table = this.$refs.buttonTable;
			const $toolbar = this.$refs.buttonToolbar;
			$table.connect($toolbar);
		});
		this.refreshList();
	},

	methods: {
		// 获取数据列表
		refreshList() {
			this.loading = true;
			buttonService
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
			} else {
				this.tablePage.orders.push({ column: "sort", asc: true });
			}
			this.refreshList();
		},
		// 新增
		add() {
			this.$refs.buttonForm.init("add", "");
		},
		// 修改
		edit(id) {
			id =
				id ||
				this.$refs.buttonTable.getCheckboxRecords().map((item) => {
					return item.id;
				})[0];
			this.$refs.buttonForm.init("edit", id);
		},
		// 查看
		view(id) {
			this.$refs.buttonForm.init("view", id);
		},
		isDefaultButton(code) {
			let defaultButtons = [
				"_flow_save",
				"_flow_agree",
				"_flow_reject",
				"_flow_back",
				"_flow_add_multi_instance",
				"_flow_del_multi_instance",
				"_flow_transfer",
				"_flow_delegate",
				"_flow_stop",
				"_flow_print",
			];
			return (
				defaultButtons.filter((val) => {
					return code === val;
				}).length > 0
			);
		},
		// 删除
		del(row) {
			if (this.isDefaultButton(row.code)) {
				this.$message.error(`不能删除预设按钮【${row.name}】`);
				return;
			}
			this.$confirm(`确定删除所选项吗?`, "提示", {
				confirmButtonText: "确定",
				cancelButtonText: "取消",
				type: "warning",
			}).then(() => {
				this.loading = true;
				buttonService.delete(row.id).then((data) => {
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
