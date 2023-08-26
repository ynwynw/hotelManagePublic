<template>
  <div style="background-color: #ecf5ff;min-height: 100vh">

    <!-- 导航栏 -->
    <top-nav></top-nav>

    <div class="hotel-list-bg d-flex align-items-center justify-content-center position-relative">
      <img src="../assets/images/hotel-list-bg.png" alt="">
      <h3>{{ $t('hotelList.title') }}</h3>
    </div>

    <div class="hotel-list-main">

      <div>
        <!--  酒店列表-->
        <div class="hotel-list-content">
          <ul class="hotel-list">
            <li @click="clickFlag && hotelDetails(index,item.id)" v-for="(item,index) in hotelList">
              <div class="li-l">
                <img :src=item.cover alt="">
              </div>
              <div class="li-r">
                <div>
                  <h4>{{ item.name }}</h4>
                  <h6>{{ item.address }}</h6>
                </div>
                <div class="flex flex-row mb-10 flex-wrap" v-if="!item.badge && item.allowIsolation">
                  <div style="background: #F56C6C" class="badge" v-if="item.allowIsolation">
                    {{ $t('hotelList.isolatedHotel') }}
                  </div>
                </div>
                <div class="flex flex-row mb-10 flex-wrap" v-if="item.badge">
                  <div style="background: #F56C6C" class="badge" v-if="item.allowIsolation">
                    {{ $t('hotelList.isolatedHotel') }}
                  </div>
                  <div v-if="item.badge" class="badge" v-for="i in item.badge.split(',')">
                    {{ i | hotelBadge }}
                  </div>
                </div>
                <div class="hotel-introduce-box" v-html="item.introduce"></div>
                <button @click="hotelDetails(index,item.id)">{{ $t('hotelList.confirm') }}</button>
              </div>
            </li>
          </ul>
        </div>

        <div v-if="pageNum > 5" class="hotel-pagination d-flex align-items-center justify-content-center mb-30">
          <el-pagination
              :small="smallPagination"
              background
              :page-size="5"
              :pager-count="5"
              @current-change="handleCurrentChange"
              @prev-click="prevPage"
              @next-click="nextPage"
              layout="prev, pager, next"
              :total="pageNum">
          </el-pagination>
        </div>
      </div>

      <!-- 页脚 -->
      <Footer></Footer>

    </div>


    <!-- 弹出框 -->
    <el-dialog
        :title="$t('hotelList.dialogTitle')"
        center
        :visible.sync="dialogVisible"
        width="570px">
      <div class="contont">
        <div class="d-flex align-items-center mb-15">
          <p class="w-100 text-left">{{ $t('hotelList.selectDate') }}</p>
          <div class="block">
            <el-date-picker
                v-model="dateValue"
                type="daterange"
                value-format="yyyy-MM-dd"
                range-separator="-"
                :start-placeholder="$t('hotelList.startDate')"
                :end-placeholder="$t('hotelList.endDate')"
                :picker-options="pickerOptions">
            </el-date-picker>
          </div>
        </div>
        <div class="d-flex align-items-center mb-15">
          <p style="white-space: nowrap;min-width: 100px!important;" class="w-100 text-left">
            {{ $t('hotelList.selectRoom') }}</p>
          <div>
            <el-radio class="room-type-radio" v-for="(item,index) in roomTypeList" v-model="roomType" :label="item.id">
              {{ item.roomTypeName | roomNameFormat }}
            </el-radio>
          </div>
        </div>
        <div class="d-flex align-items-center mb-15">
          <p class="w-100 text-left">{{ $t('hotelList.province') }}</p>
          <el-select v-model="provinceVal" :placeholder="$t('hotelList.select')">
            <el-option
                v-for="item in options"
                :label="item.label"
                :value="item.value">
            </el-option>
          </el-select>
        </div>
      </div>
      <span slot="footer" class="dialog-footer">
            <el-button @click="dialogVisible = false">{{ $t('hotelList.dialogCancel') }}</el-button>
            <el-button type="primary" @click="confirm">{{ $t('hotelList.dialogConfirm') }}</el-button>
        </span>
    </el-dialog>

  </div>
</template>

<script>
import {get, post, formDataPost} from "../utils/request";
import TopNav from '../components/TopNav'
import Footer from '../components/Footer.vue';

export default {
  name: "HotelList",
  components: {
    TopNav,
    Footer
  },
  watch: {
    dialogVisible(newVal, oldVal) {
      console.log(newVal);
      console.log(oldVal);
      if (newVal == false && oldVal == true) {
        this.dateValue = []
        this.roomType = ''
        this.provinceVal = ''
      }
    }
  },
  filters: {
    roomNameFormat(val) {
      const lang = localStorage.getItem('lang')
      if (lang == 'zh') {
        return val
      } else if (lang == 'en') {
        if (val == '单人房') {
          return val = 'Single-bed room'
        } else if (val == '豪华大床房') {
          return val = 'Deluxe single Room'
        } else if (val == '双人房') {
          return val = 'Double room'
        } else if (val == '总统套房') {
          return val = 'Presidential suite'
        } else if (val == '经济房') {
          return val = 'Economy Room'
        } else {
          return val
        }
      }
    },
    hotelBadge(value) {
      const lang = localStorage.getItem("lang");
      if (lang == "zh" || lang == null) {
        if (value == '年度最受欢迎酒店') {
          
        } else if (value == '五星级酒店') {
          
        }
        return value;
      } else if (lang == "en") {
        if (value == '年度最受欢迎酒店') {
          value = 'The most popular Hotel';
        } else if (value == '五星级酒店') {
          value = 'Five-star Hotel';
        }     
        return value   
      }
    },    
  },
  data() {
    return {
      clickFlag: false,
      provinceVal: '',
      smallPagination: false,
      options: [
        {
          value: this.$t('hotelList.beijing'),
          label: this.$t('hotelList.beijing')
        }, {
          value: this.$t('hotelList.shanghai'),
          label: this.$t('hotelList.shanghai')
        }, {
          value: this.$t('hotelList.tianjin'),
          label: this.$t('hotelList.tianjin')
        }, {
          value: this.$t('hotelList.chongqing'),
          label: this.$t('hotelList.chongqing')
        }, {
          value: this.$t('hotelList.hebei'),
          label: this.$t('hotelList.hebei')
        }, {
          value: this.$t('hotelList.shanxi'),
          label: this.$t('hotelList.shanxi')
        }, {
          value: this.$t('hotelList.neimenggu'),
          label: this.$t('hotelList.neimenggu')
        }, {
          value: this.$t('hotelList.liaoning'),
          label: this.$t('hotelList.liaoning')
        }, {
          value: this.$t('hotelList.jilin'),
          label: this.$t('hotelList.jilin')
        }, {
          value: this.$t('hotelList.heilongjiang'),
          label: this.$t('hotelList.heilongjiang')
        }, {
          value: this.$t('hotelList.jiangsu'),
          label: this.$t('hotelList.jiangsu')
        }, {
          value: this.$t('hotelList.zhejiang'),
          label: this.$t('hotelList.zhejiang')
        }, {
          value: this.$t('hotelList.anhui'),
          label: this.$t('hotelList.anhui')
        }, {
          value: this.$t('hotelList.fujian'),
          label: this.$t('hotelList.fujian')
        }, {
          value: this.$t('hotelList.jiangxi'),
          label: this.$t('hotelList.jiangxi')
        }, {
          value: this.$t('hotelList.shandong'),
          label: this.$t('hotelList.shandong')
        }, {
          value: this.$t('hotelList.henan'),
          label: this.$t('hotelList.henan')
        }, {
          value: this.$t('hotelList.hubei'),
          label: this.$t('hotelList.hubei')
        }, {
          value: this.$t('hotelList.hunan'),
          label: this.$t('hotelList.hunan')
        }, {
          value: this.$t('hotelList.guangdong'),
          label: this.$t('hotelList.guangdong')
        }, {
          value: this.$t('hotelList.guangxi'),
          label: this.$t('hotelList.guangxi')
        }, {
          value: this.$t('hotelList.hainan'),
          label: this.$t('hotelList.hainan')
        }, {
          value: this.$t('hotelList.sichuan'),
          label: this.$t('hotelList.sichuan')
        }, {
          value: this.$t('hotelList.guizhou'),
          label: this.$t('hotelList.guizhou')
        }, {
          value: this.$t('hotelList.yunnan'),
          label: this.$t('hotelList.yunnan')
        }, {
          value: this.$t('hotelList.xizang'),
          label: this.$t('hotelList.xizang')
        }, {
          value: this.$t('hotelList.shanxi'),
          label: this.$t('hotelList.shanxi')
        }, {
          value: this.$t('hotelList.gansu'),
          label: this.$t('hotelList.gansu')
        }, {
          value: this.$t('hotelList.ningxia'),
          label: this.$t('hotelList.ningxia')
        }, {
          value: this.$t('hotelList.qinghai'),
          label: this.$t('hotelList.qinghai')
        }, {
          value: this.$t('hotelList.xinjiang'),
          label: this.$t('hotelList.xinjiang')
        }, {
          value: this.$t('hotelList.taiwan'),
          label: this.$t('hotelList.taiwan')
        }, {
          value: this.$t('hotelList.xianggang'),
          label: this.$t('hotelList.xianggang')
        }, {
          value: this.$t('hotelList.aomen'),
          label: this.$t('hotelList.aomen')
        }],
      hotelList: [],
      startTime: '',
      endTime: '',
      hotelId: '',
      pageNum: 0,
      dateValue: [],
      dialogVisible: false,
      roomType: '',
      roomTypeId: '',
      roomTypeList: [],
      bedType: undefined,
      form: {
        name: undefined,
        maxLoad: undefined,
        bed: undefined,
        haveWindow: undefined,
        network: undefined,
        haveBreakfast: undefined,
        sort: undefined,
        fee: undefined,
        bedType: undefined,
        allowIsolation: 1
      },
      pickerOptions: {
        disabledDate(v) {
          return v.getTime() < new Date().getTime() - 86400000;
        }
      }
    }
  },
  mounted() {
    this.getHotelList()
    if (window.innerWidth < 768) {
      this.smallPagination = true
      this.clickFlag = true
    } else {
      this.smallPagination = false
      this.clickFlag = false
    }    
    window.onresize = () => {
      if (window.innerWidth < 768) { 
        this.smallPagination = true
        this.clickFlag = true
      } else {
        this.smallPagination = false
        this.clickFlag = false
      }
    }
  },
  methods: {
    //获取酒店列表
    getHotelList() {
      let hotelInfo = ''
      if (this.$route.params.hotelInfo) {
        hotelInfo = JSON.parse(this.$route.params.hotelInfo)
      }
      console.log(hotelInfo);
      let data = {
        page: {
          page: 1,
          size: 5
        },
      }
      if (hotelInfo) {
        this.hotelList = hotelInfo
      } else {
        this.hotelListRequest(data)
      }

    },
    //获取酒店列表请求
    hotelListRequest(data) {
      post('/api/hotel/page', data)
          .then(res => {
            console.log(res);
            this.hotelList = res.data.data.records
            this.pageNum = res.data.data.total
          })
          .catch(err => {
            console.log(err);
          })
    },

    //选择页码
    handleCurrentChange(num) {
      let data = {
        page: {
          page: num,
          size: 5
        }
      }
      this.hotelListRequest(data)
      window.scrollTo(0, 0)
    },

    //上一页
    prevPage(num) {
      let data = {
        page: {
          page: num,
          size: 5
        }
      }
      this.hotelListRequest(data)
      window.scrollTo(0, 0)
    },

    //下一页
    nextPage(num) {
      let data = {
        page: {
          page: num,
          size: 5
        }
      }
      this.hotelListRequest(data)
      window.scrollTo(0, 0)
    },


    hotelDetails(index, id) {
      console.log(index);
      console.log(id);
      this.hotelId = id
      let data = {
        hotelId: id
      }

      this.$router.push({
        name: 'hotelDetails',
        params: {
          index: index,
          hotelId: id
        }
      })
      localStorage.setItem('currentHotelId',id)
    },

    confirm() {
      let data = {
        customerId: localStorage.getItem('userId'),
        hotelId: this.hotelId,
        roomType: this.roomType,
        estimatedCheckIn: this.dateValue[0],
        estimatedCheckOut: this.dateValue[1],
        province: this.provinceVal,

      }
      post('/api/order/create', data)
          .then(res => {
            console.log(res);
            if (res.data.code == 200) {
              this.$message({
                message: this.$t('hotelList.success'),
                type: 'success'
              });
              this.dialogVisible = false
              console.log("订单id：", res.data.data.id)
            } else {
              this.$message({
                message: res.data.msg,
                type: 'warning',
                duration: 4000
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
.room-type-radio .el-radio__label {
  display: inline-block;
  width: 85px;
}

.hotel-list-main {
  height: calc(100vh - 220px);
  display: flex;
  flex-direction: column;
  justify-content: space-between;
}

.hotel-list-main > div:first-child {
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
  /* margin-bottom: 30px; */
  margin-bottom: 3rem;
}

.hotel-list li .li-l {
  /* width: 370px;
  height: 423px;
  margin: 0px 30px; */
  width: 37rem;
  height: 42.3rem;
  margin: 0 3rem;  
}

.hotel-list li .li-l img {
  width: 100%;
  height: 100%;
}

.hotel-list li .li-r {
  /* width: 360px;
  margin: 0px 30px; */
  width: 36rem;
  margin: 0 3rem;
  position: relative;
}

.hotel-list li .li-r h4 {
  /* font-size: 36px;
  margin-bottom: 15px; */
  font-size: 3rem;
  margin-bottom: 1.5rem;  
  font-weight: 400;
}

.hotel-list li .li-r h6 {
  /* font-size: 18px;
  margin-bottom: 28px; */
  font-size: 1.8rem;
  margin-bottom: 2.8rem;
  font-weight: 400;
}

.hotel-list li .li-r .hotel-introduce-box {
  /* font-size: 15px;
  margin-bottom: 20px;
  max-height: 206px; */
  font-size: 1.5rem;
  margin-bottom: 2rem;
  max-height: 20.6rem;  
  color: #898989;
  overflow: auto;
}

.hotel-list li .li-r button {
  position: absolute;
  left: 0;
  bottom: 0;
  /* width: 118px;
  height: 38px;
  line-height: 38px;
  font-size: 16px;   */
  width: 11.8rem;
  height: 3.8rem;
  line-height: 3.8rem;
  font-size: 1.6rem;
  background: #E1BD85;
  color: #FFFFFF;
  border-radius: 5px;
  border: none;
}

.hotel-list li:nth-child(even) .li-l {
  order: 2;
}

.mb-30 {
  margin-bottom: 30px;
}

.badge {
  margin: 2px 6px;
  border-radius: 6px;
  background: #4f93fe;
  padding: 6px 8px;
  color: #fff !important;
  cursor: default;
}
.badge:first-child {
  margin-left: 0px;
}
.badge:last-child {
  margin-right: 0px;
}

@media screen and (max-width: 767.9px) { /* 页面测试无法显示767，实际是767.2px */
  .hotel-list-bg {
    height: 120px;
  }
  .hotel-list-bg img {
    height: 120px;
  }
  .hotel-list-main {
    height: calc(100vh - 120px);
  }
  .hotel-list {
    padding: 0 1rem;
    width: 100%;
  }
  .hotel-list li {
    background-color: white;
    padding: 2rem;
    border-radius: 2rem;
    margin-bottom: 1.5rem;
  }
  .hotel-list li .li-l {
    width: 95px;
    height: 120px;
    margin: 0 2rem 0 0;
  }
  .hotel-list li .li-r {
    width: calc(100% - 107px);
    margin: 0;
    display: flex;
    flex-direction: column;
    justify-content: space-between;
  }
  .hotel-list li .li-r h4 {
    max-height: 48px;
    overflow: hidden;
  }
  .hotel-list li .li-r h6 {
    max-height: 32px;
    overflow: hidden;    
  }  
  .hotel-list li .li-r button {
    display: none;
  }
  .hotel-introduce-box {
    display: none;
  }
  .hotel-list li .li-r div {
    margin-bottom: 0;
  }
  .hotel-list li:nth-child(even) .li-l {
    order: 0;
  }
  .badge {
    margin: 4px 4px 4px 0;
    padding: 3px 4px;
  }  

  /* 隐藏翻页器 */
  .hotel-pagination {
    /* display: none; */
  }
}


</style>