<template>

  <div class="material-application-main">

      <div class="application-content flex flex-column justify-content-center">
            <h1 style="font-size: 20px;color: #999;margin-bottom: 12px">物资申请</h1>
            <div class="d-flex align-items-center mb-15">
                <p class="w-120 text-left font-16">申请物品名称:</p>
                <el-input
                    style="width: 350px;"
                    placeholder="请输入物品名称"
                    v-model="form.name"
                    clearable>
                </el-input>
            </div>            
            <div class="d-flex align-items-center mb-15">
                <p class="w-120 text-left font-16">申请数量:</p>
                <el-input
                    style="width: 350px;"
                    placeholder="请输入申请数量"
                    v-model="form.num"
                    clearable>
                </el-input>
            </div>
            <div class="d-flex align-items-center mb-15">
                <p class="w-120 text-left font-16">申请单位:</p>
                <el-input
                    style="width: 350px;"
                    placeholder="请输入申请物品单位"
                    v-model="form.unit"                    
                    clearable>
                </el-input>
            </div>
            <div class="d-flex align-items-center mb-15">
                <p class="w-120 text-left font-16">申请备注:</p>
                <el-input
                    style="width: 350px;"
                    placeholder="请输入申请备注"
                    v-model="form.remark"                    
                    clearable>
                </el-input>
            </div>
            <div class="d-flex align-items-center mb-15">
                <p class="w-120 text-left font-16">紧急程度:</p>
                <el-select style="width:350px;" v-model="form.emergencyLevel" placeholder="请选择">
                    <el-option
                        v-for="item in situationOptions"
                        :key="item.value"
                        :label="item.label"
                        :value="item.value">
                    </el-option>
                </el-select>                
            </div>                       
            <div>
                <el-button type="primary" @click="confirm">确定</el-button>
            </div>          
      </div>

  </div>

</template>

<script>
import { get, post } from "../utils/request";

export default {
    name: "MaterialApplication",
    data() {
        return {
            form: {
                name: '',
                num: '',
                unit: '',
                remark: '',
                status: '',
            },
            situationOptions: [{
                value: 0,
                label: '不紧急'
            }, {
                value: 1,
                label: '普通紧急'
            }, {
                value: 2,
                label: '非常紧急'
            }],
        }
    },
    mounted() {

    },
    methods: {
        confirm() {
            const data = {
                applyNum: this.form.num,
                applyRemarks: this.form.remark,
                applyThing: this.form.name,
                thingUnit: this.form.unit,
                emergencyLevel: this.form.emergencyLevel,
                // id: ''?
            }
            post('/api/materialsApply/apply', data)
                .then( res => {
                    console.log(res);
                })
                .catch( err => {
                    console.error(err);
                })
        }
    }

}
</script>

<style scoped>
.material-application-main {
    display: flex;
    flex-direction: column;
    /* justify-content: space-between; */
    width: 100%;
    min-width: 1000px;
    height: 100%;
    background: #FFFFFF;
    box-shadow: 0px 5px 30px 0px rgba(22, 115, 255, 0.1);
}
.application-content {
    padding: 30px 25px;
    border: 1px solid #d5d5d5;
    width: 500px;
    margin: 0 auto;
    margin-top: 30px;
    border-radius: 6px;

}
</style>