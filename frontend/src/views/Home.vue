<template>
  <div id="home">
    <h1>Welcome to the Classroom</h1>
    <button @click="goToTeacherLogin">선생님 입장</button>
    <button @click="goToStudentLogin">학생 입장</button>
  </div>
  <h4>안녕{{ $store.state.age }}</h4>
  <button @click="증가(10)">버튼</button>
  <p>{{ $store.state.more }}</p>
  <button @click="$store.dispatch('getData')">더보기 버튼</button>
  <p>{{ now2 }} {{ 카운터 }}</p>
  <button @click="카운터++">버튼</button>

  <p>{{ 내이름 }} {{ age }}</p>
</template>

<script>
import { mapState, mapMutations } from "vuex";

export default {
  name: "Home",
  data() {
    return {
      카운터: 0,
    };
  },
  computed: {
    now2() {
      return new Date();
    },

    ...mapState(["age", "likes"]),
    ...mapState({ 내이름: "name" }),
  },
  methods: {
    ...mapMutations(["setMore", "좋아요", "증가"]),
    now() {
      return new Date();
    },
    goToTeacherLogin() {
      this.$router.push({ name: "TeacherLogin" });
    },
    goToStudentLogin() {
      this.$router.push({ name: "Student" });
    },
  },
};
</script>

<style scoped>
#home {
  display: flex;
  flex-direction: column;
  align-items: center;
  margin-top: 50px;
}
button {
  margin: 10px;
  padding: 10px 20px;
  font-size: 16px;
  cursor: pointer;
}
</style>
