<template>
	<v-dialog
		title="加签"
		:close-on-click-modal="false"
		v-model="visible"
		:appendToBody="true"
	>
		<el-form :model="addSignForm" ref="addSignForm" label-width="120px">
			<el-row>
				<el-col :span="24">
					<el-form-item
						:rules="[
							{
								required: true,
								message: '用户不能为空',
								trigger: 'blur',
							},
						]"
						prop="userIds"
						label="加签处理人"
					>
						<user-select
							v-model="addSignForm.userIds"
						></user-select>
					</el-form-item>
				</el-col>
				<el-col :span="24">
					<el-form-item label="加签理由" prop="message">
						<el-input
							type="textarea"
							:rows="3"
							placeholder="加签理由"
							v-model="addSignForm.message"
						>
						</el-input>
					</el-form-item>
				</el-col>
			</el-row>
		</el-form>

		<template #footer>
			<span class="dialog-footer">
				<el-button icon="close" @click="visible = false"
					>取消</el-button
				>
				<el-button icon="check" type="primary" @click="doConfirm()"
					>加签</el-button
				>
			</span>
		</template>
	</v-dialog>
</template>

<script>
import UserSelect from "@/components/userSelect/index.vue";
export default {
	name: "addSign",
	components: {
		UserSelect,
	},
	data() {
		return {
			visible: false,
			addSignForm: {
				userIds: "",
				message: "",
			},
		};
	},
	methods: {
		open() {
			this.visible = true;
		},
		doConfirm() {
			this.$refs["addSignForm"].validate((valid) => {
				if (valid) {
					this.visible = false;
					this.$emit("addSign", this.addSignForm);
				}
			});
		},
	},
};
</script>
