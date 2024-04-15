<template>
    <div>
<!--        表单-->
        <el-form
            :model="form"
            :rules="rules"
            label-width="100px"
        >
            <el-form-item label="id" class="item-search" prop="IdInput">
                <el-input
                    v-model="form.IdInput"
                    class="form_input"
                    placeholder="请输入id号"
                    clearable
                />
            </el-form-item>
            <el-button type="primary" class="button" @click="idQuery">id查询</el-button>
            <el-form-item label="影片名" class="item-search" prop="MovieInput">
                <el-input
                    v-model="form.MovieInput"
                    class="form_input"
                    placeholder="请输入影片名号"
                    clearable
                />
            </el-form-item>
            <el-button type="primary" class="button" @click="movieQuery">影片名查询</el-button>
        </el-form>
<!--        新增按钮-->
        <el-button
            type="info"
            size="small"
            style="float: right;margin-right: 40px;margin-top: 50px;margin-bottom: 20px"
            @click="addButton"
        >+新增
        </el-button>
<!--        新增对话框-->
        <el-dialog v-model="newOuterVisible" title="新增" :rules="rules" >
            <template #default>
                <el-dialog
                    v-model="newInnerVisible"
                    width="30%"
                    title="确认新增"
                    append-to-body
                >
                    <el-button @click="newInnerVisible=false">关闭</el-button>
                    <el-button type="primary" @click="addMovieDate">
                        确定
                    </el-button>
                </el-dialog>
            </template>
            <template #footer>
                <div class="dialog_input">
                    <el-form
                        :model="add"
                        :rules="rules"
                    >
                        <el-form-item label="电影名称:" prop="name">
                            <el-select
                                v-model="add.name"
                                filterable
                                clearable
                            >
                                <el-option
                                    v-for="item in optionMovieNames"
                                    :key="item.value"
                                    :label="item.label"
                                    :value="item.value"
                                />
                                <template #footer>
                                    <el-button v-if="!isMovieNameAdding" text bg size="small" @click="onMovieNamesAddOption">
                                        增加选项
                                    </el-button>
                                    <template v-else>
                                        <el-input
                                            v-model="add.name"
                                            class="option-input"
                                            placeholder="输入新的电影名称"
                                            size="small"
                                        />
                                        <el-button type="primary" size="small" @click="onMovieNamesConfirm">
                                            确定
                                        </el-button>
                                        <el-button size="small" @click="MovieNamesClear">取消</el-button>
                                    </template>
                                </template>
                            </el-select>
                        </el-form-item>
                        <el-form-item label="简介:" prop="introduce">
                            <el-input
                                type="textarea"
                                v-model="add.introduce"
                                :rows="6"
                                clearable
                                style="margin-bottom: 10px;width:85%;margin-right: 5%;"
                            />
                        </el-form-item>
                        <el-form-item label="演员:"  prop="starring">
                            <el-input v-model="add.starring" clearable placeholder="例如：胡歌,杨幂,刘亦菲"/>
                        </el-form-item>
                        <el-form-item label="地区:" prop="location">
                            <el-select
                                v-model="add.location"
                                filterable
                                placeholder="请输入国家"
                                style="width: 320px"
                                clearable
                            >
                                <el-option
                                    v-for="item in areaOption"
                                    :key="item.value"
                                    :label="item.label"
                                    :value="item.value"
                                />
                            </el-select>
                        </el-form-item>
                        <el-form-item label="上映时间:" prop="time">
                            <el-date-picker
                                v-model="add.time"
                                type="date"
                                placeholder="Pick a day"
                                :shortcuts="shortcuts"
                            />
                        </el-form-item>
                        <el-form-item label="类型:" prop="type">
                            <el-select
                                v-model="addType"
                                multiple
                                filterable
                                placeholder="请输入电影类型"
                                style="width: 320px"
                            >
                                <el-option
                                    v-for="item in typeOption"
                                    :key="item.value"
                                    :label="item.label"
                                    :value="item.value"
                                />
                            </el-select>
                        </el-form-item>
                        <el-form-item label="播放时长:" prop="play_time">
                            <el-input v-model="add.play_time" clearable placeholder="例如：120"/>
                        </el-form-item>
                        <el-form-item label="选择图片:" prop="image">
                            <el-upload
                                ref="imageFile"
                                action="http://localhost:8181/Images/getImage"
                                class="el-upload-list--picture-card"
                                list-type="picture-card"
                                accept=".jpg,.jpeg,.png"
                                :auto-upload="false"
                                :on-change="handledChange"
                                :before-upload="beforeImageUpload"
                                :http-request="handleMovieImageUpload"
                            >
                                <el-icon><Plus /></el-icon>
                                <template #file="{ file }">
                                    <div>
                                        <img class="el-upload-list__item-thumbnail" :src="file.url" alt="" />
                                    </div>
                                </template>
                                <template #tip>
                                    <div class="el-upload__tip">
                                        文件类型：jpg/png/jpeg
                                    </div>
                                </template>
                            </el-upload>
                            <el-button class="upload-button" type="success" @click="submitUpload">
                                上传
                            </el-button>
                        </el-form-item>
                    </el-form>
                </div>
                <div class="dialog-footer">
                    <el-button @click="newOuterVisible = false">关闭</el-button>
                    <el-button type="primary" @click="newInnerVisible = true">
                        确定
                    </el-button>
                </div>
            </template>
        </el-dialog>
<!--        表格-->
        <el-table
            :data="tableData.value"
            style="width: 100%"
            max-height="450px"
            :border="true"
        >
            <el-table-column prop="id" label="编号" width="150" fixed="left" />
            <el-table-column prop="movie_info.name" label="电影名称" width="150" fixed="left" />
            <el-table-column prop="movie_info.introduce" label="简介" width="150" :show-overflow-tooltip="true" :resizable="true"/>
            <el-table-column prop="movie_info.starring" label="演员" width="150" :show-overflow-tooltip="true" :resizable="true"/>
            <el-table-column prop="movie_info.location" label="地区" width="150"/>
            <el-table-column prop="movie_info.type" label="类型" width="150"/>
            <el-table-column prop="movie_info.play_time" label="播放时长" width="150"/>
            <el-table-column prop="ticketNumber" label="票数" width="150"/>
            <el-table-column prop="score" label="评分" width="150"/>
            <el-table-column prop="see" label="想看人数" width="150"/>
            <el-table-column label="操作" fixed="right" width="150">
                <template v-slot="{row}">
                    <el-button @click="outerVisible=true,getRow(row)" size="small" type="primary" style="margin-left: 10px;">修改
                    </el-button>
                    <el-button @click="delVisible=true" size="small" type="danger" >删除</el-button>
                </template>
            </el-table-column>
        </el-table>
        <el-dialog v-model="outerVisible" title="修改"  >
            <template #default>
                <el-dialog
                    v-model="innerVisible"
                    width="30%"
                    title="确认修改"
                    append-to-body
                >
                    <el-button @click="innerVisible=false">关闭</el-button>
                    <el-button type="primary" @click="editMovieData">
                        确定
                    </el-button>
                </el-dialog>
            </template>
            <template #footer>
                <div class="dialog_input">
                    <el-form
                        :model="editForm"
                        :rules="rules"
                    >
                        <el-form-item label="简介:" prop="introduce">
                            <el-input
                                type="textarea"
                                v-model="editForm.introduce"
                                :rows="6"
                                clearable
                                style="margin-bottom: 10px;width:85%;margin-right: 5%;margin-left: 5%;"
                            />
                        </el-form-item>
                        <el-form-item label="演员:"  prop="starring">
                            <el-input v-model="editForm.starring" clearable/>
                        </el-form-item>
                        <el-form-item label="地区:" prop="location">
                            <el-input v-model="editForm.location" clearable/>
                        </el-form-item>
                        <el-form-item label="类型:" prop="type">
                            <el-input v-model="editForm.type" clearable/>
                        </el-form-item>
                        <el-form-item label="播放时长:" prop="play_time">
                            <el-input v-model="editForm.play_time" clearable/>
                        </el-form-item>
                    </el-form>
                </div>
                <div class="dialog-footer">
                    <el-button type="primary" @click="innerVisible = true">
                        确定
                    </el-button>
                    <el-button @click="outerVisible = false">关闭</el-button>
                </div>
            </template>
        </el-dialog>
        <el-dialog v-model="delVisible" title="确认删除吗？" width="30%" >
            <el-button type="primary" @click="delMovieDate">确定</el-button>
            <el-button @click="delVisible=false">取消</el-button>
        </el-dialog>
    </div>
</template>

<script>

import {reactive, ref} from 'vue'
import {
    AddMovieData,
    cinemaIdQuery,
    cinemaMovieQuery,
    PostData,
    selectCinema,
    setSelectHttpName
} from '@/http/cinemaApi'
import {ElMessage} from 'element-plus'
import {getMovie} from '@/http/movieApi'
import {movieTypeOption,movieAreaOption} from '@/util/data'
import {beforeImageUpload, handleMovieImageUpload, MovieImageFormData} from '@/util/imageSave'

export default {
    name: "filmManage",
    props:{
        cinemaAccName:{
            type: String,
            required: true
        }
    },
    setup(props) {
//------------------------------------变量--------------------------------------
        // 表单内input数据
        let form=reactive({
            IdInput:"",
            MovieInput:""
        })
        //input规则
        let rules=reactive({
            //-----------表单内input规则-----------
            IdInput:[
                {required: true, message: '请输入你要搜索的id', trigger: 'change'},
            ],
            MovieInput:[
                {required: true, message: '请输入你要搜索的影片名', trigger: 'change'},
            ],
            //-----------对话框内input规则-----------
            name:[
                {required: true, message: '请输入影片名称', trigger: 'change'},
            ],
            introduce:[
                {min: 15,required: true, message: '请输入简介最少15字', trigger: 'change'},
            ],
            starring:[
                {required: true, message: '请输入演员', trigger: 'change'},
            ],
            location:[
                {required: true, message: '请输入地区', trigger: 'change'},
            ],
            time:[
                {required: true, message: '请输入上映时间', trigger: 'change'},
            ],
            type:[
                {required: true, message: '请输入影片类型', trigger: 'change'},
            ],
            play_time:[
                {required: true, message: '请输入播放时长', trigger: 'change'},
            ],
            image:[
                {required: true, message: '图片未上传', trigger: 'change'},
            ],
        })
        //表格
        let tableData=reactive([])
        //对话框变量
        const outerVisible=ref(false)
        const innerVisible=ref(false)
        const newOuterVisible=ref(false)
        const newInnerVisible=ref(false)
        const delVisible=ref(false)
        //修改
        const editForm=ref({
            name:"",
            introduce:"",
            starring:"",
            location:"",
            type:"",
            play_time:"",
        })

        //新增
        let add=ref({
            name:"",
            introduce:"",
            starring:"",
            location:"",
            time:"",
            type:"",
            play_time:"",
            image:"",
        })
        const addName=ref([])
        const addType=ref([])
        //option
        const optionMovieNames=ref([
            {
                value:"你的名字",
                label:"你的名字",
            },
        ])
        const typeOption=movieTypeOption;
        const areaOption=movieAreaOption;
        const isMovieNameAdding=ref(false);

        //image
        const imageFile = ref()

//------------------------------------函数变量--------------------------------------
        //获取后台电影数据
        const cinemaAccName = props.cinemaAccName
        setSelectHttpName("http://localhost:8181/CinemaAndMovieService/findCinemaAllMovieInfo");
        selectCinema(JSON.stringify(cinemaAccName),tableData)


        //id查询
        let idQuery=()=>{
            const selectHttpName=ref("http://localhost:8181/CinemaAndMovieService/findCinemaAllMovieInfo")
            cinemaIdQuery(selectHttpName.value,cinemaAccName,tableData,form)
        }
        let movieQuery=()=>{
            const selectHttpName=ref("http://localhost:8181/CinemaAndMovieService/findCinemaAllMovieInfo")
            cinemaMovieQuery(selectHttpName.value,cinemaAccName,tableData,form)
        }

        //获取行数据
        let getRow=(row)=>{
            //把当前一行的movie_info值赋值给editForm
            tableData.value.forEach(key=>{
                if (row.movie_info.name===key.movie_info.name){
                    editForm.value=key.movie_info;
                }
            })
            console.log("editForm:")
            console.log(editForm.value)
        }

        //修改电影数据
        let editMovieData=()=>{
            //关闭修改对话框
            outerVisible.value=false;
            innerVisible.value=false;
            //把数据传送到后台
            setSelectHttpName("http://localhost:8181/Movie/updateMovie_info");
            PostData(JSON.stringify(editForm.value)).then(respond=>{
                if (respond){
                    ElMessage.success("修改成功！")
                }else {
                    ElMessage.error("修改失败！")
                }
            })
        }
        //删除电影数据
        let delMovieDate=()=>{
            delVisible.value=false;
        }

        let addButton=()=>{
            newOuterVisible.value=true
            //查找数据所有的MovieName
            getMovie("http://localhost:8181/Movie/findMovieNames").then(respond=>{
                optionMovieNames.value = respond.map(respondKey => ({
                    value: respondKey,
                    label: respondKey,
                }));
            })
        }
        //添加电影数据
        let addMovieDate=()=>{
            //关闭新增对话框
            newOuterVisible.value = false
            newInnerVisible.value = false
            //addType.value中元素用‘,’连接给add.value.type赋值
            add.value.type = addType.value.map(item => item).join(",");
            console.log("add.value:",add.value)
            console.log(MovieImageFormData.get("image"))
            MovieImageFormData.append("movieInfo",JSON.stringify(add.value))
            MovieImageFormData.append("cinemaAccName",cinemaAccName)

            //把数据传送到后台
            AddMovieData("http://localhost:8181/Movie/addMovie",MovieImageFormData).then(respond=>{
                if (respond){
                    ElMessage.success("新增成功！")
                    //清除，防止重复数据
                    MovieImageFormData.delete("movieInfo")
                    MovieImageFormData.delete("cinemaAccName")
                }else {
                    ElMessage.error("新增失败！")
                    //清除，防止重复数据
                    MovieImageFormData.delete("movieInfo")
                    MovieImageFormData.delete("cinemaAccName")
                }
            })

        }
        //select
        const onMovieNamesAddOption = () => {
            isMovieNameAdding.value = true
        }
        const onMovieNamesConfirm = () => {
            if (add.value.name) {
                optionMovieNames.value.push({
                    label: add.value.name,
                    value: add.value.name,
                })
                MovieNamesClear()
            }
        }
        const MovieNamesClear = () => {
            isMovieNameAdding.value = false
        }

        //日期
        const shortcuts = [
            {
                text: '今天',
                value: new Date(),
            },
            {
                text: '昨天',
                value: () => {
                    const date = new Date()
                    date.setTime(date.getTime() - 3600 * 1000 * 24)
                    return date
                },
            },
            {
                text: '往上一星期',
                value: () => {
                    const date = new Date()
                    console.log(date)
                    date.setTime(date.getTime() - 3600 * 1000 * 24 * 7)
                    return date
                },
            },
        ]

        //图片
        const handledChange=(file,fileList)=>{
            //只允许上传一张图片，必须接收第二个参数，第一个不可省略。
            if (fileList.length > 1) {
                fileList.splice(0, 1);
            }
        }
        const submitUpload = () => {
            console.log("imageFile.value:",imageFile.value)
            if (imageFile.value!=null){
                imageFile.value.submit()
            }
        }
        return {
            form,
            rules,
            tableData,
            outerVisible,
            innerVisible,
            newOuterVisible,
            newInnerVisible,
            delVisible,
            editForm,
            add,
            addType,
            addName,
            optionMovieNames,
            isMovieNameAdding,
            typeOption,
            areaOption,
            shortcuts,
            imageFile,

            idQuery,
            movieQuery,
            editMovieData,
            delMovieDate,
            addMovieDate,
            getRow,
            addButton,
            onMovieNamesAddOption,
            onMovieNamesConfirm,
            MovieNamesClear,
            handledChange,
            beforeImageUpload,
            handleMovieImageUpload,
            submitUpload,
        }
    },
}
</script>

<style scoped lang="scss">
.item-search{
    width: 30%;
    margin-right: 10px;
    float: left;
}
.form_input{
    float: left;
}
.dialog_input{
    width: 100%;
    .el-input{
        width:85%;
        margin-right: 5%;
        float: right;
        margin-bottom: 10px;
    }
}
.button{
    float: left;
    margin-right: 5%;
    margin-left: 2%;
}
.el-upload-list--picture-card .el-upload-list__item-thumbnail{
    width: 100%;
    height: 100%;
    object-fit: cover;
}
.upload-button {
    margin-left: 10px;
}
</style>