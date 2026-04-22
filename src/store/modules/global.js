import tool from "@/utils/tool";
export default {
	state: {
		//移动端布局
		ismobile: false,
		//布局
		layout: tool.data.get("APP_LAYOUT"),
		//菜单是否折叠 toggle
		menuIsCollapse: tool.data.get("menuIsCollapse"),
		//多标签栏
		layoutTags:
			tool.data.get("layoutTags") === null
				? true
				: tool.data.get("layoutTags"),
		//面包屑
		toolbarTags:
			tool.data.get("toolbarTags") === null
				? true
				: tool.data.get("toolbarTags"),
		appName: tool.data.get("APP_NAME"),
	},
	mutations: {
		SET_ismobile(state, key) {
			state.ismobile = key;
		},
		SET_layout(state, key) {
			state.layout = key;
		},
		TOGGLE_menuIsCollapse(state, val) {
			state.menuIsCollapse = val;
		},
		TOGGLE_layoutTags(state, val) {
			state.layoutTags = val;
		},
		TOGGLE_toolbarTags(state, val) {
			state.toolbarTags = val;
		},
		updateAppName(state, name) {
			state.appName = name;
		},
	},
};
