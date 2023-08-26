import Vue from 'vue'
import VueRouter from 'vue-router'
import login from '../views/login'
import index from '../views/index'
import HotelList from '../views/HotelList'
import HotelDetails from '../views/HotelDetails'
import OrderList from '../views/OrderList'
import SignIn from '../components/SignIn'
import Register from '../components/Register'
import Wallet from '../views/wallet'
import RecordList from '../views/RecordList'
import CollectionList from "../views/CollectionList"

Vue.use(VueRouter)

const routes = [
    {
        path: '/',
        redirect: '/login'
    },
    {
        path: '/login',
        component: login,
        children: [
            {
                path: '',
                name: 'signIn',
                component: SignIn
            },
            {
                path: 'register',
                name: 'register',
                component: Register
            },
        ]
    },
    {
        path: '/index',
        name: 'index',
        component: index
    },
    {
        path: '/hotelList',
        name: 'hotelList',
        component: HotelList
    },
    {
        path: '/collectionList',
        name: 'collectionList',
        component: CollectionList
    },
    {
        path: '/hotelDetails',
        name: 'hotelDetails',
        component: HotelDetails
    },
    {
        path: '/orderList',
        name: 'orderList',
        component: OrderList
    },
    {
        path: '/wallet',
        name: 'wallet',
        component: Wallet
    },
    {
        path: '/recordList',
        name: 'recordList',
        component: RecordList
    },
]

const router = new VueRouter({
    routes
})

//路由前置守卫
// router.beforeEach((to, from, next) => {
//     next();
//     if (to.path === '/login') {
//         next();
//     } else {
//         let Token = localStorage.getItem('Token')
//         if (Token === null || Token === '') {
//             next('/login')
//             console.log('没有token，跳转到登录页面');
//         } else {
//             console.log('路由守卫触发');
//             next();
//         }
//     }
//
// });


export default router
