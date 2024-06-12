<template>
    <div>
      <h2>Teacher Login</h2>
      <form @submit.prevent="login">
        <input v-model="username" placeholder="Username" required />
        <input type="password" v-model="password" placeholder="Password" required />
        <button type="submit">Login</button>
      </form>
    </div>
  </template>
  
  <script>
  import axios from 'axios';
  
  export default {
    data() {
      return {
        username: '',
        password: ''
      };
    },
    methods: {
      async login() {
        try {
          const response = await axios.post('http://localhost:8080/api/teacher/login', {
            username: this.username,
            password: this.password
          });
  
          if (response.data) {
            const classCodeResponse = await axios.get(`http://localhost:8080/api/classrooms/teacher/${response.data.id}`);
            const classCode = classCodeResponse.data.code;
            this.$router.push({ name: 'Classroom', params: { classCode } });
          } else {
            alert('Invalid credentials');
          }
        } catch (error) {
          console.error('Login failed', error);
        }
      }
    }
  };
  </script>
  