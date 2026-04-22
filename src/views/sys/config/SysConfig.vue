<template>
	<el-main class="page">
		<el-tabs v-model="activeName">
			<el-tab-pane label="登录" name="first">
				<el-card class="box-card">
					<template #header>
						<div class="card-header">
							<span>单一登陆配置</span>
						</div>
					</template>
					<el-form ref="loginFormSetting" :model="loginFormSetting">
						<el-form-item>
							<el-checkbox
								true-label="0"
								false-label="1"
								v-model="loginFormSetting.multiAccountLogin"
								>单一登录</el-checkbox
							>
						</el-form-item>
						<el-form-item>
							<el-radio-group
								v-model="loginFormSetting.singleLoginType"
							>
								<el-radio
									:disabled="
										loginFormSetting.multiAccountLogin ===
										'1'
									"
									value="1"
									>后登陆踢出先登录</el-radio
								>
								<el-radio
									:disabled="
										loginFormSetting.multiAccountLogin ===
										'1'
									"
									value="2"
									>已登陆禁止再登陆</el-radio
								>
							</el-radio-group>
						</el-form-item>
						<el-form-item>
							<el-button
								type="primary"
								@click="doSubmit(loginFormSetting)"
								>保存</el-button
							>
						</el-form-item>
					</el-form>
				</el-card>
			</el-tab-pane>
			<el-tab-pane label="外观" class="sys-config" name="second">
				<el-card class="box-card">
					<template #header>
						<div class="card-header">
							<span>默认外观配置</span>
						</div>
					</template>
					<el-form label-width="150px" :model="themeFormSetting">
						<el-form-item label="产品标题">
							<el-input
								v-model="themeFormSetting.productName"
							></el-input>
						</el-form-item>
						<el-form-item label="产品logo">
							<image-select
								v-model="themeFormSetting.logo"
								title="产品logo"
							></image-select>
						</el-form-item>
						<el-form-item label="主题模式">
							<el-radio-group
								v-model="themeFormSetting.defaultTheme"
							>
								<div class="setting-layout-drawer">
									<el-radio-button value="default">
										<img
											class="thumbnail-layout"
											src="~@/assets/img/assets-setting-light.svg"
										/>
									</el-radio-button>
								</div>
								<div class="setting-layout-drawer">
									<el-radio-button value="dark"
										><img
											class="thumbnail-layout"
											src="~@/assets/img/assets-setting-dark.svg"
									/></el-radio-button>
								</div>
							</el-radio-group>
						</el-form-item>
						<el-form-item label="导航布局">
							<el-radio-group
								v-model="themeFormSetting.defaultLayout"
							>
								<div class="setting-layout-drawer">
									<el-radio-button value="default"
										><img
											class="thumbnail-layout"
											src="~@/assets/img/layout/classic.png"
									/></el-radio-button>
								</div>
								<div class="setting-layout-drawer">
									<el-radio-button value="left"
										><img
											class="thumbnail-layout"
											src="~@/assets/img/layout/left.png"
									/></el-radio-button>
								</div>
								<div class="setting-layout-drawer">
									<el-radio-button value="mix"
										><img
											class="thumbnail-layout"
											src="~@/assets/img/mix.png"
									/></el-radio-button>
								</div>
								<div class="setting-layout-drawer">
									<el-radio-button value="top"
										><img
											class="thumbnail-layout"
											src="~@/assets/img/top.png"
									/></el-radio-button>
								</div>
							</el-radio-group>
						</el-form-item>
						<el-form-item label="主题色">
							<el-radio-group
								v-model="themeFormSetting.defaultColor"
								size="large"
							>
								<el-tooltip
									effect="dark"
									:content="item.key"
									placement="top-start"
									v-for="(item, index) in colorList"
									:key="index"
								>
									<el-radio-button
										:color="item.color"
										:value="item.color"
										class="setting-layout-color-group"
									>
										<div
											class="color-container"
											:style="`background:${item.color}`"
										></div>
									</el-radio-button>
								</el-tooltip>

								<div
									class="setting-layout-drawer"
									style="margin-left: 5px"
								>
									<el-color-picker
										v-model="colorPrimary"
									></el-color-picker>
								</div>
							</el-radio-group>
						</el-form-item>
						<el-form-item label="首页地址">
							<el-input
								v-model="themeFormSetting.homeUrl"
							></el-input>
						</el-form-item>
						<el-form-item>
							<el-button
								type="primary"
								@click="doSubmit(themeFormSetting)"
								>保存</el-button
							>
						</el-form-item>
					</el-form>
				</el-card>
			</el-tab-pane>
			<el-tab-pane label="短信" name="third">
				<el-card class="box-card">
					<template #header>
						<div class="card-header">
							<span
								>阿里大鱼短信配置(<a
									href="https://dayu.aliyun.com"
									target="_blank"
									>官网</a
								>)</span
							>
						</div>
					</template>
					<el-form label-width="150px" :model="smsFormSetting">
						<el-form-item
							label="accessKeyId"
							prop="accessKeyId"
							:rules="[
								{
									required: true,
									message: '必填项不能为空',
									trigger: 'blur',
								},
							]"
						>
							<el-input
								v-model="smsFormSetting.accessKeyId"
								placeholder="此处需要替换成开发者自己的AK(在阿里云访问控制台寻找)"
							></el-input>
						</el-form-item>
						<el-form-item
							label="accessKeySecret"
							prop="accessKeySecret"
							:rules="[
								{
									required: true,
									message: '必填项不能为空',
									trigger: 'blur',
								},
							]"
						>
							<el-input
								v-model="smsFormSetting.accessKeySecret"
								placeholder="此处需要替换成开发者自己的AK(在阿里云访问控制台寻找)"
							></el-input>
						</el-form-item>
						<el-form-item
							label="短信签名"
							prop="signature"
							:rules="[
								{
									required: true,
									message: '必填项不能为空',
									trigger: 'blur',
								},
							]"
						>
							<el-input
								v-model="smsFormSetting.signature"
								placeholder="必填:短信签名-可在短信控制台中找到"
							></el-input>
						</el-form-item>
						<el-form-item
							label="短信模板"
							prop="templateCode"
							:rules="[
								{
									required: true,
									message: '必填项不能为空',
									trigger: 'blur',
								},
							]"
						>
							<el-input
								v-model="smsFormSetting.templateCode"
								placeholder="必填:短信模板编号-可在短信控制台中找到"
							></el-input>
						</el-form-item>
						<el-form-item>
							<el-button
								type="primary"
								@click="doSubmit(smsFormSetting)"
								>保存</el-button
							>
						</el-form-item>
					</el-form>
				</el-card>
			</el-tab-pane>
			<el-tab-pane label="邮箱" name="fourth">
				<el-card class="box-card">
					<template #header>
						<div class="card-header">
							<span>发件人账户</span>
						</div>
					</template>
					<el-form label-width="150px" :model="emailFormSetting">
						<el-form-item
							label="邮箱服务器地址"
							prop="smtp"
							:rules="[
								{
									required: true,
									message: '必填项不能为空',
									trigger: 'blur',
								},
							]"
						>
							<el-input
								v-model="emailFormSetting.smtp"
								placeholder=" 邮件服务器的SMTP地址，默认为smtp.<发件人邮箱后缀>"
							></el-input>
						</el-form-item>
						<el-form-item
							label="邮箱服务器端口"
							prop="port"
							:rules="[
								{
									required: true,
									message: '必填项不能为空',
									trigger: 'blur',
								},
							]"
						>
							<el-input
								v-model="emailFormSetting.port"
								placeholder="邮件服务器的SMTP端口，默认25"
							></el-input>
						</el-form-item>
						<el-form-item
							label="系统邮箱地址"
							prop="mailName"
							:rules="[
								{
									required: true,
									message: '必填项不能为空',
									trigger: 'blur',
								},
							]"
						>
							<el-input
								v-model="emailFormSetting.mailName"
								placeholder="用户名，默认为发件人邮箱前缀"
							></el-input>
						</el-form-item>
						<el-form-item
							prop="mailPassword"
							label="系统邮箱密码"
							:rules="[
								{
									required: true,
									message: '必填项不能为空',
									trigger: 'blur',
								},
							]"
						>
							<el-input
								v-model="emailFormSetting.mailPassword"
								placeholder="密码（注意，某些邮箱需要为SMTP服务单独设置密码，详情查看相关帮助）"
							></el-input>
						</el-form-item>
						<el-form-item>
							<el-button
								type="primary"
								@click="doSubmit(emailFormSetting)"
								>保存</el-button
							>
						</el-form-item>
					</el-form>
				</el-card>
			</el-tab-pane>
		</el-tabs>
	</el-main>
</template>
<script>
import colorTool from "@/utils/color";
import configService from "@/api/sys/configService";
export default {
	data() {
		return {
			activeName: "first",
			loginFormSetting: {
				id: "",
				multiAccountLogin: "1",
				singleLoginType: "1",
			},
			themeFormSetting: {
				id: "",
				defaultTheme: "",
				defaultColor: "#1890FF",
				productName: "",
				logo: "",
				defaultLayout: "",
				homeUrl: "",
			},
			smsFormSetting: {
				id: "",
				accessKeyId: "",
				accessKeySecret: "",
				signature: "",
				templateCode: "",
			},
			emailFormSetting: {
				id: "",
				smtp: "",
				port: "",
				mailName: "",
				mailPassword: "",
			},
			colorList: [
				{
					key: "深湖蓝（默认）",
					color: "#2d8cf0",
					label: "0",
				},
				{
					key: "明青",
					color: "#1ba2c0",
					label: "1",
				},
				{
					key: "薄暮",
					color: "#F5222D",
					label: "2",
				},
				{
					key: "火山",
					color: "#FA541C",
					label: "3",
				},
				{
					key: "日暮",
					color: "#FAAD14",
					label: "4",
				},
				{
					key: "橄榄绿",
					color: "#16b2a3",
					label: "5",
				},
				{
					key: "咖啡色",
					color: "#9a7b71",
					label: "6",
				},
				{
					key: "极客蓝",
					color: "#2F54EB",
					label: "7",
				},
				{
					key: "酱紫",
					color: "#722ED1",
					label: "8",
				},
				{
					key: "天空蓝",
					color: "#3e8df7",
					label: "9",
				},
			],
		};
	},
	mounted() {
		configService.queryById().then((data) => {
			this.loginFormSetting = this.recover(this.loginFormSetting, data);
			this.themeFormSetting = this.recover(this.themeFormSetting, data);
			this.smsFormSetting = this.recover(this.smsFormSetting, data);
			this.emailFormSetting = this.recover(this.emailFormSetting, data);
		});
	},
	methods: {
		doSubmit(form) {
			configService.save(form).then((data) => {
				this.$message.success(data.msg);
				// 布局
				this.$store.commit("SET_layout", data.config.defaultLayout);
				this.$TOOL.data.set("APP_LAYOUT", data.config.defaultLayout);
				// 主题
				document.body.setAttribute(
					"data-theme",
					data.config.defaultTheme
				);
				this.$TOOL.data.set("APP_THEME", data.config.defaultTheme);
				// 主题色
				colorTool.setAppColors(data.config.defaultColor);
				this.$TOOL.data.set("APP_COLOR", data.config.defaultColor);
				// 产品名字
				this.$store.commit("updateAppName", data.config.productName);
				this.$TOOL.data.set("APP_NAME", data.config.productName);
			});
		},
	},
};
</script>
<style lang="less">
.sys-config {
	.setting-layout-color-group {
		.el-radio-button__inner {
			display: inline-flex;
			justify-content: center;
			align-items: center;
			border-radius: 50% !important;
			padding: 3px !important;
		}
	}

	.el-drawer__header {
		padding: 15px;
		border-bottom: 1px solid #e7e7e7;
		border-radius: 2px 2px 0 0;
		margin-bottom: 10px;
	}
	.setting-group-title {
		font-size: 14px;
		line-height: 22px;
		margin: 32px 0 24px 0;
		text-align: left;
		font-style: normal;
		font-weight: 500;
		color: #000000e6;
	}

	.el-form-item__content {
		display: block;
	}

	.setting-container {
		padding: 0px 16px 100px 16px;
		overflow: auto;
		flex: 1;
	}

	.el-radio-group {
		display: inline-flex;
		align-items: center;
		width: fit-content;
	}

	.thumbnail-layout {
		// width: 88px;
		height: 40px;
	}

	.color-container {
		width: 24px;
		height: 24px;
		border-radius: 50%;
		display: inline-block;
	}
	.setting-layout-drawer {
		display: flex;
		flex-direction: column;
		align-items: center;
		margin-bottom: 1px;
	}

	.setting-layout-color-group {
		display: inline-flex;
		width: 36px;
		height: 36px;
		justify-content: center;
		align-items: center;
		border-radius: 50% !important;
		padding: 6px !important;
		border: 2px solid transparent !important;
	}
	.el-radio-button__inner {
		padding: 6px;
	}
	.el-radio-button__original-radio:checked + .el-radio-button__inner {
		background-color: #ffffff !important;
	}
	// .el-radio-button__inner {
	//     border: 2px solid;
	// }

	.el-radio-group.el-size-m {
		min-height: 32px;
		width: 100%;
		height: auto;
		justify-content: space-between;
		align-items: center;
	}

	.setting-layout-drawer {
		display: flex;
		flex-direction: column;
		align-items: center;

		.el-color-picker__trigger {
			border: 1px solid var(--el-color-primary);
			border-radius: 50% !important;
			padding: 3px;
		}

		.el-color-picker__color-inner {
			.el-icon svg {
				display: none;
			}
			border-radius: 50% !important;
			background: conic-gradient(
				from 90deg at 50% 50%,
				rgb(255, 0, 0) -19.41deg,
				rgb(255, 0, 0) 18.76deg,
				rgb(255, 138, 0) 59.32deg,
				rgb(255, 230, 0) 99.87deg,
				rgb(20, 255, 0) 141.65deg,
				rgb(0, 163, 255) 177.72deg,
				rgb(5, 0, 255) 220.23deg,
				rgb(173, 0, 255) 260.13deg,
				rgb(255, 0, 199) 300.69deg,
				rgb(255, 0, 0) 340.59deg,
				rgb(255, 0, 0) 378.76deg
			);
		}

		.el-color-picker__color {
			border-radius: 50% !important;
		}

		.el-radio-button {
			display: inline-flex;
			// max-height: 78px;
			padding: 8px;
			// border-radius: @border-radius;
			// border: 2px solid @component-border;

			// &:last-child {
			//   border-right: 2px solid transparent;
			// }

			> .el-radio-button__label {
				display: inline-flex;
			}
		}

		.el-is-checked {
			border: 2px solid red !important;
		}

		.el-form__controls-content {
			justify-content: end;
		}
	}
}
</style>
