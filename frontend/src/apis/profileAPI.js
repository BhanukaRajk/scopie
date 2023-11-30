import api from "./api";

export const editProfile = (newData) => {
    return api.put("/account/edit", newData)
}

// SIGN UP NEW USER TO THE SYSTEM
export const signUp = (data) => {
    return api.post('/account/new/', data);
}