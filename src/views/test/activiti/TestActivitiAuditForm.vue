<template>
    <el-form :model="inputForm" ref="inputForm" v-loading="loading" :disabled="formReadOnly" label-width="120px">
      <el-row  :gutter="15">
        <el-col :span="24">
            <el-form-item label="员工" prop="user.id"
                :rules="[
                  {required: true, message:'员工不能为空', trigger:'blur'}
                 ]">
                <user-select :limit='1' v-model="inputForm.user.id"></user-select>
           </el-form-item>
        </el-col>
        <el-col :span="24">
            <el-form-item label="部门" prop="office.id"
                :rules="[
                  {required: true, message:'部门不能为空', trigger:'blur'}
                 ]">
                <office-select v-model="inputForm.office.id"/>
           </el-form-item>
        </el-col>
        <el-col :span="24">
            <el-form-item label="岗位" prop="post"
                :rules="[
                 ]">
              <el-input v-model="inputForm.post" placeholder="请填写岗位"     ></el-input>
           </el-form-item>
        </el-col>
        <el-col :span="24">
            <el-form-item label="性别" prop="sex"
                :rules="[
                 ]">
                    <el-radio-group v-model="inputForm.sex">
                        <el-radio v-for="item in $dictUtils.getDictList('sex')" :value="item.value" :key="item.value">{{item.label}}</el-radio>
                    </el-radio-group>
           </el-form-item>
        </el-col>
        <el-col :span="24">
            <el-form-item label="调整原因" prop="content"
                :rules="[
                 ]">
            <wang-editor v-model="inputForm.content"/>
           </el-form-item>
        </el-col>
        <el-col :span="24">
            <el-form-item label="调整前薪水" prop="oldSalray"
                :rules="[
                 ]">
              <el-input v-model="inputForm.oldSalray" placeholder="请填写调整前薪水"     ></el-input>
           </el-form-item>
        </el-col>
        <el-col :span="24">
            <el-form-item label="调整后薪水" prop="newSalary"
                :rules="[
                 ]">
              <el-input v-model="inputForm.newSalary" placeholder="请填写调整后薪水"     ></el-input>
           </el-form-item>
        </el-col>
        <el-col :span="24">
            <el-form-item label="生效时间" prop="exeDate"
                :rules="[
                 ]">
                <el-date-picker
                      style="width: 100%;"
                      v-model="inputForm.exeDate"
                      type="datetime"
                      value-format="YYYY-MM-DD HH:mm:ss"
                      placeholder="选择日期时间">
                    </el-date-picker>
           </el-form-item>
        </el-col>
        <el-col :span="24">
            <el-form-item label="备注信息" prop="remarks"
                :rules="[
                 ]">
              <el-input type="textarea" v-model="inputForm.remarks" placeholder="请填写备注信息"     ></el-input>
           </el-form-item>
        </el-col>
     </el-row>
    </el-form>
</template>

<script>
  import UserSelect from '@/components/userSelect'
  import OfficeSelect from '@/components/officeSelect'
  import WangEditor from '@/components/editor/WangEditor'
  import testActivitiAuditService from '@/api/test/activiti/testActivitiAuditService'
  export default {
    data () {
      return {
        title: '',
        method: '',
        visible: false,
        loading: false,
        inputForm: {
          id: '',
          user: {
            id: ''
          },
          office: {
            id: ''
          },
          post: '',
          sex: '',
          content: '',
          oldSalray: '',
          newSalary: '',
          exeDate: '',
          remarks: ''
        }
      }
    },
    components: {
      UserSelect,
      OfficeSelect,
      WangEditor
    },
    created () {
    },
    props: {
      businessId: {
        type: String,
        default: ''
      },
      formReadOnly: {
        type: Boolean,
        default: false
      }
    },
    watch: {
      'businessId': {
        handler () {
          if (this.businessId) {
            this.init(this.businessId)
          } else {
            this.$nextTick(() => {
              this.$refs.inputForm.resetFields()
            })
          }
        },
        immediate: true,
        deep: false
      }
    },
    methods: {
      init (id) {
        if (id) {
          this.loading = true
          this.inputForm.id = id
          this.$nextTick(() => {
            this.$refs.inputForm.resetFields()
            testActivitiAuditService.queryById(this.inputForm.id).then((data) => {
              this.inputForm = this.recover(this.inputForm, data)
              this.loading = false
            })
          })
        }
      },
      // 表单提交
      saveForm (callback) {
        this.$refs['inputForm'].validate((valid) => {
          if (valid) {
            this.loading = true
            testActivitiAuditService.save(this.inputForm).then((data) => {
              callback(data.businessTable, data.businessId, this.inputForm)
            }).catch(() => {
              this.loading = false
            })
          }
        })
      }
    }
  }
</script>

  
