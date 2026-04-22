<template>
	<el-card
		shadow="never"
		class="wx"
		:body-style="{
			padding: '20px',
		}"
	>
		<template #header>
			<div class="card-header">
				<span>
					<div class="gouwuche-bg">
						<el-icon size="14"><weixin></weixin></el-icon>
					</div>
					<label class="gouwuche-text">联系客服</label>
				</span>
			</div>
		</template>
		<div style="width: 100%; text-align: center">
			<img
				style="width: 150px; height: 150px"
				src="~@/assets/img/wx.png"
			/>
		</div>
	</el-card>
</template>

<style lang="less">
.wx {
	border: none;
	.el-card__header {
		padding: calc(var(--el-card-padding) - 2px) var(--el-card-padding);
		border-bottom: 1px solid var(--el-card-border-color);
		box-sizing: border-box;
	}
	.gouwuche-bg {
		color: rgb(27, 189, 107);
		background-color: rgba(27, 189, 107, 0.15);
		width: 24px;
		height: 24px;
		line-height: 27px;
		border-radius: 50%;
		text-align: center;
		vertical-align: middle;
		display: inline-block;
	}
	.gouwuche-text {
		font-size: 15px;
		margin-left: 5px;
		font-weight: 400;
	}
	.el-link {
		height: 30px;
		margin: 10px 0;
		color: rgb(78, 89, 105);
		display: inline-block;
		padding: 1px 4px;
		// color: rgb(22,93,255);
		font-size: 14px;
		line-height: 1.5715;
		text-decoration: none;
		background-color: transparent;
		border-radius: 2px;
		cursor: pointer;
		transition: all 0.1s cubic-bezier(0, 0, 1, 1);
	}
	.el-link:hover {
		background-color: #e5e6eb;
	}
}
</style>
