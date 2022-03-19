<template>
  <div id="aptDetail" class="modal" tabindex="-1">
    <div class="modal-dialog modal-dialog-scrollable">
      <div class="modal-content">
        <div class="modal-header">
          <h5 class="modal-title">{{ $store.state.apt.aptName }}</h5>
          <button
            type="button"
            class="btn-close"
            data-bs-dismiss="modal"
            aria-label="Close"
          ></button>
        </div>
        <div class="modal-body">
          <p>매물 사진</p>
          <div>
            <img
              src="../../assets/img/aptImg.jpg"
              class="img-fluid"
              alt="..."
            />
          </div>

          <br />
          <p>매물 정보</p>
          <table class="table">
            <tbody>
              <tr>
                <th scope="row">위치</th>
                <td>{{ $store.state.apt.dong }}</td>
              </tr>
              <tr>
                <th scope="row">층</th>
                <td>{{ $store.state.apt.floor }}</td>
              </tr>
              <tr>
                <th scope="row">면적</th>
                <td>{{ $store.state.apt.area }}m²</td>
              </tr>
              <tr>
                <th scope="row">건축년도</th>
                <td>{{ $store.state.apt.buildYear }}</td>
              </tr>
              <tr>
                <th scope="row">거래일</th>
                <td>{{ $store.state.apt.dealYMD }}</td>
              </tr>
              <tr>
                <th scope="row">거래액</th>
                <td>{{ $store.state.apt.dealAmount }}</td>
              </tr>
            </tbody>
          </table>
          <button class="btn btn-info rounded-pill" @click="interest">
            관심매물 등록
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "AptDetail",
  methods: {
    interest() {
      this.$emit("call-parent-interest");
    },
  },
};
</script>

<style>
#aptDetail {
  left: 200px;
}
</style>
