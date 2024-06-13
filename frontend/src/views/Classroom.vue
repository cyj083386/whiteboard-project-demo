<template>
  <div id="classroom">
    <h2>Classroom Code: {{ classCode }}</h2>
    <button @click="toggleStudentList">학생 리스트 보기</button>
    <Whiteboard :classCode="classCode" :studentName="studentName" />
  </div>
  <div v-if="showStudentList" class="popup">
    <h3>접속 중인 학생 리스트</h3>
    <ul>
      <li v-for="(name, sessionId) in students" :key="sessionId">{{ name }}</li>
    </ul>
    <button @click="toggleStudentList">닫기</button>
  </div>
</template>

<script>
import Whiteboard from "../components/Whiteboard.vue";
import { initializeWebSocket } from "../plugins/WebSocketClient";
import app from "../main"; // app 인스턴스를 가져옵니다.

export default {
  name: "Classroom",
  components: {
    Whiteboard,
  },
  props: {
    classCode: {
      type: String,
      required: true,
    },
  },
  data() {
    return {
      studentName: this.$route.query.studentName,
      students: {},
      showStudentList: false,
    };
  },
  mounted() {
    initializeWebSocket(app, this.classCode); // app 인스턴스를 전달합니다.
    this.$root.$bus.on("join", this.handleStudentListJoin);
    this.$root.$bus.on("leave", this.handleStudentListLeave);
    window.addEventListener("beforeunload", this.unLoadEvent);
  },
  methods: {
    // 사용자 이탈시 처리 함수
    // 사용자가 사이트 창을 닫으려고 할 때
    // 다른 주소로 이동하려고 할 때
    // 같은 주소여도 새로고침하려고 할 때
    unLoadEvent(event) {
      if (this.canLeaveSite) return;

      event.preventDefault();
      event.returnValue = "";

      this.leaveClassroom();
      this.$root.$bus.off("join", this.handleStudentListJoin);
      this.$root.$bus.off("leave", this.handleStudentListLeave);
      window.removeEventListener("beforeunload", this.leave);
    },

    // 클래스룸 나갈때 소켓처리
    leaveClassroom() {
      const message = JSON.stringify({
        classCode: this.classCode,
        sender: this.studentName,
        type: "LEAVE",
      });
      if (this.$root.$socket && this.$root.$socket.connected) {
        this.$root.$socket.publish({
          destination: `/pub/leave/${this.classCode}`,
          body: message,
        });
      }
    },
    // 학생 참여시 리스트 처리 함수
    handleStudentListJoin(message) {
      const { sender, classCode } = message;
      if (this.classCode === classCode && sender !== "teacher") {
        this.$set(this.students, sender);
      }
    },
    // 학생 나가기시 리스트 처리 함수
    handleStudentListLeave(message) {
      const { sender, classCode } = message;
      if (this.classCode === classCode) {
        this.$delete(this.students, sender);
      }
    },
    // 학생 리스트 팝업 토글
    toggleStudentList() {
      this.showStudentList = !this.showStudentList;
    },
  },
};
</script>

<style scoped>
#classroom {
  display: flex;
  flex-direction: column;
  align-items: center;
}
.popup {
  position: fixed;
  top: 20px;
  right: 20px;
  width: 200px;
  padding: 10px;
  background: white;
  border: 1px solid #ccc;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
}
</style>
