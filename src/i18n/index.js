import { createI18n } from "vue-i18n";
import zhCN from "vxe-table/lib/locale/lang/zh-CN";

const messages = {
	zh: {
		...zhCN,
	},
};

const i18n = createI18n({
	locale: "zh-cn",
	messages,
});

export default i18n;
