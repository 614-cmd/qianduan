<template>
	<div>
		<div class="vxe-toolbar">
			<div class="vxe-buttons--wrapper">
				<el-button
					v-if="hasPermission('sys:role:assign')"
					type="primary"
					icon="plus"
					@click="add()"
					>添加用户</el-button
				>
			</div>
			<div class="vxe-tools--wrapper"></div>
			<div class="vxe-tools--operate">
				<el-input
					v-model="searchForm.name"
					placeholder="姓名"
					clearable
				>
					<template #append>
						<el-button @click="refreshList()" icon="search" />
					</template>
				</el-input>
			</div>
		</div>
		<div>
			<vxe-table
				border
				auto-resize
				resizable
				:height="height"
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
				<vxe-column type="seq" width="40"></vxe-column>
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
				<vxe-column
					title="登录名"
					field="loginName"
					sortable
				></vxe-column>
				<vxe-column title="用户名" field="name" sortable></vxe-column>
				<vxe-column
					title="所属机构"
					field="companyDTO.name"
					sort-by="c.name"
					sortable
				></vxe-column>
				<vxe-column
					title="所属部门"
					field="officeDTO.name"
					sort-by="o.name"
					sortable
				></vxe-column>
				<vxe-column
					title="操作"
					width="80px"
					fixed="right"
					align="center"
				>
					<template #default="{ row }">
						<el-button
							v-if="hasPermission('sys:role:assign')"
							type="primary"
							text
							@click="del(row.id)"
						>
							移除
						</el-button>
					</template>
				</vxe-column>
			</vxe-table>
		</div>
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
		<user-select
			ref="userSelect"
			@doSubmit="selectUsersToRole"
		></user-select>
	</div>
</template>

<script>
import UserSelect from "@/components/userSelect/UserSelectDialog";
import roleService from "@/api/sys/roleService";
export default {
	data() {
		return {
			height: document.documentElement.clientHeight - 430,
			searchForm: {
				loginName: "",
				name: "",
			},
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
		UserSelect,
	},
	props: ["roleId"],
	mounted() {
		this.$nextTick(() => {
			// 将表格和工具栏进行关联
			const $table = this.$refs.roleTable;
			const $toolbar = this.$refs.roleToolbar;
			$table.connect($toolbar);
		});
	},
	watch: {
		roleId: {
			handler() {
				this.refreshList();
			},
			immediate: true,
			deep: false,
		},
	},
	methods: {
		// 获取数据列表
		refreshList() {
			this.loading = true;
			roleService
				.assign({
					current: this.tablePage.currentPage,
					size: this.tablePage.pageSize,
					orders: this.tablePage.orders,
					"roleDTO.id": this.roleId,
					...this.searchForm,
				})
				.then((data) => {
					this.dataList = data.records;
					this.tablePage.total = data.total;
					this.tablePage.currentPage = data.current;
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
			this.$refs.userSelect.open();
		},

		// 删除
		del(id) {
			this.$confirm(`确认把用户从角色中移除吗?`, "提示", {
				confirmButtonText: "确定",
				cancelButtonText: "取消",
				type: "warning",
			}).then(() => {
				roleService.removeUserFromRole(id, this.roleId).then((data) => {
					this.$message.success({
						dangerouslyUseHTMLString: true,
						message: data,
					});
					this.refreshList();
				});
			});
		},
		closeRight() {
			this.$emit("closeRight");
		},
		resetSearch() {
			this.$refs.searchForm.resetFields();
			this.refreshList();
		},
		selectUsersToRole(users) {
			let userIds = users
				.map((user) => {
					return user.id;
				})
				.join(",");
			this.loading = true;
			roleService.addUserToRole(this.roleId, userIds).then((data) => {
				this.loading = false;
				this.$message.success({
					dangerouslyUseHTMLString: true,
					message: data,
				});
				this.refreshList();
			});
		},
	},
};
</script>
