<template>
    <div class="mt-10">
        <div class="inp font-14">
            <p class="m-0">{{  $t('login.account') }}:</p>
            <input v-model="username" type="text" :placeholder="$t('login.accountTips')">
        </div>
        <div class="inp font-14">
            <p class="m-0">{{  $t('login.password') }}:</p>
            <input v-model="password" type="password" :placeholder="$t('login.passwordTips')">
        </div>
        <div class="inp font-14">
            <p class="m-0">{{  $t('login.passwordAgain') }}:</p>
            <input v-model="againPassword" type="password" :placeholder="$t('login.passwordAgainTips')">
        </div>
        <div class="d-flex align-items-center justify-content-center">
            <button @click="register">{{  $t('login.register') }}</button>
        </div>

    </div>
</template>

<script>
    import {get,post} from "../utils/request";

    export default {
        name: "Register",
        data(){
            return {
                username: '',
                password: '',
                againPassword: '',
            }
        },
        mounted(){},
        methods: {
            register(){
                let data = {
                    username: this.username,
                    password: this.password
                }
                if (this.password != this.againPassword){
                    this.$message.error(this.$t('login.passwordError'));
                } else {
                    post('/open/auth/register',data)
                        .then(res => {
                            console.log(res);
                            if (res.data.code == 200) {
                                this.$message({
                                    message: this.$t('login.success'),
                                    type: 'success'
                                });
                            }
                        })
                        .catch(err => {
                            console.log(err);
                        })
                }
            }
        }
    }
</script>

<style scoped>
    .inp {
        display: flex;
        align-items: center;
        /* justify-content: space-between; */
        /* height: 50px; */
        height: 5rem;
        border-bottom: 1px solid #ccc;
    }
    .inp p{
        /* min-width: 70px; */
        min-width: 7rem;
        white-space: nowrap;
        /* margin-right: 10px; */
        margin-right: 1rem;
    }

    .inp input {
        /* width: 260px;
        height: 30px; */
        width: 26rem;
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
        /* margin-top: 20px; */
        margin-top: 2rem;
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