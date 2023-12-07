// eslint-disable-next-line no-unused-vars
import React from "react";
import AddReservationForm from "../../components/Forms/Reservations/AddReservationForm";
import SeatPicker from "../../components/Forms/Reservations/SeatPicker";

const Reservation = () => {
    return (<>
        <div>
            <AddReservationForm />
        </div>

        <div>
            <SeatPicker />
        </div>
    </>)
}

export default Reservation;