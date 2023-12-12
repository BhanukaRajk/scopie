// eslint-disable-next-line no-unused-vars
import React, { useState, useEffect } from "react";
import MovieCard from "../../components/Cards/MovieCard";
import { getMovie } from "../../apis/movieAPI";

const Movies = () => {
    const [movie, setMovie] = useState([]);
    useEffect(() => {
        const fetchMovies = async () => {
            try {
                const response = await getMovie();
                setMovie(response);
            } catch {
                console.log("error");
            }
        }
        fetchMovies();
    }), [];

    return (
        <>
            <div className="h-screen w-screen bg-gray-50 flex flex-col justify-center items-center login-background">
                <div></div>
            </div>
        </>
    )
}

export default Movies;