<template>
  <div class="big-body">
    <!-- 导航栏 -->
    <top-nav></top-nav>
    <div class="hotel-list-bg d-flex align-items-center justify-content-center position-relative">
      <img src="../assets/images/hotel-list-bg.png" alt="">
      <!--      <h3>{{ $t('hotelList.title') }}</h3>-->
    </div>
    <div class="hotel-list-main">


      <div class="big-box">
        <!--                <el-button @click="payForAlipay">Test</el-button>-->
        <!--        <p>酒店id：{{ hotelDetails.hotelId }}</p>-->
        <div class="hotel-title">
          {{ hotelDetails.name }}
        </div>

        <div ref="hotelDetail" class="hotel-detail">
          <div class="detail-left">
            <div class="detail-detail">
              <div class="flex flex-row" v-if="!hotelDetails.badge && hotelDetails.allowIsolation">
                <div style="background: #F56C6C" class="badge" v-if="hotelDetails.allowIsolation">
                  {{ $t('hotelList.isolatedHotel') }}
                </div>
              </div>
              <div class="flex flex-row" v-if="hotelDetails.badge">
                <div style="background: #F56C6C" class="badge" v-if="hotelDetails.allowIsolation">
                  {{ $t('hotelList.isolatedHotel') }}
                </div>
                <div v-if="hotelDetails.badge" class="badge" v-for="i in hotelBadgeList">
                  {{ i | hotelBadge }}
                </div>
              </div>
              <div class="flex flex-row flex-n-wrap">
                <div class="flex-column address-detail">
                  <p>{{ hotelDetails.province + " " + hotelDetails.city }}</p>
                  <p>{{ hotelDetails.address }}</p>
                </div>
                <div class="flex justify-content-end align-items-center" style="width: 50%">
                  <i :class="isCollection ? 'el-icon-star-on' : 'el-icon-star-off'" class="location"
                     @click="collection()"></i>
                  <i class="el-icon-map-location location" @click="toMap()"></i>
                </div>
              </div>
              <el-divider content-position="left">{{ $t('hotelDetails.hotelIntroduction') }}</el-divider>
              <div class="detail-introduce" v-html="hotelDetails.introduce"></div>
            </div>

            <div class="detail-block">
              <h3 class="sub-title">
                酒店评价
                <span class="en">Evaluation</span>
              </h3>

              <div class="detail-content">
                <div class="comment-box flex flex-column">
                  <div class="comment-total-box">
                    <div class="comment-total-score flex flex-row align-items-end">
                      <div class="comment-total-score-ball flex justify-content-center align-items-center">
                        {{ hotelAvgScore }}
                      </div>
                      <div class="comment-total-score-star">
                        <el-rate
                            v-model="hotelAvgScore"
                            :colors="colors"
                            disabled
                            show-text>
                        </el-rate>
                      </div>
                    </div>
                    <p class="font-18 color-6">{{ $t('hotelDetails.comment1') }} {{ hotelCommentsTotal }}
                      {{ $t('hotelDetails.comment2') }}</p>
                  </div>
                  <el-divider content-position="left">{{ $t('hotelDetails.userEvaluation') }}</el-divider>

                  <div ref="commentUserBox" v-for="i in hotelCommentsList"
                       class="comment-user-box flex flex-row" style="margin-top: 0px;padding-top: 0px;border-top: 0px">
                    <div class="comment-user-box-left">
                      <el-avatar :size="circleUrlSize" :src="i.header"></el-avatar>
                    </div>
                    <div class="comment-user-box-right flex flex-column">
                      <div class="comment-user-nickname-date flex flex-row justify-content-between">
                        <div class="comment-user-nickname">{{ i.nickname ? i.nickname : i.username }}</div>
                        <div class="comment-user-date"> {{ i.createTime | dateTimeFormat('ymd') }}</div>
                      </div>
                      <div class="comment-user-roomType">{{
                          roomTypeMap[i.roomType] ? roomTypeMap[i.roomType].name : ''
                        }}
                      </div>
                      <div class="comment-user-score">
                        <el-rate
                            v-model="i.score"
                            :colors="colors"
                            disabled
                            show-score>
                        </el-rate>
                      </div>
                      <div class="comment-user-content">
                        {{ i.content }}
                      </div>
                      <div class="comment-user-pic" v-if="i.attachment">
                        <el-image
                            style="width: 10rem; height: 10rem"
                            :preview-src-list="i.attachment.split(',')"
                            :src="i.attachment">
                        </el-image>
                      </div>
                    </div>
                  </div>

                  <!-- 分页器 -->
                  <el-pagination
                      class="d-flex align-items-center justify-content-center"
                      :small="smallPagination"
                      background
                      :page-size="2"
                      :pager-count="5"
                      @current-change="handleCurrentChange"
                      @prev-click="prevPage"
                      @next-click="nextPage"
                      layout="prev, pager, next"
                      :total="hotelCommentsTotal"
                  >
                  </el-pagination>


                  <!-- <transition name="comment">
                    <div class="comment-list-box" :style="{'--commentHeight':commentHeight,}" v-show="comment">
                      <div v-for="i in hotelCommentsList.slice(1,hotelCommentsList.length)"
                           class="comment-user-box flex flex-row">
                        <div class="comment-user-box-left">
                          <el-avatar :size="circleUrlSize" :src="i.header"></el-avatar>
                        </div>
                        <div class="comment-user-box-right flex flex-column">
                          <div class="comment-user-nickname-date flex flex-row justify-content-between">
                            <div class="comment-user-nickname">{{ i.nickname ? i.nickname : i.username }}</div>
                            <div class="comment-user-date"> {{ i.createTime | dateTimeFormat('ymd') }}</div>
                          </div>
                          <div class="comment-user-roomType">
                            {{ roomTypeMap[i.roomType] ? roomTypeMap[i.roomType].name : '' }}
                          </div>
                          <div class="comment-user-score">
                            <el-rate
                                v-model="i.score"
                                :colors="colors"
                                disabled
                                show-score>
                            </el-rate>
                          </div>
                          <div class="comment-user-content">
                            {{ i.content }}
                          </div>
                          <div class="comment-user-pic" v-if="i.attachment">
                            <el-image
                                style="width: 10rem; height: 10rem"
                                :preview-src-list="i.attachment.split(',')"
                                :src="i.attachment">
                            </el-image>
                          </div>
                        </div>
                      </div>
              
                      <el-pagination
                          class="d-flex align-items-center justify-content-center"
                          :small="smallPagination"
                          background
                          :page-size="5"
                          :pager-count="5"
                          @current-change="handleCurrentChange"
                          @prev-click="prevPage"
                          @next-click="nextPage"
                          layout="prev, pager, next"
                          :total="hotelCommentsTotal"
                      >
                      </el-pagination>

                    </div>

                  </transition> -->

                  <!-- <div class="d-flex align-items-center expand-collapse cursor" @click="commentBtn">
                    <div style="margin: 0.8rem auto" v-if="hotelCommentsList.length > 1">
                      <i :class="comment ? 'el-icon-arrow-up' : 'el-icon-arrow-down'"/>&nbsp;{{
                        comment ? $t('hotelDetails.stow') : $t('hotelDetails.open')
                      }}
                    </div>
                  </div> -->


                </div>
              </div>
            </div>

            <div class="detail-block" v-if="hotelDiscounts != null && hotelDiscounts.length !== 0">
              <h3 class="sub-title">
                酒店优惠
                <span class="en">discount</span>
              </h3>

              <div class="detail-content flex flex-row flex-wrap" style="margin: 0;padding: 0">

                <div ref="couponBoxHeight" class="w-percent-100 d-flex flex-wrap">
                  <div ref="couponHeight" @click="gotCoupon(i.id)" class="discounts-box"
                       v-for="i in hotelDiscounts.slice(0, 3)">
                    <div class="discounts-title flex flex-row justify-content-between" :class="i.isGot?'active':''">
                      <p style="margin-left: 0.6rem;font-size: 1.4rem;font-weight: 700">{{ i.name }}</p>
                      <el-tooltip placement="right" style="margin: 4px">
                        <div slot="content">{{ i.description }}</div>
                        <el-button
                            style="border-radius: 50%;padding: 2px 8px;margin-left: 10px;font-weight: 800;color: #999">!
                        </el-button>
                      </el-tooltip>
                    </div>
                    <div style="border-top: 1px dotted #999;"></div>
                    <div class="discounts-body flex flex-column" :class="i.isGot?'active':''">
                      <div class="discounts-body-top flex flex-row align-items-center">
                        <div class="discounts-body-price">
                          {{ i.discountsType == 0 ? '￥' + i.discounts : i.discounts * 10 + '折' }}
                        </div>
                        <div class="discounts-body-condition" v-if="i.effectType == 0">
                          {{ '[满' + i.effectCondition + '天可用]' }}
                        </div>
                        <div class="discounts-body-condition" v-if="i.effectType == 1">
                          {{ '[满' + i.effectCondition + '元可用]' }}
                        </div>
                        <div class="discounts-body-condition" v-if="i.effectType == 2">[无门槛使用]</div>
                      </div>
                      <div class="discounts-body-bottom">
                        有效期到 {{ i.validityTime | dateTimeFormat }}
                      </div>
                    </div>
                  </div>
                </div>
                <transition name="draw">


                  <div :style="{'--couponHeight':couponHeight,}"
                       class="coupon-box d-flex flex-row flex-wrap w-percent-100" v-show="coupon">
                    <div @click="gotCoupon(i.id)" class="discounts-box"
                         v-for="i in hotelDiscounts.slice(3,hotelDiscounts.length)">
                      <div class="discounts-title flex flex-row justify-content-between" :class="i.isGot?'active':''">
                        <p style="margin-left: 0.6rem;font-size: 1.4rem;font-weight: 700">{{ i.name }}</p>
                        <el-tooltip placement="right" style="margin: 4px">
                          <div slot="content">{{ i.description }}</div>
                          <el-button
                              style="border-radius: 50%;padding: 2px 8px;margin-left: 10px;font-weight: 800;color: #999">
                            !
                          </el-button>
                        </el-tooltip>
                      </div>
                      <div style="border-top: 1px dotted #999;"></div>
                      <!-- <div class="discounts-body flex flex-column" :class="[i.isGot?'active':'', isGot.id?'active':'']"> -->
                      <div class="discounts-body flex flex-column" :class="i.isGot?'active':''">
                        <div class="discounts-body-top flex flex-row align-items-center">
                          <div class="discounts-body-price">
                            {{ i.discountsType == 0 ? '￥' + i.discounts : i.discounts * 10 + '折' }}
                          </div>
                          <div class="discounts-body-condition" v-if="i.effectType == 0">
                            {{ '[满' + i.effectCondition + '天可用]' }}
                          </div>
                          <div class="discounts-body-condition" v-if="i.effectType == 1">
                            {{ '[满' + i.effectCondition + '元可用]' }}
                          </div>
                          <div class="discounts-body-condition" v-if="i.effectType == 2">[无门槛使用]</div>
                        </div>
                        <div class="discounts-body-bottom">
                          有效期到 {{ i.validityTime | dateTimeFormat }}
                        </div>
                      </div>
                    </div>
                  </div>
                </transition>

                <div class="d-flex align-items-center expand-collapse cursor" @click="couponClick">
                  <div style="margin: 0.8rem auto" v-if="hotelDiscounts.length > 3">
                    <i :class="coupon ? 'el-icon-arrow-up' : 'el-icon-arrow-down'"/>&nbsp;{{
                      coupon ? $t('hotelDetails.stow') : $t('hotelDetails.open')
                    }}
                  </div>
                </div>

              </div>
            </div>

            <div class="detail-block">
              <h3 class="sub-title">
                酒店图片
                <span class="en">picture</span>
              </h3>

              <div class="detail-content" style="margin: 0;padding: 0">
                <el-image
                    style="width: 100%; height: 100%"
                    :src="hotelDetails.cover"
                    fit="fit"></el-image>
              </div>
            </div>

            <div class="detail-block">
              <h3 class="sub-title">
                设施服务
                <span class="en">AMENITIES</span>
              </h3>

              <div class="detail-content">
                <div v-html="hotelDetails.facilities"></div>
              </div>
            </div>


            <div class="detail-block">
              <h3 class="sub-title">
                其他政策
                <span class="en">NOTICE</span>
              </h3>

              <div class="detail-content">
                <div v-html="hotelDetails.otherPolicy"></div>
              </div>
            </div>
          </div>
          <div class="detail-right">
            <div class="detail-right-price" id="detailRight"
                 :style="isFixed ? `position:fixed;top:90px;width:${dynamicWidth}px` : ''">
              <div class="book-box">

                <el-switch
                    style="display: block;margin: 2rem 1rem;"
                    v-model="switchType"
                    active-color="#13ce66"
                    inactive-color="#ff4949"
                    :active-text="$t('hotelDetails.reserve')"
                    :inactive-text="$t('hotelDetails.declare')">
                </el-switch>

                <!-- 正常预定模块 -->
                <div v-show="switchType == true" class="natural">
                  <span class="price-sum">
                    <span>
                      <span class="unit">¥</span>
                      <span class="zg-price">
                        <strong>
                          <span v-if="!bookDay || !totalFee">{{ minFee + '~' + maxFee }}</span>
                          <span v-else>{{ totalFee }}</span>
                        </strong>
                      </span>
                    </span>
                    <!--                  <small class="text-weak">/晚</small>-->
                  </span>
                  <p class="font-18" v-if="bookDay" style="margin: 0.8rem;white-space: nowrap;">共{{ bookDay }}晚</p>
                  <div class="book-date flex flex-row align-items-center">
                    <el-date-picker
                        v-model="dateValue"
                        type="daterange"
                        value-format="yyyy-MM-dd"
                        range-separator="-"
                        :start-placeholder="$t('hotelDetails.startDate')"
                        :end-placeholder="$t('hotelDetails.endDate')"
                        :picker-options="pickerOptions">
                    </el-date-picker>
                    <!-- <p v-if="bookDay" style="margin: 2px;white-space: nowrap;">共{{ bookDay }}晚</p> -->
                  </div>
                  <div class="book-room">
                    <el-select style="width: 100%" v-model="currentRoomType" :placeholder="$t('hotelList.selectRoom')">
                      <el-option
                          v-for="item in roomTypeList"
                          :label="item.name"
                          :value="item.id">
                        <span style="float: left">{{ item.name }}</span>
                        <span style="float: right; color: #8492a6; font-size: 13px">￥{{ item.fee }}</span>
                      </el-option>
                    </el-select>
                    <div class="room-detail" v-if="currentRoomType">
                      <i class="el-icon-files"> 床位数:{{ roomTypeMap[currentRoomType].bed }}</i><br>
                      <i class="el-icon-user"> 最大容纳:{{ roomTypeMap[currentRoomType].maxLoad }}</i><br>
                      <i class="el-icon-picture-outline"> 是否有窗:{{
                          roomTypeMap[currentRoomType].haveWindow == 1 ? "是" : "否"
                        }}</i><br>
                      <i class="el-icon-fork-spoon"> 是否含早餐:{{
                          roomTypeMap[currentRoomType].haveBreakfast == 1 ? "是" : "否"
                        }}</i><br>
                      <i class="el-icon-magic-stick"> 是否有网络:{{
                          roomTypeMap[currentRoomType].network == 1 ? "是" : "否"
                        }}</i>
                    </div>
                    <div class="mt-10">
                      <el-select style="width: 100%" v-model="provinceVal" :placeholder="$t('hotelList.province')">
                        <el-option
                            v-for="item in options"
                            :label="item.label"
                            :value="item.value">
                        </el-option>
                      </el-select>
                    </div>
                    <div>
                      <el-button @click="confirmOrderHandle" type="primary" style="width: 100%;margin: 1rem 0">
                        {{ $t('hotelDetails.reserveBtn') }}
                      </el-button>
                    </div>
                  </div>
                </div>

                <!-- 自申报 -->
                <div v-show="switchType == false" class="abnormal">

                  <span v-show="situation == 2 || situation == 3" class="price-sum mb-10">
                    <span>
                      <span class="unit">¥</span>
                      <span class="zg-price">
                        <strong>

                          <!-- <span v-if="!bookDay || !totalFee">{{ minFee + '~' + maxFee }}</span> -->
                          <span>{{ isolateFee }}</span>
                        </strong>
                      </span>
                    </span>
                  </span>

                  <div class="block mb-10">
                    <el-date-picker
                        v-model="date"
                        type="date"
                        :placeholder="$t('hotelDetails.checkDate')"
                        :picker-options="pickerOptions">
                    </el-date-picker>
                  </div>

                  <!-- 申报类型 -->
                  <el-select class="mb-10" v-model="situation" :placeholder="$t('hotelDetails.type')">
                    <el-option
                        v-for="item in situationOptions"
                        :key="item.value"
                        :label="item.label"
                        :value="item.value">
                    </el-option>
                  </el-select>

                  <!-- 选择房型 -->
                  <el-select v-show="situation == 2 || situation == 3" class="mb-10" v-model="isolateRoomType"
                             :placeholder="$t('hotelList.selectRoom')">
                    <el-option
                        v-for="item in isolateRoomTypeList"
                        :label="item.name"
                        :value="item.id">
                      <span style="float: left">{{ item.name }}</span>
                      <span style="float: right; color: #8492a6; font-size: 13px">￥{{ item.isolationFee }}</span>
                    </el-option>
                  </el-select>

                  <el-input class="mb-10" v-model="userName" :placeholder="$t('hotelDetails.name')"></el-input>

                  <el-input class="mb-10" v-model="userId" :placeholder="$t('hotelDetails.idNum')"></el-input>

                  <el-input class="mb-10" v-model="phoneNum" :placeholder="$t('hotelDetails.tel')"></el-input>

                  <el-input class="mb-10" v-model="userEmail" :placeholder="$t('hotelDetails.email')"></el-input>

                  <!-- 陪同人员 start -->
                  <!-- <div class="mb-10">
                    <el-switch                    
                      active-text="是否需要陪同人员"
                      v-model="accompany"
                      active-color="#13ce66"
                      inactive-color="#ff4949">
                    </el-switch>

                    <el-tooltip placement="right">
                      <div slot="content">根据规定，<br/>14岁以下儿童，<br/>岁以上老人可申请陪同</div>        
                      <el-button style="border-radius: 50%;padding: 5px 8px;margin-left: 10px;">?</el-button>
                    </el-tooltip>                    
      
                  </div>               

                  <el-input v-show="accompany == true" class="mb-10" v-model="accompanyName" placeholder="陪同人员姓名"></el-input>

                  <el-input v-show="accompany == true" class="mb-10" v-model="accompanyId" placeholder="陪同人员身份证"></el-input> -->
                  <!-- 陪同人员 end -->

                  <div>
                    <el-cascader
                        class="mb-10"
                        size="large"
                        :options="addressData"
                        :v-model="area"
                        @change="getAddress"
                    ></el-cascader>
                  </div>

                  <p class="mb-10 font-16"><i class="el-icon-warning-outline mr-5"></i>{{ $t('hotelDetails.tips') }}</p>

                  <el-button class="w-percent-100" @click="submit" type="primary">{{
                      $t('hotelDetails.submitBtn')
                    }}
                  </el-button>


                </div>

              </div>
            </div>
          </div>


          <div class="app-booking-btn">
            <el-button style="box-shadow: 0px 0px 40px -10px #000;" class="w-percent-100" @click="appBooking = true"
                       type="primary">{{ $t('hotelDetails.reserveBtn') }}
            </el-button>
          </div>

          <van-popup v-model="appBooking" position="bottom" :style="{ height: '75%',boxSizing: 'border-box' }">

            <el-switch
                style="display: block;padding: 16px;"
                v-model="switchType"
                active-color="#13ce66"
                inactive-color="#ff4949"
                :active-text="$t('hotelDetails.reserve')"
                :inactive-text="$t('hotelDetails.declare')">
            </el-switch>

            <!-- app 预定 -->
            <div v-show="switchType" class="app-booking-main">
              <!-- 表单主体 -->
              <div class="app-booking-form">

                <!-- 选择开始时间 Start -->
                <van-field
                    v-model="appDateStart"
                    :label="$t('hotelDetails.startDate')"
                    :placeholder="$t('common.selectTips')"
                    input-align="right"
                    readonly
                    right-icon="arrow"
                    @click="showAppDateStart = true"/>

                <van-popup v-model="showAppDateStart" position="bottom">
                  <van-datetime-picker
                      v-model="startCurrentDate"
                      :min-date="minDate"
                      @cancel="startCancelDate"
                      @confirm="startConfirmDate"
                      type="date"
                  />
                </van-popup>
                <!-- 选择开始时间 End -->

                <!-- 选择结束时间 Start -->
                <van-field
                    v-model="appDateEnd"
                    :label="$t('hotelDetails.endDate')"
                    :placeholder="$t('common.selectTips')"
                    input-align="right"
                    readonly
                    right-icon="arrow"
                    @click="showAppDateEnd = true"/>

                <van-popup v-model="showAppDateEnd" position="bottom">
                  <van-datetime-picker
                      v-model="endCurrentDate"
                      :min-date="minDate"
                      @cancel="endCancelDate"
                      @confirm="endConfirmDate"
                      type="date"
                  />
                </van-popup>
                <!-- 选择结束时间 End -->

                <!-- 选择房型 Start -->
                <van-field
                    v-model="appCurrentRoomType"
                    :label="$t('hotelList.selectRoom')"
                    :placeholder="$t('common.selectTips')"
                    input-align="right"
                    readonly
                    right-icon="arrow"
                    @click="showRoomType = true"/>

                <van-popup v-model="showRoomType" position="bottom">
                  <van-picker
                      show-toolbar
                      :columns="roomTypeList"
                      value-key="name"
                      @confirm="roomTypeConfirm"
                      @cancel="roomTypeCancel"
                  />
                </van-popup>
                <!-- 选择房型 End -->

                <!-- 选择省份 Start -->
                <van-field
                    v-model="appCurrentProvince"
                    :label="$t('hotelList.province')"
                    :placeholder="$t('common.selectTips')"
                    input-align="right"
                    readonly
                    right-icon="arrow"
                    @click="showProvince = true"/>

                <van-popup v-model="showProvince" position="bottom">
                  <van-picker
                      show-toolbar
                      :columns="options"
                      value-key="label"
                      @confirm="provinceConfirm"
                      @cancel="provinceCancel"
                  />
                </van-popup>
                <!-- 选择省份 End -->


              </div>

              <!-- 按钮 -->
              <div class="mb-15 text-center">
                <el-button style="width:60%" @click="confirmOrderHandle" type="primary">{{
                    $t('common.confirm')
                  }}
                </el-button>
              </div>

            </div>

            <!-- app 自申报 -->
            <div v-show="switchType == false" class="app-booking-main">
              <!-- 表单主体 -->
              <div class="app-booking-form">

                <van-field v-model="userName" :label="$t('hotelDetails.name')"/>
                <van-field v-model="userId" :label="$t('hotelDetails.idNum')"/>
                <van-field v-model="phoneNum" type="tel" :label="$t('hotelDetails.tel')"/>
                <van-field v-model="userEmail" :label="$t('hotelDetails.email')"/>

                <!-- 入住时间 Start -->
                <van-field
                    v-model="appDate"
                    :label="$t('hotelDetails.checkDate')"
                    :placeholder="$t('common.selectTips')"
                    input-align="right"
                    readonly
                    right-icon="arrow"
                    @click="showAppDate = true"/>

                <van-popup v-model="showAppDate" position="bottom">
                  <van-datetime-picker
                      v-model="currentDate"
                      :min-date="minDate"
                      @cancel="cancelDate"
                      @confirm="confirmDate"
                      type="date"
                  />
                </van-popup>
                <!-- 入住时间 End -->


                <!-- 申报类型 Start -->
                <van-field

                    v-model="appSituation"
                    :label="$t('hotelDetails.type')"
                    :placeholder="$t('common.selectTips')"
                    input-align="right"
                    readonly
                    right-icon="arrow"
                    @click="showSituation = true"/>

                <van-popup v-model="showSituation" position="bottom">
                  <van-picker
                      show-toolbar
                      :columns="situationOptions"
                      value-key="label"
                      @confirm="situationConfirm"
                      @cancel="situationCancel"
                  />
                </van-popup>
                <!-- 申报类型 End -->

                <!-- 选择房型 Start -->
                <van-field
                    v-show="situation == 2 || situation == 3"
                    v-model="appCurrentRoomType1"
                    :label="$t('hotelList.selectRoom')"
                    :placeholder="$t('common.selectTips')"
                    input-align="right"
                    readonly
                    right-icon="arrow"
                    @click="showAppRoomType = true"/>

                <van-popup v-model="showAppRoomType" position="bottom">
                  <van-picker
                      show-toolbar
                      :columns="isolateRoomTypeList"
                      value-key="name"
                      @confirm="appRoomTypeConfirm"
                      @cancel="appRoomTypeCancel"
                  />
                </van-popup>
                <!-- 选择房型 End -->

                <!-- 选择省份 Start -->
                <van-field
                    v-model="appCurrentProvince1"
                    :label="$t('hotelList.province')"
                    :placeholder="$t('common.selectTips')"
                    input-align="right"
                    readonly
                    right-icon="arrow"
                    @click="showProvince1 = true"/>

                <van-popup v-model="showProvince1" position="bottom">
                  <van-picker
                      show-toolbar
                      :columns="addressData"
                      value-key="label"
                      @confirm="provinceConfirm1"
                      @cancel="provinceCancel1"
                  />
                </van-popup>
                <!-- 选择省份 End -->


              </div>

              <!-- 按钮 -->
              <div class="mb-15 text-center">
                <el-button style="width:60%" type="primary" @click="submit">{{ $t('common.confirm') }}</el-button>
              </div>

            </div>


          </van-popup>

        </div>

      </div>
      <Footer></Footer>

    </div>

    <el-dialog
        :title="$t('hotelDetails.confirmOrder')"
        :visible.sync="confirmOrderVisible"
        width="95%"
        class="confirm-order-dialog"
    >
      <div class="order-box flex flex-column">
        <div class="order-hotel-detail-box flex flex-row">
          <el-image
              style="width: 150px; height: 100px"
              :src="hotelDetails.cover"
              fit="fit"></el-image>
          <div class="order-hotel-detail-detail flex flex-column">
            <p class="mb-5" style="font-weight: 700;font-size: 16px">{{ hotelDetails.name }}</p>
            <p class="mt-5 mb-5">{{ roomTypeMap[currentRoomType] ? roomTypeMap[currentRoomType].name : '' }}</p>
            <p class="mb-5">{{ dateValue[0] + ' -- ' + dateValue[1] + ', 共' + bookDay + '晚' }}</p>
            <p>{{ '来自: ' + provinceVal }}</p>
          </div>
        </div>

        <div class="mt-5 mb-5" style="border-top: 1px dashed #999;width: 100%"></div>

        <div v-if="confirmOrderData.hotelDiscountList.length != 0 || confirmOrderData.personalDiscountList.length != 0"
             class="order-choose-discount flex flex-column">
          <el-collapse>
            <el-collapse-item v-if="confirmOrderData.hotelDiscountList.length != 0" title="酒店优惠券" name="1">
              <div class="choose-hotel-discount">
                <div class="choose-discount-box flex flex-row" v-for="i in confirmOrderData.hotelDiscountList"
                     @click="addUseDiscount(i.id)">
                  <div :class="confirmOrderData.useDiscountMap[i.id].use ? 'choose-discount-chosen' : ''"
                       class="choose-discount-left flex flex-row justify-content-between align-items-end">
                    <div class="choose-discount-name">
                      {{ i.name }}
                      <el-tooltip placement="right" style="margin: 4px 4px 4px 0px;">
                        <div slot="content">{{ i.description }}</div>
                        <el-button
                            style="border-radius: 50%;padding: 2px 7px;margin-left: 0px;font-weight: 800;color: #999">!
                        </el-button>
                      </el-tooltip>
                    </div>
                    <div class="choose-discount-date">
                      有效期至 {{ i.validityTime | dateTimeFormat }}
                    </div>
                  </div>
                  <div style="border-left: 1px dashed #999;height: 100%"></div>
                  <div :class="confirmOrderData.useDiscountMap[i].use ? 'choose-discount-chosen' : ''"
                       class="choose-discount-right flex align-items-center justify-content-center">
                    {{ i.discountsType == 0 ? i.discounts + '元' : i.discounts * 10 + '折' }}
                  </div>
                </div>
              </div>
            </el-collapse-item>

            <el-collapse-item v-if="confirmOrderData.personalDiscountList.length != 0"
                              :title="$t('hotelDetails.coupon')" name="1">
              <div class="choose-hotel-discount">
                <!-- 获取list -->
                <div
                    :class="[i.canUse ? '': 'disable-selected',confirmOrderData.useDiscountMap.has(i.id) ? 'choose-discount-chosen' : '']"
                    :title="i.cantUseReason" class="choose-discount-box flex flex-row cursor"
                    v-for="i in confirmOrderData.personalDiscountList"
                    @click="i.canUse && addUseDiscount(i)">
                  <div class="choose-discount-left flex flex-row justify-content-between align-items-end">
                    <div class="choose-discount-name">
                      {{ i.name }}
                      <el-tooltip placement="right" style="margin: 4px 4px 4px 0px;">
                        <div slot="content">{{ i.description }}</div>
                        <el-button
                            style="border-radius: 50%;padding: 2px 7px;margin-left: 0px;font-weight: 800;color: #999">!
                        </el-button>
                      </el-tooltip>
                    </div>
                    <div class="choose-discount-date">
                      有效期至 {{ i.validityTime | dateTimeFormat }}
                    </div>
                  </div>
                  <div style="border-left: 1px dashed #999;height: 100%"></div>
                  <!-- <div :class="confirmOrderData.useDiscountMap.has(i.id) ? 'choose-discount-chosen' : ''"
                       class="choose-discount-right flex align-items-center justify-content-center"> -->
                  <div class="choose-discount-right flex align-items-center justify-content-center">
                    {{ i.discountsType == 0 ? i.discounts + '元' : i.discounts * 10 + '折' }}
                  </div>
                </div>
              </div>
            </el-collapse-item>

          </el-collapse>

          <div class="mt-5 mb-5" style="border-top: 1px dashed #999;width: 100%"></div>


        </div>
        <div class="order-count">
          <div class="order-count-item">
            <div>{{ $t('hotelDetails.unitPrice') }}</div>
            <div>￥{{ this.confirmOrderData.totalFee / bookDay }}</div>
          </div>
          <div class="order-count-item">
            <div>{{ $t('hotelDetails.price') }}</div>
            <div>￥{{ this.confirmOrderData.totalFee }}</div>
          </div>
          <div class="order-count-item" v-for="i in discountVisibleList">
            <div>{{ i.name }}</div>
            <div>-￥{{ i.price }}</div>
          </div>
          <div class="order-count-item" style="font-size: 18px;color: black">
            <div>{{ $t('hotelDetails.spend') }}</div>
            <div style="color: #ff4d6a;">￥{{ this.confirmOrderData.totalFee - this.confirmOrderData.discountFee }}</div>
          </div>
        </div>
      </div>

      <span slot="footer" class="dialog-footer">
        <el-button @click="confirmOrderVisible = false">{{ $t('common.cancel') }}</el-button>
        <el-button type="primary" @click="confirm">{{ $t('common.confirm') }}</el-button>
      </span>
    </el-dialog>

    <el-dialog
        class="wallet-dialog"
        :title="$t('hotelDetails.payTitle')"
        :visible.sync="payVisible"
        width="95%"
        center
    >
      <div class="flex flex-column pay-box align-items-center">
        <div>{{ $t('hotelDetails.payment') }}</div>
        <div class="pay-box-price"><span style="font-size: 26px">￥</span>{{ payForm.lastPay }}</div>
        <div class="flex flex-row align-items-center">
          <div>其他支付方式：</div>
          <div class="other-pay-item" @click="payForAlipay">
            <svg t="1658196429841" class="icon" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg"
                 p-id="1181" width="36" height="36">
              <path
                  d="M896 650.666667l-247.04-83.072s18.986667-28.416 39.253333-84.138667c20.266667-55.722667 23.168-86.314667 23.168-86.314667l-159.914666-1.28V341.162667l193.706666-1.365334V301.226667h-193.706666V213.333333H456.533333v87.893334H275.882667v38.613333l180.693333-1.28v58.581333H311.637333v30.592h298.325334s-3.285333 24.832-14.72 55.722667a1254.485333 1254.485333 0 0 1-23.210667 57.941333s-140.074667-49.024-213.888-49.024-163.584 29.653333-172.288 115.712c-8.661333 86.016 41.813333 132.608 112.938667 149.76 71.125333 17.237333 136.789333-0.170667 193.962666-28.16 57.173333-27.946667 113.28-91.477333 113.28-91.477333l287.914667 139.818667A142.08 142.08 0 0 1 753.792 896H270.208A142.08 142.08 0 0 1 128 754.048V270.208A142.08 142.08 0 0 1 269.952 128h483.84A142.08 142.08 0 0 1 896 269.952v380.714667z m-360.064-48.128s-89.856 113.493333-195.754667 113.493333c-105.941333 0-128.170667-53.930667-128.170666-92.714667 0-38.741333 22.016-80.853333 112.170666-86.954666 90.069333-6.101333 211.84 66.176 211.84 66.176h-0.085333z"
                  fill="#02A9F1" p-id="1182"></path>
            </svg>
          </div>
          <div class="other-pay-item">
            <svg t="1658197773699" class="icon" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg"
                 p-id="1325" width="36" height="36">
              <path
                  d="M395.861333 585.941333a27.818667 27.818667 0 0 1-12.8 2.858667 28.16 28.16 0 0 1-24.789333-14.250667l-2.005333-3.797333L278.186667 407.04c-0.981333-1.962667-0.981333-3.797333-0.981334-5.717333 0-7.637333 5.930667-13.354667 13.866667-13.354667 2.986667 0 5.930667 0.938667 8.874667 2.858667l91.989333 62.848c6.869333 3.754667 14.805333 6.613333 23.68 6.613333 4.992 0 9.856-0.896 14.848-2.858667l431.232-184.661333C784.554667 185.173333 657.066667 128 512.554667 128 277.205333 128 85.333333 281.301333 85.333333 470.656c0 102.784 57.344 196.053333 147.328 258.901333a27.818667 27.818667 0 0 1 11.861334 21.930667 25.386667 25.386667 0 0 1-2.005334 8.533333c-6.912 25.685333-18.858667 67.626667-18.858666 69.504a33.706667 33.706667 0 0 0-2.005334 10.453334c0 7.68 5.973333 13.354667 13.866667 13.354666 2.986667 0 5.930667-0.938667 7.936-2.858666l92.928-52.352c6.869333-3.754667 14.805333-6.613333 22.741333-6.613334 3.925333 0 8.917333 0.896 12.8 1.92 43.52 12.373333 91.050667 19.072 139.52 19.072C746.794667 812.501333 938.666667 659.2 938.666667 469.845333c0-57.173333-17.792-111.36-48.469334-159.018666L398.805333 584.021333l-2.986666 1.92z"
                  fill="#09BB07" p-id="1326"></path>
            </svg>
          </div>
          <div class="other-pay-item">
            <svg t="1658197795473" class="icon" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg"
                 p-id="1469" width="36" height="36">
              <path
                  d="M211.370667 214.485333c-26.069333 3.285333-52.138667 24.874667-60.672 49.706667-5.674667 15.445333-107.52 454.229333-107.946667 462.165333-0.981333 22.997333 12.288 39.850667 34.048 44.074667 8.106667 1.877333 266.709333 1.877333 275.242667 0 24.618667-4.693333 47.872-23.893333 56.362666-47.36 3.328-8.874667 108.501333-456.106667 108.501334-463.573333 1.408-21.077333-11.392-38.442667-31.744-44.544-4.736-0.938667-262.912-1.877333-273.792-0.426667z"
                  fill="#E60012" p-id="1470"></path>
              <path
                  d="M452.48 214.485333c-26.026667 3.285333-52.096 24.874667-60.629333 49.706667-5.205333 15.445333-107.52 454.229333-107.52 462.165333-0.981333 22.997333 12.288 39.850667 34.090666 44.074667 8.064 1.877333 266.666667 1.877333 275.2 0 24.661333-4.693333 47.872-23.893333 56.405334-47.36 3.285333-8.874667 108.458667-456.106667 108.458666-463.573333 1.450667-21.077333-11.349333-38.442667-31.744-44.544-5.205333-0.938667-263.381333-1.877333-274.261333-0.426667z"
                  fill="#00508E" p-id="1471"></path>
              <path
                  d="M704 214.485333c-26.026667 3.285333-52.053333 24.874667-60.586667 49.706667-5.248 15.445333-107.562667 454.229333-107.562666 462.165333-0.938667 22.997333 12.330667 39.850667 34.133333 44.074667 8.021333 1.877333 200.362667 1.877333 208.896 0 24.618667-4.693333 47.829333-23.893333 56.32-47.36 3.370667-8.874667 108.501333-456.106667 108.501333-463.573333 1.450667-21.077333-11.349333-38.442667-31.744-44.544-4.693333-1.408-220.714667-2.346667-231.637333-0.938667h23.68v0.469333z"
                  fill="#00908C" p-id="1472"></path>
              <path
                  d="M203.306667 356.992a30.72 30.72 0 0 1-1.408 6.101333c-10.922667 36.565333-21.802667 85.76-20.394667 91.861334 3.797333 18.304 30.805333 16.896 40.277333-2.346667 2.858667-5.12 23.210667-90.922667 23.210667-97.024 0-0.426667 32.213333 0 32.213333 0.469333 0 0 0 1.408-0.469333 2.816a1510.4 1510.4 0 0 0-11.392 44.544c-12.8 49.664-14.208 54.826667-20.821333 64.682667-21.76 31.872-94.72 30.933333-100.437334-1.408-1.408-7.04 14.208-88.106667 21.333334-110.165333 0-1.408 37.888-0.426667 37.888 0.469333z m484.138666 0c21.76 4.693333 28.416 22.016 18.474667 47.786667-9.941333 25.813333-31.274667 37.077333-68.693333 37.077333-11.392 0-10.410667-1.877333-17.493334 33.28-1.450667 6.528-2.858667 12.629333-2.858666 13.568-0.512 2.346667-34.133333 2.346667-33.664 0 28.416-120.448 30.805333-130.773333 30.805333-131.712l0.469333-1.408h34.133334c26.965333 0.469333 34.56 0.469333 38.826666 1.408z m-292.266666 46.421333c4.693333 0.938667 8.021333 4.693333 8.021333 7.978667 0 11.690667-24.149333 19.669333-33.152 10.752-8.533333-8.917333 9.472-22.485333 25.130667-18.773333z m-83.882667 8.874667c0 0.938667-0.426667 2.816-0.426667 4.266667l-0.512 1.834666 5.674667-2.816c15.189333-7.466667 29.866667-6.058667 34.56 3.285334 2.858667 5.632 2.389333 8.96-5.162667 43.605333-1.450667 6.101333-3.328 14.506667-3.84 18.773333-1.877333 9.386667-0.426667 8.874667-17.493333 8.874667-14.677333 0-14.677333 0-14.208-1.408 0-0.938667 1.877333-8.405333 3.797333-17.322667 7.552-33.28 8.064-37.973333 1.408-37.973333-3.797333 0-8.96 2.346667-9.472 3.754667a1420.16 1420.16 0 0 0-9.941333 47.786666l-0.938667 4.266667-14.72 0.896c-17.962667 0.469333-16.554667 1.877333-12.8-14.08 5.248-20.608 8.106667-35.114667 9.984-48.213333 2.346667-14.08 0.938667-12.714667 14.208-14.592 6.144-0.938667 12.330667-1.834667 14.208-2.304 4.736-1.408 5.674667-0.938667 5.674667 1.365333z m225.024-0.426667c0 0.896-0.469333 2.773333-0.469333 4.181334l-0.469334 2.346666 5.674667-2.816c29.354667-14.976 40.746667-2.816 32.213333 35.626667-1.877333 8.448-4.266667 20.650667-5.674666 26.282667a49.749333 49.749333 0 0 1-2.858667 11.690666c-1.877333 1.877333-29.866667 1.408-29.354667 0 0-0.938667 1.877333-8.405333 3.797334-16.853333 8.021333-34.218667 8.021333-38.442667 0.938666-38.442667-5.674667 0-8.533333 1.877333-9.472 6.101334-1.408 5.12-8.533333 38.912-9.472 44.970666l-0.938666 5.162667-14.72 0.469333c-17.962667 0.469333-16.554667 2.346667-12.288-14.976 4.693333-18.773333 8.064-36.096 10.410666-48.768 1.877333-12.202667 0.469333-10.794667 12.330667-12.672 5.205333-0.938667 11.818667-1.877333 14.208-2.304 4.693333-2.346667 6.144-1.877333 6.144 0z m287.573333-0.469333c1.877333 28.586667 2.346667 36.992 2.346667 37.461333 0 0.469333 4.266667-6.997333 8.96-16.384 9.514667-18.773333 7.637333-16.896 18.048-18.304 2.816-0.426667 8.533333-1.408 12.8-2.304 10.410667-1.877333 10.410667-2.816-1.450667 17.792-16.085333 27.648-38.357333 66.56-46.421333 80.64-24.149333 43.562667-24.149333 43.562667-44.501333 44.032l-12.373334 0.469334 0.981334-3.285334c0.469333-1.834667 1.92-5.589333 2.389333-8.405333l1.408-5.162667h3.797333c4.266667 0 5.205333-0.938667 8.96-7.509333 1.450667-2.346667 3.84-6.101333 4.778667-8.448 1.408-2.346667 6.144-9.813333 9.941333-17.322667l7.594667-13.141333-1.92-17.322667a1133.781333 1133.781333 0 0 0-6.613333-51.114666c-0.981333-6.570667-0.981333-6.570667 7.552-7.509334 3.84-0.426667 9.941333-1.877333 13.269333-2.346666 9.002667-2.773333 9.941333-2.773333 10.410667-1.834667z m-357.205333 0.426667c36.48 6.570667 23.68 69.376-16.085333 78.293333-27.008 6.101333-45.482667-4.224-45.482667-24.832 0.426667-36.096 27.477333-59.52 61.568-53.418667z m272.853333 1.877333a28.458667 28.458667 0 0 1 6.144 4.266667c2.389333 2.304 2.389333 2.304 2.389334 0.896 0.469333-1.877333 0-1.877333 18.944-4.693334 15.146667-2.304 14.677333-2.304 13.738666 1.877334a3313.322667 3313.322667 0 0 0-13.738666 60.501333c-3.328 16.853333-0.938667 14.976-19.413334 14.506667h-15.658666v-1.877334c0-1.877333-0.938667-2.816-1.877334-1.408-5.205333 8.448-30.805333 5.162667-37.888-5.12-17.493333-26.282667 19.882667-81.578667 47.36-68.949333z m-340.138666 13.653333s0 2.304-0.426667 4.181334c-3.84 15.018667-10.453333 45.482667-11.861333 51.072l-1.450667 7.04-15.616 0.469333c-18.474667 0.469333-17.493333 1.408-13.738667-9.813333 3.328-10.794667 6.613333-23.893333 8.533334-37.973334 1.877333-12.202667 0-10.325333 13.738666-12.202666 6.144-0.938667 12.8-1.877333 14.208-2.346667 3.797333-0.938667 6.144-0.938667 6.613334-0.426667z m82.901333 96.981334a19.285333 19.285333 0 0 1-2.346667 4.693333c-0.938667 2.346667-2.389333 4.224-2.389333 4.224 27.946667 0.426667 28.928 0.426667 28.416 1.877333l-5.205333 16.853334h-40.704l-2.389334 1.877333c-5.205333 4.693333-32.682667 10.794667-32.682666 7.04l5.205333-16.853333h3.797333c6.613333 0 8.064-1.408 13.738667-11.264l4.693333-8.917334c24.661333-0.426667 29.866667 0 29.866667 0.469334z m62.08 0a29.269333 29.269333 0 0 1-1.450667 5.632 20.992 20.992 0 0 0-1.408 5.12s2.389333-0.896 5.205334-3.242667c10.453333-7.04 19.456-8.448 45.952-8.448 10.453333 0 19.413333 0 19.925333 0.469333 0.426667 0.938667-15.189333 51.584-17.536 56.277334a23.808 23.808 0 0 1-11.861333 11.690666l-4.693334 1.877334-27.050666 0.469333-26.965334 0.469333-4.778666 15.957334c-9.472 30.464-9.472 28.117333 4.266666 26.709333 10.922667-0.938667 10.453333-1.877333 7.125334 9.386667l-2.858667 9.386666h-13.738667c-29.866667 0.426667-33.621333-1.450667-30.762666-13.653333 1.408-6.058667 35.498667-117.632 35.968-118.570667 0.512-0.426667 24.661333-0.426667 24.661333 0.469334z m124.586667 0c0 0.469333-0.469333 1.877333-0.938667 3.754666-1.450667 4.693333-1.450667 4.693333 4.693333 1.408 8.106667-4.224 27.050667-5.632 65.877334-5.632h12.330666v5.632c0 6.570667 0.426667 7.04 6.144 7.978667l4.266667 0.426667-2.389333 8.490666-2.346667 8.405334h-8.533333c-21.76 0.469333-25.088-1.877333-25.6-14.506667v-6.101333l-1.408 4.224-1.408 4.693333h-4.736c-2.389333 0-4.736 0-4.736 0.426667l-26.538667 87.210666c-0.469333 0.938667 0 1.408 2.858667 1.408 4.266667 0 4.266667 0 2.816 3.754667-1.408 4.224-1.408 4.224 3.328 4.224a13.056 13.056 0 0 0 7.594666-1.877333c3.285333-1.877333 3.285333-1.408 18.474667-22.485334l6.144-8.96h-12.8c-15.616 0-14.208 0.981333-11.349333-8.405333l2.346666-7.509333h31.274667c2.858667-9.813333 3.797333-12.629333 3.797333-13.098667 0-0.469333-6.656-0.469333-15.146666-0.469333h-15.189334l4.736-16.896h42.666667c23.210667 0 42.624 0 42.624 0.469333s-0.938667 4.266667-2.389333 8.448l-2.346667 7.978667-14.208 0.426666-14.208 0.512c-2.389333 6.997333-3.328 10.282667-3.84 11.221334l-0.426667 1.877333h13.738667c16.085333 0 15.146667-0.938667 11.818667 8.448l-2.346667 7.509333h-31.274667l-4.736 5.632h12.330667l1.877333 11.221334c1.877333 12.672 1.877333 12.672 8.064 12.672 4.693333 0 4.693333-0.938667 1.408 10.325333l-2.858666 9.386667h-8.96c-15.658667 0-18.517333-2.389333-21.333334-18.304l-1.408-10.325334-5.717333 7.509334c-15.616 21.077333-16.554667 21.546667-36.48 21.546666-12.8 0-12.8 0-10.88-3.754666 0.469333-1.834667 0.469333-1.834667-3.328-1.834667s-3.797333 0-4.693333 2.773333l-0.512 2.816h-26.538667l0.469333-1.408c1.450667-4.693333 3.84-4.181333-25.088-4.181333-25.088 0-26.538667 0-26.026666-1.408l2.346666-8.448c2.858667-8.448 2.346667-8.448 5.205334-8.448 2.389333 0 2.389333 0 3.328-3.285333l30.293333-100.266667 1.92-6.144h13.226667c8.533333 0 14.72 0.469333 14.72 0.938667z m-168.192 40.32l-5.205334 16.426666h-28.416a137.386667 137.386667 0 0 1-4.266666 13.098667c-0.469333 1.408 0.469333 1.408 14.208 1.408 8.064 0 14.677333 0.469333 14.677333 0.469333 0 0.469333-0.426667 1.408-0.938667 2.816-0.426667 0.938667-1.408 4.693333-2.346666 8.448l-1.92 6.101334h-29.354667l-3.328 11.221333c-4.693333 15.957333-4.266667 16.426667 13.738667 14.08 7.594667-0.938667 7.125333-1.877333 3.797333 9.386667l-2.858667 9.386666h-19.882666c-31.274667 0-31.744-0.981333-23.210667-27.690666 2.389333-8.448 4.736-15.445333 4.736-15.445334s-3.328-0.469333-8.064-0.469333c-4.266667 0-8.021333 0-8.021333-0.469333 3.285333-11.733333 4.693333-15.488 4.693333-16.426667 0.512-0.938667 1.450667-1.408 8.533333-1.408h8.106667l3.754667-14.506667h-7.594667c-5.674667 0-7.552 0-7.552-0.938666s4.266667-14.506667 4.693333-15.957334c0.981333-0.938667 72.533333-0.426667 72.021334 0.469334z m91.904 33.749333c0 0.938667-0.938667 3.754667-1.408 6.101333-2.858667 13.141333-6.613333 15.914667-21.76 16.853334l-9.984 0.938666c-0.938667 1.408-0.938667 12.672 0 14.549334l1.408 1.877333 9.472-0.469333c5.248-0.469333 9.514667-0.469333 9.514666-0.469334 0 0.938667-5.248 17.322667-5.717333 17.792-0.938667 0.938667-29.866667 0.469333-33.621333-0.938666-5.674667-1.877333-5.674667-1.408-5.205334-30.933334l0.469334-25.770666h24.149333v9.813333h4.736c5.205333 0 5.674667-0.426667 8.064-6.997333l1.408-3.754667h9.472c8.533333 0.469333 9.472 0.469333 9.002667 1.408z m44.544-200.618667l-5.205334 22.485334h7.082667c36.48 0.938667 51.626667-40.789333 16.128-43.562667l-11.392-0.938667c-1.408 0-1.408 1.408-6.613333 22.016z m-188.544 31.872c-8.96 3.754667-17.493333 37.504-10.410667 42.666667 5.205333 4.224 12.8-2.816 16.554667-14.506667 6.186667-21.589333 4.266667-31.914667-6.144-28.16z m282.325333 2.346667c-9.472 4.693333-16.554667 37.973333-9.472 41.728 9.472 5.12 21.802667-12.672 21.802667-31.872 0-8.96-5.205333-13.141333-12.330667-9.856z m-191.36 111.573333l-2.389333 8.405334c-1.408 4.266667-2.346667 7.978667-2.816 8.448 0 0.469333 2.346667-0.426667 5.674666-2.346667 9.472-4.693333 12.330667-5.632 26.026667-6.101333l11.861333-0.426667c1.92-5.632 2.389333-7.978667 2.389334-8.448 0.426667-0.469333-40.277333-0.469333-40.746667 0.426667z m-8.064 27.178667l-1.877333 7.509333 40.704-0.469333 2.389333-7.509333c-32.213333 0-41.216 0-41.216 0.426666z m128.853333-16.896c-1.877333 6.101333-3.328 10.794667-2.858666 10.794667l5.717333-1.877334c2.816-0.938667 8.021333-2.346667 10.88-2.816 2.858667-0.426667 5.205333-0.938667 5.674667-0.938666 0 0 4.736-14.506667 4.736-14.976 0 0-4.693333-0.469333-10.410667-0.469334h-10.410667l-3.328 10.282667z m-7.594666 23.893333c0 0.512-1.877333 5.205333-3.285334 10.794667-1.92 5.632-3.328 10.794667-3.328 10.794667 0 0.469333 2.346667-0.426667 5.674667-1.877334 3.328-1.408 8.064-2.816 10.922667-3.285333 6.144-0.938667 6.613333-1.408 7.082666-3.754667 0.469333-0.938667 1.408-4.693333 2.389334-7.466666l1.877333-5.632h-10.410667c-5.674667 0-10.922667 0-10.922666 0.426666z m-16.554667 53.930667l20.821333 0.469333c4.266667-14.08 5.717333-18.773333 5.717334-19.2l-20.864-0.938666-5.674667 19.669333z"
                  fill="#FFFFFF" p-id="1473"></path>
            </svg>
          </div>
<!--          <el-button @click="alipayCheck">支付后按此按钮</el-button>-->
        </div>
        <div @click="alipayCheck" class="pay-check-btn mb-15">支付成功没有跳转?点击这里</div>
        <el-input :placeholder="$t('login.passwordTips')" v-model="payForm.walletPwd" show-password></el-input>
      </div>
      <span slot="footer" class="dialog-footer">
        <el-button type="primary" @click="pay">{{ $t('common.confirm') }}</el-button>
      </span>

    </el-dialog>


  </div>
</template>

<script>

import TopNav from '../components/TopNav'
import Footer from '../components/Footer.vue';
import {formDataPost, get, post} from "../utils/request";
import {CodeToText, provinceAndCityData} from 'element-china-area-data'
import {Popup, DatetimePicker, Picker, Field} from 'vant';
import 'vant/lib/index.css';

export default {
  name: "HotelDetails",
  components: {
    TopNav,
    Footer,
    [Popup.name]: Popup,
    [DatetimePicker.name]: DatetimePicker,
    [Picker.name]: Picker,
    [Field.name]: Field,
  },
  data() {
    return {
      circleUrl: 'https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png',
      url: 'https://fuss10.elemecdn.com/e/5d/4a731a90594a4af544c0c25941171jpeg.jpeg',


      hotelBadgeList: [],
      showAppRoomType: false,
      appCurrentRoomType1: '',
      appSituation: '',
      showSituation: false,
      appDate: '',
      showAppDate: false,
      currentDate: new Date(),
      appCurrentProvince: '',
      appCurrentProvince1: '',
      showProvince: false,
      showProvince1: false,
      showRoomType: false,
      appDateEnd: '',
      showAppDateEnd: false,
      endCurrentDate: new Date(),

      appDateStart: '',
      showAppDateStart: false,
      startCurrentDate: new Date(),

      minDate: new Date(),

      // appSwitchType: true,
      appBooking: false,
      smallPagination: false,
      circleUrlSize: 50,
      hotelCommentsList: [],
      hotelCommentsTotal: 0,
      couponHeight: '',
      coupon: false,
      commentHeight: '',
      comment: false,
      dynamicWidth: '',
      colors: ['#99A9BF', '#F7BA2A', '#ff7300'],
      hotelAvgScore: 0,
      form: {},
      accompany: false,
      date: '',
      accompanyName: '',
      accompanyId: '',
      userName: '',
      userId: '',
      phoneNum: '',
      userEmail: '',
      addressData: provinceAndCityData,
      area: [],
      situation: '',
      isGot: {},
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
      payVisible: false,
      confirmOrderVisible: false,
      payForm: {
        orderId: undefined,
        lastPay: undefined,
        walletPwd: undefined
      },
      confirmOrderData: {
        totalFee: undefined,
        discountFee: undefined,
        hotelDiscountList: [],
        personalDiscountList: [],
        useDiscountMap: {},
      },
      discountVisibleList: [],
      switchType: true,
      hotelId: '',
      isCollection: false,
      hotelDetails: '',
      roomTypeList: [],
      isolateRoomTypeList: [],
      hotelDiscounts: [],
      isolateRoomType: '',
      isolateFee: '',
      roomTypeMap: {},
      appCurrentRoomType: '',
      currentRoomType: '',
      dateValue: [],
      bookDay: 0,
      maxFee: 0,
      minFee: 0,
      totalFee: 0,
      provinceVal: '',
      pageNum: 0,
      userInfo: {},
      alipayData: undefined,
      alipayVisible: false,
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
      isFixed: false,
      offsetTop: 0,
      pickerOptions: {
        disabledDate(v) {
          return v.getTime() < new Date().getTime() - 86400000;
        }
      }

    }
  },
  filters: {
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
  watch: {
    "switchType"(val, oldVal) {
      // console.log(val)
      if (!val) {
        this.phoneNum = this.userInfo.phone
        this.userEmail = this.userInfo.email
        console.log(this.userInfo)
      }


    },
    "dateValue"(val, oldVal) {//普通的watch监听
      // console.log("a: "+val, oldVal);
      this.bookDay = 0
      this.totalFee = 0
      if (val) {
        this.updateFee()
      }
    },
    "currentRoomType"(val, oldVal) {//普通的watch监听
      // console.log("a: "+val, oldVal);
      this.bookDay = 0
      this.totalFee = 0
      if (val) {
        this.updateFee()
      }
    },
    // "date"(val, oldVal) {
    //   this.isolateFee = 0
    //   if (val) {
    //     this.isolateFeeF()
    //   }
    // },
    "isolateRoomType"(val, oldVal) {
      console.log(val);
      console.log(oldVal);
      if (val) {
        this.isolateFeeF()
      }
    }
  },
  mounted() {
    const that = this
    if (window.document.body.clientWidth < 768) {
      this.circleUrlSize = 40
      this.smallPagination = true
    } else {
      this.smallPagination = false
      this.circleUrlSize = 50
    }
    window.onresize = () => {
      return (() => {
        if (window.document.body.clientWidth < 768) {
          this.circleUrlSize = 40
        } else {
          this.smallPagination = false
          this.circleUrlSize = 50
        }
        that.dynamicWidth = (that.$refs.hotelDetail.offsetWidth) * 0.35 - 0
      })()
    }
    this.$nextTick(() => {
      this.dynamicWidth = this.$refs.hotelDetail.offsetWidth - 17
      this.dynamicWidth = this.dynamicWidth * 0.35 - 0
      //获取对象相对于版面或由 offsetTop 属性指定的父坐标的计算顶端位置
      this.offsetTop = document.querySelector('#detailRight').offsetTop;
    })
    window.addEventListener("scroll", this.handleScroll, true);
    this.hotelId = this.$route.params.hotelId || localStorage.getItem('currentHotelId')
    this.getHotelDetails()
    this.getRoomType();
    this.getIsolateRoomTypeList()
    this.getCollectionStatus()
    this.getHotelDiscountList()
    this.getHotelAvgScore()
    this.getCommentsList()
    this.getUserInfo()
  },
  methods: {
    //支付宝支付
    payForAlipay() {
      console.log("this.payForm",this.payForm)
      let url = undefined
      if (this.switchType) {
        url = `api/order/payOrder/alipay/pre/${this.payForm.orderId}`
      }else {
        url = `api/review/pay/alipay/pre/${this.payForm.orderId}`
      }
      get(url).then(res => {
        if (res.data.code == 200) {
          let newWindow = window.open("", '_blank', 'width=1000,height=600');
          const div = newWindow.document.createElement('div');
          div.innerHTML = res.data.data;
          newWindow.document.body.appendChild(div);
          newWindow.document.forms[0].submit();
        } else {
          this.$message.error(res.data.msg);
        }
      })
    },
    //确认支付状态
    alipayCheck() {
      let url = undefined
      if (this.switchType) {
        url = `api/order/payOrder/alipay/check/${this.payForm.orderId}`
      }else {
        url = `api/review/pay/alipay/check/${this.payForm.orderId}`
      }
      get(url).then(res => {
        if (res.data.code == 200) {
          if (res.data.data == true) {
            this.$message.success("订单支付成功!");
            this.payVisible = false
          } else {
            this.$message.warning("订单仍未支付!");
          }

        } else {
          this.$message.error(res.data.msg);
        }
      })
    },
    // 评论展开按钮
    // commentBtn() {
    //   this.comment = !this.comment
    //   this.commentHeight = this.$refs.commentUserBox[0].clientHeight * (this.hotelCommentsList.length - 1) + 55 + 'px'
    // },
    // 获取酒店评价
    getCommentsList() {
      const data = {
        page: {
          page: 1,
          size: 2
        },
        hotelId: this.hotelId
      }
      this.commentsListRequest(data)
    },
    //选择页码
    handleCurrentChange(num) {
      const data = {
        page: {
          page: num,
          size: 2
        },
        hotelId: this.hotelId
      }
      this.commentsListRequest(data)
    },

    //上一页
    prevPage(num) {
      const data = {
        page: {
          page: num,
          size: 2
        },
        hotelId: this.hotelId
      }
      this.commentsListRequest(data)
    },

    //下一页
    nextPage(num) {
      const data = {
        page: {
          page: num,
          size: 2
        },
        hotelId: this.hotelId
      }
      this.commentsListRequest(data)
    },

    commentsListRequest(data) {
      post('/api/orderComment/page', data)
          .then(res => {
            if (res.data.code == 200) {
              console.log(res.data.data);
              this.hotelCommentsList = res.data.data.records
              this.hotelCommentsTotal = res.data.data.total
              // if (this.$refs.commentUserBox) {
              //   this.commentHeight = this.$refs.commentUserBox[0].clientHeight * (this.hotelCommentsList.length - 1) + 55 + 'px'
              // }
            }
          })
          .catch(err => {
            console.error(err);
          })
    },

    getHotelAvgScore() {
      get(`/api/orderComment/score/${this.hotelId}`).then(res => {
        if (res.data.code == 200) {
          this.hotelAvgScore = res.data.data - 0
        } else {
          this.$message.error(res.data.msg);
        }
      })
    },
    updateDiscountVisible() {
      this.discountVisibleList = []
      this.confirmOrderData.discountFee = 0
      this.getCurrentCanUseDiscount()
      for (let val of this.confirmOrderData.useDiscountMap.values()) {
        let map = {
          name: val.name + '[优惠券]',
          price: 0
        }
        if (val.discountsType == 0) {
          this.confirmOrderData.discountFee += val.discounts - 0
          map.price = val.discounts
        } else if (val.discountsType == 1) {
          this.confirmOrderData.discountFee += this.confirmOrderData.totalFee * (1 - val.discounts) - 0;
          map.price = this.confirmOrderData.totalFee * (1 - val.discounts)
        }
        console.log(this.confirmOrderData.discountFee)
        this.discountVisibleList.push(map)
      }
    },
    confirmOrderHandle() {
      this.confirmOrderData.discountFee = 0
      this.confirmOrderData.totalFee = this.roomTypeMap[this.currentRoomType].fee * this.bookDay
      this.confirmOrderData.useDiscountMap = new Map();
      this.discountVisibleList = []
      this.getCurrentCanUseDiscount()
    },

    map2String(map) {
      let str = ""
      let size = map.size
      if (size != 0) {
        let currentNum = 1;
        for (let key of map.keys()) {
          str += key
          if (currentNum != size) {
            str += ','
          }
          currentNum++
        }
      }
      return str
    },
    getCurrentCanUseDiscount() {
      let currentUse = this.map2String(this.confirmOrderData.useDiscountMap)

      let data = {
        hotelId: this.hotelId,
        orderFee: this.confirmOrderData.totalFee,
        bookDay: this.bookDay,
        currentUse: currentUse
      }
      post("/api/discounts/list/personal/got/", data).then(res => {
        if (res.data.code == 200) {
          this.confirmOrderData.personalDiscountList = res.data.data
          // for (var index in this.confirmOrderData.personalDiscountList) {
          //   let map = {
          //     obj: this.confirmOrderData.personalDiscountList[index],
          //     use: false
          //   }
          //   this.confirmOrderData.useDiscountMap[this.confirmOrderData.personalDiscountList[index].id] = map;
          // }
          // console.log("map",this.confirmOrderData.useDiscountMap)
          this.confirmOrderVisible = true
        } else {
          this.$message.error(res.data.msg);
        }
      })
    },

    gotCoupon(id) {
      console.log('id:', id);
      let data = {
        discountsId: id
      }
      formDataPost("/api/discountsUser/gotCoupon", data).then(res => {
        if (res.data.code == 200) {
          this.$message({
            message: this.$t('common.success'),
            type: 'success'
          });
          this.getHotelDiscountList()
        } else {
          this.$message.error(res.data.msg);
        }
      })
    },

    getHotelDiscountList() {
      get("/api/discounts/list/personal/" + this.hotelId).then(res => {
        console.log(res.data);
        if (res.data.code == 200) {
          this.hotelDiscounts = res.data.data
        }
      })
    },
    // 优惠券展开点击事件
    couponClick() {
      this.coupon = !this.coupon
      if (window.innerWidth < 768) {
        this.couponHeight = (this.hotelDiscounts.length - 3) * (this.$refs.couponHeight[0].clientHeight + 24) + 'px'
      } else {
        this.couponHeight = Math.ceil((this.hotelDiscounts.length - 3) / 3) * this.$refs.couponBoxHeight.clientHeight + 'px'
      }
    },

    getCollectionStatus() {
      get('/api/collection/isCollection/' + this.hotelId).then(res => {
        if (res.data.code == 200) {
          this.isCollection = res.data.data
        }
      })
    },

    // 获取酒店隔离房间类型函数
    getIsolateRoomTypeList() {
      const data = {
        isIsolation: 1
      }
      get(`api/roomType/currentRoomTypeList/${this.hotelId}`, data)
          .then(res => {
            console.log(res);
            console.log(res.data.data);
            this.isolateRoomTypeList = res.data.data

          })
          .catch(err => {
            console.error(err);
          })
    },
    // 提交审核按钮
    submit() {
      let leaveDate
      if (this.date) {
        leaveDate = new Date(Date.parse(this.date) + 1209600000) // 14天之后的时间
      } else {
        leaveDate = new Date(Date.parse(this.appDate) + 1209600000) // 14天之后的时间
      }
      const data = {
        checkInTime: this.date || new Date(this.appDate),
        checkOutTime: leaveDate,
        type: this.situation,
        name: this.userName,
        idCard: this.userId,
        phone: this.phoneNum,
        email: this.userEmail,
        province: this.area[0],
        city: this.area[1],
        hotelId: this.hotelId,
        roomType: this.isolateRoomType

      }
      console.log('data:', data);
      post('/api/review/check', data)
          .then(res => {
            console.log(res);
            if (res.data.code == 200) {
              if (res.data.data.payStatus == 0) {
                this.payForm = {
                  orderId: res.data.data.id,
                  lastPay: res.data.data.totalFee,
                  walletPwd: undefined
                }
                this.payVisible = true
              }
              this.$message({
                message: this.$t('common.success'),
                type: 'success'
              });
            } else {
              this.$message.error(res.data.msg);
            }
          })
          .catch(err => {
            console.error(err);
          })

    },
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
    updateFee() {
      this.countTime()
      if (this.currentRoomType) {
        this.totalFee = this.bookDay * this.roomTypeMap[this.currentRoomType].fee
      }
    },
    isolateFeeF() {
      // this.countTime()
      this.isolateFee = 14 * this.roomTypeMap[this.isolateRoomType].isolationFee
    },
    countTime() {
      let estimatedCheckIn = new Date(this.dateValue[0])
      let estimatedCheckOut = new Date(this.dateValue[1])
      this.bookDay = parseInt(Math.abs(estimatedCheckIn - estimatedCheckOut) / 1000 / 60 / 60 / 24)
      // console.log("dateDiff-----------",this.bookDay)
    },

    handleScroll() {
      let scrollTop = document.documentElement.scrollTop || document.body.scrollTop;
      // console.log(scrollTop);
      // console.log(this.offsetTop * 1.15);
      // this.isFixed = scrollTop > this.offsetTop * 1.15 ? true : false;
      this.isFixed = scrollTop >= 200 ? true : false;
      // console.log(this.offsetTop)
      // console.log(this.isFixed)
      // console.log(scrollTop);
    },

    addUseDiscount(val) {
      console.log("this.confirmOrderData.useDiscountMap.has(val.id)", this.confirmOrderData.useDiscountMap.has(val.id))
      if (this.confirmOrderData.useDiscountMap.has(val.id)) {
        this.confirmOrderData.useDiscountMap.delete(val.id)
      } else {
        this.confirmOrderData.useDiscountMap.set(val.id, val)
      }
      this.updateDiscountVisible()
      console.log(this.confirmOrderData.useDiscountMap)

    },

    collection() {
      let data = {
        id: this.hotelId
      }
      formDataPost("/api/collection/collection", data).then(res => {
        if (res.data.code == 200) {
          this.isCollection = !this.isCollection
          this.$message({
            message: res.data.data,
            type: 'success'
          });
        } else {

        }
      })
    },

    toMap() {
      let that = this;
      window.open("https://ditu.amap.com/search?query=" + that.hotelDetails.address)
    },

    getRoomType() {
      //this.hotelId = this.$route.params.hotelId
      get('/api/roomType/currentRoomTypeList/' + this.hotelId)
          .then(res => {
            console.log(res);
            //this.dialogVisible = true
            this.roomTypeList = res.data.data
            var map = {};
            let min = 0;
            let max = 0;
            for (var index in this.roomTypeList) {
              let fee = this.roomTypeList[index].fee;
              if (fee < min) {
                min = fee
              } else if (fee > max) {
                max = fee
              }
              map[this.roomTypeList[index].id] = this.roomTypeList[index];
            }
            this.roomTypeMap = map
            this.maxFee = max
            this.minFee = min
            console.log(this.maxFee)
          })
          .catch(err => {
            console.error(err);
          })
    },
    pay() {
      if (switchType){
        let data = {
          orderId: this.payForm.orderId,
          walletPwd: this.payForm.walletPwd
        }
        formDataPost("/api/order/payOrder", data).then(res => {
          if (res.data.code == 200) {
            this.$message({
              message: this.$t('hotelList.success'),
              type: 'success'
            });
            this.payVisible = false
            this.confirmOrderVisible = false
            this.payForm = {
              orderId: undefined,
              lastPay: undefined,
              walletPwd: undefined
            }
          } else {
            this.$message({
              message: res.data.msg,
              type: 'warning',
              duration: 4000
            });
          }
        })
      } else {
        let data = {
          id: this.payForm.orderId,
          walletPwd: this.payForm.walletPwd
        }
        formDataPost("/api/review/pay", data).then(res => {
          if (res.data.code == 200) {
            this.$message({
              message: this.$t('hotelList.success'),
              type: 'success'
            });
            this.payVisible = false
            this.confirmOrderVisible = false
            this.payForm = {
              orderId: undefined,
              lastPay: undefined,
              walletPwd: undefined
            }
          } else {
            this.$message({
              message: res.data.msg,
              type: 'warning',
              duration: 4000
            });
          }

        })
      }



    },
    //根据id获取用户信息
    getUserInfo() {
      let id = localStorage.getItem('userId')
      get('/api/user/get/' + id)
          .then(res => {
            this.userInfo = res.data.data
            this.userInfo.password = ''
          })
          .catch(err => {
            console.log(err);
          })
    },

    confirm() {
      let data = {
        customerId: localStorage.getItem('userId'),
        hotelId: this.hotelId,
        roomType: this.currentRoomType,
        estimatedCheckIn: this.dateValue[0],
        estimatedCheckOut: this.dateValue[1],
        province: this.provinceVal,
        discount: this.map2String(this.confirmOrderData.useDiscountMap)
      }
      // console.log("data=========", data)
      post('/api/order/create', data)
          .then(res => {
            console.log(res);
            if (res.data.code == 200) {
              this.$message({
                message: this.$t('hotelList.success'),
                type: 'success'
              });
              this.payForm.orderId = res.data.data.id
              this.payForm.lastPay = res.data.data.lastPay
              this.payVisible = true
              this.confirmOrderVisible = false

              //初始化表单
              this.currentRoomType = undefined
              this.dateValue = []
              this.provinceVal = ''
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

    getHotelDetails() {
      //let id = this.$route.params.hotelId
      let that = this
      // console.log(id)
      get("/api/hotel/get/" + this.hotelId).then(res => {
        that.hotelDetails = res.data.data;
        console.log('酒店详细数据:', res.data.data);
        if (res.data.data.badge) {
          that.hotelBadgeList = res.data.data.badge.split(',')
        }
      })
    },

    // 移动端选择开始日期取消函数
    startCancelDate() {
      this.showAppDateStart = false
    },

    // 移动端选择开始日期确定函数
    startConfirmDate(value) {
      this.appDateStart = this.formatter(value)
      this.dateValue[0] = this.formatter(value)
      this.startCancelDate()
    },

    // 移动端选择结束日期取消函数
    endCancelDate() {
      this.showAppDateEnd = false
    },

    // 移动端选择结束日期确定函数
    endConfirmDate(value) {
      this.appDateEnd = this.formatter(value)
      this.dateValue[1] = this.formatter(value)
      this.endCancelDate()
    },

    // 移动端选择房间取消函数
    roomTypeCancel() {
      this.showRoomType = false
    },

    // 移动端选择房间确定函数
    roomTypeConfirm(value, index) {
      console.log(value);
      this.appCurrentRoomType = value.name
      this.currentRoomType = value.id
      this.roomTypeCancel()
    },

    // 移动端选择省份取消函数
    provinceCancel() {
      this.showProvince = false
    },

    // 移动端选择省份确定函数
    provinceConfirm(value, index) {
      console.log(value);
      this.appCurrentProvince = value.label
      this.provinceVal = value.value
      this.provinceCancel()
    },

    // 移动端选择入住时间取消函数
    cancelDate() {
      this.showAppDate = false
    },

    // 移动端选择入住时间确定函数
    confirmDate(value, index) {
      console.log(value);
      this.appDate = this.formatter(value)
      this.cancelDate()
    },

    // 移动端选择省份取消函数
    provinceCancel1() {
      this.showProvince1 = false
    },

    // 移动端选择省份确定函数
    provinceConfirm1(value, index) {
      console.log(value);
      this.area[0] = value[0]
      this.area[1] = value[1]
      this.appCurrentProvince1 = value[0] + ' / ' + value[1]
      this.provinceCancel1()
    },

    // 移动端选择申报类型取消函数
    situationCancel() {
      this.showSituation = false
    },

    // 移动端选择申报类型确定函数
    situationConfirm(value, index) {
      console.log(value);
      this.appSituation = value.label
      this.situation = value.value
      this.situationCancel()
    },

    // 移动端自申报选择房间取消函数
    appRoomTypeCancel() {
      this.showAppRoomType = false
    },

    // 移动端自申报选择房间确定函数
    appRoomTypeConfirm(value) {
      console.log(value);
      this.appCurrentRoomType1 = value.name
      this.isolateRoomType = value.id
      this.appRoomTypeCancel()
    },

    // 格式化函数
    formatter(value) {
      const dateTime = new Date(value) // Date实例
      const YYYY = dateTime.getFullYear() // 获取当前年份
      const MM = this.fillPrefix(dateTime.getMonth() + 1) // 获取当前月份
      const DD = this.fillPrefix(dateTime.getDate()) // 获取当前天数

      // 返回格式化之后的当前时间
      return `${YYYY}-${MM}-${DD}`

    },

    // 补零函数
    fillPrefix(val) {
      return val > 9 ? val : `0${val}` // 个位数时间进行补零操作
    }

  }
}


</script>

<style scoped>
.app-booking-btn {
  display: none;
}

.discounts-body-condition {
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}

/* 动画 Start */
.coupon-box {
  height: var(--couponHeight)
}

.draw-enter-active, .draw-leave-active {
  transition: all 0.6s ease;
}

.draw-enter, .draw-leave-to /* .fade-leave-active below version 2.1.8 */
{
  height: 0;
}

.expand-collapse {
  width: 100%;
  border-top: 1px solid rgba(206, 203, 203, 0.5);
  color: #aaaaaa;
  font-size: 1.2rem;
  font-weight: 600;
  background: #fff;
}

/* .comment-list-box {
  height: var(--commentHeight)
} */

.comment-enter-active, .comment-leave-active {
  transition: all 0.6s ease;
}

.comment-enter, .comment-leave-to /* .fade-leave-active below version 2.1.8 */
{
  height: 0;
}

/* 动画 End */

.expand-collapse {
  width: 100%;
  border-top: 1px solid rgba(206, 203, 203, 0.5);
  color: #aaaaaa;
  font-size: 1.2rem;
  font-weight: 600;
  background: #fff;
}

/* 定义滚动条样式 */
::-webkit-scrollbar {
  width: 10px;
  height: 10px;
  border-radius: 10px;
  background-color: #eeeeee;
}

/*定义滚动条轨道 内阴影+圆角*/
::-webkit-scrollbar-track {
  box-shadow: inset 0 0 0px rgb(245, 245, 245);
  border-radius: 10px;
  background-color: #eeeeee;
}

/*定义滑块 内阴影+圆角*/
::-webkit-scrollbar-thumb {
  border-radius: 10px;
  box-shadow: inset 0 0 0px rgba(240, 240, 240, .5);
  background-color: #b2b2b2;
}


.hotel-list-main {
  /*height: calc(100vh - 220px);*/
  display: flex;
  flex-direction: column;
  justify-content: space-between;
}

.big-body {
  background-color: #ecf5ff;
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

.big-box {
  /* margin: 0 auto; */
  /*border: 1px solid red;*/
  /* width: 1200px; */
  /* width: 120rem; */
  padding: 0 4rem;

}

.hotel-title {
  /* font-size: 30px; */
  font-size: 3rem;
  color: #252626;
  font-family: inherit;
  font-weight: 600;
  line-height: 1.1;
  /* margin: 30px 0; */
  margin: 3rem 0;
}

.hotel-detail {
  display: flex;
  justify-content: space-between;
  align-items: flex-start;
  /*border: 1px solid blue;*/
}

.detail-left {
  left: 0;
  flex: 1;
  max-width: 62%;
  overflow: hidden;
  /*border: 1px solid blueviolet;*/
}

.other-pay-item {
  margin: 0 0.2rem;
  padding: 0;
  cursor: pointer;
}

.other-pay-item :first-child :last-child {
  margin: 0;
}




/*@media (min-width: 1000px)*/
.detail-right {
  /*position:fixed;*/
  /*right: 0;*/
  /*top:100%;*/
  /* width: 440px; */
  width: 35%;
  min-width: 252px;
  /* max-width: 44rem; */
  /*border: 1px solid saddlebrown;*/
}

.detail-detail {
  line-height: 1.1;
  position: relative;
  background-color: #fff;
  overflow: hidden;
  /* padding: 10px; */
  padding: 1rem;
  box-shadow: 0 12px 5px -10px rgba(0, 0, 0, 0.1), 0 0 4px 0 rgba(0, 0, 0, 0.1);

}

.sub-title {
  display: block;
  /* font-size: 24px; */
  font-size: 2.4rem;
  /* margin-block-start: 1.6em;
  margin-block-end: 1em; */
  margin-block-start: 1.6rem;
  margin-block-end: 1rem;
  margin-inline-start: 0px;
  margin-inline-end: 0px;
  font-weight: bold;
  position: relative;
  /*margin-top: 10px;*/
}

h3.sub-title .en {
  position: absolute;
  top: 0;
  font-size: 10px;
  color: #898a8a;
  font-weight: 500;
  font-style: italic;
  margin-left: 4px;
  text-transform: uppercase;
}

.detail-content {
  display: flex;
  align-items: center;
  /* padding: 12px 10px; */
  padding: 1.2rem 1rem;
  font-size: 1.8rem;
  line-height: 2;
  /*position: relative;*/
  background-color: #fff;
  overflow: hidden;
  overflow-x: hidden;
  overflow-y: hidden;
  margin-bottom: 3rem;
  box-shadow: 0 12px 5px -10px rgba(0, 0, 0, 0.1), 0 0 4px 0 rgba(0, 0, 0, 0.1);
}

.detail-right-price {
  display: flex;
  align-items: center;
  flex-wrap: wrap;
}

.text-weak {
  color: #585a5a;
  font-size: 12px;
}

.price-sum {
  color: #ff4d6a;
  font-size: 32px;
  margin: 10px;
}

.unit {
  font-size: 16px;
}

.book-box {
  line-height: 1.1;
  position: relative;
  background-color: #fff;
  overflow: hidden;
  /* padding-top: 8px;
  padding-bottom: 12px; */
  width: 100%;
  /* padding: 8px; */
  padding: 0.8rem;
  box-shadow: 0 12px 5px -10px rgba(0, 0, 0, 0.1), 0 0 4px 0 rgba(0, 0, 0, 0.1);

}

.book-date {
  margin: 0.8rem;
}

.book-room {
  margin: 0.8rem;
}

.room-detail {
  font-size: 14px;
  line-height: 20px;
  margin: 8px 0;
}

.detail-introduce {
  /* line-height: 26px;
  margin: 0px 6px 8px 6px;
  font-size: 14px; */
  line-height: 2.8rem;
  margin: 0 0.6rem 0.8rem 0.6rem;
  font-size: 1.6rem;
}


.location {
  color: #0af;
  /* margin: 6px 12px;
  font-size: 40px; */
  margin: 0.6rem 1.2rem;
  font-size: 4rem;
  cursor: pointer;
}

.address-detail {
  width: 50%;
  /* font-size: 16px;
  line-height: 30px;
  padding: 6px; */
  font-size: 1.6rem;
  line-height: 3rem;
  padding: 0.6rem;
}

.badge {
  /* margin: 2px 6px;
  padding: 6px 8px; */
  margin: 0.2rem 0.6rem;
  padding: 0.6rem 0.8rem;
  border-radius: 6px;
  background: #4f93fe;
  font-size: 1.6rem;
  color: #fff !important;
}

.abnormal {
  padding: 10px;
}

.discounts-box {
  /*border: 1px solid red;*/
  /* width: 220px;
  height: 110px;
  margin: 10px; */
  /* width: 22rem;
  height: 11rem; */
  width: 31.3%;
  height: 110px;
  margin: 0.8rem 1%;
  box-shadow: 0 12px 5px -10px rgba(0, 0, 0, 0.1), 0 0 4px 0 rgba(0, 0, 0, 0.1);
  cursor: pointer;

}

/* .discounts-box.active {
  cursor: no-drop;
} */
.discounts-box .discounts-title.active {
  background: rgb(210 211 213);
  cursor: no-drop;
}

.discounts-box .discounts-title button {
  cursor: no-drop;
}

.discounts-box .discounts-body.active {
  background: rgb(210 211 213);
  cursor: no-drop;
}


.discounts-title {

  width: 100%;
  height: 29%;
  background: rgb(217, 236, 255);
  border-bottom-left-radius: 6px;
  border-bottom-right-radius: 6px;
}

.discounts-body {
  width: 100%;
  height: 70%;
  background: rgb(217, 236, 255);
  border-top-left-radius: 6px;
  border-top-right-radius: 6px;
}

.discounts-body-top {
  margin: 1rem 0.4rem;
}

.discounts-body-price {
  color: #ff4d6a;
  /* font-size: 22px; */
  font-size: 2.2rem;
  margin-right: 0.5rem;
  font-weight: 800;
  line-height: 30px;
}

.discounts-body-bottom {
  color: #999;
  font-size: 1.4rem;
  margin-top: -6px;
  margin-left: 6px;
}

.order-hotel-detail-detail {
  margin: 0px 6px;
}

.choose-hotel-discount {
  width: 100%;
  /* max-height: 110px;
  overflow-y: scroll;
  overflow-x: hidden; */
}

.choose-discount-box {
  margin: 6px 0px;
  width: 96%;
  height: 40px;
  background: #fff;
  /*border: 1px solid red;*/
  box-shadow: 0 12px 5px -10px rgba(0, 0, 0, 0.1), 0 0 4px 0 rgba(0, 0, 0, 0.1);

}

.choose-discount-left {
  width: 79%;
  height: 100%;
  background: rgb(217, 236, 255);
  border-top-right-radius: 6px;
  border-bottom-right-radius: 6px;
}

.choose-discount-right {
  width: 21%;
  height: 100%;
  background: rgb(217, 236, 255);
  border-top-left-radius: 6px;
  border-bottom-left-radius: 6px;
  font-size: 20px;
  font-weight: 700;
  line-height: 44px;
  color: #ff4d6a;
}

.choose-discount-chosen {
  position: relative;
  text-decoration: line-through;
  /* color: #4ABE84; */
  border-radius: 0;
  border: 2px solid rgba(74, 190, 132, 1);
}

.choose-discount-chosen:before {
  content: '';
  position: absolute;
  right: -1px;
  bottom: -1px;
  border: 17px solid #4ABE84;
  border-top-color: transparent;
  border-left-color: transparent;
}

.choose-discount-chosen:after {
  content: '';
  width: 5px;
  height: 12px;
  position: absolute;
  right: 4px;
  bottom: 6px;
  border: 2px solid #fff;
  border-top-color: transparent;
  border-left-color: transparent;
  transform: rotate(45deg);
}

.disable-selected {
  cursor: no-drop;
  text-decoration: none;
  border: none;
}

.disable-selected:after, .disable-selected:before {
  content: none;
}

.disable-selected > .choose-discount-left, .disable-selected > .choose-discount-right {
  background: #d9d9d9;
}

.choose-discount-name {
  font-size: 1.4rem;
  font-weight: 700;
  margin: auto 0;
  margin-left: 8px;
}

.choose-discount-date {
  color: #999;
  font-size: 10px;
  margin-right: 8px;
}

.order-count-item {
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  margin-top: 6px;
  color: #999;
}

.pay-box {
  width: 100%;
  height: 26rem;
  margin-bottom: -6rem;
}

.pay-box-price {
  font-size: 5.6rem;
  font-weight: 700;
  color: #ff4d6a;
  width: 100%;
  text-align: center;
  margin: 10px 0 20px 0;
}

.pay-check-btn {
  color: #999999;
  cursor: pointer;
}

.pay-check-btn:hover {
  color: #40A5FD;
  text-decoration: underline;
}

.comment-box {
  /* margin: 16px; */
  /* padding: 1.6rem 1.6rem 0 1.6rem; */
  padding: 1.6rem;
  width: 100%;
  box-sizing: border-box;
}

.comment-total-box {
  display: flex;
  align-items: center;
  justify-content: space-between;
}

.comment-total-score-ball {
  border-radius: 50%;
  background: #00aaff;
  width: 50px;
  height: 50px;
  /* font-size: 20px;
  margin-right: 6px; */
  /* width: 5rem;
  height: 5rem; */
  font-size: 2rem;
  margin-right: 0.6rem;
  font-weight: 800;
  color: #fff;
}

.comment-user-box {
  border-top: 1px solid #f1f1f1;
  padding-top: 1.2rem;
  margin-top: 0.6rem;
  margin-bottom: 10px;
}

.comment-user-box-right {
  margin-left: 10px;
  width: 100%;
}

.comment-user-date {
  color: #999;
}

.comment-user-roomType {
  color: #999;
}

.choose-discount-cantUse {

}

/* 媒体查询 Start */
@media screen and (max-width: 767.9px) {
  /* 页面测试无法显示767，实际是767.2px */
  .hotel-list-bg {
    height: 120px;
  }

  .hotel-list-bg img {
    height: 120px;
  }

  .big-box {
    padding: 0 2rem;
  }

  .detail-left {
    max-width: 100%;
  }

  .comment-total-score-ball {
    width: 40px;
    height: 40px;
  }

  .detail-right {
    display: none;
  }

  .discounts-box {
    width: 100%;
    margin: 2rem 5rem;
  }

  .comment-user-nickname {
    width: calc(100% - 100px);
    overflow: hidden;
    white-space: nowrap;
    text-overflow: ellipsis;
  }

  .comment-user-box-right {
    width: calc(100% - 50px);
  }

  .app-booking-btn {
    display: flex;
    align-items: center;
    justify-content: center;
    position: fixed;
    left: 0;
    bottom: 30px;
    width: 100%;
    padding: 0 5rem;
    box-sizing: border-box;
  }

  .app-booking-main {
    height: calc(100% - 52px);
    display: flex;
    flex-direction: column;
    justify-content: space-between;
  }
}

@media screen and (min-width: 768px) and (max-width: 992px) {
  .big-box {
    padding: 0 2rem;
  }

  .detail-introduce {
    line-height: 3.2rem;
  }

  .discounts-body-bottom {
    margin-top: 0;
    margin-left: 0;
  }

  .discounts-box {
    width: 31.5%;
    margin: 0.5rem
  }
}

@media screen and (min-width: 992px) and (max-width: 1240px) {
  .big-box {
    padding: 0 4.5%;
  }
}

@media screen and (min-width: 1240px) and (max-width: 1440px) {
  .big-box {
    padding: 0 10%;
  }
}

@media screen and (min-width: 1441px) and (max-width: 1680px) {
  .big-box {
    padding: 0 15%;
  }

  .detail-introduce {
    font-size: 1.4rem;
  }

  .detail-content {
    font-size: 1.6rem;
  }

  .discounts-box {
    height: 11rem;
  }
}

@media screen and (min-width: 1680px) {
  .big-box {
    padding: 0 18%;
  }

  .detail-introduce {
    font-size: 1.4rem;
  }

  .detail-content {
    font-size: 1.6rem;
  }

  .discounts-box {
    height: 11rem;
  }
}

/* 媒体查询 End */

</style>