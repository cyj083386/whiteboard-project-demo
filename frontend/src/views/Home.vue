<template>
  <div>
    <h1>Welcome</h1>
    <router-link to="/teacher">Teacher Login</router-link>
    <div>
      <h2>Student Login</h2>
      <form @submit.prevent="joinClassroom">
        <input v-model="classCode" placeholder="Class Code" required />
        <input v-model="name" placeholder="Your Name" required />
        <button type="submit">Join</button>
      </form>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      classCode: '',
      name: ''
    };
  },
  methods: {
    joinClassroom() {
      this.$router.push({ name: 'Classroom', params: { classCode: this.classCode }, query: { name: this.name } });
    }
  }
};
</script>
