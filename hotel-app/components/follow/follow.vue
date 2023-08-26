<template>
		<view class="follow_icon">
			<u-icon :name="flag?'star-fill':'star'" :size="size?size:'22'" color='yellow'  @click="Follow"></u-icon>
			<!-- <u-icon name="heart" :size="size?size:'22'" v-show="flag" @click="Follow"></u-icon> -->
			<!-- <u-icon name="heart-fill" :size="size?size:'22'" color="red" v-show="!flag"  @click="unFollow"></u-icon> -->
		</view>
</template>

<script>
	import db from '../../util/localStorage.js'
	
	export default {
		name:"follow",
		props:['size','id','detailId'],
		data() {
			return {
				flag:''
			};
		},
		beforeMount(){
			const hotelId = db.get('hotelId')
			this.getFollow(hotelId)
		},
		methods:{
			async getFollow(id){
				const res = await this.$myRequest({
					url:"/api/collection/isCollection/"+this.detailId,
				})
				this.flag = res.data.data;
			},
			async Follow(){
				this.flag = !this.flag
				const token = db.get('token')
				const res = await this.$myRequest({
					url: '/api/collection/collection/',
					method:'POST',
					header: {
					    'Content-Type': 'application/x-www-form-urlencoded',
						'Authorization':token
					},
					data:{
						"id":this.detailId
					}
				})
				this.getFollow()
				console.log(res.data.data);
			},
		},
	}
</script>

<style>

</style>