<template>
	<splitpanes class="default-theme">
		<pane class="jp-container m-r-0" :size="leftPane">
			<div class="page2">
				<div class="jp-table">
					<div class="vxe-toolbar">
						<div class="vxe-buttons--wrapper">
							<el-button
								v-if="hasPermission('sys:role:add')"
								type="primary"
								icon="plus"
								@click="add()"
								>新建</el-button
							>
						</div>
						<div class="vxe-tools--wrapper"></div>
						<div class="vxe-tools--operate">
							<el-input
								clearable
								v-model="searchForm.name"
								placeholder="请输入角色名"
							>
								<template #append>
									<el-button
										@click="refreshList()"
										icon="search"
									/>
								</template>
							</el-input>
						</div>
					</div>
					<div class="jp-table-body">
						<vxe-table
							border="inner"
							auto-resize
							:row-config="{ isCurrent: true, isHover: true }"
							@current-change="showDetail"
							resizable
							height="auto"
							:loading="loading"
							size="small"
							ref="roleTable"
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
							<vxe-column title="角色名称" field="name" sortable>
								<template #default="{ row }">
									<el-link
										v-bind:class="{
											'disabled-role':
												row.useable === '0',
										}"
										type="primary"
										:underline="false"
										>{{ row.name }}</el-link
									>
								</template>
							</vxe-column>
							<vxe-column
								title="英文名称"
								field="enName"
								sortable
							></vxe-column>
							<vxe-column
								title="备注"
								field="remarks"
								sortable
							></vxe-column>
							<vxe-column
								title="是否可用"
								field="useable"
								sortable
							>
								<template #default="{ row }">
									<el-tag
										:type="
											row.useable == '0'
												? 'danger'
												: 'success'
										"
									>
										{{
											$dictUtils.getDictLabel(
												"yes_no",
												row.useable,
												"否"
											)
										}}
									</el-tag>
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
			</div>
		</pane>
		<pane class="m-l-0 jp-container" :size="rightPane">
			<el-card shadow="never" class="page2" style="border: none">
				<template #header>
					<div class="card-header">
						<span>
							<el-avatar
								:size="30"
								v-if="
									roleName &&
									$TOOL.data.get('IS_PRIMARY_TENANT')
								"
								:style="{
									'margin-right': '10px',
									background:
										$utils.getTenantColorById(roleTenantId),
								}"
							>
								{{ roleName.substring(0, 1) }} </el-avatar
							>{{ roleName }}</span
						>
						<el-button icon="back" @click="closeDetail" text
							>关闭</el-button
						>
					</div>
					<el-divider style="margin-top: 5px; margin-bottom: 0px" />
				</template>

				<el-tabs v-model="cardIndex">
					<el-tab-pane label="基本信息" name="first">
						<!-- 弹窗, 新增 / 修改 -->
						<role-form
							ref="roleForm"
							@closeDetail="closeDetail"
							@delRoleById="del"
							@getRoleTenantId="getRoleTenantId"
							@getRoleName="getRoleName"
							@getRoleId="getRoleId"
							@refreshDataList="refreshList"
						></role-form>
					</el-tab-pane>
					<el-tab-pane v-if="roleId" label="授权" name="second">
						<auth-form :role-id="roleId" ref="authForm"></auth-form>
					</el-tab-pane>
					<el-tab-pane v-if="roleId" label="用户" name="third">
						<role-user-list
							:role-id="roleId"
							ref="roleUserList"
						></role-user-list>
					</el-tab-pane>
				</el-tabs>
			</el-card>
		</pane>
	</splitpanes>
</template>
<script>
import { Splitpanes, Pane } from "splitpanes";
import "splitpanes/dist/splitpanes.css";
import RoleForm from "./RoleForm";
import AuthForm from "./AuthForm";
import RoleUserList from "./RoleUserList";
import roleService from "@/api/sys/roleService";
export default {
	data() {
		return {
			searchForm: {
				name: "",
			},
			cardIndex: "first",
			dataList: [],
			leftPane: 100,
			rightPane: 0,
			tablePage: {
				total: 0,
				currentPage: 1,
				pageSize: 10,
				orders: [{ column: "create_time", asc: false }],
			},
			roleId: "",
			roleName: "",
			roleTenantId: "",
			loading: false,
		};
	},
	components: {
		RoleForm,
		AuthForm,
		RoleUserList,
		Splitpanes,
		Pane,
	},
	activated() {
		this.refreshList();
	},
	methods: {
		// 获取数据列表
		refreshList() {
			this.loading = true;
			roleService
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
		// 删除
		del(id) {
			let ids =
				id ||
				this.$refs.roleTable
					.getCheckboxRecords()
					.map((item) => {
						return item.id;
					})
					.join(",");
			this.$confirm(`确定删除所选项吗?`, "提示", {
				confirmButtonText: "确定",
				cancelButtonText: "取消",
				type: "warning",
			})
				.then(() => {
					this.loading = true;
					roleService.delete(ids).then((data) => {
						this.loading = false;
						this.$message.success({
							dangerouslyUseHTMLString: true,
							message: data,
						});
						if (
							("," + ids + ",").indexOf(
								"," + this.roleId + ","
							) >= 0
						) {
							this.closeDetail();
						}
						this.refreshList();
					});
				})
				.catch(() => {
					this.loading = false;
				});
		},
		resetSearch() {
			this.$refs.searchForm.resetFields();
			this.refreshList();
		},
		getRoleName(roleName) {
			this.roleName = roleName;
		},
		getRoleId(roleId) {
			this.roleId = roleId;
		},
		getRoleTenantId(roleTenantId) {
			this.roleTenantId = roleTenantId;
		},
		showDetail({ row }) {
			this.roleId = row.id;
			this.leftPane = 50;
			this.rightPane = 50;
			this.edit(row.id);
		},
		closeDetail() {
			this.leftPane = 100;
			this.rightPane = 0;
		},
		// 新增
		add() {
			this.roleId = null;
			this.leftPane = 50;
			this.rightPane = 50;
			this.cardIndex = "first";
			this.$refs.roleForm.init("");
		},
		// 修改
		edit(id) {
			this.$refs.roleForm.init(id);
		},
	},
};
</script>
<style lang="less" scoped>
.page .jp-table {
	background-color: #fff;
	transition: 0.3s;
	border-radius: 4px;
	overflow: hidden;
	padding: 10px;
	height: 100%;
}
.page2 {
	.jp-table {
		.jp-table-body {
			height: calc(100% - 83px);
		}
	}
}

.default-theme.splitpanes--vertical > .splitpanes__splitter,
.default-theme .splitpanes--vertical > .splitpanes__splitter {
	width: 7px;
	border-left: none;
	margin-left: -1px;
}
.splitpanes.default-theme .splitpanes__pane.page {
	.top {
		border: none;
		height: 100%;
	}
	.body {
		height: calc(100% - 90px);
	}
	// border: 1px solid #EBEEF5;
	background-color: #fff !important;
	transition: 0.3s;
	border-radius: 4px;
	overflow: hidden;
	// padding: 10px;
	height: 100%;
}
.roleAvatar {
	background: var(--el-color-primary);
	margin-right: 20px;
}
.disabled-role {
	text-decoration: line-through;
	color: #999;
}
</style>
