<template>

  <el-popover
    placement="bottom"
    title="Title"
    :width="200"
    trigger="click"
    content="this is content, this is content, this is content"
  >
    <template #reference>
             <Sketch v-model="theme" :preset-colors="predefineColors"/>
            <el-radio-button :label="theme.hex" :value="theme.hex" v-popover:popover class="setting-layout-color-group"> 
               <div class="color-container" style="background: conic-gradient(from 90deg at 50% 50%, rgb(255, 0, 0) -19.41deg, rgb(255, 0, 0) 18.76deg, rgb(255, 138, 0) 59.32deg, rgb(255, 230, 0) 99.87deg, rgb(20, 255, 0) 141.65deg, rgb(0, 163, 255) 177.72deg, rgb(5, 0, 255) 220.23deg, rgb(173, 0, 255) 260.13deg, rgb(255, 0, 199) 300.69deg, rgb(255, 0, 0) 340.59deg, rgb(255, 0, 0) 378.76deg);"></div>
             </el-radio-button>
      <el-button>Click to activate</el-button>
    </template>
  </el-popover>
             
</template>

<script>
    // import theme from "./mixins/theme";
    import { Sketch } from 'vue-color'
    export default {
        name: "ColorPicker",
        props: {
			value: {
				type: String
			}
		},
        components: {
            Sketch
        },
        data () {
            return {
                theme: {
                  hex: "#409EFF"
                },
                chalk: "",
                predefineColors: [
                '#ff4500',
                '#ff8c00',
                '#ffd700',
                '#90ee90',
                '#00ced1',
                '#1e90ff',
                '#c71585',
                'rgba(255, 69, 0, 0.68)',
                'rgb(255, 120, 0)',
                'hsv(51, 100, 98)',
                'hsva(120, 40, 94, 0.5)',
                'hsl(181, 100%, 37%)',
                'hsla(209, 100%, 56%, 0.73)',
                '#c7158577'
                ]
            };
        }
    };
</script>

<style>
.popper-class {
    padding: 0px !important;
}
</style>
