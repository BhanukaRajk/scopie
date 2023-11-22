import axios from "axios";

import { refreshToken } from "./authAPIs";

const MS_GATEWAY_URL = `${import.meta.env.GATEWAY_URL || "http://localhost:3201"}`;

const API_URL = `${MS_GATEWAY_URL}/api`;

const api = axios.create({
    baseURL: API_URL,
    headers: {
        "Content-Type": "application/json",
    },
    withCredentials: true,
});

export default api;