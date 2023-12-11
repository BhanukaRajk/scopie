// eslint-disable-next-line no-unused-vars
import React, { useEffect } from "react";
import { useNavigate, Outlet } from "react-router-dom";
import useUser from "../hooks/useUser";

import { Skeleton } from 'antd';
import SideNavBar from "../components/Common/SideNavBar";
import TopNavBar from "../components/Common/TopNavBar";

const InsideMainLayout = () => {
    const navigate = useNavigate();
    const { user } = useUser();

    useEffect(() => {
        if (!user) {
            navigate("/login");
        }
    }, [user, navigate]);

    if (!user) {
        return (
            <Skeleton active />
        );
    } else {
        return (
            <>
                <div className="flex flex-col absolute top-0 bottom-0 right-0 left-0">
                    <TopNavBar />
                    <Outlet />
                    <SideNavBar />
                </div>
            </>
        );
    }
}

export default InsideMainLayout;