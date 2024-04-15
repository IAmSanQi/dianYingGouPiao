import {PostData, setSelectHttpName} from '@/http/cinemaApi'
import $http from '@/http'
import {ref} from 'vue'

/**
 * 获取当前影院的订单数据
 * @param http
 * @param tableDate 接收数据的变量
 * @param data 影院账户
 * @returns {Promise<*>}
 */
export async function getOrderTablePost(http,tableDate,data){
    setSelectHttpName("http://localhost:8181/Order/findByCinemaName");
    await PostData(JSON.stringify(data)).then((res)=>{
        tableDate=res
    }).catch(error=>{
        console.log(error)
    })
    return tableDate;
}

/**
 * 分页
 * @param selectHttpName http
 * @param currentPage 当前页
 * @param pageSize 页的大小
 * @returns {Promise<Ref<UnwrapRef<[]>>>}
 */
export async function tableFenYe(selectHttpName,currentPage,pageSize) {
    return await $http.get(selectHttpName).then((res)=> {
        const total = res.length
        let currentPageStart = (currentPage - 1) * pageSize
        let currentPageEnd = currentPage * pageSize
        const tableData=ref([]);
        if (currentPageEnd <= total) {
            for (let i = currentPageStart; i < currentPageEnd; i++) {
                tableData.value.push(res[i])
            }
        } else {
            for (let i = currentPageStart; i < total; i++) {
                tableData.value.push(res[i])
            }
        }
        return tableData.value;
    }).catch(error => {
        console.error("请求失败：", error);
    });
}

/**
 * 获取总条数
 * @param selectHttpName http
 * @returns {Promise<axios.AxiosResponse<any>>}
 */
export async function getTotal(selectHttpName) {
    return await $http.get(selectHttpName).then((res)=> {
        return res.length;
    }).catch(error => {
        console.error("请求失败：", error);
    });
}

/**
 * 根据id查询
 * @param selectHttpName
 * @param idInput
 * @returns {Promise<axios.AxiosResponse<any>>}
 */
export async function idRecord(selectHttpName,idInput) {
    return await $http.get(selectHttpName).then((res)=> {
        for (let resKey in res) {
            if (parseInt(resKey)===parseInt(idInput)-1){
                return res[resKey];
            }
        }
    }).catch(error => {
        console.error("请求失败：", error);
    });
}

export async function nameRecord(selectHttpName,nameInput) {
    return await $http.get(selectHttpName).then((res)=> {
        const data=ref([])
        for (let resKey in res) {
            if (res[resKey].name.includes(nameInput)){
                data.value.push(res[resKey]);
            }
        }
        return data.value;
    }).catch(error => {
        console.error("请求失败：", error);
    });
}


