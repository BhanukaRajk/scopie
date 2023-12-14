// eslint-disable-next-line no-unused-vars
import React, { useState, useEffect } from "react";
import { Empty } from "antd";
import MovieCard from "../../components/Cards/MovieCard";
import { getMovies } from "../../apis/movieAPI";
import AddReservationForm from "../../components/Forms/Reservations/AddReservationForm";

const Movies = () => {
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
            <div className="flex flex-col mx-3 py-5">
                <div className="my-2 text-2xl text-black w-full text-center font-semibold">Movies</div>
                <div className="border border-gray-500 w-full my-4"></div>
                <div className="flex flex-col items-center sm:items-start sm:flex-row flex-wrap gap-4 w-full">
                    {movies.length === 0 ?
                        <Empty
                            image={Empty.PRESENTED_IMAGE_SIMPLE}
                            imageStyle={{ height: 90 }}
                            description={<div className=" text-base">No movies to show</div>}
                        /> :
                        movies.map((movie, index) => (
                            <MovieCard
                                movie={movie}
                                key={index}
                                onReservationFormOpen={() => onReservationFormOpen(movie)}
                            />
                        ))}
                </div>
            </div>

            <AddReservationForm
                isFormOpen={isFormOpen}
                onReservationFormClose={onReservationFormClose}
                movie={selectedMovie}
            />
        </>
    )
}

export default Movies;