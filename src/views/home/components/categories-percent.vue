<template>
	<el-card
		shadow="never"
		class="general-card"
		:header-style="{ paddingBottom: '0' }"
		:body-style="{
			padding: '20px',
		}"
	>
		<template #header>
			<div class="card-header">
				<span>
					<div class="person-bg">
						<el-icon size="14"><yonghu-7></yonghu-7></el-icon>
					</div>
					<label class="person-text"> 客户来源分析</label>
				</span>
			</div>
		</template>
		<v-chart height="190px" :option="option"></v-chart>
	</el-card>
</template>

<script>
export default {
	data() {
		return {
			option: {
				textStyle: {
					fontFamily: 'Inter, "Helvetica Neue", Arial, sans-serif',
				},
				title: {
					text: "客户来源",
					left: "center",
				},
				tooltip: {
					trigger: "item",
					formatter: "{a} <br/>{b} : {c} ({d}%)",
				},
				legend: {
					orient: "vertical",
					left: "left",
					data: ["电商平台", "微信", "电话", "邮件推广", "电视广告"],
				},
				series: [
					{
						name: "访问来源",
						type: "pie",
						radius: "55%",
						center: ["50%", "60%"],
						data: [
							{ value: 335, name: "电商平台" },
							{ value: 310, name: "微信" },
							{ value: 234, name: "电话" },
							{ value: 135, name: "邮件推广" },
							{ value: 1548, name: "电视广告" },
						],
						emphasis: {
							itemStyle: {
								shadowBlur: 10,
								shadowOffsetX: 0,
								shadowColor: "rgba(0, 0, 0, 0.5)",
							},
						},
					},
				],
			},
		};
	},
};
</script>

<style lang="less">
.general-card {
	border: none;
	.el-card__header {
		padding: calc(var(--el-card-padding) - 2px) var(--el-card-padding);
		border-bottom: 1px solid var(--el-card-border-color);
		box-sizing: border-box;
	}
	.person-bg {
		color: rgb(179, 127, 233);
		background-color: rgba(179, 127, 233, 0.15);
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
</style>
