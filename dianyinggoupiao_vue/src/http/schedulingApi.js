import $http from '@/http/index'

/**
 * 根据影院id和电影名称获取排片数据
 * @param selectHttpName http
 * @param cinema_id 影院id
 * @param movie_name 影片名称
 * @returns {Promise<axios.AxiosResponse<any>>}
 */
export async function post_findByCinemaIdAndByMovieName(selectHttpName,cinema_id,movie_name){
    const formData =new FormData()
    formData.append("cinema_id",cinema_id)
    formData.append("movie_name",movie_name)
    return await $http.post(selectHttpName,formData ).then(res=>{
        return res;
    }).catch(error=>{
        console.log(error)
    });
}

/**
 * 根据不同条件查询当前影片名为movie_name的在哪些影院有排片，
 * @param selectHttpName http
 * @param movie_name 电影名称
 * @param brand 品牌
 * @param administrativeDistrict 行政区域
 * @param type 影厅类型
 * @param service 服务
 * @returns {Promise<axios.AxiosResponse<any>>} 影院List
 */
export async function post_findByMovie_name(
    selectHttpName,
    movie_name,
    brand,
    administrativeDistrict,
    type,
    service
){
    const formData =new FormData()
    formData.append("movie_name",movie_name)
    formData.append("brand",brand)
    formData.append("administrativeDistrict",administrativeDistrict)
    formData.append("type",type)
    formData.append("service",service)
    return await $http.post(selectHttpName,formData ).then(res=>{
        return res;
    }).catch(error=>{
        console.log(error)
    });
}