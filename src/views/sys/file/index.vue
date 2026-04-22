<template>
	<div id="fileview" class="page-white" style="padding: 0"></div>
</template>
<script>
import httpRequest from "@/utils/httpRequest";
export default {
	data() {
		return {
			videoVisible: false,
			audioVisible: false,
			imageVisible: false,
			pdfVisible: false,
			url: "",
		};
	},
	mounted() {
		window.webix.ready(function () {
			if (window.webix.env.mobile) window.webix.ui.fullScreen();

			// use custom scrolls, optional
			if (!window.webix.env.touch && window.webix.env.scrollSize)
				window.webix.CustomScroll.init();
			window.fileManager.locales.ru = window.ruLocale;
			window.fileManager.locales.zh = window.zhLocale;
			window.webix.ui({
				id: "fm",
				container: "fileview",
				view: "filemanager",
				url: `${httpRequest.BASE_URL}/system/sys/file/list/`,
				mode: "cards",
				locale: {
					lang: "zh",
					webix: {
						// switch all webix widgets to the selected locale
						en: "en-US",
						zh: "zh-CN",
						ru: "ru-RU",
					},
				},
				readonly: false,
				handlers: {
					upload: `${httpRequest.BASE_URL}/sys/file/upload`,
					download: `${httpRequest.BASE_URL}/sys/file/download`,
					copy: `${httpRequest.BASE_URL}/sys/file/copy`,
					move: `${httpRequest.BASE_URL}/sys/file/move`,
					remove: `${httpRequest.BASE_URL}/sys/file/remove`,
					rename: `${httpRequest.BASE_URL}/sys/file/rename`,
					create: `${httpRequest.BASE_URL}/sys/file/createFolder`,
				},
			});
		});
	},
};
</script>
