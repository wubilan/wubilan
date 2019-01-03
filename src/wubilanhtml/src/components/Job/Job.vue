<template>
    <el-card style="width: 100%;margin-top: 18px">
        <el-button type="primary" plain @click="selectJobLog()" style="float: right;margin-right: 20px">查看日志</el-button>

        <h2 style="text-align: center">定时任务</h2>

        <Paginate :baseUrl="baseUrl" :headers='headers' :detailLabel='detailLabel' :searcForm='searcForm' :sortMap='sortMap' :detailInformation="detailInformation">
    </Paginate>
    </el-card>
</template>
<script>
import Paginate from '~/components/Job/JobPaginate.vue';
export default {
    data: function () {
        return {
            baseUrl: '/job',
            headers: [
                { prop: 'id', label: '定时任务id',isId:true },
                { prop: 'targetUrl', label: '地址',isLength:true },
                { prop: 'cronExp', label: '定时规则' },
                { prop: 'memo', label: '备注',isLength:false },
                { prop: 'status', label: '状态',isStatus:true},
                // { prop: 'createUserId', label: '创建人id' },
                // { prop: 'createUserName', label: '创建人用户名' },
                { prop: 'createTime', label: '创建时间',isTime:true },
                // { prop: 'updateUserId', label: '修改人id' },
                // { prop: 'updateUserName', label: '修改人用户名' },
                { prop: 'updateTime', label: '修改时间',isTime:true },
            ],
            detailLabel: [
                { prop: 'targetUrl', label: '地址', notnull: true },
                { prop: 'cronExp', label: '定时规则', notnull: true },
                { prop: 'memo', label: '备注', notnull: true },
            ],

            sortMap: {
                createTime: 'desc'
            },
            searcForm: [
                { column: 'createTime', operation:'like', value: '', name: '创建时间',isTime:true },
                { column: 'updateTime', operation:'like', value: '', name: '修改时间',isTime:true },
                { column: 'targetUrl', operation: 'like', value: '', name: '地址' },
                { column: 'createUserId', operation: 'like', value: '', name: '创建人id'},
                { column: 'createUserName', operation: 'like', value: '', name: '创建人用户名'},
                { column: 'updateUserId', operation: 'like', value: '', name: '修改人id' },
                { column: 'updateUserName', operation: 'like', value: '', name: '修改人用户名'},
            ],
            detailInformation: [
                { prop: 'id', label: '定时任务id' },
                { prop: 'targetUrl', label: '地址' },
                { prop: 'cronExp', label: '定时规则' },
                { prop: 'memo', label: '备注' },
                { prop: 'status', label: '状态'},
                { prop: 'createUserId', label: '创建人id' },
                { prop: 'createUserName', label: '创建人用户名' },
                { prop: 'createTime', label: '创建时间', },
                { prop: 'updateUserId', label: '修改人id' },
                { prop: 'updateUserName', label: '修改人用户名' },
                { prop: 'updateTime', label: '修改时间', },
            ],

        }
    },
    methods:{
        selectJobLog(){
            this.$router.push("/joblog");
        }
    },
    components: {
        Paginate
    }
}
</script>