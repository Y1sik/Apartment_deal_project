<template>
  <div class="modal" tabindex="-1" id="boardNoticeDetailModal">
    <div class="modal-dialog modal-dialog-centered modal-lg">
      <div class="modal-content">
        <div class="modal-header">
          <h5 class="modal-title">글 상세</h5>
          <button
            type="button"
            class="btn-close"
            data-bs-dismiss="modal"
            aria-label="Close"
          ></button>
        </div>
        <div class="modal-body">
          <table class="table table-hover">
            <tbody>
              <tr>
                <td style="width: 20%">글번호</td>
                <td>{{ $store.state.notice.noticeId }}</td>
              </tr>
              <tr>
                <td>제목</td>
                <td>{{ $store.state.notice.title }}</td>
              </tr>
              <tr>
                <td colspan="2">
                  <div
                    v-html="$store.state.notice.content"
                    style="
                      border: 1px solid;
                      padding: 10px;
                      height: 20em;
                      overflow-y: scroll;
                    "
                  ></div>
                </td>
              </tr>
              <!-- <tr>
                <td>좋아요</td>
                <td>
                  <img
                    @click="checked()"
                    :src="
                      $store.state.notice.check
                        ? require(`@/assets/img/redheart.png`)
                        : require(`@/assets/img/heart.png`)
                    "
                    width="30"
                  />
                  <div class="form-check form-switch">
                    <input
                      class="form-check-input"
                      type="checkbox"
                      id="flexSwitchCheckChecked"
                      v-model="$store.state.notice.check"
                      @change="checked()"
                    />
                  </div>
                </td>
              </tr> -->

              <!-- <tr>
                <td>작성자</td>
                <td>{{ $store.state.notice.userName }}</td>
              </tr> -->
              <!-- 아래 코드는 오류 발생 초기 생성 시점에 regDt = {} -->
              <!-- <tr><td>작성일시</td><td>{{ makeDateStr(regDt.date.year, regDt.date.month, regDt.date.day, '.') }}</td></tr> -->
              <!-- <tr>
                <td>작성일시</td>
                <td>{{ $store.state.notice.regDt }}</td>
              </tr>
              <tr>
                <td>조회수</td>
                <td>{{ $store.state.notice.readCount }}</td>
              </tr> -->
              <!-- New for FileUpload -->
              <tr>
                <td colspan="2">첨부파일</td>
              </tr>
              <tr>
                <td colspan="2">
                  <div
                    v-for="(file, index) in $store.state.notice.fileList"
                    :key="index"
                  >
                    <span class="fileName">{{ file.fileName }}</span>
                    &nbsp;&nbsp;
                    <a
                      type="button"
                      class="btn btn-outline btn-default btn-xs"
                      v-bind:href="file.fileUrl"
                      download
                      >내려받기</a
                    >
                  </div>
                </td>
              </tr>
              <!-- / New for FileUpload -->
            </tbody>
          </table>
        </div>
        <div class="modal-footer">
          <button
            v-show="$store.state.notice.sameUser"
            @click="changeToUpdate"
            class="btn btn-sm btn-primary btn-outline"
            data-dismiss="modal"
            type="button"
          >
            글 수정하기
          </button>
          <button
            v-show="$store.state.notice.sameUser"
            @click="changeToDelete"
            class="btn btn-sm btn-warning btn-outline"
            data-dismiss="modal"
            type="button"
          >
            글 삭제하기
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import http from "@/common/axios.js";
export default {
  name: "BoardNoticeDetailModal",
  methods: {
    changeToUpdate() {
      this.$emit("call-parent-change-to-notice-update");
    },
    changeToDelete() {
      this.$emit("call-parent-change-to-notice-delete");
    },
    checked() {
      http
        .put("/goods/", {
          userSeq: this.$store.state.login.userSeq,
          noticeId: this.$store.state.notice.noticeId,
        })
        .then(({ data }) => {
          console.log(data);
        })
        .catch((error) => {
          console.log(error);
        });
    },
  },
};
</script>

<style></style>
