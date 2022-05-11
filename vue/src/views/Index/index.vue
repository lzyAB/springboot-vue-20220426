<template>
  <div>
    <el-row class="index" :gutter="24">
      <el-col :span="6">
        <el-card shadow="hover">
          <div class="user">
            <img :src="getUserInfo.headerimg" height="75px" width="75px" style="border-radius: 50%">
            <div class="user-info">
              <p class="username">用户名：{{getUserInfo.username}}</p>
              <p class="accesslevel">用户组：{{getUserInfo.accesslevel = 1? '管理员':'普通用户'}}</p>
            </div>
          </div>
          <el-divider></el-divider>
          <div class="user-info">
            <el-tooltip class="item" effect="dark" :content="getUserInfo.email" placement="bottom">
              <el-button type="info"
                         icon="el-icon-message"
                         circle size="mini"
                         @click="handleButton(1)"
              ></el-button>
            </el-tooltip>
          </div>
        </el-card>
      </el-col>
      <el-col :span="18">
        <el-card class="box-card">
          <h1>项目介绍</h1>
          <p>
            该项目是我在大二期间学习Vue做的，适合刚学习完SpringBoot、Thymeleaf的初学者学习。
          </p>
          <p>
            该demo版本做了部门及员工的信息增删改查，以及登录的token校验，前端做了简单的导航守卫。
          </p>
        </el-card>
      </el-col>
    </el-row>
    <el-row class="index" :gutter="24">
      <el-col style="margin-top: 10px" :span="6">
        <el-card shadow="hover">
          <h1>
            环境
          </h1>
          <el-divider></el-divider>
          <div>
            <el-tag
                style="margin-left: 5px;margin-top: 5px"
                v-for="item in sys"
                :key="item.label"
                :type="item.type"
                effect="dark">
              {{ item.label }}
            </el-tag>
          </div>
        </el-card>
      </el-col>


      <el-col style="margin-top: -15px" :span="18">
        <el-card shadow="hover">
          <h1>
            技术栈
          </h1>
          <el-divider></el-divider>
          <h4>前端</h4>
          <div>
            <el-tag
                style="margin-left: 5px;margin-top: 5px"
                v-for="item in vueweb"
                :key="item.label"
                :type="item.type"
                effect="dark">
              {{ item.label }}
            </el-tag>
          </div>
          <el-divider></el-divider>
          <h4>后端</h4>
          <div>
            <el-tag
                style="margin-left: 5px;margin-top: 5px"
                v-for="item in java"
                :key="item.label"
                :type="item.type"
                effect="dark">
              {{ item.label }}
            </el-tag>
          </div>
        </el-card>
      </el-col>
    </el-row>

  </div>
</template>

<script>
export default {
  name: "index",
  data (){
    return{
      activeName:'1',
      vueweb: [
        { type: 'success', label: 'Vue 2.x' },
        { type: 'success', label: 'Element UI' },
        { type: 'success', label: 'axios' },
        { type: 'success', label: 'js-cookie' },
        { type: 'success', label: 'vuex' },
        { type: 'success', label: 'vue-router' }
      ],
      java: [
        { type: '', label: 'SpringBoot 2.6.x' },
        { type: '', label: 'MyBatis 2.2.2' },
        { type: '', label: 'MySQL 5.7' },
        { type: '', label: 'Redis' },
        { type: '', label: 'JWT' },
        { type: '', label: 'FastJSON' },
        { type: '', label: 'page-helper' }
      ],
      sys :[
        { type: '', label: 'Java 1.8.0_281' },
        { type: '', label: 'Apache Maven 3.5.0' },
        { type: '', label: 'NodeJs 14.16.0' },
      ]
    }
  },
  methods:{
    handleButton(index){
      switch (index){
        case 1:
          window.open('mailto:'+this.$store.state.user.userInfo.email)
        break;
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
.user{
  display: flex;
  align-items: center;
}
.img{
  display: flex;
  align-items: center;
}
.user-info{
  margin-left: 20px;
}

</style>