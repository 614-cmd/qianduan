<template>
	<el-config-provider :locale="locale" :button="{ autoInsertSpace: false }">
		<router-view v-if="isRefresh"></router-view>
	</el-config-provider>
</template>

<script>
import colorTool from "@/utils/color";
import zh from "element-plus/es/locale/lang/zh-cn";

export default {
	name: "App",
	data: () => ({
		isRefresh: true,
	}),
	computed: {
		locale() {
			return zh;
		},
	},
	created() {
		//设置主题颜色
		const app_color =
			this.$TOOL.data.get("APP_COLOR") || this.$CONFIG.COLOR;
		if (app_color) {
			colorTool.setAppColors(app_color);
		}
	},
	provide() {
		return {
			refresh: this.refresh,
		};
	},
	methods: {
		refresh() {
			this.isRefresh = false;
			this.$nextTick(() => {
				this.isRefresh = true;
			});
		},
	},
};
</script>

<style lang="scss">
@import "@/style/style.scss";
@import "@/style/theme/dark.scss";
@import "@/style/theme/white.scss";
</style>
