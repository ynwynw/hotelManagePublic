<template>
  <div>
    <!-- 导航栏 -->
    <div class="t-nav">
      <div class="nav-content">
        <ul>
          <li>
            <router-link to="/index">{{ $t('nav.index') }}</router-link>
          </li>
          <li>
            <router-link to="/hotelList">{{ $t('nav.list') }}</router-link>
          </li>
          <li>
            <router-link to="/collectionList">{{ $t('nav.collection') }}</router-link>
          </li>
          <li>
            <router-link to="/orderList">{{ $t('nav.order') }}</router-link>
          </li>
          <li>
            <router-link to="/recordList">{{ $t('nav.record') }}</router-link>
          </li>
          <li>
            <router-link to="/wallet">{{ $t('nav.wallet') }}</router-link>
          </li>
          <li>
            <router-link to="/login">{{ $t('nav.logOut') }}</router-link>
          </li>
          <li>
            <el-link type="primary" @click="langSwitch('zh')">中</el-link>
            <span>/</span>
            <el-link type="primary" @click="langSwitch('en')">En</el-link>
          </li>
          <li class="cursor" @click="editPersonalInfo">
            <el-avatar size="large" :src="avatar" fit="cover"></el-avatar>
          </li>
        </ul>
      </div>
      <!-- <i @click="drawer = true" class="el-icon-menu menu-icon"></i> -->
      <div class="menu-icon" @click="drawer = true">
        <img :src="require(`../assets/images/menu2.png`)" alt="">
      </div>
      <p class="app-menu-title font-26">{{ $t('nav.menu') }}</p>
      <el-drawer
          :visible.sync="drawer"
          direction="ltr"
          :size="drawerWidth"
          :with-header="false"
          :modal-append-to-body="false">
        <ul class="drawer-list">
          <li>
            <router-link to="/index">{{ $t('nav.index') }}</router-link>
          </li>
          <li>
            <router-link to="/hotelList">{{ $t('nav.list') }}</router-link>
          </li>
          <li>
            <router-link to="/collectionList">{{ $t('nav.collection') }}</router-link>
          </li>
          <li>
            <router-link to="/orderList">{{ $t('nav.order') }}</router-link>
          </li>
          <li>
            <router-link to="/recordList">{{ $t('nav.record') }}</router-link>
          </li>
          <li>
            <router-link to="/wallet">{{ $t('nav.wallet') }}</router-link>
          </li>
          <li>
            <router-link to="/login">{{ $t('nav.logOut') }}</router-link>
          </li>
          <li class="lang-link">
            <el-link type="primary" @click="langSwitch('zh')">中</el-link>
            <span class="ml-5 mr-5">/</span>
            <el-link type="primary" @click="langSwitch('en')">En</el-link>
          </li>
          <li class="cursor">
            <el-avatar size="large" :src="avatar" fit="cover"></el-avatar>
          </li>
        </ul>
      </el-drawer>
    </div>

    <!-- 修改信息 弹出框 -->
    <el-dialog
        title="修改个人信息"
        :visible.sync="editPersonalInfoVisible"
        width="570px">
      <div class="contont">

        <div class="d-flex align-items-center mb-15">
          <p class="w-100">修改头像</p>
          <img class="user-img" :src="userInfo.heads" alt="">
          <el-upload
              ref="upload"
              action="#"
              class="avatar-uploader"
              accept="image/png,image/gif,image/jpg,image/jpeg"
              :show-file-list="false"
              :on-change="onChangeFile"
              :before-upload="beforeAvatarUpload"
              :http-request="uploadImg">
            <el-button
                type="success"
                plain
                round
                size="mini">
              更改头像
            </el-button>
          </el-upload>
        </div>

        <div class="d-flex align-items-center mb-15">
          <p class="w-100">昵称:</p>
          <el-input
              style="width: 350px;"
              placeholder="请输入昵称"
              v-model="userInfo.nickname"
              clearable>
          </el-input>
        </div>
        <div class="d-flex align-items-center mb-15">
          <p class="w-100">电话:</p>
          <el-input
              style="width: 350px;"
              placeholder="请输入电话号码"
              v-model="userInfo.phone"
              clearable>
          </el-input>
        </div>
        <div class="d-flex align-items-center mb-15">
          <p class="w-100">邮箱:</p>
          <el-input
              style="width: 350px;"
              placeholder="请输入电子邮箱"
              v-model="userInfo.email"
              clearable>
          </el-input>
        </div>
        <div class="d-flex align-items-center">
          <p class="w-100">密码:</p>
          <el-input
              style="width: 350px;"
              placeholder="请输入密码"
              type="password"
              v-model="userInfo.password"
              show-password
              clearable>
          </el-input>
        </div>

      </div>
      <span slot="footer" class="dialog-footer">
            <el-button @click="editPersonalInfoVisible = false">取 消</el-button>
            <el-button type="primary" @click="changeUserInfo">确 定</el-button>
        </span>
    </el-dialog>

  </div>
</template>

<script>
import {get, post} from "../utils/request";

export default {
  name: "TopNav",
  data() {
    return {
      drawer: false,
      drawerWidth: 100,
      avatar: '',
      editPersonalInfoVisible: false,
      userInfo: {},
      requestImg: '',
      avatarUrl: ''
    }
  },
  mounted() {
    this.avatar = localStorage.getItem('avatar')
  },
  methods: {
    langSwitch(value) {
      localStorage.setItem('lang', value)
      this.$i18n.locale = value
      this.$router.go(0)
    },
    editPersonalInfo() {
      this.getUserInfo()
      this.editPersonalInfoVisible = true
    },
    //根据id获取用户信息
    getUserInfo() {
      this.userId = localStorage.getItem('userId')
      get('/api/user/get/' + this.userId)
          .then(res => {
            console.log("用户的数据：", res);
            this.userInfo = res.data.data
            // this.userName = res.data.data.username
            // this.name = res.data.data.nickname
            // this.deptId = res.data.data.dept
            // // this.avatarUrl = 'http://' + res.data.data.heads
            // this.avatarUrl = `http://${res.data.data.heads}`
            // this.requestImg = res.data.data.heads
            // this.userInfo = res.data.data
            this.userInfo.password = ''
          })
          .catch(err => {
            console.log(err);
          })
    },
    //图片改变调用函数
    onChangeFile(file) {
      this.avatarUrl = URL.createObjectURL(file.raw);
    },
    //图片上传之前的回调函数
    beforeAvatarUpload(file) {
      const isLt2M = file.size / 1024 / 1024 < 5;
      if (!isLt2M) {
        this.$message.error('上传头像图片大小不能超过 5MB!');
      }
      return isLt2M;
    },
    //图片上传函数
    uploadImg(params) {
      console.log(params);
      const config = {
        headers: {'Content-Type': 'multipart/form-data'},
      }
      const file = params.file

      //formdata
      const fd = new FormData();//通过form数据格式来传
      fd.append("img", file); //传文件
      fd.append("key", "head");

      post("/upload/img", fd, config)
          .then(res => {
            console.log(res);
            this.requestImg = 'http://' + res.data.data
            this.userInfo.heads = this.requestImg
          })
          .catch(err => {
            console.log(err);
          })
    },
    //改变用户信息
    changeUserInfo() {
      console.log("change",this.userInfo)
      let userData = this.userInfo
      post('/api/user/modify', userData)
          .then(res => {
            console.log("修改信息：", res);
            if (res.data.code == 200) {
              this.$message({
                message: '修改成功！',
                type: 'success'
              });
              this.editPersonalInfoVisible = false
              this.getUserInfo()
              this.avatar = this.userInfo.heads
              localStorage.setItem('avatar',this.userInfo.heads)
            } else {
              this.$message({
                message: '修改失败！',
                type: 'error'
              });
            }
          })
          .catch(err => {
            console.log(err);
          })
    },
  }
}
</script>

<style scoped>
.t-nav {
  width: 100%;
  height: 55px;
  background: #fff;
  opacity: 0.9;
  z-index: 999;
  position: fixed;
  top: 0;
  left: 0;
  box-shadow: 0 0 3px;
}

.nav-content {
  display: flex;
  align-items: center;
  justify-content: flex-end;
  height: 100%;
}

.nav-content ul {
  display: flex;
  align-items: center;
  height: 100%;
}

.nav-content li {
  font-size: 1.8rem;
  margin: 0 1.8rem;
  height: 100%;
  display: flex;
  align-items: center;
}

.nav-bt {
  width: 123px;
  height: 34px;
  line-height: 34px;
  background: #28B535;
  border-radius: 16px;
  border: 0;
  color: #fff;
}

.nav-content a {
  color: #A1A09E;
}

.nav-content a:hover {
  color: #323230;
}

.nav-content span {
  margin: 2px;
  color: #A1A09E;
}

.nav-mask {
  display: none;
}

.menu-icon {
  display: none;
}

.drawer-list {
  display: none;
}

.app-menu-title {
  display: none;
}

.user-img {
  width: 46px;
  height: 46px;
  border-radius: 50%;
}

.avatar-uploader {
  margin-left: 3.5rem;
}

/* 媒体查询 Start */
@media screen and (max-width: 767.9px) {
  /* 页面测试无法显示767，实际是767.2px */
  .t-nav {
    display: flex;
    align-items: center;
    height: 45px;
    opacity: 1;
  }

  .nav-content {
    display: none;
  }

  .menu-icon {
    display: flex;
    font-size: 35px;
    margin-left: 1rem;
    margin-right: 1rem;
  }

  .menu-icon > img {
    width: 38px;
    height: 38px;
  }

  .drawer-list {
    display: flex;
    flex-direction: column;
    overflow: auto;
  }

  .drawer-list li {
    border-bottom: 1px solid #999;
  }

  .drawer-list li a {
    height: 50px;
    display: flex;
    align-items: center;
    justify-content: center;
    color: #A1A09E;
    font-size: 14px;

  }

  .lang-link {
    display: flex;
    align-items: center;
    justify-content: center;
  }

  .app-menu-title {
    display: inline-block;
  }
  .cursor {
    text-align: center;
    padding: 5px 0;
  }

  /* .nav-content ul {
      width: 100%;
      flex-direction: column;
  }
  .nav-content ul li {
      margin: 0;
  }
  .nav-mask {
      display: block;
      position: fixed;
      top: 0;
      right: 0;
      bottom: 0;
      left: 100px;
      overflow: auto;
      margin: 0;
      background-color: black;
      opacity: 0.5;
  } */

}

/* 媒体查询 End */
</style>