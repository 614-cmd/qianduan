<template>
	<div class="page">
		<el-form
			v-if="searchVisible"
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
			<el-form-item>
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
			</el-form-item>
		</el-form>
		<div class="jp-table">
			<vxe-toolbar
				ref="userToolbar"
				:refresh="{ query: refreshList }"
				export
				print
				resizable
				custom
			>
				<template #tools>
					<vxe-button
						type="text"
						:title="searchVisible ? '收起检索' : '展开检索'"
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
						filename: `用户数据${moment(new Date()).format(
							'YYYY-MM-DD'
						)}`,
						sheetName: '用户数据',
						exportMethod: exportMethod,
						types: ['xlsx'],
						modes: ['current', 'selected', 'all'],
					}"
					@sort-change="sortChangeHandle"
					:sort-config="{ remote: true }"
					:data="dataList"
				>
					<vxe-column type="checkbox" width="40px"></vxe-column>
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
					<vxe-column title="登录名" field="loginName"> </vxe-column>
					<vxe-column title="状态" field="loginName">
						<el-tag type="success">在线</el-tag>
					</vxe-column>
					<vxe-column title="姓名" field="name"> </vxe-column>
					<vxe-column title="公司" field="companyDTO.name">
						<template #default="{ row }">
							{{ row.companyDTO && row.companyDTO.name }}
						</template>
					</vxe-column>
					<vxe-column title="部门" field="officeDTO.name">
						<template #default="{ row }">
							{{ row.officeDTO && row.officeDTO.name }}
						</template>
					</vxe-column>
					<vxe-column title="登录IP" field="loginIp"></vxe-column>
					<vxe-column title="登录时间" field="loginDate"></vxe-column>
					<vxe-column
						title="操作"
						width="200px"
						fixed="right"
						align="center"
					>
						<template #default="{ row }">
							<el-button
								type="danger"
								text
								icon="del-filled"
								@click="kickout(row.token)"
								>强制下线</el-button
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
	</div>
</template>

<script>
import onlineService from "@/api/monitor/onlineService";
export default {
	data() {
		return {
			searchForm: {
				loginName: "",
				name: "",
			},
			searchVisible: true,
			dataList: [],
			tablePage: {
				total: 0,
				currentPage: 1,
				pageSize: 10,
			},
			loading: false,
		};
	},
	mounted() {
		this.$nextTick(() => {
			// 将表格和工具栏进行关联
			const $table = this.$refs.userTable;
			const $toolbar = this.$refs.userToolbar;
			$table.connect($toolbar);
		});
		this.refreshList();
	},
	methods: {
		// 获取数据列表
		refreshList() {
			this.loading = true;
			onlineService
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
		// 删除
		kickout(token) {
			this.$confirm(`确定强制下线该用户吗?`, "提示", {
				confirmButtonText: "确定",
				cancelButtonText: "取消",
				type: "warning",
			}).then(() => {
				this.loading = true;
				onlineService
					.kickout(token)
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
		resetSearch() {
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
