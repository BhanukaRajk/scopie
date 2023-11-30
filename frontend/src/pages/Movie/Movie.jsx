// eslint-disable-next-line no-unused-vars
import React, { useState, useEffect } from "react";
import MovieCard from "../../components/Cards/MovieCard";
import { getMovies } from "../../apis/movieAPI";

const Movie = () => {
    const [movies, setMovies] = useState([]);
    useEffect(() => {
        const fetchMovies = async () => {
            try {
                const response = await getMovies();
                setMovies(response);
            } catch {
                console.log("error");
            }
        }
        fetchMovies();
    }), [];

    return (
        <>
            <div className="flex md:flex-col gap-3">
                {!movies ? "<div className='text-lg text-center'> No movies to show </div>" :
                    movies.map((movie) => (
                        <MovieCard movie={movie} key={movie.id} />
                    ))}
            </div>
        </>
    )
}

export default Movie;