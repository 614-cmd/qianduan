<template>
	<v-dialog :title="title" :close-on-click-modal="false" v-model="visible">
		<el-form
			:model="inputForm"
			ref="inputForm"
			v-loading="loading"
			:class="method === 'view' ? 'readonly' : ''"
			:disabled="method === 'view'"
			label-width="120px"
		>
			<el-row :gutter="15">
				<el-col :span="12">
					<el-form-item label="用户" prop="user.id" :rules="[]">
						<user-select
							:limit="1"
							v-model="inputForm.user.id"
						></user-select>
					</el-form-item>
				</el-col>
				<el-col :span="12">
					<el-form-item label="所属部门" prop="office.id" :rules="[]">
						<office-select v-model="inputForm.office.id" />
					</el-form-item>
				</el-col>
				<el-col :span="12">
					<el-form-item label="所属区域" prop="area" :rules="[]">
						<el-input
							v-model="inputForm.area"
							placeholder="请填写所属区域"
						></el-input>
					</el-form-item>
				</el-col>
				<el-col :span="12">
					<el-form-item label="名称" prop="name" :rules="[]">
						<el-input
							v-model="inputForm.name"
							placeholder="请填写名称"
						></el-input>
					</el-form-item>
				</el-col>
				<el-col :span="12">
					<el-form-item label="性别" prop="sex" :rules="[]">
						<el-radio-group v-model="inputForm.sex">
							<el-radio
								v-for="item in $dictUtils.getDictList('sex')"
								:value="item.value"
								:key="item.value"
								>{{ item.label }}</el-radio
							>
						</el-radio-group>
					</el-form-item>
				</el-col>
				<el-col :span="12">
					<el-form-item label="身份证照片" prop="file" :rules="[]">
						<el-input
							v-model="inputForm.file"
							placeholder="请填写身份证照片"
						></el-input>
					</el-form-item>
				</el-col>
				<el-col :span="12">
					<el-form-item label="加入日期" prop="inDate" :rules="[]">
						<el-date-picker
							style="width: 100%"
							v-model="inputForm.inDate"
							type="datetime"
							value-format="YYYY-MM-DD HH:mm:ss"
							placeholder="选择日期时间"
						>
						</el-date-picker>
					</el-form-item>
				</el-col>
				<el-col :span="12">
					<el-form-item label="备注信息" prop="remarks" :rules="[]">
						<el-input
							type="textarea"
							v-model="inputForm.remarks"
							placeholder="请填写备注信息"
						></el-input>
					</el-form-item>
				</el-col>
				<el-col :span="24">
					<el-tabs v-model="testDataMainTab">
						<el-tab-pane label="火车票">
							<el-button
								@click="addTestDataChild1Row"
								type="primary"
								>新增</el-button
							>
							<el-table
								class="table"
								size="small"
								:data="
									inputForm.testDataChild1DTOList.filter(
										(item) => {
											return item.delFlag !== '1';
										}
									)
								"
								style="width: 100%"
							>
								<el-table-column
									prop=""
									header-align="center"
									align="center"
									show-overflow-tooltip
									label="出发地"
								>
									<template #default="{ row }">
										<area-select v-model="row.startarea" />
									</template>
								</el-table-column>
								<el-table-column
									prop=""
									header-align="center"
									align="center"
									show-overflow-tooltip
									label="目的地"
								>
									<template #default="{ row }">
										<area-select v-model="row.endarea" />
									</template>
								</el-table-column>
								<el-table-column
									prop=""
									header-align="center"
									align="center"
									show-overflow-tooltip
									label="出发时间"
								>
									<template #default="{ row }">
										<el-date-picker
											style="width: 100%"
											v-model="row.starttime"
											type="datetime"
											value-format="YYYY-MM-DD HH:mm:ss"
											placeholder="选择日期时间"
										>
										</el-date-picker>
									</template>
								</el-table-column>
								<el-table-column
									prop=""
									header-align="center"
									align="center"
									show-overflow-tooltip
									label="到达时间"
								>
									<template #default="{ row }">
										<el-date-picker
											style="width: 100%"
											v-model="row.endtime"
											type="datetime"
											value-format="YYYY-MM-DD HH:mm:ss"
											placeholder="选择日期时间"
										>
										</el-date-picker>
									</template>
								</el-table-column>
								<el-table-column
									prop=""
									header-align="center"
									align="center"
									show-overflow-tooltip
									label="代理价格"
								>
									<template #default="{ row }">
										<el-input
											v-model="row.price"
											placeholder="请填写代理价格"
										></el-input>
									</template>
								</el-table-column>
								<el-table-column
									prop=""
									header-align="center"
									align="center"
									show-overflow-tooltip
									label="备注信息"
								>
									<template #default="{ row }">
										<el-input
											type="textarea"
											v-model="row.remarks"
											placeholder="请填写备注信息"
										></el-input>
									</template>
								</el-table-column>
								<el-table-column
									fixed="right"
									label="操作"
									width="100"
								>
									<template #default="{ row }">
										<el-button
											@click="delTestDataChild1Row(row)"
											type="text"
											size="small"
											>删除</el-button
										>
									</template>
								</el-table-column>
							</el-table>
						</el-tab-pane>
						<el-tab-pane label="飞机票">
							<el-button
								@click="addTestDataChild2Row"
								type="primary"
								>新增</el-button
							>
							<el-table
								class="table"
								size="small"
								:data="
									inputForm.testDataChild2DTOList.filter(
										(item) => {
											return item.delFlag !== '1';
										}
									)
								"
								style="width: 100%"
							>
								<el-table-column
									prop=""
									header-align="center"
									align="center"
									show-overflow-tooltip
									label="出发地"
								>
									<template #default="{ row }">
										<area-select v-model="row.startarea" />
									</template>
								</el-table-column>
								<el-table-column
									prop=""
									header-align="center"
									align="center"
									show-overflow-tooltip
									label="目的地"
								>
									<template #default="{ row }">
										<area-select v-model="row.endarea" />
									</template>
								</el-table-column>
								<el-table-column
									prop=""
									header-align="center"
									align="center"
									show-overflow-tooltip
									label="出发时间"
								>
									<template #default="{ row }">
										<el-date-picker
											style="width: 100%"
											v-model="row.starttime"
											type="datetime"
											value-format="YYYY-MM-DD HH:mm:ss"
											placeholder="选择日期时间"
										>
										</el-date-picker>
									</template>
								</el-table-column>
								<el-table-column
									prop=""
									header-align="center"
									align="center"
									show-overflow-tooltip
									label="到达时间"
								>
									<template #default="{ row }">
										<el-date-picker
											style="width: 100%"
											v-model="row.endtime"
											type="datetime"
											value-format="YYYY-MM-DD HH:mm:ss"
											placeholder="选择日期时间"
										>
										</el-date-picker>
									</template>
								</el-table-column>
								<el-table-column
									prop=""
									header-align="center"
									align="center"
									show-overflow-tooltip
									label="代理价格"
								>
									<template #default="{ row }">
										<el-input
											v-model="row.price"
											placeholder="请填写代理价格"
										></el-input>
									</template>
								</el-table-column>
								<el-table-column
									prop=""
									header-align="center"
									align="center"
									show-overflow-tooltip
									label="备注信息"
								>
									<template #default="{ row }">
										<el-input
											type="textarea"
											v-model="row.remarks"
											placeholder="请填写备注信息"
										></el-input>
									</template>
								</el-table-column>
								<el-table-column
									fixed="right"
									label="操作"
									width="100"
								>
									<template #default="{ row }">
										<el-button
											@click="delTestDataChild2Row(row)"
											type="text"
											size="small"
											>删除</el-button
										>
									</template>
								</el-table-column>
							</el-table>
						</el-tab-pane>
						<el-tab-pane label="汽车票">
							<el-button
								@click="addTestDataChild3Row"
								type="primary"
								>新增</el-button
							>
							<el-table
								class="table"
								size="small"
								:data="
									inputForm.testDataChild3DTOList.filter(
										(item) => {
											return item.delFlag !== '1';
										}
									)
								"
								style="width: 100%"
							>
								<el-table-column
									prop=""
									header-align="center"
									align="center"
									show-overflow-tooltip
									label="出发地"
								>
									<template #default="{ row }">
										<area-select v-model="row.startarea" />
									</template>
								</el-table-column>
								<el-table-column
									prop=""
									header-align="center"
									align="center"
									show-overflow-tooltip
									label="目的地"
								>
									<template #default="{ row }">
										<area-select v-model="row.endarea" />
									</template>
								</el-table-column>
								<el-table-column
									prop=""
									header-align="center"
									align="center"
									show-overflow-tooltip
									label="代理价格"
								>
									<template #default="{ row }">
										<el-input
											v-model="row.price"
											placeholder="请填写代理价格"
										></el-input>
									</template>
								</el-table-column>
								<el-table-column
									prop=""
									header-align="center"
									align="center"
									show-overflow-tooltip
									label="出发时间"
								>
									<template #default="{ row }">
										<el-date-picker
											style="width: 100%"
											v-model="row.starttime"
											type="datetime"
											value-format="YYYY-MM-DD HH:mm:ss"
											placeholder="选择日期时间"
										>
										</el-date-picker>
									</template>
								</el-table-column>
								<el-table-column
									prop=""
									header-align="center"
									align="center"
									show-overflow-tooltip
									label="到达时间"
								>
									<template #default="{ row }">
										<el-date-picker
											style="width: 100%"
											v-model="row.endtime"
											type="datetime"
											value-format="YYYY-MM-DD HH:mm:ss"
											placeholder="选择日期时间"
										>
										</el-date-picker>
									</template>
								</el-table-column>
								<el-table-column
									prop=""
									header-align="center"
									align="center"
									show-overflow-tooltip
									label="备注信息"
								>
									<template #default="{ row }">
										<el-input
											type="textarea"
											v-model="row.remarks"
											placeholder="请填写备注信息"
										></el-input>
									</template>
								</el-table-column>
								<el-table-column
									fixed="right"
									label="操作"
									width="100"
								>
									<template #default="{ row }">
										<el-button
											@click="delTestDataChild3Row(row)"
											type="text"
											size="small"
											>删除</el-button
										>
									</template>
								</el-table-column>
							</el-table>
						</el-tab-pane>
					</el-tabs>
				</el-col>
			</el-row>
		</el-form>
		<template #footer>
			<span class="dialog-footer">
				<el-button @click="visible = false" icon="circle-close"
					>关闭</el-button
				>
				<el-button
					type="primary"
					v-if="method != 'view'"
					@click="doSubmit()"
					icon="circle-check"
					v-noMoreClick
					>确定</el-button
				>
			</span>
		</template>
	</v-dialog>
</template>

<script>
import UserSelect from "@/components/userSelect";
import OfficeSelect from "@/components/officeSelect";
import AreaSelect from "@/components/areaSelect";
import testDataMainService from "@/api/test/onetomany/testDataMainService";

export default {
	data() {
		return {
			title: "",
			method: "",
			visible: false,
			loading: false,
			testDataMainTab: "0",
			inputForm: {
				id: "",
				testDataChild1DTOList: [],
				testDataChild2DTOList: [],
				testDataChild3DTOList: [],
				user: {
					id: "",
				},
				office: {
					id: "",
				},
				area: "",
				name: "",
				sex: "",
				file: "",
				inDate: "",
				remarks: "",
			},
		};
	},
	components: {
		UserSelect,
		OfficeSelect,
		AreaSelect,
	},
	created() {},
	methods: {
		init(method, id) {
			this.method = method;
			if (method === "add") {
				this.title = `新建票务代理`;
			} else if (method === "edit") {
				this.title = "修改票务代理";
			} else if (method === "view") {
				this.title = "查看票务代理";
			}
			this.visible = true;
			this.loading = false;
			this.$nextTick(() => {
				this.$refs.inputForm.resetFields();
				this.inputForm.id = id;
				this.testDataMainTab = "0";
				this.inputForm.testDataChild1DTOList = [];
				this.inputForm.testDataChild2DTOList = [];
				this.inputForm.testDataChild3DTOList = [];
				if (method === "edit" || method === "view") {
					// 修改或者查看
					this.loading = true;
					testDataMainService
						.queryById(this.inputForm.id)
						.then((data) => {
							this.inputForm = this.recover(this.inputForm, data);
							this.loading = false;
						});
				}
			});
		},
		saveTestDataChild1Row(child) {
			if (child[0] === "") {
				this.inputForm.testDataChild1DTOList.push(child[1]);
			} else {
				this.inputForm.testDataChild1DTOList.forEach((item, index) => {
					if (item === child[0]) {
						this.inputForm.testDataChild1DTOList.splice(
							index,
							1,
							child[1]
						);
					}
				});
			}
		},
		addTestDataChild1Row() {
			this.inputForm.testDataChild1DTOList.push({
				startarea: "",
				endarea: "",
				starttime: "",
				endtime: "",
				price: "",
				remarks: "",
			});
		},
		delTestDataChild1Row(child) {
			this.inputForm.testDataChild1DTOList.forEach((item, index) => {
				if (item === child && item.id === "") {
					this.inputForm.testDataChild1DTOList.splice(index, 1);
				} else if (item === child) {
					item.delFlag = "1";
					this.inputForm.testDataChild1DTOList.splice(index, 1, item);
				}
			});
		},
		saveTestDataChild2Row(child) {
			if (child[0] === "") {
				this.inputForm.testDataChild2DTOList.push(child[1]);
			} else {
				this.inputForm.testDataChild2DTOList.forEach((item, index) => {
					if (item === child[0]) {
						this.inputForm.testDataChild2DTOList.splice(
							index,
							1,
							child[1]
						);
					}
				});
			}
		},
		addTestDataChild2Row() {
			this.inputForm.testDataChild2DTOList.push({
				startarea: "",
				endarea: "",
				starttime: "",
				endtime: "",
				price: "",
				remarks: "",
			});
		},
		delTestDataChild2Row(child) {
			this.inputForm.testDataChild2DTOList.forEach((item, index) => {
				if (item === child && item.id === "") {
					this.inputForm.testDataChild2DTOList.splice(index, 1);
				} else if (item === child) {
					item.delFlag = "1";
					this.inputForm.testDataChild2DTOList.splice(index, 1, item);
				}
			});
		},
		saveTestDataChild3Row(child) {
			if (child[0] === "") {
				this.inputForm.testDataChild3DTOList.push(child[1]);
			} else {
				this.inputForm.testDataChild3DTOList.forEach((item, index) => {
					if (item === child[0]) {
						this.inputForm.testDataChild3DTOList.splice(
							index,
							1,
							child[1]
						);
					}
				});
			}
		},
		addTestDataChild3Row() {
			this.inputForm.testDataChild3DTOList.push({
				startarea: "",
				endarea: "",
				price: "",
				starttime: "",
				endtime: "",
				remarks: "",
			});
		},
		delTestDataChild3Row(child) {
			this.inputForm.testDataChild3DTOList.forEach((item, index) => {
				if (item === child && item.id === "") {
					this.inputForm.testDataChild3DTOList.splice(index, 1);
				} else if (item === child) {
					item.delFlag = "1";
					this.inputForm.testDataChild3DTOList.splice(index, 1, item);
				}
			});
		},
		// 表单提交
		doSubmit() {
			this.$refs["inputForm"].validate((valid) => {
				if (valid) {
					this.loading = true;
					testDataMainService
						.save(this.inputForm)
						.then((data) => {
							this.visible = false;
							this.$message.success(data);
							this.$emit("refreshDataList");
							this.loading = false;
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
