<template>

  <div class="personnel-main">

        <div class="personnel-content">
          <h1 style="font-size: 20px;color: #999;margin-bottom: 12px">隔离人员分配</h1>

          <div class="d-flex align-items-center mb-15">
                <p class="w-120 text-left font-16">姓名:</p>
                <el-input
                    style="width: 350px;"
                    placeholder="请输入人员姓名"
                    v-model="form.name"
                    clearable>
                </el-input>
            </div>          
            <div class="d-flex align-items-center mb-15">
                <p class="w-120 text-left font-16">用户名:</p>
                <el-input
                    style="width: 350px;"
                    placeholder="请输入用户名"
                    v-model="form.userName"
                    clearable>
                </el-input>
            </div>              
            <div class="d-flex align-items-center mb-15">
                <p class="w-120 text-left font-16">身份证:</p>
                <el-input
                    style="width: 350px;"
                    placeholder="请输入身份证号码"
                    v-model="form.idCardNum"
                    clearable>
                </el-input>
            </div>
            <div class="d-flex align-items-center mb-15">
                <p class="w-120 text-left font-16">手机号:</p>
                <el-input
                    style="width: 350px;"
                    placeholder="请输入手机号码"
                    v-model="form.phone"                    
                    clearable>
                </el-input>
            </div>
            <div class="d-flex align-items-center mb-15">
                <p class="w-120 text-left font-16">电子邮箱:</p>
                <el-input
                    style="width: 350px;"
                    placeholder="请输入电子邮箱"
                    v-model="form.email"                    
                    clearable>
                </el-input>
            </div>
            <div class="d-flex align-items-center mb-15">
                <p class="w-120 text-left font-16">隔离类型:</p>
                <el-select style="width:350px;" v-model="form.situation" placeholder="请选择">
                    <el-option
                        v-for="item in situationOptions"
                        :key="item.value"
                        :label="item.label"
                        :value="item.value">
                    </el-option>
                </el-select>                
            </div>
            <div class="d-flex align-items-center mb-15">
                <p class="w-120 text-left font-16">隔离时间:</p>
                <el-date-picker
                    style="width:350px;"
                    v-model="form.startTime"
                    type="datetime"
                    placeholder="选择日期时间">
                </el-date-picker>
            </div>
            <div class="d-flex align-items-center mb-15">
                <p class="w-120 text-left font-16">解除隔离时间:</p>
                <el-date-picker
                    style="width:350px;"
                    v-model="form.endTime"
                    type="datetime"
                    placeholder="选择日期时间">
                </el-date-picker>
            </div>
            <div class="d-flex align-items-center mb-15">
                <p class="w-120 text-left font-16">酒店:</p>
                <el-select style="width:350px;" v-model="form.hotel" placeholder="请选择">
                    <el-option
                        v-for="item in hotelOptions"
                        :key="item.id"
                        :label="item.name"
                        :value="item.id">
                    </el-option>
                </el-select>                
            </div>
            <div class="d-flex align-items-center mb-15">
                <p class="w-120 text-left font-16">房间类型:</p>
                <el-select style="width:350px;" v-model="form.roomType" placeholder="请选择">
                    <el-option
                        v-for="item in roomTypeOptions"
                        :key="item.id"
                        :label="item.name"
                        :value="item.id">
                    </el-option>
                </el-select>
            </div>             
<!--            <div class="d-flex align-items-center mb-15">-->
<!--                <p class="w-120 text-left font-16">人员状态:</p>-->
<!--                <el-select style="width:350px;" v-model="form.status" placeholder="请选择">-->
<!--                    <el-option-->
<!--                        v-for="item in statusOptions"-->
<!--                        :key="item.value"-->
<!--                        :label="item.label"-->
<!--                        :value="item.value">-->
<!--                    </el-option>-->
<!--                </el-select>                -->
<!--            </div>     -->
            <div class="d-flex align-items-center mb-15">
                <p class="w-120 text-left font-16">人员省份:</p>
                <el-cascader
                    style="width: 350px;"
                    size="large"
                    :options="addressData"
                    :v-model="area"
                    @change="getAddress"
                ></el-cascader>                               
            </div>                    
            <div>
                <el-button type="primary" @click="confirm">确定</el-button>
            </div>
        </div>

  </div>

</template>

<script>
import { get, post } from "../utils/request";
import {CodeToText, provinceAndCityData} from 'element-china-area-data'


export default {
    name: "AssignPersonnel",
    data(){
        return {
            form: {
                name: '',
                userName: '',
                idCardNum: '',
                phone: '',
                email: '',
                situation: '',
                startTime: '',
                endTime: '',
                status: '',
                hotel: '',
                roomType: '',
                province: '',
                city: '',
            },
            situationOptions: [{
                value: 0,
                label: '密接'
            }, {
                value: 1,
                label: '应隔尽隔人员'
            }, {
                value: 2,
                label: '入境人员'
            }, {
                value: 3,
                label: '中高风险地区人员'
            }],
            statusOptions: [{
                value: 0,
                label: '正在隔离'
            }, {
                value: 1,
                label: '解除隔离'
            }, {
                value: 2,
                label: '阳性入院'
            }],
            hotelOptions: [],
            roomTypeOptions: [],
            addressData: provinceAndCityData,
            area: [],
        }
    },
    watch: {
        "form.hotel"(val, oldVal){
            this.getIsolationRoomType()
        }
    },
    mounted() {
        this.getIsolationHotelList()
    },
    methods: {
        getAddress(value) { //value是长度为2的装有被选择省、市代码的数组;CodeToText是个对象，键名为代码，键值为省和城市
            this.area = [];
            for (let i = 0; i < value.length; i++) {
                let code = value[i];
                this.area.push(CodeToText[code]);
            }
            this.form.province = this.area[0]
            this.form.city = this.area[1]
            console.log(this.area); //["河北省","唐山市"]
        },
        // 获取隔离酒店
        getIsolationHotelList() {
            const data = {
                page: {
                    page: 1,
                    size: 99999
                },
                allowIsolation: 1                
            }
            post('/api/hotel/page', data)
                .then( res => {
                    console.log(res);
                    this.hotelOptions = res.data.data.records
                })
                .catch( err => {
                    console.error(err);
                })
        },

        // 获取隔离酒店对应的房间
        getIsolationRoomType() {
            const data = {
                isIsolation: 1
            }
            get(`api/roomType/currentRoomTypeList/${this.form.hotel}`, data)
                .then( res => {
                    console.log(res);
                    this.roomTypeOptions = res.data.data
                })
                .catch( err => {
                    console.error(err);
                })
        },

        confirm() {
            const data = {
                name: this.form.name,
                userName: this.form.userName,
                idCard: this.form.idCardNum,
                phone: this.form.phone,
                email: this.form.email,
                type: this.form.situation,
                checkInTime: this.form.startTime,
                checkOutTime: this.form.endTime,
                status: this.form.status,
                hotelId: this.form.hotel,
                roomType: this.form.roomType,
                province: this.form.province,
                city: this.form.city,
            }
            post('/api/isolationInfo/distribution',data)
                .then( res => {
                    console.log(res);
                    if (res.data.code == 200) {
                        this.$message({
                            message: '成功',
                            type: 'success',
                            duration: 2000
                        });                        
                    }
                })
                .catch( err => {
                    console.error(err);
                })
        }

    }

}
</script>

<style scoped>
.personnel-main {
  display: flex;
  flex-direction: column;
  /* justify-content: space-between; */
  width: 100%;
  min-width: 1000px;
  height: 100%;
  background: #FFFFFF;
  box-shadow: 0px 5px 30px 0px rgba(22, 115, 255, 0.1);    
}
.personnel-content {
    padding: 30px 25px;
  border: 1px solid #d5d5d5;
  width: 500px;
  margin: 0 auto;
  margin-top: 30px;
  border-radius: 6px;
}
</style>