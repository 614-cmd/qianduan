<template>
	<splitpanes class="default-theme">
		<pane size="20">
			<el-container class="jp-container">
				<el-header class="m-p-13-15">
					<el-input
						placeholder="数据库:请输入关键字过滤"
						clearable
						v-model="filterText"
					>
					</el-input>
				</el-header>
				<el-main class="nopadding">
					<el-tree
						class="filter-tree"
						:data="databaseTreeData"
						:props="{
							value: 'id', // ID字段名
							label: 'label', // 显示名称
							children: 'children', // 子级字段名
						}"
						default-expand-all
						:filter-node-method="filterNode"
						:expand-on-click-node="false"
						node-key="id"
						highlight-current
						@node-click="handleNodeClick"
						ref="databaseTree"
					>
							<template #default="{ node, data }">
								<span class="custom-tree-node el-tree-node__label">
									<span class="label">
										<el-icon class="m-r-5">
											<gongzuotai v-if="data.type === 'host' " />
											<zichan v-else />
										</el-icon>
										{{ node.label }}
									</span>
								</span>
							</template>
					</el-tree>
				</el-main>
			</el-container>
		</pane>
		<pane size="80">
			<el-container class="jp-container">
				<el-header v-if="searchVisible">
					<div class="left-panel">
						<el-form
							:inline="true"
							class="query-form"
							ref="searchForm"
							:model="searchForm"
							@keyup.enter="refreshList()"
							@submit.prevent
						>
							<el-form-item prop="name" label="表名：">
								<el-input
									v-model="searchForm.name"
									placeholder="请输入表名"
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
					</div>
				</el-header>
				<el-main class="nopadding page2">
					<div class="jp-table">
						<vxe-toolbar
							ref="dataSourceToolbar"
							:refresh="{ query: refreshList }"
							export
							print
							custom
						>
							<template #buttons>
								<el-button
									type="primary"
									icon="plus"
									@click="add()"
									>创建表</el-button
								>
								<el-button
									type="default"
									icon="download"
									@click="exportSql()"
									>导出数据库结构</el-button
								>
							</template>
							<template #tools>
								<vxe-button
									type="text"
									:title="
										searchVisible ? '收起检索' : '展开检索'
									"
									icon="vxe-icon-search"
									class="tool-btn"
									@click="searchVisible = !searchVisible"
								></vxe-button>
							</template>
						</vxe-toolbar>
						<div class="jp-table-body">
							<vxe-table
								border
								auto-resize
								resizable
								height="auto"
								:loading="loading"
								size="small"
								ref="dataSourceTable"
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
								<vxe-column
									type="seq"
									header-align="center"
									align="center"
									width="50"
								>
								</vxe-column
								>>
								<vxe-column field="name" sortable title="表名">
									<template #default="scope">
										<el-link
											type="primary"
											:underline="false"
											@click="view(scope.row)"
											><el-icon class="m-r-5"
												><biaoge></biaoge></el-icon
											>{{ scope.row.name }}
										</el-link>
									</template>
								</vxe-column>
								<vxe-column
									field="schema"
									sortable
									title="模式"
								>
								</vxe-column>
								<vxe-column
									field="catalog"
									sortable
									title="数据库"
								>
								</vxe-column>
								<vxe-column
									field="description"
									sortable
									title="说明"
								>
								</vxe-column>
								<vxe-column
									align="center"
									width="300px"
									title="操作"
								>
									<template #default="scope">
										<el-button
											icon="edit-filled"
											text
											type="primary"
											@click="edit(scope.row)"
											>编辑表</el-button
										>
										<el-button
											icon="del-filled"
											text
											type="danger"
											@click="drop(scope.row)"
											>删除表</el-button
										>
										<el-button
											icon="view-filled"
											text
											type="primary"
											@click="view(scope.row)"
											>查看数据</el-button
										>
									</template>
								</vxe-column>
							</vxe-table>
							<el-alert
								:title="`总共${dataList.length}条记录`"
								type="success"
								:closable="false"
							/>
						</div>
					</div>
				</el-main>
			</el-container>
		</pane>
	</splitpanes>
	<v-dialog title="查看数据" v-model="viewDataVisible">
		<el-table :data="viewData.dataList">
			<el-table-column
				v-for="column in viewData.columnList"
				show-overflow-tooltip
				:key="column.name"
				:property="column.name"
				:label="column.description || column.name"
			></el-table-column>
		</el-table>
	</v-dialog>
</template>

<script>
import { Splitpanes, Pane } from "splitpanes";
import "splitpanes/dist/splitpanes.css";
import dataSourceService from "@/api/database/dataSourceService";
import dataTableService from "@/api/database/dataTableService";
export default {
	data() {
		return {
			searchForm: {
				name: "",
				dataSourceId: "",
			},
			filterText: "",
			searchVisible: true,
			viewDataVisible: false,
			dataList: [],
			databaseTreeData: [],
			loading: false,
			dataSourceId: "",
			dataSourceName: "",
			viewData: {
				columnList: [],
				dataList: [],
			},
		};
	},
	components: {
		Splitpanes,
		Pane,
	},
	watch: {
		filterText(val) {
			this.$refs.databaseTree.filter(val);
		},
	},
	mounted() {
		this.$nextTick(() => {
			// 将表格和工具栏进行关联
			const $table = this.$refs.dataSourceTable;
			const $toolbar = this.$refs.dataSourceToolbar;
			$table.connect($toolbar);
		});
		if (this.searchForm.dataSourceId !== "") {
			this.refreshList();
			this.$nextTick(() => {
				this.$refs.databaseTree.setCurrentKey(
					this.searchForm.dataSourceId
				);
			});
		}
	},
	activated() {
		this.refreshTree();
	},
	methods: {
		add() {
			this.$router.push({
				path: "/database/datatable/TableForm",
				query: {
					dataSourceId: this.searchForm.dataSourceId,
					method: "add",
					title: "创建表",
				},
			});
		},
		exportSql() {
			if (!this.searchForm.dataSourceId) {
				this.$message.warning("请选择一个数据库!");
				return;
			}
			this.$confirm(`确定导出${this.dataSourceName}库表结构吗`, "提示", {
				confirmButtonText: "确定",
				cancelButtonText: "取消",
				type: "warning",
			})
				.then(() => {
					this.loading = true;
					dataTableService
						.export(this.searchForm.dataSourceId)
						.then((data) => {
							this.$utils.downloadExcel(
								data,
								this.dataSourceName
							);
						});
				})
				.catch(() => {
					this.loading = false;
				});
		},
		// 修改
		edit(row) {
			row =
				row ||
				this.$refs.dataSetTable.getCheckboxRecords().map((item) => {
					return item;
				})[0];
			this.$router.push({
				path: `/database/datatable/TableForm`,
				query: {
					name: row.name,
					dataSourceId: this.searchForm.dataSourceId,
					method: "edit",
					title: "编辑表",
				},
			});
		},
		// 删除
		drop(row) {
			this.$confirm(`确定删除表吗?删除之后不可恢复！`, "提示", {
				confirmButtonText: "确定",
				cancelButtonText: "取消",
				type: "warning",
			})
				.then(() => {
					this.loading = true;
					dataTableService
						.drop(row.name, this.dataSourceId)
						.then((data) => {
							this.$message.success({
								dangerouslyUseHTMLString: true,
								message: data,
							});
							this.refreshList();
							this.loading = false;
						});
				})
				.catch(() => {
					this.loading = false;
				});
		},
		filterNode(value, data) {
			if (!value) return true;
			return data.label.indexOf(value) !== -1;
		},
		// 获取数据列表
		refreshList() {
			if (!this.searchForm.dataSourceId) {
				this.$message.warning("请选择一个数据库!");
				return;
			}
			this.loading = true;
			dataTableService
				.list(this.searchForm)
				.then((data) => {
					let results = data.rows.filter((row) => {
						if (row.name.indexOf(this.searchForm.name) >= 0) {
							return row;
						}
					});
					this.dataList = results;
					this.loading = false;
				})
				.catch(() => {
					this.dataList = [];
					this.loading = false;
				});
		},
		refreshTree() {
			dataSourceService.treeData().then((data) => {
				this.databaseTreeData = data;
			});
		},
		handleNodeClick(data) {
			if (data.type === "db") {
				this.searchForm.dataSourceId = data.id;
				this.dataSourceId = data.id;
				this.dataSourceName = data.label;
				this.refreshList();
			}
		},
		handleNodeClose() {
			this.searchForm.dataSourceId = "";
			this.dataSourceId = "";
			this.dataList = [];
		},
		resetSearch() {
			this.$refs.searchForm.resetFields();
			this.refreshList();
		},
		view(row) {
			this.viewDataVisible = true;
			dataTableService
				.executeSql(row.name, this.dataSourceId)
				.then((data) => {
					this.viewData.columnList = data.columns;
					this.viewData.dataList = data.list;
				});
		},
	},
};
</script>
<style lang="scss" scoped></style>
