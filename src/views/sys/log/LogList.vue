<template>
	<div class="page">
		<el-container class="jp-container">
			<el-aside width="200px">
				<el-container class="jp-container">
					<el-main class="nopadding">
						<el-menu @select="changeLog">
							<el-menu-item index="1">
								<template #title>
									<el-icon class="no-inherit"
										><info-filled color="#409eff"
									/></el-icon>
									<span>登陆日志</span>
								</template>
							</el-menu-item>
							<el-menu-item index="2">
								<template #title>
									<el-icon class="no-inherit"
										><question-filled color="#e6a23d"
									/></el-icon>
									<span>访问日志</span>
								</template>
							</el-menu-item>
							<el-menu-item index="3">
								<template #title>
									<el-icon class="no-inherit"
										><circle-close-filled color="#f56c6d"
									/></el-icon>
									<span>异常日志</span>
								</template>
							</el-menu-item>
						</el-menu>
					</el-main>
				</el-container>
			</el-aside>
			<el-container class="jp-container is-vertical">
				<el-header>
					<div class="left-panel">
						<el-form
							:inline="true"
							class="query-form"
							ref="searchForm"
							:model="searchForm"
							@keyup.enter="refreshList()"
							@submit.prevent
						>
							<el-form-item prop="searchDates" label="日期：">
								<el-date-picker
									v-model="searchDates"
									type="datetimerange"
									align="right"
									value-format="YYYY-MM-DD HH:mm:ss"
									unlink-panels
									range-separator="至"
									start-placeholder="开始日期"
									end-placeholder="结束日期"
								>
								</el-date-picker>
							</el-form-item>
							<el-form-item prop="title" label="操作菜单：">
								<el-input
									v-model="searchForm.title"
									placeholder="请输入操作菜单"
									clearable
								></el-input>
							</el-form-item>
							<el-form-item
								prop="createBy.name"
								label="操作用户："
							>
								<el-input
									v-model="searchForm.createBy.name"
									placeholder="请输入操作用户"
									clearable
								></el-input>
							</el-form-item>
							<el-form-item prop="requestUri" label="URI：">
								<el-input
									v-model="searchForm.requestUri"
									placeholder="请输入URI"
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
							ref="logToolbar"
							:refresh="{ query: refreshList }"
							export
							print
							custom
						>
							<template #buttons>
								<el-button
									v-if="hasPermission('sys:log:del')"
									type="danger"
									icon="del-filled"
									@click="del()"
									v-show="
										$refs.logTable &&
										$refs.logTable.getCheckboxRecords()
											.length > 0
									"
									>删除</el-button
								>
								<el-button
									v-if="hasPermission('sys:log:del')"
									type="danger"
									icon="del-filled"
									@click="empty()"
									>清空</el-button
								>
							</template>
						</vxe-toolbar>
						<div class="jp-table-body">
							<vxe-table
								border="inner"
								auto-resize
								resizable
								height="auto"
								:loading="loading"
								size="mini"
								ref="logTable"
								show-header-overflow
								show-overflow
								highlight-hover-row
								@cell-click="showDetail"
								:menu-config="{}"
								:print-config="{}"
								:import-config="{}"
								:row-config="{ isCurrent: true, isHover: true }"
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
								<vxe-column title="访问功能" sortable>
									<template #default="{ row }">
										<el-icon
											v-if="row.type == '1'"
											class="no-inherit"
											><info-filled color="#409eff"
										/></el-icon>
										<el-icon
											v-if="row.type == '2'"
											class="no-inherit"
											><question-filled color="#e6a23d"
										/></el-icon>
										<el-icon
											v-if="row.type == '3'"
											class="no-inherit"
											><circle-close-filled
												color="#f56c6d"
										/></el-icon>
										{{ row.title }}
									</template>
								</vxe-column>
								<vxe-column
									title="URI"
									field="requestUri"
									sortable
								></vxe-column>
								<vxe-column
									title="提交方式"
									field="requestType"
									sortable
								></vxe-column>
								<vxe-column
									title="耗时(毫秒)"
									field="recordTime"
									sortable
								></vxe-column>
								<vxe-column
									title="请求参数"
									field="params"
									sortable
								></vxe-column>
								<vxe-column
									title="响应结果"
									field="result"
									sortable
								></vxe-column>
								<vxe-column
									title="公司"
									field="createBy.companyDTO.name"
									sort-by="c.name"
									sortable
								></vxe-column>
								<vxe-column
									title="部门"
									field="createBy.officeDTO.name"
									sort-by="o.name"
									sortable
								></vxe-column>
								<vxe-column
									fixed="right"
									title="用户"
									field="createBy.name"
									sort-by="u.name"
									sortable
								></vxe-column>
								<vxe-column
									fixed="right"
									title="IP地址"
									field="remoteAddr"
									sortable
								></vxe-column>
								<vxe-column
									width="150px"
									fixed="right"
									title="访问时间"
									field="createTime"
									sort-by="a.create_time"
									sortable
								></vxe-column>
							</vxe-table>
							<vxe-pager
								background
								size="mini"
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
		</el-container>
	</div>
	<el-drawer
		v-model="infoDrawer"
		title="日志详情"
		:size="600"
		destroy-on-close
	>
		<info ref="info"></info>
	</el-drawer>
</template>

<script>
import logService from "@/api/sys/logService";
import info from "./info";
export default {
	data() {
		return {
			infoDrawer: false,
			searchForm: {
				type: "1",
				title: "",
				createBy: {
					name: "",
				},
				requestUri: "",
				beginCreateDate: "",
				endCreateDate: "",
			},
			searchDates: [],
			dataList: [],
			tablePage: {
				total: 0,
				currentPage: 1,
				pageSize: 10,
				orders: [{ column: "a.create_time", asc: false }],
			},
			loading: false,
		};
	},
	components: {
		info,
	},
	mounted() {
		this.$nextTick(() => {
			// 将表格和工具栏进行关联
			const $table = this.$refs.logTable;
			const $toolbar = this.$refs.logToolbar;
			$table.connect($toolbar);
		});
		this.refreshList();
	},
	watch: {
		searchDates() {
			if (this.searchDates) {
				this.searchForm.beginCreateDate = this.searchDates[0];
				this.searchForm.endCreateDate = this.searchDates[1];
			} else {
				this.searchForm.beginCreateDate = "";
				this.searchForm.endCreateDate = "";
			}
		},
	},
	methods: {
		// 获取数据列表
		refreshList() {
			this.loading = true;
			logService
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
		showDetail({ row }) {
			this.infoDrawer = true;
			this.$nextTick(() => {
				this.$refs.info.setData(row);
			});
		},
		changeLog(index) {
			this.searchForm.type = index;
			this.refreshList();
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
		// 删除
		del() {
			let ids = this.$refs.logTable
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
				logService.delete(ids).then((data) => {
					this.$message.success({
						dangerouslyUseHTMLString: true,
						message: data,
					});
					this.refreshList();
				});
			});
		},
		empty() {
			this.$confirm(`确定清空日志吗?`, "提示", {
				confirmButtonText: "确定",
				cancelButtonText: "取消",
				type: "warning",
			}).then(() => {
				logService.empty().then((data) => {
					this.$message.success({
						dangerouslyUseHTMLString: true,
						message: data,
					});
					this.refreshList();
				});
			});
		},
		resetSearch() {
			this.searchDates = [];
			this.$refs.searchForm.resetFields();
			this.$nextTick(() => {
				this.refreshList();
			});
		},
	},
};
</script>
