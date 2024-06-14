<template>
  <div id="teacher-login">
    <form @submit.prevent="login">
      <label for="username">Username:</label>
      <input v-model="username" type="text" id="username" required />
      <label for="password">Password:</label>
      <input v-model="password" type="password" id="password" required />
      <button type="submit">Login</button>
    </form>
  </div>
</template>

<script>
import axios from "axios";

export default {
  data() {
    return {
      username: "",
      password: "",
    };
  },
  methods: {
    async login() {
      try {
        const response = await axios.post("http://localhost:8080/api/teacher/login", {
          username: this.username,
          password: this.password,
        });

        if (response.data) {
          const classCodeResponse = await axios.get(
            `http://localhost:8080/api/classrooms/teacher/${response.data}`
          );
          const classCode = classCodeResponse.data.code;
          this.$router.push({
            name: "Classroom",
            params: { classCode },
            query: { currentUser: this.username },
          });
        } else {
          alert("Invalid credentials");
        }
      } catch (error) {
        alert("Login failed", error);
      }
    },
  },
};
</script>

<style scoped>
#teacher-login {
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
