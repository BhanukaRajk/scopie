// eslint-disable-next-line no-unused-vars
import React, { useEffect, useState } from "react";
import { Empty } from "antd";
import useUser from "../../hooks/useUser";

import ReservationCard from "../../components/Cards/ReservationCard";
import { getReservations } from "../../apis/reservationAPI";

const Reservations = () => {
    const { user } = useUser();
    const [reservations, setReservations] = useState([]);

    useEffect(() => {
        const fetchReservations = async () => {
            try {
                const response = await getReservations({ username: user });
                setReservations((response.data));
            } catch (error) {
                console.log(error);
            }
        }
        fetchReservations();
    }, [user]);

    return (
        <>
            <div className="flex flex-col mx-3 my-2">
                <div className="my-2 text-2xl text-black w-full text-center font-semibold">My Reservations</div>
                <div className="border border-gray-500 w-full"></div>
                <div className="flex flex-col lg:flex-row lg:flex-wrap gap-3 w-full py-5">
                    {reservations.length === 0 ?
                        <Empty
                            // image={Empty.PRESENTED_IMAGE_SIMPLE}
                            imageStyle={{ height: 90 }}
                            description={<div className="text-gray-600 text-base">You do not have any reservations to show</div>}
                        /> :
                        reservations.map((reservation, index) => (
                            <ReservationCard reservation={reservation} key={index} />
                        ))}
                </div>
            </div>
        </>
    );
}

export default Reservations;