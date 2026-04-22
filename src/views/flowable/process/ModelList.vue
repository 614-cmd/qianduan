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
						:data="categoryTreeData"
						:props="{
							value: 'id', // ID字段名
							label: 'name', // 显示名称
							children: 'children', // 子级字段名
						}"
						default-expand-all
						:filter-node-method="filterNode"
						:expand-on-click-node="false"
						node-key="id"
						highlight-current
						@node-click="handleNodeClick"
						ref="categoryTree"
					>
					</el-tree>
				</el-main>
			</el-container>
		</pane>

		<pane size="80">
			<el-container class="jp-container">
				<el-header>
					<div class="left-panel">
						<el-form
							:inline="true"
							class="query-form m-b-10"
							v-if="searchVisible"
							ref="searchForm"
							:model="searchForm"
							@keyup.enter="refreshList()"
							@submit.prevent
						>
							<el-form-item label="模型名称：" prop="name">
								<el-input
									v-model="searchForm.name"
									placeholder="请输入关键词"
								></el-input>
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
							ref="modelToolbar"
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
									>新建</el-button
								>
								<el-button
									type="danger"
									icon="del-filled"
									@click="del()"
									v-show="
										$refs.modelTable &&
										$refs.modelTable.getCheckboxRecords()
											.length > 0
									"
									>删除</el-button
								>
								<el-button
									type="success"
									v-show="
										$refs.modelTable &&
										$refs.modelTable.getCheckboxRecords()
											.length === 1
									"
									@click="setCategory()"
									>设置分类</el-button
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
								ref="modelTable"
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
								<vxe-column type="seq" width="40"></vxe-column>
								<vxe-column
									type="checkbox"
									width="40px"
								></vxe-column>
								<vxe-column
									title="流程名称"
									field="name"
									sortable
								>
								</vxe-column>
								<vxe-column
									title="流程KEY"
									field="key"
									sortable
								>
								</vxe-column>
								<vxe-column
									title="分类"
									field="procDef.category"
									sortable
								>
								</vxe-column>
								<vxe-column
									title="流程版本"
									field="version"
									sortable
								>
									<template #default="scope">
										<el-tag>{{
											scope.row.procDef.version || "0"
										}}</el-tag>
									</template>
								</vxe-column>
								<vxe-column
									title="流程状态"
									field="suspended"
									sortable
								>
									<template #default="scope">
										<el-tag
											:type="
												scope.row.procDef.suspended ===
												false
													? 'success'
													: scope.row.procDef
															.suspended ===
													  undefined
													? 'primary'
													: 'danger'
											"
											>{{
												scope.row.procDef.suspended ===
												false
													? "已发布"
													: scope.row.procDef
															.suspended ===
													  undefined
													? "草稿"
													: "已挂起"
											}}</el-tag
										>
									</template>
								</vxe-column>
								<vxe-column
									title="更新时间"
									field="lastUpdated"
									sortable
								>
								</vxe-column>
								<vxe-column
									title="操作"
									width="200px"
									fixed="right"
									align="center"
								>
									<template #default="scope">
										<el-button
											type="primary"
											text
											@click="design(scope.row)"
											>设计</el-button
										>
										<el-button
											type="primary"
											text
											@click="deploy(scope.row)"
											>发布</el-button
										>
										<el-dropdown style="margin-left: 10px">
											<el-button type="primary" text>
												更多<el-icon
													class="el-icon--right"
													><arrow-down
												/></el-icon>
											</el-button>
											<template #dropdown>
												<el-dropdown-menu>
													<el-dropdown-item
														v-if="
															scope.row.procDef
																.suspended ===
															true
														"
														><el-button
															type="primary"
															text
															@click="
																active(
																	scope.row
																		.procDef
																)
															"
															>激活</el-button
														></el-dropdown-item
													>
													<el-dropdown-item
														v-if="
															scope.row.procDef
																.suspended ===
															false
														"
														><el-button
															type="primary"
															text
															@click="
																suspend(
																	scope.row
																		.procDef
																)
															"
															>挂起</el-button
														></el-dropdown-item
													>
													<el-dropdown-item
														><el-button
															type="primary"
															text
															@click="
																exportXML(
																	scope.row
																)
															"
															>导出</el-button
														></el-dropdown-item
													>
													<el-dropdown-item>
														<el-button
															type="primary"
															text
															@click="
																copy(
																	scope.row.id
																)
															"
															>复制</el-button
														>
													</el-dropdown-item>
													<el-dropdown-item>
														<el-button
															type="danger"
															text
															@click="
																del(
																	scope.row.id
																)
															"
															>删除</el-button
														>
													</el-dropdown-item>
												</el-dropdown-menu>
											</template>
										</el-dropdown>
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
						<v-dialog
							title="查看进度"
							:close-on-click-modal="true"
							v-model="visible"
							height="600px"
						>
							<iframe
								:src="processPhotoUrl"
								frameborder="0"
								scrolling="auto"
								width="100%"
								height="600px"
							></iframe>
						</v-dialog>

						<!-- 弹窗, 新增 / 修改 -->
						<model-form
							ref="modelForm"
							@create="create"
						></model-form>
						<!--模型编辑-->
						<design-form
							ref="designForm"
							@refreshList="refreshList"
						></design-form>
						<category-form
							ref="categoryForm"
							@refreshList="refreshList"
						></category-form>
					</div>
				</el-main>
			</el-container>
		</pane>
	</splitpanes>
</template>

<script>
import { Splitpanes, Pane } from "splitpanes";
import ModelForm from "./ModelForm";
import CategoryForm from "./CategoryForm";
import processService from "@/api/flowable/processService";
import modelService from "@/api/flowable/modelService";
import actCategoryService from "@/api/flowable/actCategoryService";
export default {
	data() {
		return {
			searchVisible: true,
			searchForm: {
				name: "",
			},
			filterText: "",
			selectCategoryName: "",
			categoryTreeData: [],
			dataList: [],
			tablePage: {
				total: 0,
				currentPage: 1,
				pageSize: 10,
				orders: [],
			},
			loading: false,
			visible: false,
			processPhotoUrl: "",
		};
	},
	components: {
		ModelForm,
		CategoryForm,
		Splitpanes,
		Pane,
	},
	mounted() {
		this.$nextTick(() => {
			// 将表格和工具栏进行关联
			const $table = this.$refs.modelTable;
			const $toolbar = this.$refs.modelToolbar;
			$table.connect($toolbar);
		});
		this.refreshList();
		this.refreshTree();
	},
	watch: {
		filterText(val) {
			this.$refs.categoryTree.filter(val);
		},
	},
	methods: {
		filterNode(value, data) {
			if (!value) return true;
			return data.name.indexOf(value) !== -1;
		},
		// 获取树数据
		refreshTree() {
			actCategoryService.treeData().then((data) => {
				this.categoryTreeData = data;
			});
		},
		handleNodeClick(data) {
			this.searchForm.category = data.name;
			this.selectCategoryName = "已选类型: " + data.name;
			this.refreshList();
		},
		// 获取数据列表
		refreshList() {
			this.loading = true;
			modelService
				.list({
					name: this.searchForm.name,
					modelType: 0,
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

		create(data) {
			// window.open(`${import.meta.env.SERVER_URL}/static/modler/index.html#/editor/${data.id}`)
			this.$refs.designForm.init(data.id);
		},
		design(row) {
			this.$refs.designForm.init(row.id, row.procDef.category);
		},
		design1(row) {
			this.$refs.designForm1.init(row.id);
		},
		design2(row) {
			window.open(
				`${
					import.meta.env.VITE_APP_BASE
				}/static/modler/index.html#/editor/${row.id}`
			);
		},
		exportXML(row) {
			modelService.exportBpmnXml(row.id).then((data) => {
				var blob = new Blob([data], {
					type: "application/xml;charset=utf-8",
				}); // application/vnd.openxmlformats-officedocument.spreadsheetml.sheet这里表示xlsx类型
				var downloadElement = document.createElement("a");
				var href = window.URL.createObjectURL(blob); // 创建下载的链接
				downloadElement.href = href;
				downloadElement.download = row.name + ".bpmn"; // 下载后文件名
				document.body.appendChild(downloadElement);
				downloadElement.click(); // 点击下载
				document.body.removeChild(downloadElement); // 下载完成移除元素
				window.URL.revokeObjectURL(href); // 释放掉blob对象
			});
		},
		// 部署
		deploy(row) {
			this.$confirm(`确认要发布流程吗?`, "提示", {
				confirmButtonText: "确定",
				cancelButtonText: "取消",
				type: "warning",
			}).then(() => {
				let category = row.procDef.category
					? row.procDef.category
					: "未分类";
				modelService
					.deploy({ id: row.id, category: category })
					.then((data) => {
						this.$message.success({
							dangerouslyUseHTMLString: true,
							message: data,
						});
						this.refreshList();
					});
			});
		},
		// 新增
		add() {
			// this.$refs.modelForm.init()
			this.$refs.designForm.init();
		},
		// 删除
		del(id) {
			let ids =
				id ||
				this.$refs.modelTable
					.getCheckboxRecords()
					.map((item) => {
						return item.id;
					})
					.join(",");
			this.$confirm(
				`确定删除该流程吗?删除流程会级联删除已经存在的实例与历史数据，且不可恢复，请谨慎操作!`,
				"提示",
				{
					confirmButtonText: "确定",
					cancelButtonText: "取消",
					type: "warning",
				}
			).then(() => {
				this.loading = true;
				modelService.delete(ids).then((data) => {
					this.loading = false;
					this.$message.success(data);
					this.refreshList();
				});
			});
		},
		// 复制
		copy(id) {
			this.$confirm(`确定复制该流程吗？`, "提示", {
				confirmButtonText: "确定",
				cancelButtonText: "取消",
				type: "warning",
			}).then(() => {
				this.loading = true;
				modelService.copy(id).then((data) => {
					this.loading = false;
					this.$message.success(data);
					this.refreshList();
				});
			});
		},
		suspend(row) {
			this.$confirm(`确认要挂起该流程吗?`, "提示", {
				confirmButtonText: "确定",
				cancelButtonText: "取消",
				type: "warning",
			}).then(() => {
				processService.suspend(row.id).then((data) => {
					this.$message.success({
						dangerouslyUseHTMLString: true,
						message: data,
					});
					this.refreshList();
				});
			});
		},
		active(row) {
			this.$confirm(`确定要激活码?`, "提示", {
				confirmButtonText: "确定",
				cancelButtonText: "取消",
				type: "warning",
			}).then(() => {
				processService.active(row.id).then((data) => {
					this.$message.success({
						dangerouslyUseHTMLString: true,
						message: data,
					});
					this.refreshList();
				});
			});
		},
		setCategory() {
			let row = this.$refs.modelTable.getCheckboxRecords()[0];
			this.$refs.categoryForm.init(row.key, row.procDef.category);
		},
		resetSearch() {
			this.searchForm.category = "";
			this.searchForm.category = "";
			this.filterText = "";
			this.$refs.categoryTree.setCurrentKey(null);
			this.$refs.searchForm.resetFields();
			this.$nextTick(() => {
				this.refreshList();
			});
		},
	},
};
</script>
