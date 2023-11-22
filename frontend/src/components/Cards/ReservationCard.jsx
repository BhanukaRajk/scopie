// eslint-disable-next-line no-unused-vars
import React from "react";
import PropTypes from "prop-types";

const ReservationCard = ({ reservation }) => {
    return (
        <>
            <div className="flex flex-col w-full py-2 bg-white shadow-md rounded-md">
                <div className="w-full text-xl text-black">{reservation.title}</div>
                <div className="w-full text-md text-gray-700">{reservation.date}</div>
                <div className="w-full text-md text-gray-700">{reservation.time}</div>
                <div className="w-full text-md">{reservation.theater}</div>
                <div className="w-full text-md">{reservation.seatCount}</div>
                <div className="w-full text-lg text-black">{reservation.total}</div>
            </div>
        </>
    );
}

export default ReservationCard;

ReservationCard.propTypes = {
    reservation: PropTypes.object,
};