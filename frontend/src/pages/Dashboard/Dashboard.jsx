// eslint-disable-next-line no-unused-vars
import React, { useState, useEffect } from "react";
import { Empty } from "antd";
import { message } from "antd";
import { getRecentMovies } from "../../apis/movieAPI";
import { getUpcomingReservations } from "../../apis/reservationAPI";
import useUser from "../../hooks/useUser"
import Footer from "../../components/Common/Footer";
import NewReleasesCard from "../../components/Cards/NewReleasesCard";
import ReservationCard from "../../components/Cards/ReservationCard";

const Dashboard = () => {
    const { user } = useUser();
    const [messageApi, contextHolder] = message.useMessage();

    const [movies, setNewReleases] = useState([]);
    const [upcomingReservations, setUpcomingReservations] = useState([]);

    useEffect(() => {

        const fetchRecents = async () => {
            try {
                const response = await getRecentMovies();
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
                const response = await getUpcomingReservations({ username: user });
                setUpcomingReservations((response.data));
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
                <div className="mt-4 w-full text-2xl text-black font-semibold text-center">Upcoming reservations</div>
                <div className="w-full border border-gray-300 my-2"></div>
                {/* <div className="grid grid-cols-1 lg:grid-cols-2 gap-6 w-full"> */}
                <div className="flex flex-col items-center md:justify- md:flex-row flex-wrap gap-5 w-full">
                    {upcomingReservations.length == 0 ? <Empty description="You don't have any reservations" /> :
                        upcomingReservations.map((reservation, index) => (
                            <ReservationCard reservation={reservation} key={index} />
                        ))}
                </div>

                <div className="mt-8 w-full text-2xl text-black font-semibold text-center">Latest releases</div>
                <div className="w-full border border-gray-300 my-2"></div>
                <div className={`flex flex-col items-center sm:items-start sm:flex-row flex-wrap gap-5 w-full`}>
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