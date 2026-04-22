<template>
	<template v-if="!inputForm.id">
		<el-empty description="请选择左侧机构后操作" :image-size="250">
			<template #image>
				<img src="@/assets/img/empty.svg" />
			</template>
		</el-empty>
	</template>
	<template v-else>
		<el-card
			shadow="never"
			style="border: 0; padding: 10px"
			:body-style="{ border: 0, padding: '20px' }"
		>
			<template #header>
				<div class="card-header">
					<span>{{ inputForm.name || "新增机构" }}</span>
					<el-button icon="back" @click="toView" text></el-button>
				</div>
			</template>
			<el-form
				label-width="100px"
				:model="inputForm"
				:rules="dataRule"
				ref="inputForm"
				v-loading="loading"
				@submit.prevent
			>
				<el-form-item
					label="机构名称"
					prop="name"
					:rules="[
						{
							required: true,
							message: '名称不能为空',
							trigger: 'blur',
						},
					]"
				>
					<el-input
						v-model="inputForm.name"
						placeholder="机构名称"
					></el-input>
				</el-form-item>

				<el-form-item
					label="机构编码"
					prop="code"
					:rules="[
						{
							max: 50,
							message: '最大长度不能超过50个字符',
							trigger: 'blur',
						},
					]"
				>
					<el-input
						v-model="inputForm.code"
						placeholder="机构编码"
					></el-input>
				</el-form-item>

				<el-form-item
					label="机构类型"
					prop="type"
					:rules="[
						{
							required: true,
							message: '机构类型不能为空',
							trigger: 'blur',
						},
					]"
				>
					<el-select
						v-model="inputForm.type"
						placeholder="请选择"
						style="width: 100%"
					>
						<el-option
							v-for="item in this.$dictUtils.getDictList(
								'sys_office_type'
							)"
							:key="item.value"
							:label="item.label"
							:value="item.value"
						>
						</el-option>
					</el-select>
				</el-form-item>
				<el-form-item label="所属区域" prop="area">
					<Area v-model="inputForm.area" />
				</el-form-item>
				<el-form-item
					label="详细地址"
					prop="address"
					:rules="[
						{
							max: 50,
							message: '最大长度不能超过50个字符',
							trigger: 'blur',
						},
					]"
				>
					<el-input
						v-model="inputForm.address"
						placeholder="请输入地址"
					></el-input>
				</el-form-item>
				<el-form-item
					label="邮政编码"
					prop="zipCode"
					:rules="[
						{
							max: 50,
							message: '最大长度不能超过50个字符',
							trigger: 'blur',
						},
					]"
				>
					<el-input
						v-model="inputForm.zipCode"
						placeholder="请输入邮政编码"
					></el-input>
				</el-form-item>
				<el-form-item
					:label="
						inputForm.type === '1' ? '公司负责人' : '部门负责人'
					"
					prop="master"
					:rules="[
						{
							max: 50,
							message: '最大长度不能超过50个字符',
							trigger: 'blur',
						},
					]"
				>
					<el-input
						v-model="inputForm.master"
						placeholder="请输入负责人"
					></el-input>
				</el-form-item>
				<el-form-item
					label="联系电话"
					prop="phone"
					:rules="[
						{
							max: 50,
							message: '最大长度不能超过50个字符',
							trigger: 'blur',
						},
					]"
				>
					<el-input
						v-model="inputForm.phone"
						placeholder="请输入联系电话"
					></el-input>
				</el-form-item>
				<el-form-item
					label="是否可用"
					prop="useable"
					:rules="[
						{
							required: true,
							message: '是否可用不能为空',
							trigger: 'blur',
						},
					]"
				>
					<el-select
						v-model="inputForm.useable"
						placeholder="请选择"
						style="width: 100%"
					>
						<el-option
							v-for="item in this.$dictUtils.getDictList(
								'yes_no'
							)"
							:key="item.value"
							:label="item.label"
							:value="item.value"
						>
						</el-option>
					</el-select>
				</el-form-item>

				<el-form-item label="备注" prop="remarks">
					<el-input
						maxlength="200"
						v-model="inputForm.remarks"
						type="textarea"
						:rows="2"
						placeholder="备注"
					></el-input>
				</el-form-item>
				<el-form-item>
					<el-button
						type="primary"
						@click="doSubmit()"
						icon="circle-check"
						v-noMoreClick
						>确定</el-button
					>
				</el-form-item>
			</el-form>
		</el-card>
	</template>
</template>

<script>
import officeService from "@/api/sys/officeService";
import Area from "@/components/areaSelect";
export default {
	props: ["dataList"],
	components: {
		Area,
	},
	data() {
		return {
			loading: false,
			inputForm: {},
			basicForm: {
				id: "",
				name: "",
				parent: {
					id: "",
				},
				sort: "30",
				area: "", // 归属区域
				code: "", // 机构编码
				type: "", // 机构类型（1：公司；2：部门）
				address: "", // 联系地址
				zipCode: "", // 邮政编码
				master: "", // 负责人
				phone: "", // 电话
				fax: "", // 传真
				email: "", // 邮箱
				useable: "", // 是否可用
			},
			dataRule: {
				name: [
					{
						required: true,
						message: "名称不能为空",
						trigger: "blur",
					},
				],
				type: [
					{
						required: true,
						message: "机构类型不能为空",
						trigger: "blur",
					},
				],
				code: [
					{
						required: true,
						message: "机构编码不能为空",
						trigger: "blur",
					},
				],
			},
		};
	},
	methods: {
		// 表单提交
		doSubmit() {
			this.$refs["inputForm"].validate((valid) => {
				if (valid) {
					this.loading = true;
					officeService
						.save(this.inputForm)
						.then(() => {
							this.loading = false;
							this.$message({
								message: "保存成功!",
								type: "success",
								duration: 1500,
							});
						})
						.catch(() => {
							this.loading = false;
						});
				}
			});
		},
		//表单注入数据
		setData(data) {
			this.inputForm = this.pickProperties(
				data,
				JSON.parse(JSON.stringify(this.basicForm))
			);
		},
		toView() {
			this.$emit("toView");
		},
	},
};
</script>
