// eslint-disable-next-line no-unused-vars
import React, { useState } from "react";
import PropTypes from "prop-types";
import useUser from "../../hooks/useUser";
import { payment } from "../../apis/reservationAPI";
import { message } from "antd"

const ReservationCard = ({ reservation }) => {
    const { user } = useUser();
    const [messageApi, contextHolder] = message.useMessage();
    const [paymentData] = useState({
        userName: user,
        reservationId: reservation.reservationId
    })

    const handlePayment = async (event) => {
        event.preventDefault();
        if (paymentData.userName != null && paymentData.reservationId != null) {
            try {
                const response = await payment(paymentData);
                console.log("Payment successful! " + response);
                await messageApi.open({
                    type: "success",
                    content: "Payment successful!"
                })
                window.location.reload();
            } catch {
                console.error("Payment unsuccessful!");
                messageApi.open({
                    type: "error",
                    content: "Payment unsuccessful!"
                })
            }
        } else {
            console.log("Something went wrong!");
        }
    }

    return (
        <>
            {contextHolder}

            <table className="w-full max-w-xl mx-auto bg-white shadow-md text-center rounded-md border-collapse">
                <tbody>
                    <tr>
                        <td colSpan="3" className="text-2xl font-semibold text-center text-black border-b-2 p-4">
                            {reservation.title}
                        </td>
                    </tr>
                    <tr className="flex flex-col sm:flex-row border-b-2">
                        <td className="w-full sm:w-1/3 p-4 border-r-2">
                            <div className="text-sm text-gray-700 font-bold">Reservation Id</div>
                            <div className="text-sm text-gray-700">{reservation.reservationId}</div>
                        </td>
                        <td className="w-full sm:w-1/3 p-4 border-r-2">
                            <div className="text-sm text-gray-700 font-bold">Cinema</div>
                            <div className="text-sm text-gray-700">{reservation.name}</div>
                        </td>
                        <td className="w-full sm:w-1/3 p-4">
                            <div className="text-sm text-gray-700 font-bold">Price</div>
                            <div className="text-sm text-gray-700">Rs. {reservation.totalPrice.toFixed(2)}</div>
                        </td>
                    </tr>
                    <tr className="flex flex-col sm:flex-row border-b-2">
                        <td className="w-full sm:w-1/3 p-4 border-r-2">
                            <div className="text-sm text-gray-700 font-bold">Movie Date</div>
                            <div className="text-sm text-green-700">{reservation.movieDate.split('T')[0]}</div>
                        </td>
                        <td className="w-full sm:w-1/3 p-4 border-r-2">
                            <div className="text-sm text-gray-700 font-bold">Show Time</div>
                            <div className="text-sm text-gray-700">{reservation.movieTime}</div>
                        </td>
                        <td className="w-full sm:w-1/3 p-4">
                            <div className="text-sm text-gray-700 font-bold">Reserved on</div>
                            <div className="text-sm text-gray-700">{reservation.date.split('T')[0]}</div>
                        </td>
                    </tr>
                    <tr>
                        <td colSpan="3" className="p-4 w-full text-center">
                            <div className="text-md text-gray-700 font-bold mb-2">Seat Numbers</div>
                            <div className="text-2xl text-gray-700 flex justify-center gap-6 flex-wrap">
                                {reservation.seatNumbers.map((seat, index) => (<div key={index}>{seat}</div>))}
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td colSpan="3" className="p-4 w-full text-center">
                            <div className="text-md text-gray-700 font-bold">
                                <button type="button" onClick={handlePayment} className={`py-1 w-28 text-center border-none ${reservation.paid? "bg-black text-white" : "bg-yellow-500 hover:bg-yellow-300 text-black"}`} disabled={reservation.paid} >{ reservation.paid? "Paid" : "Pay" }</button>
                            </div>
                        </td>
                    </tr>
                </tbody>
            </table>
        </>
    );
}

export default ReservationCard;

ReservationCard.propTypes = {
    reservation: PropTypes.object,
    identity: PropTypes.number,
};