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
						:default-expanded-keys="['1']"
						style="width: 100%"
						check-strictly
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
						v-noMoreClick
						>确定</el-button
					>
				</span>
			</template>
		</v-dialog>
	</div>
</template>

<script>
import iconSelect from "@/components/iconSelect";
import menuService from "@/api/sys/menuService";
import userService from "@/api/sys/userService";
export default {
	data() {
		return {
			visible: false,
			menuList: [],
			inputForm: {
				parent: {
					id: "",
				},
				name: "",
				href: "",
				icon: "",
				isShow: "1",
				affix: "2",
				type: "1",
				target: "",
			},
		};
	},
	components: {
		iconSelect,
	},
	methods: {
		init(url, name, target) {
			this.inputForm.href = url;
			this.inputForm.name = name;
			this.inputForm.target = target;
			menuService.treeData().then((data) => {
				this.menuList = data;
			});
			this.visible = true;
			this.$nextTick(() => {
				this.$refs["inputForm"].resetFields();
			});
		},
		// 图标选中
		iconActiveHandle(iconName) {
			this.inputForm.icon = iconName;
		},
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
					let inputForm = JSON.parse(JSON.stringify(this.inputForm));
					inputForm.href =
						inputForm.href + "&title=" + inputForm.name;
					menuService.save(inputForm).then(() => {
						this.$message.success({
							dangerouslyUseHTMLString: true,
							message: "创建菜单成功!",
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
