<template>
    <div ref="mapContainer" style="width: 100%; height: 400px;">

    </div>
</template>

<script>
/**
 * 通过 百度地图Api 来设置地图
 */
import {defineComponent, onMounted, ref} from 'vue'
const address=ref('')
export default defineComponent({
    name: "IMapComponent",
    setup(){
        const mapContainer = ref(null); // 创建地图容器的引用

        onMounted(() => {
            const map = new window.BMapGL.Map(mapContainer.value);          // 创建地图实例

            //获取用户当前位置
            const geoL = new window.BMapGL.Geolocation();
            geoL.getCurrentPosition(function(location) {
                const point = new window.BMapGL.Point(location.point.lng, location.point.lat);  // 创建点坐标
                map.centerAndZoom(point, 15);                 // 初始化地图，设置中心点坐标和地图级别
            });
            map.enableScrollWheelZoom(true);
            map.setHeading(64.5);   //设置地图旋转角度
            map.setTilt(73);       //设置地图的倾斜角度

            //给地图添加控件
            const scaleCtrl = new window.BMapGL.ScaleControl();  // 添加比例尺控件
            map.addControl(scaleCtrl);
            const zoomCtrl = new window.BMapGL.ZoomControl();  // 添加缩放控件
            map.addControl(zoomCtrl);
            const cityCtrl = new window.BMapGL.CityListControl();  // 添加城市列表控件
            map.addControl(cityCtrl);
            // 添加地图点击事件监听器
            map.addEventListener("click", async function(e) {
                try {
                    // 把墨卡托坐标转换为经纬度坐标（e.point 是墨卡托坐标）
                    const mercatorToLngLat = await map.mercatorToLnglat(e.point.lng, e.point.lat);
                    //使用window.BMapGL.Point创建一个Point类型的数据
                    const point = new window.BMapGL.Point(mercatorToLngLat[0], mercatorToLngLat[1]);

                    // 把坐标转换为地址
                    const geoc = new window.BMapGL.Geocoder();
                    geoc.getLocation(point, function(result) {
                        address.value=result.address;
                        console.log("result:", address.value);
                    });
                } catch (error) {
                    console.error("Error:", error);
                }
            });
        });
        return{
            mapContainer,
        }
    },
    address
})
</script>

<style scoped>

</style>