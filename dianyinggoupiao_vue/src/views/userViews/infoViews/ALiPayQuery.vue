<template>
    <div style="display: none"></div>
</template>

<script>
import {onMounted} from 'vue'
import {getOrder, postOrder, setSelectHttpName} from '@/http/orderApi'
import router from '@/router'

export default {
    name: "ALiPayQuery",
    setup(){
        let goto=()=>{
            setSelectHttpName("http://localhost:8181/Order/returnQuery")
            getOrder().then((result)=>{
                console.log(result)
                router.push({name:"order"})
            }).catch(error => {
                console.error("请求失败：", error);
                console.error("错误信息：", error.response.data);
            });
        }
        onMounted(goto);
        return{

        }
    }
}
</script>

<style scoped>

</style>