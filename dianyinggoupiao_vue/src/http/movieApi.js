import $http from '@/http/index'
import {reactive} from 'vue'

let selectHttpName="http://localhost:8181/Movie/findAccAll"
let searchMovieData=reactive([])
let allMovieInfo=reactive([])
let movieInfo=reactive({})
let movieCondition=reactive([{}])
export const movieHttp=(data)=>{
    return $http.get(selectHttpName,data)
}

const urlData=reactive({
    userAccName:"",
    type:"movie"
})
//获取图片url
export const getImgUrl =(movieName)=>{
    setSelectHttpName("http://localhost:8181/Images/imageIdByUrl")
    urlData.userAccName = movieName;
    return getImageUrl(urlData).then(url=>{
        return url;
    })
}
export async function getCount (selectHttpName) {
    return await $http.get(selectHttpName).then( res => {
        return res;
    }).catch(error=>{
        console.log(error)
    })
}
export async function findMovie (data) {
    return await $http.get(selectHttpName).then(async res => {
        const movies = await Promise.all(res.map(async item => {
            const imageUrl = await getImgUrl(item.name);
            return {
                ...item,
                imageUrl: imageUrl,
            };
        }));
        return movies;
    }).then(res=>{
        data.value=res
        return data.value;
    });
}
export async function findMovieInfoByName (selectHttpName,movieName) {
    return await $http.post(selectHttpName,{movieName}).then(res => {
        return res;
    }).catch(error=>{
        console.log(error)
    })
}

export async function findAllMovieInfo(allMovieInfo){
    return await $http.get(selectHttpName).then(async res => {
        const movies = await Promise.all(res.map(async item => {
            const imageUrl = await getImgUrl(item.name);
            return {
                ...item,
                imageUrl: imageUrl,
            };
        }));
        return movies;
    }).then(res=>{
        allMovieInfo.value=res
        return allMovieInfo.value;
    }).catch(error => {
        console.error("请求失败：", error);
    });
}

export async function findAllByCinemaId(scheduling,cinema_id){
    const formData =new FormData()
    formData.append("cinema_id",cinema_id)
    return await $http.post(selectHttpName,formData).then(async res => {
        const movies = await Promise.all(res.map(async item => {
            const imageUrl = await getImgUrl(item.name);
            return {
                ...item,
                imageUrl: imageUrl,
            };
        }));
        return movies;
    }).then(res=>{
        scheduling.value=res
        return scheduling.value;
    }).catch(error => {
        console.error("请求失败：", error);
    });
}

export async function searchMovie(movieName){
    await $http.post(selectHttpName,{movieName}).then((res)=>{
        searchMovieData=res
    }).catch(error => {
        console.error("请求失败：", error);
    });
}

export async function findMovieInfo(movieName){
    let data=reactive({});
    await $http.post(selectHttpName,{movieName}).then((res)=>{
        movieInfo=res
        data.value=res
    }).catch(error => {
        console.error("请求失败：", error);
    });
    return data;
}

export async function findConditionMovie(data){
    return await $http.post(selectHttpName,{data}).then((res)=>{
        return res
    }).catch(error => {
        console.error("请求失败：", error);
    });
}

export async function findInfo(data){
    await $http.get(selectHttpName,).then((res)=>{
        data.value=res
    }).catch(error => {
        console.error("请求失败：", error);
    });
    return data
}
export async function getMovie(selectHttpName){
    return await $http.get(selectHttpName).then((res)=>{
        return res;
    }).catch(error => {
        console.error("请求失败：", error);
    });
}
export async function getImageUrl (data) {
    return await $http.post(selectHttpName, {data}).then(res=>{
        return res;
    }).catch(error=>{
        console.log(error)
    });
}

export async function postParam (selectHttpName,cinema_id) {
    const formData=new FormData();
    formData.append("cinema_id",cinema_id)
    return await $http.post(selectHttpName, formData).then(res=>{
        return res;
    }).catch(error=>{
        console.log(error)
    });
}
export async function postParamAndUrl (selectHttpName,youData,cinema_id) {
    return await postParam(selectHttpName,cinema_id).then(async res => {
        const movies = await Promise.all(res.map(async item => {
            const imageUrl = await getImgUrl(item.name);
            return {
                ...item,
                imageUrl: imageUrl,
            };
        }));
        return movies;
    }).then(res=>{
        youData.value=res
        return youData.value;
    }).catch(error => {
        console.error("请求失败：", error);
    });

}


export async function postDelMovie (selectHttpName,id,movieName) {
    const formData=new FormData();
    formData.append("id",id)
    formData.append("movieName",movieName)
    return await $http.post(selectHttpName, formData).then(res=>{
        return res;
    }).catch(error=>{
        console.log(error)
    });
}


export function setSelectHttpName(http){
    selectHttpName=http
}

export function getAllMovieInfo(){
    return allMovieInfo
}
export function getMovieInfo(){
    return movieInfo
}
export function getMovieCondition(){
    return movieCondition;
}
export function getImg(name) {
    return require(`@/assets/${name}.png`) // 使用require导入图片路径
}



