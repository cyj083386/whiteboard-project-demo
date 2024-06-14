import { Client } from "@stomp/stompjs";

let stompClient = null;

export function initializeWebSocket(app, classCode) {
  if (stompClient && stompClient.connected) {
    stompClient.deactivate();
  }
  
  stompClient = new Client({
    brokerURL: "ws://localhost:8080/whiteboard",
    debug: function (str) {
      console.log(str);
    },
    reconnectDelay: 5000,
    heartbeatIncoming: 4000,
    heartbeatOutgoing: 4000,
    onConnect: (frame) => {
      console.log('Connected: ' + frame);
      app.config.globalProperties.$socket = stompClient;

      stompClient.subscribe(`/sub/class/${classCode}`, (message) => {
        console.log('Received message: ', message.body);
        app.config.globalProperties.$bus.emit('event', JSON.parse(message.body));
      });
      stompClient.subscribe(`/sub/class/${classCode}/join`, (message) => {
        console.log('Received join message: ', message.body);
        app.config.globalProperties.$bus.emit('join', JSON.parse(message.body));
      });

      stompClient.subscribe(`/sub/class/${classCode}/leave`, (message) => {
        console.log('Received leave message: ', message.body);
        app.config.globalProperties.$bus.emit('leave', JSON.parse(message.body));
      });
    },
    onStompError: (frame) => {
      console.error('Broker reported error: ' + frame.headers['message']);
      console.error('Additional details: ' + frame.body);
    }
  });

  stompClient.activate();
}
