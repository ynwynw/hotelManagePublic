<template>

  <div class="test-main">

    <div class="d-flex justify-content-between" style="padding: 10px;">
      <div class="block search">
        <span class="demonstration">日期：</span>
        <el-date-picker
            v-model="searchParams.dateRange"
            value-format="yyyy-MM-dd"
            type="daterange"
            range-separator="至"
            start-placeholder="开始日期"
            end-placeholder="结束日期">
        </el-date-picker>
      </div>
      <div class="block search">
        <p>用户ID：</p>
        <el-input v-model="searchParams.uid" placeholder="请输入用户ID"></el-input>
      </div>
      <div class="block search">
        <p>被检人姓名：</p>
        <el-input v-model="searchParams.name" placeholder="请输入被检人姓名"></el-input>
      </div>
      <div class="block search">
        <p>检测结果：</p>
        <el-select v-model="searchParams.result" placeholder="请选择">
          <el-option
              v-for="item in resultOptions"
              :key="item.value"
              :label="item.label"
              :value="item.value">
          </el-option>
        </el-select>
      </div>
      <el-button-group class="d-flex justify-content">
        <el-button type="primary" icon="el-icon-search" @click="getTestList">查询</el-button>
        <el-button type="info" icon="el-icon-refresh" @click="reset">重置</el-button>
      </el-button-group>
    </div>

    <!-- 导出Excel按钮 -->
    <div class="d-flex justify-content-end" style="padding: 10px;">
      <el-button type="primary" style="width: 100px;" @click="addData">添加</el-button>
      <el-button type="success" @click="downloadTemplate">下载Excel模板</el-button>
      <el-upload
          ref="upload"
          class="ml-10"
          action="#"
          :show-file-list="false"
          :http-request="uploadFile">
        <el-button type="success">导入Excel</el-button>
      </el-upload>
    </div>

    <!-- 表格 -->
    <el-table
        stripe
        border
        :data="testData"
        class="global-table"
        style="width: 100%;overflow: auto;">
      <el-table-column
          align="center"
          prop="name"
          label="受检人姓名">
      </el-table-column>
      <el-table-column
          align="center"
          prop="idCard"
          label="受检人身份证"
          width="170px">

      </el-table-column>
      <el-table-column
          align="center"
          prop="checkType"
          label="检测类型">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.checkType==0" type="warning">体温</el-tag>
          <el-tag v-if="scope.row.checkType==1">核酸</el-tag>
        </template>
      </el-table-column>
      <el-table-column
          align="center"
          prop="checkRes"
          label="检测结果">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.checkRes==0" type="success">阴性</el-tag>
          <el-tag v-else-if="scope.row.checkRes==1" type="danger">阳性</el-tag>
          <el-tag v-else-if="scope.row.checkRes > 37.3" type="danger">{{ scope.row.checkRes }}°</el-tag>
          <el-tag v-else type="success">{{ scope.row.checkRes }}°</el-tag>
        </template>
      </el-table-column>
      <el-table-column
          align="center"
          prop="checkTime"
          :formatter="eTableDateTime"
          label="检测时间">
      </el-table-column>
      <el-table-column
          align="center"
          prop="createTime"
          :formatter="eTableDateTime"
          label="创建时间">
      </el-table-column>
      <el-table-column
          label="操作"
          width="240"
          align="center">
        <template slot-scope="scope">
          <el-button
              size="mini"
              type="primary"
              @click="handleModify(scope.$index, scope.row)">编辑
          </el-button>
          <el-button
              size="mini"
              type="danger"
              @click="handleDelete(scope.$index, scope.row)">删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>


    <!-- 分页器 -->
    <el-pagination
        class="mt-10 mb-10"
        background
        @current-change="handleCurrentChange"
        @prev-click="prevPage"
        @next-click="nextPage"
        layout="prev, pager, next"
        :total="pageNum">
    </el-pagination>

    <!-- 弹出框 -->
    <el-dialog
        :title="title"
        :visible.sync="dialogVisible"
        width="570px">
      <div class="content">
        <div class="d-flex align-items-center mb-15">
          <p class="w-100 text-left">受检人ID:</p>
          <el-input
              style="width: 350px;"
              placeholder="请输入受检人ID"
              v-model="form.uId"
              @blur="getIsolationInfo"
              :disabled="testValue == 'modify'"
              clearable>
          </el-input>
        </div>
        <div class="d-flex align-items-center mb-15" v-if="isolationInfo.name">
          <p class="w-100 text-left">受检人姓名:</p>
          <el-input
              style="width: 350px;"
              placeholder="请输入受检人姓名"
              v-model="isolationInfo.name"
              :disabled="true"
              clearable>
          </el-input>
        </div>
        <div class="d-flex align-items-center mb-15" v-if="isolationInfo.idCard">
          <p class="w-100 text-left">受检人身份证:</p>
          <el-input
              style="width: 350px;"
              placeholder="请输入受检人身份证"
              v-model="isolationInfo.idCard"
              :disabled="true"
              clearable>
          </el-input>
        </div>
        <div v-if="testValue == 'modify'" class="d-flex align-items-center mb-15">
          <p class="w-100 text-left">受检人姓名:</p>
          <el-input
              style="width: 350px;"
              placeholder="请输入受检人姓名"
              v-model="form.name"
              disabled
              clearable>
          </el-input>
        </div>
        <div class="d-flex align-items-center mb-15">
          <p class="w-100 text-left">检测类型：</p>
          <el-select v-model="form.checkType" placeholder="请选择">
            <el-option
                v-for="item in testOptions"
                :key="item.value"
                :label="item.label"
                :value="item.value">
            </el-option>
          </el-select>
        </div>
        <div class="d-flex align-items-center mb-15">
          <p class="w-100 text-left">检测结果：</p>
          <el-input
              v-if="form.checkType == 0"
              style="width: 350px;"
              placeholder="请输入检测温度"
              v-model="form.checkRes"
              clearable>
          </el-input>
          <el-select v-else v-model="form.checkRes" placeholder="请选择">
            <el-option
                v-for="item in resultOptions"
                :key="item.value"
                :label="item.label"
                :value="item.value">
            </el-option>
          </el-select>
        </div>
        <div class="d-flex align-items-center">
          <p class="w-100 text-left">检测时间:</p>
          <el-date-picker
              v-model="form.checkTime"
              type="datetime"
              placeholder="选择日期时间">
          </el-date-picker>
        </div>

      </div>
      <span slot="footer" class="dialog-footer">
                <el-button @click="dialogVisible = false">取 消</el-button>
                <el-button type="primary" @click="confirm">确 定</el-button>
            </span>
    </el-dialog>


  </div>

</template>

<script>
import {get, post} from "../utils/request";
import {dateTimeFormat} from "../utils/dateTime";

export default {
  name: "DailyTest",
  data() {
    return {
      isolationInfo: {
        name: undefined,
        idCard: undefined
      },
      searchParams: { // 筛选框数据
        uid: '',
        name: '',
        dateRange: '',
        beginTime: '',
        endTime: '',
        result: '',
      },
      testData: [], // 列表数据
      resultOptions: [
        {
          value: 0,
          label: '阴性'
        },
        {
          value: 1,
          label: '阳性'
        }
      ],
      testOptions: [
        {
          value: 0,
          label: '体温'
        },
        {
          value: 1,
          label: '核酸'
        }
      ],
      pageNum: 0,
      dialogVisible: false,
      title: '', // 弹出框标题
      testValue: '', //添加或编辑的标识
      form: { // 弹出框表单数据
        uId: '',
        name: '',
        checkType: '',
        checkRes: '',
        checkTime: '',
      },
      fileRaw: ''
    }

  },
  watch: {
    "searchParams.dateRange"(val, oldVal) {//普通的watch监听

      if (val) {
        this.searchParams.beginTime = val[0]
        this.searchParams.endTime = val[1]
      }

    },
  },
  mounted() {
    this.getTestList()
  },
  methods: {
    getIsolationInfo() {
      get("api/isolationInfo/get/" + this.form.uId).then(res => {
        if (res.data.data.name || res.data.data.idCard) {
          this.isolationInfo.name = res.data.data.name
          this.isolationInfo.idCard = res.data.data.idCard
          this.$message({
            message: '查询成功',
            type: 'success',
            duration: 1000
          });
        }

      })
    },
    // 页面初次渲染函数
    getTestList() {
      const data = { // 设置data数据
        page: {
          page: 1,
          size: 10
        },
        uid: this.searchParams.uid,
        name: this.searchParams.name,
        checkRes: this.searchParams.result,
        beginTime: this.searchParams.beginTime,
        endTime: this.searchParams.endTime,
      }
      this.testListRequest(data)
    },

    //选择页码
    handleCurrentChange(num) {
      const data = {
        page: {
          page: num,
          size: 10
        },
        uid: this.searchParams.uid,
        name: this.searchParams.name,
        checkRes: this.searchParams.result,
        beginTime: this.searchParams.beginTime,
        endTime: this.searchParams.endTime,
      }
      this.testListRequest(data)
    },

    //上一页
    prevPage(num) {
      const data = {
        page: {
          page: num,
          size: 10
        },
        uid: this.searchParams.uid,
        name: this.searchParams.name,
        checkRes: this.searchParams.result,
        beginTime: this.searchParams.beginTime,
        endTime: this.searchParams.endTime,
      }
      this.testListRequest(data)
    },

    //下一页
    nextPage(num) {
      const data = {
        page: {
          page: num,
          size: 10
        },
        uid: this.searchParams.uid,
        name: this.searchParams.name,
        checkRes: this.searchParams.result,
        beginTime: this.searchParams.beginTime,
        endTime: this.searchParams.endTime,
      }
      this.testListRequest(data)
    },

    // 获取列表函数
    testListRequest(data) {
      post('/api/checkRecords/page', data)
          .then(res => {
            console.log(res);
            this.testData = res.data.data.records
            this.pageNum = res.data.data.total
          })
          .catch(err => {
            console.error(err);
          })
    },

    // 重置函数
    reset() {
      this.searchParams = {
        name: '',
        uid: '',
        dateRange: '',
        beginTime: '',
        endTime: '',
        result: '',
      }
    },

    // 添加函数
    addData() {
      this.form = {}
      this.dialogVisible = true
      this.title = '添加检测记录'
      this.testValue = 'manually'
    },

    // 下载模板函数
    downloadTemplate() {
      const config = {
        responseType: 'blob'
      }
      const data = {}
      post('/api/checkRecords/template', data, config)
          .then(res => {
            console.log(res);
            const aLink = document.createElement('a')
            const blob = new Blob([res.data], {type: 'mimeMap.xlsx'})
            const patt = new RegExp('filename=([^;]+\\.[^\\.;]+);*')
            const contentDisposition = decodeURI(res.headers['content-disposition'])
            const result = patt.exec(contentDisposition)
            let fileName = result[1]
            fileName = fileName.replace(/\"/g, '')
            aLink.href = URL.createObjectURL(blob)
            aLink.setAttribute('download', fileName) // 设置下载文件名称
            document.body.appendChild(aLink)
            aLink.click()
            document.body.appendChild(aLink)
          })
          .catch(err => {
            console.error(err);
          })
    },

    // 弹出框确定按钮函数
    confirm() {
      const value = this.testValue
      const data = {
        uId: this.form.uId,
        checkRes: this.form.checkRes,
        checkTime: this.form.checkTime,
        checkType: this.form.checkType,
      }
      if (value == 'manually') {
        this.testRequest(value, data)
      } else if (value == 'modify') {
        this.testRequest(value, data)
      }

    },

    testRequest(url, data) {
      post(`/api/checkRecords/${url}`, data)
          .then(res => {
            console.log(res);
            if (res.data.code === "200") {
              this.$message({
                message: '操作成功！',
                type: 'success',
                duration: 1000
              });
              this.$router.go(0);
            } else {
              this.$message({
                message: res.data.msg,
                type: 'warning',
                duration: 1000
              });
            }
          })
          .catch(err => {
            console.error(err);
          })


    },

    // 文件上传函数,导入Excel文件数据
    uploadFile(params) {
      const config = {
        headers: {
          'Content-Type': 'multipart/form-data'
        },
      }
      const file = params.file
      const fd = new FormData();//通过form数据格式来传
      fd.append("file", file); //传文件
      // fd.append("key", "head");

      post('/api/checkRecords/upload', fd, config)
          .then(res => {
            console.log(res);
            if (res.data.code == 200) {
              this.$message({
                message: res.data.data,
                type: 'success',
                duration: 2000
              });
              setTimeout(() => {
                this.$router.go(0)
              }, 1000)
            }
          })
          .catch(err => {
            console.error(err);
          })
    },

    // 日期时间格式化
    eTableDateTime(row, column, cellValue, index) {
      const dateTime = new Date(cellValue) // Date实例

      const YYYY = dateTime.getFullYear() // 获取当前年份
      const MM = dateTime.getMonth() + 1 // 获取当前月份
      const DD = dateTime.getDate() // 获取当前天数
      const hh = this.fillPrefix(dateTime.getHours()) // 获取当前小时，并判断是否需要补零
      const mm = this.fillPrefix(dateTime.getMinutes()) // 获取当前分钟，并判断是否需要补零
      const ss = this.fillPrefix(dateTime.getSeconds()) // 获取当前秒数，并判断是否需要补零
      // 返回格式化之后的当前时间
      return `${YYYY}-${MM}-${DD} ${hh}:${mm}:${ss}`
      // return `${YYYY}-${MM}-${DD}`
    },

    // 补零函数
    fillPrefix(val) {
      return val > 9 ? val : `0${val}` // 个位数时间进行补零操作
    },

    // 修改记录函数
    handleModify(index, row) {
      this.form = row
      this.dialogVisible = true
      this.title = '修改检测记录'
      this.testValue = 'modify'
    },

    // 删除函数
    handleDelete(index, row) {
      get(`/api/checkRecords/delete/${row.id}`)
          .then(res => {
            if (res.data.code == 200) {
              this.$message({
                message: '删除成功',
                type: 'success',
                duration: 1000
              });
              setTimeout(() => {
                this.$router.go(0)
              }, 1000)
            }
          })
          .catch(err => {
            console.error(err);
          })

    },


  }

}
</script>

<style scoped>
.test-main {
  display: flex;
  flex-direction: column;
  /* justify-content: space-between; */
  width: 100%;
  min-width: 1000px;
  height: 100%;
  background: #FFFFFF;
  box-shadow: 0px 5px 30px 0px rgba(22, 115, 255, 0.1);
}

.search {
  display: flex;
  flex-direction: row;
  align-items: center;
  margin: 0 10px;
  font-size: 14px;
  white-space: nowrap;
}
</style>