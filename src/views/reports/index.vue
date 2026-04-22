<template>
	<div class="page">
		<div class="jp-table">
			<vxe-toolbar
				ref="reportsToolbar"
				:refresh="{ query: refreshList }"
				export
				print
				custom
			>
				<template #buttons>
					<el-button type="primary" icon="plus" @click="add()"
						>新建</el-button
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
					size="small"
					ref="reportsTable"
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
					<vxe-column title="报表名" field="name" sortable>
					</vxe-column>
					<vxe-column title="更新日期" field="updateTime" sortable>
						<template #default="scope">
							{{
								moment(scope.row.updateTime).format(
									"YYYY-MM-DD HH:mm:ss"
								)
							}}
						</template>
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
								@click="pre(scope.row)"
								>预览</el-button
							>
							<el-divider direction="vertical"></el-divider>
							<el-button
								type="primary"
								text
								@click="design(scope.row)"
								>设计</el-button
							>
							<el-divider direction="vertical"></el-divider>
							<el-button
								type="danger"
								text
								@click="del(scope.row)"
								>删除</el-button
							>
							<el-divider direction="vertical"></el-divider>
							<el-button
								type="primary"
								text
								@click="createMenu(scope.row)"
								>发布</el-button
							>
						</template>
					</vxe-column>
				</vxe-table>
			</div>
		</div>
		<gen-menu-form ref="genMenuForm"></gen-menu-form>
	</div>
</template>
<script>
import GenMenuForm from "@/components/menu/GenMenuForm";
import reportService from "@/api/reports/reportService";
export default {
	data() {
		return {
			dataList: [],
			loading: false,
		};
	},
	components: {
		GenMenuForm,
	},
	activated() {
		this.$nextTick(() => {
			// 将表格和工具栏进行关联
			const $table = this.$refs.reportsTable;
			const $toolbar = this.$refs.reportsToolbar;
			$table.connect($toolbar);
		});
		this.refreshList();
	},
	methods: {
		// 获取数据列表
		refreshList() {
			this.loading = true;
			reportService.list().then((data) => {
				this.dataList = data.reports;
				this.loading = false;
			});
		},
		add() {
			this.$router.push({ path: "/ureport/designer" });
		},
		pre(report) {
			this.$router.push({
				path: `/ureport/preview`,
				query: {
					_u: `file:${report.name}`,
					title: `预览报表${report.name}`,
				},
			});
		},
		design(report) {
			this.$router.push({
				path: `/ureport/designer`,
				query: {
					_u: `file:${report.name}`,
					title: `编辑报表${report.name}`,
				},
			});
		},
		// 删除
		del(report) {
			this.$confirm(`确定删除所选项吗?`, "提示", {
				confirmButtonText: "确定",
				cancelButtonText: "取消",
				type: "warning",
			}).then(() => {
				this.loading = true;
				reportService.delete(`file:${report.name}`).then((data) => {
					this.loading = false;
					this.$message.success(data);
					this.refreshList();
				});
			});
		},
		// 创建菜单
		createMenu(report) {
			this.$refs.genMenuForm.init(
				`/ureport/preview?_u=file:${report.name}`,
				report.name,
				"iframe"
			);
		},
	},
};
</script>
