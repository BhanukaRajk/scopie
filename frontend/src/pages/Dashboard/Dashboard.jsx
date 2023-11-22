// eslint-disable-next-line no-unused-vars
import React from "react";

import NewReleasesCard from "../../components/Cards/NewReleasesCard";

const Dashboard = () => {

    const movie = {
        poster: "https://imgs.search.brave.com/DBArP3DuP1ka66lm8-5HYNoC6XKuMpic6kGY84f8IP4/rs:fit:500:0:0/g:ce/aHR0cHM6Ly9tZWRp/YS5pc3RvY2twaG90/by5jb20vaWQvMTQ4/NTE2NDAxNy9waG90/by9jaGF0LWRpc2N1/c3Npb24taWNvbi53/ZWJwP2I9MSZzPTE3/MDY2N2Emdz0wJms9/MjAmYz0yWm1iUmpY/Skl4MzlMTTVLbExB/T2hvSS0yV0Vsdkgt/TnJqWUE1MVlmeXdZ/PQ",
        title: "Movie Title",
        description: "Movie Description",
    }

    return (
        <>
            <div className="flex flex-col gap-3 w-full m-3">
                <div className="w-full text-2xl text-black">New releases</div>
                <div className="flex flex-col md:flex-row md:justify-center gap-5 w-full md:w-20 m-3">
                    <NewReleasesCard movie={movie}/>
                </div>
            </div>
        </>
    );
}

export default Dashboard;