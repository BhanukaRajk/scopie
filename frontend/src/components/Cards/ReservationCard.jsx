// eslint-disable-next-line no-unused-vars
import React from "react";
import PropTypes from "prop-types";

const ReservationCard = ({ reservation }) => {
    return (
        <>
            <div className="flex flex-col w-full max-w-xl p-4 bg-white shadow-md rounded-md">
                <div className="text-2xl text-black mb-2"><span className=" font-bold">Movie:</span> {reservation.title}</div>
                <div className="text-md text-gray-700"><span className=" font-bold">Reservation Id:</span> {reservation.reservationId}</div>
                <div className="text-md text-gray-700"><span className=" font-bold">Cinema:</span> {reservation.name}</div>
                <div className="text-md text-gray-700"><span className=" font-bold">Movie Date:</span> {reservation.movieDate.split('T')[0]}</div>
                <div className="text-md text-gray-700"><span className=" font-bold">Show Time:</span> {reservation.movieTime}</div>
                <div className="text-md text-gray-700"><span className=" font-bold">Seat Numbers:</span> {
                    reservation.seatNumbers.map((seat) => (seat + " "))
                }</div>
                <div className="text-md text-gray-700"><span className=" font-bold">Total Price:</span> Rs. {reservation.totalPrice.toFixed(2)}</div>
                <div className="text-sm text-gray-500 mt-4">Reserved on: {reservation.date.split('T')[0]}</div>
            </div>

        </>
    );
}

export default ReservationCard;

ReservationCard.propTypes = {
    reservation: PropTypes.object,
    identity: PropTypes.number,
};