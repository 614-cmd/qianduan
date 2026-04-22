import config from "@/config";
// import api from './api'
import tool from "@/utils/tool";

//系统路由
const routes = [
	{
		name: "layout",
		path: "/",
		meta: { title: "工作台" },
		component: () => import("@/layout/index.vue"),
		redirect: tool.data.get("APP_HOME") || config.DASHBOARD_URL,
		children: [
			{
				path: "/redirect/:path(.*)",
				component: () => import("@/views/redirect/index.vue"),
			},
			{
				path: "flowable/task/TaskForm",
				component: () => import("@/views/flowable/task/TaskForm.vue"),
				name: "task-form",
				meta: { title: "流程表单" },
			},
			{
				path: "flowable/task/TaskFormEdit",
				component: () =>
					import("@/views/flowable/task/TaskFormEdit.vue"),
				name: "task-form-edit",
				meta: { title: "流程表单" },
			},
			{
				path: "flowable/task/TaskFormDetail",
				component: () =>
					import("@/views/flowable/task/TaskFormDetail.vue"),
				name: "task-form-detail",
				meta: { title: "流程表单详情" },
			},
			{
				path: "form/generateList",
				component: () => import("@/views/form/GenerateList.vue"),
				name: "form-preview-list",
				meta: { icon: "histogram", title: "列表" },
			},
			{
				path: "echarts/GenerateChart",
				component: () => import("@/views/echarts/GenerateChart.vue"),
				name: "echarts-generate",
				meta: { icon: "histogram", title: "预览图表" },
			},
			{
				path: "echarts/dashboard/Preview",
				component: () =>
					import("@/views/echarts/dashboard/Preview.vue"),
				name: "preview",
				meta: { icon: "histogram", title: "预览报表看板" },
			},
			{
				path: "ureport/designer",
				component: () => import("@/layout/other/empty.vue"),
				name: "ureport-design",
				meta: {
					title: "设计报表",
					url: "/ureport/designer",
					target: "iframe",
				},
			},
			{
				path: "ureport/preview",
				component: () => import("@/layout/other/empty.vue"),
				name: "ureport-preview",
				meta: {
					title: "预览报表",
					url: "/ureport/preview",
					target: "iframe",
				},
			},
			// 支持直接传递第三方url，打开iframe, router.push('/iframe??iframeUrl=http://localhost:8088/ureport/designer?_u=file:22.ureport.xml&title=编辑报表')
			{
				path: "iframe",
				component: () => import("@/layout/other/empty.vue"),
				meta: { target: "iframe" },
			},
		],
	},
	{
		path: "/login",
		component: () => import("@/views/sys/login/login.vue"),
		meta: {
			title: "登录",
		},
	},
];

export default routes;
