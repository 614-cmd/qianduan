<template>
	<div class="page">
		<el-table v-if="type === 'table'" border :data="chartData.rows">
			<el-table-column
				v-for="(column, index) in chartData.columns"
				:key="index"
				:prop="column"
				:label="column"
			>
			</el-table-column>
		</el-table>
		<v-chart v-else :option="activeOption"></v-chart>
	</div>
</template>

<script>
import echartsService from "@/api/echarts/echartsService";
let requireComponent = import.meta.globEager("./data/**/*.js");
const modules = {};

for (const key in requireComponent) {
	if (Object.prototype.hasOwnProperty.call(requireComponent, key)) {
		modules[key.replace(/(\.\/|\.ts)/g, "")] =
			requireComponent[key].default;
	}
}
export default {
	data() {
		return {
			type: "",
			chartData: {},
			activeOption: {},
		};
	},
	activated() {
		echartsService.getChartData(this.$route.query.id).then((data) => {
			this.activeOption = JSON.parse(data.echarts.option);
			this.type = data.echarts.type;
			this.chartData = data.chartData;
			let defaultOption = modules[`data/${this.type}.js`];
			if (defaultOption) {
				this.activeOption = {
					...defaultOption(this.chartData),
					...this.activeOption,
				};
			}
		});
	},
};
</script>
