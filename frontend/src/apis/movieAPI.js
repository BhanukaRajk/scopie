import api from "./api";

// GET ALL THE MOVIES FROM THE LIST
export const getMovies = () => {
    return api.get("/movies");
}

// GET MOVIE BY USING FILTERS
export const getMovie = (movie) => {
    return api.get("/movies/", { params: movie });
}

// GET THE INFO ABOUT LOCATIONS OF MOVIES/CINEMA
export const getLocations = (location) => {
    return api.get("/movies/locations", { params: location });
}