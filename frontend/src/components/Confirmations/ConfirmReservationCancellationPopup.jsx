// eslint-disable-next-line no-unused-vars
import React, { useState } from "react";
import PropTypes from "prop-types";

import { Button } from "antd";

import { cancelReservation } from "../../apis/reservationAPI";

const ConfirmReservationCancellationPopup = ({ selectedReservation, onClose }) => {
    // const [reservationId, setReservationId] = useState(selectedReservation);

    const handleCancellation = async (event) => {
        event.preventDefault();
        try {
            const response = await cancelReservation(selectedReservation);
            console.log(response);
        } catch (e) {
            console.error(e)
        }
    }

    
    return (
        <>
            <div className="flex flex-col justify-center rounded-md p-4 bg-white shadow-xl">
                <div>Are you sure?</div>
                <div className="flex justify-center gap-3">
                        <Button type="primary" onClick={onClose} block>
                            No
                        </Button>
                        <Button type="primary" onClick={handleCancellation} block>
                            Yes, Cancel
                        </Button>
                </div>
            </div>
        </>
    );
}

export default ConfirmReservationCancellationPopup;

ConfirmReservationCancellationPopup.propTypes = {
    onClose: PropTypes.func,
    selectedReservation: PropTypes.number.isRequired,
};