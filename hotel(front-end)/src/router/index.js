import Vue from 'vue'
import VueRouter from 'vue-router'
import login from '../views/login'
import index from '../views/index'
import MainContent from '../components/MainContent'
import DepartmentManage from '../components/DepartmentManage'
import HotelManage from '../components/HotelManage'
import MyHotel from "../components/MyHotel";
import RoomType from "../components/RoomType";
import BedType from "../components/BedType";
import DiscountManage from "../components/DiscountManage";
import OrderManage from "../components/OrderManage";
import MenuManage from "../components/MenuManage";
import RoomManage from "../components/RoomManage";
import UserManage from "../components/UserManage";
import LogManage from "../components/LogManage";
import IsolationManage from "../components/IsolationManage";
import DailyTest from "../components/DailyTest";
import DeclarationReview from "../components/DeclarationReview";
import AssignPersonnel from "../components/AssignPersonnel";
import MaterialReview from "../components/MaterialReview";
import MaterialApplication from "../components/MaterialApplication";
import HotelMaterialInfo from "../components/HotelMaterialInfo";

Vue.use(VueRouter)

const routes = [
    {
        path: '/',
        redirect: '/login'
    },
    {
        path: '/login',
        component: login
    },
    {
        name: 'index',
        path: '/index',
        component: index,
        children: [
            {
                path: '',
                name: ' ',
                component:MainContent
            },
            {
                path: 'department',
                name: 'department',
                component: DepartmentManage
            },
            {
                path: 'hotel',
                name: 'hotel',
                component: HotelManage
            },
            {
                path: 'myHotel',
                name: 'myHotel',
                component: MyHotel
            },
            {
                path: 'roomType',
                name: 'roomType',
                component: RoomType
            },
            {
                path: 'bedType',
                name: 'bedType',
                component: BedType
            },
            {
                path: 'discountManage',
                name: 'discountManage',
                component: DiscountManage
            },
            {
                path: 'orderManage',
                name: 'orderManage',
                component: OrderManage
            },
            {

                path: 'menuManage',
                name: 'menuManage',
                component: MenuManage
            },
            {
                path: 'roomManage',
                name: 'roomManage',
                component: RoomManage
            },
            {
                path: 'userManage',
                name: 'userManage',
                component: UserManage
            },
            {
                path: 'logManage',
                name: 'logManage',
                component: LogManage
            },
            {
                path: 'isolationManage',
                name: 'isolationManage',
                component: IsolationManage
            },
            {
                path: 'hotelMaterialInfo',
                name: 'hotelMaterialInfo',
                component: HotelMaterialInfo
            },
            {
                path: 'dailyTest',
                name: 'dailyTest',
                component: DailyTest,
            },
            {
                path: 'declarationReview',
                name: 'declarationReview',
                component: DeclarationReview,
            },
            {
                path: 'assignPersonnel',
                name: 'assignPersonnel',
                component: AssignPersonnel,
            },
            {
                path: 'materialReview',
                name: 'materialReview',
                component: MaterialReview,                
            },
            {
                path: 'materialApplication',
                name: 'materialApplication',
                component: MaterialApplication,                    
            }

        ]
    }
]

const router = new VueRouter({
    routes
})

//路由前置守卫
router.beforeEach((to, from, next) => {
    next();
    if (to.path === '/login') {
        next();
    } else {
        let Token = localStorage.getItem('Token')
        if (Token === null || Token === '') {
            next('/login')
            console.log('没有token，跳转到登录页面');
        } else {
            // console.log('路由守卫触发');
            next();
        }
    }



    // document.getElementById("app").style.display="none";
    // if (to.path === '/login'||to.path === '/newPswForget'||to.path === '/emailVerify') {
    //     next();
    // } else {
    //     let Token = localStorage.getItem('Token');
    //     if (Token === null || Token === '') {
    //         next('/login');
    //         console.log('没有token')
    //     } else {
    //         next();
    //     }
    // }


});

export default router
