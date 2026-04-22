<template>
	<template v-if="!inputForm.id">
		<el-empty description="请选择左侧菜单后操作" :image-size="250">
			<template #image>
				<img src="@/assets/img/empty.svg" />
			</template>
		</el-empty>
	</template>
	<template v-else>
		<splitpanes style="height: 100%">
			<pane size="50" style="padding: 10px">
				<h2>{{ inputForm.name || "新增菜单" }}</h2>
				<el-form
					:model="inputForm"
					:rules="rules"
					ref="inputForm"
					label-width="100px"
					label-position="left"
				>
					<el-form-item label="显示名称" prop="name">
						<el-input
							v-model="inputForm.name"
							clearable
							:placeholder="typeList[inputForm.type] + '名字'"
						></el-input>
					</el-form-item>
					<el-form-item label="上级菜单" prop="parent.id">
						<el-cascader
							v-model="inputForm.parent.id"
							:options="menuOptions"
							:props="menuProps"
							:show-all-levels="false"
							placeholder=""
							disabled
						></el-cascader>
					</el-form-item>
					<el-form-item label="类型" prop="type">
						<el-radio-group v-model="inputForm.type">
							<el-radio-button value="0">目录</el-radio-button>
							<el-radio-button value="1">菜单</el-radio-button>
							<el-radio-button value="2">按钮</el-radio-button>
							<el-radio-button value="3">路由</el-radio-button>
						</el-radio-group>
					</el-form-item>
					<el-form-item
						v-if="
							inputForm.type === '1' ||
							inputForm.type === '2' ||
							inputForm.type === '3'
						"
						label="链接地址"
						prop="href"
					>
						<el-input
							maxlength="1000"
							v-model="inputForm.href"
							placeholder="请填写路由路径或者超链接"
						></el-input>
					</el-form-item>
					<el-form-item
						v-if="inputForm.type === '1' || inputForm.type === '2'"
						label="链接类型"
						prop="target"
					>
						<el-select
							v-model="inputForm.target"
							placeholder="路由请留空，http(s)链接选择iframe, 新窗口打开选link"
							clearable
							style="width: 100%"
						>
							<el-option
								v-for="item in [
									{ label: 'iframe', value: 'iframe' },
									{ label: 'link', value: 'link' },
								]"
								:key="item.value"
								:label="item.label"
								:value="item.value"
							>
							</el-option>
						</el-select>
					</el-form-item>
					<el-form-item
						v-if="inputForm.type !== '2' && inputForm.type !== '3'"
						label="菜单图标"
						prop="icon"
					>
						<icon-select
							v-model="inputForm.icon"
							clearable
						></icon-select>
					</el-form-item>
					<el-form-item
						v-if="inputForm.type !== '2' && inputForm.type !== '3'"
						label="可见"
						prop="isShow"
					>
						<el-radio-group v-model="inputForm.isShow">
							<el-radio
								v-for="item in this.$dictUtils.getDictList(
									'show_hide'
								)"
								:value="item.value"
								:key="item.id"
								>{{ item.label }}</el-radio
							>
						</el-radio-group>
					</el-form-item>
					<el-form-item
						v-if="inputForm.type == '1'"
						label="固定在标签栏"
						prop="affix"
					>
						<el-radio-group v-model="inputForm.affix">
							<el-radio value="1">是</el-radio>
							<el-radio value="0">否</el-radio>
						</el-radio-group>
					</el-form-item>
					<el-form-item
						v-if="inputForm.type == '1'"
						label="隐藏面包屑"
						prop="hiddenBreadcrumb"
					>
						<el-radio-group v-model="inputForm.hiddenBreadcrumb">
							<el-radio value="1">是</el-radio>
							<el-radio value="0">否</el-radio>
						</el-radio-group>
					</el-form-item>
					<el-form-item
						v-if="inputForm.type !== '0' && inputForm.type !== '3'"
						label="授权标识"
						prop="permission"
					>
						<el-input
							v-model="inputForm.permission"
							maxlength="50"
							placeholder="多个用逗号分隔, 如: user:list,user:create"
						></el-input>
					</el-form-item>
					<el-form-item label="备注" prop="remarks">
						<el-input
							v-model="inputForm.remarks"
							maxlength="200"
							type="textarea"
							:rows="2"
							placeholder="备注"
						></el-input>
					</el-form-item>
					<el-form-item>
						<el-button
							type="primary"
							@click="save"
							v-if="hasPermission('sys:menu:edit')"
							:loading="loading"
							>保 存</el-button
						>
					</el-form-item>
				</el-form>
			</pane>
			<pane size="50">
				<data-rule-list
					:menuId="inputForm.id"
					ref="dataRuleList"
				></data-rule-list>
			</pane>
		</splitpanes>
	</template>
</template>

<script>
import { Splitpanes, Pane } from "splitpanes";
import "splitpanes/dist/splitpanes.css";
import iconSelect from "@/components/iconSelect";
import DataRuleList from "./DataRuleList";
import menuService from "@/api/sys/menuService";
import userService from "@/api/sys/userService";
export default {
	components: {
		Splitpanes,
		Pane,
		iconSelect,
		DataRuleList,
	},
	props: {
		menu: { type: Object, default: () => {} },
	},
	data() {
		return {
			typeList: ["目录", "菜单", "按钮", "路由"],
			inputForm: {
				id: "",
				type: "1",
				name: "",
				parent: {
					id: "",
					name: "",
				},
				href: "",
				permission: "",
				icon: "",
				remarks: "",
				target: "",
				isShow: "1",
				affix: "0",
				hiddenBreadcrumb: "0",
			},
			menuOptions: [],
			menuProps: {
				value: "id",
				label: "title",
				checkStrictly: true,
			},
			rules: {
				name: [
					{
						required: true,
						message: "菜单名称不能为空",
						trigger: "blur",
					},
				],
				"parent.id": [
					{
						required: true,
						message: "上级菜单不能为空",
						trigger: "blur",
					},
				],
			},
			loading: false,
		};
	},
	watch: {
		menu: {
			handler() {
				// eslint-disable-next-line no-debugger
				this.menuOptions = this.treeToMap(this.menu);
			},
			deep: true,
		},
	},
	methods: {
		//简单化菜单
		treeToMap(tree) {
			const map = [];
			tree.forEach((item) => {
				var obj = {
					id: item.id,
					parentId: item.parentId,
					title: item.name,
					children:
						item.children && item.children.length > 0
							? this.treeToMap(item.children)
							: null,
				};
				map.push(obj);
			});
			return map;
		},
		//保存
		save() {
			// eslint-disable-next-line no-debugger
			this.loading = true;
			this.$refs["inputForm"].validate((valid) => {
				if (valid) {
					this.loading = true;
					if (this.inputForm.type === "0") {
						this.inputForm.href = "";
					}
					if (
						this.inputForm.type === "2" ||
						this.inputForm.type === "3"
					) {
						this.inputForm.isShow = "0";
					}
					if (this.inputForm.target == undefined) {
						this.inputForm.target = "";
					}
					menuService
						.save(this.inputForm)
						.then(() => {
							this.loading = false;
							this.$message.success("保存菜单成功");
							this.refreshMenu();
						})
						.catch(() => {
							this.loading = false;
						});
				}
			});
		},
		async refreshMenu() {
			let { menuList, permissions, dictList } =
				await userService.getMenus();
			this.$TOOL.data.set("PERMISSIONS", permissions);
			this.$TOOL.data.set("DICT_LIST", dictList);
			this.$TOOL.data.set("MENU", menuList);
		},
		selectIcon() {
			this.$refs.icon.visible = true;
		},
		//表单注入数据
		setData(data, pid) {
			this.inputForm = data;
			this.inputForm.parentId = pid;
		},
	},
};
</script>

<style scoped>
h2 {
	font-size: 17px;
	color: #3c4a54;
	padding: 0 0 30px 0;
}
.apilist {
	border-left: 1px solid #eee;
}
</style>
