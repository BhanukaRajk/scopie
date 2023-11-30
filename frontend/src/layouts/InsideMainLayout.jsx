// eslint-disable-next-line no-unused-vars
import React, { useEffect} from "react";
import { useNavigate, Outlet } from "react-router-dom";
import useUser from "../hooks/useUser";

import SideNavBar from "../components/Common/SideNavBar";
import TopNavBar from "../components/Common/TopNavBar";
import Footer from "../components/Common/Footer";

const InsideMainLayout = () => {
    const navigate = useNavigate();
    const { user } = useUser();

    useEffect(() => {
        if (!user) {
            navigate("/login");
        }
    }, [user, navigate]);


    return (
        <>
            <div className="flex flex-col w-screen">
                <TopNavBar />
                <Outlet />
                <SideNavBar />
                <Footer />
            </div>
        </>
    );
}

export default InsideMainLayout;