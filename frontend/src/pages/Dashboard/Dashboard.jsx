// eslint-disable-next-line no-unused-vars
import React, { useState, useEffect } from "react";
import { Empty } from "antd";
import { message } from "antd";
import { getMovies } from "../../apis/movieAPI";
import { getReservations } from "../../apis/reservationAPI";
import useUser from "../../hooks/useUser"
import Footer from "../../components/Common/Footer";
import NewReleasesCard from "../../components/Cards/NewReleasesCard";
import ReservationCard from "../../components/Cards/ReservationCard";

const Dashboard = () => {
    const { user } = useUser();
    const [messageApi, contextHolder] = message.useMessage();


    // TODO: Remove this mock data
    // const newReleases = [
    //     {
    //         poster: "https://webneel.com/daily/sites/default/files/images/daily/09-2019/25-movie-poster-design-jumanji-piqued.jpg",
    //         title: "Jumanji",
    //         description: "A horror and adventurous journey behind a person's life."
    //     }, {
    //         poster: "https://webneel.com/daily/sites/default/files/images/daily/09-2019/25-movie-poster-design-jumanji-piqued.jpg",
    //         title: "Jumanji",
    //         description: "A horror and adventurous journey behind a person's life."
    //     }, {
    //         poster: "https://webneel.com/daily/sites/default/files/images/daily/09-2019/25-movie-poster-design-jumanji-piqued.jpg",
    //         title: "Jumanji",
    //         description: "A horror and adventurous journey behind a person's life."
    //     },
    // ]

    const [movies, setNewReleases] = useState([]);
    const [upcomingReservations, setUpcomingReservations] = useState([]);

    useEffect(() => {

        const fetchRecents = async () => {
            try {
                const response = await getMovies();
                console.log("Movies: " + response.data);
                setNewReleases(response.data);
            } catch {
                messageApi.open({
                    type: "error",
                    content: "Couldn't load new releases!"
                })
            }
        }
        fetchRecents();

        const fetchReservations = async () => {
            try {
                const response = await getReservations(user);
                console.log("My reservations: " + response.data);
                setUpcomingReservations(response.data);
            } catch {
                messageApi.open({
                    type: "error",
                    content: "Couldn't load reservation data!"
                })
            }
        }
        fetchReservations();

    }, [user]);

    return (
        <>
            {contextHolder}

            <div className="flex flex-col gap-3 mx-3">
                <div className="mt-4 w-full text-2xl text-black font-semibold text-center lg:text-start">Upcoming reservations</div>
                <div className="w-full border border-gray-300 my-2"></div>
                <div className="flex flex-col items-center sm:items-start sm:flex-row flex-wrap gap-5 w-full min-w-fit">
                    {upcomingReservations.length == 0 ? <Empty description="You don't have any reservations" /> :
                        upcomingReservations.map((reservation, index) => (
                            <ReservationCard movie={reservation} key={index} />
                        ))}
                </div>

                <div className="mt-8 w-full text-2xl text-black font-semibold text-center lg:text-start">New releases</div>
                <div className="w-full border border-gray-300 my-2"></div>
                <div className="flex flex-col items-center sm:items-start sm:flex-row flex-wrap gap-5 w-full">
                    {movies.length == 0 ? <Empty description="No recently released movies" /> :
                        movies.map((latest, index) => (
                            <NewReleasesCard key={index} movie={latest} />
                        ))}
                </div>
            </div>
            <Footer />
        </>
    );
}

export default Dashboard;