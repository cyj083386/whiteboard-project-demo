<template>
  <div id="student-login">
    <form @submit.prevent="joinClassroom">
      <label for="classCode">classCode:</label>
      <input v-model="classCode" type="text" id="classCode" required />
      <label for="studentName">Name:</label>
      <input v-model="studentName" type="text" id="studentName" required />
      <button type="submit">Join Classroom</button>
    </form>
  </div>
</template>

<script>
import app from "../main"; // app 인스턴스를 가져옵니다.

export default {
  data() {
    return {
      classCode: "",
      studentName: "",
    };
  },
  methods: {
    joinClassroom() {
      if (this.classCode && this.studentName) {
        console.log("Joining classroom:", this.classCode, "with name:", this.studentName);
        // WebSocket 연결이 완료된 후에 메시지를 보냅니다.
        setTimeout(() => {
          app.config.globalProperties.$socket.publish({
            destination: `/pub/join/${this.classCode}`,
            body: JSON.stringify({
              type: "JOIN",
              sender: this.studentName,
            }),
          });
        }, 1000);

        // Redirect to the classroom page
        this.$router.push({
          name: "Classroom",
          params: { classCode: this.classCode },
          query: { currentUser: this.studentName },
        });
      } else {
        alert("Classroom ID and Name are required.");
      }
    },
  },
};
</script>

<style scoped>
#student-login {
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
  background-color: #007bff;
  color: white;
  border: none;
  cursor: pointer;
}
button:hover {
  background-color: #0056b3;
}
</style>
