import api from "./api";

// GET THE RESERVATIONS DATA USING FILTER
export const getReservations = (filter) => {
    return api.get("/reservations/", {params : filter})
}

// CREATE A NEW RESERVATION
export const newReservation = (data) => {
    return api.post("/reservations/reserve", data)
}

// CANCEL A RESERVATION
export const cancelReservation = (data) => {
    return api.delete("/reservations/cancel", data)
}