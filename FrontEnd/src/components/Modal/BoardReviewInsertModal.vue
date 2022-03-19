<template>
  <div class="modal" tabindex="-1" id="boardReviewInsertModal">
    <div class="modal-dialog">
      <div class="modal-content">
        <div class="modal-header">
          <h5 class="modal-title">글 쓰기</h5>
          <button
            type="button"
            class="btn-close"
            data-bs-dismiss="modal"
            aria-label="Close"
          ></button>
        </div>
        <div class="modal-body">
          <div class="mb-3">
            <input
              v-model="title"
              type="text"
              class="form-control"
              placeholder="제목"
            />
          </div>
          <div class="mb-3">
            <div id="divEditorReviewInsert"></div>
          </div>
          <div class="form-check mb-3">
            <input
              v-model="attachFile"
              class="form-check-input"
              type="checkbox"
              value=""
              id="chkFileUploadReviewInsert"
            />
            <label class="form-check-label" for="chkFileUploadReviewInsert"
              >파일 추가</label
            >
          </div>
          <div
            class="mb-3"
            v-show="attachFile"
            id="imgFileUploadReviewInsertWrapper"
          >
            <input
              @change="changeFile"
              type="file"
              id="inputFileUploadReviewInsert"
              multiple
            />
            <div
              id="imgFileUploadReviewInsertThumbnail"
              class="thumbnail-wrapper"
            >
              <!-- vue way img 를 만들어서 append 하지 않고, v-for 로 처리 -->
              <img
                v-for="(file, index) in fileList"
                v-bind:src="file"
                v-bind:key="index"
              />
            </div>
          </div>
        </div>
        <div class="modal-footer">
          <button
            @click="reivewInsert"
            class="btn btn-sm btn-primary btn-outline"
            data-dismiss="modal"
            type="button"
          >
            등록
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import Vue from "vue";
import CKEditor from "@ckeditor/ckeditor5-vue2";
import ClassicEditor from "@ckeditor/ckeditor5-build-classic";
import VueAlertify from "vue-alertify";

Vue.use(CKEditor).use(VueAlertify);

import http from "@/common/axios.js";
export default {
  name: "BoardReviewInsertModal",

  data() {
    return {
      title: "",
      CKEditor: "",
      attachFile: false,
      fileList: [],
    };
  },
  methods: {
    initUI() {
      this.title = "";
      this.CKEditor.setData("");
      this.attachFile = false;
      this.fileList = [];
      document.querySelector("#inputFileUploadReviewInsert").value = "";
    },
    changeFile(fileEvent) {
      if (fileEvent.target.files && fileEvent.target.files.length > 0) {
        for (var i = 0; i < fileEvent.target.files.length; i++) {
          const file = fileEvent.target.files[i];
          this.fileList.push(URL.createObjectURL(file));
        }
      }
    },

    reivewInsert() {
      var formData = new FormData();
      formData.append("title", this.title);
      formData.append("content", this.CKEditor.getData());

      var attachFiles = document.querySelector("#inputFileUploadReviewInsert");
      console.log("ReviewInsertModalVue: data 1 : ");
      console.log(attachFiles);

      var cnt = attachFiles.files.length;
      for (var i = 0; i < cnt; i++) {
        formData.append("file", attachFiles.files[i]);
      }

      http
        .post("/reviews", formData, {
          headers: { "Content-Type": "multipart/form-data" },
        })
        .then(({ data }) => {
          console.log("ReviewInsertModalVue: data : ");
          console.log(data);
          if (data.result == "login") {
            this.$router.push("/login");
          } else {
            this.$alertify.success("글이 등록되었습니다.");

            this.closeModal();
          }
        })
        .catch((error) => {
          console.log("ReviewInsertModalVue: error ");
          console.log(error);
        });
    },
    closeModal() {
      this.$emit("call-parent-review-insert"); // no parameter
    },
  },
  mounted() {
    ClassicEditor.create(document.querySelector("#divEditorReviewInsert"))
      .then((editor) => {
        this.CKEditor = editor;
      })
      .catch((err) => {
        console.error(err.stack);
      });

    // bootstrap modal show event hook
    // ReviewInsertModal 이 보일 때 초기화
    let $this = this;
    this.$el.addEventListener("show.bs.modal", function () {
      $this.initUI();
    });
  },
};
</script>

<style></style>
