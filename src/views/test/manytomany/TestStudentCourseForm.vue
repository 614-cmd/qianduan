<template>
  <v-dialog
    :title="title"
    :close-on-click-modal="false"
    v-model="visible">
    <el-form :model="inputForm" ref="inputForm" v-loading="loading" :class="method==='view'?'readonly':''"  :disabled="method==='view'" label-width="120px">
      <el-row  :gutter="15">
        <el-col :span="12">
            <el-form-item label="学生" prop="student.id"
                :rules="[
                 ]">
          <GridSelect
            title="选择学生"
            labelName = 'name'
            labelValue = 'id'
            v-model = "inputForm.student.id"
            :limit="1"
            :columns="[
            {
              prop: 'name',
              label: '姓名'
            }
            ]"
            :searchs="[
            {
              prop: 'name',
              label: '姓名'
            }
            ]"
            :api="testStudentService">
          </GridSelect>
           </el-form-item>
        </el-col>
        <el-col :span="12">
            <el-form-item label="课程" prop="course.id"
                :rules="[
                 ]">
          <GridSelect
            title="选择课程"
            labelName = 'name'
            labelValue = 'id'
            v-model = "inputForm.course.id"
            :limit="1"
            :columns="[
            {
              prop: 'name',
              label: '课程名'
            }
            ]"
            :searchs="[
            {
              prop: 'name',
              label: '课程名'
            }
            ]"
            :api="testCourseService">
          </GridSelect>
           </el-form-item>
        </el-col>
        <el-col :span="12">
            <el-form-item label="分数" prop="score"
                :rules="[
                 ]">
              <el-input v-model="inputForm.score" placeholder="请填写分数"     ></el-input>
           </el-form-item>
        </el-col>
        <el-col :span="12">
            <el-form-item label="备注信息" prop="remarks"
                :rules="[
                 ]">
          <el-input type="textarea" v-model="inputForm.remarks" placeholder="请填写备注信息"     ></el-input>
           </el-form-item>
        </el-col>
        </el-row>
    </el-form>
    <template #footer>    
        <span class="dialog-footer">
          <el-button @click="visible = false" icon="circle-close">关闭</el-button>
          <el-button  type="primary" v-if="method != 'view'" @click="doSubmit()" icon="circle-check" v-noMoreClick>确定</el-button>
        </span>
    </template>    
  </v-dialog>
</template>

<script>
  import GridSelect from '@/components/gridSelect'
  import testStudentCourseService from '@/api/test/manytomany/testStudentCourseService'
  import testStudentService from '@/api/test/manytomany/testStudentService'   
  import testCourseService from '@/api/test/manytomany/testCourseService'   
  export default {
    data () {
      return {
        title: '',
        method: '',
        visible: false,
        loading: false,
        testStudentService: testStudentService,  
        testCourseService: testCourseService,  
        inputForm: {
          id: '',
          student: {
            id: ''
          },
          course: {
            id: ''
          },
          score: '',
          remarks: ''
        }
      }
    },
    components: {
      GridSelect
    },
    created () {
    },
    methods: {
      init (method, id) {
        this.method = method
        this.inputForm.id = id
        if (method === 'add') {
          this.title = `新建学生课程记录`
        } else if (method === 'edit') {
          this.title = '修改学生课程记录'
        } else if (method === 'view') {
          this.title = '查看学生课程记录'
        }
        this.visible = true
        this.loading = false
        this.$nextTick(() => {
          this.$refs.inputForm.resetFields()
          if (method === 'edit' || method === 'view') { // 修改或者查看
            this.loading = true
            testStudentCourseService.queryById(this.inputForm.id).then((data) => {
              this.inputForm = this.recover(this.inputForm, data)
              this.loading = false
            })
          }
        })
      },
      // 表单提交
      doSubmit () {
        this.$refs['inputForm'].validate((valid) => {
          if (valid) {
            this.loading = true
            testStudentCourseService.save(this.inputForm).then((data) => {
              this.visible = false
              this.$message.success(data)
              this.$emit('refreshDataList')
              this.loading = false
            }).catch(() => {
              this.loading = false
            })
          }
        })
      }
    }
  }
</script>

  
