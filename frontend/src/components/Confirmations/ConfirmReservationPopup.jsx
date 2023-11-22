// eslint-disable-next-line no-unused-vars
import React from "react";
import PropTypes from "prop-types";

import { Button } from "antd";

const ConfirmReservationPopup = ({ onClose }) => {
    return (
        <>
            <div className="flex flex-col justify-center rounded-md p-4 bg-white shadow-xl">
                <div>Are you sure?</div>
                <div className="flex justify-center gap-3">
                    <Button type="primary" onClick={onClose} block>
                        Cancel
                    </Button>
                    <Button type="primary" htmlType="submit" block>
                        Reserve
                    </Button>
                </div>
            </div>
        </>
    );
}

export default ConfirmReservationPopup;

ConfirmReservationPopup.propTypes = {
    onClose: PropTypes.func,
};