import api from "./api";

export const getUserdata = (user) => {
    return api.get("/account/get-data", user)
}

export const editProfile = (updates) => {
    return api.put("/account/update-name", updates)
}

// SIGN UP NEW USER TO THE SYSTEM
export const updatePassword = (data) => {
    return api.post('/account/update-password', data);
}