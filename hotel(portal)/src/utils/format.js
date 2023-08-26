export function dateTimeFormat(value,format) {
    const dateTime = new Date(value) // Date实例

    // console.log(dateTime);

    const YYYY = dateTime.getFullYear() // 获取当前年份
    const MM = dateTime.getMonth() + 1 // 获取当前月份
    const DD = dateTime.getDate() // 获取当前天数
    const hh = fillPrefix(dateTime.getHours()) // 获取当前小时，并判断是否需要补零
    const mm = fillPrefix(dateTime.getMinutes()) // 获取当前分钟，并判断是否需要补零
    const ss = fillPrefix(dateTime.getSeconds()) // 获取当前秒数，并判断是否需要补零
    // 返回格式化之后的当前时间
    // return `${YYYY}-${MM}-${DD} ${hh}:${mm}:${ss}`
    // return `${YYYY}-${MM}-${DD} ${hh}:${mm}`
    if (format == 'ymd') {
        return `${YYYY}-${MM}-${DD}`
    } else {
        return `${YYYY}-${MM}-${DD} ${hh}:${mm}`
    }
}

// 补零函数
function fillPrefix(val) {

    return val > 9 ? val : `0${val}` // 个位数时间进行补零操作

}

