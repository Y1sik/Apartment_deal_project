<template>
  <div id="sidebar" class="active">
    <div class="sidebar-wrapper active">
      <div class="sidebar-header">
        <div class="d-flex justify-content-between">
          <div class="logo">
            <router-link to="/"
              ><img src="assets/images/logo/logo.png" alt="Logo" srcset=""
            /></router-link>
          </div>
          <div class="toggler">
            <a href="#" class="sidebar-hide d-xl-none d-block"
              ><i class="bi bi-x bi-middle"></i
            ></a>
          </div>
        </div>
      </div>
      <div class="sidebar-menu">
        <ul class="menu">
          <div class="sibebar-title">
            <a v-show="$store.state.login.isLogin">
              <img
                v-bind:src="requireImg"
                alt=""
                width="80"
                height="80"
                style="border-radius: 50%"
                class="d-inline-block align-text-top"
                align="left"
              />
              <br />
              <div style="font-size: 22px; display: inline-block">
                &nbsp;&nbsp;{{ $store.state.login.userName }}
              </div>
              <div>
                &nbsp;&nbsp;&nbsp;{{
                  $store.state.login.userPoint
                }}point<br /><br />
              </div>
            </a>
          </div>
        </ul>
        <ul class="menu">
          <li class="sidebar-item active">
            <router-link
              to="/login"
              v-show="!$store.state.login.isLogin"
              class="sidebar-link"
            >
              <svg class="bi" width="1em" height="1em" fill="currentColor">
                <use
                  xlink:href="assets/vendors/bootstrap-icons/bootstrap-icons.svg#pie-chart-fill"
                />
              </svg>
              <span>Login</span>
            </router-link>
          </li>
          <li class="sidebar-item active">
            <div
              @click="logout"
              class="sidebar-link"
              v-show="$store.state.login.isLogin"
              style="cursor: pointer"
            >
              <svg class="bi" width="1em" height="1em" fill="currentColor">
                <use
                  xlink:href="assets/vendors/bootstrap-icons/bootstrap-icons.svg#pie-chart-fill"
                />
              </svg>
              <span>Logout</span>
            </div>
          </li>
          <li class="sidebar-item active">
            <router-link
              v-show="!$store.state.login.isLogin"
              class="sidebar-link"
              to="/register"
            >
              <svg class="bi" width="1em" height="1em" fill="currentColor">
                <use
                  xlink:href="assets/vendors/bootstrap-icons/bootstrap-icons.svg#suit-heart-fill"
                />
              </svg>
              <span>회원가입</span>
            </router-link>
          </li>
          <li class="sidebar-item active has-sub" v-on:click="clickMypage">
            <router-link
              v-show="$store.state.login.isLogin"
              to="#"
              class="sidebar-link"
            >
              <svg class="bi" width="1em" height="1em" fill="currentColor">
                <use
                  xlink:href="assets/vendors/bootstrap-icons/bootstrap-icons.svg#person-fill"
                />
              </svg>
              <span>마이페이지</span>
            </router-link>
            <ul
              class="submenu active"
              v-show="$store.state.sidebar.MypageSubShow"
            >
              <li class="submenu-item">
                <router-link to="/userupdatedelete">
                  <span>회원정보 수정/탈퇴</span>
                </router-link>
              </li>
              <li class="submenu-item">
                <router-link to="/userinterest">
                  <span>나의 관심매물</span>
                </router-link>
              </li>
            </ul>
          </li>
          <li class="sidebar-item active">
            <router-link
              v-show="$store.state.login.isLogin"
              class="sidebar-link"
              to="/aptdealmain"
            >
              <svg class="bi" width="1em" height="1em" fill="currentColor">
                <use
                  xlink:href="assets/vendors/bootstrap-icons/bootstrap-icons.svg#exclamation-square-fill"
                />
              </svg>
              <span>매물검색</span>
            </router-link>
          </li>

          <li
            class="sidebar-item active has-sub"
            v-show="$store.state.login.isLogin"
          >
            <a href="#" class="sidebar-link" @click="toggleShow">
              <i class="bi bi-stack"> </i>
              <span>게시판</span>
            </a>
            <ul class="submenu active" v-if="show">
              <li class="submenu-item">
                <router-link
                  v-show="$store.state.login.isLogin"
                  to="/boardnotice"
                >
                  <span>공지사항</span>
                </router-link>
              </li>
              <li class="submenu-item">
                <router-link
                  v-show="$store.state.login.isLogin"
                  to="/boardreview"
                >
                  <span>리뷰</span>
                </router-link>
              </li>
              <li class="submenu-item">
                <router-link
                  v-show="$store.state.login.isLogin"
                  to="/boardfree"
                >
                  <span>자유게시판</span>
                </router-link>
              </li>
            </ul>
          </li>
        </ul>
      </div>
      <button class="sidebar-toggler btn x"><i data-feather="x"></i></button>
    </div>
  </div>
</template>

<script>
import Vue from "vue";
import VueAlertify from "vue-alertify";
Vue.use(VueAlertify);

import http from "@/common/axios.js";
export default {
  name: "NavBar",
  data() {
    return {
      show: false,
    };
  },
  computed: {
    isLoginGetters() {
      return this.$storethis.$store.getters.isLogin;
    },
    requireImg: function () {
      if (this.$store.state.login.userProfileImageUrl == "") {
        return require("../assets/img/noProfile.png");
      } else {
        return require("../assets" +
          this.$store.state.login.userProfileImageUrl);
      }
    },
  },
  methods: {
    toggleShow() {
      this.show = !this.show;
    },
    logout() {
      http
        .get("/logout")
        .then(({ data }) => {
          console.log(data);

          let $this = this;
          this.$store.commit("SET_LOGOUT", {
            isLogin: false,
            userSeq: "",
            userName: "",
            userProfileImageUrl: "",
          });
          this.$alertify.alert("로그아웃 되었습니다.", function () {
            $this.$router.push("/");
          });
        })
        .catch((error) => {
          if (error.response.status == "404") {
            this.$alertify.error("로그인이 되어 있는지를 확인하세요.");
          } else {
            this.$alertify.error("Opps!! 서버에 문제가 발생했습니다.");
          }
        });

      this.$store.commit("SET_MYPAGESUBSHOW", {
        MypageSubShow: false,
      });
    },
    clickMypage() {
      this.$store.commit("SET_MYPAGESUBSHOW", {
        MypageSubShow: !this.$store.state.sidebar.MypageSubShow,
      });
    },
  },
};
</script>

<style></style>
