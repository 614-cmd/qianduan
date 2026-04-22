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
						ref="areaTree"
						node-key="id"
						:data="dataList"
						:props="treeProps"
						:draggable="hasPermission('sys:area:edit')"
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
									v-if="hasPermission('sys:area:add')"
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
						v-if="hasPermission('sys:area:add')"
						icon="plus"
						@click="add()"
					></el-button>
					<el-button
						type="danger"
						size="small"
						v-if="hasPermission('sys:area:del')"
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
					<AreaForm
						@toView="toView"
						ref="save"
						v-show="method == 'edit'"
						:dataList="dataList"
					></AreaForm>
					<AreaDetail
						@toEdit="toEdit"
						ref="detail"
						v-show="method !== 'edit'"
					></AreaDetail>
				</el-main>
			</el-container>
		</pane>
	</splitpanes>
</template>

<script>
let newAreaIndex = 1;
import { Splitpanes, Pane } from "splitpanes";
import "splitpanes/dist/splitpanes.css";
import AreaForm from "./AreaForm";
import AreaDetail from "./AreaDetail";
import areaService from "@/api/sys/areaService";
export default {
	name: "settingArea",
	components: { Splitpanes, Pane, AreaForm, AreaDetail },
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
			this.$refs.areaTree.filter(val);
		},
	},
	mounted() {
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
			var data = await areaService.treeData();
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
			areaService
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
			var newAreaName = "未命名" + newAreaIndex++;
			var sort = 30;
			if (data) {
				if (data.children && data.children.length > 0) {
					sort = data.children[data.children.length - 1].sort + 30;
				}
			} else if (this.dataList && this.dataList.length > 0) {
				sort = this.dataList[this.dataList.length - 1].sort + 30;
			}
			var newAreaData = {
				id: "",
				name: newAreaName,
				parent: {
					id: data ? data.id : "0",
					name: "",
				},
				sort: sort,
				code: "", // 区域编码
				type: "", // 区域类型
				remarks: "",
			};
			this.loading = true;
			var savedArea = await areaService.save(newAreaData);
			this.loading = false;
			newAreaData.id = savedArea.id;
			this.$refs.areaTree.append(newAreaData, node);
			this.$refs.areaTree.setCurrentKey(newAreaData.id);
			this.toEdit();

			this.$refs.save.setData(newAreaData);
		},
		//删除区域
		async del() {
			var CheckedNodes = this.$refs.areaTree.getCheckedNodes();
			if (CheckedNodes.length == 0) {
				this.$message.warning("请选择需要删除的项");
				return false;
			}

			var confirm = await this.$confirm(
				"确认删除已选择的区域吗？",
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
			var res = await areaService.delete(ids);
			this.loading = false;
			this.$message.success(res);
			CheckedNodes.forEach((item) => {
				var node = this.$refs.areaTree.getNode(item);
				if (node && node.isCurrent) {
					this.$refs.save.setData({});
					this.$refs.detail.setData({});
				}
				this.$refs.areaTree.remove(item);
			});
		},
	},
};
</script>
