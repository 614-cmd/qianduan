<template>
	<v-charts
		ref="vueEcharts"
		:option="myOptions"
		:style="{ height: height, width: width }"
	></v-charts>
</template>

<script>
import * as echarts from "echarts";
import vCharts from "vue-echarts";

export default {
	...echarts,
	name: "vueEcharts",
	components: {
		vCharts,
	},
	data() {
		return {
			isActive: false,
		};
	},
	props: {
		height: { type: String, default: "100%" },
		width: { type: String, default: "100%" },
		nodata: { type: Boolean, default: false },
		option: { type: Object, default: () => {} },
	},
	computed: {
		myOptions: function () {
			return this.option || {};
		},
	},
	activated() {
		if (!this.isActive) {
			this.$nextTick(() => {
				this.resize();
			});
		}
	},
	deactivated() {
		this.isActive = false;
	},
	mounted() {
		this.isActive = true;
		this.$nextTick(() => {
			this.resize();
			window.addEventListener("resize", () => this.resize());
		});
	},
	methods: {
		resize() {
			this.$refs.vueEcharts && this.$refs.vueEcharts.resize();
		},
		clear() {
			this.$refs.vueEcharts && this.$refs.vueEcharts.clear();
		},
	},
};
</script>
