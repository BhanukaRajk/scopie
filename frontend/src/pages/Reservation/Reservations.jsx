// eslint-disable-next-line no-unused-vars
import React, { useEffect, useState } from "react";
import { Empty } from "antd";

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
            <div className="flex flex-col mx-3 my-2">
                <div className="my-2 text-2xl text-black w-full text-center font-semibold">My Reservations</div>
                <div className="border border-gray-500 w-full"></div>
                <div className="flex flex-col gap-3 w-full py-5">
                    {reservations.length === 0 ?
                        <Empty
                            // image={Empty.PRESENTED_IMAGE_SIMPLE}
                            imageStyle={{ height: 90 }}
                            description={<div className="text-gray-600 text-base">You do not have any reservations to show</div>}
                        /> :
                        reservations.map((reservation) => (
                            <ReservationCard movie={reservation} key={reservation.id} />
                        ))}
                </div>
            </div>
        </>
    );
}

export default Reservations;