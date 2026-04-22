<template>
	<el-card class="box-card">
		<template #header>
			<div class="card-header">
				<span>我的通知</span>
			</div>
		</template>
		<el-table :data="dataList" style="width: 100%" height="480">
			<el-table-column label="标题" prop="title">
				<template #default="scope">
					<el-link
						type="primary"
						:underline="false"
						@click="view(scope.row.id)"
						>{{ scope.row.title }}</el-link
					>
				</template>
			</el-table-column>
			<el-table-column label="类型" prop="type">
				<template #default="scope">
					{{
						$dictUtils.getDictLabel(
							"oa_notify_type",
							scope.row.type,
							"-"
						)
					}}
				</template>
			</el-table-column>
			<el-table-column
				show-overflow-tooltip
				label="内容"
				prop="content"
			></el-table-column>
			<el-table-column show-overflow-tooltip label="附件" prop="files">
				<template #default="scope">
					<el-link
						:href="item"
						target="_blank"
						:key="index"
						style="margin-right: 8px"
						v-for="(item, index) in (scope.row.files || '').split(
							','
						)"
					>
						<el-tag v-if="item">
							{{
								decodeURIComponent(
									item.substring(
										item.lastIndexOf("&name=") + 6
									)
								)
							}}</el-tag
						>
					</el-link>
				</template>
			</el-table-column>

			<el-table-column label="查阅状态" prop="status">
				<template #default="scope">
					<el-tag type="success" v-if="scope.row.readFlag === '1'">
						{{
							$dictUtils.getDictLabel(
								"oa_notify_read",
								scope.row.readFlag,
								"-"
							)
						}}</el-tag
					>
					<el-tag type="danger" v-if="scope.row.readFlag === '0'">
						{{
							$dictUtils.getDictLabel(
								"oa_notify_read",
								scope.row.readFlag,
								"-"
							)
						}}</el-tag
					>
				</template>
			</el-table-column>
			<el-table-column
				label="发布者"
				prop="createBy.name"
			></el-table-column>
			<el-table-column
				label="操作"
				width="200px"
				fixed="right"
				align="center"
			>
				<template #default="scope">
					<el-button
						type="primary"
						text
						icon="view-filled"
						@click="view(scope.row.id)"
						>查看</el-button
					>
				</template>
			</el-table-column>
		</el-table>
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
	</el-card>
	<NotifyForm ref="notifyForm" @refreshDataList="refreshList"></NotifyForm>
</template>

<script>
import NotifyForm from "@/views/notify/NotifyForm.vue";
import notifyService from "@/api/notify/notifyService";
export default {
	title: "我的通知",
	icon: "tonggao5",
	description: "我的通知",
	layout: {
		w: 6,
		h: 15,
	},
	components: {
		NotifyForm,
	},
	data() {
		return {
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
	mounted() {
		this.refreshList();
	},

	activated() {
		this.refreshList();
	},
	methods: {
		// 获取数据列表
		refreshList() {
			this.loading = true;
			notifyService
				.list({
					current: this.tablePage.currentPage,
					size: this.tablePage.pageSize,
					orders: this.tablePage.orders,
					isSelf: true,
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
			}
			this.refreshList();
		},
		// 查看
		view(id) {
			this.$refs.notifyForm.init("read", id);
		},
		resetSearch() {
			this.$refs.searchForm.resetFields();
			this.refreshList();
		},
	},
};
</script>
<style scoped>
.box-card {
	height: 100%;
}
</style>
