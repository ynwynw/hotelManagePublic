<template>
    <div>
        <el-container style="height: 100vh">

            <el-aside width="235px">
                <div
                        style="display: flex;justify-content: center;align-items: center;height: 80px;border-bottom: 1px solid gray">
                    <p style="font-size: 24px;color: gray">防疫酒店管控系统</p>
                </div>
                <el-menu
                        :default-active=active
                        @open="handleOpen"
                        @close="handleClose"
                        background-color="#e1e3e5"
                        text-color="#4A5A7F"
                        active-text-color="#ffffff">

                     <TreeMenu v-for="item in menuList" :menu="item"/>

                </el-menu>

            </el-aside>

            <el-container>
                <el-header height="80px">
                    <div class="user-info">
                        <img class="user-img mr-10" :src="avatarUrl" alt="">
                        <el-dropdown>
                            <span class="el-dropdown-link">
                                <p> {{ name }}</p>
                                <i class="el-icon-arrow-down el-icon--right"></i>
                            </span>
                            <el-dropdown-menu slot="dropdown">
                                <el-dropdown-item @click.native="openEdit">修改信息</el-dropdown-item>
                                <el-dropdown-item @click.native="logout">退出登录</el-dropdown-item>
                            </el-dropdown-menu>
                        </el-dropdown>
                    </div>
                </el-header>
                <el-main>
                    <router-view></router-view>
                </el-main>
            </el-container>
        </el-container>

        <!-- 修改信息 弹出框 -->
        <el-dialog
                title="修改个人信息"
                :visible.sync="dialogVisible"
                width="570px">
            <div class="contont">

                <div class="d-flex align-items-center mb-15">
                    <p class="w-100">修改头像</p>
                    <img class="user-img" :src="avatarUrl" alt="">
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
            <el-button @click="dialogVisible = false">取 消</el-button>
            <el-button type="primary" @click="changeUserInfo">确 定</el-button>
        </span>
        </el-dialog>

    </div>

</template>

<script>
    import {get, post} from "../utils/request";

    import MainContent from '../components/MainContent'
    import DepartmentManage from '../components/DepartmentManage'
    import HotelManage from '../components/HotelManage'
    import MyHotel from "../components/MyHotel";
    import RoomType from "../components/RoomType";
    import BedType from "../components/BedType";
    import DiscountManage from "../components/DiscountManage";
    import OrderManage from "../components/OrderManage";
    import TreeMenu from "../components/TreeMenu";
    import RoomManage from "../components/RoomManage";

    export default {
        name: "index",
        components: {
            MainContent,
            DepartmentManage,
            HotelManage,
            MyHotel,
            RoomType,
            BedType,
            DiscountManage,
            OrderManage,
            TreeMenu,
            RoomManage
        },
        data() {
            return {
                dialogVisible: false,
                name: '',
                nickName: '',
                userTel: '',
                userPassword: '',
                userName: '',
                userId: '',
                deptId: '',
                requestImg: '',
                avatarUrl: '',
                userInfo: {},
                menuList: [],
                active: ''

            }
        },
        methods: {
            handleOpen(key, keyPath) {
                console.log(key, keyPath);
            },
            handleClose(key, keyPath) {
                console.log(key, keyPath);
            },

            //根据id获取用户信息
            getUserInfo() {
                this.userId = localStorage.getItem('userId')
                get('/api/user/get/' + this.userId)
                    .then(res => {
                        console.log("用户的数据：", res);
                        this.userName = res.data.data.username
                        this.name = res.data.data.nickname
                        this.deptId = res.data.data.dept
                        // this.avatarUrl = 'http://' + res.data.data.heads
                        this.avatarUrl = `http://${res.data.data.heads}`
                        this.requestImg = res.data.data.heads
                        this.userInfo = res.data.data
                        this.userInfo.password = ''
                    })
                    .catch(err => {
                        console.log(err);
                    })
            },

            //路由跳转方法
            toOtherComponent(val, data) {
                this.$router.push({
                    name: val,
                    query: {
                        data: data
                    }
                })
            },

            // 获取菜单列表
            getMenuList() {

                this.menuList = JSON.parse(localStorage.getItem('menuList'))

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
                        this.requestImg = res.data.data
                    })
                    .catch(err => {
                        console.log(err);
                    })
            },
            //改变用户信息
            changeUserInfo() {
                this.userInfo.heads = this.requestImg
                let userData = this.userInfo
                post('/api/user/modify', userData)
                    .then(res => {                    
                        console.log("修改信息：", res);
                        if (res.data.code == 200) {                                                    
                            this.$message({
                                message: '修改成功！',
                                type: 'success'
                            });
                            this.dialogVisible = false
                            this.getUserInfo()
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

          openEdit() {
            this.getUserInfo()
            this.dialogVisible = true

          },

            //退出登录
            logout() {
                this.$router.push({
                    path: '/'
                })

            }

        },
        mounted() {
            this.getUserInfo()
            this.getMenuList()
            this.active = localStorage.getItem('menuId')?localStorage.getItem('menuId'):"1"
        }
    }
</script>

<style scoped>
    /* 自定样式 start */
    .user-info {
        display: flex;
        align-items: center;
    }

    .user-img {
        width: 46px;
        height: 46px;
        border-radius: 50%;
    }

    /* 自定样式 end */

    /* 自定样式 ling start */

    /* 自定样式 ling end */

    .el-row {
        margin-bottom: 0;
    }

    .el-header {
        display: flex;
        align-items: center;
    }

    .el-main {
        background-color: #F1F7FC;
        color: #333;
        text-align: center;
    }

    body > .el-container {
        margin-bottom: 40px;
    }

    .el-container:nth-child(5) .el-aside,
    .el-container:nth-child(6) .el-aside {
        line-height: 260px;
    }

    .el-container:nth-child(7) .el-aside {
        line-height: 320px;
    }

    .el-dropdown-link {
        display: flex;
        align-items: center;
        cursor: pointer;
        color: #409EFF;
    }

    .el-icon-arrow-down {
        font-size: 12px;
    }

    .el-aside {
        height: 100vh;
        padding: 0px 15px;
        /*background: #F0F2F3;*/
        background: #e1e3e5;
        /*width: 340px;*/
    }

    .el-header {
        display: flex;
        justify-content: flex-end;
        align-items: center;
        width: 100%;
        background: #FFFFFF;
        box-shadow: 5px 5px 8px 0 rgba(0, 0, 0, 0.15);
        z-index: 9;
        padding: 0px 25px;
    }

    .el-menu {
        height: calc(100% - 110px);
        margin-top: 20px;
        border: none;
    }

    .el-menu .el-menu-item {
        /*background-color: #F0F2F3!important;*/
        /*background-color: #e1e3e5 !important;*/
        height: 45px;
        line-height: 45px;
        margin: 18px 0px;
    }

    /*.el-menu-item:hover {*/
    /*    background: none !important;*/
    /*    outline: none !important;*/
    /*}*/

    .el-menu .is-active {
        background: linear-gradient(90deg, #428DFF, #397DFF) !important;
        border-radius: 6px;
    }

    .el-main {
        background-color: #F4F8FC;
    }

    .el-avatar > img {
        width: 100%;
    }

    .avatar-uploader {
        margin-left: 35px;
    }

</style>