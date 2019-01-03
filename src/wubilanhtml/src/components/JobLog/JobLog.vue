<template>
    <el-card style="width: 100%;margin:auto;padding: auto;margin-top: 20px">
        <el-button type="primary" plain @click="selectJob()" style="float: right;margin-right: 20px">查看定时任务</el-button>
        <h2 style="text-align: center">定时任务日志</h2>

        <Paginate :baseUrl="baseUrl" :headers='headers' :searcForm='searcForm' :sortMap='sortMap'>
    </Paginate>
    </el-card>
</template>
<script>
import Paginate from '~/components/JobLog/JobLogPaginate.vue';
export default {
    data: function () {
        return {
            baseUrl: '/job/jobLog',
            headers: [
                { prop: 'id', label: '定时任务日志id' },
                { prop: 'schedulerId', label: '任务id' },
                { prop: 'targetUrl', label: '地址' },
                { prop: 'executeNode', label: '执行节点' },
                { prop: 'status', label: '状态'},
                { prop: 'resultMessage', label: '结果消息' },
                { prop: 'resultCode', label: '结果编码' },
                { prop: 'createTime', label: '创建时间',isTime:true },
                { prop: 'executeStartTime', label: '开始执行时间',isTime:true },
                { prop: 'executeEndTime', label: '结束执行时间',isTime:true },
                { prop: 'executeTime', label: '执行时间' },
            ],
            sortMap: {
                createTime: 'desc'
            },
            searcForm: [
                { column: 'createTime', operation:'like', value: '', name: '创建时间',isTime:true },
                { column: 'status', operation: 'like', value: '', name: '状态',isStatus:true },
                { column: 'targetUrl', operation: 'like', value: '', name: '地址' },
                { column: 'executeNode', operation: 'like', value: '', name: '执行节点' },
            ]
        }
    },
    methods:{
        selectJob(){
            this.$router.push("/")
        }
    },
    components: {
        Paginate
    }
}
</script>