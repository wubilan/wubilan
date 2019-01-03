/*
 * Copyright (c) 2018. CISDI INFO
 */

// 引入组件
import job from './components/Job/Job.vue'
import jobLog from './components/JobLog/JobLog.vue'
export default [

    {
        path:'/',component:job
    },
    {
        path:'/jobLog',component:jobLog
    },
]