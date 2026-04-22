<template>
	<splitpanes class="default-theme">
		<pane size="20">
			<el-container class="jp-container">
				<el-header class="m-p-13-15">
					<el-input
						placeholder="请输入关键字过滤"
						clearable
						v-model="filterText"
					>
					</el-input>
				</el-header>
				<el-main class="nopadding">
					<el-tree
						class="filter-tree"
						:data="formCategoryTreeData"
						:props="{
							label: 'name', // 显示名称
							children: 'children', // 子级字段名
						}"
						v-loading="loadingTree"
						default-expand-all
						:filter-node-method="filterNode"
						:expand-on-click-node="false"
						highlight-current
						node-key="id"
						@node-click="handleNodeClick"
						draggable
						check-strictly
						show-checkbox
						@node-drop="nodeDrop"
						ref="formCategoryTree"
					>
						<template #default="{ node, data }">
							<span class="custom-tree-node el-tree-node__label">
								<span class="label">
									{{ node.label }}
								</span>
								<span class="do">
									<el-icon
										@click.stop="addTreeNode(data)"
										:size="25"
										><plus></plus
									></el-icon>
									<el-icon
										@click.stop="editTreeNode(data.id)"
										:size="25"
										><edit></edit
									></el-icon>
									<el-icon
										@click.stop="delTreeNode(data.id)"
										color="red"
										:size="25"
										><delete></delete
									></el-icon>
								</span>
							</span>
						</template>
					</el-tree>
				</el-main>
				<el-footer style="height: 51px">
					<el-button
						type="primary"
						size="small"
						icon="plus"
						@click="addTreeNode()"
					></el-button>
					<el-button
						type="danger"
						size="small"
						icon="del-filled"
						@click="delTreeNode()"
					></el-button>
					<el-button
						type="default"
						size="small"
						icon="refresh"
						@click="refreshTree()"
					></el-button>
				</el-footer>
			</el-container>
		</pane>

		<pane size="80">
			<el-container class="jp-container">
				<el-header v-if="searchVisible">
					<div class="left-panel">
						<el-form
							:inline="true"
							class="query-form m-b-10"
							ref="searchForm"
							:model="searchForm"
							@keyup.enter="refreshList()"
							@submit.prevent
						>
							<el-form-item prop="name" label="表单名称：">
								<el-input
									v-model="searchForm.name"
									placeholder="请输入表单名称"
									clearable
								></el-input>
							</el-form-item>
						</el-form>
					</div>
					<div class="right-panel">
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
					</div>
				</el-header>
				<el-main class="nopadding page2">
					<div class="jp-table">
						<vxe-toolbar
							ref="formDefinitionToolbar"
							:refresh="{ query: refreshList }"
							export
							print
							custom
						>
							<template #buttons>
								<el-button
									v-if="
										hasPermission(
											'extension:formDefinition:add'
										)
									"
									type="primary"
									icon="plus"
									@click="add()"
									>新建</el-button
								>
								<el-button
									v-if="
										hasPermission(
											'extension:formDefinition:edit'
										)
									"
									type="warning"
									icon="edit-filled"
									@click="edit()"
									v-show="
										$refs.formDefinitionTable &&
										$refs.formDefinitionTable.getCheckboxRecords()
											.length === 1
									"
									>修改</el-button
								>
								<el-button
									v-if="
										hasPermission(
											'extension:formDefinition:del'
										)
									"
									type="danger"
									icon="del-filled"
									@click="del()"
									v-show="
										$refs.formDefinitionTable &&
										$refs.formDefinitionTable.getCheckboxRecords()
											.length > 0
									"
									>删除</el-button
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
								border="inner"
								auto-resize
								resizable
								height="auto"
								:loading="loading"
								size="small"
								ref="formDefinitionTable"
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
								<vxe-column
									title="表单名称"
									field="name"
									sortable
								>
									<template #default="scope">
										<el-link
											type="primary"
											:underline="false"
											v-if="
												hasPermission(
													'extension:formDefinition:edit'
												)
											"
											@click="edit(scope.row.id)"
											>{{ scope.row.name }}</el-link
										>
										<el-link
											type="primary"
											:underline="false"
											v-else-if="
												hasPermission(
													'extension:formDefinition:view'
												)
											"
											@click="view(scope.row.id)"
											>{{ scope.row.name }}</el-link
										>
										<span v-else>{{ scope.row.name }}</span>
									</template>
								</vxe-column>
								<vxe-column
									title="分类"
									field="category.name"
									sort-by="b.name"
									sortable
								>
								</vxe-column>
								<vxe-column
									title="关联物理表"
									field="tableName"
									sort-by="a.table_name"
									sortable
								>
								</vxe-column>
								<vxe-column
									title="版本号"
									field="formDefinitionJson.version"
									sort-by="c.version"
									sortable
								>
								</vxe-column>
								<vxe-column
									title="状态"
									field="formDefinitionJson.status"
									sort-by="c.status"
									sortable
								>
									<template #default="scope">
										<el-tag
											v-if="
												scope.row.formDefinitionJson
													.status === '1'
											"
											type="success"
											>已发布</el-tag
										>
										<el-tag v-else type="danger"
											>草稿</el-tag
										>
									</template>
								</vxe-column>
								<!-- <vxe-column
									title="是否主版本"
									field="formDefinitionJson.isPrimary"
									sort-by="c.is_primary"
									sortable
								>
									<template #default="scope">
										<el-tag
											v-if="
												scope.row.formDefinitionJson
													.isPrimary === '1'
											"
											type="success"
											>主版本</el-tag
										>
										<el-tag v-else type="danger"
											>非主版本</el-tag
										>
									</template>
								</vxe-column> -->
								<vxe-column
									title="操作"
									width="300px"
									fixed="right"
									align="center"
								>
									<template #default="scope">
										<el-button
											v-if="
												hasPermission(
													'extension:formDefinition:view'
												)
											"
											type="primary"
											text
											icon="view-filled"
											@click="
												showDesignForm(
													scope.row.id,
													scope.row.formDefinitionJson
														.id
												)
											"
											>设计</el-button
										>
										<el-button
											v-if="
												hasPermission(
													'extension:formDefinition:edit'
												)
											"
											type="primary"
											text
											icon="edit-filled"
											@click="edit(scope.row.id)"
											>修改</el-button
										>
										<el-button
											v-if="
												hasPermission(
													'extension:formDefinition:edit'
												)
											"
											type="primary"
											text
											icon="edit-filled"
											@click="manage(scope.row.id)"
											>版本管理</el-button
										>
										<el-button
											v-if="
												hasPermission(
													'extension:formDefinition:del'
												)
											"
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
				</el-main>
			</el-container>
		</pane>
	</splitpanes>
	<!-- 弹窗, 新增 / 修改 -->
	<FormDefinitionForm
		ref="formDefinitionForm"
		@showDesignForm="showDesignForm"
		@refreshDataList="refreshList"
	></FormDefinitionForm>
	<FormCategoryForm
		ref="formCategoryForm"
		@refreshTree="refreshTree"
	></FormCategoryForm>
	<DesignForm ref="designForm" @refreshDataList="refreshList"></DesignForm>
</template>

<script>
import { Splitpanes, Pane } from "splitpanes";
import "splitpanes/dist/splitpanes.css";
import FormDefinitionForm from "./FormDefinitionForm";
import FormCategoryForm from "./FormCategoryForm";
import DesignForm from "./MakeFlowableForm";
import formCategoryService from "@/api/flowable/formCategoryService";
import formDefinitionService from "@/api/flowable/formDefinitionService";
export default {
	data() {
		return {
			searchForm: {
				category: {
					id: "",
				},
				name: "",
			},
			searchVisible: true,
			filterText: "",
			formCategoryTreeData: [],
			dataList: [],
			tablePage: {
				total: 0,
				currentPage: 1,
				pageSize: 10,
				orders: [{ column: "a.create_time", asc: false }],
			},
			loading: false,
			loadingTree: false,
		};
	},
	components: {
		FormDefinitionForm,
		FormCategoryForm,
		Splitpanes,
		Pane,
		DesignForm,
	},
	mounted() {
		this.$nextTick(() => {
			// 将表格和工具栏进行关联
			const $table = this.$refs.formDefinitionTable;
			const $toolbar = this.$refs.formDefinitionToolbar;
			$table.connect($toolbar);
		});
		this.refreshTree();
		this.refreshList();
	},
	watch: {
		filterText(val) {
			this.$refs.formCategoryTree.filter(val);
		},
	},
	methods: {
		filterNode(value, data) {
			if (!value) return true;
			return data.name.indexOf(value) !== -1;
		},
		refreshTree() {
			this.loadingTree = true;
			formCategoryService.treeData().then((data) => {
				this.formCategoryTreeData = data;
				this.loadingTree = false;
			});
		},
		handleNodeClick(data) {
			this.searchForm.category.id = data.id;
			this.refreshList();
		},
		addChildTreeNode(node) {
			this.$refs.formCategoryForm.init("addChild", {
				id: "",
				parent: { id: node.id, name: node.name },
			});
		},
		// 新增
		addTreeNode(data) {
			var sort = 30;
			if (data) {
				if (data.children && data.children.length > 0) {
					sort = data.children[data.children.length - 1].sort + 30;
				}
			} else if (
				this.formCategoryTreeData &&
				this.formCategoryTreeData.length > 0
			) {
				sort =
					this.formCategoryTreeData[
						this.formCategoryTreeData.length - 1
					].sort + 30;
			}
			var newAreaData = {
				id: "",
				parent: {
					id: data ? data.id : "0",
					name: "",
				},
				sort: sort,
			};
			this.$refs.formCategoryForm.init("add", newAreaData);
		},
		// 修改
		editTreeNode(id) {
			this.$refs.formCategoryForm.init("edit", {
				id: id,
				parent: { id: "", name: "" },
			});
		},
		delTreeNode(ids) {
			if (!ids) {
				var CheckedNodes =
					this.$refs.formCategoryTree.getCheckedNodes();
				if (CheckedNodes.length == 0) {
					this.$message.warning("请选择需要删除的项");
					return false;
				}
				ids = CheckedNodes.map((item) => item.id).join(",");
			}
			this.$confirm(`确定删除所选项吗?`, "提示", {
				confirmButtonText: "确定",
				cancelButtonText: "取消",
				type: "warning",
			}).then(() => {
				this.loading = true;
				formCategoryService.delete(ids).then((data) => {
					this.$message.success(data);
					this.loading = false;
					this.refreshTree();
					this.refreshList();
				});
			});
		},
		//树拖拽
		nodeDrop(draggingNode, dropNode, dropType) {
			this.loading = true;
			formCategoryService
				.drag({
					draggingNode: draggingNode.data,
					dropNode: dropNode.data,
					dropType: dropType,
				})
				.then((data) => {
					this.$message(data);
					this.loading = false;
				});
		},
		// 获取数据列表
		refreshList() {
			this.loading = true;
			formDefinitionService
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
		sortChangeHandle(obj) {
			this.tablePage.orders = [];
			if (obj.order != null) {
				this.tablePage.orders.push({
					column:
						obj.column.sortBy || this.$utils.toLine(obj.property),
					asc: obj.order === "asc",
				});
			} else {
				this.tablePage.orders = [
					{ column: "a.create_time", asc: false },
				];
			}
			this.refreshList();
		},
		// 新增
		add() {
			this.$refs.formDefinitionForm.init("add", "");
		},
		// 新增
		showDesignForm(id, jsonId) {
			this.$refs.designForm.init(id, jsonId);
		},
		manage(id) {
			this.$router.push(`/flowable/form/FormDefinitionJsonList?id=${id}`);
		},
		// 修改
		edit(id) {
			id =
				id ||
				this.$refs.formDefinitionTable
					.getCheckboxRecords()
					.map((item) => {
						return item.id;
					})[0];
			this.$refs.formDefinitionForm.init("edit", id);
		},
		// 查看
		view(id) {
			this.$refs.formDefinitionForm.init("view", id);
		},
		// 删除
		del(id) {
			let ids =
				id ||
				this.$refs.formDefinitionTable
					.getCheckboxRecords()
					.map((item) => {
						return item.id;
					})
					.join(",");
			this.$confirm(`确定删除所选流程表单以及级联的表单版本吗?`, "提示", {
				confirmButtonText: "确定",
				cancelButtonText: "取消",
				type: "warning",
			}).then(() => {
				this.loading = true;
				formDefinitionService.delete(ids).then((data) => {
					this.loading = false;
					this.$message.success(data);
					this.refreshList();
				});
			});
		},
		resetSearch() {
			this.$refs.searchForm.resetFields();
			this.filterText = "";
			this.$refs.formCategoryTree.setCurrentKey(null);
			this.searchForm.category.id = "";
			this.refreshList();
		},
	},
};
</script>
