<template>
  <div id="main">
    <section class="section">
      <div class="row justify-content-center">
        <div class="col-lg-5 col-12">
          <div class="card">
            <div class="card-body">
              <div class="col-12">
                <div id="auth-center">
                  <div class="auth-logo">
                    <img src="assets/images/logo/logo.png" alt="Logo" />
                  </div>
                  <h1 class="auth-title">Log in.</h1>
                  <p class="auth-subtitle mb-5">
                    Log in with your data that you entered during registration.
                  </p>

                  <div class="form-group position-relative has-icon-left mb-4">
                    <input
                      type="email"
                      class="form-control form-control-xl"
                      placeholder="UserEmail"
                      v-model="$store.state.login.userEmail"
                    />
                    <div class="form-control-icon">
                      <i class="bi bi-person"></i>
                    </div>
                  </div>
                  <div class="form-group position-relative has-icon-left mb-4">
                    <input
                      type="password"
                      class="form-control form-control-xl"
                      placeholder="Password"
                      v-model="$store.state.login.userPassword"
                    />
                    <div class="form-control-icon">
                      <i class="bi bi-shield-lock"></i>
                    </div>
                  </div>
                  <div class="form-check form-check-lg d-flex align-items-end">
                    <input
                      class="form-check-input me-2"
                      type="checkbox"
                      value=""
                      id="flexCheckDefault"
                    />
                    <label
                      class="form-check-label text-gray-600"
                      for="flexCheckDefault"
                    >
                      Keep me logged in
                    </label>
                  </div>
                  <button
                    @click="login"
                    class="btn btn-primary btn-block btn-lg shadow-lg mt-5"
                  >
                    Log in
                  </button>

                  <div class="text-center mt-5 text-lg fs-4">
                    <p class="text-gray-600">
                      Don't have an account?
                      <a href="auth-register.html" class="font-bold">Sign up</a
                      >.
                    </p>
                    <p>
                      <a class="font-bold" href="auth-forgot-password.html"
                        >Forgot password?</a
                      >.
                    </p>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </section>
  </div>
</template>

<script>
import Vue from "vue";
import VueAlertify from "vue-alertify";
Vue.use(VueAlertify);

import http from "@/common/axios.js";
export default {
  name: "Login",
  methods: {
    login() {
      http
        .post("/login", {
          userEmail: this.$store.state.login.userEmail,
          userPassword: this.$store.state.login.userPassword,
        })
        .then(({ data }) => {
          console.log("LoginVue: data : ");
          console.log(data);
          this.$store.commit("SET_LOGIN", {
            isLogin: true,
            userSeq: data.userSeq,
            userName: data.userName,
            userProfileImageUrl: data.userProfileImageUrl,
            userPoint: data.userPoint,
            userAdmin: data.userAdmin,
          });
          this.$router.push("/main");
        })
        .catch((error) => {
          console.log("LoginVue: error : ");
          console.log(error);
          if (error.response.status == "404") {
            this.$alertify.error("이메일 또는 비밀번호를 확인하세요.");
          } else {
            this.$alertify.error("Opps!! 서버에 문제가 발생했습니다.");
          }
        });
    },
  },
};
</script>

<style>
.auth-logo {
  margin-bottom: 7rem;
}
.auth-logo img {
  height: 2rem;
}
</style>
