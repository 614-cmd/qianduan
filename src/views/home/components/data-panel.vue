<template>
	<el-row :cols="24" :gutter="16" class="panel">
		<el-col class="panel-col" :span="6">
			<statistic
				:start="0"
				:end="10000"
				icon="document"
				title="线上总内容"
				:duration="10"
			/>
		</el-col>
		<el-col class="panel-col" :span="6">
			<statistic
				:start="0"
				:end="389"
				icon="document-checked"
				title="	投放中内容"
				:duration="10"
			/>
		</el-col>
		<el-col class="panel-col" :span="6">
			<statistic
				:start="0"
				:end="7920"
				icon="chat-dot-round"
				title="日新增评论"
				:duration="10"
			/>
		</el-col>
		<el-col class="panel-col" :span="6">
			<statistic
				:start="0"
				:end="180"
				icon="top-right"
				title="较昨日新增"
				:duration="10"
			/>
		</el-col>
		<el-col :span="24">
			<el-divider class="panel-border" />
		</el-col>
	</el-row>
</template>

<script>
import statistic from "@/components/statistic";
export default {
	components: {
		statistic,
	},
};
</script>

<style lang="less" scoped>
.panel-border {
	margin-bottom: 0;
	// padding-bottom: 20px;
	border-top: 0;
	border-bottom: 1px solid rgb(242, 243, 245);
}
.panel-col {
	padding-left: 43px;
	border-right: 1px solid rgb(var(--gray-2));
}
.col-avatar {
	margin-right: 12px;
	background-color: var(--color-fill-2);
}
.up-icon {
	color: rgb(var(--red-6));
}
.unit {
	margin-left: 8px;
	color: rgb(var(--gray-8));
	font-size: 12px;
}
:deep(.panel-border) {
	margin: 4px 0 0 0;
}
</style>
