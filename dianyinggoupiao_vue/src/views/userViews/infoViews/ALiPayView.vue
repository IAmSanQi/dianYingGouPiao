<template>
    <div id="pay">

    </div>
</template>

<script>
import {onMounted} from 'vue'
import {useRoute} from 'vue-router'

export default {
    name: "ALipayView",
    setup(){
        const useRoute1=useRoute();
        let form1=useRoute1.query.form;
        const submitForm = () => {
            document.getElementById("pay").innerHTML=form1;
            const form2 = document.querySelector('form[name="punchout_form"]');
            //
            // //这种语法是为了防止在访问不存在的对象属性或方法时出现错误。
            // // 在我们的修正代码中，在调用 submit() 方法之前，我们使用了可选链操作符 ?. 来确保 form 对象存在。如果 form 是 null 或 undefined，
            // // 则不会调用 submit() 方法并避免出现错误。
            form2?.submit();
        };

        // 在合适的时机调用 submitForm() 函数
        // 这里提供了一个示例，在组件加载完成后立即提交表单
        onMounted(submitForm);
        return{

        }
    },
}
</script>

<style scoped>

</style>