<template>
<div id="main">
  <div class="row justify-content-center">
    <div class="col-lg-4 col-12">
        <div id="auth-center">
            <div class="auth-logo">
                <a href="index.html"><img src="assets/images/logo/logo.png" alt="Logo"></a>
            </div>
            <br>
            <br>
            <h1 class="auth-title">회원정보 수정/삭제</h1>
            <div class="card">
                <div class="card-content">
                    <div class="card-body">
                            <div class="form-body">
                                <div class="row">
                                    <div class="col-12">
                                        <div class="form-group has-icon-left">
                                            <label for="first-name-icon">이름</label>
                                            <div class="position-relative">
                                                <input type="text" class="form-control"
                                                    v-model="userName"
                                                    :class="{ 'is-valid': isUserNameFocusAndValid , 'is-invalid': isUserNameFocusAndInvalid  }"
                                                    @input="validateUserName" 
                                                    @focus="isUserNameFocus = true"
                                                    placeholder="Name"
                                                    id="first-name-icon">
                                                <div class="valid-feedback">성공</div>
                                                <div class="invalid-feedback">이름을 입력해 주세요.</div>
                                            </div>
                                            
                                        </div>
                                        
                                    </div>
                                    <div class="col-12">
                                        <div class="form-group has-icon-left">
                                            <label for="email-id-icon">닉네임</label>
                                            <div class="position-relative">
                                                <input type="text" class="form-control"
                                                    :class="{ 'is-valid': isUserNicknameFocusAndValid , 'is-invalid': isUserNicknameFocusAndInvalid  }" 
                                                    v-model="userNickname"
                                                    @input="validateNickname" 
                                                    @focus="isUserNicknameFocus = true"
                                                    placeholder="Nickname"
                                                    id="email-id-icon">
                                                <div class="valid-feedback">성공</div>
                                                <div class="invalid-feedback">닉네임을 입력해주세요.</div>
                                            </div>
                                        </div>
                                        
                                    </div>
                                    <div class="col-12">
                                        <div class="form-group has-icon-left">
                                            <label for="email-id-icon">이메일</label>
                                            <div class="position-relative">
                                                <input type="text" class="form-control"
                                                    :class="{ 'is-valid': isUserEmailFocusAndValid , 'is-invalid': isUserEmailFocusAndInValid  }" 
                                                    v-model="userEmail"
                                                    @input="validateEmail" 
                                                    @focus="isUserEmailFocus = true"
                                                    placeholder="Email"
                                                    id="email-id-icon">
                                                <div class="valid-feedback">성공</div>
                                                <div class="invalid-feedback">올바른 Email 을 입력해 주세요.</div>
                                            </div>
                                        </div>
                                        
                                    </div>
                                    <div class="col-12">
                                        <div class="form-group has-icon-left">
                                            <label for="password-id-icon">비밀번호</label>
                                            <div class="position-relative">
                                                <input type="password" class="form-control"
                                                    :class="{ 'is-valid': isUserPasswordFocusAndValid , 'is-invalid': isUserPasswordFocusAndInvalid  }" 
                                                    v-model="userPassword"
                                                    @input="validatePassword"
                                                    @focus="isUserPasswordFocus = true"
                                                    placeholder="Password" id="password-id-icon">
                                                
                                                <div class="valid-feedback"> 성공</div>
                                                <div class="invalid-feedback">1개 이상의 특수문자, 대소문자 및 숫자 포함 8자리 이상이여야 합니다.</div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-12">
                                        <div class="form-group has-icon-left">
                                            <label for="password-id-icon">비밀번호 확인</label>
                                            <div class="position-relative">
                                                <input type="password" class="form-control"
                                                    v-model="userPassword2"
                                                    :class="{ 'is-valid': isUserPassword2FocusAndValid , 'is-invalid': isUserPassword2FocusAndInvalid  }" 
                                                    @input="validatePassword2"
                                                    @focus="isUserPassword2Focus = true"
                                                    placeholder="Password" id="password-id-icon">
                                                
                                                <div class="valid-feedback">성공</div>
                                                <div class="invalid-feedback">비밀번호가 일치하지 않습니다.</div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-12 d-flex justify-content-end">
                                        <button
                                            @click="update"
                                            class="btn btn-primary me-1 mb-1">수정</button>
                                        <button
                                            @click="resign"
                                            class="btn btn-danger me-1 mb-1">탈퇴</button>
                                    </div>
                                </div>
                            </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</div>
</template>

<script>
import Vue from 'vue';
import VueAlertify from 'vue-alertify'; 
Vue.use(VueAlertify);

import http from "@/common/axios.js";

export default {
    data(){
        return{
            userSeq : this.$store.state.login.userSeq,
            userName: this.$store.state.login.userName,
            userEmail: this.$store.state.login.userEmail,
            userPassword: this.$store.state.login.userPassword,
            userPassword2: '',
            userNickname: this.$store.state.login.userNickname,

            isUserNameFocus: true,
            isUserNicknameFocus: true,
            isUserEmailFocus: true,
            isUserPasswordFocus: true,
            isUserPassword2Focus: false,

            isUserNameValid: true,
            isUserNicknameValid: true,
            isUserEmailValid: true,
            isUserPasswordValid: true,
            isUserPassword2Valid: false,
        }        
    },
    computed: {
    isUserNameFocusAndValid(){
      return this.isUserNameFocus && this.isUserNameValid;
    },
    isUserNameFocusAndInvalid(){
      return this.isUserNameFocus && ! this.isUserNameValid;
    },
    isUserEmailFocusAndValid(){
      return this.isUserEmailFocus && this.isUserEmailValid;
    },
    isUserEmailFocusAndInValid(){
      return this.isUserEmailFocus && ! this.isUserEmailValid;
    },
    isUserPasswordFocusAndValid(){
      return this.isUserPasswordFocus && this.isUserPasswordValid;
    },
    isUserPasswordFocusAndInvalid(){
      return this.isUserPasswordFocus && ! this.isUserPasswordValid;
    },
    isUserPassword2FocusAndValid(){
      return this.isUserPassword2Focus && this.isUserPassword2Valid;
    },
    isUserPassword2FocusAndInvalid(){
      return this.isUserPassword2Focus && ! this.isUserPassword2Valid;
    },
    isUserNicknameFocusAndValid(){
      return this.isUserNicknameFocus && this.isUserNicknameValid;
    },
    isUserNicknameFocusAndInvalid(){
      return this.isUserNicknameFocus && ! this.isUserNicknameValid;
    },
  },
  methods: {
    validateUserName() {
      this.isUserNameValid = this.userName.length > 0 ? true : false;
      console.log(this.isUserNameValid)
    },
    validateEmail() {
      // ^ 시작일치, $ 끝 일치
      // {2, 3} 2개 ~ 3개
      // * 0회 이상, + 1회 이상
      // [-_.] - 또는 _ 또는 .
      // ? 없거나 1회
      let regexp = /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i;
      this.isUserEmailValid = (regexp.test(this.userEmail)) ? true : false;
      console.log(this.isUserEmailValid)
    },
    validatePassword() {

      let patternEngAtListOne = new RegExp(/[a-zA-Z]+/);// + for at least one
      let patternSpeAtListOne = new RegExp(/[~!@#$%^&*()_+|<>?:{}]+/);// + for at least one
      let patternNumAtListOne = new RegExp(/[0-9]+/);// + for at least one
      
      this.isUserPasswordValid = 
        ( patternEngAtListOne.test( this.userPassword ) 
          && patternSpeAtListOne.test( this.userPassword )  
          && patternNumAtListOne.test( this.userPassword )
          && this.userPassword.length >= 8
        ) ? true : false;
    },
    validatePassword2() {
        this.isUserPassword2Valid = ( this.userPassword == this.userPassword2 ) ? true : false;
    },
    validateNickname() {
      this.isUserNicknameValid = this.userNickname.length > 0 ? true : false;
      console.log(this.isUserNicknameValid)
    },
    update(){
      if( ! this.isUserEmailValid || ! this.isUserPasswordValid || ! this.isUserPassword2Valid || ! this.isUserNicknameValid) return;
      http.put(
        "/update",
        {
          userSeq: this.userSeq,
          userName: this.userName,
          userEmail: this.userEmail,
          userPassword: this.userPassword,
          userNickname: this.userNickname
        }
      )
      .then(({ data }) => {
        console.log("RegisterVue: data : ");
        console.log(data);

        let $this = this;
        this.$store.commit(
                'SET_LOGIN',
                { isLogin: false, userNickname:'', userSeq:'', userName: '', userProfileImageUrl: ''}
            );
        this.$alertify.alert(
          '회원정보가 수정되었습니다. 다시 로그인 해주세요.',
          function() {
            $this.$router.push("/login");
          }
        );        
      })
      .catch( error => {
        console.log("RegisterVue: error : ");
        console.log(error);
        if( error.response.status == '404'){
          this.$alertify.error('서버에 문제가 발생했습니다.');          
        }
          
      });
    },
    resign(){
        http.delete(
        "/delete/"+this.userSeq
      )
      .then(({ data }) => {
        console.log("RegisterVue: data : ");
        console.log(data);

        let $this = this;
        this.$store.commit(
          'SET_LOGIN',
          { isLogin: false,userSeq:'', userName: '', userProfileImageUrl: ''}
        );
        this.$alertify.alert(
          '회원정보가 삭제되었습니다.',
          function() {
            $this.$router.push("/main");
          }
        );        
      })
      .catch( error => {
        console.log("RegisterVue: error : ");
        console.log(error);
        if( error.response.status == '404'){
          this.$alertify.error('서버에 문제가 발생했습니다.');          
        }
          
      });
    }
  },
}
</script>

<style>

</style>