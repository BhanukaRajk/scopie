// eslint-disable-next-line no-unused-vars
import React from "react";
import PropTypes from "prop-types";

const OutsideMainLayout = ({ children }) => {
    return (
        <>
            <div>
                {children}
            </div>
        </>
    );
}

export default OutsideMainLayout;

OutsideMainLayout.propTypes = {
    children: PropTypes.node.isRequired,
};