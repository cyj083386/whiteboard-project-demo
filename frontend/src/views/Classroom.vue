<template>
  <div id="classroom">
    <h2>Classroom Code: {{ classCode }}</h2>
    <button @click="toggleStudentList">학생 리스트 보기</button>
    <Whiteboard :classCode="classCode" :sender="sender" />
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
import { reactive } from "vue";

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
  setup() {
    const students = reactive({});
    return { students };
  },
  data() {
    return {
      sender: this.$route.query.currentUser,
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
        sender: this.sender,
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
      try {
        console.log("handleStudentListJoin", message);
        const { sender, sessionId } = message;
        this.students[sessionId] = sender;
      } catch (error) {
        console.error(error);
      }
    },
    // 학생 나가기시 리스트 처리 함수
    handleStudentListLeave(message) {
      try {
        console.log("handleStudentListLeave", message);
        const { sessionId } = message;
        delete this.students[sessionId];
      } catch (error) {
        console.error(error);
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
