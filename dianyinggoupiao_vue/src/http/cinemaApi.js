import $http from '@/http/index'

let selectHttpName="http://localhost:8181/Cinema/findCinemaAll"
// 查询某个影院的所有影片 "http://localhost:8181/CinemaAndMovieService/findCinemaAllMovieInfo"

export const cinemaHttp=()=>{
    return $http.get(selectHttpName)
}

export async function getData(data){
    await $http.get(selectHttpName).then((res)=>{
        data.value=res
    }).catch(error => {
        console.error("请求失败：", error);
        console.error("错误信息：", error.response.data);
    });
    return data
}
export async function PostData(data){
    return new Promise((resolve, reject) => {
        $http.post(selectHttpName, {data}).then((msg) => {
            resolve(msg); // 使用 resolve 将数据返回
        }).catch(error => {
            console.error("请求失败：", error);
            console.error("错误信息：", error.response.data);
            reject(error); // 使用 reject 将错误返回
        });
    });
}

export async function AddMovieData(selectHttpName,data){
    return await $http.post(selectHttpName, data).then((msg) => {
            return msg;
        }).catch(error => {
            console.error("请求失败：", error);
            console.error("错误信息：", error.response.data);
            return error;
        });
}
export async function selectCinema(data,cinemaInfo){
    await $http.post(selectHttpName,{data}).then((res)=>{
        cinemaInfo.value=res
    }).catch(error => {
        console.error("请求失败：", error);
        console.error("错误信息：", error.response.data);
    });
    return {cinemaInfo}
}
//id查询
export function cinemaIdQuery(selectHttpName,globalVariable,tableData,form){
    setSelectHttpName(selectHttpName);
    selectCinema(JSON.stringify(globalVariable),tableData).then((result)=>{

        //判断数据是否为空
        if (result.cinemaInfo.value!=null&&form.IdInput!==""){
            for (let index = result.cinemaInfo.value.length - 1; index >= 0; index--) {
                //判断输入的数据和result.cinemaInfo.value[index].id数据是否相同
                if (form.IdInput==result.cinemaInfo.value[index].id){
                    tableData.value[0]=result.cinemaInfo.value[index]
                    if (index!=0){
                        //删除tableData的数组对象中索引为index的数据
                        tableData.value.splice(index,1)
                    }
                }else {
                    //删除tableData的数组对象中索引为index的数据
                    tableData.value.splice(index,1)
                }
            }
        }
    })
}

//影片查询
export function cinemaMovieQuery(selectHttpName,globalVariable,tableData,form){
    setSelectHttpName(selectHttpName);
    selectCinema(JSON.stringify(globalVariable),tableData).then((result)=>{
        if (result.cinemaInfo.value!=null&&form.MovieInput!==""){
            let i=0;
            for (let index = result.cinemaInfo.value.length - 1; index >= 0; index--) {
                if (result.cinemaInfo.value[index].movie_info.name.includes(form.MovieInput)){
                    tableData.value[i]=result.cinemaInfo.value[index]
                    if (index!=i){
                        tableData.value.splice(index,1)
                    }
                    i++;
                }else {
                    tableData.value.splice(index,1)
                }
            }
        }
    })
}
//获取图片url
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

export async function postCinemaParam (data) {

    //使用FormData可以确保请求的数据格式正确、完整性等问题
    let formData =new FormData();
    formData.append("data",data)
    return await $http.post(selectHttpName, formData).then(res=>{
        console.log("res:")
        console.log(res)
        return res;
    }).catch(error=>{
        console.log(error)
    });
}

export async function postCinemaBody (data) {

    return await $http.post(selectHttpName, data).then(res=>{
        console.log("res:")
        console.log(res)
        return res;
    }).catch(error=>{
        console.log(error)
    });
}


export function setSelectHttpName(http){
    selectHttpName=http
}
export function getImg(name) {
    return require(`@/assets/${name}.png`) // 使用require导入图片路径
}



