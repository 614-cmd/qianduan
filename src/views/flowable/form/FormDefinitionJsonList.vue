<template>
	<div class="page">
		<el-page-header
			@back="goBack"
			content="版本列表"
			class="page-header"
		></el-page-header>
		<div class="jp-table">
			<vxe-toolbar
				ref="formDefinitionJsonToolbar"
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
					ref="formDefinitionJsonTable"
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
						title="表单定义id"
						field="formDefinitionId"
						sortable
					>
						<template #default="scope">
							<el-link
								type="primary"
								:underline="false"
								@click="view(scope.row.id)"
								>{{ scope.row.formDefinitionId }}</el-link
							>
						</template>
					</vxe-column>
					<vxe-column title="流程表单结构体" field="json" sortable>
					</vxe-column>
					<vxe-column title="版本号" field="version" sortable>
					</vxe-column>
					<vxe-column title="状态" field="status" sortable>
						<template #default="scope">
							<el-tag
								v-if="scope.row.status === '1'"
								type="success"
								>已发布</el-tag
							>
							<el-tag v-else type="danger">草稿</el-tag>
						</template>
					</vxe-column>
					<vxe-column title="是否主版本" field="isPrimary" sortable>
						<template #default="scope">
							<el-tag
								v-if="scope.row.isPrimary === '1'"
								type="success"
								>主版本</el-tag
							>
							<el-tag v-else type="danger">非主版本</el-tag>
						</template>
					</vxe-column>
					<vxe-column
						title="操作"
						width="300px"
						fixed="right"
						align="center"
					>
						<template #default="scope">
							<el-button
								text
								type="primary"
								icon="view-filled"
								@click="view(scope.row.id)"
								>预览</el-button
							>
							<el-button
								text
								type="primary"
								icon="edit-filled"
								v-if="scope.row.isPrimary === '0'"
								@click="updatePrimary(scope.row.id)"
								>设置为主版本</el-button
							>
							<el-button
								text
								type="danger"
								icon="del-filled"
								v-if="scope.row.isPrimary === '0'"
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
		<FormDefinitionJsonForm
			ref="formDefinitionJsonForm"
			@refreshDataList="refreshList"
		></FormDefinitionJsonForm>
	</div>
</template>

<script>
import useTabs from "@/utils/useTabs";
import FormDefinitionJsonForm from "./FormDefinitionJsonForm";
import formDefinitionJsonService from "@/api/flowable/formDefinitionJsonService";
export default {
	data() {
		return {
			searchForm: {
				formDefinitionId: "",
				version: "",
				status: "",
				isPrimary: "",
			},
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
		FormDefinitionJsonForm,
	},
	mounted() {
		this.$nextTick(() => {
			// 将表格和工具栏进行关联
			const $table = this.$refs.formDefinitionJsonTable;
			const $toolbar = this.$refs.formDefinitionJsonToolbar;
			$table.connect($toolbar);
		});
	},
	activated() {
		this.refreshList();
	},
	watch: {
		"$route.query.id": {
			handler(val) {
				this.searchForm.formDefinitionId = val;
			},

			immediate: true,
			deep: false,
		},
	},
	methods: {
		// 获取数据列表
		refreshList() {
			this.loading = true;
			formDefinitionJsonService
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
		// 查看
		view(id) {
			this.$refs.formDefinitionJsonForm.init(id);
		},
		// 删除
		del(id) {
			let ids =
				id ||
				this.$refs.formDefinitionJsonTable
					.getCheckboxRecords()
					.map((item) => {
						return item.id;
					})
					.join(",");
			this.$confirm(
				`确定删除该版本吗? 删除之后，已发起的流程如果使用了该版本，将无法查看表单内容!`,
				"警告",
				{
					confirmButtonText: "确定",
					cancelButtonText: "取消",
					type: "warning",
				}
			).then(() => {
				this.loading = true;
				formDefinitionJsonService.delete(ids).then((data) => {
					this.loading = false;
					this.$message.success(data);
					this.refreshList();
				});
			});
		},
		// 设置为主版本
		updatePrimary(id) {
			this.$confirm(`确定设置该版本为主版本吗?`, "提示", {
				confirmButtonText: "确定",
				cancelButtonText: "取消",
				type: "warning",
			}).then(() => {
				this.loading = true;
				formDefinitionJsonService.updatePrimary(id).then((data) => {
					this.loading = false;
					this.$message.success(data);
					this.refreshList();
				});
			});
		},
		goBack() {
			useTabs.close();
			this.$router.push({ path: `/flowable/form/FormDefinitionList` });
		},
	},
};
</script>
<style lang="less" scoped>
.jp-table {
	height: calc(100% - 65px);
	padding: 10px 10px 0 10px;
	background: #fff;
	.jp-table-body {
		height: calc(100% - 90px);
	}
}
.page-header {
	padding: 13px 15px;
	font-size: 12px;
	margin-top: -10px;
	margin-left: -10px;
	margin-right: -10px;
	margin-bottom: 10px;
	border-bottom: 1px solid #eaeefb;
	overflow: hidden;
	background: #fff;
}
</style>
