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
						:data="officeTreeData"
						:props="{
							value: 'id', // ID字段名
							label: 'name', // 显示名称
							children: 'children', // 子级字段名
						}"
						node-key="id"
						default-expand-all
						:filter-node-method="filterNode"
						:expand-on-click-node="false"
						highlight-current
						@node-click="handleNodeClick"
						ref="officeTree"
					>
						<template #default="{ node, data }">
							<span class="custom-tree-node el-tree-node__label">
								<span class="label">
									<el-icon class="m-r-5">
										<qiye v-if="data.type === '1'"></qiye>
										<bumen v-else></bumen>
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
							<el-form-item prop="loginName" label="登录名：">
								<el-input
									v-model="searchForm.loginName"
									placeholder="请输入登录名"
									clearable
								></el-input>
							</el-form-item>
							<el-form-item prop="name" label="姓名：">
								<el-input
									v-model="searchForm.name"
									placeholder="请输入姓名"
									clearable
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
						>
							查询</el-button
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
							ref="userToolbar"
							:refresh="{ query: refreshList }"
							import
							export
							print
							resizable
							custom
						>
							<template #buttons>
								<el-button
									v-if="hasPermission('sys:user:add')"
									type="primary"
									icon="plus"
									@click="add()"
									>新建</el-button
								>
								<el-button
									v-if="hasPermission('sys:user:edit')"
									type="warning"
									icon="edit-filled"
									@click="edit()"
									v-show="
										$refs.userTable &&
										$refs.userTable.getCheckboxRecords()
											.length === 1
									"
									>修改</el-button
								>
								<el-button
									v-if="hasPermission('sys:user:del')"
									type="danger"
									icon="del-filled"
									@click="del()"
									v-show="
										$refs.userTable &&
										$refs.userTable.getCheckboxRecords()
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
								<vxe-button
									type="text"
									title="下载导入模板"
									v-if="hasPermission('sys:user:import')"
									icon="vxe-icon-copy"
									class="tool-btn m-l-0"
									@click="downloadTpl()"
								>
								</vxe-button>
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
								ref="userTable"
								show-header-overflow
								show-overflow
								highlight-hover-row
								:print-config="{}"
								:import-config="{
									importMethod: importMethod,
									types: ['csv', 'xls', 'xlsx'],
									remote: true,
								}"
								:export-config="{
									remote: true,
									filename: `用户数据${moment(
										new Date()
									).format('YYYY-MM-DD')}`,
									sheetName: '用户数据',
									exportMethod: exportMethod,
									types: ['xlsx'],
									modes: ['current', 'selected', 'all'],
								}"
								@sort-change="sortChangeHandle"
								:sort-config="{ remote: true }"
								:data="dataList"
							>
								<vxe-column
									type="checkbox"
									width="40px"
								></vxe-column>
								<vxe-column title="头像" field="photo">
									<template #default="{ row }">
										<img
											:src="
												row.photo === ''
													? '/static/img/avatar.png'
													: row.photo
											"
											style="height: 35px"
										/>
									</template>
								</vxe-column>
								<vxe-column
									title="登录名"
									field="loginName"
									sortable
								>
									<template #default="{ row }">
										<el-link
											v-bind:class="{
												'disabled-user':
													row.loginFlag === '0',
											}"
											type="primary"
											:underline="false"
											v-if="
												hasPermission('sys:user:edit')
											"
											@click="edit(row.id)"
											>{{ row.loginName }}</el-link
										>
										<el-link
											v-bind:class="{
												'disabled-user':
													row.loginFlag === '0',
											}"
											type="primary"
											:underline="false"
											v-else-if="
												hasPermission('sys:user:view')
											"
											@click="view(row.id)"
											>{{ row.loginName }}</el-link
										>
										<span
											v-bind:class="{
												'disabled-user':
													row.loginFlag === '0',
											}"
											v-else
											>{{ row.loginName }}</span
										>
									</template>
								</vxe-column>

								<vxe-column title="姓名" field="name" sortable>
								</vxe-column>
								<vxe-column
									title="公司"
									sort-by="c.name"
									field="companyDTO.name"
									sortable
								>
									<template #default="{ row }">
										{{
											row.companyDTO &&
											row.companyDTO.name
										}}
									</template>
								</vxe-column>
								<vxe-column
									title="部门"
									sort-by="o.name"
									field="officeDTO.name"
									sortable
								>
									<template #default="{ row }">
										{{
											row.officeDTO && row.officeDTO.name
										}}
									</template>
								</vxe-column>
								<vxe-column
									title="状态"
									field="loginFlag"
									sortable
								>
									<template #default="{ row }">
										<el-tag
											v-if="row.loginFlag === '1'"
											type="success"
											>正常</el-tag
										>
										<el-tag
											v-else-if="row.loginFlag === '0'"
											type="danger"
											>禁用</el-tag
										>
									</template>
								</vxe-column>
								<vxe-column
									title="操作"
									width="200px"
									fixed="right"
									align="center"
								>
									<template #default="{ row }">
										<el-button
											v-if="
												hasPermission('sys:user:view')
											"
											type="primary"
											text
											icon="view-filled"
											@click="view(row.id)"
											>查看</el-button
										>
										<el-button
											v-if="
												hasPermission('sys:user:edit')
											"
											type="primary"
											text
											icon="edit-filled"
											@click="edit(row.id)"
											>修改</el-button
										>
										<el-button
											v-if="hasPermission('sys:user:del')"
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
				</el-main>
			</el-container>
		</pane>
	</splitpanes>
	<user-form ref="userForm" @refreshDataList="refreshList"></user-form>
</template>

<script>
import { Splitpanes, Pane } from "splitpanes";
import "splitpanes/dist/splitpanes.css";
import UserForm from "./UserForm";
import userService from "@/api/sys/userService";
import officeService from "@/api/sys/officeService";
export default {
	data() {
		return {
			searchForm: {
				loginName: "",
				name: "",
				companyDTO: {
					id: "",
				},
				officeDTO: {
					id: "",
				},
			},
			searchVisible: true,
			filterText: "",
			dataList: [],
			officeTreeData: [],
			tablePage: {
				total: 0,
				currentPage: 1,
				pageSize: 10,
				orders: [{ column: "a.create_time", asc: false }],
			},
			loading: false,
		};
	},
	components: { Splitpanes, Pane, UserForm },
	mounted() {
		this.$nextTick(() => {
			// 将表格和工具栏进行关联
			const $table = this.$refs.userTable;
			const $toolbar = this.$refs.userToolbar;
			$table.connect($toolbar);
		});
		this.refreshTree();
		this.refreshList();
	},
	watch: {
		filterText(val) {
			this.$refs.officeTree.filter(val);
		},
	},
	methods: {
		filterNode(value, data) {
			if (!value) return true;
			return data.name.indexOf(value) !== -1;
		},
		// 获取数据列表
		refreshList() {
			this.loading = true;
			userService
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
		refreshTree() {
			officeService.treeData().then((data) => {
				this.officeTreeData = data;
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
				this.tablePage.orders = [
					{
						column:
							obj.column.sortBy ||
							this.$utils.toLine(obj.property),
						asc: obj.order === "asc",
					},
				];
			} else {
				this.tablePage.orders = [
					{ column: "a.create_time", asc: false },
				];
			}
			this.refreshList();
		},
		// 新增
		add() {
			this.$refs.userForm.init("add", "");
		},
		// 修改
		edit(id) {
			id =
				id ||
				this.$refs.userTable.getCheckboxRecords().map((item) => {
					return item.id;
				})[0];
			this.$refs.userForm.init("edit", id);
		},
		// 查看
		view(id) {
			this.$refs.userForm.init("view", id);
		},
		// 删除
		del(id) {
			let ids =
				id ||
				this.$refs.userTable
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
				userService
					.delete(ids)
					.then((data) => {
						this.loading = false;
						this.$message.success({
							dangerouslyUseHTMLString: true,
							message: data,
						});
						this.refreshList();
					})
					.catch(() => {
						this.loading = false;
					});
			});
		},
		// 下载模板
		downloadTpl() {
			// this.$utils.downloadExcel('/sys/user/import/template')
			userService
				.exportTemplate()
				.then((data) => {
					// 将二进制流文件写入excel表，以下为重要步骤
					this.$utils.downloadExcel(data, "用户导入模板");
				})
				.catch(function (err) {
					if (err.response) {
						console.log(err.response);
					}
				});
		},
		handleNodeClick(data) {
			if (data.type === "1") {
				this.searchForm.companyDTO.id = data.id;
				this.searchForm.officeDTO.id = "";
			} else {
				this.searchForm.companyDTO.id = "";
				this.searchForm.officeDTO.id = data.id;
			}
			this.refreshList();
		},
		// 自定义服务端导入
		importMethod({ file }) {
			// 处理表单
			const formBody = new FormData();
			formBody.append("file", file);
			userService.importExcel(formBody).then((result) => {
				this.$message.success({
					dangerouslyUseHTMLString: true,
					message: result,
				});
				this.refreshList();
			});
		},
		// 自定义服务端导出
		exportMethod({ options }) {
			// 传给服务端的参数
			const params = {
				current: this.tablePage.currentPage,
				size: this.tablePage.pageSize,
				orders: this.tablePage.orders,
				...this.searchForm,
				filename: options.filename,
				sheetName: options.sheetName,
				isHeader: options.isHeader,
				original: options.original,
				mode: options.mode,
				selectIds:
					options.mode === "selected"
						? options.data.map((item) => item.id)
						: [],
				exportFields: options.columns.map(
					(column) => column.property && column.property.split(".")[0]
				),
			};
			this.loading = true;
			return userService
				.exportExcel(params)
				.then((data) => {
					// 将二进制流文件写入excel表，以下为重要步骤
					this.$utils.downloadExcel(data, options.filename);
					this.loading = false;
				})
				.catch(function (err) {
					if (err.response) {
						console.log(err.response);
					}
				});
		},
		resetSearch() {
			this.searchForm.companyDTO.id = "";
			this.searchForm.officeDTO.id = "";
			this.filterText = "";
			this.$refs.officeTree.setCurrentKey(null);
			this.$refs.searchForm.resetFields();
			this.refreshList();
		},
	},
};
</script>
<style scoped>
.disabled-user {
	text-decoration: line-through;
	color: #999;
}
</style>
