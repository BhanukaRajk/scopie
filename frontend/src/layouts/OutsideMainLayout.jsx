// eslint-disable-next-line no-unused-vars
import React, {Outlet, useEffect} from "react";
import { useNavigate } from "react-router-dom";
import useUser from "../hooks/useUser";

const OutsideMainLayout = () => {
    const navigate = useNavigate();
    const user = useUser();

    useEffect(() => {
        if (!user) {
            navigate("/login");
        } else {
            navigate("/dashboard");
        }
    }, [user, navigate]);

    return (
        <>
            <section>
                <Outlet />
            </section>
        </>
    );
}

export default OutsideMainLayout;