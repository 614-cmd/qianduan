<template>
	<span class="bt-group">
		<el-button
			style="width: 100px"
			type="danger"
			v-if="inputForm.id && hasPermission('sys:dict:del')"
			@click="del()"
			text
			bg
			>删除</el-button
		>
		<el-button
			style="width: 100px"
			type="primary"
			v-if="
				hasPermission('sys:dict:edit') || hasPermission('sys:dict:add')
			"
			@click="doSubmit()"
			v-noMoreClick
			>保存</el-button
		>
	</span>
	<el-form
		:model="inputForm"
		:rules="dataRule"
		v-loading="loading"
		ref="inputForm"
		label-width="80px"
		@submit.prevent
	>
		<el-form-item label="类型" prop="type">
			<el-input
				v-model="inputForm.type"
				maxlength="50"
				placeholder="类型"
			></el-input>
		</el-form-item>
		<el-form-item label="描述" prop="remarks">
			<el-input
				type="textarea"
				v-model="inputForm.remarks"
				maxlength="50"
				placeholder="描述"
			></el-input>
		</el-form-item>
	</el-form>
</template>

<script>
import dictService from "@/api/sys/dictService";
export default {
	data() {
		return {
			loading: false,
			inputForm: {
				id: "",
				type: "",
				remarks: "",
			},
			dataRule: {
				type: [
					{
						required: true,
						message: "类型不能为空",
						trigger: "blur",
					},
				],
			},
		};
	},
	emits: ["delTypeById", "getTypeTitle", "getDictTypeId", "refreshDataList"],
	watch: {
		"inputForm.type": {
			handler(val) {
				this.$emit("getTypeTitle", val);
			},
			immediate: true,
			deep: false,
		},
	},
	methods: {
		init(id) {
			this.inputForm.id = id;
			this.$nextTick(() => {
				this.$refs["inputForm"].resetFields();
				if (this.inputForm.id) {
					// 修改或者查看
					dictService.queryById(this.inputForm.id).then((data) => {
						this.inputForm = this.recover(this.inputForm, data);
					});
				}
			});
		},
		del() {
			this.$emit("delTypeById", this.inputForm.id);
		},
		// 表单提交
		doSubmit() {
			this.$refs["inputForm"].validate((valid) => {
				if (valid) {
					this.loading = true;
					dictService
						.save(this.inputForm)
						.then((data) => {
							this.loading = false;
							this.$message({
								message: "保存成功!",
								type: "success",
								duration: 1500,
							});
							this.inputForm.id = data.id;
							this.$emit("getDictTypeId", data.id);
							this.$emit("refreshDataList");
							this.$dictUtils.refreshDictList();
						})
						.catch(() => {
							this.loading = false;
						});
				}
			});
		},
	},
};
</script>
