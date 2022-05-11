import Cookie from 'js-cookie'
export default {
    state:{
        token:'',
        userInfo:{
            id:null,
            username:null,
            accesslevel:null,
            email:null,
            headerimg:null
        }
    },
    mutations:{
        setToken(state,val){
            state.token = val
            Cookie.set('token',val)
        },
        clearToken(state){
            state.token = ''
            Cookie.remove('token')
        },
        getToken(state){
            state.token = state.token || Cookie.get('token')
        },
        setUserInfo(state,Info){
            state.userInfo.username = Info.username
            state.userInfo.id = Info.id
            state.userInfo.accesslevel = Info.accesslevel
            state.userInfo.email = Info.email
            state.userInfo.headerimg = Info.headerimg
        },
        clearUserInfo(state){
            state.userInfo.username = null
            state.userInfo.id = null
            state.userInfo.accesslevel = null
            state.userInfo.email = null
            state.userInfo.headerimg = null
        }
    }
}