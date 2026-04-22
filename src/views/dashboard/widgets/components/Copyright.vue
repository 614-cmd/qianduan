<template>
	<el-card shadow="hover" style="height: 100%" header="版权信息">
		<div style="height: 210px; text-align: center">
			<img src="/img/logo.png" style="height: 140px" />
			<h2 style="margin-top: 15px">
				Jeeplus SAAS微服务平台 {{ $CONFIG.CORE_VER }}
			</h2>
			<p style="margin-top: 5px">最新版本 {{ ver }}</p>
		</div>
		<div style="margin-top: 20px">
			<p>本软件受版权法保护，未经授权，不得用于商业用途!</p>
		</div>
	</el-card>
</template>

<script>
export default {
	title: "版权信息",
	icon: "banquan",
	description: "JEEPLUS 微服务平台",
	layout: {
		w: 8,
		h: 8,
	},
	data() {
		return {
			ver: "9.7",
		};
	},
};
</script>
