<template>
    <div ref="div">
        <h1>注册成功</h1>
    </div>
</template>

<script>
import { ref, onMounted, onBeforeUnmount } from 'vue';
import { useRouter } from 'vue-router';

export default {
    name: "SuccessView",
    setup() {
        const div = ref(null);
        const router = useRouter();
        let time = 5;

        const jumpFun = () => {
            if (time <= 0) {
                clearInterval(intervalId);
                time = 0;
                router.push({ path: "/" });
            } else {
                div.value.innerHTML = "将在" + time + "秒后返回登录页面";
                time--;
            }
        };

        let intervalId;

        onMounted(() => {
            intervalId = setInterval(jumpFun, 1000);
        });

        onBeforeUnmount(() => {
            console.log("beforeUnmount执行了");
            clearInterval(intervalId);
        });

        return {
            div,
            time,
            jumpFun,
        };
    },
};
</script>

<style scoped>

</style>