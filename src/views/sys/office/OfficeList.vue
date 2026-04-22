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
						ref="officeTree"
						node-key="id"
						:data="dataList"
						check-strictly
						:props="treeProps"
						:draggable="hasPermission('sys:office:edit')"
						highlight-current
						:expand-on-click-node="false"
						show-checkbox
						:filter-node-method="filterNode"
						@node-click="nodeClick"
						@node-drop="nodeDrop"
					>
						<template #default="{ node, data }">
							<span class="custom-tree-node el-tree-node__label">
								<span
									class="label"
									v-bind:class="{
										grey: data.useable === '0',
									}"
								>
									<el-icon class="m-r-5">
										<qiye v-if="data.type === '1'"> </qiye>
										<bumen v-else> </bumen>
									</el-icon>
									{{ node.label }}
								</span>
								<span
									class="do"
									v-if="hasPermission('sys:office:add')"
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
						v-if="hasPermission('sys:office:add')"
						icon="plus"
						@click="add()"
					></el-button>
					<el-button
						type="danger"
						size="small"
						v-if="hasPermission('sys:office:del')"
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
					<OfficeForm
						@toView="toView"
						ref="save"
						v-show="method == 'edit'"
						:dataList="dataList"
					></OfficeForm>
					<OfficeDetail
						@toEdit="toEdit"
						ref="detail"
						v-show="method !== 'edit'"
					></OfficeDetail>
				</el-main>
			</el-container>
		</pane>
	</splitpanes>
</template>

<script>
let newOfficeIndex = 1;
import { Splitpanes, Pane } from "splitpanes";
import "splitpanes/dist/splitpanes.css";
import OfficeForm from "./OfficeForm";
import OfficeDetail from "./OfficeDetail";
import officeService from "@/api/sys/officeService";
export default {
	name: "settingOffice",
	components: { Splitpanes, Pane, OfficeForm, OfficeDetail },
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
			this.$refs.officeTree.filter(val);
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
			var data = await officeService.treeData({ showAll: "1" });
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
			officeService
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
			var newOfficeName = "未命名" + newOfficeIndex++;
			var sort = 30;
			if (data) {
				if (data.children && data.children.length > 0) {
					sort = data.children[data.children.length - 1].sort + 30;
				}
			} else if (this.dataList && this.dataList.length > 0) {
				sort = this.dataList[this.dataList.length - 1].sort + 30;
			}
			var newOfficeData = {
				id: "",
				name: newOfficeName,
				parent: {
					id: data ? data.id : "0",
					name: "",
				},
				sort: sort,
				area: "", // 归属区域
				code: "", // 机构编码
				type: "2", // 机构类型（1：公司；2：部门）
				address: "", // 联系地址
				zipCode: "", // 邮政编码
				master: "", // 负责人
				phone: "", // 电话
				fax: "", // 传真
				email: "", // 邮箱
				useable: "1", // 是否可用
				remarks: "", //备注
			};
			this.loading = true;
			var savedOffice = await officeService.save(newOfficeData);
			this.loading = false;
			newOfficeData.id = savedOffice.id;
			this.$refs.officeTree.append(newOfficeData, node);
			this.$refs.officeTree.setCurrentKey(newOfficeData.id);
			this.toEdit();

			this.$refs.save.setData(newOfficeData);
		},
		//删除机构
		async del() {
			var CheckedNodes = this.$refs.officeTree.getCheckedNodes();
			if (CheckedNodes.length == 0) {
				this.$message.warning("请选择需要删除的项");
				return false;
			}

			var confirm = await this.$confirm(
				"确认删除已选择的机构吗？",
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
			var res = await officeService.delete(ids);
			this.loading = false;
			this.$message.success(res);
			CheckedNodes.forEach((item) => {
				var node = this.$refs.officeTree.getNode(item);
				if (node && node.isCurrent) {
					this.$refs.save.setData({});
					this.$refs.detail.setData({});
				}
				this.$refs.officeTree.remove(item);
			});
		},
	},
};
</script>
