import {ElMessage} from 'element-plus'
import $axios from 'axios'

const formData = new FormData();// 创建 FormData 对象

export function clearFormData(formData){
    // 创建一个空数组用于存储需要删除的 key
    let keysToDelete = [];

    // 遍历 formData，将需要删除的 key 存入 keysToDelete 数组
    formData.forEach((value, key) => {
        keysToDelete.push(key);
    });

    // 根据 keysToDelete 数组删除 formData 中的元素
    keysToDelete.forEach(key => {
        formData.delete(key);
    });
}
//图片上传功能
export async function handleImageUpload() {
    try {
        const response = await $axios.post('http://localhost:8181/Images/uploadImage', formData, {
            headers: {
                'Content-Type': 'multipart/form-data'
            }
        });
        ElMessage.success(response.data)
    } catch (error) {
        console.error(error);
    }
}
//图片上传前处理
export function beforeAvatarUpload (AccName,type) {
    return (rawFile)=>{
        clearFormData(formData)
        if (rawFile.type === 'image/jpg' || rawFile.type === 'image/png' || rawFile.type === 'image/jpeg' ) {
            formData.append('image', rawFile); // 将文件添加到 FormData 对象中，并设置参数名为 'image'
            formData.append('accName', AccName)
            formData.append('type', type)
        }else if (rawFile.size / 1024 / 1024 > 1) {
            ElMessage.error('用户头像文件大小不能超过1MB！');
            return false;
        }else {
            ElMessage.error('图片格式必须为png/jpeg或者jpg格式！');
            return false;
        }
        return true;
    }
}



const MovieImageFormData = new FormData();
//获取AccName
export function beforeImageUpload(rawFile){
    clearFormData(MovieImageFormData)
    MovieImageFormData.append("image",rawFile);
    console.log("MovieImageFormData:",MovieImageFormData)
    console.log("rawFile:",rawFile)
    return true;
}
export async function handleMovieImageUpload() {
    try {
        const response = await $axios.post('http://localhost:8181/Images/getImage', MovieImageFormData, {
            headers: {
                'Content-Type': 'multipart/form-data'
            }
        });
        ElMessage.success(response.data)
    } catch (error) {
        console.error(error);
    }
}
export {MovieImageFormData};