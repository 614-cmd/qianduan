<template>
	<splitpanes class="default-theme">
		<pane class="jp-container m-r-0" :size="leftPane">
			<div class="page2">
				<el-form
					v-if="searchVisible"
					:inline="true"
					class="query-form p-l-16"
					ref="searchForm"
					:model="searchForm"
					@keyup.enter="refreshList()"
					@submit.prevent
				>
					<el-form-item prop="type" label="类型：">
						<el-input
							v-model="searchForm.type"
							placeholder="请输入类型"
							clearable
						></el-input>
					</el-form-item>
					<el-form-item>
						<el-button
							type="primary"
							@click="refreshList()"
							icon="search"
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
				<el-divider style="margin: 0"></el-divider>
				<div class="jp-table">
					<vxe-toolbar
						ref="dictToolbar"
						:refresh="{ query: refreshList }"
						export
						print
						custom
					>
						<template #buttons>
							<el-button
								v-if="hasPermission('sys:dict:add')"
								type="primary"
								icon="plus"
								@click="add()"
								>新建</el-button
							>
							<el-button
								v-if="hasPermission('sys:dict:del')"
								type="danger"
								icon="del-filled"
								@click="del()"
								v-show="
									$refs.dictTable &&
									$refs.dictTable.getCheckboxRecords()
										.length > 0
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
							:row-config="{ isCurrent: true, isHover: true }"
							@current-change="showDetail"
							:loading="loading"
							size="small"
							ref="dictTable"
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
							<vxe-column
								type="checkbox"
								width="40px"
							></vxe-column>
							<vxe-column title="类型" field="type" sortable>
								<template #default="{ row }">
									<el-link
										type="primary"
										:underline="false"
										>{{ row.type }}</el-link
									>
								</template>
							</vxe-column>
							<vxe-column
								title="描述"
								field="remarks"
								sortable
							></vxe-column>
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
		</pane>
		<pane class="jp-container m-l-0" :size="rightPane">
			<el-card shadow="never" class="page2" style="border: none">
				<template #header>
					<div class="card-header">
						<span> {{ dictTypeTitle }}</span>
						<el-button icon="back" @click="closeDetail" text
							>关闭</el-button
						>
					</div>
					<el-divider style="margin-top: 5px; margin-bottom: 0px" />
				</template>

				<el-tabs v-model="cardIndex">
					<el-tab-pane label="基本信息" name="first">
						<!-- 弹窗, 新增 / 修改 -->
						<dict-type-form
							ref="dictTypeForm"
							@delTypeById="del"
							@getTypeTitle="getTypeTitle"
							@getDictTypeId="getDictTypeId"
							@refreshDataList="refreshList"
						></dict-type-form>
					</el-tab-pane>
					<el-tab-pane
						v-if="dictTypeId"
						label="数据字典值列表"
						name="second"
					>
						<dict-value-list
							ref="dictValueList"
							:dict-type-id="dictTypeId"
						></dict-value-list>
					</el-tab-pane>
				</el-tabs>
			</el-card>
		</pane>
	</splitpanes>
</template>

<script>
import { Splitpanes, Pane } from "splitpanes";
import "splitpanes/dist/splitpanes.css";
import DictTypeForm from "./DictTypeForm";
import DictValueList from "./DictValueList";
import dictService from "@/api/sys/dictService";
export default {
	data() {
		return {
			searchForm: {
				type: "",
			},
			searchVisible: true,
			dataList: [],
			cardIndex: "first",
			leftPane: 100,
			rightPane: 0,
			tablePage: {
				total: 0,
				currentPage: 1,
				pageSize: 10,
				orders: [{ column: "create_time", asc: false }],
			},
			dictTypeId: "",
			dictTypeTitle: "",
			loading: false,
		};
	},
	components: {
		DictTypeForm,
		DictValueList,
		Splitpanes,
		Pane,
	},
	mounted() {
		this.$nextTick(() => {
			// 将表格和工具栏进行关联
			const $table = this.$refs.dictTable;
			const $toolbar = this.$refs.dictToolbar;
			$table.connect($toolbar);
		});
		this.refreshList();
	},
	methods: {
		// 获取数据列表
		refreshList() {
			this.loading = true;
			dictService
				.list({
					current: this.tablePage.currentPage,
					size: this.tablePage.pageSize,
					orders: this.tablePage.orders,
					...this.searchForm,
				})
				.then((data) => {
					this.dataList = data.records;
					this.tablePage.total = data.total;
					this.tablePage.currentPage = data.current;
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
			if (column.order != null) {
				this.tablePage.orders = [
					{
						column: this.$utils.toLine(column.property),
						asc: column.order === "asc",
					},
				];
			} else {
				this.tablePage.orders = [{ column: "create_time", asc: false }];
			}
			this.refreshList();
		},
		showDetail({ row }) {
			this.dictTypeId = row.id;
			this.leftPane = 50;
			this.rightPane = 50;
			this.edit(row.id);
		},
		closeDetail() {
			this.leftPane = 100;
			this.rightPane = 0;
		},
		getDictTypeId(dictTypeId) {
			this.dictTypeId = dictTypeId;
		},
		getTypeTitle(typeTitle) {
			this.dictTypeTitle = typeTitle;
		},
		// 新增
		add() {
			this.dictTypeId = null;
			this.leftPane = 50;
			this.rightPane = 50;
			this.cardIndex = "first";
			this.$refs.dictTypeForm.init("");
		},

		// 修改
		edit(id) {
			this.$refs.dictTypeForm.init(id);
		},
		// 删除
		del(id) {
			let ids =
				id ||
				this.$refs.dictTable
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
				dictService.delete(ids).then((data) => {
					this.$message.success(data);
					if (
						("," + ids + ",").indexOf(
							"," + this.dictTypeId + ","
						) >= 0
					) {
						this.closeDetail();
					}
					this.refreshList();
					this.$dictUtils.refreshDictList();
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
<style lang="less" scoped>
.default-theme.splitpanes--vertical > .splitpanes__splitter,
.default-theme .splitpanes--vertical > .splitpanes__splitter {
	width: 7px;
	border-left: none;
	margin-left: -1px;
}

.page2 {
	.query-form + .el-divider + .jp-table {
		.jp-table-body {
			height: calc(100% - 151px);
		}
	}
}

.splitpanes.default-theme .splitpanes__pane.page {
	// .top{
	//   border: none;
	//   height: 100%;
	// }
	// .body {
	//     height: calc(100% - 160px);
	// }
	border: 1px solid #ebeef5;
	background-color: #fff !important;
	transition: 0.3s;
	border-radius: 4px;
	overflow: hidden;
	// padding: 10px;
	height: 100%;
}
</style>
