<template>
    <baidu-map
        class="bm-view"
        :center="{ lng: 116.403401, lat: 39.924823 }"
        :zoom="15"
        :scroll-wheel-zoom="true"
        ak="lKTF6dTPp0bflD1mToHqAhmfIhGml6mn"
        v="3.0"
        type="API"
        @click="handelClick"
    >
        <!-- anchor：控件所在方位 可选(BMAP_ANCHOR_TOP_LEFT,BMAP_ANCHOR_TOP_RIGHT,BMAP_ANCHOR_BOTTOM_LEFT，BMAP_ANCHOR_BOTTOM_RIGHT) 左上，右上，左下，右下-->
        <!-- 分别是二维地图和卫星地图 -->
        <bm-map-type
            :map-types="['BMAP_NORMAL_MAP', 'BMAP_HYBRID_MAP']"
        ></bm-map-type>
        <!-- 城市列表-选择城市搜索城市(支持市级以上搜索) -->
        <bm-city-list
            anchor="BMAP_ANCHOR_TOP_LEFT"
            @changeAfter="ElMessage.success('成功切换城市')"
        ></bm-city-list>
        <!-- 点标记 -->
        <bm-marker :position="state" :dragging="true" :raiseOnDrag="true">
            <bm-info-window :show="show" @close="show = false" @open="show = true">
                {{ address }}
            </bm-info-window>
        </bm-marker>
        <!-- 定位 -->
        <bm-geolocation
            anchor="BMAP_ANCHOR_TOP_RIGHT"
            :showAddressBar="true"
            :autoLocation="true"
            @locationSuccess="(e) =>ElMessage.success(
                '定位成功已切换到' +
                e.addressComponent.province +
                e.addressComponent.city,
          )
      "
            @locationError="ElMessage.error('定位失败，请稍后再试')"
        ></bm-geolocation>
    </baidu-map>
</template>

<script>

/**
 * 通过vue-baidu-map-3x来设置地图
 */
import {defineComponent, reactive, ref} from 'vue'
import {
    BaiduMap,
    BmMapType,
    BmCityList,
    BmMarker,
    BmGeolocation,
    BmInfoWindow,
} from 'vue-baidu-map-3x'
import {ElMessage} from 'element-plus'
export default defineComponent({
    name: "VMapComponent",
    setup(){
        const show = ref(false)
        const address = ref(undefined)
        const state = reactive({
            lng: 116.403401,
            lat: 39.924823,
        })

        /**
         * 添加点标记
         * @param e e
         */
        const handelClick = (e) => {
            show.value = false
            state.lng = e.point.lng
            state.lat = e.point.lat
            const geocoder = new window.BMap.Geocoder()
            // 解析地址
            geocoder.getLocation(e.point, (res) => {
                if (res) {
                    show.value = true
                    address.value = res.address
                }
            })
        }
        return{
            show,
            address,
            state,
            ElMessage,
            BaiduMap,
            BmMapType,
            BmCityList,
            BmMarker,
            BmGeolocation,
            BmInfoWindow,
            handelClick
        }
    }
})
</script>

<style scoped>

</style>