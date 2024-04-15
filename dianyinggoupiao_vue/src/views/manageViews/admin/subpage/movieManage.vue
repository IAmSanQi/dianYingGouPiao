<template>
    <div>
        <!--        查询-->
        <el-form
            label-width="100px"
            :model="MovieRecord"
            :rules="rules"
            class="form"
            :inline="true"
        >
            <el-form-item label="id" prop="IdInput" >
                <el-input
                    v-model="MovieRecord.idInput"
                    class="form_input"
                    placeholder="请输入id号"
                    clearable
                />
            </el-form-item>
            <el-form-item>
                <el-button  type="primary"  @click="MovieIdRecord">id查询</el-button>
            </el-form-item>

            <el-form-item label="影片名" prop="MovieInput" >
                <el-input
                    v-model="MovieRecord.accInput"
                    class="form_input"
                    placeholder="请输入影片名号"
                    clearable
                />
            </el-form-item>
            <el-form-item>
                <el-button type="primary"  @click="MovieNameRecord">影片名查询</el-button>
            </el-form-item>
        </el-form>
        <!--        新增按钮-->
        <el-button
            type="info"
            size="small"
            @click="newOuterVisible=true"
            style="float: right;margin-right: 40px;margin-top: 10px;margin-bottom: 20px"
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
                            <el-input v-model="add.name" clearable style="width: 60%;"></el-input>
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
                                ref="manageImageFile"
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
        <el-table :data="Data" style="width: 100%" :border="true">
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
                    <el-button
                        @click="outerVisible=true, getRow(row)"
                        size="small"
                        type="primary"
                        style="margin-left: 10px;"
                    >修改
                    </el-button>
                    <el-button @click="delVisible=true,getDelRowData(row) " size="small" type="danger" >删除</el-button>
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
                        <el-form-item label="选择图片:" prop="image">
                            <el-upload
                                ref="manageImageFile"
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
        <!--        分页-->
        <div class="demo-pagination-block">
            <el-pagination
                v-model:current-page="MovieTable.currentPage"
                v-model:page-size="MovieTable.pageSize"
                :small="false"
                :background="true"
                layout="total, prev, pager, next, jumper"
                :total="MovieTable.total"
                @current-change="handleCurrentChange"
            />
        </div>
    </div>
</template>

<script>
import {reactive, ref} from 'vue'
import {
    accRecord, Data, fenYe, getTable,
    idRecord,
    setRecord, setSelectHttpName, setTable,
} from '@/http/accApi'
import {movieAreaOption, movieTypeOption} from '@/util/data'
import {getMovie, postDelMovie} from '@/http/movieApi'
import {beforeImageUpload, handleMovieImageUpload, MovieImageFormData} from '@/util/imageSave'
import {AddMovieData, PostData} from '@/http/cinemaApi'
import {ElMessage} from 'element-plus'

export default {
    name: "movieManage",
    setup() {
        let MovieTable=reactive({})
        let MovieRecord=reactive({})
        const editForm=ref({
            name:"",
            introduce:"",
            starring:"",
            location:"",
            type:"",
            play_time:"",
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
        const newOuterVisible=ref(false)
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

        //对话框变量
        const outerVisible=ref(false)
        const newInnerVisible=ref(false)
        const innerVisible=ref(false)
        const delVisible=ref(false)

        //image
        const manageImageFile = ref(null)
        //删除
        const delData=ref(null);

//-------------------------------------函数--------------------------------------------------------
        setSelectHttpName("http://localhost:8181/Movie/findMovieAllInfo")
        fenYe(Data)
        MovieTable=getTable()
        let handleCurrentChange=()=>{
            fenYe(Data)
            setTable(MovieTable)
        }
        let MovieIdRecord=()=>{
            setRecord(MovieRecord)
            idRecord()
        }
        let MovieNameRecord = ()=>{
            setRecord(MovieRecord)
            accRecord()
        }

        //获取当前行数据
        let getRow=(row)=>{
            //把当前一行的movie_info值赋值给editForm
            Data.forEach(key=>{
                if (row.movie_info.name===key.movie_info.name){
                    editForm.value=key.movie_info;
                }
            })
            console.log("editForm:")
            console.log(editForm.value)
        }
        const getDelRowData=(row)=>{
            delData.value=row;
        }
        //修改电影数据
        let editMovieData=()=>{
            //关闭修改对话框
            outerVisible.value=false;
            innerVisible.value=false;

            MovieImageFormData.append("movieInfo",JSON.stringify(editForm.value))
            //把数据传送到后台
            AddMovieData("http://localhost:8181/Movie/updateMovie_infoAndImage",MovieImageFormData).then(respond=>{
                if (respond){
                    ElMessage.success("修改成功！")
                    //清除，防止重复数据
                    MovieImageFormData.delete("movieInfo")
                }else {
                    ElMessage.error("修改失败！")
                    //清除，防止重复数据
                    MovieImageFormData.delete("movieInfo")
                }
            })
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
        //提交
        const submitUpload = () => {
            if (manageImageFile.value!=null){
                manageImageFile.value.submit()
            }else {
                console.log("manageImageFile.value:",manageImageFile.value)
            }
        }
        //删除
        const delMovieDate=()=>{
            delVisible.value=false;
            console.log("delData.value",delData.value);
            postDelMovie("http://localhost:8181/Movie/delMovie",delData.value.id,delData.value.name).then(res=>{
                if (res){
                    ElMessage.success("删除成功！")
                }else {
                    ElMessage.error("删除失败！")
                }
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

            //把数据传送到后台
            AddMovieData("http://localhost:8181/Movie/addOneMovie",MovieImageFormData).then(respond=>{
                if (respond){
                    ElMessage.success("新增成功！")
                    //清除，防止重复数据
                    MovieImageFormData.delete("movieInfo")
                }else {
                    ElMessage.error("新增失败！")
                    //清除，防止重复数据
                    MovieImageFormData.delete("movieInfo")
                }
            })

        }

        return {
            handleCurrentChange,
            Data,
            MovieTable,
            MovieRecord,
            rules,
            newOuterVisible,
            newInnerVisible,
            editForm,
            add,
            addName,
            addType,
            areaOption,
            typeOption,
            optionMovieNames,
            isMovieNameAdding,
            shortcuts,
            manageImageFile,
            outerVisible,
            innerVisible,
            delVisible,

            MovieIdRecord,
            MovieNameRecord,
            handledChange,
            beforeImageUpload,
            handleMovieImageUpload,
            submitUpload,
            delMovieDate,
            getDelRowData,
            addMovieDate,
            getRow,
            editMovieData,

        }
    },
}
</script>

<style scoped lang="scss">
.form {
    width: 100%;
    border-bottom: lightgray 2px solid;
}

el-form-item {
    width: 100%;
}

.recordInput {
    width: 100%;
    margin-right: 10px;
}


.dialog-footer button:first-child {
    margin-right: 10px;
}

.el-upload-list--picture-card .el-upload-list__item-thumbnail{
    width: 100%;
    height: 100%;
    object-fit: cover;
}
</style>