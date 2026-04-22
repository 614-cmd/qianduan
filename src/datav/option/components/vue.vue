<!-- 通用形配置 -->
<template>
	<div>
		<el-form-item label="组件">
			<el-button type="primary" @click="openCode">编辑</el-button>
			<el-button
				type="primary"
				@click="api.box = true"
				icon="el-icon-search"
			></el-button>
		</el-form-item>
		<codeedit
			@submit="codeClose"
			title="配置列表"
			:rules="false"
			v-model="code.obj"
			v-if="code.box"
			:type="code.type"
			v-model:visible="code.box"
		></codeedit>
<!-- 
		<el-dialog title="组件库" v-model="api.box" width="70%">
			<component-list
				v-if="api.box"
				:activeName="0"
				@change="handleApiChange"
				:menu="false"
			></component-list>
		</el-dialog> -->
	</div>
</template>

<script>
import codeedit from "../../page/group/code.vue";
// import componentList from "../../page/list/components.vue";
import dataComponentService from "@/api/datav/dataComponentService.js";
export default {
	name: "vue",
	inject: ["main"],
	components: {
		// componentList,
		codeedit,
	},
	data() {
		return {
			api: {
				box: false,
				item: {},
			},
			code: {
				type: "content",
				box: false,
				obj: {},
			},
		};
	},
	methods: {
		handleApiChange(val) {
			this.api.item = val;
			dataComponentService.queryById(this.api.item.id).then((data) => {
				this.main.activeOption[this.code.type] = data.content;
				this.api.box = false;
				this.api.item = {};
			});
		},
		codeClose(value) {
			this.main.activeOption[this.code.type] = value;
		},
		openCode() {
			this.code.obj = this.main.activeOption[this.code.type];
			this.code.box = true;
		},
	},
};
</script>

<style></style>
