<template>
	<div class="page">
		<h2>数据权限规则</h2>
		<div class="jp-table">
			<vxe-toolbar
				ref="roleToolbar"
				:refresh="{ query: refreshList }"
				custom
			>
				<template #buttons>
					<el-button
						type="primary"
						v-if="hasPermission('sys:role:datarule')"
						@click="add()"
						>新建</el-button
					>
				</template>
			</vxe-toolbar>
			<div class="jp-table-body">
				<vxe-table
					border
					auto-resize
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
					:sort-config="{ remote: false }"
					:data="dataList"
					:checkbox-config="{}"
				>
					<vxe-column type="seq" width="40"></vxe-column>
					<vxe-column
						width="160px"
						title="数据规则名称"
						field="name"
						sortable
					></vxe-column>
					<vxe-column
						width="160px"
						title="规则mapper方法"
						field="className"
						sortable
					></vxe-column>
					<vxe-column
						width="160px"
						title="规则字段"
						field="field"
						sortable
					></vxe-column>
					<vxe-column
						width="160px"
						title="规则条件"
						field="express"
						sortable
					>
						<template #default="{ row }">
							{{
								$dictUtils.getDictLabel(
									"t_express",
									row.express,
									""
								)
							}}
						</template>
					</vxe-column>
					<vxe-column
						width="160px"
						title="规则值"
						field="value"
						sortable
					></vxe-column>
					<vxe-column
						width="160px"
						title="自定义SQL"
						field="sqlSegment"
						sortable
					></vxe-column>
					<vxe-column
						width="160px"
						title="备注信息"
						field="remarks"
						sortable
					></vxe-column>
					<vxe-column
						width="160px"
						title="规则值"
						field="value"
						sortable
					></vxe-column>
					<vxe-column
						title="操作"
						width="160px"
						fixed="right"
						align="center"
					>
						<template #default="{ row }">
							<el-button
								v-if="hasPermission('sys:role:datarule')"
								@click="edit(row.id)"
								>修改</el-button
							>
							<el-button
								type="danger"
								v-if="hasPermission('sys:role:datarule')"
								@click="del(row.id)"
							>
								删除
							</el-button>
						</template>
					</vxe-column>
				</vxe-table>
			</div>
			<data-rule-form
				ref="dataRuleForm"
				@refreshDataList="refreshList"
			></data-rule-form>
		</div>
	</div>
</template>

<script>
import DataRuleForm from "./DataRuleForm";
import dataRuleService from "@/api/sys/dataRuleService";
export default {
	data() {
		return {
			dataList: [],
			loading: false,
			// menuId: '',
			title: "",
		};
	},
	props: ["menuId"],
	components: {
		DataRuleForm,
	},
	mounted() {
		this.$nextTick(() => {
			// 将表格和工具栏进行关联
			const $table = this.$refs.roleTable;
			const $toolbar = this.$refs.roleToolbar;
			$table.connect($toolbar);
		});
	},
	watch: {
		menuId: {
			handler() {
				this.refreshList();
			},
			immediate: true,
		},
	},
	methods: {
		// 获取数据列表
		refreshList() {
			this.loading = true;
			dataRuleService
				.list({
					current: 1,
					size: -1,
					menuId: this.menuId,
				})
				.then((data) => {
					this.dataList = data.records;
					this.loading = false;
				});
		},
		// 新增
		add() {
			this.$refs.dataRuleForm.init("add", {
				id: "",
				menuId: this.menuId,
			});
		},
		// 修改
		edit(id) {
			this.$refs.dataRuleForm.init("edit", {
				id: id,
				menuId: this.menuId,
			});
		},
		// 查看
		view(id) {
			this.$refs.dataRuleForm.init("view", {
				id: id,
				menuId: this.menuId,
			});
		},
		// 排序
		sortChangeHandle(column) {
			this.tablePage.orders = [];
			this.tablePage.orders.push({
				column: this.$utils.toLine(column.property),
				asc: column.order === "asc",
			});
			this.refreshList();
		},
		// 删除
		del(id) {
			this.$confirm(`确定删除该条记录吗?`, "提示", {
				confirmButtonText: "确定",
				cancelButtonText: "取消",
				type: "warning",
			}).then(() => {
				this.loading = true;
				dataRuleService.delete(id).then((data) => {
					this.loading = false;
					this.$message.success(data);
					this.refreshList(this.menuId);
				});
			});
		},
	},
};
</script>
<style lang="less" scoped>
.page {
	padding: 10px;
	border: none;
	height: calc(100% - 25px);
	margin: 0;
	.jp-table {
		.jp-table-body {
			height: calc(100% - 51px);
		}
	}
}
</style>
