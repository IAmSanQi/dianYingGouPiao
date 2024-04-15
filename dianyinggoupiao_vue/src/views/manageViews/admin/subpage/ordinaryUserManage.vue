<template>
    <div>
        <el-form
            label-width="100px"
            :model="memberRecord"
            :rules="rules"
            class="form"
            inline="inline"
        >
            <el-form-item label="id" prop="idInput">
                <el-input v-model="memberRecord.idInput" placeholder="请输入id号" class="recordInput" clearable/>
            </el-form-item>
            <el-form-item>
                <el-button type="primary" @click="memberIdRecord(memberRecord.idInput)" >id查询</el-button>
            </el-form-item>
            <el-form-item label="账号" prop="acc">
                <el-input v-model="memberRecord.nameInput" placeholder="请输入账号" class="recordInput" clearable/>
            </el-form-item>
            <el-form-item>
                <el-button type="primary" @click="memberAccRecord(memberRecord.nameInput)">名称查询</el-button>
            </el-form-item>
        </el-form>


        <el-table :data="tableData" style="width: 100%" :border="true">
            <el-table-column type="index" :index="indexMethod" label="编号" width="150" fixed="left" />
            <el-table-column prop="name" label="账户" width="150" :formatter="formData" :show-overflow-tooltip="true" :resizable="true" fixed="left" />
            <el-table-column prop="userName" label="姓名" width="150" :formatter="formData" :show-overflow-tooltip="true" :resizable="true"/>
            <el-table-column prop="age" label="年龄" width="150" :formatter="formData" :show-overflow-tooltip="true" :resizable="true"/>
            <el-table-column prop="sex" label="性别" width="性别" :formatter="formData" :show-overflow-tooltip="true" :resizable="true"/>
            <el-table-column prop="phone" label="电话" width="150" :formatter="formData" :show-overflow-tooltip="true" :resizable="true"/>
            <el-table-column prop="mail" label="邮箱" width="150" :formatter="formData"/>
            <el-table-column prop="birthday" label="生日" width="150" :formatter="formData"/>
            <el-table-column prop="area" label="地区"  width="150" :formatter="formData"/>
      </el-table>

        <div class="demo-pagination-block">
            <el-pagination
                v-model:current-page="fenYeInfo.currentPage"
                v-model:page-size="fenYeInfo.pageSize"
                :small="false"
                :background="true"
                layout="total, prev, pager, next, jumper"
                :total="fenYeInfo.total"
                @current-change="handleCurrentChange"
            />
        </div>
    </div>
</template>

<script>
import {reactive, ref} from 'vue'
import {getTotal, idRecord, nameRecord, tableFenYe} from '@/util/getTable'
export default {
    name: "ordinaryUserManage",
    setup() {
        const memberRecord=ref({
            idInput:"",
            nameInput:""
        })
        const fenYeInfo=ref({
            currentPage:1,
            pageSize:7,
            total:null
        })
        const tableData=ref([])
        let rules = reactive({
            idInput: [
                {required: true, message: '请输入你要搜索的id', trigger: 'blur'},
            ],
            accInput: [
                {required: true, message: '请输入你要搜索的账号', trigger: 'blur'},
            ],
        })


        // 根据id查询
        let memberIdRecord=(idInput)=>{
            idRecord("http://localhost:8181/acc/findUserAll",idInput).then(res=>{
                tableData.value=[]
                tableData.value.push(res);
            })
        }
        // 根据影院名称查询
        let memberAccRecord = (nameInput)=>{
            nameRecord("http://localhost:8181/acc/findUserAll",nameInput).then(res=>{
                tableData.value=res;
            })
        }
        // 获取分页数据
        tableFenYe(
            "http://localhost:8181/acc/findUserAll",
            fenYeInfo.value.currentPage,
            fenYeInfo.value.pageSize,
        ).then(res=>{
            tableData.value=res;
        })
        // 获取总条数
        fenYeInfo.value.total=getTotal(
            "http://localhost:8181/acc/findUserAll"
        ).then(res=>{
            fenYeInfo.value.total=res;
        })
        // 改变当前页时获取分页数据
        const handleCurrentChange=(number)=>{
            fenYeInfo.value.currentPage=number;
            tableFenYe(
                "http://localhost:8181/acc/findUserAll",
                fenYeInfo.value.currentPage,
                fenYeInfo.value.pageSize,
            ).then(res=>{
                tableData.value=res;
            })
        }
        // 获取当前行编号
        const indexMethod=(number)=>{
            return number+fenYeInfo.value.currentPage*fenYeInfo.value.pageSize-fenYeInfo.value.pageSize+1;
        }

        // 格式化数据
        const formData =(row,column,cellValue)=>{
            if(cellValue==="1"){
                return "男"
            }else if (cellValue==="0"){
                return "女"
            }else if(cellValue===null||cellValue===""||cellValue==="2"){
                return "暂无"
            }else {
                return cellValue;
            }
        }

        return {
            rules,
            tableData,
            fenYeInfo,
            memberRecord,
            memberIdRecord,
            memberAccRecord,
            handleCurrentChange,
            indexMethod,
            formData,
        }
    },
}
</script>


<style scoped lang="scss">
.form {
    width: 100%;
    border-bottom: lightgray 2px solid;
    display: flex;
    flex-direction: inherit;
    justify-content:space-between;
}


</style>