<template>
  <v-dialog
    :title="title"
    :close-on-click-modal="false"
    v-model="visible">
    <el-form :model="inputForm" ref="inputForm" v-loading="loading" :class="method==='view'?'readonly':''"  :disabled="method==='view'" label-width="120px">
      <el-row  :gutter="15">
        <el-col :span="12">
            <el-form-item label="品牌" prop="name"
                :rules="[
                 ]">
          <el-input v-model="inputForm.name" placeholder="请填写品牌"     ></el-input>
           </el-form-item>
        </el-col>
        <el-col :span="12">
            <el-form-item label="车系" prop="kind.id"
                :rules="[
                 ]">
          <el-tree-select
	          value-key="id" 
	          :props="{
	              label: 'name',         // 显示名称
	              children: 'children'    // 子级字段名
	            }"
               placeholder="请选择车系"
              :data="testCarKindTreeData"
              v-model="inputForm.kind.id"
              :clearable="true"
              :accordion="true"/>
           </el-form-item>
        </el-col>
        <el-col :span="12">
            <el-form-item label="简介" prop="remarks"
                :rules="[
                 ]">
          <el-input type="textarea" v-model="inputForm.remarks" placeholder="请填写简介"     ></el-input>
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
  import testCarService from '@/api/test/treetable/testCarService'
  import testCarKindService from '@/api/test/treetable/testCarKindService'      
  export default {
    data () {
      return {
        title: '',
        method: '',
        visible: false,
        loading: false,
        testCarKindTreeData: [],
        inputForm: {
          id: '',
          name: '',
          kind: {
            id: ''
          },
          remarks: ''
        }
      }
    },
    components: {
    },
    created () {
      testCarKindService.treeData().then((data) => {
          this.kindTreeData = data
      })
    },
    methods: {
      init (method, id) {
        this.method = method
        this.inputForm.id = id
        if (method === 'add') {
          this.title = `新建车辆`
        } else if (method === 'edit') {
          this.title = '修改车辆'
        } else if (method === 'view') {
          this.title = '查看车辆'
        }
        this.visible = true
        this.loading = false
        this.$nextTick(() => {
          this.$refs.inputForm.resetFields()    
          testCarKindService.treeData().then((data) => {
            this.testCarKindTreeData = data
          })
          if (method === 'edit' || method === 'view') { // 修改或者查看
            this.loading = true
            testCarService.queryById(this.inputForm.id).then((data) => {
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
            testCarService.save(this.inputForm).then((data) => {
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

  
