# 员工信息管理系统

基于SpringBoot+Vue前后端分离的项目，适合初学者学习。

## 介绍

这是我在大二期间学习Vue后第一次尝试使用Vue+SpringBoot来开发前后端分离架构项目的demo

- 前端采用Vue、Element UI
- 后端采用SpringBoot、Mybatis、Redis、Jwt ····

## 功能

1. 部门管理：添加、删除、修改、查询、搜索
2. 员工管理：添加、删除、修改、查询、搜索
3. 权限认证：Jwt

## 开发环境

- Java 1.8.0_281 (JDK8)
- Apache Maven 3.5.0
- NodeJs 14.16.0
- MySQL 5.7
- 数据库脚本：` springboot/src/main/resources/db/20220426.sql`

## 安装和运行

### 前端

```
cd vue

# 下载依赖
yarn install
npm install

# 启动
yarn serve
npm run serve

# 编译项目
yarn build
npm run build
```

### 后端

```
cd springboot

mvn install

mvn package

# 启动项目
mvn spring-boot:run
```

后端默认端口8089，默认管理员账号：admin，密码：123456

## 系统效果

#### 登录（该页面是在哔哩上copy别人的）

![OdgHbD.md.png](https://s1.ax1x.com/2022/05/11/OdgHbD.png)

#### 首页

![OdgjPA.md.png](https://s1.ax1x.com/2022/05/11/OdgjPA.png)

#### 员工信息

![Od22sf.md.png](https://s1.ax1x.com/2022/05/11/Od22sf.png)

#### 搜索

![Od2RL8.md.png](https://s1.ax1x.com/2022/05/11/Od2RL8.png)

#### 部门信息

![Od2feS.md.png](https://s1.ax1x.com/2022/05/11/Od2feS.png)

#### 新增

![Od2gQP.md.png](https://s1.ax1x.com/2022/05/11/Od2gQP.png)



## 其他说明

博客：[空城里](https://www.ilzya.com/)

皮皮虾：lzyAB
