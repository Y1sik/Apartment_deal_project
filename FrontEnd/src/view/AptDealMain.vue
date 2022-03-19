<template>
  <div id="main" style="padding: 0">
    <div id="wrapper">
      <div id="map" style="height: 100vh"></div>

      <div id="searchBox">
        <!-- 동으로 검색 -->
        <div class="row mb-3">
          <div class="col-sm-10">
            <input
              type="text"
              class="form-control"
              id="inputEmail3"
              placeholder="지역명(동)"
              v-model="searchDong"
            />
          </div>
          <button
            type="submit"
            class="btn btn-primary col-sm-2"
            @click="clickSearchBtn"
          >
            <svg class="bi" width="1em" height="1em" fill="currentColor">
              <use
                xlink:href="assets/vendors/bootstrap-icons/bootstrap-icons.svg#search"
              />
            </svg>
          </button>
        </div>

        <!-- 모달 확인용
      <div class="row mb-1">
        <div class="col-sm-10">
          <div class="form-check">
            <button class="btn btn-sm btn-primary" @click="showInsertModal">모달</button>
          </div>
        </div>
      </div> -->
        <!-- 주변 상권 -->
        <div class="row mb-1">
          <div class="col-sm-10">
            <div class="form-check">
              <input
                class="form-check-input"
                type="checkbox"
                id="checkbox"
                v-model="$store.state.apt.radioShow"
                @click="checkboxChange"
              />
              <label class="form-check-label" for="checkbox">
                주변상권 보기
              </label>
            </div>
          </div>
        </div>
        <div v-show="$store.state.apt.radioShow" class="row mb-3">
          <div class="container">
            <div class="row row-cols-3">
              <div class="col">
                <div class="form-check">
                  <input
                    class="form-check-input"
                    type="radio"
                    name="flexRadioDefault"
                    id="flexRadioDefault1"
                    v-model="radioValue"
                    value="CE7"
                  />
                  <label class="form-check-label" for="flexRadioDefault1">
                    카페
                  </label>
                </div>
              </div>
              <div class="col">
                <div class="form-check">
                  <input
                    class="form-check-input"
                    type="radio"
                    name="flexRadioDefault"
                    id="flexRadioDefault2"
                    v-model="radioValue"
                    value="MT1"
                  />
                  <label class="form-check-label" for="flexRadioDefault2">
                    마트
                  </label>
                </div>
              </div>
              <div class="col">
                <div class="form-check">
                  <input
                    class="form-check-input"
                    type="radio"
                    name="flexRadioDefault"
                    id="flexRadioDefault3"
                    v-model="radioValue"
                    value="CS2"
                  />
                  <label class="form-check-label" for="flexRadioDefault3">
                    편의점
                  </label>
                </div>
              </div>
              <div class="col">
                <div class="form-check">
                  <input
                    class="form-check-input"
                    type="radio"
                    name="flexRadioDefault"
                    id="flexRadioDefault4"
                    v-model="radioValue"
                    value="HP8"
                  />
                  <label class="form-check-label" for="flexRadioDefault4">
                    병원
                  </label>
                </div>
              </div>
              <div class="col">
                <div class="form-check">
                  <input
                    class="form-check-input"
                    type="radio"
                    name="flexRadioDefault"
                    id="flexRadioDefault5"
                    v-model="radioValue"
                    value="FD6"
                  />
                  <label class="form-check-label" for="flexRadioDefault5">
                    음식점
                  </label>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
    <apt-detail v-on:call-parent-interest="interest"> </apt-detail>
  </div>
</template>

<script>
import AptDetail from "@/components/Modal/AptDetail.vue";

import { Modal } from "bootstrap";

import util from "@/common/util.js";
import http from "@/common/axios.js";

export default {
  components: { AptDetail },
  data() {
    return {
      container: "",
      options: "",
      map: "",
      positions: [],
      swlatlng: 0.0,
      nelatlng: 0.0,
      ismapchange: false,
      aptList: [],

      // 동검색
      ps: "",
      searchDong: "",

      // 모달
      aptDetail: null,

      radioValue: "",

      markers: [],
    };
  },
  mounted() {
    this.aptDetail = new Modal(document.getElementById("aptDetail"));
    if (window.kakao && window.kakao.maps) {
      this.initMap();
    } else {
      const script = document.createElement("script");
      /* global kakao */
      script.onload = () => kakao.maps.load(this.initMap);
      script.src =
        "//dapi.kakao.com/v2/maps/sdk.js?autoload=false&appkey=e69304609b9fcdc7c40ff0142f9df19a&libraries=services";
      document.head.appendChild(script);
    }
  },
  methods: {
    initMap() {
      this.container = document.getElementById("map");
      this.options = {
        center: new kakao.maps.LatLng(37.566292771444616, 126.97953529063118),
        level: 4,
      };

      this.map = new kakao.maps.Map(this.container, this.options); // 지도를 생성합니다

      // var $this = this;
      // kakao.maps.event.addListener(this.map, 'dragend', function() {
      //   $this.moveMap();
      // });

      // 현재 위치의 남서쪽, 북동쪽 좌표를 가져온다
      var bounds = this.map.getBounds();

      // 영역정보의 남서쪽 정보를 얻어옵니다
      this.swlatlng = bounds.getSouthWest();

      // 영역정보의 북동쪽 정보를 얻어옵니다
      this.nelatlng = bounds.getNorthEast();

      console.log("남서:" + this.swlatlng);
      console.log("북동:" + this.nelatlng);

      // 현재 화면안에서 마커를 표시할 위치와 title 객체 배열을 백으로 부터 받아온다
      http
        .get("/markers", {
          params: {
            swLat: this.swlatlng.getLat(),
            swLng: this.swlatlng.getLng(),
            neLat: this.nelatlng.getLat(),
            neLng: this.nelatlng.getLng(),
          },
        })
        .then(({ data }) => {
          for (var i = 0; i < data.length; i++) {
            var lat = data[i].lat;
            var lng = data[i].lng;
            var aptName = data[i].aptName;

            this.positions[i] = {
              title: aptName,
              latlng: new kakao.maps.LatLng(lat, lng),
            };
          }

          this.displayMarker();
        });

      this.$store.commit("SET_RADIOBUTTON", {
        radioShow: false,
      });
    },

    interest() {
      console.log(this.$store.state.login.userSeq);
      console.log(this.$store.state.apt.no);
      var $this = this;
      http
        .get("/interests", {
          params: {
            userSeq: $this.$store.state.login.userSeq,
            house_no: $this.$store.state.apt.no,
            aptName: $this.$store.state.apt.aptName,
            dong: $this.$store.state.apt.dong,
            floor: $this.$store.state.apt.floor,
            area: $this.$store.state.apt.area,
            dealAmount: $this.$store.state.apt.dealAmount,
          },
        })
        .then(({ data }) => {
          console.log(data);
          if (data == 1) {
            this.$alertify.success("관심 매물이 등록되었습니다.");
          } else {
            this.$alertify.error("이미 관심 매물이 등록되어 있습니다.");
          }
        })
        .catch((error) => {
          console.log(error);
        });
    },

    displayMarker() {
      // 마커 이미지의 이미지 주소입니다
      var imageSrc =
        "https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/markerStar.png";

      for (var i = 0; i < this.positions.length; i++) {
        // 마커 이미지의 이미지 크기 입니다
        var imageSize = new kakao.maps.Size(24, 35);

        // 마커 이미지를 생성합니다
        var markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize);

        // 마커를 생성합니다
        var marker = new kakao.maps.Marker({
          map: this.map, // 마커를 표시할 지도
          position: this.positions[i].latlng, // 마커를 표시할 위치
          title: this.positions[i].title, // 마커의 타이틀, 마커에 마우스를 올리면 타이틀이 표시됩니다
          image: markerImage, // 마커 이미지
          clickable: true,
        });
        marker.setMap(this.map);

        var $this = this;
        kakao.maps.event.addListener(
          marker,
          "click",
          (function (marker) {
            return function () {
              http
                .get("/selectmarkers", {
                  params: {
                    aptName: marker.getTitle(),
                  },
                })
                .then(({ data }) => {
                  console.log(marker.getPosition());
                  $this.$store.commit("SET_APT_DEATIL", {
                    no: data.no,
                    aptName: data.aptName,
                    area: data.area,
                    buildYear: data.buildYear,
                    dealAmount: data.dealAmount,
                    dealYMD: $this.makeDateStr(
                      data.dealYear,
                      data.dealMonth,
                      data.dealDay,
                      "."
                    ),
                    dealDay: data.dealDay,
                    dealMonth: data.dealMonth,
                    dealYear: data.dealYear,
                    dong: data.dong,
                    floor: data.floor,
                    lat: data.lat,
                    lng: data.lng,
                  });
                  $this.aptDetail.show();
                });
            };
          })(marker)
        );
      }
    },

    makeDateStr: util.makeDateStr,

    checkboxChange() {
      this.$store.commit("SET_RADIOBUTTON", {
        radioShow: !this.$store.state.apt.radioShow,
      });
      this.radioValue = "";
    },

    placesSearchCB(data, status) {
      if (status === kakao.maps.services.Status.OK) {
        for (var i = 0; i < data.length; i++) {
          this.displayCommercial(data[i]);
        }
      }
    },

    displayCommercial(place) {
      var imageSize = new kakao.maps.Size(30, 30);

      var markerImage = new kakao.maps.MarkerImage(
        "https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/marker_red.png",
        imageSize
      );

      // 마커를 생성하고 지도에 표시합니다
      var marker = new kakao.maps.Marker({
        map: this.map,
        position: new kakao.maps.LatLng(place.y, place.x),
        image: markerImage,
      });

      this.markers.push(marker);
    },

    clickSearchBtn() {
      // 주소-좌표 변환 객체를 생성합니다
      var geocoder = new kakao.maps.services.Geocoder();

      var $this = this;
      geocoder.addressSearch(
        "서울특별시" + this.searchDong,
        function (result, status) {
          // 정상적으로 검색이 완료됐으면
          if (status === kakao.maps.services.Status.OK) {
            $this.options = {
              center: new kakao.maps.LatLng(result[0].y, result[0].x),
              level: 4,
            };
            $this.map = new kakao.maps.Map($this.container, $this.options); // 지도를 생성합니다

            // 현재 위치의 남서쪽, 북동쪽 좌표를 가져온다
            var bounds = $this.map.getBounds();

            // 영역정보의 남서쪽 정보를 얻어옵니다
            $this.swlatlng = bounds.getSouthWest();

            // 영역정보의 북동쪽 정보를 얻어옵니다
            $this.nelatlng = bounds.getNorthEast();

            // 현재 화면안에서 마커를 표시할 위치와 title 객체 배열을 백으로 부터 받아온다
            http
              .get("/markers", {
                params: {
                  swLat: $this.swlatlng.getLat(),
                  swLng: $this.swlatlng.getLng(),
                  neLat: $this.nelatlng.getLat(),
                  neLng: $this.nelatlng.getLng(),
                },
              })
              .then(({ data }) => {
                for (var i = 0; i < data.length; i++) {
                  var lat = data[i].lat;
                  var lng = data[i].lng;
                  var aptName = data[i].aptName;

                  $this.positions[i] = {
                    title: aptName,
                    latlng: new kakao.maps.LatLng(lat, lng),
                  };
                }

                $this.displayMarker();
              });
          }
        }
      );
    },
  },
  watch: {
    radioValue() {
      for (var i = 0; i < this.markers.length; i++) {
        this.markers[i].setMap(null);
      }
      this.markers = [];
      var ps = new kakao.maps.services.Places(this.map);
      // 카테고리로 은행을 검색합니다
      ps.categorySearch(this.radioValue, this.placesSearchCB, {
        useMapBounds: true,
      });
    },
  },
};
</script>

<style>
/* 스크롤바, list 등 부트스트랩에서 찾아서 이쁘게 바꾸자 */
#wrapper {
  position: relative;
}

#searchBox {
  position: absolute;

  top: 10px;
  left: 10px;
  z-index: 100;

  width: 450px;
  padding: 20px;

  background-color: white;
}
#list {
  position: absolute;

  /* 위치 */
  top: 10px;
  bottom: 10px;
  right: 5px;
  z-index: 100;

  /* list */
  width: 400px;
  padding: 20px;

  /* rgba로 하면 배경만 투명해진다 */
  /* background-color:yellow;
    /* 배경 투명도 
    opacity: 0.7; */
  background-color: rgba(8, 129, 243, 0.8);

  /* list크기를 넘는 데이터가 들어왔을때 */
  /* 세로 */
  overflow-y: scroll;
}

/* 스크롤바 */
/* width */
::-webkit-scrollbar {
  width: 10px;
}

/* Track */
::-webkit-scrollbar-track {
  background: #f1f1f1;
}

/* Handle */
::-webkit-scrollbar-thumb {
  background: #888;
}

/* Handle on hover */
::-webkit-scrollbar-thumb:hover {
  background: #555;
}
</style>
