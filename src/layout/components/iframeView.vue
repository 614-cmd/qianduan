<template>
	<div v-show="$route.meta.target == 'iframe'" class="iframe-pages">
		<iframe
			v-for="item in iframeList"
			:key="item.fullPath"
			v-show="$route.fullPath == item.fullPath"
			:src="toURL(item)"
			frameborder="0"
		></iframe>
	</div>
</template>

<script>
import { isURL } from "@/utils/validate";
export default {
	setup() {
		return {
			apiUrl: import.meta.env.VITE_APP_BASE,
		};
	},
	data() {
		return {};
	},
	watch: {
		$route(e) {
			this.push(e);
		},
	},
	created() {
		this.push(this.$route);
	},
	computed: {
		iframeList() {
			return this.$store.state.iframe.iframeList;
		},
		ismobile() {
			return this.$store.state.global.ismobile;
		},
		layoutTags() {
			return this.$store.state.global.layoutTags;
		},
	},
	mounted() {},
	methods: {
		toURL(item) {
			let query = this.$route.query;
			let params = "";
			for (let i in query) {
				params += "&" + i + "=" + query[i];
			}
			if (params != "") {
				params = "?" + params;
			}

			return (
				item.query.iframeUrl ||
				(isURL(item.meta.url)
					? item.meta.url + params
					: this.apiUrl + item.meta.url + params)
			);
		},
		push(route) {
			if (route.meta.target == "iframe") {
				if (this.ismobile || !this.layoutTags) {
					this.$store.commit("setIframeList", route);
				} else {
					this.$store.commit("pushIframeList", route);
				}
			} else {
				if (this.ismobile || !this.layoutTags) {
					this.$store.commit("clearIframeList");
				}
			}
		},
	},
};
</script>

<style scoped>
.adminui-topbar + .iframe-pages {
	height: calc(100% - 119px);
	margin: 8px 12px;
}
.iframe-pages {
	/* width:100%; */
	height: calc(100% - 16px);
	margin: 8px 12px;
	background: #fff;
}
iframe {
	border: 0;
	width: 100%;
	height: 100%;
	display: block;
}
</style>
