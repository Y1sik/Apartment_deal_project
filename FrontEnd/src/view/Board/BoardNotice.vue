<template>
  <div id="main">
    <br />
    <br />
    <br />
    <br />
    <section class="section">
      <div class="row justify-content-center" id="table-inverse">
        <div class="col-10">
          <div class="card">
            <div class="card-body">
              <div style="float: left">
                <h3 class="auth-title">NOTICE BOARD</h3>
              </div>
              <div align="right">
                <button
                  @click="noticeList"
                  class="btn btn-success"
                  type="button"
                  style="float: right"
                >
                  Search
                </button>
                <input
                  style="width: 300px"
                  v-model="$store.state.notice.searchWord"
                  @keydown.enter="noticeList"
                  type="text"
                  class="form-control"
                />
                <br />
              </div>
              <div class="table-responsive">
                <table class="table table-hover mb-0">
                  <thead>
                    <tr style="font-size: 20px">
                      <th><strong>번호</strong></th>
                      <th style="width: 40%">제목</th>
                      <th>작성자</th>
                      <th>작성일시</th>
                      <th>조회수</th>
                    </tr>
                  </thead>
                  <tbody>
                    <tr
                      style="cursor: pointer"
                      v-for="(notice, index) in this.$store.state.notice.list"
                      @click="noticeDetail(notice.noticeId)"
                      v-bind:key="index"
                    >
                      <td>{{ notice.noticeId }}</td>
                      <td>{{ notice.title }}</td>
                      <td>{{ notice.userName }}</td>
                      <td>
                        {{
                          makeDateStr(
                            notice.regDt.date.year,
                            notice.regDt.date.month,
                            notice.regDt.date.day,
                            "."
                          )
                        }}
                      </td>
                      <td>{{ notice.readCount }}</td>
                    </tr>
                  </tbody>
                </table>
                <br />

                <notice-pagination
                  v-on:call-parent="movePage"
                ></notice-pagination>
                <button
                  class="btn btn-sm btn-primary"
                  @click="showNoticeInsertModal"
                  v-show="$store.state.login.userAdmin"
                >
                  글쓰기
                </button>
                <board-notice-insert-modal
                  v-on:call-parent-notice-insert="closeAfterNoticeInsert"
                ></board-notice-insert-modal>
                <!-- props 제거 -->
                <board-notice-detail-modal
                  v-on:call-parent-change-to-notice-update="changeToUpdate"
                  v-on:call-parent-change-to-notice-delete="changeToDelete"
                ></board-notice-detail-modal>
                <board-notice-update-modal
                  v-on:call-parent-notice-update="closeAfterUpdate"
                ></board-notice-update-modal>

                <!-- <pagination v-on:call-parent="movePage"></pagination> -->
              </div>
            </div>
          </div>
        </div>
      </div>
    </section>
  </div>
</template>

<script>
import BoardNoticeInsertModal from "@/components/Modal/BoardNoticeInsertModal.vue";
import BoardNoticeDetailModal from "@/components/Modal/BoardNoticeDetailModal.vue";
import BoardNoticeUpdateModal from "@/components/Modal/BoardNoticeUpdateModal.vue";

import { Modal } from "bootstrap";

import http from "@/common/axios.js";
import util from "@/common/util.js";

import NoticePagination from "@/components/NoticePagination.vue";

import Vue from "vue";
import VueAlertify from "vue-alertify";
Vue.use(VueAlertify);

export default {
  name: "BoardNotice",
  components: {
    BoardNoticeInsertModal,
    BoardNoticeDetailModal,
    BoardNoticeUpdateModal,
    NoticePagination,
  },
  data() {
    return {
      boardNoticeInsertModal: null,
      boardNoticeDetailModal: null,
      boardNoticeUpdateModal: null,
    };
  },
  computed: {
    listGetters() {
      return this.$store.getters.getNoticeList;
    },
  },
  methods: {
    noticeList() {
      this.$store.dispatch("noticeList");
    },

    movePage(pageIndex) {
      console.log("BoardNotice : movePage : pageIndex : " + pageIndex);

      this.$store.commit("SET_NOTICE_MOVE_PAGE", pageIndex);

      this.noticeList();
    },

    makeDateStr: util.makeDateStr,

    showNoticeInsertModal() {
      this.boardNoticeInsertModal.show();
    },

    closeAfterNoticeInsert() {
      this.boardNoticeInsertModal.hide();
      this.noticeList();
    },

    noticeDetail(noticeId) {
      http
        .get("/notices/" + noticeId)
        .then(({ data }) => {
          console.log("DetailModalVue: data : ");
          console.log(data);

          if (data.result == "login") {
            this.$router.push("/login");
          } else {
            this.$store.commit("SET_NOTICE_DETAIL", {
              noticeId: data.dto.noticeId,
              title: data.dto.title,
              content: data.dto.content,
              userName: data.dto.userName,
              regDt: this.makeDateStr(
                data.dto.regDt.date.year,
                data.dto.regDt.date.month,
                data.dto.regDt.date.day,
                "."
              ),
              fileList: data.dto.fileList,
              sameUser: data.dto.sameUser,
              readCount: data.dto.readCount,
            });

            this.boardNoticeDetailModal.show();
          }
        })
        .catch((error) => {
          console.log("DetailModalVue: error ");
          console.log(error);
        });
    },

    changeToUpdate() {
      this.boardNoticeDetailModal.hide();
      this.boardNoticeUpdateModal.show();
    },

    closeAfterUpdate() {
      this.boardNoticeUpdateModal.hide();
      this.noticeList();
    },

    changeToDelete() {
      this.boardNoticeDetailModal.hide();

      var $this = this;
      this.$alertify.confirm(
        "이 글을 삭제하시겠습니까?",
        function () {
          $this.noticeDelete();
        },
        function () {
          console.log("cancel");
        }
      );
    },
    noticeDelete() {
      console.log(
        ">>>>>>>>>>>>>>>>>>>>>> " + this.$store.state.notice.noticeId
      );
      http
        .delete("/notices/" + this.$store.state.notice.noticeId)
        .then(({ data }) => {
          console.log("BoardMainVue: data : ");
          console.log(data);
          if (data.result == "login") {
            this.doLogout();
          } else {
            this.noticeList();
          }
        })
        .catch((error) => {
          console.log(error);
        });
    },
    // logout 처리 별도 method
    doLogout() {
      this.$store.commit("SET_LOGIN", {
        isLogin: false,
        userName: "",
        userProfileImageUrl: "",
      });
      this.$router.push("/login");
    },
  },
  created() {
    this.noticeList();
  },
  mounted() {
    console.log(1);
    this.boardNoticeInsertModal = new Modal(
      document.getElementById("boardNoticeInsertModal")
    );
    console.log(2);
    this.boardNoticeDetailModal = new Modal(
      document.getElementById("boardNoticeDetailModal")
    );
    console.log(3);
    this.boardNoticeUpdateModal = new Modal(
      document.getElementById("boardNoticeUpdateModal")
    );
  },
};
</script>

<style></style>
