<template>
	<el-dialog
		title="图库"
		width="80%"
		class="imgList avue-dialog"
		:close-on-click-modal="false"
		v-model="imgVisible"
	>
		<el-tabs class="imgList__header menu__tabs" v-model="imgActive">
			<el-tab-pane
				:label="item.name"
				v-for="(item, index) in imgList"
				:key="index"
				:name="index"
			></el-tab-pane>
		</el-tabs>
		<div class="imgList__content">
			<el-upload
				v-if="imgActive == 3"
				class="imgList__border"
				:on-success="onSuccess"
				:on-progress="onProgress"
				:show-file-list="false"
				:http-request="request"
				multiple
				list-type="picture"
			>
				<el-button
					:loading="loading"
					icon="el-icon-upload"
					type="primary"
					>点击上传</el-button
				>
			</el-upload>
			<div
				class="imgList__border"
				:key="index"
				v-for="(item, index) in imgList[imgActive].list"
			>
				<img :src="item.value" @click="handleSetimg(item.value)" />
			</div>
		</div>
	</el-dialog>
</template>

<script>
import fileService from "@/api/sys/fileService";
import store2 from "store2";
import { resolveComponent } from "vue";
export default {
	data() {
		return {
			loading: false,
			imgVisible: false,
			imgObj: "",
			type: "",
			imgActive: 0,
			imgList: [],
		};
	},
	watch: {
		type: {
			handler() {
				if (this.type === "background") {
					this.imgActive = 0;
				} else if (this.type == "border") {
					this.imgActive = 2;
				} else {
					this.imgActive = 1;
				}
			},
			immediate: true,
		},
	},
	created() {
		this.imgList = this.getImgList();
	},
	methods: {
		getImgList() {
			function concat(prop, count, type, extend = [], defaults) {
				let list = [];
				for (let i = 1; i <= count; i++) {
					list.push({
						label: prop + i,
						value: `/datav/img/${prop}/${prop}${i}.${
							extend.includes(i) ? defaults : type
						}`,
					});
				}
				return list;
			}
			return [
				{
					name: "背景图",
					list: concat("bg", 18, "jpg", [1, 2, 3], "png"),
				},
				{
					name: "素材",
					list: concat(
						"source",
						260,
						"svg",
						[
							1, 15, 16, 20, 239.24, 241, 242, 243, 244, 245, 246,
							247, 248, 249, 250, 251, 252, 253, 254, 255, 256,
							257, 258, 259, 260,
						],
						"png"
					),
				},
				{
					name: "边框",
					list: concat("border", 16, "png"),
				},
				{
					name: "自定义",
					list: store2.get("imgList") || [],
				},
			];
		},
		onProgress() {
			this.loading = true;
		},
		onSuccess(res) {
			const url = res;
			this.loading = false;
			const imgObj = {
				label: url,
				value: url,
			};
			let list = store2.get("imgList") || [];
			list.push(imgObj);
			store2.set("imgList", list);
			this.imgList = this.getImgList();
		},
		openImg(item, type) {
			this.type = type;
			this.imgObj = item;
			this.imgVisible = true;
		},
		handleSetimg(item) {
			this.imgVisible = false;
			this.$emit("change", item, this.imgObj);
		},
		request(param) {
			const data = new FormData();
			data.append(param.filename, param.file);
			for (const key in param.data) {
				data.append(key, param.data[key]);
			}
			fileService
				.uploadFile(data, {
					onUploadProgress: (e) => {
						const complete = parseInt(
							((e.loaded / e.total) * 100) | 0,
							10
						);
						param.onProgress({ percent: complete });
					},
				})
				.then((res) => {
					param.onSuccess(res);
					// eslint-disable-next-line no-unused-vars
				})
				.catch(() => {
					// param.onError(err)
				});
		},
	},
};
</script>

<style lang="scss">
.imgList {
	.el-dialog__body {
		position: relative;
	}
	&__header {
		width: 100%;
		.el-tabs__item {
			min-width: 100px;
			text-align: center;
		}
	}
	&__content {
		display: flex;
		flex-wrap: wrap;
		justify-content: flex-start;
	}
	&__border {
		padding: 5px;
		width: 200px;
		height: 100px;
		margin-right: 10px;
		margin-top: 10px;
		border-radius: 5px;
		text-align: center;
		background-color: #333;
		.el-upload,
		.el-button {
			width: 100%;
			height: 100%;
		}
		img {
			width: 100%;
			height: 100%;
		}
		&:hover {
			cursor: pointer;
		}
	}
}
</style>
