const option = {
	tooltip: {
		formatter: "{a} <br/>{b} : {c}%",
	},
	series: [
		{
			name: "",
			type: "gauge",
			progress: {
				show: true,
			},
			detail: {
				valueAnimation: true,
				formatter: "{value}",
			},
			data: [],
		},
	],
};

function transf(option, data) {
	option.series[0].name = data.columns[1];
	option.series[0].data.push({
		name: data.rows[0][data.columns[0]],
		value: data.rows[0][data.columns[1]],
	});

	return option;
}

export default function getData(data) {
	let op = JSON.parse(JSON.stringify(option));
	return transf(op, data);
}
