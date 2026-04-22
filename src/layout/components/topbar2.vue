<template>
	<div class="adminui-topbar inner-bar">
		<div class="left-panel">
			<el-breadcrumb
				v-if="toolbarTags"
				separator-icon="fxy"
				class="hidden-sm-and-down"
			>
				<transition-group name="breadcrumb" mode="out-in">
					<template v-for="item in breadList">
						<el-breadcrumb-item
							v-if="item.path != '/'"
							:key="item.meta.title"
						>
							<el-icon size="12px"
								><component :is="item.meta.icon"
							/></el-icon>
							{{ item.meta.title }}
						</el-breadcrumb-item>
					</template>
				</transition-group>
			</el-breadcrumb>
		</div>
		<div class="ivu-page-header-title">
			{{ breadList[breadList.length - 1].meta.title }}
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
		},
	},
};
</script>

<style lang="less">
.adminui-topbar {
	padding: 16px 16px 10px 16px;
	margin: 0;
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
.adminui-topbar.inner-bar {
	.left-panel {
		margin-bottom: 16px;
		.el-breadcrumb {
			margin-left: 15px;
		}
		.icon {
			color: #86909c;
		}
		.el-breadcrumb__item .el-breadcrumb__inner {
			color: #86909c;
		}
		.el-breadcrumb__item:last-child .icon {
			color: rgb(78, 89, 105);
		}
		.el-breadcrumb__item:last-child .el-breadcrumb__inner {
			color: rgb(78, 89, 105);
		}
	}
	.ivu-page-header-title {
		display: inline-block;
		color: #17233d;
		font-weight: 500;
		font-size: 20px;
		margin-bottom: 16px;
		margin-left: 12px;
	}
}
// .el-breadcrumb {margin-left: 15px;}
</style>
