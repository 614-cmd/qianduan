<template>
	<div class="jp-search">
		<el-input
			ref="input"
			v-model="input"
			class="s-input"
			placeholder="搜索..."
			prefix-icon="el-icon-search"
			:trigger-on-focus="false"
			@input="inputChange"
		/>
		<div class="jp-search-result" v-if="visible">
			<div class="jp-search-no-result" v-if="result.length <= 0">
				暂无搜索结果
			</div>
			<ul v-else>
				<el-scrollbar max-height="366px">
					<li
						v-for="item in result"
						:key="item.path"
						@click="to(item)"
					>
						<el-icon
							><component :is="item.icon || 'el-icon-menu'"
						/></el-icon>
						<span class="title">{{ item.title }}</span>
					</li>
				</el-scrollbar>
			</ul>
		</div>
	</div>
</template>

<script>
export default {
	data() {
		return {
			input: "",
			visible: false,
			menu: [],
			result: [],
			history: [],
		};
	},
	mounted() {
		var searchHistory = this.$TOOL.data.get("SEARCH_HISTORY") || [];
		this.history = searchHistory;
		var menuTree = this.$TOOL.data.get("MENU");
		this.filterMenu(menuTree);
		this.$refs.input.focus();
	},
	methods: {
		inputChange(value) {
			if (value) {
				this.result = this.menuFilter(value);
				this.visible = true;
			} else {
				this.result = [];
				this.visible = false;
			}
		},
		filterMenu(map) {
			map.forEach((item) => {
				if (item.meta.isShow == "0" || item.meta.type == "2") {
					return false;
				}
				if (item.meta.target == "iframe") {
					item.path = `/i/${item.meta.id}`;
				}
				if (item.children && item.children.length > 0 && !item.path) {
					this.filterMenu(item.children);
				} else {
					if (item.path) {
						this.menu.push(item);
					}
				}
			});
		},
		menuFilter(queryString) {
			var res = [];
			//过滤菜单树
			var filterMenu = this.menu.filter((item) => {
				if (
					item.meta.title
						.toLowerCase()
						.indexOf(queryString.toLowerCase()) >= 0
				) {
					return true;
				}
				if (
					item.name
						.toLowerCase()
						.indexOf(queryString.toLowerCase()) >= 0
				) {
					return true;
				}
			});
			//匹配系统路由
			var router = this.$router.getRoutes();
			var filterRouter = filterMenu.map((m) => {
				if (m.meta.type == "link") {
					return router.find((r) => r.path == "/" + m.path);
				} else {
					return router.find((r) => r.path == m.path);
				}
			});
			//重组对象
			filterRouter.forEach((item) => {
				if (item) {
					res.push({
						name: item.name,
						type: item.meta.type,
						path:
							item.meta.type == "link"
								? item.path.slice(1)
								: item.path,
						icon: item.meta.icon,
						title: item.meta.title,
						breadcrumb: item.meta.breadcrumb
							.map((v) => v.meta.title)
							.join(" - "),
					});
				}
			});
			return res;
		},
		to(item) {
			if (!this.history.includes(this.input)) {
				this.history.push(this.input);
				this.$TOOL.data.set("SEARCH_HISTORY", this.history);
			}
			if (item.type == "link") {
				setTimeout(() => {
					let a = document.createElement("a");
					a.style = "display: none";
					a.target = "_blank";
					a.href = item.path;
					document.body.appendChild(a);
					a.click();
					document.body.removeChild(a);
				}, 10);
			} else {
				this.$router.push({ path: item.path });
			}
			this.$emit("success", true);
			this.input = "";
			this.visible = false;
		},
		historyClick(text) {
			this.input = text;
			this.inputChange(text);
		},
		historyClose(index) {
			this.history.splice(index, 1);
			if (this.history.length <= 0) {
				this.$TOOL.data.remove("SEARCH_HISTORY");
			} else {
				this.$TOOL.data.set("SEARCH_HISTORY", this.history);
			}
		},
	},
};
</script>

<style lang="less">
.jp-search {
	.s-input {
		display: inline-block;
		height: 32px;
		line-height: 32px;
		padding: 0 24px 0 8px;
		font-size: 14px;
		outline: none;
		border: none;
		-webkit-box-sizing: border-box;
		box-sizing: border-box;
		color: #515a6e;
		background-color: transparent;
		position: relative;
		cursor: pointer;
		--el-input-placeholder-color: rgba(255, 255, 255, 0.7);
		.el-input__wrapper {
			display: inline-flex;
			flex-grow: 1;
			align-items: center;
			justify-content: center;
			padding: 1px 11px;
			background-color: transparent;
			background-image: none;
			border-radius: var(
				--el-input-border-radius,
				var(--el-border-radius-base)
			);
			transition: var(--el-transition-box-shadow);
			box-shadow: 0 0;
			.el-input__prefix {
				color: rgba(255, 255, 255, 0.7);
			}
			.el-input__inner {
				width: 100px;
				color: rgba(255, 255, 255, 0.7);
			}
		}
	}
}
.jp-search-no-result {
	padding: 5px;
	background-color: #fff;
	box-sizing: border-box;
	color: #777;
	box-shadow: 2px 2px 19px #e0e0e0;
	z-index: 900;
}
.jp-search-result {
	position: fixed;
	z-index: 1000;
	margin-top: 15px;
	box-shadow: 2px 2px 19px #e0e0e0;
}
.jp-search-result li {
	padding: 10px;
	min-width: 150px;
	color: var(--el-menu-text-color);
	background: var(--el-bg-color-overlay);
	list-style: none;
	border-radius: 4px;
	/* margin-bottom: 5px; */
	font-size: 14px;
	display: flex;
	align-items: center;
	cursor: pointer;
}
.jp-search-result li i {
	font-size: 20px;
	margin-right: 15px;
}
.jp-search-result li:hover {
	color: var(--el-menu-hover-text-color);
	background-color: var(--el-menu-hover-bg-color);
}
</style>
