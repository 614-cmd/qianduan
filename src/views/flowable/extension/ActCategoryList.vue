<template>
	<splitpanes class="default-theme">
		<pane size="20">
			<el-container class="jp-container" v-loading="loading">
				<el-header class="m-p-13-15">
					<el-input
						placeholder="输入关键字进行过滤"
						v-model="nodeFilterText"
						clearable
					></el-input>
				</el-header>
				<el-main class="nopadding">
					<el-tree
						ref="actCategoryTree"
						node-key="id"
						:data="dataList"
						:props="treeProps"
						:draggable="hasPermission('extension:actCategory:edit')"
						highlight-current
						:expand-on-click-node="false"
						check-strictly
						show-checkbox
						:filter-node-method="filterNode"
						@node-click="nodeClick"
						@node-drop="nodeDrop"
					>
						<template #default="{ node, data }">
							<span class="custom-tree-node el-tree-node__label">
								<span class="label">
									{{ node.label }}
								</span>
								<span
									class="do"
									v-if="
										hasPermission(
											'extension:actCategory:add'
										)
									"
								>
									<el-icon
										@click.stop="add(node, data)"
										:size="25"
										><plus></plus
									></el-icon>
								</span>
							</span>
						</template>
					</el-tree>
				</el-main>
				<el-footer style="height: 51px">
					<el-button
						type="primary"
						size="small"
						v-if="hasPermission('extension:actCategory:add')"
						icon="plus"
						@click="add()"
					></el-button>
					<el-button
						type="danger"
						size="small"
						v-if="hasPermission('extension:actCategory:del')"
						icon="del-filled"
						@click="del()"
					></el-button>
					<el-button
						type="default"
						size="small"
						icon="refresh"
						@click="getDataList()"
					></el-button>
				</el-footer>
			</el-container>
		</pane>
		<pane size="80">
			<el-container class="jp-container">
				<el-main class="nopadding">
					<ActCategoryForm
						@toView="toView"
						ref="save"
						v-show="method == 'edit'"
						:dataList="dataList"
					></ActCategoryForm>
					<ActCategoryDetail
						@toEdit="toEdit"
						ref="detail"
						v-show="method !== 'edit'"
					></ActCategoryDetail>
				</el-main>
			</el-container>
		</pane>
	</splitpanes>
</template>

<script>
let newActCategoryIndex = 1;
import { Splitpanes, Pane } from "splitpanes";
import "splitpanes/dist/splitpanes.css";
import ActCategoryForm from "./ActCategoryForm";
import ActCategoryDetail from "./ActCategoryDetail";
import actCategoryService from "@/api/flowable/actCategoryService";
export default {
	name: "settingActCategory",
	components: { Splitpanes, Pane, ActCategoryForm, ActCategoryDetail },
	data() {
		return {
			loading: false,
			method: "view",
			dataList: [],
			treeProps: {
				label: (data) => {
					return data.name;
				},
			},
			nodeFilterText: "",
		};
	},
	watch: {
		nodeFilterText(val) {
			this.$refs.actCategoryTree.filter(val);
		},
	},
	activated() {
		this.getDataList();
	},
	methods: {
		//跳到编辑
		toEdit() {
			this.method = "edit";
		},
		//跳到查看
		toView() {
			this.method = "view";
		},
		//加载树数据
		async getDataList() {
			this.loading = true;
			var data = await actCategoryService.treeData();
			this.loading = false;
			this.dataList = data;
		},
		//树点击
		nodeClick(data) {
			this.$refs.save.setData(data);
			this.$refs.detail.setData(data);
		},
		//树过滤
		filterNode(value, data) {
			if (!value) return true;
			var targetText = data.name;
			return targetText.indexOf(value) !== -1;
		},
		//树拖拽
		nodeDrop(draggingNode, dropNode, dropType) {
			// eslint-disable-next-line no-debugger
			this.$refs.save.setData({});
			this.$refs.detail.setData({});
			this.loading = true;
			actCategoryService
				.drag({
					draggingNode: draggingNode.data,
					dropNode: dropNode.data,
					dropType: dropType,
				})
				.then((data) => {
					this.$message(data);
					this.loading = false;
				});
		},
		//增加
		async add(node, data) {
			var newActCategoryName = "未命名" + newActCategoryIndex++;
			var sort = 30;
			if (data) {
				if (data.children && data.children.length > 0) {
					sort = data.children[data.children.length - 1].sort + 30;
				}
			} else if (this.dataList && this.dataList.length > 0) {
				sort = this.dataList[this.dataList.length - 1].sort + 30;
			}
			var newActCategoryData = {
				id: "",
				name: newActCategoryName,
				parent: {
					id: data ? data.id : "0",
					name: "",
				},
				sort: sort,
				code: "", // 流程分类编码
				type: "", // 流程分类类型
				remarks: "",
			};
			this.loading = true;
			var savedActCategory = await actCategoryService.save(
				newActCategoryData
			);
			this.loading = false;
			newActCategoryData.id = savedActCategory.id;
			this.$refs.actCategoryTree.append(newActCategoryData, node);
			this.$refs.actCategoryTree.setCurrentKey(newActCategoryData.id);
			this.toEdit();

			this.$refs.save.setData(newActCategoryData);
		},
		//删除流程分类
		async del() {
			var CheckedNodes = this.$refs.actCategoryTree.getCheckedNodes();
			if (CheckedNodes.length == 0) {
				this.$message.warning("请选择需要删除的项");
				return false;
			}

			var confirm = await this.$confirm(
				"确认删除已选择的流程分类吗？",
				"提示",
				{
					type: "warning",
					confirmButtonText: "删除",
					confirmButtonClass: "el-button--danger",
				}
			).catch(() => {});
			if (confirm != "confirm") {
				return false;
			}

			this.loading = true;
			var ids = CheckedNodes.map((item) => item.id).join(",");
			var res = await actCategoryService.delete(ids);
			this.loading = false;
			this.$message.success(res);
			CheckedNodes.forEach((item) => {
				var node = this.$refs.actCategoryTree.getNode(item);
				if (node && node.isCurrent) {
					this.$refs.save.setData({});
					this.$refs.detail.setData({});
				}
				this.$refs.actCategoryTree.remove(item);
			});
		},
	},
};
</script>
