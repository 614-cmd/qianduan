<template>
  <v-dialog
    :title="title"
    :close-on-click-modal="false"
    v-model="visible">
    <el-form :model="inputForm" ref="inputForm" v-loading="loading" :class="method==='view'?'readonly':''"  :disabled="method==='view'" label-width="120px">
      <el-row  :gutter="15">
        <el-col :span="12">
            <el-form-item label="国名" prop="name"
                :rules="[
                 ]">
              <el-input v-model="inputForm.name" placeholder="请填写国名"     ></el-input>
           </el-form-item>
        </el-col>
        <el-col :span="12">
            <el-form-item label="人口" prop="sum"
                :rules="[
                 ]">
              <el-input v-model="inputForm.sum" placeholder="请填写人口"     ></el-input>
           </el-form-item>
        </el-col>
        <el-col :span="12">
            <el-form-item label="所属洲" prop="continent.id"
                :rules="[
                 ]">
          <GridSelect
            title="选择所属洲"
            labelName = 'name'
            labelValue = 'id'
            v-model = "inputForm.continent.id"
            :limit="1"
            :columns="[
            {
              prop: 'name',
              label: '洲名'
            }
            ]"
            :searchs="[
            {
              prop: 'name',
              label: '洲名'
            }
            ]"
            :api="testContinentService">
          </GridSelect>
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
  import testCountryService from '@/api/test/grid/testCountryService'
  import testContinentService from '@/api/test/grid/testContinentService'   
  export default {
    data () {
      return {
        title: '',
        method: '',
        visible: false,
        loading: false,
        testContinentService: testContinentService,  
        inputForm: {
          id: '',
          name: '',
          sum: '',
          continent: {
            id: ''
          },
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
          this.title = `新建国家`
        } else if (method === 'edit') {
          this.title = '修改国家'
        } else if (method === 'view') {
          this.title = '查看国家'
        }
        this.visible = true
        this.loading = false
        this.$nextTick(() => {
          this.$refs.inputForm.resetFields()
          if (method === 'edit' || method === 'view') { // 修改或者查看
            this.loading = true
            testCountryService.queryById(this.inputForm.id).then((data) => {
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
            testCountryService.save(this.inputForm).then((data) => {
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

  
