<template>
	<div class="page">
		<el-form
			:inline="true"
			v-show="isSearchCollapse"
			class="query-form m-b-10"
			ref="searchForm"
			:model="searchForm"
			@keyup.enter="refreshList()"
			@submit.prevent
		>
			<el-form-item label="流程分类：" prop="category">
				<el-select
					v-model="searchForm.category"
					placeholder="请选择"
					style="width: 100%"
				>
					<el-option
						v-for="item in this.$dictUtils.getDictList(
							'act_category'
						)"
						:key="item.value"
						:label="item.label"
						:value="item.value"
					>
					</el-option>
				</el-select>
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
		</el-form>
		<el-row>
			<el-button
				type="danger"
				icon="del-filled"
				@click="del()"
				v-show="dataListSelections.length > 0"
				>删除
			</el-button>
			<el-button-group class="pull-right">
				<el-tooltip
					class="item"
					effect="dark"
					content="搜索"
					placement="top"
				>
					<el-button
						type="default"
						icon="search"
						@click="
							(isSearchCollapse = !isSearchCollapse),
								(isImportCollapse = false)
						"
					>
					</el-button>
				</el-tooltip>
				<el-tooltip
					class="item"
					effect="dark"
					content="刷新"
					placement="top"
				>
					<el-button
						type="default"
						icon="refresh"
						@click="refreshList"
					>
					</el-button>
				</el-tooltip>
			</el-button-group>
		</el-row>
		<el-table
			:data="dataList"
			border
			v-loading="loading"
			@selection-change="selectionChangeHandle"
			class="table"
		>
			<el-table-column
				type="selection"
				header-align="center"
				align="center"
				width="50"
			>
			</el-table-column>
			<el-table-column
				prop="category"
				header-align="center"
				align="center"
				label="流程分类"
			>
				<template #default="scope">
					{{
						$dictUtils.getDictLabel(
							"act_category",
							scope.row.category,
							""
						)
					}}
				</template>
			</el-table-column>
			<el-table-column
				prop="key"
				header-align="center"
				align="center"
				label="流程标识"
			>
			</el-table-column>
			<el-table-column
				prop="name"
				header-align="center"
				show-overflow-tooltip
				align="center"
				label="流程名称"
			>
			</el-table-column>
			<el-table-column
				prop="resourceName"
				header-align="center"
				align="center"
				label="流程XML"
			>
				<template #default="scope">
					<el-button text @click="download(scope.row)">
						{{ scope.row.resourceName }}
					</el-button>
				</template>
			</el-table-column>
			<el-table-column
				prop="diagramResourceName"
				header-align="center"
				align="center"
				label="流程图"
			>
				<template #default="scope">
					<el-button text @click="trace(scope.row)">
						{{ scope.row.diagramResourceName }}
					</el-button>
				</template>
			</el-table-column>
			<el-table-column
				prop="version"
				header-align="center"
				align="center"
				label="流程版本"
			>
			</el-table-column>
			<el-table-column
				prop="suspended"
				header-align="center"
				align="center"
				width="150"
				:show-overflow-tooltip="true"
				label="状态"
			>
				<template #default="scope">
					<el-tag v-if="scope.row.suspended" type="danger"
						>已挂起</el-tag
					>
					<el-tag v-if="!scope.row.suspended" type="success"
						>已激活</el-tag
					>
				</template>
			</el-table-column>
			<el-table-column
				prop="deploymentTime"
				header-align="center"
				align="center"
				width="150"
				:show-overflow-tooltip="true"
				label="部署时间"
			>
			</el-table-column>
			<el-table-column
				fixed="right"
				header-align="center"
				align="center"
				width="200"
				label="操作"
			>
				<template #default="scope">
					<el-button
						text
						v-if="scope.row.suspended"
						@click="active(scope.row)"
						>激活
					</el-button>
					<el-button
						text
						v-if="hasPermission('act:process:del')"
						@click="del(scope.row.deploymentId)"
						>删除
					</el-button>
					<el-button
						text
						v-if="!scope.row.suspended"
						@click="suspend(scope.row)"
						>挂起
					</el-button>
					<el-button text @click="toModel(scope.row)"
						>转换为模型
					</el-button>
				</template>
			</el-table-column>
		</el-table>
		<el-pagination
			@size-change="sizeChangeHandle"
			@current-change="currentChangeHandle"
			:current-page="pageNo"
			:page-sizes="[10, 20, 50, 100]"
			:page-size="pageSize"
			:total="total"
			background
			layout="total, sizes, prev, pager, next, jumper"
		>
		</el-pagination>
		<v-dialog
			title="查看进度"
			:close-on-click-modal="true"
			v-model="visible"
			height="600px"
		>
			<iframe
				:src="processPhotoUrl"
				frameborder="0"
				scrolling="auto"
				width="100%"
				height="600px"
			></iframe>
		</v-dialog>
	</div>
</template>

<script>
export default {
	data() {
		return {
			searchForm: {
				category: "",
			},
			searchDates: "",
			dataList: [],
			pageNo: 1,
			pageSize: 10,
			total: 0,
			isSearchCollapse: false,
			loading: false,
			visible: false,
			dataListSelections: [],
			processPhotoUrl: "",
		};
	},
	activated() {
		this.refreshList();
	},
	watch: {
		searchDates() {
			if (this.searchDates) {
				this.searchForm.beginDate = this.searchDates[0];
				this.searchForm.endDate = this.searchDates[1];
			} else {
				this.searchForm.beginDate = "";
				this.searchForm.endDate = "";
			}
		},
	},
	methods: {
		// 获取数据列表
		refreshList() {
			this.loading = true;
			this.$http({
				url: "/flowable/process/list",
				method: "get",
				params: {
					pageNo: this.pageNo,
					pageSize: this.pageSize,
					...this.searchForm,
				},
			}).then((data) => {
				if (data) {
					this.dataList = data.records;
					this.total = data.total;
					this.loading = false;
				}
			});
		},
		// 每页数
		sizeChangeHandle(val) {
			this.pageSize = val;
			this.pageNo = 1;
			this.refreshList();
		},
		// 当前页
		currentChangeHandle(val) {
			this.pageNo = val;
			this.refreshList();
		},
		// 多选
		selectionChangeHandle(val) {
			this.dataListSelections = val;
		},
		active(row) {
			this.$confirm(`确定要激活码?`, "提示", {
				confirmButtonText: "确定",
				cancelButtonText: "取消",
				type: "warning",
			}).then(() => {
				this.$http
					.get(`/flowable/process/update/active?procDefId=${row.id}`)
					.then((data) => {
						if (data && data.success) {
							this.$message.success({
								dangerouslyUseHTMLString: true,
								message: data,
							});
							this.refreshList();
						}
					});
			});
		},
		del(id) {
			let ids =
				id ||
				this.dataListSelections
					.map((item) => {
						return item.deploymentId;
					})
					.join(",");
			this.$confirm(`确认要删除该流程吗?`, "提示", {
				confirmButtonText: "确定",
				cancelButtonText: "取消",
				type: "warning",
			}).then(() => {
				this.$http
					.delete("/flowable/process/delete", {
						params: { deploymentIds: ids },
					})
					.then((data) => {
						if (data && data.success) {
							this.$message.success(data);
							this.refreshList();
						}
					});
			});
		},
		suspend(row) {
			this.$confirm(`确认要挂起该流程吗?`, "提示", {
				confirmButtonText: "确定",
				cancelButtonText: "取消",
				type: "warning",
			}).then(() => {
				this.$http
					.get(`/flowable/process/update/suspend?procDefId=${row.id}`)
					.then((data) => {
						if (data && data.success) {
							this.$message.success({
								dangerouslyUseHTMLString: true,
								message: data,
							});
							this.refreshList();
						}
					});
			});
		},
		toModel(row) {
			this.$confirm(`确认要转换为模型吗?`, "提示", {
				confirmButtonText: "确定",
				cancelButtonText: "取消",
				type: "warning",
			}).then(() => {
				this.$http
					.get(
						`/flowable/process/convert/toModel?procDefId=${row.id}`
					)
					.then((data) => {
						if (data && data.success) {
							this.$message.success({
								dangerouslyUseHTMLString: true,
								message: data,
							});
							this.refreshList();
						}
					});
			});
		},
		resetSearch() {
			this.searchDates = "";
			this.$refs.searchForm.resetFields();
			this.$nextTick(() => {
				this.refreshList();
			});
		},
		trace(row) {
			this.processPhotoUrl = `${
				import.meta.env.VITE_APP_BASE
			}/flowable/process/resource/read?procDefId=${row.id}&resType=image`;
			this.visible = true;
		},
		download(row) {
			this.$utils.download(
				`/flowable/process/resource/read?procDefId=${row.id}&resType=xml`
			);
		},
	},
};
</script>
