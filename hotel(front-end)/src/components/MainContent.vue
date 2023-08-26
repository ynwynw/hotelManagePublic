<template>
  <div>
    <div class="flex flex-row-reverse">
      <el-button :type="mode == 0 ? 'danger' : 'success'" @click="refresh"><span v-show="mode == 1">切换至订单视图</span><span v-show="mode == 0">切换至防疫视图</span></el-button>
    </div>
    <div class="main-content">
      <div id="doughnut-chart"></div>
      <div id="nightingale-chart"></div>
      <div id="basic-bar" v-show="this.mode == 0"></div>
      <div id="stacked-column-chart" v-show="this.mode == 1"></div>
      <div id="mixed-line-and-bar" v-show="this.mode == 0"></div>
      <div id="stacked-line-chart" v-show="this.mode == 1"></div>
      <div id="map-box"></div>
    </div>
  </div>
</template>

<script>
import * as echarts from 'echarts';
import {get, post} from "../utils/request";
import chinaJson from "../assets/js/china"

echarts.registerMap('china', {
  geoJSON: chinaJson
})

export default {
  name: "MainContent",
  data() {
    return {
      mode: 0
    }
  },
  mounted() {
    this.doughnutChartInit()
    this.baseBarInit()
    this.mapInit()
    this.nightingaleChartInit()
    this.mixedLineAndBarInit()
    this.stackedColumnChartInit()
    this.stackedLineChartInit()
  },
  methods: {
    refresh() {
      if (this.mode == 1) {
        this.mode = 0
      } else {
        this.mode = 1
      }
      console.log(this.mode)
      this.doughnutChartInit()
      this.baseBarInit()
      this.mapInit()
      this.nightingaleChartInit()
      this.mixedLineAndBarInit()
      this.stackedColumnChartInit()
      this.stackedLineChartInit()

    },

    //堆叠折线图初始化
    stackedLineChartInit() {
      let element = document.getElementById('stacked-line-chart')
      let stackedLineChart = echarts.init(element)

      // option data
      let xAxis = []
      let series = []

      // request parameter
      let range = 'month'
      let status = 0

      // mission list
      const mission0 = new Promise((resolve, reject) => {
        get(`api/chart/isolation/status/${range}/${status}`).then(res => {
          let list = res.data.data
          let data = []
          for (let i = 0; i < list.length; i++) {
            xAxis.push(list[i].name)
            data.push(list[i].value - 0)
          }

          let item = {
            name: '正在隔离',
            type: 'line',
            // stack: 'Total',
            color: '#edd600',
            smooth: true,
            data: data
          }
          series.push(item)
          console.log("promise: 0ok")
          resolve()
        })
        .catch(err => {
          reject(err)
        })
      })
      const mission1 = new Promise((resolve, reject) => {
        status = 1
        get(`api/chart/isolation/status/${range}/${status}`).then(res => {
          let list = res.data.data
          let data = []
          for (let i = 0; i < list.length; i++) {
            data.push(list[i].value - 0)
          }

          let item = {
            name: '解除隔离',
            type: 'line',
            // stack: 'Total',
            color: '#9fe080',
            smooth: true,
            data: data
          }
          series.push(item)
          console.log("promise: 1ok")
          resolve()
        })
        .catch(err => {
          reject(err)
        })
      })
      const mission2 = new Promise((resolve, reject) => {
        status = 2
        get(`api/chart/isolation/status/${range}/${status}`).then(res => {
          let list = res.data.data
          let data = []
          for (let i = 0; i < list.length; i++) {
            data.push(list[i].value - 0)
          }

          let item = {
            name: '阳性入院',
            type: 'line',
            // stack: 'Total',
            color: '#f06767',
            smooth: true,
            data: data
          }
          series.push(item)
          console.log("promise: 2ok")
          resolve()
        })
        .catch(err => {
          reject(err)
        })
      })

      // do mission
      Promise.all([mission0, mission1, mission2]).then(res => {
        console.log("promise: then", res)
        console.log("series", series)
        console.log("xAxis", xAxis)
        stackedLineChart.setOption({
          title: {
            text: '隔离人员状态统计'
          },
          tooltip: {
            trigger: 'axis'
          },
          legend: {
            right: '1%',
            data: ['正在隔离','解除隔离', '阳性入院']
          },
          // color: ['#edd600', '#9fe080', '#f06767'],
          grid: {
            left: '3%',
            right: '4%',
            bottom: '3%',
            containLabel: true
          },
          xAxis: {
            type: 'category',
            boundaryGap: false,
            data: xAxis
          },
          yAxis: {
            type: 'value'
          },
          series: series
        })
        stackedLineChart.resize();
      }).catch(err => {
        console.log("promise: err", err)
      })

    },

    //堆叠条形图初始化
    stackedColumnChartInit() {
      let element = document.getElementById('stacked-column-chart')
      let stackedColumnChart = echarts.init(element)

      // option data
      let xAxis = []
      let series = []

      // request parameter
      let range = 'week'
      let type = 0

      //mission list
      const mission0 = new Promise((resolve, reject) => {
        get(`api/chart/isolation/type/${range}/${type}`).then(res => {
          let list = res.data.data
          let data = []
          for (let i = 0; i < list.length; i++) {
            xAxis.push(list[i].name)
            data.push(list[i].value - 0)
          }
          let item = {
            name: '密接',
            type: 'bar',
            stack: '隔离人数',
            emphasis: {
              focus: 'series'
            },
            data: data
          }
          series.push(item)
          console.log("promise: 0ok")
          resolve()
        }).catch(err => {
          reject(err)
        })
      })
      const mission1 = new Promise((resolve, reject) => {
        type = 1
        get(`api/chart/isolation/type/${range}/${type}`).then(res => {
          let list = res.data.data
          let data = []
          for (let i = 0; i < list.length; i++) {
            data.push(list[i].value - 0)
          }
          let item = {
            name: '应隔离人员',
            type: 'bar',
            stack: '隔离人数',
            emphasis: {
              focus: 'series'
            },
            data: data
          }
          series.push(item)
          console.log("promise: 1ok")
          resolve()
        }).catch(err => {
          reject(err)
        })
      })
      const mission2 = new Promise((resolve, reject) => {
        type = 2
        get(`api/chart/isolation/type/${range}/${type}`).then(res => {
          let list = res.data.data
          let data = []
          for (let i = 0; i < list.length; i++) {
            data.push(list[i].value - 0)
          }
          let item = {
            name: '入境人员',
            type: 'bar',
            stack: '隔离人数',
            emphasis: {
              focus: 'series'
            },
            data: data
          }
          series.push(item)
          console.log("promise: 2ok")
          resolve()
        }).catch(err => {
          reject(err)
        })
      })
      const mission3 = new Promise((resolve, reject) => {
        type = 3
        get(`api/chart/isolation/type/${range}/${type}`).then(res => {
          let list = res.data.data
          let data = []
          for (let i = 0; i < list.length; i++) {
            data.push(list[i].value - 0)
          }
          let item = {
            name: '中高风险人员',
            type: 'bar',
            stack: '隔离人数',
            emphasis: {
              focus: 'series'
            },
            data: data
          }
          series.push(item)
          console.log("promise: 3ok")
          resolve()
        }).catch(err => {
          reject(err)
        })
      })
      const mission4 = new Promise((resolve, reject) => {
        get(`api/chart/isolation/num/${range}`).then(res => {
          let list = res.data.data
          let data = []
          for (let i = 0; i < list.length; i++) {
            data.push(list[i].value - 0)
          }
          let item = {
            name: '隔离人数',
            type: 'bar',
            barWidth: 15,
            emphasis: {
              focus: 'series'
            },
            data: data
          }
          series.push(item)
          console.log("promise: 4ok")
          resolve()
        }).catch(err => {
          reject(err)
        })
      })

      // do mission
      Promise.all([mission0, mission1, mission2, mission3, mission4]).then(res => {
        console.log("promise: then", res)
        console.log("series", series)
        console.log("xAxis", xAxis)        
        stackedColumnChart.setOption({
          title: {
            text: '每日新增隔离人员统计图'
          },
          tooltip: {
            trigger: 'axis',
            axisPointer: {
              type: 'shadow'
            }
          },
          legend: {
            right: '1%'
          },
          grid: {
            left: '3%',
            right: '4%',
            bottom: '3%',
            containLabel: true
          },
          xAxis: [
            {
              type: 'category',
              data: xAxis
            }
          ],
          yAxis: [
            {
              type: 'value'
            }
          ],
          series: series
        })
        stackedColumnChart.resize();
      })
          .catch(err => {
            console.log("promise: err", err)
          })

    },

    //圆环图初始化
    doughnutChartInit() {
      let element = document.getElementById('doughnut-chart')
      let doughnutChart = echarts.init(element)
      if (this.mode == 1) {
        get('/api/chart/isolation/way/all/1')
            .then(res => {
              doughnutChart.setOption({
                title: {
                  text: '隔离人员来源统计',
                  left: 'center'
                },
                tooltip: {
                  trigger: 'item'
                },
                legend: {
                  orient: 'vertical',
                  left: '5%',
                  top: '5%'
                },
                series: [
                  {
                    type: 'pie',
                    radius: ['40%', '70%'],
                    avoidLabelOverlap: false,
                    label: {
                      show: false,
                      position: 'center'
                    },
                    emphasis: {
                      label: {
                        show: true,
                        fontSize: '40',
                        fontWeight: 'bold'
                      }
                    },
                    labelLine: {
                      show: false
                    },
                    data: res.data.data
                  }
                ]
              })
            })
            .catch(err => {
              console.log(err);
            })
      } else {
        get('/api/chart/order/way')
            .then(res => {
              doughnutChart.setOption({
                title: {
                  text: '订单来源统计',
                  left: 'center'
                },
                tooltip: {
                  trigger: 'item'
                },
                legend: {
                  orient: 'vertical',
                  left: '5%',
                  top: '5%'
                },
                series: [
                  {
                    type: 'pie',
                    radius: ['40%', '70%'],
                    avoidLabelOverlap: false,
                    label: {
                      show: false,
                      position: 'center'
                    },
                    emphasis: {
                      label: {
                        show: true,
                        fontSize: '40',
                        fontWeight: 'bold'
                      }
                    },
                    labelLine: {
                      show: false
                    },
                    data: res.data.data
                  }
                ]
              })
            })
            .catch(err => {
              console.log(err);
            })
      }


    },

    //
    baseBarInit() {
      let orderStatistics = document.getElementById('basic-bar')
      let orderStatisticsChart = echarts.init(orderStatistics)

      get('/api/chart/order/orderNum/year/1')
          .then(res => {
            let monthList = []
            let valList = []
            for (let i = 0; i < res.data.data.length; i++) {
              monthList.push(res.data.data[i].name)
              valList.push(res.data.data[i].value)
            }
            orderStatisticsChart.setOption({
              title: {
                text: '近一年订单统计',
                left: 'center'
              },
              tooltip: {
                trigger: 'item',
                formatter: '{b} <br/> {c}单'
              },
              xAxis: {
                type: 'category',
                data: monthList
              },
              yAxis: {
                type: 'value'
              },
              series: [
                {
                  data: valList,
                  type: 'bar',
                  showBackground: true,
                  backgroundStyle: {
                    color: 'rgba(180, 180, 180, 0.2)'
                  }
                }
              ]
            })
          })
          .catch(err => {
            console.log(err);
          })
    },

    //订单来访地统计
    mapInit() {
      let map = document.getElementById('map-box')
      let mapChart = echarts.init(map)

      if (this.mode == 1) {
        get('/api/chart/isolation/income').then(res => {
          if (res.data.code == 200) {
            let valArr = []
            for (let i = 0; i < res.data.data.length; i++) {
              valArr.push(res.data.data[i].value)
            }
            let maxNum = Math.max(...valArr)
            mapChart.setOption({
              // 进行相关配置
              title: {
                text: '隔离人员来访地统计',
                left: 'center'
              },
              toolbox: {
                show: true,
                feature: {
                  mark: {show: true},
                  dataView: {show: true, readOnly: true},
                  restore: {show: true},
                  saveAsImage: {show: true}
                }
              },
              tooltip: {
                trigger: 'item',
                showDelay: 0,
                transitionDuration: 0.2,
                formatter: function (params) {
                  return params.seriesName + '<br/>' + params.name + ': ' + params.value;

                }
              },
              visualMap: {
                left: 'left',
                min: 0,
                max: maxNum + 10,
                inRange: {
                  color: [
                    '#ffffbf',
                    '#eaf7f8',
                    '#abd9e9',
                    '#74add1',
                    '#4575b4',
                    '#313695',
                    // '#fdae61',
                    // '#f46d43',
                    // '#d73027',
                    // '#a50026'
                  ]
                },
                text: ['High', 'Low'],
                calculable: true
              },
              geo: {
                // 这个是重点配置区
                map: "china", // 表示中国地图
                roam: true,
                label: {
                  normal: {
                    show: true, // 是否显示对应地名
                    textStyle: {
                      color: "#000",
                    },
                  },
                },
                zoom: 1.2,
                scaleLimit: {
                  min: 1,
                  max: 3.5,
                },
              },
              series: [
                {
                  type: "scatter",
                  coordinateSystem: "geo", // 对应上方配置
                },
                {
                  name: "来访数量", // 浮动框的标题
                  type: "map",
                  geoIndex: 0,
                  data: res.data.data
                },
              ],
            });
          }
        })
      }else {
        get('/api/chart/order/map')
            .then(res => {
              if (res.data.code == 200) {
                let valArr = []
                for (let i = 0; i < res.data.data.length; i++) {
                  valArr.push(res.data.data[i].value)
                }
                let maxNum = Math.max(...valArr)
                mapChart.setOption({
                  // 进行相关配置
                  title: {
                    text: '订单来访地统计',
                    left: 'center'
                  },
                  toolbox: {
                    show: true,
                    feature: {
                      mark: {show: true},
                      dataView: {show: true, readOnly: true},
                      restore: {show: true},
                      saveAsImage: {show: true}
                    }
                  },
                  tooltip: {
                    trigger: 'item',
                    showDelay: 0,
                    transitionDuration: 0.2,
                    formatter: function (params) {
                      return params.seriesName + '<br/>' + params.name + ': ' + params.value;

                    }
                  },
                  visualMap: {
                    left: 'left',
                    min: 0,
                    max: maxNum + 10,
                    inRange: {
                      color: [
                        '#ffffbf',
                        '#eaf7f8',
                        '#abd9e9',
                        '#74add1',
                        '#4575b4',
                        '#313695',
                        // '#fdae61',
                        // '#f46d43',
                        // '#d73027',
                        // '#a50026'
                      ]
                    },
                    text: ['High', 'Low'],
                    calculable: true
                  },
                  geo: {
                    // 这个是重点配置区
                    map: "china", // 表示中国地图
                    roam: true,
                    label: {
                      normal: {
                        show: true, // 是否显示对应地名
                        textStyle: {
                          color: "#000",
                        },
                      },
                    },
                    zoom: 1.2,
                    scaleLimit: {
                      min: 1,
                      max: 3.5,
                    },
                  },
                  series: [
                    {
                      type: "scatter",
                      coordinateSystem: "geo", // 对应上方配置
                    },
                    {
                      name: "订单数量", // 浮动框的标题
                      type: "map",
                      geoIndex: 0,
                      data: res.data.data
                    },
                  ],
                });
              }
            })
            .catch(err => {

            })
      }


    },

    //
    nightingaleChartInit() {
      let element = document.getElementById('nightingale-chart')
      let nightingaleChart = echarts.init(element)
      if (this.mode == 1) {
        let range = 'all'
        let type = 1
        get(`/api/chart/isolation/type/${range}/${type}`)
            .then(res => {
              nightingaleChart.setOption({
                title: {
                  text: '隔离人员类型',
                  left: 'center'
                },
                tooltip: {
                  trigger: 'item',
                  formatter: '{b} <br/> {c} ({d}%)'
                },
                legend: {
                  top: 'bottom',
                },
                series: [
                  {
                    type: 'pie',
                    radius: [20, 120],
                    center: ['50%', '50%'],
                    roseType: 'area',
                    itemStyle: {
                      borderRadius: 8
                    },
                    data: res.data.data
                  }
                ]
              })
            })
            .catch(err => {
              console.log(err);
            })
      } else {
        get('/api/chart/order/roomType')
            .then(res => {
              nightingaleChart.setOption({
                title: {
                  text: '订单房间类型',
                  left: 'center'
                },
                tooltip: {
                  trigger: 'item',
                  formatter: '{b} <br/> {c} ({d}%)'
                },
                legend: {
                  top: 'bottom',
                },
                series: [
                  {
                    type: 'pie',
                    radius: [20, 120],
                    center: ['50%', '50%'],
                    roseType: 'area',
                    itemStyle: {
                      borderRadius: 8
                    },
                    data: res.data.data
                  }
                ]
              })
            })
            .catch(err => {
              console.log(err);
            })
      }


    },

    //订单统计-盈利统计
    mixedLineAndBarInit() {
      let incomeList = []
      let dateList = []
      let orderNumList = []

      // 实例化
      let contrast = document.getElementById('mixed-line-and-bar')
      let contrastChart = echarts.init(contrast)

      function f1() {
        get('/api/chart/order/income/day/7')
            .then(res => {
              for (let i = 0; i < res.data.data.length; i++) {
                incomeList.push(res.data.data[i].value) //整理出符合的value值到新数组
                genFunction.next() //next
              }
            })
            .catch(err => {
              console.log(err);
            })

      }

      function f2() {
        get('/api/chart/order/orderNum/day/7')
            .then(res => {
              for (let i = 0; i < res.data.data.length; i++) {
                dateList.push(res.data.data[i].name)//整理后台数据到新数组
                orderNumList.push(res.data.data[i].value)//整理后台数据到新数组
              }

              // 渲染数据，创建可视化视图
              contrastChart.setOption({
                tooltip: {
                  trigger: 'axis',
                  axisPointer: {
                    type: 'cross',
                    crossStyle: {
                      color: '#999'
                    }
                  }
                },
                legend: {
                  data: ['订单统计', '盈利统计']
                },
                xAxis: [
                  {
                    type: 'category',
                    data: dateList,
                    axisPointer: {
                      type: 'shadow'
                    }
                  }
                ],
                yAxis: [
                  {
                    type: 'value',
                    name: '订单统计',
                    nameTextStyle: {
                      color: 'skyblue',
                      fontSize: 16,
                      align: 'center'
                    },
                    nameGap: 30,
                    axisLabel: {
                      formatter: '{value} 单'
                    }
                  },
                  {
                    type: 'value',
                    name: '盈利统计',
                    nameTextStyle: {
                      color: 'skyblue',
                      fontSize: 16,
                      align: 'center'
                    },
                    nameGap: 30,
                    axisLabel: {
                      formatter: '{value} 元'
                    }
                  }
                ],
                series: [
                  {
                    name: '订单统计',
                    type: 'bar',
                    barWidth: 30,//柱图宽度
                    data: orderNumList
                  },
                  {
                    name: '盈利统计',
                    type: 'line',
                    yAxisIndex: 1,
                    data: incomeList
                  }
                ]
              })
              genFunction.next() //next
            })
            .catch(err => {
              console.log(err);
            })


      }

      // 生成器函数
      function* gen() {
        yield f1()
        yield f2()

      }

      let genFunction = gen()
      genFunction.next()


    }

  }
}
</script>

<style scoped>
.main-content {
  display: flex;
  justify-content: space-between;
  flex-wrap: wrap;
}

.main-content > div {
  display: flex;
  align-items: center;
  justify-content: center;
  width: 46%;
  height: 430px;
  margin: 2%;
  background: #FFFFFF;
  padding: 15px 0px;
}

/* #stacked-column-chart, #stacked-line-chart {
  min-width: 450px;
} */

#map-box {
  width: 100%;
}
</style>