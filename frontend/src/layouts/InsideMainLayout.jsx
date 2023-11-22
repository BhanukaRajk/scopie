// eslint-disable-next-line no-unused-vars
import React from "react";
import PropTypes from "prop-types";

import SideNavBar from "../components/Navbars/SideNavBar";

const InsideMainLayout = ({ children }) => {
    return (
        <>
            <div>
                <SideNavBar />
                {children}
            </div>
        </>
    );
}

export default InsideMainLayout;

InsideMainLayout.propTypes = {
    children: PropTypes.node.isRequired,
};