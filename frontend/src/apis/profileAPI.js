import api from "./api";

export const getUserdata = (userName) => {
    return api.get("/auth/account/update/", { params: userName })
}

export const editProfile = (updates) => {
    return api.patch("/auth/account/update", updates)
}

// SIGN UP NEW USER TO THE SYSTEM
export const updatePassword = (data) => {
    return api.patch('/auth/account/change-password', data);
}