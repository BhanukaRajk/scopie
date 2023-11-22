// eslint-disable-next-line no-unused-vars
import React from "react";
import PropTypes from "prop-types";

const MovieCard = ({ movie }) => {
    return (
        <>
            <div className="flex w-full py-2 bg-white shadow-md rounded-md">
                <div>
                    <img className="w-20 h-20 rounded-md" src={movie.poster} alt={movie.title} />
                </div>
                <div className="flex flex-col">
                    <div className="w-full text-xl text-black">{movie.title}</div>
                    <div className="w-full text-md text-gray-700">{movie.description}</div>
                    <div className="w-full text-md">{movie.theater}</div>
                    <div className="w-full text-md">{movie.ticketPrice}</div>
                </div>
            </div>
        </>
    );
}

export default MovieCard;

MovieCard.propTypes = {
    movie: PropTypes.object,
    // movie: PropTypes.object.isRequired,
};