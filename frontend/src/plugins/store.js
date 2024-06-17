import { createStore } from 'vuex'
import axios from 'axios'

const store = createStore({
  state(){
    return {
      name: 'kim',
      age: 20,
      more: {},
    }
  },
  mutations :{
    setMore(state, data){
        state.more = data;
    },
    증가(state, data){
        state.age += data
    }
  },
  actions : {
    getData(context){
        axios.get('https://codingapple1.github.io/vue/more0.json')
        .then((a)=>{
            console.log(a.data);
            context.commit('setMore', a.data);
        })
    },
  },
})

export default store;