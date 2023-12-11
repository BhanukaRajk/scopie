// eslint-disable-next-line no-unused-vars
import React, { useState, useEffect } from "react";
import { Empty } from "antd";
import NewReleasesCard from "../../components/Cards/NewReleasesCard";
import Footer from "../../components/Common/Footer";

import { getMovies } from "../../apis/movieAPI";
// import useUser from "../../hooks/useUser"

const Dashboard = () => {
    // const { user } = useUser();

    // TODO: Remove this mock data
    const newReleases = [
        {
            poster: "https://webneel.com/daily/sites/default/files/images/daily/09-2019/25-movie-poster-design-jumanji-piqued.jpg",
            title: "Jumanji",
            description: "A horror and adventurous journey behind a person's life."
        }, {
            poster: "https://webneel.com/daily/sites/default/files/images/daily/09-2019/25-movie-poster-design-jumanji-piqued.jpg",
            title: "Jumanji",
            description: "A horror and adventurous journey behind a person's life."
        }, {
            poster: "https://webneel.com/daily/sites/default/files/images/daily/09-2019/25-movie-poster-design-jumanji-piqued.jpg",
            title: "Jumanji",
            description: "A horror and adventurous journey behind a person's life."
        },
    ]

    const [movies, setNewReleases] = useState([]);
    // const [upcomingReservations, setUpcomingReservations] = useState([]);
    useEffect(() => {
        const fetchRecents = async () => {
            try {
                const response = await getMovies();
                console.log(response.data);
                // setNewReleases(response.data);
            } catch {
                console.error("e");
            }
        }
        fetchRecents();

    }), [];

    return (
        <>
            <div className="flex flex-col gap-3 mx-3">
                <div className="mt-4 w-full text-2xl text-black font-semibold text-center lg:text-start">Upcoming reservations</div>
                <div className="w-full border border-gray-300 my-2"></div>
                <div className="flex flex-col sm:flex-row sm:justify-start sm:flex-wrap gap-5 w-full min-w-fit">
                    {movies.length == 0 ? <Empty description="You don't have any reservations" /> :
                        movies.map((latest) => (
                            <NewReleasesCard movie={latest} key={latest.id} />
                        ))}
                </div>

                <div className="mt-8 w-full text-2xl text-black font-semibold text-center lg:text-start">New releases</div>
                <div className="w-full border border-gray-300 my-2"></div>
                <div className="flex flex-col sm:flex-row sm:justify-start sm:flex-wrap gap-5 w-full">
                    {newReleases.length == 0 ? <Empty description="No recently released movies" /> :
                        newReleases.map((latest) => (
                            <NewReleasesCard movie={latest} key={latest.movieId} />
                        ))}
                </div>
            </div>
            <Footer />
        </>
    );
}

export default Dashboard;