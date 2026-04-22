<template>
  <monaco-editor v-model="code"
                 v-if="reload"
                 :read-only="disabled"
                 :language="language"
                 :options="options"
                 :height="height"></monaco-editor>
</template>

<script>
import MonacoEditor from './monaco-editor'
export default {
  components: { MonacoEditor },
  data () {
    return {
      code: '',
      reload: false,
      options: {
        minimap: {
          enabled: false,
        },
        fullScreen: true,
        fontSize: 12
      }
    }
  },
  props: {
    language: {
      type: String,
      default: 'javascript'
    },
    disabled: Boolean,
    height: {
      type: [String, Number],
      default: 400
    },
    modelValue: [String, Object, Array]
  },
  watch: {
    code (val) {
      this.$emit('update:modelValue', val);
    },
    modelValue: {
      handler (val) {
        if (typeof (val) == 'object') {
          this.code = JSON.stringify(val, null, 4);
        } else {
          this.code = val || '';
        }
      },
      immediate: true,
      deep: true,
    },
  },
  mounted () {
    setTimeout(() => {
      this.reload = true
    })
  }
}
</script>
