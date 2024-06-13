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
import Whiteboard from '../components/Whiteboard.vue';
import { initializeWebSocket } from '../plugins/WebSocketClient';
import app from '../main'; // app 인스턴스를 가져옵니다.

export default {
  name: 'Classroom',
  components: {
    Whiteboard
  },
  data() {
    return {
      classCode: this.$route.params.classCode,
      studentName: this.$route.query.studentName,
      students: {},
      showStudentList: false
    };
  },
  mounted() {
    initializeWebSocket(app); // app 인스턴스를 전달합니다.
    this.$root.$bus.on('join', this.handleStudentListJoin);
    this.$root.$bus.on('leave', this.handleStudentLeave);
  },
  beforeUnmount() {
    this.$root.$bus.off('join', this.handleStudentListJoin);
    this.$root.$bus.off('leave', this.handleStudentListLeave);
    this.leaveClassroom();
  },
  methods: {
    // 클래스룸 나가기
    leaveClassroom() {
      const message = JSON.stringify({
        classCode: this.classCode,
      });
      if (this.$root.$socket && this.$root.$socket.connected) {
        this.$root.$socket.send("/app/leave", {}, message);
      }
    },
     // 학생 참여시 리스트 처리 함수
     handleStudentListJoin(message) {
      console.log("handleStudentListJoin", message.body);
      const { studentName, sessionId, classCode } = JSON.parse(message.body);
      if (this.classCode === classCode && studentName !== 'teacher') {
        this.students = {
          ...this.students,
          [sessionId]: studentName,
        };
        console.log("Updated students list: ", this.students);
      }
    },
    // 학생 나가기시 리스트 처리 함수
    handleStudentListLeave(message) {
      console.log("handleStudentListLeave", message.body);
      const { sessionId, classCode } = JSON.parse(message.body);
      if (this.classCode === classCode) {
        const { [sessionId]: _, ...rest } = this.students;
        this.students = rest;
        console.log("Updated students list: ", this.students);
      }
    },
    // 학생 리스트 팝업 토글
    toggleStudentList() {
      this.showStudentList = !this.showStudentList;
    }
  }
}
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
