import SockJS from 'sockjs-client';
import Stomp from 'stompjs';

let stompClient = null;
//웹소켓 연결 객체를 재사용(필요한 컴포넌트에서 아래 함수를 init시 사용가능) 
//웹소켓 연결과 구독 하나의 파일에서 관리
export function initializeWebSocket(app) {
  if (!stompClient) {
    const socket = new SockJS('http://localhost:8080/whiteboard');
    stompClient = Stomp.over(socket);

    stompClient.connect({}, (frame) => {
      console.log('Connected: ' + frame);
      app.config.globalProperties.$socket = stompClient;

      stompClient.subscribe('/topic/whiteboard', (message) => {
        console.log('Received whiteboard message: ', message.body);
        app.config.globalProperties.$bus.emit('whiteboard-event', message);
      });
      stompClient.subscribe('/topic/join', (message) => {
        console.log('Received join message: ', message.body);
        app.config.globalProperties.$bus.emit('join', message);
      });
      stompClient.subscribe('/topic/leave', (message) => {
        console.log('Received leave message: ', message.body);
        app.config.globalProperties.$bus.emit('leave', message);
      });
    });
  }
}
