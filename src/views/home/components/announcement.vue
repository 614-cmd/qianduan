<template>
	<el-card shadow="never" class="notify">
		<template #header>
			<div class="card-header">
				<span>
					<div class="info-bg">
						<el-icon size="14"><tonggao5></tonggao5></el-icon>
					</div>
					<label class="info-text">公告</label>
				</span>
				<el-button type="primary" text>更多</el-button>
			</div>
		</template>
		<div>
			<div v-for="(item, idx) in list" :key="idx" class="item">
				<el-tag :type="item.type">{{ item.label }}</el-tag>
				<span class="item-content">
					{{ item.content }}
				</span>
			</div>
		</div>
	</el-card>
</template>

<script>
export default {
	data() {
		return {
			list: [
				{
					type: "warning",
					label: "活动",
					content: "内容最新优惠活动",
				},
				{
					type: "success",
					label: "消息",
					content: "新增内容尚未通过审核，详情请点击查看。",
				},
				{
					type: "",
					label: "通知",
					content: "当前产品试用期即将结束，如需续费请点击查看。",
				},
				{
					type: "",
					label: "通知",
					content: "1月新系统升级计划通知",
				},
				{
					type: "danger",
					label: "消息",
					content: "新增内容已经通过审核，详情请点击查看。",
				},
				{
					type: "",
					label: "通知",
					content: "1月新系统升级计划通知",
				},
				{
					type: "danger",
					label: "消息",
					content: "新增内容已经通过审核，详情请点击查看。",
				},
			],
		};
	},
};
</script>

<style lang="less">
.notify {
	border: none;
	.el-card__header {
		padding: calc(var(--el-card-padding) - 2px) var(--el-card-padding);
		border-bottom: 1px solid var(--el-card-border-color);
		box-sizing: border-box;
	}
	.info-bg {
		color: rgb(252, 130, 192);
		background-color: rgba(252, 130, 192, 0.15);
		width: 24px;
		height: 24px;
		line-height: 27px;
		border-radius: 50%;
		text-align: center;
		vertical-align: middle;
		display: inline-block;
	}
	.info-text {
		font-size: 15px;
		margin-left: 5px;
		font-weight: 400;
	}
}
.item {
	display: flex;
	align-items: center;
	width: 100%;
	height: 24px;
	margin-bottom: 6px;
	.item-content {
		flex: 1;
		overflow: hidden;
		text-overflow: ellipsis;
		white-space: nowrap;
		margin-left: 4px;
		color: var(--color-text-2);
		text-decoration: none;
		font-size: 13px;
		cursor: pointer;
	}
}
</style>
