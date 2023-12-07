// eslint-disable-next-line no-unused-vars
import React, { useEffect, useState } from "react";

import ReservationCard from "../../components/Cards/ReservationCard";
import { getReservations } from "../../apis/reservationAPI";

const Reservations = () => {
    const [reservations, setReservations] = useState([]);
    useEffect(() => {
        const fetchReservations = async () => {
            try {
                const response = await getReservations();
                setReservations(response);
            } catch {
                console.log("error");
            }
        }
        fetchReservations();
    }), [];

    return (
        <>
            <div className="flex mx-3 my-2">
                <div className="mb-2 text-lg text-black">My Reservations</div>
                <div className="flex flex-col gap-3 w-full">
                    {reservations.length === 0 ? <div className='text-lg text-center text-gray-400'> You do not have any upcoming reservations to show </div> :
                        reservations.map((reservation) => (
                            <ReservationCard movie={reservation} key={reservation.id} />
                        ))}
                </div>
            </div>
        </>
    );
}

export default Reservations;