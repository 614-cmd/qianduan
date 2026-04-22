<template>
	<template v-for="navMenu in navMenus">
		<el-menu-item
			class="rush"
			v-if="!hasChildren(navMenu)"
			:key="navMenu.path"
			:index="navMenu.path || navMenu.meta.id"
		>
			<a
				v-if="navMenu.meta && navMenu.meta.target == 'link'"
				:href="toURL(navMenu.path)"
				target="_blank"
				@click.stop="() => {}"
			></a>
			<el-icon v-if="navMenu.meta && navMenu.meta.icon"
				><component :is="navMenu.meta.icon || 'menu'"
			/></el-icon>
			<template #title>
				<span>{{ navMenu.meta.title }}</span>
			</template>
		</el-menu-item>
		<el-sub-menu
			class="rush top-header"
			:popper-class="popperClass"
			v-else
			:key="navMenu.meta.id"
			:index="navMenu.path || navMenu.meta.id"
		>
			<template #title>
				<el-icon v-if="navMenu.meta && navMenu.meta.icon"
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
<style lang="less">
.rush .el-sub-menu__title {
	padding: 0 5px;
}
.adminui-header .nav li.rush {
	padding: 0;
}

.el-sub-menu.top-header .el-sub-menu__icon-arrow {
	position: unset;
	margin-top: 0px;
}
</style>
