<template>
  <div id="main">
    <div class="row justify-content-center">
      <div class="col-lg-5 col-12">
        <div id="auth-center">
          <div class="auth-logo">
            <a href="index.html"
              ><img src="assets/images/logo/logo.png" alt="Logo"
            /></a>
          </div>
          <br />
          <br />
          <h1 class="auth-title">회원가입</h1>

          <div class="card">
            <div class="card-content">
              <div class="card-body">
                <div class="form-body">
                  <div class="row">
                    <div class="col-12">
                      <div class="form-group has-icon-left">
                        <label for="first-name-icon">이름</label>
                        <div class="position-relative">
                          <input
                            type="text"
                            class="form-control"
                            v-model="userName"
                            :class="{
                              'is-valid': isUserNameFocusAndValid,
                              'is-invalid': isUserNameFocusAndInvalid,
                            }"
                            @input="validateUserName"
                            @focus="isUserNameFocus = true"
                            placeholder="Name"
                            id="first-name-icon"
                          />
                          <div class="valid-feedback">성공</div>
                          <div class="invalid-feedback">
                            이름을 입력해 주세요.
                          </div>
                        </div>
                      </div>
                    </div>
                    <div class="col-12">
                      <div class="form-group has-icon-left">
                        <label for="email-id-icon">이메일</label>
                        <div class="position-relative">
                          <input
                            type="text"
                            class="form-control"
                            :class="{
                              'is-valid': isUserEmailFocusAndValid,
                              'is-invalid': isUserEmailFocusAndInValid,
                            }"
                            v-model="userEmail"
                            @input="validateEmail"
                            @focus="isUserEmailFocus = true"
                            placeholder="Email"
                            id="email-id-icon"
                          />
                          <div class="valid-feedback">성공</div>
                          <div class="invalid-feedback">
                            올바른 Email 을 입력해 주세요.
                          </div>
                        </div>
                      </div>
                    </div>
                    <div class="col-12">
                      <div class="form-group has-icon-left">
                        <label for="password-id-icon">비밀번호</label>
                        <div class="position-relative">
                          <input
                            type="password"
                            class="form-control"
                            :class="{
                              'is-valid': isUserPasswordFocusAndValid,
                              'is-invalid': isUserPasswordFocusAndInvalid,
                            }"
                            v-model="userPassword"
                            @input="validatePassword"
                            @focus="isUserPasswordFocus = true"
                            placeholder="Password"
                            id="password-id-icon"
                          />

                          <div class="valid-feedback">성공</div>
                          <div class="invalid-feedback">
                            1개 이상의 특수문자, 대소문자 및 숫자 포함 8자리
                            이상이여야 합니다.
                          </div>
                        </div>
                      </div>
                    </div>
                    <div class="col-12">
                      <div class="form-group has-icon-left">
                        <label for="password-id-icon">비밀번호 확인</label>
                        <div class="position-relative">
                          <input
                            type="password"
                            class="form-control"
                            v-model="userPassword2"
                            :class="{
                              'is-valid': isUserPassword2FocusAndValid,
                              'is-invalid': isUserPassword2FocusAndInvalid,
                            }"
                            @input="validatePassword2"
                            @focus="isUserPassword2Focus = true"
                            placeholder="Password"
                            id="password-id-icon"
                          />

                          <div class="valid-feedback">성공</div>
                          <div class="invalid-feedback">
                            비밀번호가 일치하지 않습니다.
                          </div>
                        </div>
                      </div>
                    </div>
                    <div class="col-12">
                      <div class="form-group has-icon-left">
                        <label for="email-id-icon">닉네임</label>
                        <div class="position-relative">
                          <input
                            type="text"
                            class="form-control"
                            :class="{
                              'is-valid': isUserNicknameFocusAndValid,
                              'is-invalid': isUserNicknameFocusAndInvalid,
                            }"
                            v-model="userNickname"
                            @input="validateNickname"
                            @focus="isUserNicknameFocus = true"
                            placeholder="Nickname"
                            id="email-id-icon"
                          />
                          <div class="valid-feedback">성공</div>
                          <div class="invalid-feedback">
                            닉네임을 입력해주세요.
                          </div>
                        </div>
                      </div>
                    </div>
                    <div class="col-12">
                      <div class="form-group has-icon-left">
                        <label for="email-id-icon">추천인</label>
                        <div class="position-relative">
                          <input
                            type="text"
                            class="form-control"
                            v-model="Recommender"
                            placeholder="없다면 입력하지 않으셔도 됩니다"
                            id="email-id-icon"
                          />
                        </div>
                      </div>
                    </div>
                    <div class="col-12 d-flex justify-content-end">
                      <button
                        type="submit"
                        @click="register"
                        class="btn btn-primary col-3"
                      >
                        가입
                      </button>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
          <div class="text-center mt-5 text-lg fs-4">
            <p class="text-gray-600">
              이미 계정이 있으신가요?
              <router-link to="/login">로그인</router-link>
            </p>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import Vue from "vue";
import VueAlertify from "vue-alertify";
Vue.use(VueAlertify);

import http from "@/common/axios.js";

export default {
  data() {
    return {
      userName: "",
      userEmail: "",
      userPassword: "",
      userPassword2: "",
      userNickname: "",
      Recommender: "",

      isUserNameFocus: false,
      isUserEmailFocus: false,
      isUserPasswordFocus: false,
      isUserPassword2Focus: false,
      isUserNicknameFocus: false,

      isUserNameValid: false,
      isUserEmailValid: false,
      isUserPasswordValid: false,
      isUserPassword2Valid: false,
      isUserNicknameValid: false,
    };
  },
  computed: {
    isUserNameFocusAndValid() {
      return this.isUserNameFocus && this.isUserNameValid;
    },
    isUserNameFocusAndInvalid() {
      return this.isUserNameFocus && !this.isUserNameValid;
    },
    isUserEmailFocusAndValid() {
      return this.isUserEmailFocus && this.isUserEmailValid;
    },
    isUserEmailFocusAndInValid() {
      return this.isUserEmailFocus && !this.isUserEmailValid;
    },
    isUserPasswordFocusAndValid() {
      return this.isUserPasswordFocus && this.isUserPasswordValid;
    },
    isUserPasswordFocusAndInvalid() {
      return this.isUserPasswordFocus && !this.isUserPasswordValid;
    },
    isUserPassword2FocusAndValid() {
      return this.isUserPassword2Focus && this.isUserPassword2Valid;
    },
    isUserPassword2FocusAndInvalid() {
      return this.isUserPassword2Focus && !this.isUserPassword2Valid;
    },
    isUserNicknameFocusAndValid() {
      return this.isUserNicknameFocus && this.isUserNicknameValid;
    },
    isUserNicknameFocusAndInvalid() {
      return this.isUserNicknameFocus && !this.isUserNicknameValid;
    },
  },
  methods: {
    validateUserName() {
      this.isUserNameValid = this.userName.length > 0 ? true : false;
      console.log(this.isUserNameValid);
    },
    validateEmail() {
      // ^ 시작일치, $ 끝 일치
      // {2, 3} 2개 ~ 3개
      // * 0회 이상, + 1회 이상
      // [-_.] - 또는 _ 또는 .
      // ? 없거나 1회
      let regexp =
        /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i;
      this.isUserEmailValid = regexp.test(this.userEmail) ? true : false;
      console.log(this.isUserEmailValid);
    },
    validatePassword() {
      let patternEngAtListOne = new RegExp(/[a-zA-Z]+/); // + for at least one
      let patternSpeAtListOne = new RegExp(/[~!@#$%^&*()_+|<>?:{}]+/); // + for at least one
      let patternNumAtListOne = new RegExp(/[0-9]+/); // + for at least one

      this.isUserPasswordValid =
        patternEngAtListOne.test(this.userPassword) &&
        patternSpeAtListOne.test(this.userPassword) &&
        patternNumAtListOne.test(this.userPassword) &&
        this.userPassword.length >= 8
          ? true
          : false;
    },
    validatePassword2() {
      this.isUserPassword2Valid =
        this.userPassword == this.userPassword2 ? true : false;
    },
    validateNickname() {
      this.isUserNicknameValid = this.userNickname.length > 0 ? true : false;
      console.log(this.isUserNicknameValid);
    },
    register() {
      if (
        !this.isUserEmailValid ||
        !this.isUserPasswordValid ||
        !this.isUserPassword2Valid ||
        !this.isUserNicknameValid
      )
        return;
      http
        .post("/register", {
          userName: this.userName,
          userEmail: this.userEmail,
          userPassword: this.userPassword,
          userNickname: this.userNickname,
        })
        .then(({ data }) => {
          console.log("RegisterVue: data : ");
          console.log(data);

          let $this = this;
          this.$alertify.alert(
            "회원가입을 축하합니다. 로그인 페이지로 이동합니다",
            function () {
              $this.$router.push("/login");
            }
          );
        })
        .catch((error) => {
          console.log("RegisterVue: error : ");
          console.log(error);
          if (error.response.status == "404") {
            this.$alertify.error("서버에 문제가 발생했습니다.");
          }
        });
    },
  },
};
</script>

<style></style>
