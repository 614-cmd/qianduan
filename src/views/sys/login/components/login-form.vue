<template>
	<div class="login-form-wrapper">
		<div class="login-form-title">登录 JeePlus</div>
		<div class="login-form-sub-title">请使用账号密码登录</div>
		<div class="login-form-error-msg">{{ errorMessage }}</div>
		<el-form
			ref="loginForm"
			:model="inputForm"
			size="large"
			:rules="rules"
			class="login-form"
			@keyup.enter="login()"
			@submit.prevent
		>
			<el-form-item prop="username">
				<el-input
					prefix-icon="user"
					placeholder="请输入账号"
					clearable
					v-model="inputForm.username"
				></el-input>
			</el-form-item>
			<el-form-item prop="password">
				<el-input
					type="password"
					prefix-icon="lock"
					placeholder="请输入密码"
					clearable
					v-model="inputForm.password"
				></el-input>
			</el-form-item>

			<el-row :gutter="10">
				<el-col :span="16">
					<el-form-item prop="code">
						<el-input
							placeholder="请输入验证码"
							v-model="inputForm.code"
							text
							clearable
						></el-input>
					</el-form-item>
				</el-col>
				<el-col :span="8"
					><el-image
						@click="getCaptcha"
						:src="captchaImg"
						style="height: 34px"
					></el-image
				></el-col>
			</el-row>

			<el-form-item>
				<el-button
					type="primary"
					:loading="loading"
					class="login-submit"
					@click="login"
					>登录 Jeeplus</el-button
				>
			</el-form-item>
			<el-form-item> </el-form-item>
		</el-form>
	</div>
</template>

<script>
import config from "@/config";
import loginService from "@/api/auth/loginService";
import userService from "@/api/sys/userService";
export default {
	data() {
		return {
			time: "",
			logo: "",
			productName: "",
			loading: false,
			captchaImg: "",
			inputForm: {
				username: "admin",
				password: "admin",
				uuid: "",
				captcha: "",
			},
			rules: {
				username: [
					{
						required: true,
						message: "帐号不能为空",
						trigger: "blur",
					},
				],
				password: [
					{
						required: true,
						message: "密码不能为空",
						trigger: "blur",
					},
				],
				code: [
					{
						required: true,
						message: "验证码不能为空",
						trigger: "blur",
					},
				],
			},
			captchaPath: "",
		};
	},
	created() {
		this.$cookies.remove(config.TOKEN);
		this.$TOOL.data.remove("DICT_LIST");
		this.$TOOL.data.remove(config.TOKEN);
		this.$TOOL.data.remove("USER_INFO");
		this.$TOOL.data.remove("MENU");
		this.$TOOL.data.remove("PERMISSIONS");
		this.$TOOL.data.remove("grid");
		this.$store.commit("clearViewTags");
		this.$store.commit("clearKeepLive");
		this.$store.commit("clearIframeList");
	},
	mounted() {
		this.getCaptcha();
	},
	computed: {
		appName() {
			return this.$store.state.global.appName;
		},
	},
	methods: {
		// 获取验证码
		getCaptcha() {
			loginService.getCode().then((data) => {
				// 后端返回的是 { codeImg: "base64字符串", uuid: "xxx" }
				this.captchaImg = "data:image/png;base64," + data.codeImg;
				this.inputForm.uuid = data.uuid;
			}).catch((error) => {
				console.error('验证码加载失败:', error);
				// 降级方案：直接请求图片
				this.captchaImg = '/sys/getCode?t=' + new Date().getTime();
			});
		},
		// 提交表单
		async login() {
			this.$refs.loginForm.validate((valid) => {
				if (valid) {
					this.loading = true;
					loginService
						.login(this.inputForm)
						.then(async (loginResp) => {
							this.$TOOL.data.set(config.TOKEN, loginResp.token);
							this.$cookies.set(
								config.TOKEN,
								loginResp.token,
								-1
							);
							let { user } = await userService.info();
							this.$TOOL.data.set("USER_INFO", user);
							this.$store.commit("user/updateUser", user);
							let { menuList, permissions, dictList } =
								await userService.getMenus();
							if (menuList.length == 0) {
								this.loading = false;
								this.$alert(
									"当前用户无任何菜单权限，请联系系统管理员",
									"无权限访问",
									{
										type: "error",
										center: true,
									}
								);
								return false;
							}
							this.$TOOL.data.set("MENU", menuList);
							this.$TOOL.data.set("PERMISSIONS", permissions);
							this.$TOOL.data.set("DICT_LIST", dictList);
							this.$notify({
								title: "登录成功",
								message: `欢迎回来！<br/>你上次的登录IP是 ${loginResp.oldLoginIp}，登录时间是 ${loginResp.oldLoginDate}。`,
								dangerouslyUseHTMLString: true,
								duration: 10000,
								type: "success",
							});
							this.$router.replace({
								path: "/",
							});
						})
						.catch(() => {
							this.loading = false;
							this.getCaptcha();
						});
				}
			});
		},
	},
};
</script>

<style lang="less" scoped>
.login_config {
	position: absolute;
	top: 60px;
	right: 60px;
}
.login-form {
	&-wrapper {
		width: 400px;
	}

	&-title {
		text-align: center;
		color: #475055;
		font-weight: 500;
		font-size: 24px;
		line-height: 32px;
	}

	&-sub-title {
		text-align: center;
		color: #86909c;
		font-size: 14px;
		line-height: 34px;
	}

	&-error-msg {
		height: 32px;
		color: rgb(var(--red-6));
		line-height: 32px;
	}

	&-password-actions {
		display: flex;
		justify-content: space-between;
	}

	&-register-btn {
		color: #86909c !important;
	}
	.login-submit {
		width: 100%;
		color: #fff;
		font-size: 16px;
		background-color: #1890ff;
		border-color: #1890ff;
	}
}
</style>
