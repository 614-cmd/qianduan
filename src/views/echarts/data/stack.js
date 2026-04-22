const option = {
	dataset: {
		dimensions: [],
		source: [],
	},
	title: {
		show: true,
		text: "",
		left: "auto",
		textStyle: {
			color: "#333",
			fontSize: 18,
		},
		subtext: "",
		subtextStyle: {
			color: "#aaa",
			fontSize: 12,
		},
	},
	tooltip: {
		textStyle: {
			color: "#333",
			fontSize: 14,
		},
	},
	grid: {
		left: 10,
		top: 60,
		right: 10,
		bottom: 60,
	},
	legend: {
		show: true,
		orient: "horizontal",
		textStyle: {
			fontSize: 12,
			color: "#333",
		},
	},
	color: [],
	yAxis: { type: "category" },
	xAxis: { type: "value" },
	series: [],
};
function transf(option, data) {
	option.dataset.dimensions = data.columns;
	option.dataset.source = data.rows;
	for (var i = 0; i < data.columns.length; i++) {
		option.series.push({
			type: "bar",
			stack: "Total",
			label: {
				show: true,
			},
		});
	}
	return option;
}

export default function getData(data) {
	let op = JSON.parse(JSON.stringify(option));
	return transf(op, data);
}
