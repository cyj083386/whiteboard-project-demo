import SockJS from 'sockjs-client';
import Stomp from 'stompjs';

export default {
  install(app) {
    const socket = new SockJS('http://localhost:8080/whiteboard');
    const stompClient = Stomp.over(socket);

    stompClient.connect({}, (frame) => {
      console.log('Connected: ' + frame);
      stompClient.subscribe('/topic/whiteboard', (message) => {
        console.log('Received message:', message.body); // 로그 추가
        app.config.globalProperties.$bus.emit('whiteboard-event', message);
      });
    });

    app.config.globalProperties.$socket = stompClient;
  },
};
