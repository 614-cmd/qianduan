<template>
	<div>
		<v-dialog
			:title="title"
			:close-on-click-modal="false"
			v-model="visible"
		>
			<fm-generate-form :data="options" ref="generateForm" v-if="visible">
			</fm-generate-form>
			<template #footer>
				<span class="dialog-footer">
					<el-button @click="visible = false" icon="circle-close"
						>关闭</el-button
					>
					<el-button type="primary" @click="visible = false"
						>确定</el-button
					>
				</span>
			</template>
		</v-dialog>
	</div>
</template>

<script>
import formDefinitionJsonService from "@/api/flowable/formDefinitionJsonService";
export default {
	data() {
		return {
			title: "预览表单",
			method: "",
			visible: false,
			loading: false,
			options: { list: [] },
		};
	},
	methods: {
		init(id) {
			this.loading = true;
			formDefinitionJsonService.queryById(id).then((data) => {
				this.options = JSON.parse(data.json);
				this.visible = true;
				this.loading = false;
			});
		},
	},
};
</script>
