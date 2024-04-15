import $http from '@/http/index'
import {reactive} from 'vue'

let selectHttpName="http://localhost:8181/Order/findOrderAll"


export const orderHttp=()=>{
    return $http.get(selectHttpName)
}

export async function postOrder (order){
    let orderData=reactive({})
    await $http.post(selectHttpName,{order}).then((res)=>{
        orderData=res
    }).catch(error => {
        console.error("请求失败：", error);
        console.error("错误信息：", error.response.data);
    });
    console.log("orderData:",orderData);
    return orderData
}
export async function getOrder (){
    let orderData=reactive({})
    await $http.get(selectHttpName).then((res)=>{
        orderData=res
    }).catch(error => {
        console.error("请求失败：", error);
        console.error("错误信息：", error.response.data);
    });
    return orderData
}


export function setSelectHttpName(http){
    selectHttpName=http
}



