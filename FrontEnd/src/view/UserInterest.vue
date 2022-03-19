<template>
  <div id="main">
    <section class="section">
      <div class="row" id="table-hover-row">
        <div class="col-12">
          <div class="card" style="height: 800px">
            <div class="card-header">
              <h4 class="card-title">나의 관심매물</h4>
            </div>
            <div class="card-content">
              <!-- table hover -->
              <div class="table-responsive">
                <br />
                <table class="table table-hover mb-0">
                  <thead>
                    <tr>
                      <th>아파트명</th>
                      <th>지역</th>
                      <th>층</th>
                      <th>면적</th>
                      <th>가격</th>
                      <th>삭제</th>
                    </tr>
                  </thead>
                  <tbody>
                    <tr
                      v-for="(interest, index) in this.$store.state.interest
                        .list"
                      v-bind:key="index"
                    >
                      <td>{{ interest.aptName }}</td>
                      <td>{{ interest.dong }}</td>
                      <td>{{ interest.floor }}</td>
                      <td>{{ interest.area }}</td>
                      <td>{{ interest.dealAmount }}</td>
                      <td>
                        <button
                          class="btn btn-sm btn-danger rounded-pill"
                          @click="interestDelete(interest.aptName)"
                        >
                          삭제
                        </button>
                      </td>
                    </tr>
                  </tbody>
                </table>
              </div>
            </div>
          </div>
        </div>
      </div>
    </section>
  </div>
</template>

<script>
import http from "@/common/axios.js";
export default {
  methods: {
    interestLists() {
      this.$store.dispatch("interestLists");
    },
    interestDelete(aptName) {
      console.log(aptName);
      http
        .delete("/interestdelete/" + aptName)
        .then(({ data }) => {
          console.log(data);
          this.interestLists();
        })
        .catch((error) => {
          console.log(error);
        });
    },
  },
  created() {
    this.interestLists();
  },
};
</script>

<style></style>
