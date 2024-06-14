import { createApp } from 'vue';
import App from './App.vue';
import router from './router';
import mitt from 'mitt';

const app = createApp(App);

// 이벤트 버스 설정(글로벌한)
app.config.globalProperties.$bus = mitt();

app.use(router);
app.mount('#app');

export default app; // 애플리케이션 인스턴스를 내보냅니다.
