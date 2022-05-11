<template>
<div class="header">
  <div class="l-content">
    <el-dropdown>
      <el-button plain icon="el-icon-s-operation" @click="handleMenu" size="mini"></el-button>
    </el-dropdown>
  </div>


  <div class="r-content">
    <el-dropdown size="mini" trigger="click" @command="handleCommand">
        <span class="el-dropdown-link">
          <img :src="getUserInfo.headerimg"/>
        </span>
      <el-dropdown-menu slot="dropdown">
        <el-dropdown-item command="goUserInfo">个人中心</el-dropdown-item>
        <el-dropdown-item command="logout">退出</el-dropdown-item>
      </el-dropdown-menu>
    </el-dropdown>
  </div>
</div>
</template>

<script>
export default {
  name: "componentHeader",
  methods:{
    handleMenu(){
      this.$store.commit("menuCollapse")
    },
    handleCommand(command){
      this.$store.commit("clearToken")
      if(command == 'logout'){
        this.$message({
          message: '退出成功',
          type: 'success'
        });
        this.$router.push({name:'login'})
      }else if(command == 'goUserInfo'){
        this.$message.error('待添加');
      }
    }

  },
  computed:{
    getUserInfo(){
      return this.$store.state.user.userInfo
    }
  }
}
</script>

<style lang="less" scoped>
.header {
  display: flex;
  height: 100%;
  justify-content: space-between;
  align-items: center;
}
.l-content {
  display: flex;
  align-items: center;
  .logo {
    margin-right: 20px;
  }
}
.r-content {
  img {
    width: 40px;
    height: 40px;
    border-radius: 50%;
  }
}
</style>