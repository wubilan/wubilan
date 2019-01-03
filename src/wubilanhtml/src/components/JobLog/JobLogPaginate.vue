<template>
    <div>
        <div>
            <el-form  label-width="80px" :inline="true">
            <template v-for="item in searcForm">
                <el-form-item v-if="item.isTime" label-width="130px" :label="item.name" :key="item.column">
                    <el-date-picker
                            v-model="item.value"
                            style="width: 460px"
                            type="daterange"
                            range-separator="至"
                            start-placeholder="开始日期"
                            end-placeholder="结束日期">
                    </el-date-picker>
                </el-form-item>
                <el-form-item v-else-if="item.isStatus" :label="item.name" label-width="130px" :key="item.column">
                    <el-select v-model="optionStatus" placeholder="请选择">
                        <el-option
                                v-for="item in statusOptions"
                                :key="item.value"
                                :label="item.label"
                                :value="item.value">
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item v-else :label="item.name" label-width="130px" :key="item.column">
                    <el-input v-model="item.value" ></el-input>
                </el-form-item>
            </template>
            <el-form-item>
                <el-button type="primary" @click="onSubmit">查询</el-button>
            </el-form-item>
            </el-form>
        </div>
         <el-table :data="list" border style="width: 100%">
             <template v-for="header in headers">
                 <el-table-column v-if="header.isTime" align="center" :formatter="formatDateTime" :prop="header.prop" :key="header.prop" :label="header.label" :width="header.width"></el-table-column>
                 <el-table-column v-else :prop="header.prop"  align="center"  :key="header.prop" :label="header.label" :width="header.width"></el-table-column>
             </template>
         </el-table>
         <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :page-sizes="[10, 15, 20]"
            :page-size="searchParam.pageSize" layout="total, sizes, prev, pager, next, jumper" :total="searchParam.total"
            :current-page="searchParam.pageNum" style="text-align: center"></el-pagination>
    </div>
</template>
<script>
export default {
    name: 'paginate',
    props: {
      headers:Array,
      baseUrl:String,
      sortMap:Object,
      searcForm:Array,
    },
    data:function(){
        return{
            optionStatus:'',
            statusOptions:[
                {value:'SUCCESS',label:'成功'},
                {value:'FAIL',label:'失败'}],
            searcFormTemp:[],
            list:[],
            searchParam:{
                pageNum:1,
                pageSize:10,
                pages:0,
                total:0,
                clauses:[],
                sortMap:this.sortMap
            },
        }
    },
    created(){
        this.searcFormTemp=this.searcForm;
    },
    methods:{
        formatDateTime(row, column, cellValue, index){
            if (cellValue === null){
                return null;
            } else {
                let rowTime = this.$moment(cellValue).format("YYYY-MM-DD HH:mm:ss");
                row[column.property] = rowTime.replace(/-/g,"/");
                return rowTime;
            }
        },
        handleSizeChange(val) {
            this.searchParam.pageSize = val;
            this.doSearch();
        },
        handleCurrentChange(val) {
            this.searchParam.pageNum = val;
            this.doSearch();
        },
        transferStatusToChinese(list){
            let self=this;
            let listTemp=[];
            listTemp = list;
            listTemp.forEach((value,index)=>{
                if(value.status=='SUCCESS'){
                    listTemp[index].status='成功';
                }
                if(value.status=='FAIL'){
                    listTemp[index].status='失败';
                }
            });
           return listTemp;
        },
        doSearch(){
            let self = this;
            self.exportParma = JSON.parse(JSON.stringify(self.searchParam));
            self.$Axios.post(self.baseUrl + "/selectPage   ",self.searchParam
            ).then(function(res){
                self.list=self.transferStatusToChinese(res.data.pageInfo.list);
                self.searchParam.total = res.data.pageInfo.total;
                if (res.data.pageInfo.pages < self.searchParam.pageNum){
                    self.searchParam.pageNum = res.data.pageInfo.pages;
                    self.doSearch();
                } else {
                    self.searchParam.pageNum = res.data.pageInfo.pageNum;
                }
            });
        },
        onSubmit(){
            let self=this;
            let tempClauses=[];
            let searcFormTemp=[];
            searcFormTemp=self.searcForm;
            searcFormTemp.forEach((value,index)=>{
                if (value.column=='createTime'||value.column=='updateTime') {
                    if(value.value!=''&&value.value!=null){
                        let tempSmall={};
                        let tempLarge={};
                        tempSmall.column=value.column;
                        tempLarge.column=value.column;
                        tempSmall.name=value.name;
                        tempLarge.name=value.name;
                        tempSmall.operation=">=";
                        tempLarge.operation="<=";
                        if(value.value[0]<value.value[1]){
                            tempSmall.value=self.$moment(value.value[0]).format("YYYY-MM-DD HH:mm:ss");
                            tempLarge.value=self.$moment(value.value[1]).format("YYYY-MM-DD HH:mm:ss");
                        }else {
                            tempSmall.value=self.$moment(value.value[1]).format("YYYY-MM-DD HH:mm:ss");
                            tempLarge.value=self.$moment(value.value[0]).format("YYYY-MM-DD HH:mm:ss");
                        }
                        tempClauses.push(tempSmall);
                        tempClauses.push(tempLarge);
                    }
                }else {
                    if(value.column=='status'){
                        let temp={};
                        temp.column=value.column;
                        temp.name=value.name;
                        temp.operation="=";
                        temp.value=self.optionStatus;
                        tempClauses.push(temp);
                    }else {
                        tempClauses.push(value)
                    }
                }

            });
            self.searchParam.clauses=tempClauses;
            self.doSearch();
        },
    },
    mounted:function(){
       this.doSearch();
    }
}
</script>
<style lang="less">
    .category-dialog{
        .el-cascader{
            width: 100%;
        }
    }
</style>
