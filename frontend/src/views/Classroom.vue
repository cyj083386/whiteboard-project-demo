<template>
    <div>
      <h2>Classroom {{ classCode }}</h2>
      <Whiteboard />
    </div>
  </template>
  
  <script>
  import Whiteboard from '../components/Whiteboard.vue';
  
  export default {
    name: 'Classroom',
    components: {
      Whiteboard
    },
    props: ['classCode'],
    mounted() {
      this.$socket.send("/app/join", {}, JSON.stringify({ classCode: this.classCode, name: this.$route.query.name }));
    }
  };
  </script>
  