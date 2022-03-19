import Vue from "vue";
import VueRouter from "vue-router";

Vue.use(VueRouter);

import Main from "@/view/Main.vue";
import Login from "@/view/Login.vue";
import Register from "@/view/Register.vue";
import AptDealMain from "@/view/AptDealMain.vue";
import BoardFree from "@/view/Board/BoardFree.vue";
import BoardNotice from "@/view/Board/BoardNotice.vue";
import BoardReview from "@/view/Board/BoardReview.vue";
import UserInterest from "@/view/UserInterest.vue";
import UserUpdateDelete from "@/view/UserUpdateDelete.vue";
import store from "@/store/store.js";

export default new VueRouter({
  routes: [
    {
      path: "/",
      component: Main,
      beforeEnter: (to, from, next) => {
        if (!store.state.login.isLogin) {
          next("/login");
        } else {
          return next();
        }
      },
    },

    {
      name: "Main",
      path: "/main",
      component: Main,
      beforeEnter: (to, from, next) => {
        if (!store.state.login.isLogin) {
          next("/login");
        } else {
          return next();
        }
      },
    },

    {
      name: "Login",
      path: "/login",
      component: Login,
    },

    {
      name: "Register",
      path: "/register",
      component: Register,
    },

    {
      name: "UserInterest",
      path: "/userinterest",
      component: UserInterest,
      beforeEnter: (to, from, next) => {
        if (!store.state.login.isLogin) {
          next("/login");
        } else {
          return next();
        }
      },
    },

    {
      name: "UserUpdateDelete",
      path: "/userupdatedelete",
      component: UserUpdateDelete,
    },

    {
      name: "AptDealMain",
      path: "/aptdealmain",
      component: AptDealMain,
      beforeEnter: (to, from, next) => {
        console.log(store.state.login.isLogin);
        if (!store.state.login.isLogin) {
          next("/login");
        } else {
          return next();
        }
      },
    },

    {
      name: "BoardNotice",
      path: "/boardnotice",
      component: BoardNotice,
      beforeEnter: (to, from, next) => {
        console.log(store.state.login.isLogin);
        if (!store.state.login.isLogin) {
          next("/login");
        } else {
          return next();
        }
      },
    },

    {
      name: "BoardReview",
      path: "/boardreview",
      component: BoardReview,
      beforeEnter: (to, from, next) => {
        console.log(store.state.login.isLogin);
        if (!store.state.login.isLogin) {
          next("/login");
        } else {
          return next();
        }
      },
    },

    {
      name: "BoardFree",
      path: "/boardfree",
      component: BoardFree,
      beforeEnter: (to, from, next) => {
        console.log(store.state.login.isLogin);
        if (!store.state.login.isLogin) {
          next("/login");
        } else {
          return next();
        }
      },
    },
  ],
});
