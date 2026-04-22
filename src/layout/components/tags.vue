<template>
	<div class="adminui-tags">
		<el-button text style="width: 32px" @click="toLeft()">
			<el-icon><ArrowLeftBold /></el-icon>
		</el-button>
		<ul ref="tags">
			<li
				v-for="tag in tagList"
				v-bind:key="tag"
				:class="[
					isActive(tag) ? 'active' : '',
					tag.meta.affix == '1' ? 'affix' : '',
				]"
				@contextmenu.prevent="openContextMenu($event, tag)"
			>
				<router-link
					:to="{
						path: tag.path,
						query: tag.query,
						fullPath: tag.fullPath,
					}"
				>
					<span>
						{{
							(tag.query && tag.query.title) || tag.meta.title
						}}</span
					>
					<el-icon
						v-if="tag.meta.affix !== '1'"
						@click.prevent.stop="closeSelectedTag(tag)"
						><close
					/></el-icon>
				</router-link>
			</li>
		</ul>
		<el-button text style="width: 32px; right: 0" @click="toRight">
			<el-icon><ArrowRightBold /></el-icon>
		</el-button>
	</div>

	<transition name="el-zoom-in-top">
		<ul
			v-if="contextMenuVisible"
			:style="{ left: left + 'px', top: top + 'px' }"
			class="contextmenu"
			id="contextmenu"
		>
			<li @click="refreshTab()">
				<el-icon><refresh /></el-icon>刷新
			</li>
			<hr />
			<li
				@click="closeTabs()"
				:class="contextMenuItem.meta.affix == '1' ? 'disabled' : ''"
			>
				<el-icon><close /></el-icon>关闭标签
			</li>
			<li @click="closeOtherTabs()">
				<el-icon><folder-delete /></el-icon>关闭其他标签
			</li>
			<hr />
			<li @click="maximize()">
				<el-icon><full-screen /></el-icon>最大化
			</li>
			<li @click="openWindow()">
				<el-icon><copy-document /></el-icon>在新的窗口中打开
			</li>
		</ul>
	</transition>
</template>

<script>
import Sortable from "sortablejs";

export default {
	name: "tags",
	data() {
		return {
			contextMenuVisible: false,
			contextMenuItem: null,
			left: 0,
			top: 0,
			tagList: this.$store.state.viewTags.viewTags,
		};
	},
	inject: ["refresh"],
	watch: {
		$route(e) {
			this.addViewTags(e);
			//判断标签容器是否出现滚动条
			this.$nextTick(() => {
				const tags = this.$refs.tags;
				if (tags && tags.scrollWidth > tags.clientWidth) {
					//确保当前标签在可视范围内
					let targetTag = tags.querySelector(".active");
					targetTag.scrollIntoView();
				}
			});
		},
		contextMenuVisible(value) {
			var _this = this;
			var cm = function (e) {
				let sp = document.getElementById("contextmenu");
				if (sp && !sp.contains(e.target)) {
					_this.closeMenu();
				}
			};
			if (value) {
				document.body.addEventListener("click", (e) => cm(e));
			} else {
				document.body.removeEventListener("click", (e) => cm(e));
			}
		},
	},
	created() {
		var menu = this.$router.getMenu();
		// eslint-disable-next-line no-debugger
		this.treeFind(menu, (node) => node.meta.affix == "1");
		this.addViewTags(this.$route);
	},
	mounted() {
		this.tagDrop();
		this.scrollInit();
	},
	methods: {
		//查找树
		treeFind(tree, func) {
			for (const data of tree) {
				if (func(data)) {
					// eslint-disable-next-line no-debugger
					data.fullPath = data.path;
					this.addViewTags(data);
				}
				if (data.children) {
					this.treeFind(data.children, func);
				}
			}
		},
		//标签拖拽排序
		tagDrop() {
			const target = this.$refs.tags;
			Sortable.create(target, {
				draggable: "li",
				animation: 300,
			});
		},
		//增加tag
		addViewTags(route) {
			if (route.name && !route.meta.fullpage) {
				this.$store.commit("pushViewTags", route);
				this.$store.commit("pushKeepLive", route.name);
			}
		},
		//高亮tag
		isActive(route) {
			return route.fullPath === this.$route.fullPath;
		},
		//关闭tag
		closeSelectedTag(tag, autoPushLatestView = true) {
			this.$store.commit("removeViewTags", tag);
			this.$store.commit("removeIframeList", tag);
			this.$store.commit("removeKeepLive", tag.name);
			if (autoPushLatestView && this.isActive(tag)) {
				const latestView = this.tagList.slice(-1)[0];
				if (latestView) {
					this.$router.push(latestView.fullPath);
				} else {
					this.$router.push("/");
				}
			}
		},
		//tag右键
		openContextMenu(e, tag) {
			this.contextMenuItem = tag;
			this.contextMenuVisible = true;
			this.left = e.clientX + 1;
			this.top = e.clientY + 1;

			//FIX 右键菜单边缘化位置处理
			this.$nextTick(() => {
				let sp = document.getElementById("contextmenu");
				if (document.body.offsetWidth - e.clientX < sp.offsetWidth) {
					this.left = document.body.offsetWidth - sp.offsetWidth + 1;
					this.top = e.clientY + 1;
				}
			});
		},
		//关闭右键菜单
		closeMenu() {
			this.contextMenuItem = null;
			this.contextMenuVisible = false;
		},
		//TAB 刷新
		refreshTab() {
			this.refresh();
		},
		//TAB 关闭
		closeTabs() {
			var nowTag = this.contextMenuItem;
			if (nowTag.meta.affix !== "1") {
				this.closeSelectedTag(nowTag);
				this.contextMenuVisible = false;
			}
		},
		//TAB 关闭其他
		closeOtherTabs() {
			var nowTag = this.contextMenuItem;
			//判断是否当前路由，否的话跳转
			if (this.$route.fullPath != nowTag.fullPath) {
				this.$router.push({
					path: nowTag.fullPath,
					query: nowTag.query,
				});
			}
			var tags = [...this.tagList];
			tags.forEach((tag) => {
				if (
					(tag.meta && tag.meta.affix == "1") ||
					nowTag.fullPath == tag.fullPath
				) {
					return true;
				} else {
					this.closeSelectedTag(tag, false);
				}
			});
			this.contextMenuVisible = false;
		},
		//TAB 最大化
		maximize() {
			var nowTag = this.contextMenuItem;
			this.contextMenuVisible = false;
			//判断是否当前路由，否的话跳转
			if (this.$route.fullPath != nowTag.fullPath) {
				this.$router.push({
					path: nowTag.fullPath,
					query: nowTag.query,
				});
			}
			document.getElementById("app").classList.add("main-maximize");
		},
		//新窗口打开
		openWindow() {
			var nowTag = this.contextMenuItem;
			var url = nowTag.href || "/";
			if (nowTag.meta.affix !== "1") {
				this.closeSelectedTag(nowTag);
			}
			window.open(url);
			this.contextMenuVisible = false;
		},
		//横向滚动
		scrollInit() {
			const scrollDiv = this.$refs.tags;
			scrollDiv.addEventListener("mousewheel", handler, false) ||
				scrollDiv.addEventListener("DOMMouseScroll", handler, false);
			function handler(event) {
				const detail = event.wheelDelta || event.detail;
				//火狐上滚键值-3 下滚键值3，其他内核上滚键值120 下滚键值-120
				const moveForwardStep = 1;
				const moveBackStep = -1;
				let step = 0;
				if (detail == 3 || (detail < 0 && detail != -3)) {
					step = moveForwardStep * 50;
				} else {
					step = moveBackStep * 50;
				}
				scrollDiv.scrollLeft += step;
			}
		},
		toLeft() {
			for (let i = 1; i <= 100; i++) {
				setTimeout(() => {
					this.$refs.tags.scrollLeft = this.$refs.tags.scrollLeft - 5;
				}, 2 * i);
			}
		},
		toRight() {
			for (let i = 1; i <= 100; i++) {
				setTimeout(() => {
					this.$refs.tags.scrollLeft = this.$refs.tags.scrollLeft + 5;
				}, 2 * i);
			}
		},
	},
};
</script>

<style lang="less">
.adminui-tags + .contextmenu + .adminui-main,
.adminui-tags + .adminui-main {
	.adminui-topbar {
		margin-top: 6px;
	}
	.adminui-topbar + .splitpanes {
		padding: 12px 12px 8px 12px;
		height: calc(100% - 109px);
	}

	.adminui-topbar + .iframe-pages {
		height: calc(100% - 125px);
		margin: 8px 12px;
	}
	.adminui-topbar + .el-container.jp-container {
		height: calc(100% - 109px);
		padding: 12px 12px 8px 12px;
	}
	.adminui-topbar + .page {
		height: calc(100% - 129px) !important;
		margin: 12px 12px 8px 12px;
	}
}
.adminui-tags {
	background: #f5f7f9;
	padding: 6px 0 0 0;
	margin: 0 12px 0 12px;
	display: -webkit-box;
	display: -ms-flexbox;
	display: flex;
	ul {
		display: flex;
		overflow: hidden;
		flex-shrink: 0;
		width: calc(100% - 64px);
		li {
			height: 32px;
			background: #fff;
			border-radius: 3px;
			border: none;
			margin-right: 6px;
			color: #808695;
			cursor: pointer;
			display: inline-block;
			// height: 24px;
			line-height: 32px;
			// margin: 0 0 0 4px;
			position: relative;
			flex-shrink: 0;

			:hover {
				background-color: var(--el-color-primary-light-9);
			}
			a {
				display: inline-block;
				padding: 0 10px;
				width: 100%;
				height: 100%;
				color: #808695;
				line-height: 1.5;
				font-size: 14px;
				border-color: transparent;
				text-decoration: none;
				display: flex;
				align-items: center;
				i {
					margin-left: 10px;
					border-radius: 3px;
					width: 18px;
					height: 18px;
					display: flex;
					align-items: center;
					justify-content: center;
					:hover {
						// background: rgba(0, 0, 0, 0.2);
						color: red;
					}
				}
			}
		}
		li.active {
			a {
				color: var(--el-color-primary);
			}
		}
	}
}

.contextmenu {
	position: fixed;
	width: 200px;
	margin: 0;
	border-radius: 0px;
	background: #fff;
	border: 1px solid #e4e7ed;
	box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
	z-index: 3000;
	list-style-type: none;
	padding: 10px 0;
}
.contextmenu hr {
	margin: 5px 0;
	border: none;
	height: 1px;
	font-size: 0px;
	background-color: #ebeef5;
}
.contextmenu li {
	display: flex;
	align-items: center;
	margin: 0;
	cursor: pointer;
	line-height: 30px;
	padding: 0 17px;
	color: #606266;
}
.contextmenu li i {
	font-size: 14px;
	margin-right: 10px;
}
.contextmenu li:hover {
	background-color: #ecf5ff;
	color: #66b1ff;
}
.contextmenu li.disabled {
	cursor: not-allowed;
	color: #bbb;
	background: transparent;
}
</style>
