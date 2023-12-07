// import React, { useState } from "react";
import PropTypes from "prop-types";

const SeatPicker = ({ seatSpace, onClose }) => {

    return (
        <>
            <div className="flex flex-row flex-wrap">
                {seatSpace.length === 0 ? <div className='text-lg text-center text-gray-400'> Could not fetch data </div> :
                    seatSpace.map((seat) => (
                        <div className={seat ? `bg-red-700 text-white` : `text-black`} key={seat.id} >{seat.id}</div>
                    ))}
            </div>
            <button type="submit" className=" text-md text-center bg-blue-700 hover:bg-blue-400 text-white hover:text-white py-2 rounded font-semibold cursor-pointer">
                Reserve
            </button>
            <a className="text-black mt-4 text-sm cursor-pointer" onClick={onClose}>Cancel</a>

        </>
    )
}

export default SeatPicker;

SeatPicker.propTypes = {
    seatSpace: PropTypes.any,

    onClose: PropTypes.func,
}