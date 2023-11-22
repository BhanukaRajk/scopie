import api from "./api";

// LOGIN TO THE SYSTEM WITH THE PROVIDED CREDENTIALS
export const login = (data) => {
    return api.post('/user/login/', data);
}

// LOGOUT THE USER FROM THE SYSTEM
export const logout = (data) => {
    return api.post('user/logout/', data);
}

// TO RUN WHEN USER REQUESTS TO RESEND THE OTP CODE - DO NOT REFRESH THE PAGE
export const resendCode = (data) => {
    return api.post('/forgot-password/resend-one-time-passcode/', data);
}

// VERIFY THE MEAIL PROVIDED BY THE USER TO GET THE OTP CODE - IF ACCOUNT EXISTS OTP HAS TO BE SEND TO THE USER
export const verifyEmail = (data) => {
    return api.post('/forgot-password/verify-email/', data);
}

// VERIFY THE EMAIL AND THE OTP CODE PROVIDED BY THE USER
export const verifyUser = (data) => {
    return api.post('/forgot-password/verify-user/', data);
}

// CHANGE THE ACCOUNT PASSWORD WITH THE USER PROVIDED NEW PASSWORD
export const resetPassword = (data) => {
    return api.put('/forgot-password/change-password/', data);
}
