// eslint-disable-next-line no-unused-vars
import React from "react";

import NewReleasesCard from "../../components/Cards/NewReleasesCard";

const Dashboard = () => {
    return (
        <>
            <div className="flex flex-col w-full m-3">
                <div className="w-full text-2xl text-black">New releases</div>
                <div className="w-full">
                    <NewReleasesCard />
                </div>
            </div>
        </>
    );
}

export default Dashboard;