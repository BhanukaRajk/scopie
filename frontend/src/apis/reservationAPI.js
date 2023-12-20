import api from "./api";

// GET THE USER'S RESERVATIONS
export const getReservations = (user) => {
    return api.get("/reservations/my-reservations", { params : user })
}

// GET THE USER'S RESERVATIONS
export const getUpcomingReservations = (user) => {
    return api.get("/reservations/upcoming-reservations", { params : user })
}

// CREATE A NEW RESERVATION
export const newReservation = (data) => {
    return api.post("/reservations/new", data)
}

// CANCEL A RESERVATION
export const cancelReservation = (data) => {
    return api.delete("/reservations/cancel", data)
}

export const payment = (data) => {
    return api.post("/reservations/payment", data);
}

// GET THE SEAT ARRAY
export const checkSeatAvailability = (requestedBooking) => {
    return api.post("/reservations/availability", requestedBooking);
}