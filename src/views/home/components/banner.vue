<template>
	<div class="top-layout">
		<div class="banner">
			<el-avatar :size="80" :src="currentUser.photo"></el-avatar>
		</div>
		<el-descriptions
			:column="2"
			class="desc"
			:title="`欢迎回来！` + currentUser.name"
		>
			<el-descriptions-item width="300px" label="用户名:">{{
				currentUser.name
			}}</el-descriptions-item>
			<el-descriptions-item label="电话:">{{
				currentUser.phone
			}}</el-descriptions-item>
			<el-descriptions-item label="公司:">
				<el-tag size="small">{{
					currentUser?.companyDTO?.name
				}}</el-tag>
			</el-descriptions-item>
			<el-descriptions-item label="部门:">
				<el-tag size="small">{{ currentUser?.officeDTO?.name }}</el-tag>
			</el-descriptions-item>
		</el-descriptions>
	</div>
</template>

<script>
export default {
	computed: {
		currentUser() {
			var userInfo = this.$TOOL.data.get("USER_INFO");
			return userInfo;
		},
	},
};
</script>

<style lang="less">
.banner {
	display: inline-block;
	padding: 20px 20px 0 20px;
	background-color: #fff;
	border-radius: 4px 4px 0 0;
}
.desc {
	float: right;
	padding: 20px;
	.el-descriptions__title {
		color: rgba(0, 0, 0, 0.85);
		font-weight: 600;
		font-size: 20px;
		line-height: 1.4;
	}
}
.top-layout {
	display: flex;
	flex-wrap: wrap;
	position: relative;
	box-sizing: border-box;
}

.panel-border {
	margin-bottom: 0;
	padding-bottom: 20px;
	border-top: 0;
	border-bottom: 1px solid rgb(242, 243, 245);
}
:deep(.arco-icon-home) {
	margin-right: 6px;
}
h5.ele-typography {
	float: right;
	font-size: 20px;
}
</style>
