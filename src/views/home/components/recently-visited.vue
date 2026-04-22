<template>
	<el-card title="最近访问" :body-style="{ paddingTop: '26px' }">
		<template #header>
			<div class="card-header">
				<span>快捷操作</span>
				<el-button type="primary" text>管理</el-button>
			</div>
		</template>
		<div style="margin-bottom: -1rem">
			<el-row :gutter="8">
				<el-col
					v-for="link in links"
					:key="link.text"
					:span="8"
					class="wrapper"
				>
					<el-link :underline="false">
						<el-icon size="12px" class="icon">
							<component :is="link.icon"></component>
						</el-icon>
						<p class="text">
							{{ link.text }}
						</p>
					</el-link>
				</el-col>
			</el-row>
		</div>
	</el-card>
</template>

<script>
export default {
	data() {
		return {
			links: [
				{
					text: "内容管理",
					icon: "document",
				},
				{
					text: "内容分析",
					icon: "notebook",
				},
				{
					text: "高级管理",
					icon: "setting",
				},
			],
		};
	},
};
</script>

<style lang="less" scoped>
.wrapper {
	margin-bottom: 18px;
	text-align: center;
}
.icon {
	display: inline-block;
	margin-bottom: 4px;
	color: rgb(var(23, 23, 26));
	line-height: 32px;
	text-align: center;
	background-color: rgb(247, 248, 250);
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
</style>
