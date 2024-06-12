<template>
  <div id="whiteboard">
    <canvas ref="canvas"
            @mousedown="startDrawing"
            @mousemove="draw"
            @mouseup="stopDrawing"
            @mouseout="stopDrawing">
    </canvas>
    <div id="designTool">
      <button @click="buttonErase">모두 지우기</button>
    </div>
  </div>
</template>

<script>
export default {
  name: "Whiteboard",
  data() {
    return {
      drawing: false,
      context: null,
      lastX: 0,
      lastY: 0,
      scaleFactorX: 1,
      scaleFactorY: 1,
    };
  },
  mounted() {
    this.initCanvas();
    this.$root.$bus.on('whiteboard-event', this.handleIncomingDrawing);
    window.addEventListener('resize', this.resizeCanvas);
  },
  beforeUnmount() {
    this.$root.$bus.off('whiteboard-event', this.handleIncomingDrawing);
    window.removeEventListener('resize', this.resizeCanvas);
  },
  methods: {
    // 캔버스 세팅
    initCanvas() {
      this.resizeCanvas();
      const canvas = this.$refs.canvas;
      this.context = canvas.getContext("2d");
      this.context.strokeStyle = "#000000";
      this.context.lineWidth = 10;
      this.context.lineCap = "round";
      console.log("initCanvas");
    },
    resizeCanvas() {
      const canvas = this.$refs.canvas;
      const rect = canvas.getBoundingClientRect();
      const dpr = window.devicePixelRatio || 1;

      canvas.width = rect.width * dpr;
      canvas.height = rect.height * dpr;

      this.scaleFactorX = canvas.width / rect.width;
      this.scaleFactorY = canvas.height / rect.height;

      this.context = canvas.getContext("2d");
      this.context.scale(dpr, dpr);

      console.log("resizeCanvas", this.scaleFactorX, this.scaleFactorY);
    },
    // 그리기 시작 함수
    startDrawing(e) {
      this.drawing = true;
      this.lastX = e.offsetX * this.scaleFactorX;
      this.lastY = e.offsetY * this.scaleFactorY;
      console.log("startDrawing");
    },
    // 그리기 함수
    draw(e) {
      if (!this.drawing) return;

      const newX = e.offsetX * this.scaleFactorX;
      const newY = e.offsetY * this.scaleFactorY;

      // 그리기
      this.context.beginPath();
      this.context.moveTo(this.lastX, this.lastY);
      this.context.lineTo(newX, newY);
      this.context.stroke();

      // 좌표 업데이트
      const prevX = this.lastX;
      const prevY = this.lastY;
      this.lastX = newX;
      this.lastY = newY;

      console.log("draw", this.lastX, this.lastY);

      // 메시지 전송
      const message = JSON.stringify({
        x: this.lastX,
        y: this.lastY,
        prevX,
        prevY,
      });
      if (this.$root.$socket) {
        this.$root.$socket.send("/app/update", {}, message);
      }
    },
    // 그리기 스탑
    stopDrawing() {
      this.drawing = false;
    },
    // 지우개 버튼
    buttonErase() {
      this.context.clearRect(0, 0, this.$refs.canvas.width, this.$refs.canvas.height);
    },
    // 메시지 수신 처리 함수
    handleIncomingDrawing(message) {
      const { x, y, prevX, prevY } = JSON.parse(message.body);
      console.log(`Handling incoming drawing from (${prevX}, ${prevY}) to (${x}, ${y})`);

      // 그리기
      this.context.beginPath();
      this.context.moveTo(prevX, prevY);
      this.context.lineTo(x, y);
      this.context.stroke();

      // 좌표 업데이트
      this.lastX = x;
      this.lastY = y;
    }
  }
};
</script>

<style scoped>
#whiteboard {
  position: relative;
  width: 100%;
  height: 100vh;
  background: white;
}
canvas {
  display: block;
  width: 100%;
  height: 100%;
}
#designTool {
  position: absolute;
  top: 10px;
  left: 10px;
}
</style>