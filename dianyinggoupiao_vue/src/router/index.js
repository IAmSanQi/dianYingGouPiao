import {createRouter, createWebHistory} from 'vue-router'
import HomeView from '../views/HomeView.vue'

const routes = [
    {
        path: '/Home',
        name: 'home',
        component: HomeView,
        children: [
            {
                path: '/Index',
                name: 'index',
                meta: {
                    isShow: true,
                    title: "首页"
                },
                component: () => import(/* webpackChunkName: "home" */ '../views/userViews/indexViews'),
            },
            {
                path: '/Movie',
                name: 'movie',
                meta: {
                    isShow: true,
                    title: "电影"
                },
                component: () => import(/* webpackChunkName: "home" */ '../views/userViews/movieViews'),
            },
            {
                path: '/Cinema',
                name: 'cinema',
                meta: {
                    isShow: true,
                    title: "影院"
                },
                component: () => import(/* webpackChunkName: "home" */ '../views/userViews/cinemaViews'),
            },
            {
                path: '/List',
                name: 'list',
                meta: {
                    isShow: true,
                    title: "榜单"
                },
                component: () => import(/* webpackChunkName: "home" */ '../views/userViews/listViews'),
                children: [
                    {
                        path: '/HotScreening',
                        name: 'hotScreening',
                        meta: {
                            isShow: true,
                            title: "热映口碑榜"
                        },
                        props: true,
                        component: () => import(/* webpackChunkName: "home" */ '../views/userViews/listViews/hotScreeningView'),
                    },
                    {
                        path: '/Expect',
                        name: 'expect',
                        meta: {
                            isShow: true,
                            title: "最受期待榜"
                        },
                        props: true,
                        component: () => import(/* webpackChunkName: "home" */ '../views/userViews/listViews/expectView'),
                    },
                    {
                        path: '/Picket',
                        name: 'picket',
                        meta: {
                            isShow: true,
                            title: "国内票房榜"
                        },
                        props: true,
                        component: () => import(/* webpackChunkName: "home" */ '../views/userViews/listViews/picketView'),
                    },
                    {
                        path: '/Ranking',
                        name: 'ranking',
                        meta: {
                            isShow: true,
                            title: "Top100榜"
                        },
                        props: true,
                        component: () => import(/* webpackChunkName: "home" */ '../views/userViews/listViews/rankingView'),
                    },
                ]
            },
            {
                path: '/Order',
                name: 'order',
                meta: {
                    isShow: true,
                    title: "订单"
                },
                component: () => import(/* webpackChunkName: "home" */ '../views/userViews/infoViews/order'),
            },
            {
                path: '/ALiPayView',
                name: 'aLiPayView',
                component: () => import(/* webpackChunkName: "home" */ '../views/userViews/infoViews/ALiPayView'),
            },
            {
                path: '/ALiPayQuery',
                name: 'aLiPayQuery',
                component: () => import(/* webpackChunkName: "home" */ '../views/userViews/infoViews/ALiPayQuery'),
            },
            {
                path: '/Search',
                name: 'search',
                meta: {
                    isShow: true,
                    title: "搜索"
                },
                props: true,
                component: () => import(/* webpackChunkName: "home" */ '../views/userViews/searchViews'),
                children: [
                    {
                        path: '/MovieContent',
                        name: 'movieContent',
                        meta: {
                            isShow: true,
                            title: "电影"
                        },
                        props: true,
                        component: () => import(/* webpackChunkName: "home" */ '../views/userViews/searchContentViews/movieContentView'),
                    },
                    {
                        path: '/CinemaContent',
                        name: 'cinemaContent',
                        meta: {
                            isShow: true,
                            title: "电影"
                        },
                        props: true,
                        component: () => import(/* webpackChunkName: "home" */ '../views/userViews/searchContentViews/cinemaContentView'),
                    },
                ]
            },
            {
                path: '/movieDetails',
                name: 'movieDetails',
                meta: {
                    isShow: true,
                    title: "电影详情"
                },
                component: () => import(/* webpackChunkName: "home" */ '../views/buyTicket/movieDetails'),
            },
            {
                path: '/buyTicket',
                name: 'buyTicket',
                meta: {
                    isShow: true,
                    title: "购票详情"
                },
                component: () => import(/* webpackChunkName: "home" */ '../views/buyTicket/buyTicket'),
            },
            {
                path: '/Ticketing',
                name: 'ticketing',
                meta: {
                    isShow: true,
                    title: "购票"
                },
                component: () => import(/* webpackChunkName: "home" */ '../views/buyTicket/ticketing'),
            },
            {
                path: '/SelectSeat',
                name: 'selectSeat',
                meta: {
                    isShow: true,
                    title: "选座"
                },
                component: () => import(/* webpackChunkName: "home" */ '../views/buyTicket/selectSeat'),
            },
            {
                path: '/Payment',
                name: 'payment',
                meta: {
                    isShow: true,
                    title: "付款"
                },
                component: () => import(/* webpackChunkName: "home" */ '../views/buyTicket/payment'),
            },
            {
                path: '/UserInfo',
                name: 'userInfo',
                // route level code-splitting
                // this generates a separate chunk (about.[hash].js) for this route
                // which is lazy-loaded when the route is visited.
                component: () => import(/* webpackChunkName: "about" */ '../views/userViews/userInfoViews')
            },

        ]
    },
    {
        path: '/',
        name: 'login',
        // route level code-splitting
        // this generates a separate chunk (about.[hash].js) for this route
        // which is lazy-loaded when the route is visited.
        component: () => import(/* webpackChunkName: "about" */ '../views/loginViews/LoginView.vue')
    },
    {
        path: '/CinemaLogin',
        name: 'cinemaLogin',
        // route level code-splitting
        // this generates a separate chunk (about.[hash].js) for this route
        // which is lazy-loaded when the route is visited.
        component: () => import(/* webpackChunkName: "about" */ '../views/loginViews/cinemaLogin.vue')
    },
    {
        path: '/ManageLogin',
        name: 'manageLogin',
        // route level code-splitting
        // this generates a separate chunk (about.[hash].js) for this route
        // which is lazy-loaded when the route is visited.
        component: () => import(/* webpackChunkName: "about" */ '../views/loginViews/manageLogin.vue')
    },
    {
        path: '/Manage',
        name: 'manage',
        // route level code-splitting
        // this generates a separate chunk (about.[hash].js) for this route
        // which is lazy-loaded when the route is visited.
        component: () => import(/* webpackChunkName: "about" */ '../views/manageViews/admin/ManageView'),
        children: [
            {
                path: '/AdminPersonInfo',
                name: 'adminPersonInfo',
                meta: {
                    isShow: true,
                    title:"个人信息"
                },
                component: () => import(/* webpackChunkName: "home" */ '../views/manageViews/admin/subpage/adminPersonInfo'),
            },
            {
                path: '/MovieManage',
                name: 'movieManage',
                meta: {
                    isShow: true,
                    title:"电影管理"
                },
                component: () => import(/* webpackChunkName: "home" */ '../views/manageViews/admin/subpage/movieManage'),
            },
            {
                path: '/MoviegoerManage',
                name: 'moviegoerManage',
                meta: {
                    isShow: true,
                    title:"影院用户信息"
                },
                component: () => import(/* webpackChunkName: "home" */ '../views/manageViews/admin/subpage/moviegoerManage'),
            },
            {
                path: '/OrdinaryUserManage',
                name: 'ordinaryUserManage',
                meta: {
                    isShow: true,
                    title:"普通用户信息"
                },
                component: () => import(/* webpackChunkName: "home" */ '../views/manageViews/admin/subpage/ordinaryUserManage'),
            },
        ]
    },
    {
        path: '/Register',
        name: 'register',
        // route level code-splitting
        // this generates a separate chunk (about.[hash].js) for this route
        // which is lazy-loaded when the route is visited.
        component: () => import(/* webpackChunkName: "about" */ '../views/RegisterView')
    },
    {
        path: '/CinemaRegister',
        name: 'cinemaRegister',
        // route level code-splitting
        // this generates a separate chunk (about.[hash].js) for this route
        // which is lazy-loaded when the route is visited.
        component: () => import(/* webpackChunkName: "about" */ '../views/registerViews/cinemaRegister')
    },
    {
        path: '/Success',
        name: 'success',
        // route level code-splitting
        // this generates a separate chunk (about.[hash].js) for this route
        // which is lazy-loaded when the route is visited.
        component: () => import(/* webpackChunkName: "about" */ '../views/SuccessView')
    },
    {
        path:'/CinemaMangeViews',
        name:'cinemaManageViews',
        component: ()=>import('../views/manageViews/cinemaManage/cinemaManageViews'),
        children: [
            {
                path: '/cinemaPersonInfo',
                name: 'cinemaPersonInfo',
                meta: {
                    isShow: true,
                    title: "个人信息"
                },
                component: () => import(/* webpackChunkName: "home" */ '../views/manageViews/cinemaManage/subpage/cinemaPersonInfo'),
            },
            {
                path: '/FilmManage',
                name: 'filmManage',
                meta: {
                    isShow: true,
                    title:"电影管理"
                },
                component: () => import(/* webpackChunkName: "home" */ '../views/manageViews/cinemaManage/subpage/filmManage'),
            },
            {
                path: '/TheaterManage',
                name: 'theaterManage',
                meta: {
                    isShow: true,
                    title:"排片管理"
                },
                component: () => import(/* webpackChunkName: "home" */ '../views/manageViews/cinemaManage/subpage/theaterManage'),
            },
            {
                path: '/CinemaOrderInfo',
                name: 'cinemaOrderInfo',
                meta: {
                    isShow: true,
                    title:"电影订单信息"
                },
                component: () => import(/* webpackChunkName: "home" */ '../views/manageViews/cinemaManage/subpage/cinemaOrderInfo'),
            },
        ]
    },

]

const router = createRouter({
    history: createWebHistory(process.env.BASE_URL),
    routes
})

export default router
