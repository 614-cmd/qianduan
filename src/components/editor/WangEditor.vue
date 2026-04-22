<template>
	<div style="border: 1px solid #ccc">
		<Toolbar
			style="border-bottom: 1px solid #ccc"
			:editor="editorRef"
			:defaultConfig="toolbarConfig"
			:mode="mode"
		/>
		<Editor
			style="height: 500px; overflow-y: hidden"
			v-model="valueHtml"
			:defaultConfig="editorConfig"
			:mode="mode"
			@onCreated="handleCreated"
		/>
	</div>
</template>

<script>
import "@wangeditor/editor/dist/css/style.css"; // 引入 css

import { onBeforeUnmount, ref, shallowRef, watch } from "vue";
import { Editor, Toolbar } from "@wangeditor/editor-for-vue";
import config from "@/config/upload";
import { ElMessage } from "element-plus";
import { ElLoading } from "element-plus";

export default {
	components: { Editor, Toolbar },
	props: {
		modelValue: {
			type: String,
			default: "",
		},
	},
	setup(props, { emit }) {
		// 编辑器实例，必须用 shallowRef
		const editorRef = shallowRef();

		// 内容 HTML
		const valueHtml = ref("");

		const toolbarConfig = {};
		const editorConfig = { placeholder: "请输入内容..." };

		editorConfig.MENU_CONF = {
			// 上传图片的配置
		};

		editorConfig.MENU_CONF.uploadImage = {
			maxFileSize: 2 * 1024 * 1024, // 2M
			// base64LimitSize: 1024 * 1024, // 1M 以下插入 base64
			customUpload(file, insertFn) {
				const maxSize = file.size / 1024 / 1024 < config.maxSize;
				if (!maxSize) {
					ElMessage({
						message: `上传文件大小不能超过 ${config.maxSize}MB!`,
						type: "warning",
					});
					return false;
				}
				const obj = new FormData();
				obj.append("file", file);
				var apiObj = config.apiObj;
				const loading = ElLoading.service({
					lock: true,
					text: "图片上传中...",
					background: "rgba(0, 0, 0, 0.7)",
				});
				apiObj
					.upload(obj)
					.then((res) => {
						insertFn(res, "", res);
						// 以服务的方式调用的 Loading 需要异步关闭
						loading.close();
					})
					.catch((err) => {
						// 以服务的方式调用的 Loading 需要异步关闭
						loading.close();
						console.log(err);
					});
			},
		};

		// 组件销毁时，也及时销毁编辑器
		onBeforeUnmount(() => {
			const editor = editorRef.value;
			if (editor == null) return;
			editor.destroy();
		});

		watch(valueHtml, (newValue) => {
			emit("update:modelValue", newValue);
		});
		watch(
			() => props.modelValue,
			(newValue) => {
				valueHtml.value = newValue;
			}
		);

		const handleCreated = (editor) => {
			editorRef.value = editor; // 记录 editor 实例，重要！
		};

		return {
			editorRef,
			valueHtml,
			mode: "default", // 或 'simple'
			toolbarConfig,
			editorConfig,
			handleCreated,
		};
	},
};
</script>
