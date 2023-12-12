// eslint-disable-next-line no-unused-vars
import React, { useState, useEffect } from "react";
import { Empty } from "antd";
import MovieCard from "../../components/Cards/MovieCard";
import { getMovies } from "../../apis/movieAPI";

const Movies = () => {
    const [movies, setMovies] = useState([]);
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

    return (
        <>
            <div className="flex flex-col mx-3 my-2">
                <div className="my-3 text-lg text-black w-full text-center font-sans font-semibold">Movies</div>
                <div className="border border-gray-300 w-full my-4"></div>
                <div className="flex flex-col md:flex-row flex-wrap gap-4 w-full">
                {movies.length === 0 ?
                    <Empty
                        image={Empty.PRESENTED_IMAGE_SIMPLE}
                        imageStyle={{ height: 90 }}
                        description={<div className=" text-base">No movies to show</div>}
                    /> :
                    movies.map((movie) => (
                        <MovieCard movie={movie} key={movie.movieId} />
                    ))}
                </div>
            </div>
        </>
    )
}

export default Movies;