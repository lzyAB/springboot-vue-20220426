<template>
<div style="height: 100%">
  <el-container style="height: 100%; border: 1px solid #eee">
    <el-aside style="width: auto!important;height: 100%">
        <component-aside>
        </component-aside>
    </el-aside>

    <el-container>
      <el-header style="text-align: left;">
        <component-header></component-header>
      </el-header>

      <el-main style="height: 100%;width: 100%">
        <el-scrollbar class="page-scroll">
          <router-view >
          </router-view>
        </el-scrollbar>
      </el-main>
    </el-container>
  </el-container>
</div>
</template>

<script>
import ComponentAside from "../components/componentAside";
import ComponentHeader from "../components/componentHeader";
import store from "../store";



export default {
  name: "Home",
  components: {ComponentHeader, ComponentAside},
  data() {
    return{

    }
  },
  mounted() {
    const that = this
    this.request.post( '/user/userinfo', {

        }).then(response => {
          if(response.status == 0){
            store.commit('setUserInfo',response.data)
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
};
</script>

<style lang="less">
.el-header {
  color: #333;
  height: 100%;
  box-shadow: 0 2px 4px rgba(0, 0, 0, .12), 0 0 6px rgba(0, 0, 0, .04)
}

.el-aside {
  overflow-x: hidden;
  overflow-y: hidden;
}

.page-scroll {
  height: 100%;
}

.page-scroll .el-scrollbar__wrap {
  overflow-x: hidden;
}
</style>