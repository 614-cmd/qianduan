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
						:data="docCategoryTreeData"
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
						ref="docCategoryTree"
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
				<el-footer class="tree-footer">
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
							class="query-form"
							ref="searchForm"
							:model="searchForm"
							@keyup.enter="refreshList()"
							@submit.prevent
						>
							<el-form-item
								label="类型："
								prop="docCategoryDTO.id"
							>
								<el-tree-select
									ref="category"
									value-key="id"
									:props="{
										label: 'name', // 显示名称
										children: 'children', // 子级字段名
									}"
									:data="docCategoryTreeData"
									v-model="searchForm.docCategoryDTO.id"
									check-strictly
									default-expand-all
									clearable
									:accordion="true"
								/>
							</el-form-item>
						</el-form>
					</div>
					<div class="right-panel">
						<el-button
							type="primary"
							class="round"
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
							ref="docTemplateToolbar"
							:refresh="{ query: refreshList }"
							export
							print
							custom
						>
							<template #buttons>
								<el-dropdown
									trigger="click"
									@command="createFile"
									class="m-r-10"
								>
									<el-button
										type="primary"
										icon="plus"
										class="e-b"
										>新建</el-button
									>
									<template #dropdown>
										<el-dropdown-menu>
											<el-dropdown-item command="w"
												><el-icon size="16px">
													<word /></el-icon
												><span
													style="text-align: center"
													>文字文档</span
												></el-dropdown-item
											>
											<el-dropdown-item command="s"
												><el-icon size="16px">
													<excel /></el-icon
												><span
													style="text-align: center"
													>表格文档</span
												></el-dropdown-item
											>
											<el-dropdown-item command="p"
												><el-icon size="16px">
													<ppt /></el-icon
												><span
													style="text-align: center"
													>演示文档</span
												></el-dropdown-item
											>
										</el-dropdown-menu>
									</template>
								</el-dropdown>
								<el-button
									v-if="hasPermission('wps:docTemplate:add')"
									type="default"
									icon="uploadFilled"
									@click="add()"
									>上传</el-button
								>
								<el-button
									v-if="hasPermission('wps:docTemplate:edit')"
									type="warning"
									icon="edit-filled"
									@click="edit()"
									v-show="
										$refs.docTemplateTable &&
										$refs.docTemplateTable.getCheckboxRecords()
											.length === 1
									"
									>修改</el-button
								>
								<el-button
									v-if="hasPermission('wps:docTemplate:del')"
									type="danger"
									icon="del-filled"
									@click="del()"
									v-show="
										$refs.docTemplateTable &&
										$refs.docTemplateTable.getCheckboxRecords()
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
								ref="docTemplateTable"
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
									title="名称"
									field="name"
									sort-by="b.name"
									sortable
								>
									<template #default="{ row }">
										<el-link
											type="primary"
											:underline="false"
											v-if="
												hasPermission(
													'wps:docTemplate:edit'
												)
											"
											@click="edit(row.id)"
										>
											{{ row.name }}
										</el-link>
										<el-link
											type="primary"
											:underline="false"
											v-else-if="
												hasPermission(
													'wps:docTemplate:view'
												)
											"
											@click="view(row.id)"
										>
											{{ row.name }}
										</el-link>
										<span v-else>{{ row.name }}</span>
									</template>
								</vxe-column>

								<vxe-column title="文件" field="path" sortable>
									<template #default="{ row }">
										<el-link
											type="primary"
											:underline="false"
											v-if="
												row.path &&
												hasPermission(
													'wps:docTemplate:edit'
												)
											"
											@click="design(row)"
										>
											{{
												decodeURIComponent(
													row.path.substring(
														row.path.lastIndexOf(
															"&name="
														) + 6
													)
												)
											}}
										</el-link>
									</template>
								</vxe-column>
								<vxe-column
									title="类型"
									field="docCategoryDTO.name"
									sortable
								></vxe-column>
								<vxe-column
									title="备注信息"
									field="remarks"
									sortable
								></vxe-column>
								<vxe-column
									title="操作"
									width="300px"
									fixed="right"
									align="center"
								>
									<template #default="{ row }">
										<el-button
											v-if="
												hasPermission(
													'wps:docTemplate:view'
												)
											"
											text
											type="primary"
											size="small"
											icon="download"
											@click="download(row.path)"
										>
											下载
										</el-button>
										<el-button
											v-if="
												hasPermission(
													'wps:docTemplate:edit'
												)
											"
											text
											type="primary"
											size="small"
											icon="edit-filled"
											@click="edit(row.id)"
										>
											修改
										</el-button>
										<el-button
											v-if="
												hasPermission(
													'wps:docTemplate:del'
												)
											"
											text
											type="danger"
											size="small"
											icon="del-filled"
											@click="del(row.id)"
										>
											删除
										</el-button>
										<el-button
											v-if="
												hasPermission(
													'wps:docTemplate:edit'
												)
											"
											text
											type="primary"
											size="small"
											icon="edit-filled"
											@click="design(row)"
										>
											编辑内容
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
						</div>
					</div>
				</el-main>
			</el-container>
		</pane>
	</splitpanes>
	<!-- 弹窗, 新增 / 修改 -->
	<!-- 弹窗, 新增 / 修改 -->
	<DocTemplateForm
		ref="docTemplateForm"
		@refreshDataList="refreshList"
	></DocTemplateForm>
	<DocCategoryForm
		ref="docCategoryForm"
		@refreshTree="refreshTree"
	></DocCategoryForm>
	<el-dialog
		title="编辑文档"
		:close-on-click-modal="true"
		fullscreen
		class="word"
		style="padding: 0px"
		v-model="visible"
		draggable
	>
		<iframe
			v-if="visible"
			:src="wpsUrl"
			frameborder="0"
			scrolling="auto"
			height="1000px"
			width="100%"
		></iframe>
	</el-dialog>
</template>

<script>
import { Splitpanes, Pane } from "splitpanes";
import "splitpanes/dist/splitpanes.css";
import DocTemplateForm from "./DocTemplateForm.vue";
import DocCategoryForm from "./DocCategoryForm.vue";
import docCategoryService from "@/api/wps/docCategoryService";
import docTemplateService from "@/api/wps/docTemplateService";
export default {
	data() {
		return {
			searchForm: {
				docCategoryDTO: {
					id: "",
				},
			},
			searchVisible: true,
			filterText: "",
			visible: false,
			docCategoryTreeData: [],
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
		DocTemplateForm,
		DocCategoryForm,
		Splitpanes,
		Pane,
	},
	mounted() {
		this.$nextTick(() => {
			// 将表格和工具栏进行关联
			const $table = this.$refs.docTemplateTable;
			const $toolbar = this.$refs.docTemplateToolbar;
			$table.connect($toolbar);
		});
		this.refreshTree();
		this.refreshList();
	},
	watch: {
		filterText(val) {
			this.$refs.docCategoryTree.filter(val);
		},
	},
	methods: {
		filterNode(value, data) {
			if (!value) return true;
			return data.name.indexOf(value) !== -1;
		},
		refreshTree() {
			docCategoryService.treeData().then((data) => {
				this.docCategoryTreeData = data;
			});
		},
		handleNodeClick(data) {
			this.searchForm.docCategoryDTO.id = data.id;
			this.refreshList();
		},
		design(row) {
			this.wpsUrl = `${
				import.meta.env.VITE_APP_BASE
			}/wps-server/weboffice/index?filename=${
				row.path
			}&fileid=${new Date().getTime()}`;
			this.visible = true;
		},
		createFile(type) {
			docTemplateService.createFile(type).then((data) => {
				window.open(data, "_blank");
			});
		},
		// 下载文件
		download(path) {
			window.open(path, "_blank");
		},
		// 新增
		addTreeNode(data) {
			var sort = 30;
			if (data) {
				if (data.children && data.children.length > 0) {
					sort = data.children[data.children.length - 1].sort + 30;
				}
			} else if (
				this.docCategoryTreeData &&
				this.docCategoryTreeData.length > 0
			) {
				sort =
					this.docCategoryTreeData[
						this.docCategoryTreeData.length - 1
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
			this.$refs.docCategoryForm.init("add", newAreaData);
		},
		addChildTreeNode(node) {
			this.$refs.formCategoryForm.init("addChild", {
				id: "",
				parent: { id: node.id, name: node.name },
			});
		},
		// 修改
		editTreeNode(id) {
			this.$refs.docCategoryForm.init("edit", {
				id: id,
				parent: { id: "", name: "" },
			});
		},
		delTreeNode(ids) {
			if (!ids) {
				var CheckedNodes = this.$refs.docCategoryTree.getCheckedNodes();
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
				docCategoryService.delete(ids).then((data) => {
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
			docCategoryService
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
			docTemplateService
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
			this.$refs.docTemplateForm.init("add", "");
		},
		// 修改
		edit(id) {
			id =
				id ||
				this.$refs.docTemplateTable.getCheckboxRecords().map((item) => {
					return item.id;
				})[0];
			this.$refs.docTemplateForm.init("edit", id);
		},
		// 查看
		view(id) {
			this.$refs.docTemplateForm.init("view", id);
		},
		// 删除
		del(id) {
			let ids =
				id ||
				this.$refs.docTemplateTable
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
				docTemplateService.delete(ids).then((data) => {
					this.loading = false;
					this.$message.success(data);
					this.refreshList();
				});
			});
		},
		resetSearch() {
			this.$refs.searchForm.resetFields();
			this.filterText = "";
			this.$refs.docCategoryTree.setCurrentKey(null);
			this.refreshList();
		},
	},
};
</script>
