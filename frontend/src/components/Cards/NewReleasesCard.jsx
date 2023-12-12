// eslint-disable-next-line no-unused-vars
import React from "react";
import PropTypes from "prop-types";
import NoImage from "../../assets/empty.svg";

const NewReleasesCard = ({ movie }) => {
    return (
        <>
            <div className="flex flex-col gap-2 w-72 md:w-64 p-4 bg-zinc-100 shadow-md shadow-zinc-500 rounded-md">
                <div className="text-center">
                    <img className="w-full h-full rounded-md" src={movie.poster != null ? movie.poster : NoImage} alt={movie.title} />
                </div>
                <div className="flex flex-col gap-2">
                    <div className="w-full text-xl font-semibold text-center text-black">{movie.title}</div>
                    <div className="w-full text-center">
                        <button className="rounded-xl px-8 py-2 bg-yellow-400 hover:bg-black text-black hover:text-white transition-colors">Book now</button>
                    </div>
                </div>
            </div>
        </>
    );
}

export default NewReleasesCard;

NewReleasesCard.propTypes = {
    movie: PropTypes.object,
    key: PropTypes.number.isRequired,
};