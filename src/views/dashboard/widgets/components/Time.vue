<template>
	<el-card shadow="hover" header="时钟" class="item-background">
		<div class="time">
			<h2>{{ time }}</h2>
			<p>{{ day }}</p>
		</div>
	</el-card>
</template>

<script>
export default {
	title: "时钟",
	icon: "el-icon-clock",
	description: "显示系统时间",
	layout: {
		w: 4,
		h: 4,
	},
	data() {
		return {
			time: "",
			day: "",
		};
	},
	mounted() {
		this.showTime();
		setInterval(() => {
			this.showTime();
		}, 1000);
	},
	methods: {
		showTime() {
			this.time = this.$TOOL.dateFormat(new Date(), "hh:mm:ss");
			this.day = this.$TOOL.dateFormat(new Date(), "yyyy年MM月dd日");
		},
	},
};
</script>

<style scoped>
.item-background {
	height: 100%;
	background: linear-gradient(to right, #4776e6, #8e54e9);
	color: #fff;
}
.time h2 {
	font-size: 40px;
}
.time p {
	font-size: 14px;
	margin-top: 3px;
	opacity: 0.7;
}
</style>
