<template>
  <v-dialog
    :title="title"
    :close-on-click-modal="false"
    v-model="visible">
    <el-form :model="inputForm" ref="inputForm" v-loading="loading" :class="method==='view'?'readonly':''"  :disabled="method==='view'"
             label-width="120px">
      <el-row  :gutter="15">
        <el-col :span="24">
            <el-form-item label="名称" prop="name"
                :rules="[
                 ]">
          <el-input v-model="inputForm.name" placeholder="请填写名称"     ></el-input>
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
    <template #footer>    
    	<span class="dialog-footer">
	    <el-button @click="visible = false">关闭</el-button>
	    <el-button type="primary" v-if="method != 'view'" @click="doSubmit()" v-noMoreClick>确定</el-button>
	</span>
    </template>    
  </v-dialog>

</template>

<script>
  import testCarKindService from '@/api/test/treetable/testCarKindService'
  export default {
    data () {
      return {
        title: '',
        method: '',
        visible: false,
        loading: false,
        inputForm: {
          id: '',
          name: '',
          sort: '',
          remarks: '',
          parent: {
            id: ''
          },
          parentIds: ''
        }
      }
    },
    components: {
    },
    created () {
    },
    methods: {
      init (method, obj) {
        this.method = method
        this.inputForm.id = obj.id
        if (method === 'add') {
          this.title = '新建车系'
        } else if (method === 'addChild') {
          this.title = '添加下级车系'
        } else if (method === 'edit') {
          this.title = '修改车系'
        } else if (method === 'view') {
          this.title = '查看车系'
        }
        this.visible = true
        this.loading = false
        this.$nextTick(() => {
          this.$refs.inputForm.resetFields()
          this.inputForm.parent.id = obj.parent.id
          this.inputForm.sort = obj.sort
          testCarKindService.treeData({extId: this.inputForm.id}).then((data) => {
            this.testCarKindTreeData = data
          })
          if (method === 'edit' || method === 'view') { // 修改或者查看
            this.loading = true
            testCarKindService.queryById(this.inputForm.id).then((data) => {
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
            testCarKindService.save(this.inputForm).then((data) => {
              this.loading = false
              this.visible = false
              this.$message.success(data)
              this.$emit('refreshTree')
            })
          }
        })
      }
    }
  }
</script>

  
