<template>
	<template v-if="!inputForm.id">
		<el-empty description="请选择左侧数据后操作" :image-size="100"></el-empty>
	</template>
	<template v-else>
    <el-card shadow="never" style="border: 0; padding:10px" :body-style="{border: 0, padding: '20px'}">
      <template #header>
        <div class="card-header">
          <span>{{inputForm.name|| "新增商品类型"}}</span>
          <el-button icon="back" @click="toView" text></el-button>
        </div>
      </template>
    <el-form  label-width="100px" :model="inputForm"  ref="inputForm" v-loading="loading"  @submit.prevent>  
            <el-form-item label="类型名" prop="name"
                :rules="[
                  {required: true, message:'类型名不能为空', trigger:'blur'}
                 ]">
          <el-input v-model="inputForm.name" placeholder="请填写类型名"     ></el-input>
        </el-form-item>
            <el-form-item label="备注信息" prop="remarks"
                :rules="[
                 ]">
          <el-input type="textarea" v-model="inputForm.remarks" placeholder="请填写备注信息"     ></el-input>
        </el-form-item>
        <el-form-item>
            <el-button  type="primary" @click="doSubmit()"  icon="circle-check" v-noMoreClick>确定</el-button>
        </el-form-item>
    </el-form>
    </el-card>  
  </template>
</template>

<script>
  import testCategoryService from '@/api/test/shop/testCategoryService'
  export default {
    props: ['dataList'],
    data () {
      return {
        loading: false,
        inputForm: {}, 
        basicForm: {
          id: '',
          name: '',
          remarks: '',
          parent: {
            id: ''
          },
          parentIds: '',
          sort: ''
        }
      }
    },
    components: {
    },
    created () {
    },
    methods: {
      // 表单提交
      doSubmit () {
        this.$refs['inputForm'].validate((valid) => {
          if (valid) {
            this.loading = true
            testCategoryService.save(this.inputForm).then(() => {
              this.visible = false
              this.$message.success('保存商品类型成功!')
              this.loading = false
            }).catch(() => {
              this.loading = false
            })
          }
        })
      },
      //表单注入数据
      setData (data) {
        this.inputForm = this.pickProperties(data, JSON.parse(JSON.stringify(this.basicForm)))
      },
      //跳转到查看数据
      toView () {
        this.$emit('toView')
      }
    }
  }
</script>

  
