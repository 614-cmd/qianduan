<template>
	<splitpanes class="default-theme">
		<pane size="20">
			<el-container class="jp-container" v-loading="loading">
				<el-header class="m-p-13-15">
					<el-input
						placeholder="输入关键字进行过滤"
						v-model="menuFilterText"
						clearable
					></el-input>
				</el-header>
				<el-main class="nopadding">
					<el-tree
						ref="menu"
						class="menu"
						node-key="id"
						:data="menuList[0]?.children"
						:props="menuProps"
						check-strictly
						:draggable="hasPermission('sys:menu:edit')"
						highlight-current
						:expand-on-click-node="false"
						show-checkbox
						:filter-node-method="menuFilterNode"
						@node-click="menuClick"
						@node-drop="nodeDrop"
					>
						<template #default="{ node, data }">
							<span class="custom-tree-node el-tree-node__label">
								<span
									class="label"
									v-bind:class="{ grey: data.isShow === '0' }"
								>
									<el-icon v-if="data.icon" :size="20"
										><component :is="data.icon" /></el-icon
									>{{ node.label }}
								</span>
								<span
									class="do"
									v-if="hasPermission('sys:menu:add')"
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
						v-if="hasPermission('sys:menu:add')"
						icon="plus"
						@click="add()"
					></el-button>
					<el-button
						type="danger"
						size="small"
						v-if="hasPermission('sys:menu:del')"
						icon="del-filled"
						@click="delMenu"
					></el-button>
					<el-button
						type="default"
						size="small"
						icon="refresh"
						@click="getMenu()"
					></el-button>
				</el-footer>
			</el-container>
		</pane>
		<pane size="80">
			<el-container class="jp-container">
				<el-main class="nopadding">
					<save ref="save" :menu="menuList"></save>
				</el-main>
			</el-container>
		</pane>
	</splitpanes>
</template>

<script>
let newMenuIndex = 1;
import { Splitpanes, Pane } from "splitpanes";
import "splitpanes/dist/splitpanes.css";
import save from "./MenuForm.vue";
import menuService from "@/api/sys/menuService";
import userService from "@/api/sys/userService";
export default {
	name: "settingMenu",
	components: { Splitpanes, Pane, save },
	data() {
		return {
			loading: false,
			menuList: [],
			menuProps: {
				label: (data) => {
					return data.name;
				},
			},
			menuFilterText: "",
		};
	},
	watch: {
		menuFilterText(val) {
			this.$refs.menu.filter(val);
		},
	},
	mounted() {
		this.getMenu();
	},
	methods: {
		//加载树数据
		async getMenu() {
			this.loading = true;
			var data = await menuService.treeData({ isShowHide: "1" });
			this.loading = false;
			this.menuList = data;
		},
		//树点击
		menuClick(data, node) {
			var pid = node.level == 1 ? undefined : node.parent.data.id;
			this.$refs.save.setData(data, pid);
		},
		//树过滤
		menuFilterNode(value, data) {
			if (!value) return true;
			var targetText = data.name;
			return targetText.indexOf(value) !== -1;
		},
		//树拖拽
		nodeDrop(draggingNode, dropNode, dropType) {
			// eslint-disable-next-line no-debugger
			this.$refs.save.setData({});
			this.loading = true;
			menuService
				.drag({
					draggingNode: draggingNode.data,
					dropNode: dropNode.data,
					dropType: dropType,
				})
				.then((data) => {
					this.$message(data);
					this.loading = false;
					this.refreshMenu();
				});
		},
		//增加
		async add(node, data) {
			var newMenuName = "未命名" + newMenuIndex++;
			var sort = 30;
			if (data) {
				if (data.children && data.children.length > 0) {
					sort = data.children[data.children.length - 1].sort + 30;
				}
			} else if (this.dataList && this.dataList.length > 0) {
				sort = this.dataList[this.dataList.length - 1].sort + 30;
			}
			var newMenuData = {
				id: "",
				type: "1",
				name: newMenuName,
				parent: {
					id: data ? data.id : "1",
					name: "",
				},
				href: "",
				permission: "",
				sort: sort,
				icon: "",
				remarks: "",
				target: "",
				isShow: "1",
				affix: "0",
				hiddenBreadcrumb: "0",
			};
			this.loading = true;
			var savedMenu = await menuService.save(newMenuData);
			this.loading = false;
			newMenuData.id = savedMenu.id;

			this.$refs.menu.append(newMenuData, node);
			this.$refs.menu.setCurrentKey(newMenuData.id);
			var pid = node ? node.data.id : "";
			this.$refs.save.setData(newMenuData, pid);
			this.refreshMenu();
		},
		//删除菜单
		async delMenu() {
			var CheckedNodes = this.$refs.menu.getCheckedNodes();
			if (CheckedNodes.length == 0) {
				this.$message.warning("请选择需要删除的项");
				return false;
			}

			var confirm = await this.$confirm(
				"确认删除已选择的菜单吗？",
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
			var res = await menuService.delete(ids);
			this.loading = false;
			this.$message.success(res);
			CheckedNodes.forEach((item) => {
				var node = this.$refs.menu.getNode(item);
				if (node && node.isCurrent) {
					this.$refs.save.setData({});
				}
				this.$refs.menu.remove(item);
			});
			this.refreshMenu();
		},
		async refreshMenu() {
			let { menuList, permissions, dictList } =
				await userService.getMenus();
			this.$TOOL.data.set("PERMISSIONS", permissions);
			this.$TOOL.data.set("DICT_LIST", dictList);
			this.$TOOL.data.set("MENU", menuList);
		},
	},
};
</script>
