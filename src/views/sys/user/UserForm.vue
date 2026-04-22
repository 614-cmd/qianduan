<template>
	<v-dialog :title="title" v-model="visible">
		<el-form
			:model="inputForm"
			ref="inputForm"
			@keyup.enter="doSubmit()"
			label-width="120px"
			v-loading="loading"
			:class="method === 'view' ? 'readonly' : ''"
			:disabled="method === 'view'"
			@submit.prevent
		>
			<el-divider content-position="left"
				><el-icon> <lock /></el-icon> 登录信息</el-divider
			>
			<el-row :gutter="15">
				<el-col :span="12">
					<el-form-item
						label="账号"
						prop="loginName"
						:rules="[
							{
								required: true,
								message: '登录名不能为空',
								trigger: 'blur',
							},
						]"
					>
						<el-input
							v-model="inputForm.loginName"
							maxlength="50"
							placeholder=""
						></el-input>
					</el-form-item>
				</el-col>
				<el-col :span="12">
					<el-form-item
						label="密码:"
						prop="newPassword"
						:rules="
							inputForm.id
								? []
								: [
										{
											required: true,
											message: '密码不能为空',
											trigger: 'blur',
										},
								  ]
						"
					>
						<el-input
							v-model="inputForm.newPassword"
							maxlength="50"
							placeholder="若不修改，请留空"
							show-password
						></el-input>
					</el-form-item>
				</el-col>
				<el-col :span="12">
					<el-form-item
						label="确认密码"
						prop="confirmNewPassword"
						:rules="
							inputForm.id
								? [
										{
											validator: validatePass2,
											trigger: 'blur',
										},
								  ]
								: [
										{
											required: true,
											message: '确认密码不能为空',
											trigger: 'blur',
										},
										{
											validator: validatePass2,
											trigger: 'blur',
										},
								  ]
						"
					>
						<el-input
							v-model="inputForm.confirmNewPassword"
							maxlength="50"
							placeholder=""
							show-password
						></el-input>
					</el-form-item>
				</el-col>
				<el-col :span="12">
					<el-form-item
						label="是否允许登录"
						prop="loginFlag"
						:rules="[
							{
								required: true,
								message: '必须选择',
								trigger: 'blur',
							},
						]"
					>
						<el-radio-group v-model="inputForm.loginFlag">
							<el-radio
								v-for="item in this.$dictUtils.getDictList(
									'yes_no'
								)"
								:value="item.value"
								:key="item.id"
								>{{ item.label }}</el-radio
							>
						</el-radio-group>
					</el-form-item>
				</el-col>
			</el-row>
			<el-divider content-position="left"
				><el-icon> <user /></el-icon> 身份信息</el-divider
			>
			<el-row :gutter="15">
				<el-col :span="12">
					<el-form-item
						label="姓名"
						:rules="[
							{
								required: true,
								message: '姓名不能为空',
								trigger: 'blur',
							},
						]"
						prop="name"
					>
						<el-input
							v-model="inputForm.name"
							maxlength="50"
							placeholder=""
						></el-input>
					</el-form-item>
				</el-col>
				<el-col :span="12">
					<el-form-item
						label="手机"
						prop="mobile"
						:rules="[
							{ validator: validator.isMobile, trigger: 'blur' },
						]"
					>
						<el-input
							v-model="inputForm.mobile"
							maxlength="50"
							placeholder=""
						></el-input>
					</el-form-item>
				</el-col>
				<el-col :span="12">
					<el-form-item
						label="电话"
						prop="phone"
						:rules="[
							{ validator: validator.isPhone, trigger: 'blur' },
						]"
					>
						<el-input
							v-model="inputForm.phone"
							maxlength="50"
							placeholder=""
						></el-input>
					</el-form-item>
				</el-col>
				<el-col :span="12">
					<el-form-item
						label="邮箱"
						prop="email"
						:rules="[
							{
								type: 'email',
								message: '请输入正确的邮箱地址',
								trigger: 'blur',
							},
						]"
					>
						<el-input
							v-model="inputForm.email"
							maxlength="50"
							placeholder=""
						></el-input>
					</el-form-item>
				</el-col>
			</el-row>
			<el-divider content-position="left"
				><el-icon> <postcard /></el-icon> 岗位信息</el-divider
			>
			<el-row :gutter="15">
				<el-col :span="12">
					<el-form-item
						prop="companyDTO.id"
						:rules="[
							{
								required: true,
								message: '公司不能为空',
								trigger: 'blur',
							},
						]"
						label="公司"
					>
						<el-tree-select
							ref="companyTree"
							value-key="id"
							:props="{
								label: 'name', // 显示名称
								children: 'children', // 子级字段名
							}"
							@change="handleChangeCompany"
							:data="companyTreeData"
							v-model="inputForm.companyDTO.id"
							check-strictly
							style="width: 100%"
							:clearable="true"
							:accordion="true"
						>
							<template #default="{ node, data }">
								<span
									class="custom-tree-node el-tree-node__label"
								>
									<span class="label">
										<el-icon class="m-r-5">
											<qiye
												v-if="data.type === '1'"
											></qiye>
											<bumen v-else></bumen>
										</el-icon>
										{{ node.label }}
									</span>
								</span>
							</template>
						</el-tree-select>
					</el-form-item>
				</el-col>
				<el-col :span="12">
					<el-form-item
						prop="officeDTO.id"
						:rules="[
							{
								required: true,
								message: '部门不能为空',
								trigger: 'blur',
							},
						]"
						label="部门"
					>
						<el-tree-select
							ref="officeTree"
							value-key="id"
							:props="{
								label: 'name', // 显示名称
								children: 'children', // 子级字段名
							}"
							:data="officeTreeData"
							v-model="inputForm.officeDTO.id"
							check-strictly
							style="width: 100%"
							:clearable="true"
							:accordion="true"
						>
							<template #default="{ node, data }">
								<span
									class="custom-tree-node el-tree-node__label"
								>
									<span class="label">
										<el-icon class="m-r-5">
											<qiye
												v-if="data.type === '1'"
											></qiye>
											<bumen v-else></bumen>
										</el-icon>
										{{ node.label }}
									</span>
								</span>
							</template>
						</el-tree-select>
					</el-form-item>
				</el-col>

				<el-col :span="12">
					<el-form-item
						label="工号"
						prop="no"
						:rules="[
							{
								required: true,
								message: '工号不能为空',
								trigger: 'blur',
							},
						]"
					>
						<el-input
							v-model="inputForm.no"
							maxlength="50"
							placeholder=""
						></el-input>
					</el-form-item>
				</el-col>
				<el-col :span="12">
					<el-form-item label="岗位" prop="postIdList">
						<el-select
							v-model="inputForm.postIdList"
							style="width: 100%"
							multiple
							placeholder="请选择"
						>
							<el-option
								v-for="item in postList"
								:key="item.id"
								:label="item.name"
								:value="item.id"
							>
							</el-option>
						</el-select>
					</el-form-item>
				</el-col>
				<el-col :span="12">
					<el-form-item
						label="角色"
						prop="roleIdList"
						:rules="[
							{
								required: true,
								message: '角色不能为空',
								trigger: 'blur',
							},
						]"
					>
						<el-select
							v-model="inputForm.roleIdList"
							style="width: 100%"
							multiple
							placeholder="请选择"
						>
							<el-option
								v-for="role in roleList"
								:key="role.id"
								:label="role.name"
								:value="role.id"
							>
							</el-option>
						</el-select>
					</el-form-item>
				</el-col>
				<el-col :span="12">
					<el-form-item label="备注" prop="remarks">
						<el-input
							type="textarea"
							v-model="inputForm.remarks"
							maxlength="200"
							placeholder=""
						></el-input>
					</el-form-item>
				</el-col>
			</el-row>
		</el-form>

		<!-- <template #footer>
      <span class="dialog-footer">
        <el-button  @click="visible = false" icon="circle-close">关闭</el-button>
        <el-button  type="primary" v-if="method != 'view'" @click="doSubmit()" icon="circle-check" v-noMoreClick>确定</el-button>
      </span>
     </template> -->
		<template #footer>
			<span class="dialog-footer">
				<el-button @click="visible = false" icon="circle-close"
					>关闭</el-button
				>
				<el-button
					type="primary"
					v-show="method != 'view'"
					@click="doSubmit()"
					icon="circle-check"
					>确定</el-button
				>
			</span>
		</template>
	</v-dialog>
</template>

<script>
import userService from "@/api/sys/userService";
import postService from "@/api/sys/postService";
import roleService from "@/api/sys/roleService";
import officeService from "@/api/sys/officeService";
export default {
	data() {
		return {
			visible: false,
			loading: false,
			title: "",
			method: "",
			roleList: [],
			postList: [],
			companyTreeData: [],
			officeTreeData: [],
			inputForm: {
				id: "",
				companyDTO: {
					// 归属公司
					id: "",
					name: "",
				},
				officeDTO: {
					id: "",
					name: "",
				},
				roleIdList: [],
				postIdList: [],
				loginName: "", // 登录名
				no: "", // 工号
				name: "", // 姓名
				email: "", // 邮箱
				phone: "", // 电话
				mobile: "", // 手机
				loginFlag: "1", // 是否允许登陆
				photo: "", // 头像
				qrCode: "", // 二维码
				oldLoginName: "", // 原登录名
				newPassword: "", // 新密码
				confirmNewPassword: "",
				sign: "", // 签名
				remarks: "", // 备注
			},
		};
	},
	methods: {
		init(method, id) {
			this.method = method;
			if (method === "add") {
				this.title = `新建用户`;
			} else if (method === "edit") {
				this.title = "修改用户";
			} else if (method === "view") {
				this.title = "查看用户";
			}
			this.visible = true;
			this.$nextTick(() => {
				if (this.$refs.inputForm !== undefined) {
					this.$refs.inputForm.resetFields();
				}
				Promise.all([
					roleService.list({ current: 1, size: -1 }),
					postService.list({ current: 1, size: -1 }),
					officeService.treeData({ type: 1 }),
				]).then((result) => {
					this.roleList = result[0].records;
					this.postList = result[1].records;
					this.companyTreeData = result[2];
				});
				this.inputForm.oldLoinName = "";
				this.inputForm.id = id;
				if (method === "edit" || method === "view") {
					// 修改或者查看
					userService.queryById(this.inputForm.id).then((data) => {
						this.inputForm = this.recover(this.inputForm, data);
						this.inputForm.oldLoginName = this.inputForm.loginName;
						officeService
							.treeData({
								type: 2,
								parentId: this.inputForm.companyDTO.id,
							})
							.then((data) => {
								this.officeTreeData = data;
							});
					});
				}
			});
		},
		handleChangeCompany(companyId) {
			officeService
				.treeData({ type: 2, parentId: companyId })
				.then((data) => {
					this.officeTreeData = data;
					this.inputForm.officeDTO.id = "";
					this.inputForm.officeDTO.name = "";
				});
		},
		// 表单提交
		doSubmit() {
			this.$refs["inputForm"].validate((valid) => {
				if (valid) {
					this.loading = true;
					userService
						.save(this.inputForm)
						.then((data) => {
							this.loading = false;
							this.visible = false;
							this.$message.success(data);
							this.$emit("refreshDataList");
						})
						.catch(() => {
							this.loading = false;
						});
				}
			});
		},
		validatePass2(rule, value, callback) {
			if (value !== this.inputForm.newPassword) {
				callback(new Error("两次输入密码不一致!"));
			} else {
				callback();
			}
		},
	},
};
</script>
<style scoped>
.avatar {
	height: 100px;
}
.el-divider__text {
	font-weight: bold;
	font-size: 16px;
}
</style>
