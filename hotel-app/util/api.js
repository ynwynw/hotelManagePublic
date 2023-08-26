import db from '../util/localStorage.js'
const BASE_URL = "http://106.52.219.171:8100"

export const myRequest = (options) => { //暴露一个function：myRequest，使用options接收页面传过来的参数
        return new Promise((resolve, reject) => { //异步封装接口，使用Promise处理异步请求
			const token = db.get('token')
            uni.request({ //发送请求
				url: BASE_URL + options.url, 
				data: options.data || {},  // 这里传入你的参数(json格式)
				method: options.method || 'GET',
				header: options.header || {
					'Authorization':token
				},
                success: (res) => { //数据获取成功
                    if (res.statusCode != 200) { //因为200是返回成功的状态码，如果不等于200,则代表获取失败,
                        return uni.showToast({
                            title:res.data.msg
                        })
                    }
                    resolve(res) //成功,将数据返回
                },
                fail: (err) => { //失败操作
                    uni.showToast({
                        title: "请求接口失败！"
                    })
                    reject(err)
                }
            })
        })
    }

