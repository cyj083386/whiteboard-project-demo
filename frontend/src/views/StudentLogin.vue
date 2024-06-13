<template>
  <div id="student">
    <form @submit.prevent="joinClassroom">
      <label for="classCode">Class Code:</label>
      <input v-model="classCode" type="text" id="classCode" required />
      <label for="studentName">Student Name:</label>
      <input v-model="studentName" type="text" id="studentName" required />
      <button type="submit">Join Classroom</button>
    </form>
  </div>
</template>

<script>
import SockJS from 'sockjs-client';
import Stomp from 'stompjs';

export default {
  name: "Student",
  data() {
    return {
      classCode: "",
      studentName: "",
    };
  },
  methods: {
    joinClassroom() {
      if (this.classCode && this.studentName) {
        console.log('Joining classroom:', this.classCode, 'with name:', this.studentName);
        if (!this.$root.$socket) {
          const socket = new SockJS('http://localhost:8080/whiteboard');
          const stompClient = Stomp.over(socket);

          stompClient.connect({}, (frame) => {
            console.log('Connected: ' + frame);
            this.$root.$socket = stompClient;

            this.$root.$socket.send("/app/join", {}, JSON.stringify({
              classCode: this.classCode,
              studentName: this.studentName,
            }));
          });
        } else {
          this.$root.$socket.send("/app/join", {}, JSON.stringify({
            classCode: this.classCode,
            studentName: this.studentName,
          }));
        }

        // Redirect to the whiteboard page
        this.$router.push({ name: 'Classroom', params: { classCode: this.classCode, studentName: this.studentName } });
      } else {
        alert('Classroom Code and Student Name are required.');
      }
    }
  },
};
</script>

<style scoped>
#student {
  display: flex;
  flex-direction: column;
  width: 300px;
  margin: 50px auto;
}
label {
  margin: 10px 0 5px;
}
input {
  padding: 10px;
  margin-bottom: 15px;
  border: 1px solid #ccc;
}
button {
  padding: 10px;
  background-color: #007BFF;
  color: white;
  border: none;
  cursor: pointer;
}
button:hover {
  background-color: #0056b3;
}
</style>
