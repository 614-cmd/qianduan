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
			<el-form-item prop="name" label="岗位名称：">
				<el-input
					v-model="searchForm.name"
					placeholder="请输入岗位名称"
					clearable
				></el-input>
			</el-form-item>
			<el-form-item prop="code" label="岗位编码：">
				<el-input
					v-model="searchForm.code"
					placeholder="请输入岗位编码"
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
				ref="postToolbar"
				:refresh="{ query: refreshList }"
				export
				print
				custom
			>
				<template #buttons>
					<el-button
						v-if="hasPermission('sys:post:add')"
						type="primary"
						icon="plus"
						@click="add()"
						>新建</el-button
					>
					<el-button
						v-if="hasPermission('sys:post:edit')"
						type="warning"
						icon="edit-filled"
						@click="edit()"
						v-show="
							$refs.postTable &&
							$refs.postTable.getCheckboxRecords().length === 1
						"
						>修改</el-button
					>
					<el-button
						v-if="hasPermission('sys:post:del')"
						type="danger"
						icon="del-filled"
						@click="del()"
						v-show="
							$refs.postTable &&
							$refs.postTable.getCheckboxRecords().length > 0
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
					ref="postTable"
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
					<vxe-column type="checkbox" width="40px"></vxe-column>
					<vxe-column title="岗位名称" field="name" sortable>
						<template #default="{ row }">
							<el-link
								type="primary"
								:underline="false"
								v-if="hasPermission('sys:post:edit')"
								@click="edit(row.id)"
								>{{ row.name }}</el-link
							>
							<el-link
								type="primary"
								:underline="false"
								v-else-if="hasPermission('sys:post:view')"
								@click="view(row.id)"
								>{{ row.name }}</el-link
							>
							<span v-else>{{ row.name }}</span>
						</template>
					</vxe-column>
					<vxe-column title="岗位编码" field="code" sortable>
					</vxe-column>
					<vxe-column title="岗位类型" field="type" sortable>
						<template #default="{ row }">
							{{
								$dictUtils.getDictLabel(
									"sys_post_type",
									row.type,
									"-"
								)
							}}
						</template>
					</vxe-column>
					<vxe-column title="是否可用" field="status" sortable>
						<template #default="{ row }">
							<el-tag v-if="row.status === '1'">
								{{
									$dictUtils.getDictLabel(
										"yes_no",
										row.status,
										"-"
									)
								}}
							</el-tag>
							<el-tag v-else type="danger">
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
						title="岗位排序"
						field="sort"
						sortable
					></vxe-column>
					<vxe-column
						title="备注信息"
						field="remarks"
						sortable
					></vxe-column>
					<vxe-column
						title="操作"
						width="200px"
						fixed="right"
						align="center"
					>
						<template #default="{ row }">
							<el-button
								v-if="hasPermission('sys:post:view')"
								type="primary"
								text
								icon="view-filled"
								@click="view(row.id)"
								>查看</el-button
							>
							<el-button
								v-if="hasPermission('sys:post:edit')"
								type="primary"
								text
								icon="edit-filled"
								@click="edit(row.id)"
								>修改</el-button
							>
							<el-button
								v-if="hasPermission('sys:post:del')"
								type="danger"
								text
								icon="del-filled"
								@click="del(row.id)"
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
		<PostForm ref="postForm" @refreshDataList="refreshList"></PostForm>
	</div>
</template>

<script>
import PostForm from "./PostForm";
import postService from "@/api/sys/postService";
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
				orders: [{ column: "create_time", asc: false }],
			},
			loading: false,
		};
	},
	components: {
		PostForm,
	},
	mounted() {
		this.$nextTick(() => {
			// 将表格和工具栏进行关联
			const $table = this.$refs.postTable;
			const $toolbar = this.$refs.postToolbar;
			$table.connect($toolbar);
		});
		this.refreshList();
	},

	methods: {
		// 获取数据列表
		refreshList() {
			this.loading = true;
			postService
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
				this.tablePage.orders = [{ column: "create_time", asc: false }];
			}
			this.refreshList();
		},
		// 新增
		add() {
			this.$refs.postForm.init("add", "");
		},
		// 修改
		edit(id) {
			id =
				id ||
				this.$refs.postTable.getCheckboxRecords().map((item) => {
					return item.id;
				})[0];
			this.$refs.postForm.init("edit", id);
		},
		// 查看
		view(id) {
			this.$refs.postForm.init("view", id);
		},
		// 删除
		del(id) {
			let ids =
				id ||
				this.$refs.postTable
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
				postService.delete(ids).then((data) => {
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
