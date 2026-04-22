<template>
	<el-card
		shadow="hover"
		:header="title"
		style="height: 100%"
		body-style="height:100%"
		class="item-background"
	>
		<el-table v-if="type === 'table'" border :data="chartData.rows">
			<el-table-column
				v-for="(column, index) in chartData.columns"
				:key="index"
				:prop="column"
				:label="column"
			>
			</el-table-column>
		</el-table>
		<v-chart
			ref="chart"
			v-else
			style="height: 100%; width: 100%"
			:option="activeOption"
		></v-chart>
	</el-card>
</template>

<script>
import echartsService from "@/api/echarts/echartsService";
export default {
	title: "工作流",
	icon: "liucheng1",
	description: "工作流常用菜单",
	props: {
		id: String,
		title: String,
		grid: Object,
		w: String,
		h: String,
	},
	layout: {
		w: 8,
		h: 4,
	},
	data() {
		return {
			type: "",
			chartData: {},
			activeOption: {},
		};
	},
	watch: {
		w() {
			if (this.type !== "table") {
				this.$refs.chart.resize();
			}
		},
		h() {
			if (this.type !== "table") {
				this.$refs.chart.resize();
			}
		},
	},
	created() {
		echartsService.getChartData(this.id).then((data) => {
			this.type = data.echarts.type;
			this.chartData = data.chartData;
			if (this.type != "table") {
				this.activeOption = JSON.parse(data.echarts.option);
				this.activeOption = {
					...import(`./../../../data/${this.type}`).default(
						this.chartData
					),
					...this.activeOption,
				};
			}
		});
	},
};
</script>
