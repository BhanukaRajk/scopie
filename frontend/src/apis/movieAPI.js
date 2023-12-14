import api from "./api";

// GET ALL THE MOVIES FROM THE LIST
export const getMovies = () => {
    return api.get("/movies");
}

// GET MOVIE BY USING FILTERS
export const getMovie = (filters) => {
    return api.get("/movies/", { params: filters });
}

// GET THE INFO ABOUT LOCATIONS OF MOVIES/CINEMA
export const getLocations = (location) => {
    return api.get("/movies/locations", { params: location });
}