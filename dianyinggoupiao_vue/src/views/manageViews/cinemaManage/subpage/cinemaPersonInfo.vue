<template>
    <div style="margin-left: 40px">
        <div style="font-size: 36px;font-family: '新宋体';margin: 40px 0 20px 40px;">
            个人信息
        </div>
        <div>
            <div style="margin-bottom: 20px">
                <p style="font-size: 20px">头像</p>
                <span style="margin-right: 40px">请选择图片文件</span>
                <WarningFilled style="width:1em;height:1em;" />
                <span style="opacity: 0.5">最佳尺寸100*100,仅支持png和jpeg或者jpg格式</span>
            </div>
<!--            上传头像-->
            <el-upload
                class="avatar-uploader"
                action="http://localhost:8181/Images/uploadImage"
                :show-file-list="false"
                :on-success="handleAvatarSuccess"
                :before-upload="beforeAvatarUpload(cinemaAccName,data.type)"
                :http-request="handleImageUpload"
            >
                <div class="demo-image__error">
                    <div class="block">
                        <el-image v-if="imageUrl.value!==''" :src="imageUrl">
                            <template #error>
                                <div class="image-slot">
                                    <el-icon><Picture/></el-icon>
                                </div>
                            </template>
                        </el-image>
                    </div>
                </div>
            </el-upload>
        </div>
<!--        表单-->
        <el-form class="el-form--default"
                 :model="form"
                 label-width="auto"
                 label-position="left"
                 style="max-width: 600px;margin-top: 30px;">
            <el-form-item label="账号：">
                <span>{{cinemaAccName}}</span>
            </el-form-item>
            <el-button
                type="primary"
                text
                @click="disabled=!disabled"
                style="float: right;margin-right: 10%;"
            >
                修改
            </el-button>
            <el-form-item label="名称：">
                <span v-show="!disabled">{{form.name}}</span>
                <el-input
                    v-show="disabled"
                    v-model="form.name"
                    style="width: 60%;"
                    :placeholder="form.name"
                />
            </el-form-item>
            <el-form-item label="地区：">
                <span v-show="!disabled">{{form.location}}</span>
                <el-input
                    v-show="disabled"
                    v-model="form.location"
                    style="width: 80%;"
                    :placeholder="form.location"
                />
            </el-form-item>
            <el-form-item label="品牌：">
                <el-select
                    v-model="form.brand"
                >
                    <el-option
                        v-for="item in optionBrand"
                        :key="item.value"
                        :label="item.label"
                        :value="item.value"
                    />
                    <template #footer>
                        <el-button v-if="!isBrandAdding" text bg size="small" @click="onBrandAddOption">
                            增加选项
                        </el-button>
                        <template v-else>
                            <el-input
                                v-model="optionBrandName"
                                class="option-input"
                                placeholder="输入品牌"
                                size="small"
                            />
                            <el-button type="primary" size="small" @click="onBrandConfirm">
                                确定
                            </el-button>
                            <el-button size="small" @click="brandClear">取消</el-button>
                        </template>
                    </template>
                </el-select>
            </el-form-item>
            <el-form-item label="行政区域：">
                <span v-show="!disabled">{{form.administrativeDistrict}}</span>
                <el-input
                    v-show="disabled"
                    v-model="form.location"
                    style="width: 40%;"
                    :placeholder="form.administrativeDistrict"
                />
            </el-form-item>
            <el-form-item label="影厅类型：">
                <el-select
                    v-model="form.type"
                >
                    <el-option
                        v-for="item in optionType"
                        :key="item.value"
                        :label="item.label"
                        :value="item.value"
                    />

                    <template #footer>
                        <el-button v-if="!isTypeAdding" text bg size="small" @click="onTypeAddOption">
                            增加选项
                        </el-button>
                        <template v-else>
                            <el-input
                                v-model="optionTypeName"
                                class="option-input"
                                placeholder="输入影厅类型"
                                size="small"
                            />
                            <el-button type="primary" size="small" @click="onTypeConfirm">
                                确定
                            </el-button>
                            <el-button size="small" @click="typeClear">取消</el-button>
                        </template>
                    </template>
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
            <el-form-item label="最低价格：">
                <span v-show="!disabled">{{form.price}}</span>
                <el-input
                    v-show="disabled"
                    v-model="form.price"
                    style="width: 40%;"
                    :placeholder="form.price"
                />
            </el-form-item>
            <el-button type="primary" text @click="save" style="float:right;margin-right: 40%;">保存</el-button>
            <el-form-item label="联系电话：">
                <span v-show="!disabled">{{form.phone}}</span>
                <el-input
                    v-show="disabled"
                    v-model="form.phone"
                    style="width: 80%;"
                    :placeholder="form.phone"
                />
            </el-form-item>
        </el-form>
    </div>
</template>

<script>
import {reactive, ref} from 'vue'
import {beforeAvatarUpload, handleImageUpload} from '@/util/imageSave'
import {getImageUrl, postCinemaBody, postCinemaParam, setSelectHttpName} from '@/http/cinemaApi'
import {ElMessage} from 'element-plus'

export default {
    name: "cinemaPersonInfo",
    setup(){
//----------------------------------------变量----------------------------------------
        const imageUrl = ref('');
        const data=reactive({
            userAccName:"",
            type:"cinemaAvatar"
        })
        const form=ref({
            name:"万象影城（吴江万象汇店）",
            location:"吴江区xx街道xx路xxxx号xxx",
            brand:"",
            administrativeDistrict:"",
            type:"",
            rebook:"",
            returnTicket:"",
            price:"",
            phone:"",
        });

        //----------------option----------------
        const optionBrand=ref([
                {
                    value:"上影国际影城",
                    label:"上影国际影城"
                },
                {
                    value:"自由人影城",
                    label:"自由人影城"
                },
                {
                    value:"卢米埃影城",
                    label:"卢米埃影城"
                },
                {
                    value:"万象影城",
                    label:"万象影城"
                },
                {
                    value:"万达影城",
                    label:"万达影城"
                },
                {
                    value:"红星电影世界",
                    label:"红星电影世界"
                },
                {
                    value:"永乐影城",
                    label:"永乐影城"
                },
                {
                    value:"幸福蓝海国际影城",
                    label:"幸福蓝海国际影城"
                },
                {
                    value:"中影东方影城",
                    label:"中影东方影城"
                },
            ]);
        const optionType=ref([
                {
                    value:"IMAX厅",
                    label:"IMAX厅"
                },
                {
                    value:"杜比全景声厅",
                    label:"杜比全景声厅"
                },
                {
                    value:"杜LUXE巨幕厅",
                    label:"杜LUXE巨幕厅"
                },
                {
                    value:"巨幕厅",
                    label:"巨幕厅"
                },
                {
                    value:"RealD厅",
                    label:"RealD厅"
                },
                {
                    value:"DTS:X 临境音厅",
                    label:"DTS:X 临境音厅"
                },
            ])
        const optionBrandName=ref("");
        const optionTypeName=ref("");
        const isBrandAdding=ref(false);
        const isTypeAdding=ref(false);

        const disabled=ref(false);
        //获取用户账号
        const cinemaAccName=localStorage.getItem('cinemaAccName')
        data.userAccName=cinemaAccName;
        //获取图片url
        setSelectHttpName("http://localhost:8181/Images/imageIdByUrl")
        getImageUrl(data).then(url=>{
            imageUrl.value=url
        })

        //获取cinema信息
        setSelectHttpName("http://localhost:8181/Cinema/findCinemaByCinemaAcc_name")
        postCinemaParam(cinemaAccName).then(cinema=>{
            form.value=cinema
        })
//-------------------------------------函数--------------------------------------
        //上传成功
        const handleAvatarSuccess = (response, uploadFile) => {
            if (uploadFile && uploadFile.raw) {
                imageUrl.value = URL.createObjectURL(uploadFile.raw);
            } else {
                console.error('uploadFile 或 uploadFile.raw 为 null 或 undefined！');
            }
        };
        //保存
        const save = () => {
            setSelectHttpName("http://localhost:8181/Cinema/saveOneCinema")
            postCinemaBody(form.value).then(respond=>{
                if (respond){
                    ElMessage.success("保存成功！")
                    disabled.value=false;
                }else {
                    ElMessage.error("保存失败！")
                }
            })
        }
        //-------------------------select----------------------
        const onTypeAddOption = () => {
            isTypeAdding.value = true
        }
        const onTypeConfirm = () => {
            if (optionTypeName.value) {
                optionType.value.push({
                    label: optionTypeName.value,
                    value: optionTypeName.value,
                })
                typeClear()
            }
        }
        const typeClear = () => {
            optionTypeName.value = ''
            isTypeAdding.value = false
        }
        const onBrandAddOption = () => {
            isBrandAdding.value = true
        }
        const onBrandConfirm = () => {
            if (optionBrandName.value) {
                optionBrand.value.push({
                    label: optionBrandName.value,
                    value: optionBrandName.value,
                })
                brandClear()
            }
        }
        const brandClear=()=>{
            optionBrandName.value = ''
            isBrandAdding.value=false
        }
        return{
            imageUrl,
            cinemaAccName,
            data,
            form,
            disabled,
            optionBrand,
            optionType,
            optionBrandName,
            optionTypeName,
            isBrandAdding,
            isTypeAdding,
            save,
            handleAvatarSuccess,
            beforeAvatarUpload,
            handleImageUpload,
            onTypeAddOption,
            onTypeConfirm,
            typeClear,
            onBrandAddOption,
            onBrandConfirm,
            brandClear,
        }
    }
}

</script>

<style lang="scss" scoped>
.demo-image__error .block {
    display: inline-block;
    box-sizing: border-box;
    vertical-align: top;
    text-align: center;
    width: 100px;
    height: 100px;
    //background: #F5F7FA;
}
.demo-image__error .el-image {
    max-width: 100px;
    max-height: 100px;
    width: 100%;
    height: 100%;
    margin: 0;
    padding: 0;
    left: 0;
}
.demo-image__error .image-slot {
    display: flex;
    justify-content: center;
    align-items: center;
    width: 100%;
    height: 100%;
    background: var(--el-fill-color-light);
    color: var(--el-text-color-secondary);
    font-size: 30px;
}
.demo-image__error .image-slot .el-icon {
    font-size: 30px;
}
.infoPerson >p{
    margin-top: 30px;
}
</style>