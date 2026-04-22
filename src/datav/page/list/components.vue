<template>
	<div class="list">
		<el-container>
			<el-header class="content__header">
				<div class="content__box content__nav">
					<div class="content__add" @click="handleAdd">
						<img
							:src="`${$router.options.base}img/new-project.png`"
							style="height: 40px"
							alt=""
						/>
						<p>新建组件</p>
					</div>
					<div class="content__page">
						<el-pagination
							v-if="page.total > 0"
							layout="total, sizes, prev, pager, next, jumper"
							background
							@size-change="handleSizeChange"
							@current-change="handleCurrentChange"
							:page-size="page.size"
							:current-page="page.page"
							:total="page.total"
						>
						</el-pagination>
					</div>
				</div>
				<template v-for="(item, index) in dicData">
					<el-button
						v-if="menu"
						:key="index"
						@click="handleTabClick(item.value)"
						:type="active == item.value ? 'primary' : 'info'"
						>{{ item.label }}</el-button
					>
				</template>
			</el-header>
			<el-main class="content">
				<div class="content__box">
					<div
						class="content__item"
						v-for="(item, index) in data"
						@mouseover="item._menu = true"
						@mouseout="item._menu = false"
						:key="index"
					>
						<div class="content__info">
							<img v-if="item.img" :src="item.img" alt="" />
							<div class="content__menu" v-show="item._menu">
								<div class="content__right" v-if="menu">
									<el-tooltip content="预览">
										<i
											class="el-icon-view"
											@click="handleView(item, index)"
										></i>
									</el-tooltip>
									<el-tooltip content="删除">
										<i
											class="el-icon-delete"
											@click="rowDel(item, index)"
										></i>
									</el-tooltip>
								</div>
								<div
									class="content__btn"
									v-if="menu"
									@click="handleEdit(item, index)"
								>
									编辑
								</div>
								<div
									v-if="!menu"
									class="content__btn"
									@click="handleRow(item, index)"
								>
									选择
								</div>
							</div>
						</div>
						<div class="content__main">
							<span class="content__name">{{ item.name }}</span>
							<span class="content__status">
								{{ getTypeLabel(item.type) }}
							</span>
						</div>
					</div>
				</div>
			</el-main>
		</el-container>
		<el-dialog
			title="编辑组件"
			width="70%"
			destroy-on-close
			:close-on-click-modal="false"
			v-model="dialog"
		>
			<avue-form :option="option" v-model="form" @submit="handleSubmit">
				<template #content>
					<monaco-editor
						v-model="form.content"
						:language="options.language"
						:height="options.height"
						:options="options"
					></monaco-editor>
					<br />
					<el-button
						v-if="!view"
						type="primary"
						icon="el-icon-check"
						@click="handleOption"
						>运行</el-button
					>
				</template>
				<template #view>
					<div class="view" v-if="reload">
						<avue-echart-vue
							:option="vueOption"
							v-if="form.type == 0"
							width="100%"
							height="100%"
						></avue-echart-vue>
						<avue-echart-common
							:echart-formatter="getFunction(vueOption.content)"
							v-else-if="form.type == 1"
							width="100%"
							height="600"
						></avue-echart-common>
					</div>
				</template>
			</avue-form>
		</el-dialog>

		<el-dialog title="预览组件" v-model="box" width="60%">
			<avue-echart-vue
				:option="vueOption"
				v-if="form.type == 0"
				width="100%"
				height="100%"
			></avue-echart-vue>
			<avue-echart-common
				:echart-formatter="getFunction(vueOption.content)"
				v-else-if="form.type == 1"
				width="100%"
				height="600"
			></avue-echart-common>
		</el-dialog>
	</div>
</template>

<script>
import AvueEchartVue from "@/datav/echart/packages/vue/index.vue";
import AvueEchartCommon from "@/datav/echart/packages/common/index.vue";
import MonacoEditor from "@/datav/page/components/monaco-editor";
import { getFunction, dataURLtoFile } from "@/datav/utils/utils";
import dataComponentService from "@/api/datav/dataComponentService.js";
const dicData = [
	{
		label: "vue组件",
		value: 0,
		content:
			"<template>\n    <div>\n        \n    </div>\n</template>\n<script>\n    export default{\n        data(){\n            return {}\n        },\n        created(){\n\n        },\n        methods:{\n\n        }\n    }\n<\/script>\n\n<style>\n\n<\/style>",
	},
	{
		label: "echart组件",
		value: 1,
		content:
			"function (data,params){\n    const myChart = this.myChart;\n    return option;\n}",
	},
];
export default {
	components: {
		AvueEchartCommon,
		AvueEchartVue,
		MonacoEditor,
	},
	props: {
		menu: {
			type: Boolean,
			default: true,
		},
		activeName: "",
	},
	data() {
		return {
			reload: false,
			active: 0,
			dicData,
			box: false,
			options: {
				height: 300,
				language: "javascript",
				fontSize: 12,
			},
			content: "",
			index: -1,
			isEdit: false,
			form: {},
			vueOption: {},
			data: [],
			type: "",
			page: {
				size: 50,
				page: 1,
				total: 0,
			},
			dialog: false,
			option: {
				labelWidth: 120,
				column: [
					{
						label: "组件名称",
						prop: "name",
						row: true,
						rules: [
							{
								required: true,
								message: "请输入组件名称",
								trigger: "blur",
							},
						],
					},
					{
						label: "组件类型",
						prop: "type",
						type: "select",
						dicData: dicData,
						rules: [
							{
								required: true,
								message: "请选择组件类型",
								trigger: "blur",
							},
						],
					},
					{
						label: "组件数据",
						prop: "content",
						span: 24,
					},
					{
						label: "组件预览",
						prop: "view",
						span: 24,
					},
				],
			},
		};
	},
	created() {
		this.getFunction = getFunction;
		if (!this.menu) {
			this.active = this.activeName;
		}
		this.getList();
	},
	watch: {
		"form.type"(val) {
			if (this.isEdit) {
				this.isEdit = false;
				return;
			}
			let obj = dicData.find((ele) => ele.value == val) || {};
			this.form.content = obj.content;
		},
	},
	methods: {
		handleTabClick(val) {
			this.active = val;
			this.page.currentPage = 1;
			this.getList();
		},
		getTypeLabel(type) {
			return dicData.find((ele) => ele.value == type).label;
		},
		handleRow(row, index) {
			this.$emit("change", row);
		},
		vaildData(id) {
			return [0, 1, 2, 3, 4].includes(id);
		},
		rowDel(row, index) {
			if (this.vaildData(index) && this.$website.isDemo) {
				this.$message.error(this.$website.isDemoTip);
				return false;
			}
			this.$confirm("此操作将永久删除, 是否继续?", "提示", {
				confirmButtonText: "确定",
				cancelButtonText: "取消",
				type: "warning",
			}).then(() => {
				delObj(row.id).then(() => {
					this.$message.success("删除成功");
					this.getList();
				});
			});
		},
		handleOption() {
			this.reload = false;
			this.vueOption = this.deepClone(this.form);
			this.$nextTick(() => {
				this.reload = true;
			});
		},
		getImg() {
			return new Promise((resolve) => {
				html2canvas(document.querySelector(".view"), {
					useCORS: true,
					backgroundColor: null,
					logging: false,
					allowTaint: true,
				}).then((canvas) => {
					let result = canvas.toDataURL("image/jpeg", 0.1);
					if (result.length < 10) resolve("");
					var file = dataURLtoFile(
						result,
						new Date().getTime() + ".jpg"
					);
					var formdata = new FormData();
					formdata.append("file", file);
					uploadImg(formdata).then((res) => {
						const data = res.data.data;
						const url = data.link;
						resolve(url);
					});
				});
			});
		},
		handleAdd() {
			this.form = {
				name: "",
				type: "",
				content: "",
			};
			this.type = "add";
			this.isEdit = true;
			this.handleOption();
			this.dialog = true;
		},
		handleEdit(row, index) {
			this.index = index;
			this.type = "edit";
			this.isEdit = true;
			getObj(row.id).then((res) => {
				const data = res.data.data;
				this.form = data;
				this.handleOption();
				this.dialog = true;
			});
		},
		handleSubmit(form, done) {
			if (this.type == "add") {
				this.rowSave(this.from, done);
			} else if (this.type == "edit") {
				this.rowUpdate(this.from, this.index, done);
			}
		},
		rowUpdate(row, index, done) {
			if (this.vaildData(index) && this.$website.isDemo) {
				done();
				this.$message.error(this.$website.isDemoTip);
				return false;
			}
			this.getImg()
				.then((img) => {
					this.form.img = img;
					return updateObj(this.form);
				})
				.then(() => {
					done();
					this.dialog = false;
					this.$message.success("修改成功");
					this.getList();
				});
		},
		rowSave(row, done) {
			this.getImg()
				.then((img) => {
					this.form.img = img;
					return addObj(this.form);
				})
				.then(() => {
					done();
					this.dialog = false;
					this.$message.success("新增成功");
					this.getList();
				});
		},
		handleView(row, index) {
			getObj(row.id).then((res) => {
				const data = res.data.data;
				this.form = data;
				this.vueOption = this.deepClone({
					content: data.content,
				});
				this.box = true;
			});
		},
		handleCurrentChange(val) {
			this.page.page = val;
			this.getList();
		},
		handleSizeChange(val) {
			this.page.size = val;
			this.getList();
		},
		getList() {
			getList({
				current: this.page.page,
				size: this.page.size,
				type: this.active,
			}).then((res) => {
				const data = res.data.data;
				this.page.total = data.total;
				this.data = data.records;
				this.data.forEach((ele, index) => {
					this.data[index]["_menu"] = false;
				});
			});
		},
	},
};
</script>

<style lang="scss" scoped>
@import "@/datav/styles/list.scss";
.components {
	&_content {
		display: flex;
		align-items: flex-end;
		flex-direction: column;
	}
	&_left,
	&_right {
		width: 100%;
	}
}
</style>
