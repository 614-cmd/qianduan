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
			<el-form-item prop="name" label="看板名称：">
				<el-input
					v-model="searchForm.name"
					placeholder="请输入看板名称"
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
			<el-form-item style="float: right">
				<el-pagination
					:current-page="tablePage.currentPage"
					:page-size="tablePage.pageSize"
					:small="small"
					:background="background"
					layout="total, prev, pager, next"
					:total="tablePage.total"
					@current-change="currentChangeHandle"
				/>
			</el-form-item>
		</el-form>

		<div class="bg-white top" style="padding: 0px">
			`
			<div
				class="el-scrollbar__wrap"
				style="padding: 10px; height: calc(100% - 70px)"
			>
				<div class="el-scrollbar__view">
					<el-row :gutter="20">
						<el-col
							v-if="hasPermission('echarts:dashboard:add')"
							:xs="24"
							:sm="12"
							:md="8"
							:lg="6"
							:xl="4"
						>
							<el-card
								shadow="hover"
								border
								:body-style="{ padding: '0px' }"
								@click="add()"
							>
								<div class="code-item">
									<div class="img">
										<el-icon><plus></plus></el-icon>
									</div>
									<div class="bottom">
										<div class="title">
											<h2>创建图表看板</h2>
											<h4>添加一个新的图表看板</h4>
										</div>
									</div>
								</div>
							</el-card>
						</el-col>
						<el-col
							:xs="24"
							:sm="12"
							:md="8"
							:lg="6"
							:xl="4"
							v-for="item in dataList"
							:key="item.id"
						>
							<el-card
								shadow="hover"
								border
								:body-style="{ padding: '0px' }"
							>
								<div class="code-item">
									<div class="img">
										<el-icon :size="80"
											><kanban></kanban
										></el-icon>
									</div>

									<h3 class="jp-card-label">
										<el-row>
											<el-col :span="16">
												<div class="left">
													{{ item.name }}
												</div>
											</el-col>
											<el-col :span="8">
												<el-link
													:underline="false"
													icon="edit-filled"
													v-if="
														hasPermission(
															'echarts:dashboard:design'
														)
													"
													class="right"
													@click="design(item.id)"
												>
													设计</el-link
												>
											</el-col>
										</el-row>
									</h3>

									<ul class="jp-card-actions">
										<li style="width: 25%">
											<el-link
												:underline="false"
												v-if="
													hasPermission(
														'echarts:dashboard:edit'
													)
												"
												@click="edit(item.id)"
												icon="edit-filled"
											>
												编辑</el-link
											>
										</li>
										<li style="width: 25%">
											<el-link
												:underline="false"
												v-if="
													hasPermission(
														'echarts:dashboard:view'
													)
												"
												@click="
													view(item.id, item.name)
												"
												icon="view-filled"
											>
												预览</el-link
											>
										</li>
										<li style="width: 25%">
											<el-link
												:underline="false"
												v-if="
													hasPermission(
														'echarts:dashboard:deploy'
													)
												"
												@click="
													createMenu(
														item.id,
														item.name
													)
												"
												icon="baobiao5"
											>
												发布</el-link
											>
										</li>
										<li style="width: 25%">
											<el-link
												:underline="false"
												v-if="
													hasPermission(
														'echarts:dashboard:del'
													)
												"
												@click="del(item.id)"
												icon="del-filled"
												type="danger"
											>
												删除</el-link
											>
										</li>
									</ul>
								</div>
							</el-card>
						</el-col>
					</el-row>
				</div>
				`
			</div>

			<!-- 弹窗, 新增 / 修改 -->
			<DashboardForm
				ref="dashboardForm"
				@refreshDataList="refreshList"
			></DashboardForm>
			<gen-menu-form ref="genMenuForm"></gen-menu-form>
		</div>
	</div>
</template>

<script>
import DashboardForm from "./DashboardForm";
import GenMenuForm from "@/components/menu/GenMenuForm";
import dashboardService from "@/api/echarts/dashboardService";
export default {
	data() {
		return {
			searchVisible: true,
			searchForm: {
				name: "",
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
		DashboardForm,
		GenMenuForm,
	},
	activated() {
		this.refreshList();
	},
	methods: {
		// 获取数据列表
		refreshList() {
			this.loading = true;
			dashboardService
				.list({
					current: this.tablePage.currentPage,
					size: this.tablePage.pageSize,
					...this.searchForm,
				})
				.then((data) => {
					this.dataList = data.records;
					this.tablePage.total = data.total;
					this.loading = false;
				});
		},
		// 当前页
		currentChangeHandle(currentPage) {
			this.tablePage.currentPage = currentPage;
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
		// 新增
		add() {
			this.$refs.dashboardForm.init("add", "");
		},
		// 修改
		edit(id) {
			this.$refs.dashboardForm.init("edit", id);
		},
		// 查看
		view(id, title) {
			this.$router.push(
				"/echarts/dashboard/Preview?id=" + id + "&title=" + title
			);
		},
		design(id) {
			this.$router.push("/echarts/dashboard/Design?id=" + id);
		},
		// 创建菜单
		createMenu(id, title) {
			this.$refs.genMenuForm.init(
				`/echarts/dashboard/Preview?id=${id}`,
				title
			);
		},
		// 删除
		del(id) {
			this.$confirm(`确定删除所选项吗?`, "提示", {
				confirmButtonText: "确定",
				cancelButtonText: "取消",
				type: "warning",
			}).then(() => {
				this.loading = true;
				dashboardService.delete(id).then((data) => {
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

<style lang="less" scoped>
.el-card {
	margin-bottom: 15px;
}
.code-item {
	cursor: pointer;
}
.code-item .img {
	width: 100%;
	height: 150px;
	display: flex;
	align-items: center;
	justify-content: center;
}
.code-item .img:hover {
	background-image: -webkit-linear-gradient(
		top left,
		#fff,
		var(--el-color-primary) 200px
	);
}
.code-item .img i {
	font-size: 100px;
	color: var(--el-color-primary);
	background-image: -webkit-linear-gradient(top left, #fff, #09f 100px);
	-webkit-background-clip: text;
	-webkit-text-fill-color: transparent;
}
.code-item .img:hover i {
	font-size: 100px;
	color: #fff;
	background-image: -webkit-linear-gradient(top left, #fff, #09f 100px);
	-webkit-background-clip: text;
	-webkit-text-fill-color: transparent;
}
.code-item .bottom {
	border-top: 1px solid #e8e8e8;
	background: #f5f8fa;
	padding: 16.5px;
	display: flex;
	justify-content: space-between;
	align-items: center;
}
.code-item .bottom .title h2 {
	font-size: 16px;
	width: 150px;
	overflow: hidden; /*内容超出后隐藏*/
	white-space: nowrap; //让内容只显示为一行
	text-overflow: ellipsis; //内容超出后显示为省略号
}
.code-item .bottom .title h4 {
	font-size: 12px;
	color: #999;
	font-weight: normal;
	margin-top: 8px;
	width: 150px;
	overflow: hidden; /*内容超出后隐藏*/
	white-space: nowrap; //让内容只显示为一行
	text-overflow: ellipsis; //内容超出后显示为省略号
}
.code-item .bottom .title p {
	margin-top: 15px;
}
.code-item .bottom .action .el-link {
	padding-left: 20px;
	font-size: 20px;
}

.jp-card-label {
	font-size: 14px;
	padding-left: 10px;
	padding-right: 10px;
	height: 36px;
	margin: 0;
	background: #f5f8fa91;
	.left {
		line-height: 36px;
		font-weight: 400;
		overflow: hidden;
		text-overflow: ellipsis;
		color: rgba(0, 0, 0, 0.55) !important;
		white-space: nowrap;
	}
	.right {
		line-height: 38px;
		justify-content: center;
		text-decoration: none;
		float: right;
	}
}
.jp-card-actions {
	border-top: 1px solid #e8e8e8;
	background: #f5f8fa;
	zoom: 1;
	list-style: none;
	margin: 0;
	padding: 0;
}

.jp-card-actions:after,
.jp-card-actions:before {
	content: "";
	display: table;
}

.jp-card-actions:after {
	clear: both;
}

.jp-card-actions > li {
	float: left;
	text-align: center;
	margin: 12px 0;
	color: rgba(0, 0, 0, 0.45);
}

.jp-card-actions > li > span {
	display: inline-block;
	font-size: 14px;
	cursor: pointer;
	line-height: 22px;
	min-width: 32px;
	position: relative;
}

.jp-card-actions > li > span:hover {
	color: var(--el-color-primary);
	-webkit-transition: color 0.3s;
	transition: color 0.3s;
}

.jp-card-actions > li > span > .jp-icon {
	font-size: 16px;
	line-height: 22px;
	display: block;
	width: 100%;
}

.jp-card-actions > li > span a {
	color: rgba(0, 0, 0, 0.45);
	line-height: 22px;
	display: inline-block;
	width: 100%;
}

.jp-card-actions > li > span a:hover {
	color: var(--el-color-primary);
}

.jp-card-actions > li:not(:last-child) {
	border-right: 1px solid #e8e8e8;
}
</style>
