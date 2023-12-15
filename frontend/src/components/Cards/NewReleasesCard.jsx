// eslint-disable-next-line no-unused-vars
import React from "react";
import PropTypes from "prop-types";
import NoImage from "../../assets/empty.svg";

const NewReleasesCard = ({ movie }) => {
    return (
        <>
            <div className="flex flex-col gap-2 w-64 p-4 bg-white shadow-md shadow-zinc-400 rounded-md">
                <div className="text-center">
                    <img className="rounded-md" style={{ width: '14rem', height: '18rem', objectFit: 'fill' }} src={movie.banner != null ? movie.banner : NoImage} alt={movie.title} />
                </div>
                <div className="flex flex-col gap-2">
                    <div className="w-full text-xl font-semibold text-center text-black">{movie.title}</div>
                </div>
            </div>
        </>
    );
}

export default NewReleasesCard;

NewReleasesCard.propTypes = {
    movie: PropTypes.object,
    identity: PropTypes.number,
};