import axios from "axios";
axios.defaults.withCredentials = true;
// axios 객체 생성
export default axios.create({
  baseURL: "http://localhost:8081/vue",
  headers: {
    "Content-type": "application/json",
    Authorization: sessionStorage.getItem("accessToken"),
  },
});
