<template>
	<el-card
		shadow="never"
		class="docs"
		:header-style="{ paddingBottom: '0' }"
		:body-style="{
			padding: '20px',
		}"
	>
		<template #header>
			<div class="card-header">
				<span>
					<div class="gouwuche-bg">
						<el-icon size="14"><gouwuche></gouwuche></el-icon>
					</div>
					<label class="gouwuche-text">获取授权</label>
				</span>
			</div>
		</template>
		<el-descriptions
			:column="1"
			class="auth"
			title="Jeeplus单体版 商业授权"
		>
			<el-descriptions-item
				>本软件受版权法保护，未经授权，不得用于商业用途！</el-descriptions-item
			>
		</el-descriptions>
		<el-row>
			<el-col :span="6">
				<el-link
					:underline="false"
					href="http://www.jeeplus.org/#/store/detail?id=f717568bf0c24fff86e3ca01c1f6ff95"
					target="_blank"
				>
					获取授权
				</el-link>
			</el-col>
			<el-col :span="6">
				<el-link
					:underline="false"
					href="http://www.jeeplus.org/#/store/detail?id=f717568bf0c24fff86e3ca01c1f6ff95"
					target="_blank"
				>
					使用指南
				</el-link>
			</el-col>
			<el-col :span="6">
				<el-link
					:underline="false"
					href="http://www.jeeplus.org/#/store/detail?id=f717568bf0c24fff86e3ca01c1f6ff95"
					target="_blank"
				>
					接入流程
				</el-link>
			</el-col>
			<el-col :span="6">
				<el-link
					:underline="false"
					href="http://www.jeeplus.org/#/store/detail?id=f717568bf0c24fff86e3ca01c1f6ff95"
					target="_blank"
				>
					接口文档
				</el-link>
			</el-col>
		</el-row>
	</el-card>
</template>

<style lang="less">
.docs {
	border: none;
	.el-card__header {
		padding: calc(var(--el-card-padding) - 2px) var(--el-card-padding);
		border-bottom: 1px solid var(--el-card-border-color);
		box-sizing: border-box;
	}
	.auth {
		margin-bottom: 35px;
	}
	.gouwuche-bg {
		color: rgb(251, 173, 19);
		background-color: rgba(251, 173, 19, 0.15);
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
		color: var(--el-color-primary);
	}
}
</style>
