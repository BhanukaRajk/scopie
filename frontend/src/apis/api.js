import axios from "axios";

const MS_GATEWAY_URL = `${import.meta.env.VITE_GATEWAY_URL || "http://localhost:3202"}`;

const API_URL = `${MS_GATEWAY_URL}/api`;

const api = axios.create({
    baseURL: API_URL,
    headers: {
        "Content-Type": "application/json",
    },
    withCredentials: false,
});

export default api;