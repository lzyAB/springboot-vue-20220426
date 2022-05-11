<template>
  <div>
    <el-page-header @back="goBack" content="员工管理">
    </el-page-header>
    <el-divider></el-divider>

    <div>
      <el-form :inline="true" :model="searchForm">
        <el-form-item label="员工姓名">
          <el-input v-model="searchForm.ename" placeholder="请输入员工姓名"></el-input>
        </el-form-item>
        <el-form-item label="部门名称">
          <el-select v-model="searchForm.deptno" filterable placeholder="请选择">
            <el-option
                v-for="item in depts"
                :key="item.id"
                :label="item.dname"
                :value="item.id">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" icon="el-icon-edit" @click="editdialogFormVisible = true">新增</el-button>
          <el-button type="primary" icon="el-icon-search" @click="search(searchForm)">搜索</el-button>
        </el-form-item>
      </el-form>
    </div>






    <el-table
        v-loading="loading"
        :data="tableData">
      <el-table-column prop="id" label="ID" width="50">
      </el-table-column>
      <el-table-column prop="ename" label="姓名" width="80">
      </el-table-column>
      <el-table-column prop="phone" label="手机号" width="120">
      </el-table-column>
      <el-table-column prop="sal" label="工资" width="100">
      </el-table-column>
      <el-table-column prop="job" label="岗位" width="120">
      </el-table-column>
      <el-table-column prop="dept.dname" label="部门名称" width="120">
      </el-table-column>
      <el-table-column prop="birdate" label="生日" width="140">
      </el-table-column>
      <el-table-column prop="carno" label="身份证">
      </el-table-column>




      <el-table-column label="操作">
        <template slot-scope="scope">
          <el-button
              size="mini"
              @click="handleEdit(scope)"
              >编辑</el-button>
          <el-button
              size="mini"
              type="danger"
              @click="open_delete(scope.row.id)"
              >删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-pagination
        background
        align="center"
        style="margin: 8px"
        layout="prev, pager, next"
        :total="this.page.total"
        :page-size="this.page.pagesize"
        @prev-click="handlePage"
        @next-click="handlePage"
        @current-change="handlePage"
    >
    </el-pagination>



    <el-dialog :title="dialog_name"
               width="30%"
               :close-on-click-modal="false"
               :visible.sync="editdialogFormVisible">
      <el-form :model="updataForm" :rules="updataRules" ref="updataForm">
        <el-input v-model="updataForm.id"
                  placeholder="id"
                  autocomplete="off" type="hidden"></el-input>
        <el-form-item label="员工姓名" prop="ename">
          <el-input v-model="updataForm.ename"
                    placeholder="请输入员工姓名"
                    autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="员工手机号" prop="phone">
          <el-input v-model="updataForm.phone"
                    placeholder="请输入员工手机号"
                    autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="员工职位" prop="job">
          <el-input v-model="updataForm.job"
                    placeholder="请输入员工职位"
                    autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="员工生日" prop="birdate">
          <el-date-picker
              v-model="updataForm.birdate"
              type="date"
              placeholder="选择日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="员工工资" prop="sal">
          <el-input v-model="updataForm.sal"
                    placeholder="请输入员工生日"
                    autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="员工身份证" prop="carno">
          <el-input v-model="updataForm.carno"
                    placeholder="请输入员工身份证"
                    autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="员工部门" prop="deptno">
          <el-select v-model="updataForm.deptno" filterable placeholder="请选择">
            <el-option
                v-for="item in depts"
                :key="item.id"
                :label="item.dname"
                :value="item.id">
            </el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="editdialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="changeInfo(updataForm)">确 定</el-button>
      </div>
    </el-dialog>





  </div>
</template>

<script>
export default {
  inject: ["reload"],
  name: "index",
  data(){
    return {
      dialog_name:'',
      depts:[],
      tableData: [],
      loading: true,
      editdialogFormVisible:false,
      updataForm:{
        ename:'',
        phone:'',
        job:'',
        birdate:'',
        sal:null,
        carno:'',
        deptno:null
      },
      insertForm:{
        ename:'',
        phone:'',
        job:'',
        birdate:'',
        sal:null,
        carno:'',
        deptno:null
      },
      updataRules:{
        ename:[{ required: true, message: '请输入员工姓名', trigger: 'blur' }],
        phone:[{ required: true, message: '请输入员工手机号', trigger: 'blur' }],
        job:[{ required: true, message: '请输入部门职位', trigger: 'blur' }],
        sal:[{ required: true, message: '请输入部门工资', trigger: 'blur' }],
        carno:[{ required: true, message: '请输入员工身份证', trigger: 'blur' }],
      },
      page:{
        pagesize:0,
        total:0,
      },
      searchForm:{
        ename:null,
        deptno:null
      }
    }
  },
  methods:{
    search(rdata){
      const that = this
      this.request.post('/emp/page',{
          ename:rdata.ename,
          deptno:rdata.deptno
      }).then(response =>{
        this.loading = false
        if(response.status == 0){
          this.tableData = response.list
          this.page.total = response.total
          this.page.pagesize = response.pageSize
        }else if(response.status == 401){
          that.$alert('登录过期,请重新登录', 'error', {
            confirmButtonText: '确定',
            callback: action => {
              that.$router.push({name:'login'})
            }
          });
          return
        }
      })
    },
    changeInfo(rdata){
      const that = this
      if(rdata.id != null || rdata.id > 0){
        this.request.post('/emp/changeInfo',{
            ename:rdata.ename,
            phone:rdata.phone,
            job:rdata.job,
            birdate:rdata.birdate,
            sal:rdata.sal,
            carno:rdata.carno,
            deptno:rdata.deptno,
            id:rdata.id
        }).then(response => {
          if(response.status == 0){
            this.$message({
              message: response.message,
              type: 'success'
            });
            this.reload()
          }else if(response.status == 401){
            that.$alert('登录过期,请重新登录', 'error', {
              confirmButtonText: '确定',
              callback: action => {
                that.$router.push({name:'login'})
              }
            });
            return
          }else {
            this.$message.error(response.message);
          }
        })
      }else{
        this.request.post('/emp/addInfo',{
            ename:rdata.ename,
            phone:rdata.phone,
            job:rdata.job,
            birdate:rdata.birdate,
            sal:rdata.sal,
            carno:rdata.carno,
            deptno:rdata.deptno
        }).then(response =>{
          if(response.status == 0){
            this.$message({
              message: response.message,
              type: 'success'
            });
            this.reload()
          }else if(response.status == 401){
            that.$alert('登录过期,请重新登录', 'error', {
              confirmButtonText: '确定',
              callback: action => {
                that.$router.push({name:'login'})
              }
            });
            return
          }else {
            this.$message.error(response.message);
          }
        })
      }
      this.editdialogFormVisible = false
    },
    handlePage(pages){
      const that = this
      this.request.post('/emp/page',{
          pageNum:pages,
          ename:this.search.ename,
          deptno:this.search.deptno
      }).then(response =>{
        this.loading = false
        if(response.status == 0){
          this.tableData = response.list
          this.page.total = response.total
          this.page.pagesize = response.pageSize
        }else if(response.status == 401){
          that.$alert('登录过期,请重新登录', 'error', {
            confirmButtonText: '确定',
            callback: action => {
              that.$router.push({name:'login'})
            }
          });
          return
        }
      })

    },
    handleEdit(item){
      this.dialog_name = "编辑员工信息"
      const that = this
      this.request.post('/dept/queryall',{

      }).then(response =>{
        if(response.status == 0){
          that.depts = response.data
        }else if(response.status == 401){
          that.$alert('登录过期,请重新登录', 'error', {
            confirmButtonText: '确定',
            callback: action => {
              that.$router.push({name:'login'})
            }
          });
          return
        }
      })
      this.editdialogFormVisible = true
      this.updataForm.id = item.row.id
      this.updataForm.ename = item.row.ename
      this.updataForm.phone = item.row.phone
      this.updataForm.job = item.row.job
      this.updataForm.birdate = item.row.birdate
      this.updataForm.sal = item.row.sal
      this.updataForm.carno = item.row.carno
      this.request.post('/emp/queryid',{
          id:item.row.id
      }).then(response =>{
        if(response.status == 0){
          this.updataForm.deptno = response.data.deptno
        }else if(response.status == 401){
          that.$alert('登录过期,请重新登录', 'error', {
            confirmButtonText: '确定',
            callback: action => {
              that.$router.push({name:'login'})
            }
          });
          return
        }
      })
    },
    queryed(){
      this.tableData =[]
      const that = this
      //象征性的延迟,以后方便优化速度二次收费
      setTimeout(function () {
        that.request.post('/emp/page',{

        }).then(response =>{
          that.loading = false
          if(response.status == 0) {
            that.tableData = response.list
            that.page.total = response.total
            that.page.pagesize = response.pageSize
          }else if(response.status == 401){
            that.$alert('登录过期,请重新登录', 'error', {
              confirmButtonText: '确定',
              callback: action => {
                that.$router.push({name:'login'})
              }
            });
            return
          }
        })
      },2000)
    },
    open_delete(id){
      const that = this;
      this.$confirm('该操作将会删除这个员工, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        //确认删除
        that.request.post('/emp/delete',{
            'id':id,
        }).then(response =>{
          if(response.status == 0){
            this.$message({
              message: response.message,
              type: 'success'
            });
            this.reload()
          }else if(response.status == 401){
            that.$alert('登录过期,请重新登录', 'error', {
              confirmButtonText: '确定',
              callback: action => {
                that.$router.push({name:'login'})
              }
            });
            return
          }else{
            this.$message.error(response.message);
          }
        })
      }).catch(() => {
        //取消
        this.$message({
          type: 'info',
          message: '已取消删除'
        });
      });
    },
    goBack() {
      this.$router.go(-1)
    }
  },
  mounted() {
    const that = this
    this.queryed()
    this.request.post('/dept/queryall',{

    }).then(response =>{
      if(response.status == 0){
        this.depts = response.data
      }else if(response.status == 401){
        that.$alert('登录过期,请重新登录', 'error', {
          confirmButtonText: '确定',
          callback: action => {
            that.$router.push({name:'login'})
          }
        });
        return
      }
    })
  }
}
</script>

<style>

</style>