<template>
	<div class="adminui-topbar">
		<div class="left-panel">
			<el-breadcrumb
				v-if="toolbarTags"
				separator-icon="arrow-right"
				class="hidden-sm-and-down"
			>
				<transition-group name="breadcrumb" mode="out-in">
					<template v-for="item in breadList">
						<el-breadcrumb-item
							v-if="item.path != '/'"
							:key="item.meta.title"
							><el-icon
								:size="18"
								class="icon"
								v-if="item.meta.icon"
								><component :is="item.meta.icon" /></el-icon
							>{{ item.meta.title }}</el-breadcrumb-item
						>
					</template>
				</transition-group>
			</el-breadcrumb>
		</div>
		<div class="center-panel"></div>
		<div class="right-panel">
			<slot></slot>
		</div>
	</div>
</template>

<script>
export default {
	data() {
		return {
			breadList: [],
		};
	},
	created() {
		this.getBreadcrumb();
	},
	watch: {
		$route() {
			this.getBreadcrumb();
		},
	},
	computed: {
		toolbarTags() {
			return (
				this.$store.state.global.toolbarTags &&
				this.$route.meta.hiddenBreadcrumb !== "1"
			);
		},
	},
	methods: {
		getBreadcrumb() {
			let matched = this.$route.meta.breadcrumb || [
				{
					meta: {
						title:
							this.$route.query.title || this.$route.meta.title,
					},
				},
			];
			this.breadList = matched;
			console.log(this.$route);
		},
	},
};
</script>

<style lang="less" scoped>
.adminui-topbar {
	display: flex;
	justify-content: space-between;
	margin: 0;
	padding: 0px;
	background: #fff;
	border-bottom: 1px solid #e8eaec;
	.left-panel {
		display: flex;
		align-items: center;
	}
	.right-panel {
		display: flex;
		align-items: center;
	}
}
.el-breadcrumb {
	margin-left: 15px;
}
.el-breadcrumb .el-breadcrumb__inner .icon {
	font-size: 14px;
	margin-right: 5px;
	float: left;
}
.breadcrumb-enter-active,
.breadcrumb-leave-active {
	transition: all 0.3s;
}
.breadcrumb-enter-from,
.breadcrumb-leave-active {
	opacity: 0;
	transform: translateX(20px);
}
.breadcrumb-leave-active {
	position: absolute;
}
</style>
