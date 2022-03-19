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
                <h3 class="auth-title">FREE BOARD</h3>
              </div>
              <div align="right">
                <button
                  @click="freeList"
                  class="btn btn-success"
                  type="button"
                  style="float: right"
                >
                  Search
                </button>
                <input
                  style="width: 300px"
                  v-model="$store.state.free.searchWord"
                  @keydown.enter="freeList"
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
                      v-for="(free, index) in this.$store.state.free.list"
                      @click="freeDetail(free.freeId)"
                      v-bind:key="index"
                    >
                      <td>{{ free.freeId }}</td>
                      <td>{{ free.title }}</td>
                      <td>{{ free.userName }}</td>
                      <td>
                        {{
                          makeDateStr(
                            free.regDt.date.year,
                            free.regDt.date.month,
                            free.regDt.date.day,
                            "."
                          )
                        }}
                      </td>
                      <td>{{ free.readCount }}</td>
                    </tr>
                  </tbody>
                </table>
                <br />

                <pagination v-on:call-parent="movePage"></pagination>

                <button class="btn btn-sm btn-primary" @click="showInsertModal">
                  글쓰기
                </button>

                <board-insert-modal
                  v-on:call-parent-insert="closeAfterInsert"
                ></board-insert-modal>
                <!-- props 제거 -->
                <board-free-detail-modal
                  v-on:call-parent-change-to-update="changeToUpdate"
                  v-on:call-parent-change-to-delete="changeToDelete"
                ></board-free-detail-modal>
                <board-update-modal
                  v-on:call-parent-update="closeAfterUpdate"
                ></board-update-modal>

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
import BoardInsertModal from "@/components/Modal/BoardInsertModal.vue";
import BoardFreeDetailModal from "@/components/Modal/BoardFreeDetailModal.vue";
import BoardUpdateModal from "@/components/Modal/BoardUpdateModal.vue";

import { Modal } from "bootstrap";

import http from "@/common/axios.js";
import util from "@/common/util.js";

import Pagination from "@/components/Pagination.vue";

import Vue from "vue";
import VueAlertify from "vue-alertify";
Vue.use(VueAlertify);

export default {
  name: "BoardFree",
  components: {
    BoardInsertModal,
    BoardFreeDetailModal,
    BoardUpdateModal,
    Pagination,
  },
  data() {
    return {
      boardInsertModal: null,
      boardFreeDetailModal: null,
      boardUpdateModal: null,
    };
  },
  computed: {
    listGetters() {
      return this.$store.getters.getFreeList;
    },
  },
  methods: {
    freeList() {
      this.$store.dispatch("freeList");
    },

    movePage(pageIndex) {
      console.log("BoardFree : movePage : pageIndex : " + pageIndex);

      this.$store.commit("SET_FREE_MOVE_PAGE", pageIndex);

      this.freeList();
    },

    makeDateStr: util.makeDateStr,

    showInsertModal() {
      this.boardInsertModal.show();
    },

    closeAfterInsert() {
      this.boardInsertModal.hide();
      this.freeList();
    },

    freeDetail(freeId) {
      http
        .get("/frees/" + freeId)
        .then(({ data }) => {
          console.log("DetailModalVue: data : ");
          console.log(data);

          if (data.result == "login") {
            this.$router.push("/login");
          } else {
            this.$store.commit("SET_FREE_DETAIL", {
              freeId: data.dto.freeId,
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
              check: data.check,
            });

            this.boardFreeDetailModal.show();
          }
        })
        .catch((error) => {
          console.log("DetailModalVue: error ");
          console.log(error);
        });
    },

    changeToUpdate() {
      this.boardFreeDetailModal.hide();
      this.boardUpdateModal.show();
    },

    closeAfterUpdate() {
      this.boardUpdateModal.hide();
      this.freeList();
    },

    changeToDelete() {
      this.boardFreeDetailModal.hide();

      var $this = this;
      this.$alertify.confirm(
        "이 글을 삭제하시겠습니까?",
        function () {
          $this.freeDelete();
        },
        function () {
          console.log("cancel");
        }
      );
    },
    freeDelete() {
      console.log(">>>>>>>>>>>>>>>>>>>>>> " + this.$store.state.free.freeId);
      http
        .delete("/frees/" + this.$store.state.free.freeId)
        .then(({ data }) => {
          console.log("BoardMainVue: data : ");
          console.log(data);
          if (data.result == "login") {
            this.doLogout();
          } else {
            this.freeList();
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
    this.freeList();
  },
  mounted() {
    this.boardInsertModal = new Modal(
      document.getElementById("boardInsertModal")
    );
    this.boardFreeDetailModal = new Modal(
      document.getElementById("boardFreeDetailModal")
    );
    this.boardUpdateModal = new Modal(
      document.getElementById("boardUpdateModal")
    );
  },
};
</script>

<style></style>
