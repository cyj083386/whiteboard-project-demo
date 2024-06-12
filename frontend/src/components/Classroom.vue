<template>
    <div id="whiteboard" @mousedown="startDrawing" @mouseup="stopDrawing" @mousemove="draw">
      <canvas ref="canvas"></canvas>
    </div>
  </template>
  
  <script>
  import { ref, onMounted } from 'vue';
  
  export default {
    name: 'Whiteboard',
    setup() {
      const canvasRef = ref(null);
      const drawing = ref(false);
      let context = null;
  
      const startDrawing = (event) => {
        drawing.value = true;
        draw(event);
      };
  
      const stopDrawing = () => {
        drawing.value = false;
        context.beginPath();
      };
  
      const draw = (event) => {
        if (!drawing.value) return;
        context.lineWidth = 5;
        context.lineCap = 'round';
        context.strokeStyle = 'black';
  
        context.lineTo(event.clientX, event.clientY);
        context.stroke();
        context.beginPath();
        context.moveTo(event.clientX, event.clientY);
  
        const message = JSON.stringify({ x: event.clientX, y: event.clientY });
        window.app.config.globalProperties.$socket.send("/app/update", {}, message);
      };
  
      const handleEvent = (event) => {
        const { x, y } = event;
        context.lineTo(x, y);
        context.stroke();
        context.beginPath();
        context.moveTo(x, y);
      };
  
      onMounted(() => {
        context = canvasRef.value.getContext('2d');
        canvasRef.value.width = window.innerWidth;
        canvasRef.value.height = window.innerHeight;
        window.app.config.globalProperties.$bus.on('whiteboard-event', handleEvent);
      });
  
      return {
        canvasRef,
        startDrawing,
        stopDrawing,
        draw,
      };
    },
  };
  </script>
  
  <style>
  #whiteboard {
    position: relative;
    width: 100%;
    height: 100vh;
    background: white;
  }
  </style>
  