<template>
    <el-button link  icon="el-icon-arrow-left"  >
        <router-link to="/CinemaLogin" style="color: black">
            <el-icon><ArrowLeftBold /></el-icon>返回
        </router-link>
    </el-button>
    <div class="CRForm">
        <el-form :model="form" label-width="auto" :rules="formRules" style="max-width: 600px">
            <el-form-item label="影院名称" prop="name" style="width: 60%;" >
                <el-input v-model="form.name" placeholder="三柒影院"  clearable/>
            </el-form-item>
            <el-form-item label="地址" prop="location" style="width: 100%;" >
                <el-input v-model="form.location"  style="margin-bottom: 10px;" clearable/>
                <div style="width: 100%; height: 400px;">
                    <IMapComponent/>
                </div>
            </el-form-item>
            <el-form-item label="品牌" prop="brand" style="width: 60%;">
                <el-input v-model="form.brand"  placeholder="例如：上影国际影城 或者 万达影城"  clearable/>
            </el-form-item>
            <el-form-item label="行政区域" prop="administrativeDistrict" style="width: 60%;">
                <el-input v-model="form.administrativeDistrict"  placeholder="例如：地铁附近 或者 xx吾悦商业广场"  clearable/>
            </el-form-item>
            <el-form-item label="影厅类型" prop="type" >
                <el-select v-model="form.type" placeholder="请选择影厅类型">
                    <el-option
                        v-for="item in typeOptions"
                        :key="item.value"
                        :label="item.label"
                        :value="item.value"
                    />
                </el-select>
            </el-form-item>
            <el-form-item label="是否可以改签：">
                <el-radio-group v-model="form.rebook">
                    <el-radio value="1" label="1">是</el-radio>
                    <el-radio value="0" label="0">否</el-radio>
                </el-radio-group>
            </el-form-item>
            <el-form-item label="是否可以退票：">
                <el-radio-group v-model="form.returnTicket">
                    <el-radio value="1" label="1">是</el-radio>
                    <el-radio value="0" label="0">否</el-radio>
                </el-radio-group>
            </el-form-item>
            <el-form-item label="价格" prop="price" style="width: 60%;">
                <el-input v-model="form.price"  type="text" show-word-limit maxlength="7" placeholder="影院最低价格，如：32"  clearable/>
            </el-form-item>
            <el-form-item label="电话" prop="phone" style="width: 60%;" >
                <el-input v-model="form.phone"  clearable/>
            </el-form-item>
            <el-form-item>
                <el-button type="primary" @click="onSubmit" style="margin:auto 20px auto auto;">提交</el-button>
            </el-form-item>
        </el-form>
    </div>
</template>

<script>
import {reactive, ref, watchEffect} from 'vue'
import {theaterOptions} from '@/util/data'
import IMapComponent from '@/views/registerViews/IMapComponent'
import {postCinemaBody, setSelectHttpName} from '@/http/cinemaApi'
import {ElMessage} from 'element-plus'
import router from '@/router'


export default {
    name: "cinemaRegister",
    components: {
        IMapComponent
    },
    setup(){
        const form = reactive({
            name: '',
            location: '',
            brand: '',
            administrativeDistrict: '',
            type: '',
            rebook:"1",
            returnTicket:"1",
            price: '',
            phone: '',
        })
        const typeOptions=ref();
        typeOptions.value=theaterOptions.value

        //表单验证
        const formRules = ref({
            name: [
                { required: true, message: '请输入用户名', trigger: 'blur' }
            ],
            location: [
                { required: true, message: '请选择地址', trigger: 'blur' }
            ],
            brand: [
                { required: true, message: '请选择品牌', trigger: 'blur' }
            ],
            administrativeDistrict: [
                { required: true, message: '请输入行政区域', trigger: 'blur' }
            ],
            type: [
                { required: true, message: '请选影厅类型', trigger: 'blur' }
            ],
            price: [
                { required: true, message: '请输入价格', trigger: 'blur' },
                { pattern: /^[0-9]+$/, message: '只能包括数字', trigger: 'blur' }
            ],
            phone: [
                { required: true, message: '请输入电话号码', trigger: 'blur' },
                { pattern: /^[0-9]+$/, message: '只能包括数字', trigger: 'blur' }
            ],
        });

        const onSubmit = () => {
            setSelectHttpName("http://localhost:8181/Cinema/saveOneCinema")
            postCinemaBody(form).then(respond=>{
                if (respond){
                    router.push({path: 'Success'})
                }else {
                    ElMessage.error("注册失败！")
                }
            })
        }

        // 监听 IMapComponent 中 address 的变化
        watchEffect(() => {
            form.location = IMapComponent.address.value;
            console.log( form.location)
        });

        return{
            form,
            typeOptions,
            formRules,
            onSubmit
        }
    }
}
</script>
<style scoped  lang="scss" >
.CRForm{
    width: 60%;
    margin: 80px auto;
    >form{
        margin: auto;
    }
}
</style>