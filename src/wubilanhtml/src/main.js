import Vue from 'vue'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import App from './App.vue'
import upperFirst from 'lodash/upperFirst'
import camelCase from 'lodash/camelCase'
import VueRouter from 'vue-router'
import routeConfig from './router-config'
import axios from 'axios'
import moment from 'moment'
axios.interceptors.response.use(function(response){
  return response;
},function (error) {
  //出现错误填入main
	localStorage.clear()
		router.replace({
                        path: '/main',
                        query: {redirect: router.currentRoute.fullPath}
                    })
	}
);
Vue.prototype.$Axios = axios;
Vue.prototype.$moment = moment;

const requireComponent = require.context(
    // components 文件夹的相对路径
    './components',
    // 是否查找子文件夹
    false,
    // 用于匹配组件文件名的正则表达式
    /[A-Z]\w+\.(vue|js)$/
)
requireComponent.keys().forEach(fileName => {
    // 获取组件配置
    const componentConfig = requireComponent(fileName)

    // 取得组件的 Pascal 式命名
    const componentName = upperFirst(
        camelCase(
            // 将文件名前面的 `'./` 和扩展名剥离
            fileName.replace(/^\.\/(.*)\.\w+$/, '$1')
        )
    )
    // 以全局方式注册组件
    Vue.component(
    componentName,
    // 如果组件是通过 `export default` 导出，
    // 则在 `.default` 中，查找组件选项，
    // 否则回退至模块根对象中，查找组件选项
    componentConfig.default || componentConfig
)
})
Vue.use(ElementUI)
Vue.use(VueRouter)
//定义路由
const router = new VueRouter({
    routes: routeConfig
})
new Vue({
    router,
  el: '#app',
  render: h => h(App)
})
