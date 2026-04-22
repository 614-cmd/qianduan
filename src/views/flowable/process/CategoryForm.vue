<template>
	<v-dialog
		title="请选择流程分类"
		:close-on-click-modal="false"
		v-model="visible"
		width="500px"
	>
		{{ selectCategoryName }}
		<el-tree
			class="filter-tree"
			:data="categoryTreeData"
			:props="{
				value: 'id', // ID字段名
				label: 'name', // 显示名称
				children: 'children', // 子级字段名
			}"
			default-expand-all
			:expand-on-click-node="false"
			@node-click="handleNodeClick"
			ref="categoryTree"
		>
		</el-tree>
		<template #footer>
			<span class="dialog-footer">
				<el-button @click="visible = false" icon="circle-close"
					>关闭</el-button
				>
				<el-button
					type="primary"
					:disabled="!inputForm.category"
					@click="inputFormSubmit()"
					>确定</el-button
				>
			</span>
		</template>
	</v-dialog>
</template>

<script>
import actCategoryService from "@/api/flowable/actCategoryService";
import modelService from "@/api/flowable/modelService";
export default {
	data() {
		return {
			visible: false,
			categoryTreeData: [],
			selectCategoryName: "",
			inputForm: {
				key: "",
				category: "",
			},
		};
	},
	methods: {
		init(key) {
			this.visible = true;
			this.inputForm.key = key;
			this.inputForm.category = "";
			this.$nextTick(() => {
				this.selectCategoryName = "";
				this.refreshTree();
			});
		},
		// 获取树数据
		refreshTree() {
			actCategoryService.treeData().then((data) => {
				this.categoryTreeData = data;
			});
		},
		handleNodeClick(data) {
			this.inputForm.category = data.name;
			this.selectCategoryName = "已选类型: " + data.name;
		},
		// 表单提交
		inputFormSubmit() {
			if (!this.inputForm.category) {
				this.$message.error("请选择一个分类!");
				return;
			}
			modelService
				.updateCategory({
					key: this.inputForm.key,
					category: this.inputForm.category,
				})
				.then((data) => {
					this.$message.success({
						dangerouslyUseHTMLString: true,
						message: data,
					});
					this.$emit("refreshList");
					this.visible = false;
				});
		},
	},
};
</script>
