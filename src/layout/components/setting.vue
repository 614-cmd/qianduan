<template>
	<div>
		<el-drawer
			title="页面配置"
			v-model="isRightVisible"
			size="450px"
			:modal="true"
			show-close
			:with-header="true"
		>
			<div class="setting-drawer-container">
				<div class="setting-container">
					<el-form>
						<!-- <el-alert title="以下配置可实时预览，开发者可在 config/index.js 中配置默认值，非常不建议在生产环境下开放布局设置" type="error" :closable="false"></el-alert> -->
						<div class="setting-group-title">主题模式</div>

						<el-form-item>
							<el-radio-group v-model="theme">
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
						<div class="setting-group-title">导航布局</div>
						<el-form-item>
							<el-radio-group v-model="layout">
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

						<div class="setting-group-title">主题色</div>
						<el-form-item>
							<el-radio-group v-model="colorPrimary">
								<div
									v-for="(item, index) in colorList"
									:key="index"
									class="setting-layout-drawer"
								>
									<el-radio-button
										:key="index"
										:text-color="item.color"
										:fill="item.color"
										:value="item.color"
										class="setting-layout-color-group"
									>
										<div
											class="color-container"
											:style="`background:${item.color}`"
										></div>
									</el-radio-button>
								</div>
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
						<div class="setting-group-title">元素开关</div>
						<el-form-item label="折叠菜单">
							<el-switch
								style="float: right"
								v-model="menuIsCollapse"
							></el-switch>
						</el-form-item>
						<el-form-item label="标签栏">
							<el-switch
								style="float: right"
								v-model="layoutTags"
							></el-switch>
						</el-form-item>
						<el-form-item label="面包屑">
							<el-switch
								style="float: right"
								v-model="toolbarTags"
							></el-switch>
						</el-form-item>
						<el-divider></el-divider>
					</el-form>
				</div>
			</div>
		</el-drawer>
	</div>
</template>

<script>
import config from "@/config";
import colorTool from "@/utils/color";
import SettingDarkIcon from "@/assets/img/assets-setting-dark.svg";
import SettingLightIcon from "@/assets/img/assets-setting-light.svg";
export default {
	data() {
		return {
			isRightVisible: false,
			layout: this.$TOOL.data.get("APP_LAYOUT") || config.LAYOUT,
			menuIsCollapse: this.$store.state.global.menuIsCollapse,
			layoutTags: this.$store.state.global.layoutTags,
			toolbarTags: this.$store.state.global.toolbarTags,
			theme: this.$TOOL.data.get("APP_THEME") || config.THEME,
			colorPrimary: this.$TOOL.data.get("APP_COLOR") || config.COLOR,
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
	components: {
		SettingDarkIcon,
		SettingLightIcon,
	},
	watch: {
		layout(val) {
			this.$store.commit("SET_layout", val);
			this.$TOOL.data.set("APP_LAYOUT", val);
		},
		menuIsCollapse(val) {
			this.$store.commit("TOGGLE_menuIsCollapse", val);
			this.$TOOL.data.set("menuIsCollapse", val);
		},
		layoutTags(val) {
			this.$store.commit("TOGGLE_layoutTags", val);
			this.$TOOL.data.set("layoutTags", val);
		},
		toolbarTags(val) {
			this.$store.commit("TOGGLE_toolbarTags", val);
			this.$TOOL.data.set("toolbarTags", val);
		},
		theme(val) {
			document.body.setAttribute("data-theme", val);
			this.$TOOL.data.set("APP_THEME", val);
		},
		colorPrimary(val) {
			colorTool.setAppColors(val);
			this.$TOOL.data.set("APP_COLOR", val);
		},
	},
	methods: {
		showRight() {
			this.isRightVisible = true;
		},
	},
};
</script>

<style lang="scss">
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

.setting-drawer-container {
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
		margin-bottom: 16px;

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
