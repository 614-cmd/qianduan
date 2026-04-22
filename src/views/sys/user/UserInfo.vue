<template>
	<el-container class="userInfo">
		<el-aside class="aside">
			<el-card shadow="never" body-style="height:600px">
				<template #header>
					<div class="card-header">
						<span>
							<div class="person-bg">
								<el-icon size="14"
									><user-filled></user-filled
								></el-icon>
							</div>
							<label class="person-text">基本信息</label>
						</span>
					</div>
				</template>
				<div class="user-info">
					<div class="user-info-top">
						<el-avatar :size="80" :src="userInfo.photo"></el-avatar>
						<h2>{{ userInfo.name || "-" }}</h2>
						<p>{{ userInfo.sign || "无签名" }}</p>
						<el-button type="primary" round icon="collection-tag">{{
							userInfo.loginName
						}}</el-button>
					</div>
					<div class="user-info-main">
						<el-descriptions :column="1">
							<el-descriptions-item>
								<template #label>
									<el-icon :size="14"><Iphone /></el-icon>
								</template>
								{{ userInfo.mobile }}
							</el-descriptions-item>
							<el-descriptions-item>
								<template #label>
									<el-icon :size="14"><phone /></el-icon>
								</template>
								{{ userInfo.phone }}
							</el-descriptions-item>
							<el-descriptions-item>
								<template #label>
									<el-icon :size="14"><youjian /></el-icon>
								</template>
								{{ userInfo.email }}
							</el-descriptions-item>
							<el-descriptions-item>
								<template #label>
									<el-icon :size="14"><location /></el-icon>
								</template>
								{{ userInfo.companyDTO?.name }}
							</el-descriptions-item>
							<el-descriptions-item>
								<template #label>
									<el-icon :size="14"
										><office-building
									/></el-icon>
								</template>
								{{ userInfo.officeDTO?.name }}
							</el-descriptions-item>
							<el-descriptions-item>
								<template #label>
									<el-icon :size="14"><coin /></el-icon>
								</template>
								{{ userInfo.roleNames }}
							</el-descriptions-item>
						</el-descriptions>
					</div>
				</div>
			</el-card>
		</el-aside>
		<el-main style="padding: 0">
			<el-card shadow="never" body-style="height:600px">
				<template #header>
					<div class="card-header">
						<span>
							<div class="person-bg">
								<el-icon size="14"><bianji></bianji></el-icon>
							</div>
							<label class="person-text">资料修改</label>
						</span>
					</div>
				</template>
				<el-tabs tab-position="top">
					<el-tab-pane label="基本信息">
						<el-form
							:model="generalForm"
							ref="generalForm"
							label-width="100px"
						>
							<el-form-item
								label="姓名"
								:rules="[
									{
										required: true,
										message: '必填项不能为空',
										trigger: 'blur',
									},
								]"
								prop="name"
							>
								<el-input v-model="generalForm.name"></el-input>
							</el-form-item>
							<el-form-item label="签名" prop="sign">
								<el-input v-model="generalForm.sign"></el-input>
							</el-form-item>
							<el-form-item label="公司" prop="companyDTO.name">
								<el-input
									v-model="generalForm.companyDTO.name"
									:disabled="true"
								></el-input>
							</el-form-item>
							<el-form-item label="部门" prop="officeDTO.name">
								<el-input
									v-model="generalForm.officeDTO.name"
									:disabled="true"
								></el-input>
							</el-form-item>
							<el-form-item label="备注" prop="remarks">
								<el-input
									v-model="generalForm.remarks"
								></el-input>
							</el-form-item>
							<el-form-item>
								<el-button
									el-button
									type="primary"
									@click="submitGeneralForm()"
									>保存</el-button
								>
							</el-form-item>
						</el-form>
					</el-tab-pane>
					<el-tab-pane label="上传头像">
						<el-form
							:model="photoForm"
							ref="photoForm"
							label-width="100px"
						>
							<el-form-item prop="photo">
								<image-select
									v-model="photoForm.photo"
									round
									icon="el-icon-avatar"
									title="头像"
								></image-select>
							</el-form-item>
							<el-form-item>
								<el-button
									style="width: 148px"
									type="primary"
									@click="submitPhotoForm()"
									>保存</el-button
								>
							</el-form-item>
						</el-form>
					</el-tab-pane>
					<el-tab-pane label="联系方式">
						<el-form
							:model="concatForm"
							ref="concatForm"
							label-width="100px"
						>
							<el-form-item
								label="邮箱"
								:rules="[
									{
										type: 'email',
										message: '请输入正确的邮箱地址',
										trigger: 'blur',
									},
								]"
								prop="email"
							>
								<el-input v-model="concatForm.email"></el-input>
							</el-form-item>
							<el-form-item
								label="手机"
								prop="mobile"
								:rules="[
									{
										validator: validator.isMobile,
										trigger: 'blur',
									},
								]"
							>
								<el-input
									v-model="concatForm.mobile"
								></el-input>
							</el-form-item>
							<el-form-item
								label="电话"
								prop="phone"
								:rules="[
									{
										validator: validator.isPhone,
										trigger: 'blur',
									},
								]"
							>
								<el-input v-model="concatForm.phone"></el-input>
							</el-form-item>
							<el-form-item>
								<el-button
									type="primary"
									@click="submitConcatForm()"
									>保存</el-button
								>
							</el-form-item>
						</el-form>
					</el-tab-pane>
					<el-tab-pane label="修改密码">
						<el-form
							:model="pwdForm"
							ref="pwdForm"
							label-width="100px"
						>
							<el-form-item
								label="旧密码"
								:rules="[
									{
										required: true,
										message: '必填项不能为空',
										trigger: 'blur',
									},
								]"
								prop="oldPassword"
							>
								<el-input
									v-model="pwdForm.oldPassword"
									show-password
								></el-input>
							</el-form-item>
							<el-form-item
								label="新密码"
								:rules="[
									{
										required: true,
										message: '必填项不能为空',
										trigger: 'blur',
									},
								]"
								prop="newPassword"
							>
								<el-input
									v-model="pwdForm.newPassword"
									show-password
								></el-input>
							</el-form-item>
							<el-form-item
								label="确认新密码"
								:rules="rule"
								prop="confirmNewPassword"
							>
								<el-input
									v-model="pwdForm.confirmNewPassword"
									show-password
								></el-input>
							</el-form-item>
							<el-form-item>
								<el-button
									type="primary"
									@click="submitPwdForm()"
									>保存</el-button
								>
							</el-form-item>
						</el-form>
					</el-tab-pane>
					<el-tab-pane label="我的日志">
						<el-menu
							default-active="1"
							mode="horizontal"
							@select="changeLog"
						>
							<el-menu-item index="1">
								<template #title>登陆日志</template>
							</el-menu-item>
							<el-menu-item index="2">
								<template #title>访问日志</template>
							</el-menu-item>
						</el-menu>
						<vxe-table
							auto-resize
							resizable
							height="400px"
							:loading="loading"
							size="mini"
							ref="logTable"
							show-header-overflow
							show-overflow
							highlight-hover-row
							:menu-config="{}"
							:print-config="{}"
							:import-config="{}"
							:export-config="{}"
							@sort-change="sortChangeHandle"
							:sort-config="{ remote: true }"
							:data="dataList"
							:checkbox-config="{}"
						>
							<vxe-column type="seq" width="40"></vxe-column>
							<vxe-column
								title="操作菜单"
								field="title"
								sortable
							></vxe-column>
							<vxe-column
								title="耗时(毫秒)"
								field="recordTime"
								sortable
							></vxe-column>
							<vxe-column
								title="请求参数"
								field="params"
								sortable
							></vxe-column>
							<vxe-column
								title="返回结果"
								field="result"
								sortable
							></vxe-column>
							<vxe-column
								title="公司"
								field="createBy.companyDTO.name"
								sort-by="c.name"
								sortable
							></vxe-column>
							<vxe-column
								title="部门"
								field="createBy.officeDTO.name"
								sort-by="o.name"
								sortable
							></vxe-column>
							<vxe-column
								title="用户"
								field="createBy.name"
								sort-by="u.name"
								sortable
							></vxe-column>
							<vxe-column
								title="IP地址"
								field="remoteAddr"
								sortable
							></vxe-column>
							<vxe-column
								title="访问时间"
								field="createTime"
								sort-by="a.create_time"
								sortable
							></vxe-column>
						</vxe-table>
						<vxe-pager
							background
							size="mini"
							:current-page="tablePage.currentPage"
							:page-size="tablePage.pageSize"
							:total="tablePage.total"
							:page-sizes="[
								10,
								20,
								100,
								1000,
								{ label: '全量数据', value: 1000000 },
							]"
							:layouts="[
								'PrevPage',
								'JumpNumber',
								'NextPage',
								'FullJump',
								'Sizes',
								'Total',
							]"
							@page-change="currentChangeHandle"
						>
						</vxe-pager>
					</el-tab-pane>
				</el-tabs>
			</el-card>
		</el-main>
	</el-container>
</template>

<script>
import colorTool from "@/utils/color";
import userService from "@/api/sys/userService";
import logService from "@/api/sys/logService";
export default {
	data() {
		var validatePass2 = (rule, value, callback) => {
			if (value !== this.pwdForm.newPassword) {
				callback(new Error("两次输入密码不一致!"));
			} else {
				callback();
			}
		};
		return {
			type: "1",
			colorList: [
				"#409EFF",
				"#009688",
				"#536dfe",
				"#ff5c93",
				"#c62f2f",
				"#fd726d",
			],
			config: {
				lang: this.$TOOL.data.get("APP_LANG") || this.$CONFIG.LANG,
				theme: this.$TOOL.data.get("APP_THEME") || "default",
				colorPrimary:
					this.$TOOL.data.get("APP_COLOR") ||
					this.$CONFIG.COLOR ||
					"#409EFF",
			},
			userInfo: {},
			generalForm: {
				id: "",
				name: "",
				sign: "",
				loginName: "",
				companyDTO: {
					name: "",
				},
				officeDTO: {
					name: "",
				},
				remarks: "",
			},
			concatForm: {
				id: "",
				email: "",
				phone: "",
				mobile: "",
			},
			photoForm: {
				id: "",
				photo: "",
			},
			pwdForm: {
				id: "",
				oldPassword: "",
				newPassword: "",
				confirmNewPassword: "",
			},
			dataList: [],
			tablePage: {
				total: 0,
				currentPage: 1,
				pageSize: 10,
				orders: [{ column: "a.create_time", asc: false }],
			},
			loading: false,
			rule: [
				{ required: true, message: "必填项不能为空", trigger: "blur" },
				{ validator: validatePass2, trigger: "blur" },
			],
		};
	},
	created() {
		this.userInfo = this.$TOOL.data.get("USER_INFO");
		var userId = this.userInfo.id;
		if (userId) {
			userService.queryById(userId).then((data) => {
				this.recover(this.generalForm, data);
				this.recover(this.concatForm, data);
				this.recover(this.photoForm, data);
			});
			this.refreshList();
		}
	},
	watch: {
		"config.theme"(val) {
			document.body.setAttribute("data-theme", val);
			this.$TOOL.data.set("APP_THEME", val);
		},
		"config.lang"(val) {
			this.$i18n.locale = val;
			this.$TOOL.data.set("APP_LANG", val);
		},
		"config.colorPrimary"(val) {
			colorTool.setAppColors(val);
			this.$TOOL.data.set("APP_COLOR", val);
		},
	},
	methods: {
		async submitGeneralForm() {
			let valid = await this.$refs["generalForm"].validate();
			if (valid) {
				const loading = this.$loading();
				let data = await userService.saveInfo(this.generalForm);
				let { user } = await userService.info();
				this.userInfo = user;
				this.$TOOL.data.set("USER_INFO", user);
				loading.close();
				this.$message.success(data);
			}
		},
		async submitConcatForm() {
			let valid = await this.$refs["concatForm"].validate();
			if (valid) {
				const loading = this.$loading();
				let data = await userService.saveInfo(this.concatForm);
				let { user } = await userService.info();
				this.userInfo = user;
				this.$TOOL.data.set("USER_INFO", user);
				loading.close();
				this.$message.success(data);
			}
		},
		async submitPhotoForm() {
			const loading = this.$loading();
			let data = await userService.saveInfo(this.photoForm);
			let { user } = await userService.info();
			this.userInfo = user;
			this.$TOOL.data.set("USER_INFO", user);
			loading.close();
			this.$message.success(data);
		},
		async submitPwdForm() {
			let valid = await this.$refs["pwdForm"].validate();
			if (valid) {
				const loading = this.$loading();
				let data = await userService.savePwd(this.pwdForm);
				loading.close();
				this.$message.success(data);
				await userService.info();
			}
		},
		// 获取数据列表
		refreshList() {
			this.loading = true;
			logService
				.mine({
					current: this.tablePage.currentPage,
					size: this.tablePage.pageSize,
					orders: this.tablePage.orders,
				})
				.then((data) => {
					this.dataList = data.records;
					this.tablePage.total = data.total;
					this.loading = false;
				});
		},
		changeLog(index) {
			this.type = index;
			this.refreshList();
		},
		// 当前页
		currentChangeHandle({ currentPage, pageSize }) {
			this.tablePage.currentPage = currentPage;
			this.tablePage.pageSize = pageSize;
			this.refreshList();
		},
		// 排序
		sortChangeHandle(obj) {
			this.tablePage.orders = [];
			if (obj.order != null) {
				this.tablePage.orders.push({
					column:
						obj.column.sortBy || this.$utils.toLine(obj.property),
					asc: obj.order === "asc",
				});
			} else {
				this.tablePage.orders = [
					{ column: "a.create_time", asc: false },
				];
			}
			this.refreshList();
		},
	},
};
</script>

<style lang="less">
.userInfo {
	padding: 8px 12px;
	.aside {
		width: 500px;
		margin-right: 20px;
	}
	.el-card__header {
		padding: calc(var(--el-card-padding) - 2px) var(--el-card-padding);
		border-bottom: 1px solid var(--el-card-border-color);
		box-sizing: border-box;
	}
	.person-bg {
		color: rgb(24, 144, 255);
		background-color: rgb(230, 247, 255);
		width: 24px;
		height: 24px;
		border-radius: 50%;
		text-align: center;
		vertical-align: middle;
		display: inline-block;
	}
	.person-text {
		font-size: 15px;
		margin-left: 5px;
		font-weight: 400;
	}
}

.adminui-topbar + .userInfo {
	padding: 12px 12px 8px 12px;
}
</style>
