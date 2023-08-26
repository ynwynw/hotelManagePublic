<template>
	<view class="container">
		<view class="scan">
			<u-icon name='scan' size="30"></u-icon>
		</view>
		
		<!-- 用户消息 -->
		<view class="account">
			<view class="account_profile">
				<image :src="head" mode="aspectFill"></image>
			</view>
			<view class="account_name">
				<text>
					{{userInfo.nickname}}
				</text>
			</view>
		</view>

		<!-- 头像下面的列表 -->
		<view class="option_list" v-for="(item,index) in option" :key="index">
			<view class='order option'>
				<view class="order_image">
					<img style="visibility: visible" alt="" width="20px" height="20px" :src='item.icon_src'>
				</view>
				<view class="order_link" @click="toPersonal(item.to)">
					{{item.name}}
				</view>
			</view>
		</view>
		
		<!-- 登出按钮 -->
		<view class="exit">
					<u-button type="error" text="退出" @click="exit"></u-button>
		</view>


	</view>
</template>

<script>
	import db from '../../util/localStorage.js'
	export default {

		name: "accountPop",
		props:['userInfo','heads'],
		// props: {
		// 	userInfo: {
		// 		type: [String, Array, Object],
		// 		default: ''
		// 	}
		// },
		data() {
			return {
				head:this.heads,
				option: [{
						name: '个人',
						icon_src: 'https://z1.muscache.cn/pictures/c8008cbd-3c25-4d68-b124-7711f2341ff0.jpg',
						to: 'personal'
					},
					{
						name: '订单',
						icon_src: "https://z1.muscache.cn/pictures/6772036c-e88c-466d-bf3b-00c73d1f0391.jpg",
						to: 'myOrder'
					},
					{
						name: '钱包',
						icon_src: "https://z1.muscache.cn/pictures/56f58310-949b-4bc3-83d6-f26c3e7f7cdc.jpg",
						to: 'wallet'
					},
					{
						name: '心愿',
						icon_src: 'https://z1.muscache.cn/pictures/fcdb0b6a-da8d-4a9d-acb8-d15d63abc110.jpg',
						to: 'wishList'
					},
					// {
					// 	name: '语言',
					// 	icon_src: "https://z1.muscache.cn/pictures/ca1d518e-4d50-4fd5-9205-48ab3a5f26a8.jpg",
					// 	to: '#/pages/order/personal'
					// },
					{
						name: '关于',
						icon_src: "https://z1.muscache.cn/pictures/f521016a-3cb8-416c-ae8d-41189a386501.jpg",
						to: 'about'
					},
				]
			};
		},
		methods: {
			toPersonal(to){
				let url = '../../pages/list/'+to
				uni.navigateTo({
					url:url
				})
			},
			exit() {
				db.clear();
				// db.removeItem('userInfo')
				// db.removeItem('hotelList')
				uni.redirectTo({
					url: '../../pages/login/login'
				})
			}
		}
	}
</script>

<style lang="scss">
	.scan{
		position: absolute;
		right: 20rpx;
		top:20rpx;
	}

	.exit {
		// position: absolute;
		bottom: 180px;
		width: 75%;
		margin: 150rpx 80rpx 0px 80rpx;
		.exit_button{
			bottom: 0px;
			background-color: #006C70;
		}
	}

	.order {
		display: flex;
		margin-left: 20rpx;
	}

	.order_link {
		width: 32px;
		height: 20.8px;
		color: #323233;
		font-size: 14px;
		a {
			text-decoration: none;
			color: #676a6e;
		}
	}

	.order_image {
		margin: 0px 36rpx 0px 24px;
		width: 20px;
		height: 20px;
	}

	.container {
		width: 580rpx;
		height: 100%;
		display: flex;
		flex-direction: column;
	}

	.account_name {
		margin-top: 30rpx;
		text{
			font-size: 25px;
			font-weight: bolder;
		}
	}

	.account_profile>image {
		width: 180rpx;
		height: 180rpx;
		border-radius: 50%;
		border: 10rpx solid #515151;
	}

	.account {
		align-items: center;
		flex-direction: column;
		display: flex;
		padding: 80rpx;
		height: 330rpx;
	}

	.account_name {
		color: #606266;
	}

	.option {
		margin-bottom: 65rpx;
	}
</style>
