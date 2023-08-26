<template>
  <div ref="walletBox" style="min-height: 100vh;">

    <!-- 导航栏 -->
    <top-nav></top-nav>

    <div class="hotel-list-bg d-flex align-items-center justify-content-center position-relative">
      <img src="../assets/images/hotel-list-bg.png" alt="">
    </div>

    <div class="big-box">

      <div class="verify-box flex flex-column ">
        <h1 style="margin: 3rem auto;font-size:3.2rem">{{ $t('wallet.myWallet') }}</h1>

        <div v-if="!walletInfo" class="verify-box-inner flex flex-column align-items-center justify-content-center">
          <p style="color: #999;font-size:2rem"><i class="el-icon-warning"> {{ $t('wallet.tips') }}</i></p>
          <br>
          <el-input class="mb-15 font-18" :placeholder="$t('common.passwordTips')" v-model="pwd" show-password></el-input>

          <el-button class="mt-10" type="primary" @click="verifyAccount">{{ $t('common.confirm') }}</el-button>

        </div>
        <div class="wallet-box flex flex-column align-items-center justify-content-center" v-else>
          <i class="el-icon-money" style="color: orange;font-size: 5rem;margin: 2rem"></i>
          <p style="margin: 2.6rem 1rem -0.8rem 1rem;font-size: 1.6rem">{{ $t('wallet.myBalance') }}</p>
          <p class="price">￥{{ walletInfo.balance }}</p>
          <el-button class="wallet-button" type="success">{{ $t('wallet.topUp') }}</el-button>
          <el-button class="wallet-button" type="info" plain>{{ $t('wallet.withdraw') }}</el-button>

        </div>
      </div>

    </div>


  </div>
</template>

<script>
import {formDataPost, get, post, postOnForm} from "../utils/request";
import TopNav from '../components/TopNav'
import Footer from '../components/Footer.vue';

export default {
  name: "wallet",
  components: {
    TopNav,
    Footer
  },
  data() {
    return {
      pwd: '',
      walletInfo:undefined
    }
  },
  filters: {},
  mounted() {
    if (window.innerWidth < 768) {
        this.$refs.walletBox.style.height = `${window.innerHeight}px`
    } else {
        this.$refs.walletBox.style.height = `100vh`
    }    

  },
  methods: {
    verifyAccount() {
      let data = {
        pwd:this.pwd
      }
      formDataPost("api/wallet/get",data).then(res => {
        console.log(res)
        if (res.data.code === "200") {
          this.walletInfo = res.data.data
          console.log(this.walletInfo)
        }else {
          this.$message.error(res.data.msg);
        }
      }).catch(reason => {
        this.$message.error(reason);
      })
    }
  }
}
</script>

<style scoped>
.big-box {
  height: calc(100vh - 220px);
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  background-color: #ecf5ff;
}

.t-nav {
  width: 100%;
  height: 68px;
  background: #fff;
  opacity: 0.8;
  z-index: 999;
  position: fixed;
  top: 0;
  left: 0;
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
  font-size: 18px;
  margin: 0 30px;
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
  color: #323230
}

.hotel-list-bg {
  width: 100%;
  height: 220px;
}

.hotel-list-bg img {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 220px;
}

.hotel-list-bg h3 {
  margin-bottom: 25px;
  font-size: 36px;
  font-weight: 400;
  color: #FFFFFF;
}

.hotel-list-content {
  display: flex;
  justify-content: center;
  padding-top: 30px;
}

.hotel-list {

}

.hotel-list li {
  display: flex;
  margin-bottom: 30px;
}

.hotel-list li .li-l {
  width: 370px;
  height: 423px;
  margin: 0px 30px;
}

.hotel-list li .li-l img {
  width: 100%;
  height: 100%;
}

.hotel-list li .li-r {
  width: 360px;
  margin: 0px 30px;
  position: relative;
}

.hotel-list li .li-r h4 {
  font-size: 36px;
  margin-bottom: 15px;
  font-weight: 400;
}

.hotel-list li .li-r h6 {
  font-size: 14px;
  margin-bottom: 28px;
  font-weight: 400;
}

.hotel-list li .li-r div {
  font-size: 15px;
  color: #898989;
  margin-bottom: 20px;
  overflow: auto;
  max-height: 206px;
}

.hotel-list li .li-r button {
  position: absolute;
  left: 0;
  bottom: 0;
  width: 118px;
  height: 38px;
  line-height: 38px;
  background: #E1BD85;
  font-size: 12px;
  color: #FFFFFF;
  border-radius: 5px;
  border: none;
}

.hotel-list li:nth-child(even) .li-l {
  order: 2;
}

.order-list-content {
  /* margin: 20px auto 0px auto;
  width: 80vw; */

  padding: 30px 15%;
  background-color: #ecf5ff;
}

.order-status {
  display: flex;
  align-items: center;
  justify-content: center;
  background-color: white;
  border-radius: 15px;
  flex: 1;
  height: 120px;
  margin: 0px 10px;
  padding: 0px 20px;
}

.order-status > img {
  width: 60px;
  height: 60px;
  margin-right: 22px;
}

.order-status > div {
  display: flex;
  flex-direction: column;
  height: 60px;
  justify-content: space-between;
}

.order-list {
  margin: 20px 10px
}

.order-list li {
  padding: 25px;
  margin-bottom: 10px;
  background-color: white;
  border-radius: 15px;
  flex: 1;
}

.el-pagination.is-background .btn-next, .el-pagination.is-background .btn-prev, .el-pagination.is-background .el-pager li {
  background-color: #ffffff !important;
}

.verify-box {
  margin:auto auto;
  border-radius: 6px;
  /*border: 1px solid red;*/
  /* width: 600px;
  height: 450px; */
  width: 60rem;
  height: 45rem;  
  background: #fff;
  box-shadow: 0 12px 5px -10px rgba(0, 0, 0, 0.1), 0 0 4px 0 rgba(0, 0, 0, 0.1);

}

.verify-box-inner {
  margin:auto;
  text-align: center;
  padding: 3rem;
  /*border: 1px solid red;*/

}

.wallet-button {
  width: 18rem;
  margin: 0.6rem;
}

.price {
  font-size: 5rem;
  margin: 1rem;
  font-weight: bolder;
}

/* 媒体查询 Start */
@media screen and (max-width: 767.9px) { /* 页面测试无法显示767，实际是767.2px */
  .hotel-list-bg {
    height: 120px;
  }
  .hotel-list-bg img {
    height: 120px;
  }
  .big-box {
    height: calc(100% - 120px);
    align-items: center;
    justify-content: center;
  }
  .verify-box {
    width: 90%;
    height: 50rem;
  }
}
/* 媒体查询 End */

</style>