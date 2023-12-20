// eslint-disable-next-line no-unused-vars
import React, { useEffect, useState } from "react";
import { message, Empty } from "antd";
import useUser from "../../hooks/useUser";
import ReservationCard from "../../components/Cards/ReservationCard";
import { getReservations } from "../../apis/reservationAPI";
import Footer from "../../components/Common/Footer";

const Reservations = () => {
    const { user } = useUser();
    const [messageApi, contextHolder] = message.useMessage();
    const [reservations, setReservations] = useState([]);

    useEffect(() => {
        const fetchReservations = async () => {
            try {
                const response = await getReservations({ username: user });
                setReservations(response.data);
            } catch (error) {
                console.log(error);
                messageApi.open({
                    type: "error",
                    content: "Couldn't load your reservations!"
                })
            }
        };
        fetchReservations();
    }, [user]);

    return (
        <>
            {contextHolder}

            <div className="mx-auto p-8 bg-gray-100 rounded-md shadow-md w-full ">
                <h1 className="text-3xl text-center font-bold mb-6 text-gray-800">My Reservations</h1>
                <hr className="border-t border-gray-500 mb-6" />

                {reservations.length === 0 ? (
                    <Empty
                        image={Empty.PRESENTED_IMAGE_SIMPLE}
                        imageStyle={{ height: 90 }}
                        description={<div className="text-gray-600 text-lg text-center">You do not have any reservations to show</div>}
                    />
                ) : (
                    // <div className="grid grid-cols-1 lg:grid-cols-2 gap-6 w-full">
                    <div className="flex flex-col items-center md:justify-center md:flex-row flex-wrap gap-5 w-full">
                        {reservations.map((reservation, index) => (
                            <ReservationCard reservation={reservation} key={index} />
                        ))}
                    </div>
                )}
            </div>
            <Footer />
        </>
    );
};

export default Reservations;
