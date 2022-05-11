<template>
  <div>
    <el-page-header @back="goBack" content="部门管理">
    </el-page-header>
    <el-divider></el-divider>
    <div>
      <el-form :inline="true" :model="searchForm">
        <el-form-item label="部门名称">
          <el-input v-model="searchForm.dname" placeholder="请输入部门名称"></el-input>
        </el-form-item>
        <el-form-item label="部门地址">
          <el-input v-model="searchForm.address" placeholder="请输入部门地址"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" icon="el-icon-edit" @click="addInfo(searchForm)">新增</el-button>
          <el-button type="primary" icon="el-icon-search" @click="search(searchForm)">搜索</el-button>
        </el-form-item>
      </el-form>
    </div>
    <el-table
        v-loading="loading"
        :data="tableData">
      <el-table-column prop="id" label="ID" width="50">
      </el-table-column>
      <el-table-column prop="dname" label="部门名称" width="200">
      </el-table-column>
      <el-table-column prop="address" label="部门地址" width="280">
      </el-table-column>
      <el-table-column prop="status" label="状态" width="100">
        <template slot-scope="scope">
<!--          <span v-if="scope.row.status===1">在编</span>-->
<!--          <span v-else-if="scope.row.status===0">不在编</span>-->
          <el-switch
              v-model="scope.row.status"
              active-color="#13ce66"
              inactive-color="#ff4949"
              :active-value='1'
              :inactive-value='0'
              @change="changeStatus(scope.row.id)"
          >
          </el-switch>
        </template>


      </el-table-column>


      <el-table-column label="操作">
        <template slot-scope="scope">
          <el-button
              size="mini"
              @click="handleAdd(scope)"
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



    <el-dialog title="编辑部门信息"
               width="30%"
               :close-on-click-modal="false"
               :visible.sync="dialogFormVisible">
      <el-form :model="updataForm" :rules="updataRules" ref="updataForm">
          <el-input v-model="updataForm.id"
                    placeholder="id"
                    autocomplete="off" type="hidden"></el-input>
        <el-form-item label="部门名称" prop="dname">
          <el-input v-model="updataForm.dname"
                    placeholder="请输入部门名称"
                    autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="部门地址" prop="address">
          <el-input v-model="updataForm.address"
                    placeholder="请输入部门地址"
                    autocomplete="off"></el-input>
        </el-form-item>

      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
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
      tableData:[],
      loading: true,
      formLabelWidth: '120px',
      dialogFormVisible: false,
      updataForm: {
        dname: '',
        address: '',
        id:'',
        date1: '',
        date2: '',
        delivery: false,
        type: [],
        resource: '',
        desc: ''
      },
      updataRules:{
        dname:[
            { required: true, message: '请输入部门名称', trigger: 'blur' },
        ],
        address:[
          { required: true, message: '请输入部门地址', trigger: 'blur' },
        ]
      },
      searchForm:{
        dname:null,
        address:null
      },
      page:{
        pagesize:0,
        total:0,
      }
    }
  },
  methods:{
    search(rdata){
      const that = this
      this.request.post('/dept/page',{
          dname:rdata.dname,
          address:rdata.address
      }).then(response =>{
        this.loading = false
        if(response.status == 0) {
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
    handlePage(pages){
      const that = this
      this.request.post('/dept/page',{
          pageNum:pages,
          dname:this.search.dname,
          address:this.search.address
      }).then(response =>{
        this.loading = false
        if(response.status == 0) {
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


    addInfo(rdata){
      const that = this
      this.request.post('/dept/addInfo',{
          dname:rdata.dname,
          address:rdata.address
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
        }else{
          this.$message.error(response.message);
        }
      })
    },
    handleAdd(item){
      this.dialogFormVisible = true
      this.updataForm.id = item.row.id
      this.updataForm.dname = item.row.dname
      this.updataForm.address = item.row.address
    },
    queryed(){
      this.tableData =[]
      const that = this
      //象征性的延迟,以后方便优化速度二次收费
      setTimeout(function () {
        that.request.post('/dept/page',{

        }).then(response =>{
          that.loading = false
          if(response.status == 0) {
            that.tableData = response.list
            that.page.total = response.total
            that.page.pagesize = response.pageSiz
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
      this.$confirm('该操作将会删除这个部门, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        //确认删除
        that.request.post('/dept/delete',{
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
          }else {
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
    },
    changeStatus(id){
      const that = this
      this.request.post('/dept/changeStatus',{
          id:id
      }).then(response => {
        if(response.status == 0){
          this.$message({
            message: response.message,
            type: 'success'
          });
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
      this.reload()
    },
    changeInfo(data){
      //alert(data.address)
      let dname = data.dname
      let address = data.address
      let id = data.id
      const that = this
      this.request.post('/dept/changeInfo',{
          dname:dname,
          address:address,
          id:id
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
      this.dialogFormVisible = false
    }
  },
  mounted() {
    this.queryed()
  }

}
</script>

<style>

</style>