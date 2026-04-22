<template>
	<div class="container">
		<div class="logo">
			<img alt="logo" src="/img/logo.svg" />
			<div class="logo-text">{{ appName }}</div>
		</div>
		<LoginBanner />
		<div class="content">
			<div class="content-inner">
				<LoginForm />
			</div>
			<div class="footer">
				<Footer />
			</div>
		</div>
	</div>
</template>

<script>
import Footer from "@/components/footer/index.vue";
import LoginBanner from "./components/banner.vue";
import LoginForm from "./components/login-form.vue";
import configService from "@/api/sys/configService";
import colorTool from "@/utils/color";
export default {
	components: {
		Footer,
		LoginBanner,
		LoginForm,
	},
	data() {
		return {
			productName: "",
		};
	},
	beforeCreate() {
		configService.getConfig().then((data) => {
			// 初始化产品名
			if (!this.$TOOL.data.get("APP_NAME")) {
				this.$store.commit("updateAppName", data.productName);
				this.$TOOL.data.set("APP_NAME", data.productName);
			}
			// 初始化布局
			if (!this.$TOOL.data.get("APP_LAYOUT")) {
				this.$store.commit("SET_layout", data.defaultLayout);
				this.$TOOL.data.set("APP_LAYOUT", data.defaultLayout);
			}
			// 初始化主题色
			if (!this.$TOOL.data.get("APP_COLOR")) {
				colorTool.setAppColors(data.defaultColor);
				this.$TOOL.data.set("APP_COLOR", data.defaultColor);
			}
			// 初始化主题
			if (!this.$TOOL.data.get("APP_THEME")) {
				document.body.setAttribute("data-theme", data.defaultTheme);
				this.$TOOL.data.set("APP_THEME", data.defaultTheme);
			}
		});
	},
	computed: {
		appName() {
			return this.$store.state.global.appName;
		},
	},
};
</script>

<style lang="less" scoped>
.container {
	display: flex;
	height: 100vh;

	.banner {
		width: 50%;
		background: linear-gradient(163.85deg, #4b94f2 0%, #4393fa 100%);
	}

	.content {
		position: relative;
		display: flex;
		flex: 1;
		background: #ffffff;
		align-items: center;
		justify-content: center;
	}

	.footer {
		position: absolute;
		right: 0;
		bottom: 0;
		width: 100%;
	}
}

.logo {
	position: fixed;
	top: 24px;
	padding-left: 22px;
	height: 80px;
	z-index: 1;
	width: 40%;
	display: inline-flex;
	align-items: center;
	background: linear-gradient(
		270deg,
		rgba(73, 147, 244, 0.5),
		rgba(135, 181, 245, 0.5)
	);

	&-text {
		margin-right: 4px;
		margin-left: 4px;
		color: #f7f8fa;
		font-size: 26px;
		font-family: cursive;
	}
}
</style>

<style lang="less" scoped>
// responsive
@media (max-width: 992px) {
	.container {
		.banner {
			width: 25%;
		}
	}
}
</style>
