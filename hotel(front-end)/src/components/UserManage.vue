<template>
  <div class="hotel-main">
    <div>
      <div class="d-flex justify-content" style="padding: 10px;">
        <div class="ml-10 mr-10 d-flex align-items-center">
          <p class="mr-5" style="width: 70px;font-size: 14px">用户名:</p>
          <el-input
              placeholder="请输入用户名"
              prefix-icon="el-icon-search"
              v-model="username">
          </el-input>
        </div>
        <div class="ml-10 mr-10 d-flex align-items-center">
          <p class="mr-5" style="width: 70px;font-size: 14px">昵称:</p>
          <el-input
              placeholder="请输入昵称"
              prefix-icon="el-icon-search"
              v-model="nickname">
          </el-input>
        </div>
        <el-button @click="getUserList" size="small">查询</el-button>
      </div>
      <div class="d-flex justify-content-end" style="padding: 10px;">
        <el-button type="primary" style="width: 100px;" @click="addUser">添加</el-button>
      </div>

      <el-table
          stripe
          size="small "
          :data="hotelData"
          style="width: 100%"
          border>
        <el-table-column
            type="index"
            label="序号"
            width="60"
            align="center">
        </el-table-column>
        <el-table-column
            prop="username"
            label="用户名"
            width="200">
        </el-table-column>
        <el-table-column
            prop="nickname"
            label="昵称">
        </el-table-column>
        <el-table-column
            prop="phone"
            label="电话">
        </el-table-column>
        <el-table-column
            prop="dept"
            label="部门">
          <template scope="scope">
            {{ scope.row.dept | mapFormat(deptMap) }}
          </template>
        </el-table-column>
        <el-table-column
            label="操作"
            width="240"
            align="center">
          <template slot-scope="scope">
            <el-button
                size="mini"
                @click="handleCheck(scope.$index, scope.row)">查看
            </el-button>
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
    </div>


    <el-pagination
        class="mb-15"
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
          <p class="w-100 text-left">部门:</p>
          <el-cascader ref="cascader"
                       style="width: 350px;"
                       v-model="form.dept"
                       :disabled="userValue == 'check'"
                       :options="deptList"
                       :props="{
                         checkStrictly: true,
                         expandTrigger: 'hover',
                         emitPath: false,
                         value: 'id',
                         label: 'name'
                       }">
            <template slot-scope="{ node, data }">
              <div @click="cascaderClick(data)">
                <span>{{ data.name }}</span>
                <span v-if="!node.isLeaf"> ({{ data.children.length }}) </span>
              </div>
            </template>
          </el-cascader>

        </div>
        <div class="d-flex align-items-center mb-15">
          <p class="w-100 text-left">用户名:</p>
          <el-input
              style="width: 350px;"
              placeholder="请输入用户名"
              v-model="form.username"
              :disabled="userValue == 'check'"
              clearable>
          </el-input>
        </div>
        <div class="d-flex align-items-center mb-15">
          <p class="w-100 text-left">昵称:</p>
          <el-input
              style="width: 350px;"
              placeholder="请输入昵称"
              v-model="form.nickname"
              :disabled="userValue == 'check'"
              clearable>
          </el-input>
        </div>
        <div class="d-flex align-items-center mb-15">
          <p class="w-100 text-left">手机号:</p>
          <el-input
              style="width: 350px;"
              placeholder="请输入手机号"
              v-model="form.phone"
              :disabled="userValue == 'check'"
              clearable>
          </el-input>
        </div>
        <div class="d-flex align-items-center">
          <p class="w-100 text-left">密码:</p>
          <el-input
              type="password"
              style="width: 350px;"
              placeholder="请输入用户密码"
              v-model="form.password"
              :disabled="userValue == 'check'"
              clearable>
          </el-input>
        </div>

      </div>
      <span slot="footer" class="dialog-footer">
            <el-button @click="dialogVisible = false">取 消</el-button>
            <el-button type="primary" @click="confirm" v-if="userValue != 'check'">确 定</el-button>
        </span>
    </el-dialog>

  </div>

</template>

<script>
import {get, post} from "../utils/request";

export default {
  name: "HotelManage",
  data() {
    return {
      username: undefined,
      nickname: undefined,
      deptList: undefined,
      deptMap: {},
      name: '',
      address: undefined,
      hotelData: [],
      title: '',
      page: '',
      departData: '',
      hotelName: '',
      hotelAddress: '',
      userValue: '',
      hotelId: '',
      deptId: '',
      parking: '',
      pageNum: 0,
      dialogVisible: false,
      userList: [],
      tempPassword: undefined,
      form: {
        id: '',
        dept: '',
        nickname: '',
        username: undefined,
        phone: undefined,
        password: undefined
      }
    }
  },
  filters: {
    mapFormat(val, map) {
      console.log(val)
      console.log(map)
      return map[val]
    }
  },
  methods: {
    //获取用户列表
    getUserList() {
      let data = {
        page: {
          page: 1,
          size: 10
        },
        username: this.username,
        nickname: this.nickname
      }
      let dept = {}
      //获取用户列表
      this.userListRequest(data)
    },

    //获取用户列表请求
    userListRequest(data) {
      post('/api/user/page', data)
          .then(res => {
            console.log(res);
            this.hotelData = res.data.data.records
            this.pageNum = res.data.data.total
          })
          .catch(err => {
            console.log(err);
          })
    },

    //删除用户方法
    handleDelete(index, row) {
      get('/api/user/delete/' + row.id)
          .then(res => {
            console.log(res);
            this.$message({
              message: '删除成功',
              type: 'success',
              duration: 1000
            });
            setTimeout(() => {
              this.$router.go(0)
            }, 1000)
          })
          .catch(err => {
            console.log(err);
          })
    },

    getDeptList() {
      let data = {
        page: {
          page: 1,
          size: 9999999
        },
      }
      post("/api/dept/list", data).then(res => {
        this.deptList = this.getTreeData(res.data.data)
        // console.log(this.deptList)
      })
      post("/api/dept/page", data).then(res => {
        let list = res.data.data.records
        // console.log("list===",list)
        list.forEach(row => {
          // console.log("listitem===",row)
          this.deptMap[row.id] = row.name
        })
        // console.log("deptmap===",this.deptMap)
      })

    },

    getTreeData(data) {
      for (var i = 0; i < data.length; i++) {
        if (data[i].children.length < 1) {
          // children若为空数组，则将children设为undefined
          data[i].children = undefined;
        } else {
          // children若不为空数组，则继续 递归调用 本方法
          this.getTreeData(data[i].children);
        }
      }
      return data;
    },

    cascaderClick(nodeData) {
      this.addrCode = nodeData.id;
      this.$refs.cascader.checkedValue = nodeData.id;
      this.$refs.cascader.computePresentText();
      this.$refs.cascader.toggleDropDownVisible(false);
      this.$message({
        message: '已选择：' + nodeData.name,
        type: 'success',
        duration: 1000
      });
    },

    //增加用户方法
    addUser() {
      this.form = {}
      this.dialogVisible = true
      this.title = '添加用户'
      this.userValue = 'add'
      this.getUserList()
    },

    handleCheck(index, row) {
      this.userValue = 'check'
      this.dialogVisible = true
      this.title = '查看用户信息'
      this.form = row
    },

    //修改用户信息
    handleModify(index, row) {
      console.log(index);
      console.log(row);
      this.form = row
      this.tempPassword = this.form.password
      this.dialogVisible = true
      this.title = '修改用户信息'
      this.userValue = 'modify'
    },

    //
    confirm() {
      let value = this.userValue
      let data = {
        id: undefined,
        dept: this.form.dept,
        nickname: this.form.nickname,
        username: this.form.username,
        phone: this.form.phone,
        password: this.form.password
      }
      if (value == 'add') {
        this.userRequest(value, data)
      } else if (value == 'modify') {
        data.id = this.form.id
        if (this.tempPassword == this.form.password) {
          this.form.password = undefined
        }
        // data.deptId = this.deptId
        this.userRequest(value, data)
      }
    },

    //请求方法
    userRequest(url, data) {
      post('/api/user/' + url, data)
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
            console.log(err);
          })
    },

    //选择页码
    handleCurrentChange(num) {
      let data = {
        page: {
          page: num,
          size: 10
        },
        username: this.username,
        nickname: this.nickname
      }
      this.userListRequest(data)
    },

    //上一页
    prevPage(num) {
      let data = {
        page: {
          page: num,
          size: 10
        },
        username: this.username,
        nickname: this.nickname
      }
      this.userListRequest(data)

    },

    //下一页
    nextPage(num) {
      let data = {
        page: {
          page: num,
          size: 10
        },
        username: this.username,
        nickname: this.nickname
      }
      this.userListRequest(data)
    },


  },
  created() {
    this.getDeptList()
  },
  mounted() {

    this.getUserList()
  },

}
</script>

<style scoped>
.hotel-main {
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  width: 100%;
  height: 100%;
  background: #FFFFFF;
  box-shadow: 0px 5px 30px 0px rgba(22, 115, 255, 0.1);
}
</style>
