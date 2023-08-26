<template>
    <div class="mt-10">
        <div class="inp font-14">
            <p class="m-0">{{  $t('login.account') }}:</p>
            <input v-model="username" type="text" :placeholder="$t('login.accountTips')">
        </div>
        <div class="inp font-14">
            <p class="m-0">{{  $t('login.password') }}:</p>
            <input @keyup.enter="login" v-model="password" type="password" :placeholder="$t('login.passwordTips')">
        </div>
        <div class="d-flex align-items-center justify-content-center">
            <button @click="login">{{  $t('login.login') }}</button>
        </div>

    </div>
</template>

<script>
    import {get,post} from "../utils/request";

    export default {
        name: "SignIn",
        data(){
            return {
                username: '',
                password: '',
            }
        },
        mounted(){

        },
        methods: {
            //登录
            login() {
                let userData = {
                    username: this.username,
                    password: this.password,
                }
                post('/auth/login', userData)
                    .then(res => {
                        console.log('登录信息:',res);
                        //判断返回的数据是否存在token和用户id
                        if (res.data.data.token && res.data.data.userInfo.id ) {
                            // 将token及用户id存放到Vuex中
                            this.$store.commit('SAVE_TOKEN',res.data.data.token)
                            this.$store.commit('SAVE_USER_ID',res.data.data.userInfo.id)
                            localStorage.setItem('avatar',res.data.data.userInfo.heads)
                            //路由跳转
                            this.$router.push({
                                name: 'index',
                            })

                        } else {
                            console.log('token或用户id不存在，登录失败');
                        }
                    })
                    .catch(err => {
                        console.log(err)
                    })
            }
        }
    }
</script>

<style scoped>
    .inp {
        display: flex;
        align-items: center;
        /* height: 50px; */
        height: 5rem;
        border-bottom: 1px solid #ccc;
    }
    .inp p{
        /* min-width: 70px; */
        min-width: 7rem;
        white-space: nowrap;
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
        /* margin-top: 71px; */
        margin-top: 7.1rem;
        cursor: pointer;
        font-size: 1.6rem;
    }
    

    /* 媒体查询 Start */
    @media screen and (max-width: 767.9px) { /* 页面测试无法显示767，实际是767.2px */
        .inp {
            border-bottom: none;
            height: 8.5rem;
            margin-bottom: 1rem;
        }
        .inp>p {
            display: none;
        }
        .inp input {
            border: 1px #ccc solid;
            height: 6rem;
            border-radius: 2rem;
            padding: 0.5rem 0.8rem;
            box-sizing: border-box;
            width: 100%;
        }
        .login-r button {
            width: 35rem;
            height: 5.5rem;
            font-size: 1.8rem;
            margin-top: 4rem;
        }
        .login-r button {
            outline: none;           
        }        
    }

    /* @media screen and (min-width: 768px) and (max-width: 992px) {} */

    /* 媒体查询 End */
</style>