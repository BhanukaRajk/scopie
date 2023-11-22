import api from "./indexAPI";


export const login = (data) => {
    return api.post('/user/login/', data);
}

export const logout = (data) => {
    return api.post('user/logout/', data);
}