import Vue from "vue";
import Vuex from "vuex";

Vue.use(Vuex);

import http from "@/common/axios.js";
import router from "@/routers/routers.js";

export default new Vuex.Store({
  state: {
    login: {
      // login, navbar, mypage
      isLogin: false,
      userSeq: "",
      userEmail: "admin@ssafy.com",
      userPassword: "admin",
      userName: "",
      userProfileImageUrl: "",
      userNickname: "",
      userAdmin: "",
      userPoint: "",
    },

    free: {
      //free board list
      list: [],
      limit: 10,
      offset: 0,
      searchWord: "",

      // pagination
      listRowCount: 10,
      pageLinkCount: 10,
      currentPageIndex: 1,

      totalListItemCount: 0,

      // board detail
      freeId: 0,
      title: "",
      content: "",
      userName: "",
      regDt: {},
      likeCount: 0,
      readCount: 0,
      fileList: [],
      sameUser: false,
      check: false,
    },

    review: {
      //free board list
      list: [],
      limit: 10,
      offset: 0,
      searchWord: "",

      // pagination
      listRowCount: 10,
      pageLinkCount: 10,
      currentPageIndex: 1,

      totalListItemCount: 0,

      // board detail
      reviewId: 0,
      title: "",
      content: "",
      userName: "",
      regDt: {},
      likeCount: 0,
      readCount: 0,
      fileList: [],
      sameUser: false,
    },

    notice: {
      //free board list
      list: [],
      limit: 10,
      offset: 0,
      searchWord: "",

      // pagination
      listRowCount: 10,
      pageLinkCount: 10,
      currentPageIndex: 1,

      totalListItemCount: 0,

      // board detail
      noticeId: 0,
      title: "",
      content: "",
      userName: "",
      regDt: {},
      likeCount: 0,
      readCount: 0,
      fileList: [],
      sameUser: false,
    },

    sidebar: {
      MypageSubShow: false,
    },

    apt: {
      radioShow: false,
      // deal list
      list: [],
      limit: 5,
      offset: 0,
      searchWord: "",

      selectLat: 0.0,
      selectLng: 0.0,

      //housedeal
      dealAmount: 0,
      dealYMD: "",
      dealYear: "",
      dealMonth: "",
      dealDay: "",
      area: "",
      floor: "",
      type: "",
      rentMoney: 0,
      house_no: "",

      // houseinfo
      no: 0,
      city: "",
      sido: "",
      gugun: "",
      dong: "",
      aptName: "",
      buildYear: "",
      jibun: "",
      lat: 0,
      lng: 0,
      img: "",
    },
    interest: {
      // interestlist
      list: [],
      AptName: "",
      dong: "",
      floor: "",
      area: "",
      dealAmount: "",
    },
  },
  // state 상태를 변경하는 유일한 방법
  mutations: {
    SET_LOGIN(state, payload) {
      state.login.userSeq = payload.userSeq;
      state.login.isLogin = payload.isLogin;
      state.login.userName = payload.userName;
      state.login.userProfileImageUrl = payload.userProfileImageUrl;
      state.login.userPoint = payload.userPoint;
      state.login.userAdmin = payload.userAdmin;
      state.login.userNickname = payload.userNickname;
    },
    SET_MYPAGESUBSHOW(state, payload) {
      state.sidebar.MypageSubShow = payload.MypageSubShow;
    },
    SET_LOGOUT(state, payload) {
      state.login.userSeq = payload.userSeq;
      state.login.isLogin = payload.isLogin;
      state.login.userName = payload.userName;
      state.login.userProfileImageUrl = payload.userProfileImageUrl;
    },

    SET_RADIOBUTTON(state, payload) {
      state.apt.radioShow = payload.radioShow;
    },
    SET_APT_LIST(state, payload) {
      state.apt.list = payload.data;
    },
    SET_APT_DEATIL(state, payload) {
      state.apt.no = payload.no;
      state.apt.aptName = payload.aptName;
      state.apt.area = payload.area;
      state.apt.buildYear = payload.buildYear;
      state.apt.dealAmount = payload.dealAmount;
      state.apt.dealYMD = payload.dealYMD;
      state.apt.dealDay = payload.dealDay;
      state.apt.dealMonth = payload.dealMonth;
      state.apt.dealYear = payload.dealYear;
      state.apt.dong = payload.dong;
      state.apt.floor = payload.floor;
      state.apt.lat = payload.lat;
      state.apt.lng = payload.lng;
    },

    SET_FREE_LIST(state, list) {
      state.free.list = list;
    },

    SET_FREE_TOTAL_LIST_ITEM_COUNT(state, count) {
      state.free.totalListItemCount = count;
    },

    SET_FREE_MOVE_PAGE(state, pageIndex) {
      state.free.offset = (pageIndex - 1) * state.free.listRowCount;
      state.free.currentPageIndex = pageIndex;
    },

    SET_FREE_DETAIL(state, payload) {
      state.free.freeId = payload.freeId;
      state.free.title = payload.title;
      state.free.content = payload.content;
      state.free.userName = payload.userName;
      state.free.regDt = payload.regDt;
      state.free.fileList = payload.fileList;
      state.free.sameUser = payload.sameUser;
      state.free.readCount = payload.readCount;
      state.free.check = payload.check;
    },

    //review
    SET_REVIEW_LIST(state, list) {
      state.review.list = list;
    },

    SET_REVIEW_TOTAL_LIST_ITEM_COUNT(state, count) {
      state.review.totalListItemCount = count;
    },

    SET_REVIEW_MOVE_PAGE(state, pageIndex) {
      state.review.offset = (pageIndex - 1) * state.review.listRowCount;
      state.review.currentPageIndex = pageIndex;
    },

    SET_REVIEW_DETAIL(state, payload) {
      state.review.reviewId = payload.reviewId;
      state.review.title = payload.title;
      state.review.content = payload.content;
      state.review.userName = payload.userName;
      state.review.regDt = payload.regDt;
      state.review.fileList = payload.fileList;
      state.review.sameUser = payload.sameUser;
      state.review.readCount = payload.readCount;
    },

    //NOTICE

    SET_NOTICE_LIST(state, list) {
      state.notice.list = list;
    },

    SET_NOTICE_TOTAL_LIST_ITEM_COUNT(state, count) {
      state.notice.totalListItemCount = count;
    },

    SET_NOTICE_MOVE_PAGE(state, pageIndex) {
      state.notice.offset = (pageIndex - 1) * state.notice.listRowCount;
      state.notice.currentPageIndex = pageIndex;
    },

    SET_NOTICE_DETAIL(state, payload) {
      state.notice.noticeId = payload.noticeId;
      state.notice.title = payload.title;
      state.notice.content = payload.content;
      state.notice.userName = payload.userName;
      state.notice.regDt = payload.regDt;
      state.notice.fileList = payload.fileList;
      state.notice.sameUser = payload.sameUser;
      state.notice.readCount = payload.readCount;
    },

    SET_INTEREST_LIST(state, list) {
      state.interest.list = list;
    },
  },
  // for async method
  actions: {
    interestLists(context) {
      var userSeq = this.state.login.userSeq;
      http
        .get("/interestlists/" + userSeq)
        .then(({ data }) => {
          console.log(data);
          context.commit("SET_INTEREST_LIST", data);
        })
        .catch((error) => {
          console.log(error);
        });
    },
    freeList(context) {
      http
        .get("/frees", {
          params: {
            limit: this.state.free.limit,
            offset: this.state.free.offset,
            searchWord: this.state.free.searchWord,
          },
        })
        .then(({ data }) => {
          console.log("FreeMainVue: data : ");
          console.log(data);
          if (data.result == "login") {
            router.push("/login");
          } else {
            context.commit("SET_FREE_LIST", data.list);
            context.commit("SET_FREE_TOTAL_LIST_ITEM_COUNT", data.count);
          }
        });
    },

    reviewList(context) {
      http
        .get("/reviews", {
          params: {
            limit: this.state.review.limit,
            offset: this.state.review.offset,
            searchWord: this.state.review.searchWord,
          },
        })
        .then(({ data }) => {
          console.log("ReviewMainVue: data : ");
          console.log(data);
          if (data.result == "login") {
            router.push("/login");
          } else {
            context.commit("SET_REVIEW_LIST", data.list);
            context.commit("SET_REVIEW_TOTAL_LIST_ITEM_COUNT", data.count);
          }
        });
    },

    noticeList(context) {
      http
        .get("/notices", {
          params: {
            limit: this.state.notice.limit,
            offset: this.state.notice.offset,
            searchWord: this.state.notice.searchWord,
          },
        })
        .then(({ data }) => {
          console.log("NoticeMainVue: data : ");
          console.log(data);
          if (data.result == "login") {
            router.push("/login");
          } else {
            context.commit("SET_NOTICE_LIST", data.list);
            context.commit("SET_NOTICE_TOTAL_LIST_ITEM_COUNT", data.count);
          }
        });
    },
  },
  getters: {
    isLogin: function (state) {
      return state.login.isLogin;
    },

    isMypageSubShow: function (state) {
      return state.sidebar.MypageSubShow;
    },

    //free
    getFreeList: function (state) {
      return state.free.list;
    },

    getFreePageCount: function (state) {
      return Math.ceil(state.free.totalListItemCount / state.free.listRowCount);
    },

    getStartFreePageIndex: function (state) {
      if (state.free.currentPageIndex % state.free.pageLinkCount == 0) {
        //10, 20,... 맨마지막
        return (
          (state.free.currentPageIndex / state.free.pageLinkCount - 1) *
            state.free.pageLinkCount +
          1
        );
      } else {
        return (
          Math.floor(state.free.currentPageIndex / state.free.pageLinkCount) *
            state.free.pageLinkCount +
          1
        );
      }
    },
    getFreeEndPageIndex: function (state, getters) {
      let ret = 0;
      if (state.free.currentPageIndex % state.free.pageLinkCount == 0) {
        ret =
          (state.free.currentPageIndex / state.free.pageLinkCount - 1) *
            state.free.pageLinkCount +
          state.free.pageLinkCount;
      } else {
        ret =
          Math.floor(state.free.currentPageIndex / state.free.pageLinkCount) *
            state.free.pageLinkCount +
          state.free.pageLinkCount;
      }
      // 위 오류나는 코드를 아래와 같이 비교해서 처리
      return ret > getters.getFreePageCount ? getters.getFreePageCount : ret;
    },
    getPrev: function (state) {
      if (state.free.currentPageIndex <= state.free.pageLinkCount) {
        return false;
      } else {
        return true;
      }
    },
    getNext: function (state, getters) {
      if (
        Math.floor(getters.getPageCount / state.free.pageLinkCount) *
          state.free.pageLinkCount <
        state.free.currentPageIndex
      ) {
        return false;
      } else {
        return true;
      }
    },

    //review
    getReviewList: function (state) {
      return state.review.list;
    },

    getReviewPageCount: function (state) {
      return Math.ceil(
        state.review.totalListItemCount / state.review.listRowCount
      );
    },

    getStartReviewPageIndex: function (state) {
      if (state.review.currentPageIndex % state.review.pageLinkCount == 0) {
        //10, 20,... 맨마지막
        return (
          (state.review.currentPageIndex / state.review.pageLinkCount - 1) *
            state.review.pageLinkCount +
          1
        );
      } else {
        return (
          Math.floor(
            state.review.currentPageIndex / state.review.pageLinkCount
          ) *
            state.review.pageLinkCount +
          1
        );
      }
    },
    getReviewEndPageIndex: function (state, getters) {
      let ret = 0;
      if (state.review.currentPageIndex % state.review.pageLinkCount == 0) {
        ret =
          (state.review.currentPageIndex / state.review.pageLinkCount - 1) *
            state.review.pageLinkCount +
          state.review.pageLinkCount;
      } else {
        ret =
          Math.floor(
            state.review.currentPageIndex / state.review.pageLinkCount
          ) *
            state.review.pageLinkCount +
          state.review.pageLinkCount;
      }
      // 위 오류나는 코드를 아래와 같이 비교해서 처리
      return ret > getters.getReviewPageCount
        ? getters.getReviewPageCount
        : ret;
    },
    getReviewPrev: function (state) {
      if (state.review.currentPageIndex <= state.review.pageLinkCount) {
        return false;
      } else {
        return true;
      }
    },
    getReviewNext: function (state, getters) {
      if (
        Math.floor(getters.getPageCount / state.review.pageLinkCount) *
          state.review.pageLinkCount <
        state.review.currentPageIndex
      ) {
        return false;
      } else {
        return true;
      }
    },

    //NOTICE
    getNoticeList: function (state) {
      return state.notice.list;
    },

    getNoticePageCount: function (state) {
      return Math.ceil(
        state.notice.totalListItemCount / state.notice.listRowCount
      );
    },

    getStartNoticePageIndex: function (state) {
      if (state.notice.currentPageIndex % state.notice.pageLinkCount == 0) {
        //10, 20,... 맨마지막
        return (
          (state.notice.currentPageIndex / state.notice.pageLinkCount - 1) *
            state.notice.pageLinkCount +
          1
        );
      } else {
        return (
          Math.floor(
            state.notice.currentPageIndex / state.notice.pageLinkCount
          ) *
            state.notice.pageLinkCount +
          1
        );
      }
    },
    getNoticeEndPageIndex: function (state, getters) {
      let ret = 0;
      if (state.notice.currentPageIndex % state.notice.pageLinkCount == 0) {
        ret =
          (state.notice.currentPageIndex / state.notice.pageLinkCount - 1) *
            state.notice.pageLinkCount +
          state.notice.pageLinkCount;
      } else {
        ret =
          Math.floor(
            state.notice.currentPageIndex / state.notice.pageLinkCount
          ) *
            state.notice.pageLinkCount +
          state.notice.pageLinkCount;
      }
      // 위 오류나는 코드를 아래와 같이 비교해서 처리
      return ret > getters.getNoticePageCount
        ? getters.getNoticePageCount
        : ret;
    },
    getNoticePrev: function (state) {
      if (state.notice.currentPageIndex <= state.notice.pageLinkCount) {
        return false;
      } else {
        return true;
      }
    },
    getNoticeNext: function (state, getters) {
      if (
        Math.floor(getters.getPageCount / state.notice.pageLinkCount) *
          state.notice.pageLinkCount <
        state.notice.currentPageIndex
      ) {
        return false;
      } else {
        return true;
      }
    },
  },
});
