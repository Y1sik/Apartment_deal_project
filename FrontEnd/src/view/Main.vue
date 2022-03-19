<template>
  <div id="main">
    <div class="page-heading">
      <h3></h3>
    </div>
    <div class="page-content">
      <section class="row">
        <div class="col-12 col-lg">
          <div class="row">
            <div class="col-6 col-lg-3 col-md-6">
              <div class="card">
                <div class="card-body px-3 py-4-5">
                  <div class="row">
                    <div class="col-md-2" style="padding-left: 60px">
                      <div class="stats-icon purple">
                        <i class="iconly-boldShow"> </i>
                      </div>
                    </div>
                    <div class="col-md-8">
                      <h6 class="font-extrbold">공지사항</h6>
                      <h6 class="text-muted font-semibold">
                        [공지사항] 회원가입 이용
                      </h6>
                      <h6 class="text-muted font-semibold">
                        [공지사항] 게시판 이용
                      </h6>
                      <h6 class="text-muted font-semibold">
                        [공지사항] 매물 검색 이용
                      </h6>
                      <h6 class="text-muted font-semibold">
                        [공지사항] 로그인 시 작성
                      </h6>
                    </div>
                  </div>
                </div>
              </div>
            </div>

            <div class="col-6 col-lg-3 col-md-6">
              <div class="card">
                <div class="card-body px-3 py-4-5">
                  <div class="row">
                    <div class="col-md-2" style="padding-left: 60px">
                      <div class="stats-icon blue">
                        <i class="iconly-boldBookmark"> </i>
                      </div>
                    </div>
                    <div class="col-md-8">
                      <h6 class="font-extrbold">추천매물</h6>
                      <h6 class="text-muted font-semibold">
                        [매물] e편한 아파트
                      </h6>
                      <h6 class="text-muted font-semibold">[매물] 레미안</h6>
                      <h6 class="text-muted font-semibold">[매물] 자이</h6>
                      <h6 class="text-muted font-semibold">[매물] 아이파트</h6>
                    </div>
                  </div>
                </div>
              </div>
            </div>

            <div class="col-6 col-lg-3 col-md-6">
              <div class="card">
                <div class="card-body px-3 py-4-5">
                  <div class="row">
                    <div class="col-md-2" style="padding-left: 60px">
                      <div class="stats-icon green">
                        <i class="iconly-boldProfile"> </i>
                      </div>
                    </div>
                    <div class="col-md-8">
                      <h6 class="font-extrbold">리뷰</h6>
                      <h6 class="text-muted font-semibold">
                        [리뷰] 아이파크 리뷰
                      </h6>
                      <h6 class="text-muted font-semibold">
                        [리뷰] 오션뷰 리뷰
                      </h6>
                      <h6 class="text-muted font-semibold">
                        [리뷰] 사상 아파트 주변 건물
                      </h6>
                      <h6 class="text-muted font-semibold">
                        [리뷰] 역세권 리뷰
                      </h6>
                    </div>
                  </div>
                </div>
              </div>
            </div>

            <div class="col-6 col-lg-3 col-md-5">
              <div class="card">
                <div class="card-body px-3 py-4-5">
                  <div class="row">
                    <div class="col-md-2" style="padding-left: 60px">
                      <div class="stats-icon red">
                        <i class="iconly-boldAdd-User"> </i>
                      </div>
                    </div>
                    <div class="col-md-8">
                      <h6 class="font-extrbold">부동산</h6>
                      <h6 class="text-muted font-semibold">
                        [부동산] 서울 영등포구 부동산
                      </h6>
                      <h6 class="text-muted font-semibold">
                        [부동산] 서울 사직동
                      </h6>
                      <h6 class="text-muted font-semibold">
                        [부동산] 서울 평창동
                      </h6>
                      <h6 class="text-muted font-semibold">
                        [부동산] 서울 숭인동
                      </h6>
                    </div>
                  </div>
                </div>
              </div>
            </div>

            <div class="row" style="padding-right: 0">
              <div class="col-12 col-lg-9 col-md-5">
                <div class="card">
                  <div class="card-body" style="position: relative">
                    <div
                      id="carouselExampleCaptions"
                      class="carousel slide"
                      data-bs-ride="carousel"
                    >
                      <ol class="carousel-indicators">
                        <li
                          data-bs-target="#carouselExampleCaptions"
                          data-bs-slide-to="0"
                          class="active"
                        ></li>
                        <li
                          data-bs-target="#carouselExampleCaptions"
                          data-bs-slide-to="1"
                        ></li>
                        <li
                          data-bs-target="#carouselExampleCaptions"
                          data-bs-slide-to="2"
                        ></li>
                      </ol>
                      <div class="carousel-inner">
                        <div class="carousel-item active">
                          <img
                            src="@/assets/img/aptImg.jpg"
                            class="d-block w-100"
                            alt="..."
                            style="height: 600px"
                          />
                          <div class="carousel-caption d-none d-md-block">
                            <h5>어떤 집을 찾고 계세요?</h5>
                            <p>your capacity to enjoy life</p>
                          </div>
                        </div>
                        <div class="carousel-item">
                          <img
                            src="@/assets/img/aptImg2.png"
                            class="d-block w-100"
                            alt="..."
                            style="height: 600px"
                          />
                          <div class="carousel-caption d-none d-md-block">
                            <h5>Life's not all gloom and despondency</h5>
                            <p>
                              Nulla vitae elit libero, a pharetra augue mollis
                              interdum.
                            </p>
                          </div>
                        </div>
                      </div>
                      <a
                        class="carousel-control-prev"
                        href="#carouselExampleCaptions"
                        role="button"
                        data-bs-slide="prev"
                      >
                        <span
                          class="carousel-control-prev-icon"
                          aria-hidden="true"
                        ></span>
                        <span class="visually-hidden">Previous</span>
                      </a>
                      <a
                        class="carousel-control-next"
                        href="#carouselExampleCaptions"
                        role="button"
                        data-bs-slide="next"
                      >
                        <span
                          class="carousel-control-next-icon"
                          aria-hidden="true"
                        ></span>
                        <span class="visually-hidden">Next</span>
                      </a>
                    </div>
                  </div>
                </div>
              </div>

              <div class="col-3 col-lg-3 col-md-3">
                <div class="card">
                  <div class="card-header"><h4>분양 정보</h4></div>
                  <div class="card-body">
                    <img
                      src="@/assets/img/apt.png"
                      style="width: 320px; height: 400px"
                    />
                    <div class="col-md-8" style="padding: 10px">
                      <h6 class="font-extrbold">건물유형</h6>
                      <h6 class="text-muted font-semibold">오피스텔</h6>
                      <h6 class="font-extrbold">공급유형</h6>
                      <h6 class="text-muted font-semibold">민간분양</h6>
                      <h6 class="font-extrbold">총/분양 세대수</h6>
                      <h6 class="text-muted font-semibold">
                        총 420세대 / 분양 420세대
                      </h6>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </section>
    </div>
  </div>
</template>

<script>
export default {};
</script>

<style></style>
