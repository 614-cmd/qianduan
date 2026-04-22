import { createRouter, createWebHashHistory } from "vue-router";
import { ElNotification } from "element-plus";
import config from "@/config";
import NProgress from "nprogress";
import "nprogress/nprogress.css";
import tool from "@/utils/tool";
import systemRouter from "./systemRouter";
import { beforeEach, afterEach } from "./scrollBehavior";

// 匹配views里面所有的.vue文件
const modules = import.meta.glob("./../views/**/*.vue");
const otherModules = {
	404: () => import("../layout/other/404.vue"),
	empty: () => import("../layout/other/empty.vue"),
};

//系统路由
const routes = systemRouter;

//系统特殊路由
const routes_404 = {
	path: "/:pathMatch(.*)*",
	hidden: true,
	component: otherModules["404"],
};
let routes_404_r = () => {};

const router = createRouter({
	history: createWebHashHistory(),
	routes,
});

//设置标题
document.title = tool.data.get("APP_NAME");

//判断是否已加载过动态/静态路由
var isGetRouter = false;

router.beforeEach(async (to, from, next) => {
	NProgress.start();
	//动态标题
	document.title = to.meta.title
		? `${to.meta.title} - ` + (tool.data.get("APP_NAME") || config.APP_NAME)
		: tool.data.get("APP_NAME") || config.APP_NAME;

	let token = tool.data.get(config.TOKEN);

	if (to.path === "/login") {
		//删除路由(替换当前layout路由)
		router.addRoute(routes[0]);
		//删除路由(404)
		routes_404_r();
		isGetRouter = false;
		next();
		return false;
	}

	if (!token) {
		next({
			path: "/login",
		});
		return false;
	}

	//整页路由处理
	if (to.meta.fullpage) {
		to.matched = [to.matched[to.matched.length - 1]];
	}
	//加载动态/静态路由
	if (!isGetRouter) {
		let apiMenu = tool.data.get("MENU") || [];
		var menuRouter = filterAsyncRouter(apiMenu);
		menuRouter = flatAsyncRoutes(menuRouter);
		menuRouter.forEach((item) => {
			router.addRoute("layout", item);
		});
		routes_404_r = router.addRoute(routes_404);
		if (to.matched.length == 0) {
			router.push(to.fullPath);
		}
		isGetRouter = true;
	}
	beforeEach(to, from);
	next();
});

router.afterEach((to, from) => {
	afterEach(to, from);
	NProgress.done();
});

router.onError((error) => {
	NProgress.done();
	ElNotification.error({
		title: "路由错误",
		message: error.message,
	});
});

//入侵追加自定义方法、对象
router.getMenu = () => {
	var apiMenu = tool.data.get("MENU") || [];
	return apiMenu;
};

//转换
function filterAsyncRouter(routerMap) {
	const accessedRouters = [];
	routerMap.forEach((item) => {
		item.meta = item.meta ? item.meta : {};
		//处理外部链接特殊路由
		if (item.meta.target == "iframe") {
			item.meta.url = item.path;
			item.path = `/i/${item.meta.id}`;
			item.component = undefined;
		} else {
			item.component = item.path;
		}
		if (item.path == undefined) {
			item.path = "";
		}
		//MAP转路由对象
		var route = {
			path: item.path,
			name:
				item.path
					.replace(/^\//g, "")
					.replace(/[/]/g, "-")
					.replace(/[?]/g, "-")
					.replace(/&/g, "-")
					.replace(/=/g, "-") + item.meta.id,
			meta: item.meta,
			redirect: item.redirect,
			children: item.children ? filterAsyncRouter(item.children) : null,
			component: loadComponent(item.component),
		};
		accessedRouters.push(route);
	});
	return accessedRouters;
}
function loadComponent(component) {
	if (component) {
		for (const path in modules) {
			const dir = path.split("views")[1].split(".vue")[0];
			if (dir === component || dir === component + "/index") {
				return () => modules[path]();
			}
		}
	}
	return otherModules["empty"];
}

//路由扁平化
function flatAsyncRoutes(routes, breadcrumb = []) {
	let res = [];
	routes.forEach((route) => {
		const tmp = { ...route };
		if (tmp.children && tmp.children.length > 0) {
			let childrenBreadcrumb = [...breadcrumb];
			childrenBreadcrumb.push(route);
			let tmpRoute = { ...route };
			tmpRoute.meta.breadcrumb = childrenBreadcrumb;
			delete tmpRoute.children;
			if (tmpRoute.path) {
				res.push(tmpRoute);
			}

			let childrenRoutes = flatAsyncRoutes(
				tmp.children,
				childrenBreadcrumb
			);
			childrenRoutes.map((item) => {
				if (item.path) {
					res.push(item);
				}
			});
		} else {
			let tmpBreadcrumb = [...breadcrumb];
			tmpBreadcrumb.push(tmp);
			tmp.meta.breadcrumb = tmpBreadcrumb;
			if (tmp.path) {
				res.push(tmp);
			}
		}
	});
	return res;
}

export default router;
