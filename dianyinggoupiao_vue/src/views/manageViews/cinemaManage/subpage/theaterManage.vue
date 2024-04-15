<template>
    <el-table
        :data="tableData.items"
        style="width: 100%"
        lazy
    >
        <el-table-column fixed prop="name" label="电影名" width="180" >
            <template #default="{ row }">
                <el-select
                    v-model="row.name"
                    clearable
                    placeholder="电影名称"
                >
                <el-option
                    v-for="item in nameOptions.value"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value"
                />
            </el-select>
        </template>
        </el-table-column>
        <el-table-column prop="date" label="上映日期" width="240" >
            <template #default="{ row }">
                <el-date-picker
                    v-model="row.date"
                    type="date"
                    placeholder="Date"
                    format="YYYY/MM/DD"
                />
            </template>
        </el-table-column>
        <el-table-column prop="startTime" label="开始时间" width="150" >
            <template #default="{ row }">
                <el-time-select
                    v-model="row.startTime"
                    :editable="true"
                    start="00:00"
                    step="00:30"
                    end="23:30"
                    :max-time="row.endTime"
                    placeholder="Start time"
                />
            </template>
        </el-table-column>
        <el-table-column prop="endTime" label="结束时间" width="150" >
            <template #default="{ row }">
                <el-time-select
                    v-model="row.endTime"
                    :editable="true"
                    start="00:00"
                    step="00:30"
                    end="23:30"
                    :min-time="row.startTime"
                    placeholder="End time"
                />
            </template>
        </el-table-column>
        <el-table-column prop="movieTheater" label="影厅号" width="150" >
            <template #default="{ row }">
                <el-select
                    v-model="row.movieTheater"
                    clearable
                    :filterable="true"
                    allow-create
                    placeholder="1号厅"
                >
                <el-option
                    v-for="item in movieTheaterOptions"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value"
                />
                </el-select>
            </template>
        </el-table-column>
        <el-table-column prop="price" label="票价" width="150" >
            <template #default="{ row }">
                <el-select
                    v-model="row.price"
                    clearable
                    :filterable="true"
                    allow-create
                    placeholder="32元"
                >
                    <el-option
                        v-for="item in priceOptions"
                        :key="item.value"
                        :label="item.label"
                        :value="item.value"
                    />
                </el-select>
            </template>
        </el-table-column>
        <el-table-column prop="type" label="放映类型" width="150" >
            <template #default="{ row }">
                <el-select
                    v-model="row.type"
                    clearable
                    :filterable="true"
                    allow-create
                    placeholder="国语2D"
                >
                    <el-option
                        v-for="item in movieTypeOptions"
                        :key="item.value"
                        :label="item.label"
                        :value="item.value"
                    />
                </el-select>
            </template>
        </el-table-column>
        <el-table-column fixed="right" label="操作" width="140">
            <template #default>
                <el-button type="primary" plain size="small" @click="addition">
                    新增
                </el-button>

            </template>
        </el-table-column>
    </el-table>
    <el-table
        :data="schedulingData.items"
        style="width: 100%; position: relative;"
        :border="true"
        :highlight-current-row="true"
        :lazy="true"
        :flexible="true"
        max-height="480px"
        class="el-table--fluid-height"
        :row-class-name="getRowIndex"
        @select="selectHandle"
        @select-all="selectAllHandle"
    >
        <el-table-column type="selection" width="55" />
        <el-table-column fixed type="index" label="id" width="55"/>
        <el-table-column fixed prop="name" label="电影名" width="150">
            <template #default="{ row }">
                <span v-show="!row.show">{{row.name}}</span>
                <div v-show="row.show">
                    <el-select
                        v-model="row.name"
                        clearable
                        placeholder="电影名称"
                    >
                        <el-option
                            v-for="item in nameOptions.value"
                            :key="item.value"
                            :label="item.label"
                            :value="item.value"
                        />
                    </el-select>
                </div>
            </template>
        </el-table-column>
        <el-table-column prop="data" label="上映日期" width="150" >
            <template #default="{ row }">
                <span v-show="!row.show">{{row.date}}</span>
                <div v-show="row.show">
                    <el-date-picker
                        v-model="row.date"
                        type="date"
                        placeholder="日期"
                        format="YYYY/MM/DD"
                    />
                </div>
            </template>
        </el-table-column>
        <el-table-column prop="startTime" label="开始时间" width="150" >
            <template #default="{ row }" >
                <span v-show="!row.show">{{row.startTime}}</span>
                <div v-show="row.show">
                    <el-time-select
                        v-model="row.startTime"
                        :editable="true"
                        start="00:00"
                        step="00:30"
                        end="23:30"
                        :max-time="row.endTime"
                        placeholder="Start time"
                    />
<!--                    <el-time-picker v-model="row.startTime" placeholder="开始时间" />-->
                </div>
            </template>
        </el-table-column>
        <el-table-column prop="endTime" label="结束时间" width="150" >
            <template #default="{ row }" >
                <span v-show="!row.show">{{row.endTime}}</span>
                <div v-show="row.show">
                    <el-time-select
                        v-model="row.endTime"
                        :editable="true"
                        start="00:00"
                        step="00:30"
                        end="23:30"
                        :min-time="row.startTime"
                        placeholder="End time"
                    />
<!--                    <el-time-picker-->
<!--                        v-model="row.endTime"-->
<!--                        arrow-control-->
<!--                        placeholder="结束时间"-->
<!--                    />-->
                </div>
            </template>
        </el-table-column>
        <el-table-column prop="movieTheater" label="影厅号" width="140" >
            <template #default="{ row }" >
                <span v-show="!row.show">{{row.movieTheater}}</span>
                <div v-show="row.show">
                    <el-select
                        v-model="row.movieTheater"
                        clearable
                        :filterable="true"
                        allow-create
                        placeholder="1号厅"
                    >
                        <el-option
                            v-for="item in movieTheaterOptions"
                            :key="item.value"
                            :label="item.label"
                            :value="item.value"
                        />
                    </el-select>
                </div>
            </template>
        </el-table-column>
        <el-table-column prop="price" label="票价" width="140" >
            <template #default="{ row }" >
                <span v-show="!row.show">{{row.price}}</span>
                <div v-show="row.show">
                    <el-select
                        v-model="row.price"
                        clearable
                        :filterable="true"
                        allow-create
                        placeholder="32元"
                    >
                        <el-option
                            v-for="item in priceOptions"
                            :key="item.value"
                            :label="item.label"
                            :value="item.value"
                        />
                    </el-select>
                </div>
            </template>
        </el-table-column>
        <el-table-column prop="type" label="放映类型" width="140" >
            <template #default="{ row }">
                <span v-show="!row.show">{{row.type}}</span>
                <div v-show="row.show">
                    <el-select
                        v-model="row.type"
                        clearable
                        :filterable="true"
                        allow-create
                        placeholder="国语2D"
                    >
                        <el-option
                            v-for="item in movieTypeOptions"
                            :key="item.value"
                            :label="item.label"
                            :value="item.value"
                        />
                    </el-select>
                </div>
            </template>
        </el-table-column>
        <el-table-column fixed="right" type="index" label="操作" width="140">
            <template #default="{row}">
                <span v-show="row.show">
                    <el-button type="success" plain size="small" @click="row.modifyDialogVisible=true">完成</el-button>
                     <el-button type="info" plain size="small" @click="row.show=false">取消</el-button>
                </span>
                <div v-show="!row.show">
                    <el-button type="success" plain size="small" @click="modify(row)">
                        修改
                    </el-button>
                    <el-button type="danger" plain size="small" @click="row.delDialogVisible=true">
                        删除
                    </el-button>
<!--                    删除对话框-->
                    <el-dialog
                        v-model="row.delDialogVisible"
                        title="提示"
                        width="500px"
                        :append-to-body="true"
                    >
                        <span>是否删除第{{row.index+1}}条，名为 "{{row.name}}" 的数据</span>
                        <template #footer>
                            <div class="dialog-footer">
                                <el-button @click="row.delDialogVisible = false">取消</el-button>
                                <el-button type="primary" @click="delDialog(row)">确定</el-button>
                            </div>
                        </template>
                    </el-dialog>
<!--                    修改完成对话框-->
                    <el-dialog
                        v-model="row.modifyDialogVisible"
                        title="提示"
                        width="500px"
                        :append-to-body="true"
                    >
                        <span>是否修改第{{row.index+1}}条，名为 "{{row.name}}" 的数据</span>
                        <template #footer>
                            <div class="dialog-footer">
                                <el-button @click="row.modifyDialogVisible=false;">取消</el-button>
                                <el-button type="primary" @click="save(row)">确定</el-button>
                            </div>
                        </template>
                    </el-dialog>
                </div>
            </template>
        </el-table-column>
    </el-table>
    <el-button type="danger" style="position: absolute; right: 2%;" @click="delAll">删除</el-button>
    <div class="demo-pagination-block">
<!--        使用el-config-provider组件包裹设置locale的值为zhCn（导入的中文语言包）-->
        <el-config-provider :locale="zhCn">
            <el-pagination
                v-model:current-page="schedulingData.pagination.currentPage"
                v-model:page-size="schedulingData.pagination.pageSize"
                :page-sizes="[1,5, 10, 15, 20]"
                :small="false"
                :disabled="false"
                :background="true"
                layout="total, sizes, prev, pager, next, jumper"
                :total="schedulingData.pagination.total"
                @size-change="handleSizeChange(schedulingData.pagination.pageSize)"
                @current-change="handleCurrentChange(schedulingData.pagination.currentPage)"
            >
            </el-pagination>
        </el-config-provider>
    </div>
</template>

<script>
import {reactive, ref} from 'vue'
import {PostData, setSelectHttpName} from '@/http/cinemaApi'
import zhCn from 'element-plus/./es/locale/lang/zh-cn'
import {ElMessage} from 'element-plus'

export default {
    name: "theaterManage",
    props:{
        cinemaAccName:{
            type: String,
            required: true
        }
    },
    setup(props){
        const AccName = props.cinemaAccName
        //表格数据
        const tableData=reactive({
            cinemaAccName:AccName,
            items:[
                {
                    name:"",
                    date:null,
                    startTime:"",
                    endTime:"",
                    movieTheater:null,
                    price:null,
                    type:"",
                },
            ]
        });
        let nameOptions=reactive([{
            value: '',
            label: '',
        },]);
        const movieTheaterOptions =reactive([
            {
                value: '1号影厅',
                label: '1号影厅',
            },
            {
                value: '2号影厅',
                label: '2号影厅',
            },
            {
                value: '3号影厅',
                label: '3号影厅',
            },
            {
                value: '4号影厅',
                label: '4号影厅',
            },
            {
                value: '5号影厅',
                label: '5号影厅',
            },
        ]);
        const movieTypeOptions =reactive([
            {
                value: '国语2D',
                label: '国语2D',
            },
            {
                value: '国语3D',
                label: '国语3D',
            },
            {
                value: '日语2D',
                label: '日语2D',
            },
            {
                value: '英语2D',
                label: '英语2D',
            },
            {
                value: '德语2D',
                label: '德语2D',
            },
        ]);
        const priceOptions =reactive([
            {
                value: '30',
                label: '30 元',
            },
            {
                value: '35',
                label: '35 元',
            },
            {
                value: '40',
                label: '40 元',
            },
            {
                value: '42',
                label: '42 元',
            },
            {
                value: '46',
                label: '46 元',
            },            {
                value: '50',
                label: '50 元',
            },            {
                value: '55',
                label: '55 元',
            },
            {
                value: '58',
                label: '58 元',
            },
        ]);

        //获取后台电影名称数据
        setSelectHttpName("http://localhost:8181/CinemaAndMovieService/findNameByMovie");
        PostData(JSON.stringify(AccName)).then(function(result) {
            // 使用map将数据赋值给外部变量
            nameOptions.value = result.map(movieName => ({
                value: movieName,
                label: movieName,
            }));
        }).catch(function(error) {
            console.log(error);
        });

        //获取排片数据
        let schedulingData=reactive({
            //嵌套是为了把里面的数据都定义为响应式数据
            items:[
                {
                    name:"你的名字",
                    date:"2024/05/12",
                    startTime:"8:20:23",
                    endTime:"10:30:23",
                    movieTheater:null,
                    price:null,
                    type:"国语2D",
                    show:false,
                },
            ],
            pagination:{
                pageSize:5,
                currentPage:1,
                total:40,
            }
        });
        let getTotal=()=>{
            setSelectHttpName("http://localhost:8181/CinemaAndScheduling/findSchedulingByCinemaName");
            PostData(JSON.stringify(AccName)).then((response)=>{
                schedulingData.pagination.total=response.length
            }).catch(error=>{
                console.log(error)
            })
        }

        //分页数据
        const pageData =reactive({
            cinemaAccName:AccName,
            pageable:{
                pageSize:5,
                currentPage:1,
            }
        })

        //-----------------------函数-----------------------------
        let addition=()=>{
            setSelectHttpName("http://localhost:8181/CinemaAndScheduling/saveCinemaAndSchedulingTable");
            PostData(JSON.stringify(tableData)).then((response)=>{
                if (response===true){
                    ElMessage({
                        message: '新增成功',
                        type: 'success',
                    })
                }else {
                    ElMessage.error("新增失败")
                }
            }).catch(error=>{
                console.log(error)
            })

        }
        let modify=(row)=>{
            row.show=!row.show;
        }
        let save=(row)=>{
            setSelectHttpName("http://localhost:8181/scheduling/saveScheduling");
            PostData(JSON.stringify(row)).then((response)=>{
                if (response===true){
                    ElMessage({
                        message: '修改成功',
                        type: 'success',
                    })
                }else {
                    ElMessage.error("修改失败")
                }
            }).catch(error=>{
                console.log(error)
            })
            row.show=!row.show;
            pageModify();
        }
        let cancelled=(row)=>{
            row.show=false;
        }
        //----------------删除对话框--------------
        let delDialog=(row)=>{
            setSelectHttpName("http://localhost:8181/CinemaAndScheduling/deleteCinemaAndSchedulingTable");
            PostData(JSON.stringify(row.id)).then((response)=>{
                if (response===true){
                    ElMessage({
                        message: '删除成功',
                        type: 'success',
                    })
                }else {
                    ElMessage.error("删除失败")
                }
            }).catch(error=>{
                console.log(error)
            })
            row.delDialogVisible=false;
            pageModify();
        }
        //获取行的索引
        let getRowIndex=({row,rowIndex})=>{
            row.index=rowIndex
        }

        //------------分页--------------
        //获取分页后的排片数据
        let pageModify=()=>{
            getTotal();
            setSelectHttpName("http://localhost:8181/scheduling/getSchedulingPage");
            PostData(JSON.stringify(pageData)).then((response)=>{
                schedulingData.items=response
                schedulingData.items.forEach(item=>{
                    // item.startTime=moment(item.startTime, "HH:mm").toDate();
                    // item.endTime=moment(item.endTime, "HH:mm").toDate();
                    Object.assign(item, { show: false });
                    Object.assign(item, { modifyDialogVisible: false });
                    Object.assign(item, { delDialogVisible: false });
                })
            }).catch(error=>{
                console.log(error)
            })
        }
        //修改每页显示条目个数
        let handleSizeChange=(pageSize)=>{
            schedulingData.pagination.pageSize=pageSize
            pageData.pageable.pageSize=schedulingData.pagination.pageSize
            pageModify()
        }
        //修改当前页数数据
        let handleCurrentChange=(currentPage)=>{
            schedulingData.pagination.currentPage=currentPage
            pageData.pageable.currentPage=schedulingData.pagination.currentPage
            pageModify()
        }

        //----------------Select----------------
        let SchedulingId=reactive({id:[]})
        //行的checkbox被选中和取消时调用，获取真实id
        let selectHandle=(selection)=>{
            console.log("SchedulingId:")
            let id=reactive([])
            selection.forEach((item,index)=>{
                id[index]=item.id;
            })
            SchedulingId.id=id
            console.log(SchedulingId.id)
        }
        //全选的checkbox被选中和取消时调用，获取真实id
        let selectAllHandle=(selection)=>{
            console.log("SchedulingId:")
            let id=reactive([])
            selection.forEach((item,index)=>{
                id[index]=item.id;
            })
            SchedulingId.id=id
            console.log(SchedulingId.id)
        }
        //删除多条数据
        let delAll=()=>{
            setSelectHttpName("http://localhost:8181/CinemaAndScheduling/delManyCinemaAndSchedulingTableData");
            if (SchedulingId==null){
                ElMessage({
                    message: '请选择要删除的数据',
                    type: 'success',
                })
            }else {
                PostData(SchedulingId.id).then((response)=>{
                    if (response===true){
                        ElMessage({
                            message: '删除成功',
                            type: 'success',
                        })
                    }else {
                        ElMessage.error("删除失败")
                    }
                }).catch(error=>{
                    console.log(error)
                })
            }
            pageModify();
        }

        //执行
        pageModify()
        return{
            tableData,
            nameOptions,
            movieTheaterOptions,
            movieTypeOptions,
            priceOptions,
            schedulingData,
            getRowIndex,
            addition,
            modify,
            save,
            handleSizeChange,
            handleCurrentChange,
            delDialog,
            cancelled,
            selectHandle,
            selectAllHandle,
            delAll,
            zhCn,
        }
    },
}
</script>

<style scoped>

</style>