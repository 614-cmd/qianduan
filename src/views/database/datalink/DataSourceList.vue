<template>
	<el-main>
		<el-row :gutter="15">
			<el-col
				:xl="6"
				:lg="6"
				:md="8"
				:sm="12"
				:xs="24"
				v-for="item in dataList"
				:key="item.id"
			>
				<el-card class="task task-item" shadow="hover">
					<h2>
						<el-icon class="m-r-5"><zichan /></el-icon
						>{{ item.name }}
					</h2>
					<ul>
						<li>
							<h4>数据库类型</h4>
							<p>
								<el-tag
									size="small"
									round
									v-if="item.type === 'oracle'"
									type="danger"
									>{{
										$dictUtils.getDictLabel(
											"db_type",
											item.type,
											"-"
										)
									}}</el-tag
								>
								<el-tag
									size="small"
									round
									v-else-if="item.type === 'mssql'"
									type="warning"
									>{{
										$dictUtils.getDictLabel(
											"db_type",
											item.type,
											"-"
										)
									}}</el-tag
								>
								<el-tag
									size="small"
									round
									v-else-if="item.type === 'postgre'"
									type="success"
									>{{
										$dictUtils.getDictLabel(
											"db_type",
											item.type,
											"-"
										)
									}}</el-tag
								>
								<el-tag
									size="small"
									round
									v-else
									>{{
										$dictUtils.getDictLabel(
											"db_type",
											item.type,
											"-"
										)
									}}</el-tag
								>
							</p>
						</li>
						<li>
							<h4>链接英文名</h4>
							<span>{{ item.enName }}</span>
						</li>
						<li>
							<h4>主机地址&nbsp;&nbsp;&nbsp;</h4>
							<p>{{ item.host }}</p>
						</li>

						<li>
							<h4>主机端口&nbsp;&nbsp;&nbsp;</h4>
							<p>{{ item.port }}</p>
						</li>

						<li>
							<h4>数据库名&nbsp;&nbsp;&nbsp;</h4>
							<p>{{ item.databaseName }}</p>
						</li>
					</ul>
					<div class="bottom">
						<div class="state">
							<el-tag v-if="item.enable">启用</el-tag>
							<el-tag v-else type="info">停用</el-tag>
						</div>
						<div class="handler">
							<el-popconfirm
								v-if="!item.enable"
								title="确定启用数据库链接吗？"
								@confirm="start(item.id)"
							>
								<template #reference>
									<el-button
										type="primary"
										icon="el-icon-caret-right"
										circle
									></el-button>
								</template>
							</el-popconfirm>
							<el-popconfirm
								v-if="item.enable"
								title="确定停用数据库链接吗？"
								@confirm="stop(item.id)"
							>
								<template #reference>
									<el-button
										type="danger"
										class="btn-c"
										icon="jianqu"
										circle
									></el-button>
								</template>
							</el-popconfirm>
							<el-dropdown trigger="click">
								<el-button
									type="primary"
									class="btn-c"
									icon="el-icon-more"
									circle
								></el-button>
								<template #dropdown>
									<el-dropdown-menu>
										<el-dropdown-item @click="edit(item.id)"
											>编辑</el-dropdown-item
										>
										<el-dropdown-item
											@click="del(item.id)"
											divided
											>删除</el-dropdown-item
										>
									</el-dropdown-menu>
								</template>
							</el-dropdown>
						</div>
					</div>
				</el-card>
			</el-col>
			<el-col :xl="6" :lg="6" :md="8" :sm="12" :xs="24">
				<el-card class="task task-add" shadow="none" @click="add">
					<el-icon><el-icon-plus /></el-icon>
					<p>添加数据库</p>
				</el-card>
			</el-col>
		</el-row>
	</el-main>

	<!-- 弹窗, 新增 / 修改 -->
	<data-source-form
		ref="dataSourceForm"
		@refreshDataList="refreshList"
	></data-source-form>
</template>

<script>
import { Edit } from "@element-plus/icons-vue";
import DataSourceForm from "./DataSourceForm";
import dataSourceService from "@/api/database/dataSourceService";
export default {
	data() {
		return {
			dataList: [],
			loading: false,
		};
	},
	components: {
		DataSourceForm,
		Edit,
	},
	activated() {
		this.refreshList();
	},
	methods: {
		// 获取数据列表
		refreshList() {
			this.loading = true;
			dataSourceService
				.list({
					current: 0,
					size: -1,
				})
				.then((data) => {
					this.dataList = data.records;
					this.loading = false;
				});
		},
		// 新增
		add() {
			this.$refs.dataSourceForm.init("add", "");
		},
		// 修改
		edit(id) {
			this.$refs.dataSourceForm.init("edit", id);
		},

		start(id) {
			this.loading = true;
			dataSourceService
				.start(id)
				.then(() => {
					this.$message.success("启用成功");
					this.loading = false;
					this.refreshList();
				})
				.catch(() => {
					this.loading = false;
				});
		},

		stop(id) {
			this.loading = true;
			dataSourceService
				.stop(id)
				.then(() => {
					this.$message.success("停用成功");
					this.loading = false;
					this.refreshList();
				})
				.catch(() => {
					this.loading = false;
				});
		},
		// 删除
		del(id) {
			this.$confirm(`确定删除所选项吗?`, "提示", {
				confirmButtonText: "确定",
				cancelButtonText: "取消",
				type: "warning",
			})
				.then(() => {
					dataSourceService.delete(id).then((data) => {
						this.$message.success({
							dangerouslyUseHTMLString: true,
							message: data,
						});
						this.refreshList();
					});
				})
				.catch(() => {});
		},
		resetSearch() {
			this.$refs.searchForm.resetFields();
			this.refreshList();
		},
	},
};
</script>
<style scoped>
.el-main {
	padding: 12px 12px 8px 12px;
}

.btn-c {
	width: 30px;
	height: 30px;
}
.task {
	height: 230px;
}
.task-item h2 {
	font-size: 15px;
	color: #3c4a54;
	padding-bottom: 10px;
}
.task-item li {
	list-style-type: none;
	margin-bottom: 7px;
}
.task-item li h4 {
	font-size: 12px;
	font-weight: normal;
	color: #999;
	display: inline;
	margin-right: 15px;
}
.task-item li p {
	display: inline;
}
.task-item .bottom {
	border-top: 1px solid #ebeef5;
	text-align: right;
	padding-top: 8px;
	display: flex;
	justify-content: space-between;
	align-items: center;
}

.task-add {
	display: flex;
	flex-direction: column;
	align-items: center;
	justify-content: center;
	text-align: center;
	cursor: pointer;
	color: #999;
}
.task-add:hover {
	color: var(--el-color-primary);
}
.task-add i {
	font-size: 30px;
}
.task-add p {
	font-size: 12px;
	margin-top: 20px;
}
.el-col .el-card {
	margin-bottom: 15px;
}
</style>
