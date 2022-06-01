import Vue from "vue";
import kakao from "vue-kakao-sdk";

const apiKey = "8f5222bad0724268fd9320ee91df6988";
Vue.use(kakao, { apiKey }); // apiKey 를 반드시 입력해줘야한다.
