// eslint-disable-next-line no-unused-vars
import React, { useState, useEffect } from "react";
import { message, Empty } from "antd";
import MovieCard from "../../components/Cards/MovieCard";
import { getMovies } from "../../apis/movieAPI";
import AddReservationForm from "../../components/Forms/Reservations/AddReservationForm";
import Footer from "../../components/Common/Footer";

const Movies = () => {
    const [messageApi, contextHolder] = message.useMessage();
    const [isFormOpen, setFormVisible] = useState(false);

    const [movies, setMovies] = useState([]);
    const [selectedMovie, setSelectedMovie] = useState([]);

    useEffect(() => {
        const fetchMovies = async () => {
            try {
                const response = await getMovies();
                setMovies(response.data);
            } catch {
                console.log("error");
                messageApi.open({
                    type: "error",
                    content: "Couldn't load movie list!"
                })
            }
        }
        fetchMovies();
    }, []);

    const onReservationFormOpen = (movie) => {
        setSelectedMovie(movie);
        setFormVisible(true);
    }

    const onReservationFormClose = () => {
        setFormVisible(false);
    }

    return (
        <>
            {contextHolder}

            <div className="mx-auto p-8 bg-gray-100 rounded-md shadow-md">
                <h1 className="text-3xl text-center font-bold mb-6 text-gray-800">Movie List</h1>
                <hr className="border-t border-gray-500 mb-6" />

                {movies.length === 0 ? (
                    <Empty
                        image={Empty.PRESENTED_IMAGE_SIMPLE}
                        imageStyle={{ height: 90 }}
                        description={<div className="text-gray-600 text-lg text-center">No movies to show</div>}
                    />
                ) : (
                    <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
                        {movies.map((movie, index) => (
                            <MovieCard
                                movie={movie}
                                key={index}
                                onReservationFormOpen={() => onReservationFormOpen(movie)}
                            />
                        ))}
                    </div>
                )}
            </div>
            <Footer/>

            {/* {isFormOpen && <AddReservationForm
                isFormOpen={isFormOpen}
                onReservationFormClose={onReservationFormClose}
                movie={selectedMovie}
            />} */}
            <AddReservationForm
                isFormOpen={isFormOpen}
                onReservationFormClose={onReservationFormClose}
                movie={selectedMovie}
            />
        </>
    )
}

export default Movies;