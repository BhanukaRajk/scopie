import api from "./api";

// LOGIN TO THE SYSTEM WITH THE PROVIDED CREDENTIALS
export const login = (data) => {
    return api.post('/auth/login', data);
}

// LOGOUT THE USER FROM THE SYSTEM
export const logout = (data) => {
    return api.post('/auth/logout', data);
}

export const getVerificationCode = (data) => {
    return api.post('/auth/signup-validation', data);
}

export const signUp = (data) => {
    return api.post('/auth/signup-verification', data);
}

// TO RUN WHEN USER REQUESTS TO RESEND THE OTP CODE - DO NOT REFRESH THE PAGE - GET REQUSET USING PARAMETERS
export const resendCode = (email) => {
    return api.get('/auth/forgot-password/resend-one-time-passcode', {
        params: {
            email: email,
        }
    });
};


// VERIFY THE MAIL PROVIDED BY THE USER TO GET THE OTP CODE - IF ACCOUNT EXISTS OTP HAS TO BE SEND TO THE USER
export const verifyEmail = (data) => {
    return api.post('/auth/forgot-password/verify-email', data);
}

// VERIFY THE EMAIL AND THE OTP CODE PROVIDED BY THE USER
export const verifyUser = (data) => {
    return api.post('/auth/forgot-password/verify-user', data);
}

// CHANGE THE ACCOUNT PASSWORD WITH THE USER PROVIDED NEW PASSWORD
export const resetPassword = (data) => {
    return api.put('/auth/forgot-password/change-password', data);
}