import { createRouter, createWebHashHistory } from "vue-router";
const router = createRouter({
	history: createWebHashHistory(),
	routes: [
		{
			path: "/componentsPreview",
			component: () =>
				import(
					/* webpackChunkName: "page" */ "@/datav/page/list/componentsPreview.vue"
				),
		},
		{
			path: "/build/:id",
			name: "build",
			component: () =>
				import(/* webpackChunkName: "page" */ "@/datav/page/build.vue"),
		},
		{
			path: "/view/:id",
			name: "view",
			component: () =>
				import(/* webpackChunkName: "page" */ "@/datav/page/view.vue"),
		},
	],
});
export default router;
