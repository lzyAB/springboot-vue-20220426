<template>
<div style="height: 100%;">



  <el-menu
      default-active="1-4-1"
      class="el-menu-vertical-demo"
      @open="handleOpen"
      @close="handleClose"
      :collapse="isCollapse"
      style="height: 100%"
  >

    <div style="width: 100%;text-align: center;">
      <el-image
          style="width: 50px; height: 50px;border-radius: 50%;top: 5px;"
          :src="imgurl"
          :fit="fit"></el-image>
      <div style="line-height: 50px;width: calc(100% - 1px);">
        <strong>{{ isCollapse ? '':'员工信息管理系统'}}</strong>
      </div>
    </div>


    <el-menu-item
        v-for="item in noChildren"
        :index="item.path"
        :key="item.path"
        @click="goPage(item)"
    >
      <i :class="item.icon"></i>
      <span slot="title">{{ item.label }}</span>
    </el-menu-item>

    <el-submenu
        v-for="item in hasChildren"
        :index="item.path"
        :key="item.path"
    >
      <template slot="title">
        <i :class="item.icon"></i>
        <span slot="title">{{ item.label }}</span>
      </template>
      <el-menu-item-group>
        <el-menu-item
            v-for="(subItem, subIndex) in item.children"
            :index="subIndex"
            :key="subItem.path"
            @click="goPage(subItem)"
        >
          <i :class="subItem.icon" style="font-size: 15px"></i>
          {{ subItem.label }}
        </el-menu-item>
      </el-menu-item-group>
    </el-submenu>
  </el-menu>
</div>
</template>

<script>
export default {
  name: "componentAside",
  data() {
    return {
      imgurl:'https://www.ilzya.com/usr/uploads/2022/04/2543040636.jpg',
      menu: [
        {
          path: "/index",
          name: "index",
          label: "首页",
          icon: "el-icon-s-home",
          url: "/",
        },
        {
          label: "系统管理",
          icon: "el-icon-setting",
          children:[
            {
              path: "/emp",
              name: "emp",
              label: "员工管理",
              icon: "el-icon-user-solid",
              url: "/emp",
            },
            {
              path: "/dept",
              name: "dept",
              label: "部门管理",
              icon: "el-icon-office-building",
              url: "/dept",
            }
          ]
        }
      ],
    };
  },
  methods: {
    handleOpen(key, keyPath) {
      console.log(key, keyPath);
    },
    handleClose(key, keyPath) {
      console.log(key, keyPath);
    },
    goPage(item){
      console.log(item)
      this.$router.push({
        name:item.name
      });
    }
  },

  computed: {
    noChildren() {
      return this.menu.filter((item) => !item.children);
    },
    hasChildren() {
      return this.menu.filter((item) => item.children);
    },
    isCollapse(){
      return this.$store.state.tab.isCollapse
    }
  },
}
</script>
<style>
.el-menu-vertical-demo:not(.el-menu--collapse) {
  width: 200px;
  min-height: 100%;
}

</style>