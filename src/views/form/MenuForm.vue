<template>
	<div>
		<v-dialog
			title="创建菜单"
			:close-on-click-modal="false"
			v-model="visible"
		>
			<el-form
				:model="inputForm"
				ref="inputForm"
				@keyup.enter="doSubmit()"
				label-width="120px"
				@submit.prevent
			>
				<el-form-item
					label="上级菜单"
					prop="parent.id"
					:rules="[
						{
							required: true,
							message: '请选择上级菜单',
							trigger: 'blur',
						},
					]"
				>
					<el-tree-select
						ref="menuParentTree"
						value-key="id"
						:props="{
							label: 'name', // 显示名称
							children: 'children', // 子级字段名
						}"
						:data="menuList"
						v-model="inputForm.parent.id"
						check-strictly
						style="width: 100%"
						:clearable="true"
						:accordion="true"
					/>
				</el-form-item>
				<el-form-item
					label="名称"
					prop="name"
					:rules="[
						{
							required: true,
							message: '名称不能为空',
							trigger: 'blur',
						},
					]"
				>
					<el-input
						maxlength="200"
						v-model="inputForm.name"
						placeholder="菜单名称"
					></el-input>
				</el-form-item>
				<el-form-item
					v-if="inputForm.type !== '2'"
					label="菜单图标"
					prop="icon"
				>
					<icon-select
						v-model="inputForm.icon"
						clearable
					></icon-select>
				</el-form-item>
				<data-rule-list
					ref="dataRuleList"
					:form="form"
				></data-rule-list>
			</el-form>
			<template #footer>
				<span class="dialog-footer">
					<el-button @click="visible = false" icon="circle-close"
						>关闭</el-button
					>
					<el-button
						type="primary"
						icon="circle-check"
						@click="doSubmit()"
						>确定</el-button
					>
				</span>
			</template>
		</v-dialog>
	</div>
</template>

<script>
import iconSelect from "@/components/iconSelect";
import DataRuleList from "./DataRuleList";
import menuService from "@/api/sys/menuService";
import makeFormService from "@/api/form/makeFormService";
import userService from "@/api/sys/userService";
export default {
	data() {
		return {
			visible: false,
			menuList: [],
			form: {},
			menuListTreeProps: {
				label: "name",
				children: "children",
			},
			inputForm: {
				parent: {
					id: "",
				},
				name: "",
				id: "",
				icon: "",
				dataRuleDTOList: [],
				formId: "",
			},
		};
	},
	components: {
		DataRuleList,
		iconSelect,
	},
	methods: {
		init(form) {
			this.form = form;
			this.inputForm.name = form.name;
			this.inputForm.formId = form.id;
			menuService.treeData().then((data) => {
				this.menuList = data;
			});
			this.visible = true;
			this.$nextTick(() => {
				this.$refs["inputForm"].resetFields();
				this.$refs.dataRuleList.dataRuleDTOList = [];
			});
		},
		// 图标选中
		iconActiveHandle(iconName) {
			this.inputForm.icon = iconName;
		},
		// 刷新菜单
		async refreshMenu() {
			let { menuList, permissions, dictList } =
				await userService.getMenus();
			this.$TOOL.data.set("PERMISSIONS", permissions);
			this.$TOOL.data.set("DICT_LIST", dictList);
			this.$TOOL.data.set("MENU", menuList);
		},
		// 表单提交
		doSubmit() {
			this.$refs["inputForm"].validate((valid) => {
				if (valid) {
					this.inputForm.dataRuleDTOList =
						this.$refs.dataRuleList.dataRuleDTOList;
					makeFormService.createMenu(this.inputForm).then((data) => {
						this.$message.success({
							dangerouslyUseHTMLString: true,
							message: data,
						});
						this.visible = false;
						this.refreshMenu();
					});
				}
			});
		},
	},
};
</script>
