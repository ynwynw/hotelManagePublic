<template>

    <div ref="bg" class="bg">
        <img src="../assets/images/bg.jpg" alt="">
        <div class="login-center">

            <div class="login-l">
                <img src="../assets/images/login-img.png" alt="">
            </div>

            <div class="login-r">
                <h3>{{  $t('login.title') }}</h3>
              <div class="flex flex-row align-items-end">
                <p class="mt-5 font-16 mr-10">{{  $t('login.text') }}</p>
                <div class="ml-10 cursor" v-if="!qrShow" @click="QRCode(true)"><i class="el-icon-full-screen mr-5" style="font-size: 2rem"></i>{{ $t('login.qrCode') }}</div>
                <div class="ml-10 cursor" v-if="qrShow" @click="QRCode(false)"><i class="el-icon-postcard mr-5" style="font-size: 2rem"></i>{{ $t('login.accountSignIn') }}</div>
              </div>
              <div class="qr mt-10" v-if="qrShow">
                <p style="font-size: 16px;color: #666666" v-if="confirmShow"><i style="color: #67C23A" class="el-icon-success"></i> {{ $t('login.scanSuccess') }}</p>
                <vue-qr :logo-src="logoSrc"
                        :size="180"
                        :margin="0"
                        :auto-color="true"
                        :dot-scale="1"
                        :text="code"
                        v-if="!confirmShow"
                />
              </div>


                <ul class="login-list d-flex align-items-center" v-if="!qrShow">
                    <li @click="skip('signIn')" :class="status==0?'active':''">{{  $t('login.login') }}</li>
                    <li @click="skip('register')" :class="status==1?'active':''">{{  $t('login.register') }}</li>
                </ul>

                <router-view  v-if="!qrShow"></router-view>
            </div>

            <div class="lang-switch">
                <!-- <el-link type="primary" @click="$i18n.locale = 'zh'">中</el-link>
                <span>/</span>
                <el-link type="primary" @click="$i18n.locale = 'en'">English</el-link> -->
                <el-link type="primary" @click="langSwitch('zh')">中</el-link>
                <span>/</span>
                <el-link type="primary" @click="langSwitch('en')">English</el-link>
                <!-- <a href="#" @click="$i18n.locale = 'zh'">中</a>
                <span>/</span>
                <a href="#" @click="$i18n.locale = 'en'">English</a> -->
            </div>

        </div>
    </div>

</template>

<script>
    import {get, post} from "../utils/request";
    import SignIn from "../components/SignIn"
    import Register from "../components/Register"
    import VueQr from 'vue-qr';

    export default {
        name: "login",
        components: {
            SignIn,
            Register,
          VueQr
        },
        data() {
            return {
              logoSrc: '',
              code: '25760875-7b97-4594-996f-fd2a041257dc',
              qrShow: false,
              confirmShow: false,
              interval: undefined,
                status: 0,
                username: 'lee',
                password: '123',
            }
        },
        mounted(){
            //页面挂载完毕清除token和用户id
            localStorage.removeItem('Token')
            localStorage.removeItem('userId')
            localStorage.removeItem('avatar')

            if (window.innerWidth < 768) {
                this.$refs.bg.style.height = `${window.innerHeight}px`
            } else {
                this.$refs.bg.style.height = `100vh`
            }

        },
        methods: {
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
                    localStorage.setItem('avatar',res.data.data.userInfo.heads)
                    // //将导航菜单存到localStorage
                    // localStorage.setItem('menuList', JSON.stringify(res.data.data.menuList))
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

            //路由
            skip(val){
                if (val == 'signIn') {
                    this.status = 0
                } else if (val == 'register') {
                    this.status = 1
                }
                this.$router.push({
                    name: val,
                })
            },
            langSwitch(value){
                localStorage.setItem('lang',value)
                this.$i18n.locale = value
                this.$router.go(0)

            }

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
        /* width: 700px;
        height: 300px; */
        width: 70rem;
        height: 30rem;
        position: relative;
        background-color: #fff;
        box-shadow: 0px 0px 40px -10px #000;
        /* border-radius: 20px;
        padding: 60px 50px; */
        border-radius: 2rem;
        padding: 6rem 5rem;        
        display: flex;
        z-index: 9;
        justify-content: space-between;
    }

    .login-l img {
        /* width: 300px;
        height: 300px; */
        width: 30rem;
        height: 30rem;        
        opacity: 1;
    }

    .login-l, .login-r {
        width: 100%;
    }
    .login-r {
        /* min-width: 370px; */
        min-width: 3.7rem;
    }

    .login-r h3 {
        /* font-size: 25px; */
        font-size: 2.5rem;
        font-weight: normal;
        /* margin-top: 10px; */
        margin-top: 1rem;
    }

    .login-r p {
        color: #ccc;
    }

    .inp {
        display: flex;
        align-items: center;
        /* height: 50px; */
        height: 5rem;
        border-bottom: 1px solid #ccc;
    }

    .inp input {
        /* width: 300px;
        height: 30px; */
        width: 30rem;
        height: 3rem;        
        outline: none;
        border: none;
        color: #ccc;
        font-size: 1.6rem;
    }

    .login-r button {
        /* width: 300px;
        height: 35px; */
        width: 30rem;
        height: 3.5rem;        
        background-color: #4f93fe;
        color: #fff;
        /* border-radius: 50px; */
        border-radius: 5rem;
        border: none;
        /* margin-top: 50px; */
        margin-top: 5rem;
        cursor: pointer;
    }

    .login-list {}
    .login-list li{
        /* padding: 8px;
        font-size: 18px;
        border-bottom: 4px solid transparent; */
        padding: 0.8rem;
        font-size: 1.8rem;
        border-bottom: 0.4rem solid transparent;        
        cursor: pointer;
    }
    .login-list li.active{
        border-bottom-color: #009688;
    }
    .login-list li a {
        color: #333333;
    }

    .lang-switch {
        position: absolute;
        /* right: 20px;
        top: 15px; */
        right: 2rem;
        top: 1.5rem;        
        display: flex;
        align-items: center;
    }
    .lang-switch span {
        margin: 0 2px;
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

    /* 媒体查询 Start */
    /* @media screen and (min-width: 0px) and (max-width: 767px) { */
    @media screen and (max-width: 767.9px) { /* 页面测试无法显示767，实际是767.2px */
        /* .bg {
            height: 700px;
        } */
        .cursor {
            display: none;
        }
        .bg>img {
            display: none;
        }
        .login-center {
            /* display: flex; */
            flex-direction: column;
            height: 100%;
            width: 100%;
            box-sizing: border-box;
            justify-content: normal;
            padding: 3rem 2.5rem;
            border-radius: 0px;
        }
        .login-l {
            height: 40%;
        }
        .login-l img {
            width: 100%;
            height: 100%;
            position: static;
        }
        .login-r {
            padding: 4rem 5rem;
            width: 100%;
            min-height: 45%;
            text-align: center;
            box-shadow: 0px 0px 15px -10px #000;
            border-radius: 2rem;
            margin-top: -4rem;
            box-sizing: border-box;
            background-color: white;            
        }
        .login-r>h3 {
            font-size: 3.8rem;
        }
        .login-r>p {
            font-size: 2.8rem!important;
        }
        .login-list li {
            font-size: 2.8rem;
        }
        .lang-switch {
            top: 5rem;
            right: 4rem;
        }
    }

    /* @media screen and (min-width: 768px) and (max-width: 992px) {} */

    /* 媒体查询 End */

</style>