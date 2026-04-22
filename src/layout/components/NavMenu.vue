<template>
	<div v-if="navMenus.length <= 0" style="padding: 20px">
		<el-alert
			title="无子集菜单"
			center
			type="info"
			:closable="false"
		></el-alert>
	</div>
	<template v-for="(navMenu, index) in navMenus">
		<el-menu-item
			:key="index"
			v-if="!hasChildren(navMenu)"
			:index="navMenu.path || navMenu.meta.id"
		>
			<a
				v-if="navMenu.meta && navMenu.meta.target == 'link'"
				:href="toURL(navMenu.path)"
				target="_blank"
				@click.stop="() => {}"
			></a>
			<el-icon size="13px" v-if="navMenu.meta && navMenu.meta.icon"
				><component :is="navMenu.meta.icon || 'menu'"
			/></el-icon>
			<template #title>
				<span>{{ navMenu.meta.title }}</span>
			</template>
		</el-menu-item>
		<el-sub-menu
			:popper-class="popperClass"
			v-else
			:index="navMenu.path || navMenu.meta.id"
			:key="navMenu.path || navMenu.meta.id"
		>
			<template #title>
				<el-icon size="13px" v-if="navMenu.meta && navMenu.meta.icon"
					><component :is="navMenu.meta.icon || 'el-icon-menu'"
				/></el-icon>
				<span>{{ navMenu.meta.title }}</span>
			</template>
			<NavMenu :navMenus="navMenu.children"></NavMenu>
		</el-sub-menu>
	</template>
</template>

<script>
import { isURL } from "@/utils/validate";
export default {
	name: "NavMenu",
	props: ["navMenus", "popperClass"],
	setup() {
		return {
			apiUrl: import.meta.env.VITE_APP_BASE,
		};
	},
	data() {
		return {};
	},
	methods: {
		toURL(path) {
			return isURL(path) ? path : this.apiUrl + path;
		},
		hasChildren(item) {
			return (
				item.children &&
				!item.children.every((item) => item.meta.isShow != "1")
			);
		},
	},
};
</script>
