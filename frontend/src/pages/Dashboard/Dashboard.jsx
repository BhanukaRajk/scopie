// eslint-disable-next-line no-unused-vars
import React, { useState, useEffect } from "react";
import { Empty } from "antd";
import NewReleasesCard from "../../components/Cards/NewReleasesCard";
import { getMovies } from "../../apis/movieAPI";

import useUser from "../../hooks/useUser";

import Footer from "../../components/Common/Footer";

const Dashboard = () => {

    const { user } = useUser();
    console.log(user);

    // TODO: Remove this mock data
    const movies = [
        // {
        //     poster: "https://webneel.com/daily/sites/default/files/images/daily/09-2019/25-movie-poster-design-jumanji-piqued.jpg",
        //     title: "Jumanji",
        //     description: "A horror and adventurous journey behind a person's life."
        // }, {
        //     poster: "https://webneel.com/daily/sites/default/files/images/daily/09-2019/25-movie-poster-design-jumanji-piqued.jpg",
        //     title: "Jumanji",
        //     description: "A horror and adventurous journey behind a person's life."
        // }, {
        //     poster: "https://webneel.com/daily/sites/default/files/images/daily/09-2019/25-movie-poster-design-jumanji-piqued.jpg",
        //     title: "Jumanji",
        //     description: "A horror and adventurous journey behind a person's life."
        // },
    ]

    const [newReleases, setNewReleases] = useState([]);
    useEffect(() => {
        const fetchRecents = async () => {
            try {
                const response = await getMovies();
                setNewReleases(response);
            } catch {
                console.log("error");
            }
        }
        fetchRecents();
    }), [];

    return (
        <>
            <div className="flex flex-col gap-3 mx-3">
                <div className="mt-36 md:mt-0 w-full text-2xl text-black">Upcoming reservations</div>
                <div className="flex flex-col sm:flex-row sm:justify-start sm:flex-wrap gap-5 w-full m-3">
                    {movies.length === 0 ? <Empty /> :
                        movies.map((latest) => (
                            <NewReleasesCard movie={latest} key={latest.id} />
                        ))}
                </div>

                <div className="w-full text-2xl text-black">New releases</div>
                <div className="flex flex-col sm:flex-row sm:justify-start sm:flex-wrap gap-5 w-full m-3">
                    {newReleases.length === 0 ? <Empty /> :
                        newReleases.map((latest) => (
                            <NewReleasesCard movie={latest} key={latest.id} />
                        ))}
                </div>
            </div>
            <Footer />
        </>
    );
}

export default Dashboard;