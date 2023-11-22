// eslint-disable-next-line no-unused-vars
import React, { useEffect} from "react";
import { useNavigate, Outlet } from "react-router-dom";
import useUser from "../hooks/useUser";

import SideNavBar from "../components/Common/SideNavBar";

const InsideMainLayout = () => {
    const navigate = useNavigate();
    const user = useUser();

    useEffect(() => {
        if (!user) {
            navigate("/login");
        }
    }, [user, navigate]);

    return (
        <>
            <section>
                <Outlet />
                <SideNavBar />
            </section>
        </>
    );
}

export default InsideMainLayout;