<template>
  <v-dialog
    :title="title"
    :close-on-click-modal="false"
    v-model="visible">
    <el-form :model="inputForm" ref="inputForm" v-loading="loading" :class="method==='view'?'readonly':''"  :disabled="method==='view'" label-width="120px">
      <el-row  :gutter="15">
        <el-col :span="24">
            <el-form-item label="组件名称" prop="name"
                :rules="[
                 ]">
              <el-input v-model="inputForm.name" placeholder="请填写组件名称"     ></el-input>
           </el-form-item>
        </el-col>
        <el-col :span="24">
            <el-form-item label="组件内容" prop="content"
                :rules="[
                 ]">
          <v-ace-editor
              v-model:value="inputForm.content"
              @init="editorInit"
              lang="javascript"
              theme="monokai"
              style="height: 400px; width: 100%;"
            >
            </v-ace-editor>
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
  import dataComponentService from '@/api/datav/dataComponentService'
  import * as ace from 'ace-builds';
  ace.config.set('basePath', '/static/src-min-noconflict/');
  import { VAceEditor } from "vue3-ace-editor";
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
          content: ''
        }
      }
    },
    components: {
      VAceEditor
    },
    created () {
    },
    methods: {
      init (method, id) {
        this.method = method
        this.inputForm.id = id
        if (method === 'add') {
          this.title = `新建大屏组件`
        } else if (method === 'edit') {
          this.title = '修改大屏组件'
        } else if (method === 'view') {
          this.title = '查看大屏组件'
        }
        this.visible = true
        this.loading = false
        this.$nextTick(() => {
          this.$refs.inputForm.resetFields()
          if (method === 'edit' || method === 'view') { // 修改或者查看
            this.loading = true
            dataComponentService.queryById(this.inputForm.id).then((data) => {
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
            dataComponentService.save(this.inputForm).then((data) => {
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

  
