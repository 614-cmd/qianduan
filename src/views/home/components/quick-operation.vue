<template>
	<el-card shadow="never" class="quick">
		<template #header>
			<div class="card-header">
				<span>
					<div class="person-bg">
						<el-icon size="14"><xitong2></xitong2></el-icon>
					</div>
					<label class="person-text">快捷操作</label>
				</span>
			</div>
		</template>
		<el-row :gutter="8">
			<el-col
				v-for="(link, index) in links"
				:key="index"
				:span="8"
				class="wrapper"
			>
				<router-link :underline="false" :to="link.path">
					<el-icon
						size="18px"
						:style="{ color: link.color }"
						class="icon"
					>
						<component :is="link.icon"></component>
					</el-icon>
					<p class="text">
						{{ link.text }}
					</p>
				</router-link>
			</el-col>
		</el-row>
	</el-card>
</template>

<script>
export default {
	data() {
		return {
			links: [
				{
					text: "用户管理",
					icon: "yonghu7",
					color: "#5cadff",
					path: "/sys/user/UserList",
				},
				{
					text: "角色管理",
					icon: "jiaose5",
					color: "#ff9900",
					path: "/sys/role/RoleList",
				},
				{
					text: "岗位管理",
					icon: "gangwei2",
					color: "#19be6b",
					path: "/sys/post/PostList",
				},
				{
					text: "机构管理",
					icon: "jigou5",
					color: "#ed4014",
					path: "/sys/office/OfficeList",
				},
				{
					text: "菜单管理",
					icon: "caidan3",
					color: "#fe84c0",
					path: "/sys/menu/MenuList",
				},
				{
					text: "区域管理",
					icon: "quyu1",
					color: "#94de64",
					path: "/sys/area/AreaList",
				},
				{
					text: "信箱",
					icon: "xinxiang",
					color: "#5bdbd2",
					path: "/mailbox/index",
				},
				{
					text: "我的日程",
					icon: "richeng2",
					color: "#ffc069",
					path: "/calendar/MyCalendar",
				},
				{
					text: "在线用户",
					icon: "zaixianyonghu",
					color: "#795548",
					path: "/monitor/OnlineUserList",
				},
				{
					text: "日志查询",
					icon: "rizhi2",
					color: "#1d31a3",
					path: "/sys/log/LogList",
				},
			],
		};
	},
};
</script>

<style lang="less">
.quick {
	border: none;
	.el-card__header {
		padding: calc(var(--el-card-padding) - 2px) var(--el-card-padding);
		border-bottom: 1px solid var(--el-card-border-color);
		box-sizing: border-box;
	}
	.person-bg {
		color: rgb(24, 144, 255);
		background-color: rgba(24, 144, 255, 0.15);
		line-height: 27px;
		width: 24px;
		height: 24px;
		border-radius: 50%;
		text-align: center;
		vertical-align: middle;
		display: inline-block;
	}
	.person-text {
		font-size: 15px;
		margin-left: 5px;
		font-weight: 400;
	}
}
.wrapper {
	margin-bottom: 18px;
	text-align: center;
	.el-link__inner {
		display: inline;
	}
	.icon {
		display: inline-block;

		margin-bottom: 4px;
		color: rgb(var(23, 23, 26));
		line-height: 32px;
		font-size: 16px;
		text-align: center;
		border-radius: 4px;
	}
	a:focus,
	a:hover {
		text-decoration: none;
	}
	.text {
		font-size: 12px;
		text-align: center;
		// color: rgb(78,89,105);
		display: block;
		margin-top: 0;
		margin-bottom: 1em;
	}
}
</style>
