<template>
  <div class="depart-main">
    <div class="d-flex justify-content-end mb-10" style="padding: 10px;">
      <el-button type="primary" style="width: 100px;" @click="addDepart">添加</el-button>
    </div>

    <el-table
        :data="menuData"
        style="width: 100%"
        row-key="id"
        border
        :tree-props="{ hasChildren: 'hasChildren',children: 'children',}">
      <el-table-column
          prop="name"
          label="菜单名"
          width="300">
      </el-table-column>
      <el-table-column
          label="路径"
          align="center"
          width="120"
          prop="path"
      >
      </el-table-column>
      <el-table-column
          label="图标"
          align="center"
          width="120">
        <template slot-scope="scope">
          <i :class="scope.row.icon"></i>
        </template>
      </el-table-column>
      <el-table-column
          label="排序"
          align="center"
          width="120"
          prop="sort"
      ></el-table-column>
      <el-table-column
          label="状态"
          align="center"
          width="120">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.visible==1" type="success">正常</el-tag>
          <el-tag v-if="scope.row.visible==0" type="danger">停用</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="操作">
        <template slot-scope="scope">
          <el-button
              size="mini"
              type="primary"
              @click="handleModify(scope.$index, scope.row)">编辑
          </el-button>
          <el-button
              :disabled="scope.row.children.length !== 0"
              size="mini"
              type="danger"
              @click="handleDelete(scope.$index, scope.row)">删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 弹出框 -->
    <el-dialog
        :title="title"
        :visible.sync="dialogVisible"
        width="570px">
      <div class="contont">
        <div class="d-flex align-items-center mb-15">
          <p class="w-100 text-left">父菜单:</p>
          <el-cascader ref="cascader"
                       placeholder="请选择父菜单"
                       style="width: 350px;"
                       v-model="form.pId"
                       :options="menuList"
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
          <p class="w-100 text-left">菜单名称:</p>
          <el-input
              style="width: 350px;"
              placeholder="请输入菜单名称"
              v-model="form.name"
              clearable>
          </el-input>
        </div>
        <div class="d-flex align-items-center mb-15">
          <p class="w-100 text-left">路由路径:</p>
          <el-input
              style="width: 350px;"
              placeholder="请输入路由路径"
              v-model="form.path"
              clearable>
          </el-input>
        </div>
        <div class="d-flex align-items-center mb-15">
          <p class="w-100 text-left">导航图标:</p>
          <el-input
              style="width: 350px;"
              placeholder="请输入导航图标"
              v-model="form.icon"
              clearable>
          </el-input>
          <i :class="form.icon" style="margin-left: 6px"></i>
        </div>
        <div class="d-flex align-items-center mb-15">
          <p class="w-100 text-left">角色权限:</p>
          <el-select style="width: 350px;" v-model="form.role" placeholder="请选择权限" multiple clearable>
            <el-option v-for="item in roleOptions" :label="item.label" :value="item.value"></el-option>
          </el-select>
        </div>
        <div class="d-flex align-items-center mb-15">
          <p class="w-100 text-left">部门权限:</p>
          <el-cascader ref="cascader"
                       placeholder="请选择部门"
                       style="width: 350px;"
                       v-model="form.deptId"
                       :options="deptList"
                       :props="{
                         checkStrictly: true,
                         expandTrigger: 'hover',
                         emitPath: false,
                         value: 'id',
                         label: 'name',
                         multiple: true
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
          <el-tag type="success" style="width: 100%;">tips: 角色权限和部门权限共存</el-tag>
        </div>
        <div class="d-flex align-items-center mb-15">
          <p class="w-100 text-left">排序:</p>
          <el-input-number v-model="form.sort" :min="0" label="排序"></el-input-number>
        </div>
        <div style="margin-top: 20px" class="d-flex align-items-center">
          <p class="w-100 text-left">状态:</p>
          <div>
            <el-radio v-model="form.visible" label="1">正常</el-radio>
            <el-radio v-model="form.visible" label="0">停用</el-radio>
          </div>
        </div>
        <div class="d-flex align-items-center mb-15">
          <p class="w-100 text-left">备注:</p>
          <el-input
              style="margin-top: 20px"
              type="textarea"
              :autosize="{ minRows: 2, maxRows: 10}"
              placeholder="请输入备注"
              v-model="form.remark">
          </el-input>
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
  name: "MenuManage",
  data() {
    return {
      menuList: [],
      deptList: [],
      menuData: [],
      title: '',  //弹出框标题名
      dialogVisible: false, //添加弹出框
      value: '',
      departName: '',
      departId: '',
      menuValue: '',
      deptStatus: '1',  //状态
      pId: '',
      authority: '', //权限select
      role: '',
      form: {
        id: undefined,
        pId: undefined,
        name: undefined,
        deptId: undefined,
        visible: "1",
        path: undefined,
        icon: undefined,
        sort: undefined,
        remark: undefined,
        role: undefined
      },
      roleOptions: [
        {
          label: '管理员',
          value: 'admin'
        },
        {
          label: '酒店管理员',
          value: 'hotel_admin'
        },
        {
          label: '酒店员工',
          value: 'hotel_member'
        },
        {
          label: '防疫人员',
          value: 'anti-epidemic'
        }
      ]

    }
  },
  methods: {
    //获取部门列表
    getMenuList() {
      let data = {
        // deptVO: this.$route.query.data
      }
      post('/api/menu/list', data)
          .then(res => {
            console.log(res);
            this.menuData = res.data.data
          })
          .catch(err => {
            console.log(err);
          })
    },

    //
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

    //获取dialog下拉框数据
    getData() {
      let dept = {
        // deptVO: this.$route.query.data
      }
      post('/api/menu/list', dept).then(res => {
        this.menuList = this.getTreeData(res.data.data)
        console.log(res)
        console.log(this.deptList)
      })
      post('/api/dept/list', dept).then(res => {
        this.deptList = this.getTreeData(res.data.data)
        console.log(res)
        console.log(this.deptList)
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

    //确定按钮
    confirm() {
      let value = this.menuValue
      let data = this.form
      //格式化deptId数组
      let deptId = ''
      console.log(this.form.deptId)
      console.log(this.form.role)
      if (this.form.deptId) {
        for (let i = 0; i < this.form.deptId.length; i++) {
          if (this.form.deptId[i]) {
            deptId += this.form.deptId[i]
            if (i != this.form.deptId.length - 1) {
              deptId += ','
            }
          }
        }
      }

      //格式化role数组
      let role = ''
      if (this.form.role) {
        for (let i = 0; i < this.form.role.length; i++) {
          if (this.form.role[i]) {
            role += this.form.role[i]
            if (i != this.form.role.length - 1) {
              role += ','
            }
          }
        }
      }

      this.form.deptId = deptId
      this.form.role = role
      if (value == 'add') {
        this.menuRequest(value, data)
      } else if (value == 'modify') {
        data.id = this.form.id
        this.menuRequest(value, data)
      }

    },

    //请求方法
    menuRequest(url, data) {
      console.log("data",data)
      post('/api/menu/' + url, data)
          .then(res => {
            this.$message({
              message: '操作成功！',
              type: 'success',
              duration: 1000
            });
            console.log(res);
            this.$router.go(0);
          })
          .catch(err => {
            console.log(err);
          })
    },

    initForm() {
      this.form = {
        id: undefined,
        pId: undefined,
        name: undefined,
        deptId: undefined,
        visible: "1",
        path: undefined,
        icon: undefined,
        sort: undefined,
        remark: undefined,
        role: undefined
      }
    },

    //增加菜单
    addDepart() {
      this.initForm()
      this.dialogVisible = true
      this.title = '添加菜单'
      this.menuValue = 'add'
    },

    //表格的删除按钮点击方法
    handleDelete(index, row) {
      get('/api/menu/delete/' + row.id)
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

    //表格的编辑按钮点击方法
    handleModify(index, row) {
      console.log(index);
      console.log(row);
      this.form = row
      get("/api/menuDept/list/deptId/" + row.id).then(res => {
        this.form.deptId = res.data.data
      })
      get("/api/menuDept/list/role/" + row.id).then(res => {
        this.form.role = res.data.data
        console.log(this.form.role)
      })
      this.dialogVisible = true
      this.title = '修改菜单'
      this.menuValue = 'modify'
    },

  },
  mounted() {
    this.getMenuList()
    this.getData()
  }
}
</script>

<style scoped>
.depart-main {
  width: 100%;
  min-width: 950px;
  background: #FFFFFF;
  box-shadow: 0px 5px 30px 0px rgba(22, 115, 255, 0.1);
}
</style>
