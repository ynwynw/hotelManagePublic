let db = {
  save (key, value) {  //写入本地存储
  uni.setStorageSync(key, JSON.stringify(value));
  },
  
  get (key, defaultValue = {}) {  //从本地存储读出
  const value = uni.getStorageSync(key) || defaultValue
    return JSON.parse(localStorage.getItem(key)) 
  },
  
  remove (key) {  //删除
    uni.removeStorageSync(key);
  },
  
  clear () { //删除全部
    uni.clearStorageSync();
  }
}
 
export default db