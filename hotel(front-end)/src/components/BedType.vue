<template>
  <div class="bed-main">
    <div class="d-flex justify-content-end" style="padding: 10px;">
      <el-button type="primary" style="width: 100px;" @click="addBed">添加</el-button>
    </div>
    <el-table
        stripe
        border
        :data="bedData"
        class="global-table"
        style="width: 100%;overflow: auto;">
      <el-table-column
          align="center"
          prop="name"
          label="名称">
      </el-table-column>
      <el-table-column
          align="center"
          prop="specification"
          label="规格">
      </el-table-column>
      <el-table-column
          align="center"
          prop="maxLoad"
          label="最大容量">
      </el-table-column>
      <el-table-column
          align="center"
          prop="hardness"
          label="硬度">
        <template slot-scope="scope">
          <div class="cell" v-if="scope.row.hardness == '0'">超软</div>
          <div class="cell" v-if="scope.row.hardness == '1'">偏软</div>
          <div class="cell" v-if="scope.row.hardness == '2'">适中</div>
          <div class="cell" v-if="scope.row.hardness == '3'">偏硬</div>
          <div class="cell" v-if="scope.row.hardness == '4'">硬</div>
        </template>
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
      <div class="contont">
        <div class="d-flex align-items-center mb-15">
          <p class="w-100 text-left">名称:</p>
          <el-input
              style="width: 350px;"
              placeholder="请输入床的名称"
              v-model="form.name"
              clearable>
          </el-input>
        </div>
        <div class="d-flex align-items-center mb-15">
          <p class="w-100 text-left">规格:</p>
          <el-input
              style="width: 350px;"
              placeholder="请输入床的规格"
              v-model="form.specification"
              clearable>
          </el-input>
        </div>
        <div class="d-flex align-items-center mb-15">
          <p class="w-100 text-left">最大容量:</p>
          <el-input-number
              v-model="form.maxLoad"
              :min="1"
              :max="6">
          </el-input-number>
        </div>
        <div class="d-flex align-items-center mb-15">
          <p class="w-100 text-left">硬度:</p>
          <div>
            <el-radio v-model="form.hardness" label="0">超软</el-radio>
            <el-radio v-model="form.hardness" label="1">偏软</el-radio>
            <el-radio v-model="form.hardness" label="2">适中</el-radio>
            <el-radio v-model="form.hardness" label="3">偏硬</el-radio>
            <el-radio v-model="form.hardness" label="4">硬</el-radio>
          </div>
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

export default {
  name: "BedType",
  data() {
    return {
      pageNum: 0,
      bedData: [],
      dialogVisible: false,
      bedId: '',
      title: '',
      bedValue: '',
      bedName: '',
      bedSpec: '',
      num: '',
      bedStatus: '1',
      form: {
        id: undefined,
        name: undefined,
        specification: undefined,
        maxLoad: undefined,
        hardness: undefined,
      }
    }
  },
  methods: {
    getBedList() {
      let data = {
        page: {
          page: 1,
          size: 10
        },
      }
      this.bedListRequest(data)
    },
    //获取酒店列表请求
    bedListRequest(data) {
      post('/api/bedType/page', data)
          .then(res => {
            console.log(res);
            this.bedData = res.data.data.records
            this.pageNum = res.data.data.total
          })
          .catch(err => {
            console.log(err);
          })
    },

    //选择页码
    handleCurrentChange(num) {
      let data = {
        page: {
          page: num,
          size: 10
        }
      }
      this.hotelListRequest(data)
    },

    //上一页
    prevPage(num) {
      let data = {
        page: {
          page: num,
          size: 10
        }
      }
      this.hotelListRequest(data)

    },

    //下一页
    nextPage(num) {
      let data = {
        page: {
          page: num,
          size: 10
        }
      }
      this.hotelListRequest(data)
    },


    //添加按钮
    addBed() {
      this.form = {
        name: undefined,
        specification: undefined,
        maxLoad: undefined,
        hardness: undefined,
      }
      this.title = '添加床'
      this.bedValue = 'add'
      this.dialogVisible = true
    },
    //修改床信息
    handleModify(index, row) {
      console.log(index);
      console.log(row);
      this.form = row
      this.bedId = row.id
      this.title = '修改床信息'
      this.bedValue = 'modify'
      this.dialogVisible = true
    },
    //删除床方法
    handleDelete(index, row) {
      get('/api/bedType/delete/' + row.id)
          .then(res => {
            console.log(res);
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
            console.log(err);
          })
    },

    confirm() {
      let value = this.bedValue
      let data = this.form
      if (value == 'add') {
        this.bedRequest(value, data)
      } else if (value == 'modify') {
        data.id = this.bedId
        this.bedRequest(value, data)
      }
    },
    bedRequest(url, data) {
      post('/api/bedType/' + url, data)
          .then(res => {
            console.log(res);
            if (res.data.code == 200) {
              this.$message({
                message: '操作成功！',
                type: 'success',
                duration: 1000
              });
              this.$router.go(0);
            } else if (res.data.code == 1001) {
              this.$message({
                message: res.data.msg,
                type: 'warning',
                duration: 1000
              });

            }
          })
          .catch(err => {
            console.log(err);
          })
    }

  },
  mounted() {
    this.getBedList()
  }
}
</script>

<style scoped>
.bed-main {
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  width: 100%;
  height: 100%;
  background: #FFFFFF;
  box-shadow: 0px 5px 30px 0px rgba(22, 115, 255, 0.1);
}
</style>