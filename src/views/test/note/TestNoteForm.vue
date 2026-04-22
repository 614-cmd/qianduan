<template>
  <v-dialog
    :title="title"
    :close-on-click-modal="false"
    v-model="visible">
    <el-form :model="inputForm" ref="inputForm" v-loading="loading" :class="method==='view'?'readonly':''"  :disabled="method==='view'" label-width="120px">
      <el-row  :gutter="15">
        <el-col :span="12">
            <el-form-item label="标题" prop="title"
                :rules="[
                 ]">
              <el-input v-model="inputForm.title" placeholder="请填写标题"     ></el-input>
           </el-form-item>
        </el-col>
        <el-col :span="24">
            <el-form-item label="富文本" prop="contents"
                :rules="[
                 ]">
            <wang-editor v-model="inputForm.contents"/>
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
  import WangEditor from '@/components/editor/WangEditor'
  import testNoteService from '@/api/test/note/testNoteService'
  export default {
    data () {
      return {
        title: '',
        method: '',
        visible: false,
        loading: false,
        inputForm: {
          id: '',
          title: '',
          contents: '',
          remarks: ''
        }
      }
    },
    components: {
      WangEditor
    },
    created () {
    },
    methods: {
      init (method, id) {
        this.method = method
        this.inputForm.id = id
        if (method === 'add') {
          this.title = `新建富文本`
        } else if (method === 'edit') {
          this.title = '修改富文本'
        } else if (method === 'view') {
          this.title = '查看富文本'
        }
        this.visible = true
        this.loading = false
        this.$nextTick(() => {
          this.$refs.inputForm.resetFields()
          if (method === 'edit' || method === 'view') { // 修改或者查看
            this.loading = true
            testNoteService.queryById(this.inputForm.id).then((data) => {
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
            testNoteService.save(this.inputForm).then((data) => {
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

  
