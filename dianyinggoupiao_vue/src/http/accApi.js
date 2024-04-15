import $http from '@/http/index'
import {reactive} from 'vue'
import $axios from 'axios'


let selectHttpName = "http://localhost:8181/acc/findAccAll"
let delHttpName = ""
export const loginHttp = (data) => {
    return $http.get(selectHttpName, data)
}

let table = reactive({
    currentPage: 1,
    pageSize: 6,
    total: null,
})
let record = reactive({
    idInput: "",
    accInput: "",
    name: "",
})
const del = reactive({
    name: "",
    dialogVisible: false,
    isdig: false,
    isDel: false,
})
export let Data = reactive([])

export const clear = (data) => {
    for (let key in data) {
        delete data[key];
    }
}

export async function fenYe(data) {
    await $http.get(selectHttpName).then((res)=> {
        table.total = res.length
        let currentPageStart = (table.currentPage - 1) * table.pageSize
        let currentPageEnd = table.currentPage * table.pageSize
        let count = currentPageStart
        clear(Data)
        if (currentPageEnd <= table.total) {
            for (let i = currentPageStart; i < currentPageEnd; i++) {
                Data[count] = res[i]
                count++
            }
        } else {
            for (let i = currentPageStart; i < table.total; i++) {
                Data[count] = res[i]
                count++
            }
        }
        data=Data
    }).catch(error => {
        console.error("请求失败：", error);
        console.error("错误信息：", error.response.data);
    });
    return data
}

export const idRecord = async () => {
    await $axios.get(selectHttpName).then(function (res) {
        clear(Data)
        for (let i = 0; i < res.data.length; i++) {
            if (parseInt(record.idInput) == res.data[i].id) {
                Data[0] = res.data[i]
            }
        }
    })
}
export let accRecord = async () => {
    await $axios.get(selectHttpName).then(function (res) {
        clear(Data)
        res.data.forEach(item => {
            if (item.name.indexOf(record.accInput) != -1 && record.accInput != null) {
                Data.push(item)
            }
        })

    })
}


export let delRecord = () => {
    console.log(del.name)
    $axios.get(delHttpName + del.name).then(function (res) {
        del.dialogVisible = false
        if (res.data == "删除成功") {
            alert("删除成功!")
        } else {
            alert("删除失败!")
        }
    })
}

//获取图片数据url
export async function getImageUrl (data) {
    // console.log("data:")
    // console.log(data)
    return await $http.post(selectHttpName, {data}).then(res=>{
        // console.log("res:")
        // console.log(res)
        return res;
    }).catch(error=>{
        console.log(error)
    });
}

//post获取数据
export async function postUserInfo (data) {
    return await $http.post(selectHttpName, data).then(res=>{
        // console.log("res:")
        // console.log(res)
        return res;
    }).catch(error=>{
        console.log(error)
    });
}

//get获取数据
export async function getUserInfo (httpName) {
    return await $http.get(httpName).then(res=>{
        // console.log("res:")
        // console.log(res)
        return res;
    }).catch(error=>{
        console.log(error)
    });
}


export function setSelectHttpName(http) {
    selectHttpName = http
}

export function setDelHttpName(http) {
    delHttpName = http
}

export function getTable() {
    return table
}

export function setTable(tt) {
    table = tt
}

export function setRecord(Re) {
    record = Re
}

export function setDelRecord(Re) {
    record = Re
}

export function getDelRecord() {
    return del
}


