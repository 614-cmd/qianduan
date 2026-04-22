<template>
	<!-- left+top布局 !-->
	<template v-if="layout == 'mix'">
		<nav ref="navbar" class="adminui-header">
			<div id="header" class="adminui-header-left">
				<div class="logo-bar">
					<img class="logo" src="/img/logo.svg" />
					<span>{{ appName }}</span>
				</div>
				<div
					class="panel-item hidden-sm-and-down"
					@click="
						$store.commit(
							'TOGGLE_menuIsCollapse',
							!$store.state.global.menuIsCollapse
						)
					"
				>
					<el-icon
						><indent v-if="menuIsCollapse" /><outdent v-else
					/></el-icon>
				</div>
				<div
					class="panel-item hidden-sm-and-down"
					@click="refreshTab()"
				>
					<el-icon><refresh /></el-icon>
				</div>
				<el-menu
					v-if="!ismobile"
					class="nav"
					mode="horizontal"
					:ellipsis="false"
				>
					<el-menu-item
						v-for="menu in topMenuList"
						:index="menu.meta.id"
						:key="menu.meta.id"
						:ref="menu.meta.id"
						@click="showMenu(menu)"
					>
						<el-icon size="16px"
							><component
								:is="menu.meta.icon || el - icon - menu"
						/></el-icon>
						{{ menu.meta.title }}
					</el-menu-item>
					<el-sub-menu>
						<template #title
							><el-icon size="16px"><more></more></el-icon
							>更多</template
						>
						<el-menu-item
							v-for="menu in topHideMenuList"
							:index="menu.meta.id"
							:key="menu.meta.id"
							:ref="menu.meta.id"
							@click="showMenu(menu)"
							><el-icon size="16px"
								><component
									:is="menu.meta.icon || el - icon - menu"
							/></el-icon>
							{{ menu.meta.title }}</el-menu-item
						>
					</el-sub-menu>
				</el-menu>
			</div>
			<div class="adminui-header-right">
				<userbar></userbar>
			</div>
		</nav>
		<section class="aminui-wrapper">
			<div
				v-if="(!ismobile && nextMenu.length > 0) || !pmenu.component"
				:class="
					menuIsCollapse ? 'aminui-side isCollapse' : 'aminui-side'
				"
			>
				<div class="adminui-side-scroll">
					<el-scrollbar>
						<el-menu
							:default-active="active"
							router
							:collapse="menuIsCollapse"
							:unique-opened="$CONFIG.MENU_UNIQUE_OPENED"
						>
							<NavMenu :navMenus="nextMenu"></NavMenu>
						</el-menu>
					</el-scrollbar>
				</div>
			</div>
			<Side-m v-if="ismobile"></Side-m>
			<div class="aminui-body el-container">
				<Tags v-if="!ismobile && layoutTags"></Tags>
				<div class="adminui-main" id="adminui-main">
					<Topbar2 v-if="!ismobile && toolbarTags"></Topbar2>
					<router-view v-slot="{ Component }">
						<keep-alive>
							<component :is="Component" :key="$route.fullPath" />
						</keep-alive>
					</router-view>
					<iframe-view></iframe-view>
				</div>
			</div>
		</section>
	</template>

	<!-- 左侧布局 -->
	<template v-else-if="layout == 'left'">
		<header class="adminui-header">
			<div class="adminui-header-left">
				<div class="logo-bar">
					<img class="logo" src="/img/logo.svg" />
					<span>{{ appName }}</span>
				</div>
				<div
					class="panel-item hidden-sm-and-down"
					@click="
						$store.commit(
							'TOGGLE_menuIsCollapse',
							!$store.state.global.menuIsCollapse
						)
					"
				>
					<el-icon
						><indent v-if="menuIsCollapse" /><outdent v-else
					/></el-icon>
				</div>
				<div
					class="panel-item hidden-sm-and-down"
					@click="refreshTab()"
				>
					<el-icon><refresh /></el-icon>
				</div>
			</div>

			<div class="adminui-header-right">
				<userbar></userbar>
			</div>
		</header>
		<section class="aminui-wrapper">
			<div
				v-if="!ismobile"
				:class="
					menuIsCollapse ? 'aminui-side isCollapse' : 'aminui-side'
				"
			>
				<div class="adminui-side-scroll">
					<el-scrollbar>
						<el-menu
							:default-active="active"
							router
							:collapse="menuIsCollapse"
							:unique-opened="$CONFIG.MENU_UNIQUE_OPENED"
						>
							<NavMenu :navMenus="menu"></NavMenu>
						</el-menu>
					</el-scrollbar>
				</div>
				<!-- <div class="adminui-side-bottom" @click="$store.commit('TOGGLE_menuIsCollapse', !$store.state.global.menuIsCollapse)">
					<el-icon><expand v-if="menuIsCollapse"/><fold v-else /></el-icon>
				</div> -->
			</div>
			<Side-m v-if="ismobile"></Side-m>
			<div class="aminui-body el-container">
				<Tags v-if="!ismobile && layoutTags"></Tags>
				<div class="adminui-main" id="adminui-main">
					<Topbar2 v-if="!ismobile && toolbarTags"></Topbar2>
					<router-view v-slot="{ Component }">
						<keep-alive>
							<component :is="Component" :key="$route.fullPath" />
						</keep-alive>
					</router-view>
					<iframe-view></iframe-view>
				</div>
			</div>
		</section>
	</template>

	<!-- 顶部菜单布局 -->
	<template v-else-if="layout == 'top'">
		<nav ref="navbar" class="adminui-header">
			<div class="adminui-header-left">
				<div class="logo-bar">
					<img class="logo" src="/img/logo.svg" />
					<span>{{ appName }}</span>
				</div>
				<div v-if="!ismobile" class="adminui-header-menu">
					<el-menu
						class="nav"
						mode="horizontal"
						:default-active="active"
						:ellipsis="false"
						router
					>
						<NavMenu2 :navMenus="topMenuList"></NavMenu2>
						<el-sub-menu>
							<template #title
								><el-icon size="16px"><more></more></el-icon>
								更多</template
							>
							<NavMenu2 :navMenus="topHideMenuList"></NavMenu2>
						</el-sub-menu>
					</el-menu>
				</div>
			</div>
			<div class="adminui-header-right">
				<Side-m v-if="ismobile"></Side-m>
				<userbar></userbar>
			</div>
		</nav>
		<section class="aminui-wrapper">
			<div class="aminui-body el-container">
				<Tags v-if="!ismobile && layoutTags"></Tags>
				<div class="adminui-main" id="adminui-main">
					<Topbar2 v-if="!ismobile && toolbarTags"></Topbar2>
					<router-view v-slot="{ Component }">
						<keep-alive>
							<component :is="Component" :key="$route.fullPath" />
						</keep-alive>
					</router-view>
					<iframe-view></iframe-view>
				</div>
			</div>
		</section>
	</template>

	<!-- 默认布局 -->
	<template v-else>
		<section class="aminui-wrapper">
			<div v-if="!ismobile" class="aminui-side-split">
				<div class="aminui-side-split-top">
					<router-link :to="$CONFIG.DASHBOARD_URL">
						<img class="logo" :title="appName" src="/img/logo.svg" />
					</router-link>
				</div>
				<div class="adminui-side-split-scroll">
					<el-scrollbar>
						<ul>
							<li
								v-for="item in menu"
								:key="item"
								:class="
									pmenu.meta.id == item.meta.id
										? 'active'
										: ''
								"
								@click="showMenu(item)"
							>
								<span>
									<el-icon
										><component
											:is="
												item.meta.icon ||
												el - icon - menu
											"
									/></el-icon>
									<p>
										{{ item.meta.title }}
									</p>
								</span>
							</li>
						</ul>
					</el-scrollbar>
				</div>
			</div>
			<div
				v-if="(!ismobile && nextMenu.length > 0) || !pmenu.component"
				:class="
					menuIsCollapse ? 'aminui-side isCollapse' : 'aminui-side'
				"
			>
				<div v-if="!menuIsCollapse" class="adminui-side-top">
					<h2>{{ pmenu.meta.title }}</h2>
				</div>
				<div class="adminui-side-scroll">
					<el-scrollbar>
						<el-menu
							:default-active="active"
							router
							:collapse="menuIsCollapse"
							:unique-opened="$CONFIG.MENU_UNIQUE_OPENED"
						>
							<NavMenu :navMenus="nextMenu"></NavMenu>
						</el-menu>
					</el-scrollbar>
				</div>
				<!-- <div class="adminui-side-bottom" @click="$store.commit('TOGGLE_menuIsCollapse', !$store.state.global.menuIsCollapse)">
					<el-icon><expand v-if="menuIsCollapse"/><fold v-else /></el-icon>
				</div> -->
			</div>
			<Side-m v-if="ismobile"></Side-m>
			<div class="aminui-body el-container">
				<Topbar>
					<userbar></userbar>
				</Topbar>
				<Tags v-if="!ismobile && layoutTags"></Tags>
				<div class="adminui-main" id="adminui-main">
					<router-view v-slot="{ Component }">
						<keep-alive>
							<component :is="Component" :key="$route.fullPath" />
						</keep-alive>
					</router-view>
					<iframe-view></iframe-view>
				</div>
			</div>
		</section>
	</template>

	<div class="main-maximize-exit" @click="exitMaximize">
		<el-icon><close /></el-icon>
	</div>
</template>

<script>
import SideM from "./components/sideM.vue";
import Topbar from "./components/topbar.vue";
import Topbar2 from "./components/topbar2.vue";
import Tags from "./components/tags.vue";
import NavMenu from "./components/NavMenu.vue";
import NavMenu2 from "./components/NavMenu2.vue";
import userbar from "./components/userbar.vue";
import iframeView from "./components/iframeView.vue";
export default {
	name: "index",
	components: {
		SideM,
		Topbar,
		Topbar2,
		Tags,
		NavMenu,
		NavMenu2,
		userbar,
		iframeView,
	},
	inject: ["refresh"],
	data() {
		return {
			settingDialog: false,
			topMenuList: [],
			topHideMenuList: [],
			menu: [],
			nextMenu: [],
			pmenu: { meta: {} },
			active: "",
		};
	},
	computed: {
		ismobile() {
			return this.$store.state.global.ismobile;
		},
		layout() {
			return this.$store.state.global.layout;
		},
		toolbarTags() {
			return (
				this.$store.state.global.toolbarTags &&
				this.$route.meta.hiddenBreadcrumb !== "1"
			);
		},
		layoutTags() {
			return this.$store.state.global.layoutTags;
		},
		appName() {
			return this.$store.state.global.appName;
		},
		menuIsCollapse() {
			return this.$store.state.global.menuIsCollapse;
		},
	},
	created() {
		this.onLayoutResize();
		window.addEventListener("resize", this.onLayoutResize);
		var menu = this.$router.getMenu();
		this.menu = this.filterUrl(menu);
		this.showThis();
	},
	watch: {
		$route() {
			this.showThis();
		},
		layout: {
			handler(val) {
				document.body.setAttribute("data-layout", val);
				if (this.layout === "mix" || this.layout === "top") {
					this.$nextTick(() => {
						this.fixTopMenu();
						window.addEventListener("resize", this.fixTopMenu);
					});
				}
			},
			immediate: true,
		},
	},
	mounted() {
		if (this.layout === "mix" || this.layout === "top") {
			this.fixTopMenu();
			window.addEventListener("resize", this.fixTopMenu);
		}
	},
	methods: {
		fixTopMenu() {
			let width = window.getComputedStyle(this.$refs.navbar).width;
			let menuWidth = 200;
			let size = (parseInt(width) - 600) / menuWidth;
			this.topMenuList = [];
			this.topHideMenuList = [];
			this.menu.forEach((item, index) => {
				if (index < size - 1) {
					this.topMenuList.push(item);
				} else {
					this.topHideMenuList.push(item);
				}
			});
		},
		onLayoutResize() {
			this.$store.commit("SET_ismobile", document.body.clientWidth < 992);
		},
		//路由监听高亮
		showThis() {
			if (this.$route.meta.breadcrumb) {
				this.pmenu = this.$route.meta.breadcrumb[0];
				this.nextMenu = this.filterUrl(this.pmenu.children);
			}
			this.$nextTick(() => {
				this.active = this.$route.meta.active || this.$route.fullPath;
			});
		},
		//点击显示
		showMenu(route) {
			this.pmenu = route;
			this.nextMenu = this.filterUrl(route.children);
			if (
				(!route.children || route.children.length == 0) &&
				route.component
			) {
				this.$router.push({ path: route.path });
			}
		},
		//TAB 刷新
		refreshTab() {
			this.refresh();
		},
		//转换外部链接的路由
		filterUrl(map) {
			var newMap = [];
			map &&
				map.forEach((item) => {
					item.meta = item.meta ? item.meta : {};
					//处理隐藏
					if (item.meta.isShow !== "1") {
						return false;
					}
					//处理http
					if (item.meta.target == "iframe") {
						item.path = `/i/${item.meta.id}`;
					}
					//递归循环
					if (item.children && item.children.length > 0) {
						item.children = this.filterUrl(item.children);
					}
					newMap.push(item);
				});
			return newMap;
		},
		//退出最大化
		exitMaximize() {
			document.getElementById("app").classList.remove("main-maximize");
		},
	},
};
</script>
