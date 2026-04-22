<template>
	<span class="bt-group">
		<el-button
			type="primary"
			style="width: 100px"
			v-if="hasPermission('sys:role:auth')"
			@click="inputFormSubmit()"
			v-noMoreClick
			>保存</el-button
		>
	</span>
	<el-form
		:model="inputForm"
		status-icon
		v-loading="loading"
		ref="inputForm"
		@submit.prevent
	>
		<el-tabs type="border-card">
			<el-tab-pane>
				<template #label>
					<span>菜单权限</span>
				</template>
				<el-scrollbar :height="height">
					<el-tree
						:data="menuList"
						:props="{
							label: 'name',
							children: 'children',
						}"
						node-key="id"
						ref="menuListTree"
						:default-expanded-keys="['1']"
						:default-checked-keys="menuCheckedKeys"
						show-checkbox
					>
					</el-tree>
				</el-scrollbar>
			</el-tab-pane>
			<el-tab-pane label="数据权限">
				<template #label>
					<span>数据权限</span>
				</template>
				<el-scrollbar :height="height">
					<el-tree
						:data="dataRuleList"
						:props="{
							label: 'name',
							children: 'children',
						}"
						node-key="id"
						ref="dataRuleTree"
						:default-expanded-keys="['1']"
						:default-checked-keys="dataRuleCheckedKeys"
						show-checkbox
					>
					</el-tree>
				</el-scrollbar>
			</el-tab-pane>
		</el-tabs>
	</el-form>
	<!-- </el-main>
    </el-container> -->

	<!-- <template #footer> -->

	<!-- </template> -->
	<!-- </v-dialog> -->
</template>

<script>
import menuService from "@/api/sys/menuService";
import dataRuleService from "@/api/sys/dataRuleService";
import roleService from "@/api/sys/roleService";
export default {
	props: ["roleId"],
	data() {
		return {
			loading: false,
			title: "",
			height: document.documentElement.clientHeight - 500,
			menuList: [],
			dataRuleList: [],
			menuCheckedKeys: [],
			dataRuleCheckedKeys: [],
			inputForm: {
				id: "",
				menuIds: "",
				dataRuleIds: "",
			},
		};
	},
	watch: {
		roleId: {
			handler(val) {
				this.init(val);
			},
			immediate: true,
			deep: false,
		},
	},
	methods: {
		init(id) {
			this.inputForm.id = id;
			this.$nextTick(() => {
				this.$refs.inputForm.resetFields();
				this.$refs.menuListTree.setCheckedKeys([]);
				this.$refs.dataRuleTree.setCheckedKeys([]);
				let p1 = menuService.treeData({ isShowHide: "1" });
				let p2 = dataRuleService.treeData();
				if (this.inputForm.id) {
					this.loading = true;
					let p3 = roleService.queryById(this.inputForm.id);
					Promise.all([p1, p2, p3]).then((result) => {
						this.menuList = result[0];
						this.dataRuleList = result[1];
						let data = result[2];
						this.loading = false;
						this.inputForm = this.recover(this.inputForm, data);
						this.$refs.menuListTree.setCheckedKeys(
							data.menuIds.split(",")
						);
						this.$refs.dataRuleTree.setCheckedKeys(
							data.dataRuleIds.split(",")
						);
					});
				} else {
					Promise.all([p1, p2]).then((result) => {
						this.menuList = result[0];
						this.dataRuleList = result[1];
						this.loading = false;
					});
				}
			});
		},
		// 表单提交
		inputFormSubmit() {
			this.$refs["inputForm"].validate((valid) => {
				this.inputForm.menuIds = this.$refs.menuListTree
					.getCheckedKeys()
					.concat(this.$refs.menuListTree.getHalfCheckedKeys())
					.join(",");
				this.inputForm.dataRuleIds = this.$refs.dataRuleTree
					.getCheckedKeys()
					.concat(this.$refs.dataRuleTree.getHalfCheckedKeys())
					.join(",");
				if (valid) {
					this.loading = true;
					roleService
						.assignAuthorityToRole(this.inputForm)
						.then((data) => {
							this.loading = false;
							this.$message.success(data);
							this.$emit("refreshDataList");
						})
						.catch(() => {
							this.loading = false;
						});
				}
			});
		},
	},
};
</script>

<style lang="scss" scoped>
.el-tree {
	min-width: 100%;
	display: inline-block !important;
}

.el-scrollbar {
	height: 100%;
}
.bt-group {
	padding: 20px;
	align: right;
	text-align: right;
	display: block;
	width: 100%;
}
</style>
