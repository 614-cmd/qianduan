import dictService from "@/api/sys/dictService";
import tool from "@/utils/tool";

export function getDictLabel(type, value, defaultLabel) {
	if ((!value && value !== 0) || (!type && type !== 0)) {
		if (defaultLabel !== undefined) {
			return defaultLabel;
		} else {
			return "--";
		}
	}
	let dictList = tool.data.get("DICT_LIST") || [];
	let dicts = dictList[type];
	if (dicts) {
		for (let i = 0; i < dicts.length; i++) {
			if (
				dicts[i].value &&
				dicts[i].value.toString() === value.toString()
			) {
				return dicts[i].label;
			}
		}
	}
	if (defaultLabel !== undefined) {
		return defaultLabel;
	} else {
		return "--";
	}
}

export function getDictValue(type, label, defaultValue) {
	if ((!label && label !== 0) || (!type && type !== 0)) {
		if (defaultValue !== undefined) {
			return defaultValue;
		} else {
			return "--";
		}
	}
	let dictList = tool.data.get("DICT_LIST") || [];
	let dicts = dictList[type];
	if (dicts) {
		for (let i = 0; i < dicts.length; i++) {
			if (
				dicts[i].label &&
				dicts[i].label.toString() === label.toString()
			) {
				return dicts[i].value;
			}
		}
	}
	if (defaultValue !== undefined) {
		return defaultValue;
	} else {
		return "--";
	}
}

export function getDictList(type) {
	if (!type && type !== 0) {
		return [];
	}
	let dictList = tool.data.get("DICT_LIST") || [];
	let dicts = dictList[type];
	return dicts || [];
}

export function refreshDictList() {
	dictService.getDictMap().then((data) => {
		tool.data.set("DICT_LIST", data || []);
	});
}

export default { getDictLabel, getDictValue, getDictList, refreshDictList };
