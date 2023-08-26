<template>
	<view class="container">
		
		<view class="search">
			<u-search 
			placeholder="日照香炉生紫烟" 
			v-model="keyword"
			shape="square"
			:showAction="false"
			height="48px"
			@focus="toSearchPage"
			></u-search>
		</view>
	</view>
</template>

<script>
	import searchPop from "../../components/searchPop/searchPop.vue"
	
	export default {
		components:{searchPop},
		props:['list'],
		name:"Search",
		data() {
			return {
			showSearch: false,
			keyword:'谁是照香炉',
				
			};
		},
		methods:{
			toSearchPage(){
				uni.navigateTo({
					url:'../../pages/search/searchPage'
				})
			},
		}
	}
</script>

<style>
.search {
		margin:-40rpx 40rpx 0rpx 40rpx;
		position: relative;
	}
</style>
