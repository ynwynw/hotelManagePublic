<template>

    <div class="bg">
        <img src="../assets/images/bg.jpg" alt="">
        <div class="login-center">
            <div class="login-l">
                <img src="../assets/images/login-img.png" alt="">
            </div>

            <div class="login-r" v-if="!qrShow">
                <h3>防疫酒店管控系统</h3>
                <p>Welcome欢迎登录</p>

                <div class="inp">
                    <p style="margin: 0;">账号：</p>
                    <input v-model="username" type="text" placeholder="请输入用户名">
                </div>
                <div class="inp">
                    <p style="margin: 0;">密码：</p>
                    <input @keyup.enter="login" v-model="password" type="password" placeholder="请输入密码">
                </div>

                <button @click="login">登录</button>
                <button @click="QRCode(true)" style="margin-top: 20px">二维码登录</button>

            </div>
            <div class="login-r" v-if="qrShow">
                <h3>酒店管理系统</h3>
                <p>Welcome欢迎登录</p>
                <div class="qr">
                    <p style="font-size: 16px;color: #666666" v-if="confirmShow"><i style="color: #67C23A" class="el-icon-success"></i>&nbsp已扫描成功,请在手机上按确认登录</p>
                    <vue-qr :logo-src="logoSrc"
                            :size="180"
                            :margin="0"
                            :auto-color="true"
                            :dot-scale="1"
                            :text="code"
                            v-if="!confirmShow"
                    />
                </div>
                <button @click="QRCode(false)" style="margin-top: 6px">账号密码登录</button>

            </div>
        </div>
    </div>

</template>

<script>
    import {get, post} from "../utils/request";
    import VueQr from 'vue-qr';

    export default {
        name: "login",
        components: {
            VueQr,
        },

        data() {
            return {
                username: 'admin',
                password: '123456',
                logoSrc: '',
                code: '25760875-7b97-4594-996f-fd2a041257dc',
                qrShow: false,
                confirmShow: false,
                interval: undefined
            }
        },
        methods: {
            // QRCode(){},
            QRCode(flag) {
                this.qrShow = !this.qrShow;
                this.confirmShow = false
                let that = this
                if (flag) {
                    get('/open/auth/qrCode/1?code=' + null).then(res => {
                        console.log("qrcode",res)
                        this.code = res.data.data
                    })

                    this.interval = setInterval(this.getQrCodeStatus, 3000)
                } else {
                    console.log("clearInterval", this.interval)
                    clearInterval(this.interval);
                    console.log("before", this.interval)

                }
            },

            getQrCodeStatus() {
                get('/open/auth/qrCode/2?code=' + this.code).then(res => {
                    console.log("res.data.status====",res.data.data)
                    switch (res.data.data.status) {
                        case "0":
                            break;
                        case "1":
                            this.confirmShow = true
                            break;
                        case "2":
                            clearInterval(this.interval);
                            console.log('qrCode登录信息:', res);
                            //判断返回的数据是否存在token和用户id
                            if (res.data.data.token && res.data.data.userInfo.id) {
                                // 将token及用户id存放到Vuex中
                                this.$store.commit('SAVE_TOKEN', res.data.data.token)
                                this.$store.commit('SAVE_USER_ID', res.data.data.userInfo.id)
                                //将导航菜单存到localStorage
                                localStorage.setItem('menuList', JSON.stringify(res.data.data.menuList))
                                //路由跳转
                                this.$router.push({
                                    path: '/index'
                                })

                            } else {
                                console.log('token或用户id不存在，登录失败');

                            }
                            break
                    }
                })
            },
            //登录
            login() {
                let userData = {
                    username: this.username,
                    password: this.password,
                }
                post('/auth/login', userData)
                    .then(res => {
                        console.log('登录信息:', res);
                        //判断返回的数据是否存在token和用户id
                        if (res.data.data.token && res.data.data.userInfo.id) {
                            // 将token及用户id存放到Vuex中
                            this.$store.commit('SAVE_TOKEN', res.data.data.token)
                            this.$store.commit('SAVE_USER_ID', res.data.data.userInfo.id)
                            //将导航菜单存到localStorage
                            localStorage.setItem('menuList', JSON.stringify(res.data.data.menuList))
                            //路由跳转
                            this.$router.push({
                                path: '/index'
                            })

                        } else {
                            console.log('token或用户id不存在，登录失败');

                        }
                    })
                    .catch(err => {
                        console.log(err)
                      this.$notify.error({
                        title: '错误',
                        message: "登录异常,请重试"
                      });
                    })
            }

        },
        mounted() {
            //页面挂载完毕清除token和用户id
            localStorage.removeItem('Token')
            localStorage.removeItem('userId')
            localStorage.removeItem('menuId')
        }
    }
</script>

<style scoped>
    * {
        padding: 0;
        margin: 0;
    }

    .bg {
        width: 100vw;
        height: 100vh;
        display: flex;
        align-items: center;
        justify-content: center;
    }

    .bg img {
        width: 100%;
        height: 100%;
        opacity: 0.6;
        position: absolute;
    }

    .login-center {
        width: 700px;
        height: 300px;
        background-color: #fff;
        box-shadow: 0px 0px 40px -10px #000;
        border-radius: 20px;
        padding: 60px 50px;
        display: flex;
        z-index: 9;
        justify-content: space-between;
    }

    .login-l img {
        width: 300px;
        height: 300px;
        opacity: 1;
    }

    .login-l, .login-r {
        width: 100%;
    }

    .login-r h3 {
        font-size: 25px;
        font-weight: normal;
        margin-top: 10px;
    }

    .login-r p {
        margin-top: 10px;
        margin-bottom: 20px;
        color: #ccc;
    }

    .inp {
        display: flex;
        align-items: center;
        height: 50px;
        border-bottom: 1px solid #ccc;
    }

    .inp input {
        width: 300px;
        height: 30px;
        outline: none;
        border: none;
        color: #ccc;
        font-size: 16px;
    }

    .login-r button {
        width: 300px;
        height: 35px;
        background-color: #4f93fe;
        color: #fff;
        border-radius: 50px;
        border: none;
        margin-top: 50px;
        cursor: pointer;
    }

    .qr {
        width: 100%;
        height: 200px;

    }

    .qr img {
        width: 180px;
        height: 180px;
        margin: 0 auto;
        opacity: 0.7;

    }
</style>