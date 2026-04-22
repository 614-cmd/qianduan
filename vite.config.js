import { defineConfig, loadEnv } from 'vite'
import path from 'path'
import vue from '@vitejs/plugin-vue'
const rewritePlugin = (path, excludes) => ({
  name: "rewrite-middleware",
  configureServer(serve) {
    serve.middlewares.use((req, res, next) => {
      if (req.url.startsWith(path)) {
        const isRaw = excludes.some((dir) => req.url.includes(dir))
        if (!isRaw) req.url = path
      }
      next()
    })
  },
})

export default ({ mode, command }) => {
  const env = loadEnv(mode, process.cwd())
  const { VITE_APP_BASE } = env
  return defineConfig({
    base: './',
    plugins: [vue(),
      rewritePlugin('/datav.html/', ['/datav.html/src', '/datav.html/static'])
    ],
    resolve: {
      // https://cn.vitejs.dev/config/#resolve-alias
      alias: {
        // 设置路径
        '~': path.resolve(__dirname, './'),
        // 设置别名
        '@': path.resolve(__dirname, './src')
      },
      // https://cn.vitejs.dev/config/#resolve-extensions
      extensions: ['.mjs', '.js', '.ts', '.jsx', '.tsx', '.json', '.vue']
    },
    build: {
      rollupOptions: {
        input: {
          index: path.resolve(__dirname, 'index.html'),
          datav: path.resolve(__dirname, 'datav.html')
        }
      }
    },
    // vite 相关配置
    server: {
      port: 2800,
      host: true,
      open: true,
      proxy: {
        '/sys': {
          target: 'http://localhost:8081',
          changeOrigin: true,
        },
        '/api': {
          target: VITE_APP_BASE,
          changeOrigin: true,
          rewrite: (p) => p.replace(/^\/api/, '')
        },
        '/file': {
          target: VITE_APP_BASE,
          changeOrigin: true,
          rewrite: (p) => p.replace(/^\/file/, /file/)
        }
      },
    },
    css: {
      postcss: {
        plugins: [
          {
            postcssPlugin: 'internal:charset-removal',
            AtRule: {
              charset: (atRule) => {
                if (atRule.name === 'charset') {
                  atRule.remove();
                }
              }
            }
          }
        ],
      },
    },
  })
}
