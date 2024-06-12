import { createApp } from 'vue';
import App from './App.vue';
import WebSocketClient from './plugins/WebSocketClient';
import mitt from 'mitt';
import router from './router';

const app = createApp(App);
const eventBus = mitt();

app.config.globalProperties.$bus = eventBus;
app.use(WebSocketClient);
app.use(router);
app.mount('#app');
