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
                <el-form-item v-else :label="item.name" label-width="130px" :key="item.column">
                    <el-input v-model="item.value" ></el-input>
                </el-form-item>
            </template>
            <el-form-item>
                <el-button type="primary" @click="onSubmit">查询</el-button>
                <el-button type="primary" @click="add">新增</el-button>
            </el-form-item>
            </el-form>
        </div>
         <el-table :data="list" border style="width: 100%">
             <template v-for="header in headers">
                 <el-table-column v-if="header.isTime" align="center" :formatter="formatDateTime" :prop="header.prop" :key="header.prop" :label="header.label" :width="header.width"></el-table-column>
                 <el-table-column v-else-if="header.isLength" align="center" :prop="header.prop"   :key="header.prop" :label="header.label" :width="330"></el-table-column>
                 <el-table-column v-else-if="header.isId" align="center" :prop="header.prop"   :key="header.prop" :label="header.label" :width="100"></el-table-column>
                 <el-table-column v-else-if="header.isStatus" align="center" :prop="header.prop"   :key="header.prop" :label="header.label" :width="100"></el-table-column>
                 <el-table-column v-else :prop="header.prop"  align="center" :key="header.prop" :label="header.label" :width="header.width"></el-table-column>
             </template>
              <el-table-column fixed="right" label="操作" width="330" >
                 <template slot-scope="scope">
                     <el-button type="text"  @click="detailClick(scope.row)" size="small">详情</el-button>
                     <el-button type="text"  @click="handleClick(scope.row)" size="small">编辑</el-button>
                    <el-button  type="text" @click="deleteClick(scope.row)" size="small">删除</el-button>
                    <el-button  type="text" @click="buttonClick(scope.row,'/pause')" size="small">暂停</el-button>
                    <el-button  type="text" @click="buttonClick(scope.row,'/resume')" size="small">恢复</el-button>
                    <el-button  type="text" @click="runClick(scope.row)" size="small">单次执行</el-button>
                </template>
            </el-table-column>
         </el-table>
         <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :page-sizes="[10, 15, 20]"
            :page-size="searchParam.pageSize" layout="total, sizes, prev, pager, next, jumper" :total="searchParam.total"
            :current-page="searchParam.pageNum" style="text-align: center;margin-top: 20px"></el-pagination>
            <div class="category-dialog">
                <el-dialog :title="'编辑'" :visible="dialogFormVisible" :show-close="false" width="50%">
                    <el-form :label-position="'right'" label-width="100px">
                         <template v-for="item in detailLabel">
                             <el-form-item :label="item.label" :key="item.prop" >
                                 <template v-if="item.prop === 'cronExp'">
                                     <el-input  v-model="detailForm[item.prop]"  auto-complete="off">
                                         <el-button slot="append" v-if="!showCronBox" icon="el-icon-arrow-up" @click="showCronBox = true" title="打开图形配置"></el-button>
                                         <el-button slot="append" v-else icon="el-icon-arrow-down" @click="showCronBox = false" title="关闭图形配置"></el-button>
                                     </el-input>
                                     <div style="margin-top: 15px;">
                                         <el-form :model="form" label-width="80px">
                                             <el-form-item style="margin-top: -10px; margin-bottom:0px;">
                                                 <cron v-if="showCronBox" v-model="detailForm['cronExp']"></cron>
                                             </el-form-item>
                                         </el-form>
                                     </div>
                                 </template>
                                 <el-input v-else  v-model="detailForm[item.prop]"></el-input>
                             </el-form-item>
                         </template>

                    </el-form>
                    <div slot="footer" class="dialog-footer">
                        <el-button @click="btncancel">取 消</el-button>
                        <el-button type="primary"  @click="btnOk">确 定</el-button>
                    </div>
                </el-dialog>
            </div>

        <div class="category-dialog">
            <el-dialog :title="'定时任务详情'" :visible="dialogDetailVisible" :show-close="false" width="50%">
                <el-form :label-position="'right'" label-width="130px" style="height: 450px;overflow-x: hidden">
                    <template v-for="detail in detailInformation">
                        <el-form-item :label="detail.label" :key="detail.prop" style="margin-top: 10px">
                            <div style="background-color: #66b1ff0a;" v-text="detailInformationData[detail.prop]"></div>
                        </el-form-item>
                    </template>
                </el-form>
                <div slot="footer" class="dialog-footer">
                    <el-button @click="dialogDetailVisible=false">关 闭</el-button>
                </div>
            </el-dialog>
        </div>
    </div>
</template>
<script>
import qs from 'qs'
import cron from "~/components/CronExpression/cron.vue";

export default {
    components:{cron},
    name: 'paginate',
    props: {
      headers:Array,
      baseUrl:String,
      sortMap:Object,
      detailLabel:Array,
      searcForm:Array,
      exportUrl:String,
      detailInformation:Array,
    },
    data:function(){
        return{
            searcFormTemp:[],
            showCronBox: false,
            dialogFormVisible:false,
            detailForm:{},
            list:[],
            searchParam:{
                pageNum:1,
                pageSize:10,
                pages:0,
                total:0,
                clauses:[],
                sortMap:this.sortMap
            },
            exportParma:{},
            labelPosition:'left',
            form:'',
            detailInformationData:{},
            dialogDetailVisible:false,
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
        buttonClick(row,url){
            let self = this;
            let msg;
            if(url=='resume'){
                msg='恢复该定时任务，是否继续？';
            }else {
                msg='暂停该定时任务，是否继续？';
            }
            self.$confirm(msg, '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                self.$Axios.get(self.baseUrl + url + '/' + row.id).then(function (res) {
                    if (res.data.success === true) {
                        self.$notify({
                            title: '成功',
                            message: res.data.message,
                            type: 'success'
                        });
                    } else {
                        self.$notify({
                            title: '错误',
                            message: res.data.message,
                            type: 'error'
                        });
                    }
                    self.doSearch();
                });
            });
        },
        runClick(row){
            let self = this;
            let schedulerJob={};
            schedulerJob=self.transferStatusToLetter(row);
            self.$confirm('单次执行该定时任务，是否继续？', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                self.$Axios.post(self.baseUrl+'/run',qs.stringify(schedulerJob)).then(function (res) {
                    if(res.data.success === true){
                        self.$notify({
                            title: '成功',
                            message: res.data.message,
                            type: 'success'
                        });
                    }else {
                        self.$notify({
                            title: '错误',
                            message: res.data.message,
                            type: 'error'
                        });
                    }
                    self.doSearch();
                });
            });
        },
        add(){
          this.detailForm = {is_insert:true};
          this.dialogFormVisible = true;
        },
        btnOk(){
          let self = this;
          // self.addAndUpdateDisable=true;
          let msg = "";

          for(let checkItem in self.detailLabel){
            if (self.detailLabel[checkItem].notnull == true){
              if (self.detailForm[self.detailLabel[checkItem].prop] == undefined 
              || self.detailForm[self.detailLabel[checkItem].prop].replace(/^\s+|\s+$/g, '') == ""){
                msg += "【" + self.detailLabel[checkItem].label + "】不能为空！<br>";
              }
            }
          }

          if (msg != ""){
            self.$message.error({
              dangerouslyUseHTMLString: true,
              message: msg
            });
            return;
          }
          
          if (self.detailForm.is_insert == true){
            msg = "新增这条记录，是否继续？";
          } else {
            msg = "更新这条记录，是否继续？";
          }

          self.$confirm(msg, '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
          }).then(() => {
             if (self.detailForm.is_insert == true){
               self.$Axios.post(self.baseUrl+"/addJob",qs.stringify(self.detailForm)).then(function(res){
                 if (res.data.success == true){
                      self.$notify({
                        title: '成功',
                        message: res.data.message,
                        type: 'success'
                      });
                  } else {
                       self.$notify({
                           title: '错误',
                           message: res.data.message,
                           type: 'error'
                       });
                   }
                  self.onSubmit();
                  self.dialogFormVisible = false;
                   self.addAndUpdateDisable=false;
               });

             } else {
                let form = self.detailForm;
                for (let item in form) {
                    if(form[item] === null){
                        delete form[item];
                    }
                }
               self.detailForm = form;
                 self.detailFor=self.transferStatusToLetter(self.detailForm);
               self.$Axios.put(self.baseUrl+"/updateJob",qs.stringify(self.detailForm)).then(function(res){
                 if (res.data.success == true){
                      self.$notify({
                        title: '成功',
                        message:res.data.message,
                        type: 'success'
                      });
                  }else {
                     self.$notify({
                         title: '错误',
                         message: res.data.message,
                         type: 'error'
                     });
                 }
                  self.onSubmit();
                  self.dialogFormVisible = false;
                   self.addAndUpdateDisable=false;
               });

             }
          });
           
         
        },
        deleteClick(row){
          let self = this;
          self.$confirm('此操作将删除该条记录, 是否继续?', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
          }).then(() => {
            self.$Axios.delete(self.baseUrl+"/deleteJob/"+row.id).then(function(res){
              if (res.data.success == true){
                self.$notify({
                  title: '成功',
                  message:  res.data.message,
                  type: 'success'
                });
              }else {
                  self.$notify({
                      title: '错误',
                      message: res.data.message,
                      type: 'error'
                  });
              }
             
              self.onSubmit();
            
          }).catch(() => {

          });});

        },
        handleClick(row){
            this.detailForm = JSON.parse(JSON.stringify(row));
            this.dialogFormVisible = true;
        },
        detailClick(row){
            let _this=this;
            _this.dialogDetailVisible=true;
            _this.detailInformationData=row;
            console.log("111111111111111111333")
            console.log(_this.detailInformation)
            console.log(_this.detailInformationData)
        },
        btncancel(){
            this.dialogFormVisible = false;
        },
        handleSizeChange(val) {
            this.searchParam.pageSize = val;
            this.doSearch();
        },
        handleCurrentChange(val) {
            this.searchParam.pageNum = val;
            this.doSearch();
        },
        transferStatusToLetter(detailForm){
            let self=this;
            let temp={};
            temp = detailForm;
            if(temp.status=='正常'){
                temp.status='NORMAL';
            }
            if (temp.status=='暂停'){
                temp.status='PAUSE';
            }
            return temp;
        },
        transferStatusToChinese(list){
            let self=this;
            let listTemp=[];
            listTemp = list;
            listTemp.forEach((value,index)=>{
                if(value.status=='NORMAL'){
                    listTemp[index].status='正常';
                }
                if(value.status=='PAUSE'){
                    listTemp[index].status='暂停';
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
                    if(value.column=='createUserId'||value.column=='updateUserId'){
                        if(value.value!=''&&value.value!=null){
                            let temp={};
                            temp.column=value.column;
                            temp.name=value.name;
                            temp.operation="=";
                            temp.value=value.value;
                            tempClauses.push(temp);
                        }else {
                            tempClauses.push(value)
                        }
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
    body .el-table th.gutter{
        display: table-cell!important;
    }

</style>
