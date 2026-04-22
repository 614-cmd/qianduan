const DEFAULT_CONFIG = {
	// APP NAME
	APP_NAME: "JeePlus微服务平台",
	//首页地址
	DASHBOARD_URL: "/home",

	//内核版本号
	CORE_VER: "1.0.0",

	//接口地址
	API_URL: "/api",

	//请求超时
	TIMEOUT: 10000,

	//TokenName
	TOKEN: "token",

	//追加其他头
	HEADERS: {},

	//布局 默认：default | 通栏：header | 经典：menu | 功能坞：dock
	//dock将关闭标签和面包屑栏
	LAYOUT: "default",
	//主题
	THEME: "dark",
	// 主题色
	COLOR: "#2d8cf0",
	//菜单是否折叠
	MENU_IS_COLLAPSE: false,

	//菜单是否启用手风琴效果
	MENU_UNIQUE_OPENED: true,

	//是否开启多标签
	LAYOUT_TAGS: true,

	//是否开启面包屑
	TOOLBAR_TAGS: true,

	//语言
	LANG: "zh-cn",
};

export default DEFAULT_CONFIG

