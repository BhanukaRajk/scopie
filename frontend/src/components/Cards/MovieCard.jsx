// eslint-disable-next-line no-unused-vars
import React from "react";
import PropTypes from "prop-types";
import { useNavigate } from "react-router-dom"

import NoImage from "../../assets/empty.svg"

const MovieCard = ({ movie }) => {

    const navigate = useNavigate();

    const handleClickOnMovie = (id) => {
		navigate(`/movies/${id}`);
	}

    return (
        <>
            <div className="flex flex-col gap-2 w-72 md:w-64 p-4 bg-zinc-100 shadow-md shadow-zinc-500 rounded-md">
                <div className="text-center">
                    <img className="rounded-md" style={{ width: '14rem', height: '18rem', objectFit: 'cover' }} src={movie.banner != null ? movie.banner : NoImage} alt={movie.title} />
                </div>
                <div className="flex flex-col gap-2">
                    <div className="w-full text-xl font-semibold text-center text-black">{movie.title}</div>
                    <div className="w-full text-center">
                        <button onClick={() => handleClickOnMovie(movie.movieId)} className="rounded-xl px-6 py-1 bg-yellow-400 hover:bg-black text-black hover:text-white transition-colors">Book now</button>
                    </div>
                </div>
            </div>
        </>
    );
}

export default MovieCard;

MovieCard.propTypes = {
    movie: PropTypes.object,
    key: PropTypes.number.isRequired,
};