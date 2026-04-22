const option = {
	dataset: {
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
	series: [
		{
			name: "",
			type: "pie",
			radius: "50%",
			emphasis: {
				itemStyle: {
					shadowBlur: 10,
					shadowOffsetX: 0,
					shadowColor: "rgba(0, 0, 0, 0.5)",
				},
			},
		},
	],
};
function transf(option, data) {
	option.series[0].name = data.columns[1];
	data.rows.forEach((element) => {
		option.dataset.source.push({
			name: element[data.columns[0]],
			value: element[data.columns[1]],
		});
	});
	return option;
}

export default function getData(data) {
	let op = JSON.parse(JSON.stringify(option));
	return transf(op, data);
}
