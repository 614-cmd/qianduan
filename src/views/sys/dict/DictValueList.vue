<template>
	<vxe-toolbar
		ref="dictValueToolbar"
		:refresh="{ query: refreshList }"
		custom
	>
		<template #buttons>
			<el-button type="primary" icon="plus" @click="add()"
				>新建</el-button
			>
			<el-button
				v-if="hasPermission('sys:dict:del')"
				type="danger"
				icon="del-filled"
				@click="del()"
				v-show="
					$refs.dictValueTable &&
					$refs.dictValueTable.getCheckboxRecords().length > 0
				"
				>删除</el-button
			>
		</template>
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
	<vxe-table
		border="inner"
		auto-resize
		resizable
		height="600px"
		:loading="loading"
		size="small"
		ref="dictValueTable"
		show-header-overflow
		show-overflow
		highlight-hover-row
		:menu-config="{}"
		:print-config="{}"
		:import-config="{}"
		:export-config="{}"
		:sort-config="{ remote: true }"
		:data="dataList"
		:checkbox-config="{}"
	>
		<vxe-column type="seq" width="40"></vxe-column>
		<vxe-column type="checkbox" width="40px"></vxe-column>
		<vxe-column field="label" title="标签">
			<template #default="{ row }">
				<el-link
					type="primary"
					:underline="false"
					v-if="hasPermission('sys:dict:edit')"
					@click="edit(row.id)"
					>{{ row.label }}</el-link
				>
				<span v-else>{{ row.label }}</span>
			</template>
		</vxe-column>
		<vxe-column title="键值" field="value"></vxe-column>
		<vxe-column title="排序" field="sort"></vxe-column>
		<vxe-column title="操作" width="250px" fixed="right" align="center">
			<template #default="{ row }">
				<el-button
					v-if="hasPermission('sys:dict:edit')"
					type="primary"
					text
					@click="edit(row.id)"
				>
					修改
				</el-button>
				<el-divider direction="vertical"></el-divider>
				<el-button
					v-if="hasPermission('sys:dict:del')"
					type="danger"
					text
					@click="del(row.id)"
				>
					删除
				</el-button>
			</template>
		</vxe-column>
	</vxe-table>
	<!-- 弹窗, 新增 / 修改 -->
	<dict-value-form
		ref="dictValueForm"
		@refreshDataList="refreshList"
	></dict-value-form>
</template>

<script>
import DictValueForm from "./DictValueForm";
import dictService from "@/api/sys/dictService";
export default {
	data() {
		return {
			dataList: [],
			searchVisible: true,
			loading: false,
		};
	},
	props: ["dictTypeId"],
	components: {
		DictValueForm,
	},
	mounted() {
		this.$nextTick(() => {
			// 将表格和工具栏进行关联
			const $table = this.$refs.dictValueTable;
			const $toolbar = this.$refs.dictValueToolbar;
			$table.connect($toolbar);
		});
	},
	watch: {
		dictTypeId: {
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
			dictService.getDictValue(this.dictTypeId).then((data) => {
				this.dataList = data;
				this.loading = false;
			});
		},
		// 新增
		add() {
			this.dictVisible = true;
			this.$nextTick(() => {
				this.$refs.dictValueForm.init("add", {
					dictValueId: "",
					dictTypeId: this.dictTypeId,
				});
			});
		},
		// 修改
		edit(id) {
			this.dictVisible = true;
			this.$nextTick(() => {
				this.$refs.dictValueForm.init("edit", {
					dictValueId: id,
					dictTypeId: this.dictTypeId,
				});
			});
		},
		// 删除
		del(id) {
			let ids =
				id ||
				this.$refs.dictValueTable
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
				dictService.deleteDictValue(ids).then((data) => {
					this.$message.success(data);
					this.refreshList();
					this.$dictUtils.refreshDictList();
				});
			});
		},
		closeRight() {
			this.$emit("closeRight");
		},
	},
};
</script>
