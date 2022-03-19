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
                <h3 class="auth-title">REVIEW BOARD</h3>
              </div>
              <div align="right">
                <button
                  @click="reviewList"
                  class="btn btn-success"
                  type="button"
                  style="float: right"
                >
                  Search
                </button>
                <input
                  style="width: 300px"
                  v-model="$store.state.review.searchWord"
                  @keydown.enter="reviewList"
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
                      v-for="(review, index) in this.$store.state.review.list"
                      @click="reviewDetail(review.reviewId)"
                      v-bind:key="index"
                    >
                      <td>{{ review.reviewId }}</td>
                      <td>{{ review.title }}</td>
                      <td>{{ review.userName }}</td>
                      <td>
                        {{
                          makeDateStr(
                            review.regDt.date.year,
                            review.regDt.date.month,
                            review.regDt.date.day,
                            "."
                          )
                        }}
                      </td>
                      <td>{{ review.readCount }}</td>
                    </tr>
                  </tbody>
                </table>
                <br />

                <review-pagination
                  v-on:call-parent="movePage"
                ></review-pagination>

                <button
                  class="btn btn-sm btn-primary"
                  @click="showReviewInsertModal"
                >
                  글쓰기
                </button>

                <board-review-insert-modal
                  v-on:call-parent-review-insert="closeAfterReviewInsert"
                ></board-review-insert-modal>
                <!-- props 제거 -->
                <board-review-detail-modal
                  v-on:call-parent-change-to-review-update="changeToUpdate"
                  v-on:call-parent-change-to-delete="changeToDelete"
                ></board-review-detail-modal>
                <board-review-update-modal
                  v-on:call-parent-review-update="closeAfterUpdate"
                ></board-review-update-modal>

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
import BoardReviewInsertModal from "@/components/Modal/BoardReviewInsertModal.vue";
import BoardReviewDetailModal from "@/components/Modal/BoardReviewDetailModal.vue";
import BoardReviewUpdateModal from "@/components/Modal/BoardReviewUpdateModal.vue";

import { Modal } from "bootstrap";

import http from "@/common/axios.js";
import util from "@/common/util.js";

import ReviewPagination from "@/components/ReviewPagination.vue";

import Vue from "vue";
import VueAlertify from "vue-alertify";
Vue.use(VueAlertify);

export default {
  name: "BoardReview",
  components: {
    BoardReviewInsertModal,
    BoardReviewDetailModal,
    BoardReviewUpdateModal,
    ReviewPagination,
  },
  data() {
    return {
      boardReviewInsertModal: null,
      boardReviewDetailModal: null,
      boardReviewUpdateModal: null,
    };
  },
  computed: {
    listGetters() {
      return this.$store.getters.getReviewList;
    },
  },
  methods: {
    reviewList() {
      this.$store.dispatch("reviewList");
    },

    movePage(pageIndex) {
      console.log("BoardReview : movePage : pageIndex : " + pageIndex);

      this.$store.commit("SET_REVIEW_MOVE_PAGE", pageIndex);

      this.reviewList();
    },

    makeDateStr: util.makeDateStr,

    showReviewInsertModal() {
      this.boardReviewInsertModal.show();
    },

    closeAfterReviewInsert() {
      this.boardReviewInsertModal.hide();
      this.reviewList();
    },

    reviewDetail(reviewId) {
      http
        .get("/reviews/" + reviewId)
        .then(({ data }) => {
          console.log("DetailModalVue: data : ");
          console.log(data);

          if (data.result == "login") {
            this.$router.push("/login");
          } else {
            this.$store.commit("SET_REVIEW_DETAIL", {
              reviewId: data.dto.reviewId,
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

            this.boardReviewDetailModal.show();
          }
        })
        .catch((error) => {
          console.log("DetailModalVue: error ");
          console.log(error);
        });
    },

    changeToUpdate() {
      this.boardReviewDetailModal.hide();
      this.boardReviewUpdateModal.show();
    },

    closeAfterUpdate() {
      this.boardReviewUpdateModal.hide();
      this.reviewList();
    },

    changeToDelete() {
      this.boardReviewDetailModal.hide();

      var $this = this;
      this.$alertify.confirm(
        "이 글을 삭제하시겠습니까?",
        function () {
          $this.reviewDelete();
        },
        function () {
          console.log("cancel");
        }
      );
    },
    reviewDelete() {
      console.log(
        ">>>>>>>>>>>>>>>>>>>>>> " + this.$store.state.review.reviewId
      );
      http
        .delete("/reviews/" + this.$store.state.review.reviewId)
        .then(({ data }) => {
          console.log("BoardMainVue: data : ");
          console.log(data);
          if (data.result == "login") {
            this.doLogout();
          } else {
            this.reviewList();
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
    this.reviewList();
  },
  mounted() {
    console.log(1);
    this.boardReviewInsertModal = new Modal(
      document.getElementById("boardReviewInsertModal")
    );
    console.log(2);
    this.boardReviewDetailModal = new Modal(
      document.getElementById("boardReviewDetailModal")
    );
    console.log(3);
    this.boardReviewUpdateModal = new Modal(
      document.getElementById("boardReviewUpdateModal")
    );
  },
};
</script>

<style></style>
