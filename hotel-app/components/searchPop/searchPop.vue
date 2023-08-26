<template>
	<view class="container">
		
		<view class="search_box">
			<!-- <view class="position_icon">
				<u-icon name="map" size='30px' color='#6062663'></u-icon>
			</view> -->
				<u-search
				searchIcon='map'
				@clickIcon='getLocaltion(),postLocaltion()'
				placeholder="输入省份/直辖市/酒店名" 
				v-model="keyword"
				shape="square"
				height="48px"
				:animation='true'
				:actionStyle="{color: '#7f7f7f'}"
				@custom="filterHotel"
				></u-search>
		</view>
		
		<button @click="clog"></button>
		
		<view class="hotel_list" v-for="(item,index) of list1" :key='index'>
			{{item.name}}
		</view>
		
		<view class="hotel_list" v-for="(item,index) of list" :key='index'>
			<view class="header">
				<text>{{item.name}}</text>
			</view>
			<u-line color="#969696"></u-line>
			<view class="order_body">
				<view class="bed_image">
					<image :src="item.cover" mode=""></image>
				</view>
				<view class="order_info">
					房型：<br>
					时间：<br>
					价格：
				</view>
			</view>
		</view>
	
	</view>
</template>

<script>
	import db from '../../util/localStorage.js'
	
	export default {
		components:{},
		name:"searchPop",
		props:['list'],
		data() {
			return {
				filterList:[],
				keyword:'',
				list1:this.list,
			};
		},
		methods:{
			async postLocaltion(){
				var token = db.get('token')
				const res = await this.$myRequest({
					url:'/api/hotel/myHotel',	
				})
			},
			
			getLocaltion(){
				uni.getLocation({
					type: 'wgs84',
					success: function (res) {
						console.log('当前位置的经度：' + res.longitude);
						console.log('当前位置的纬度：' + res.latitude);
					}
				});
			},
			clog(){
				console.log(this.list);
				console.log(this.list1);
			},
			// 酒店筛选
			async filterHotel(){
				const res = await this.$myRequest({
					url: '/api/hotel/page',
					method: 'POST',
					data: {
						"page": {
							"page": this.page,
							"size": 6
						},
					}
				})
				console.log(res);
				this.list = [...this.list, ...res.data.data.records]
				if (res.data.data.records.length < 6) {
					this.flag = true
				}
				console.log(this.list);
			},
		}
	}
</script>

<style>
.container{
	width: 100%;
	height: 1400rpx;

}
.search_box{
	width: 100%;
	height: 176rpx;
	display: flex;
	align-items: center;
	box-shadow:0 0 10px #303133 ;
}
.order_list {
		width: 700rpx;
		height: 300rpx;
		background-color: #FFFFFF;
		border-radius: 15rpx;
		border: #cfc0cc 0.5px solid;

		.header {
			display: flex;
			justify-content: space-between;
			padding: 30rpx;
		}
	}
	
	.order_body{
		display: flex;
		padding:20rpx;
		.bed_image{
			width: 150rpx;
			height: 150rpx;
			background-color: #007AFF;
			margin-right: 40rpx;
			image {
				width: 170rpx;
				height: 155rpx;
			}
		}
		.order_info{
			line-height: 45rpx;
		}
	}
button{
	bottom: 0px;
}
</style>
