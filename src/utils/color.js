import config from "@/config";
export default {
	//hex颜色转rgb颜色
	HexToRgb(str) {
		str = str.replace("#", "");
		var hxs = str.match(/../g);
		for (var i = 0; i < 3; i++) hxs[i] = parseInt(hxs[i], 16);
		return hxs;
	},
	//rgb颜色转hex颜色
	RgbToHex(a, b, c) {
		var hexs = [a.toString(16), b.toString(16), c.toString(16)];
		for (var i = 0; i < 3; i++) {
			if (hexs[i].length == 1) hexs[i] = "0" + hexs[i];
		}
		return "#" + hexs.join("");
	},
	//加深
	darken(color, level) {
		var rgbc = this.HexToRgb(color);
		for (var i = 0; i < 3; i++) rgbc[i] = Math.floor(rgbc[i] * (1 - level));
		return this.RgbToHex(rgbc[0], rgbc[1], rgbc[2]);
	},
	//变淡
	lighten(color, level) {
		var rgbc = this.HexToRgb(color);
		for (var i = 0; i < 3; i++)
			rgbc[i] = Math.floor((255 - rgbc[i]) * level + rgbc[i]);
		return this.RgbToHex(rgbc[0], rgbc[1], rgbc[2]);
	},
	setAppColors(app_color) {
		document.documentElement.style.setProperty(
			"--el-color-primary",
			app_color
		);
		document.documentElement.style.setProperty(
			"--el-color-primary-focus",
			app_color + "33"
		);
		if (app_color == config.COLOR) {
			document.documentElement.style.setProperty(
				"--el-color-header-left",
				"rgb(29 66 171)"
			);
			document.documentElement.style.setProperty(
				"--el-color-header-center",
				"rgb(33 115 220)"
			);
			document.documentElement.style.setProperty(
				"--el-color-header-right",
				"#1e93ff"
			);
		} else {
			document.documentElement.style.setProperty(
				"--el-color-header-left",
				this.darken(app_color, 0.4)
			);
			document.documentElement.style.setProperty(
				"--el-color-header-center",
				app_color
			);
			document.documentElement.style.setProperty(
				"--el-color-header-right",
				this.lighten(app_color, 0.2)
			);
		}

		for (let i = 1; i <= 9; i++) {
			document.documentElement.style.setProperty(
				`--el-color-primary-light-${i}`,
				this.lighten(app_color, i / 10)
			);
			document.documentElement.style.setProperty(
				`--el-color-primary-dark-${i}`,
				this.darken(app_color, i / 10)
			);
		}
	},
};
