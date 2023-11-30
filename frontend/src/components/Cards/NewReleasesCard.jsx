// eslint-disable-next-line no-unused-vars
import React from "react";
import PropTypes from "prop-types";

const NewReleasesCard = ({ movie }) => {
    return (
        <>
            <div className="flex md:flex-col gap-2 w-72 md:w-64 p-4 bg-gray-300 shadow-md rounded-md">
                <div className="text-center">
                    <img className="w-20 h-20 rounded-md" src={movie.poster} alt={movie.title} />
                </div>
                <div className="flex flex-col">
                    <div className="w-full text-xl text-black">{movie.title}</div>
                    <div className="w-full text-md text-gray-700">{movie.description}</div>
                </div>
            </div>
        </>
    );
}

export default NewReleasesCard;

NewReleasesCard.propTypes = {
    movie: PropTypes.object,
};