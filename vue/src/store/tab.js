export default {
    state:{
        isCollapse:false
    },
    mutations: {
        menuCollapse(state){
            state.isCollapse = !state.isCollapse
        }
    }
}