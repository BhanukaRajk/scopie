// eslint-disable-next-line no-unused-vars
import React, { useEffect, useState } from "react";
import PropTypes from "prop-types";
import { newReservation, checkSeatAvailability } from "../../../apis/reservationAPI";
import { getMovie } from "../../../apis/movieAPI";
import { message, Select, DatePicker, InputNumber } from "antd";
import SeatPicker from "./SeatPicker";
import useUser from "../../../hooks/useUser";

const AddReservationForm = ({ movie, onReservationFormClose, isFormOpen }) => {
    const { user } = useUser();

    const [messageApi, contextHolder] = message.useMessage();
    const [isSelectionOpen, setSelectionBoxVisible] = useState(false);

    const [selectedCinema, setSelectedCinema] = useState(null);
    const [selectedTimeSlot, setSelectedTimeSlot] = useState(null);

    const [seatAvailability, setSeatAvailability] = useState([]);
    const [movieAvailability, setMovieAvailability] = useState([]);
    const [seatSelection, setSeatSelection] = useState([]);

    const [bookingReq, setBookingReq] = useState({
        movieId: null,
        cinemaId: null,
        timeSlotId: null,
        seatCount: null,
        movieDate: null,
    });
    const [reservationData, setReservationData] = useState({
        userName: user,
        movieId: null,
        cinemaId: null,
        timeSlotId: null,
        seatSelection: [],
        movieDate: null,
    });

    useEffect(() => {

        setBookingReq({
            ...bookingReq,
            movieId: movie.movieId,
            cinemaId: null,
            timeSlotId: null,
        });
        setReservationData({
            ...reservationData,
            userName: user,
            movieId: movie.movieId,
        });

        const fetchMovie = async () => {
            try {
                const response = await getMovie({ movieId: movie.movieId });
                setMovieAvailability(response.data.movieShows);
            } catch (error) {
                console.error("Error fetching movie:", error);
            }
        };
        fetchMovie();
    }, [movie.movieId]);

    const onSelectionOpen = () => {
        setSelectionBoxVisible(true);
    };

    const onSelectionClose = () => {
        setSelectionBoxVisible(false);
    };

    const handleReservation = async () => {
        // event.preventDefault();

        setReservationData({
            ...reservationData,
            seatSelection: seatSelection,
        });

        console.log(reservationData); // TODO: REMOVE THIS

        try {
            const response = await newReservation(reservationData);
            console.log(response);
            messageApi.open({
                type: "success",
                content: "Success!"
            })
        } catch (error) {
            console.error("Error making reservation:", error);
            messageApi.open({
                type: "error",
                content: "Error!"
            })
        }
    };

    const checkAvailability = async (event) => {
        event.preventDefault();
        if (
            bookingReq.movieId == null ||
            bookingReq.movieId == "" ||
            bookingReq.cinemaId == null ||
            bookingReq.cinemaId == "" ||
            bookingReq.timeSlotId == null ||
            bookingReq.timeSlotId == "" ||
            bookingReq.movieDate == null ||
            bookingReq.movieDate == "" ||
            bookingReq.seatCount == null ||
            bookingReq.seatCount == 0
        ) {
            messageApi.open({
                type: "warning",
                content: "All the fields must be filled!"
            })
        } else {
            try {
                const availability = await checkSeatAvailability(bookingReq);
                setSeatAvailability(availability.data);
                console.log(seatAvailability.length); // TODO: REMOVE THIS

                if (seatAvailability != null && seatAvailability.length > 0) {
                    console.log(availability.data); // TODO: REMOVE THIS
                    onSelectionOpen();
                    messageApi.open({
                        type: "info",
                        content: "Great! Pick your seats..",
                    });
                } else {
                    messageApi.open({
                        type: "warning",
                        content: "No seats available!",
                    });
                }
            } catch (error) {
                console.error("Error checking availability:", error);
                messageApi.open({
                    type: "error",
                    content: "Could not get seat data!",
                });
            }
        }
    };

    const filteredCinemas = movieAvailability.map((cinema) => ({
        value: cinema.id,
        label: cinema.name,
    }));

    const filteredTimeSlots = movieAvailability
        .find((cinema) => cinema.id === bookingReq.cinemaId)
        ?.timeSlots.map((timeSlot) => ({
            value: timeSlot.slotId,
            label: timeSlot.startTime,
        })) || [];

    return (
        <>
            {contextHolder}
            <div
                className={`fixed top-0 bottom-0 left-0 right-0 ${isFormOpen ? "fixed" : "hidden"
                    } flex flex-col justify-center items-center bg-black bg-opacity-70`}
            >
                <div className="bg-white border text-black border-gray-300 w-full sm:w-96 py-8 flex items-center flex-col rounded-md">
                    <div className="text-lg font-semibold">How many tickets?</div>
                    <form className="mt-8 w-10/12 flex flex-col">
                        <div className="mb-4">
                            <span className="font-semibold">Movie: </span>
                            {movie != null ? movie.title : "null"}
                        </div>
                        <div className="mb-4">
                            <DatePicker
                                style={{
                                    width: "100%",
                                    paddingBlock: "2.75%",
                                }}
                                onChange={(value) => {
                                    setBookingReq({
                                        ...bookingReq,
                                        movieDate: value.toJSON(),
                                    });
                                    setReservationData({
                                        ...reservationData,
                                        movieDate: value.toJSON(),
                                    });
                                    setSelectedCinema(value);
                                }}
                            />
                        </div>
                        <div className="mb-4">
                            <InputNumber
                                size="large"
                                min={1}
                                max={10}
                                onChange={(value) => {
                                    setBookingReq({
                                        ...bookingReq,
                                        seatCount: value,
                                    });
                                }}
                                placeholder="No. of seats"
                                style={{ width: "100%" }}
                            />
                        </div>
                        <div className="mb-4">
                            <Select
                                placeholder="Select Cinema"
                                value={selectedCinema}
                                onChange={(value) => {
                                    setBookingReq({
                                        ...bookingReq,
                                        cinemaId: value,
                                    });
                                    setReservationData({
                                        ...reservationData,
                                        cinemaId: value,
                                    });
                                    setSelectedCinema(value);
                                }}
                                size="large"
                                style={{
                                    width: "100%",
                                }}
                                options={filteredCinemas}
                            />
                        </div>
                        <div className="mb-4">
                            <Select
                                placeholder="Select Time"
                                value={selectedTimeSlot}
                                onChange={(value) => {
                                    setBookingReq({
                                        ...bookingReq,
                                        timeSlotId: value,
                                    });
                                    setReservationData({
                                        ...reservationData,
                                        timeSlotId: value,
                                    });
                                    setSelectedTimeSlot(value);
                                }}
                                size="large"
                                style={{
                                    width: "100%",
                                }}
                                options={filteredTimeSlots}
                            />
                        </div>
                        <button
                            type="button"
                            onClick={checkAvailability}
                            className="text-md text-center bg-yellow-400 hover:bg-yellow-200 hover:shadow-md border-none text-black hover:text-gray-700 py-2 mb-4 rounded font-semibold cursor-pointer"
                        >
                            Select seats
                        </button>
                        <div className="w-full text-center">
                            <button
                                type="reset"
                                className="text-black bg-none border-none text-sm cursor-pointer"
                                onClick={onReservationFormClose}
                            >
                                Cancel
                            </button>
                        </div>
                        <SeatPicker
                            isSelectionOpen={isSelectionOpen}
                            seatAvailability={seatAvailability}
                            onSelectionClose={onSelectionClose}
                            handleReservation={handleReservation}
                            setSeatSelection={setSeatSelection}
                        />
                    </form>
                </div>
            </div>
        </>
    );
};

export default AddReservationForm;

AddReservationForm.propTypes = {
    movie: PropTypes.object,
    onReservationFormClose: PropTypes.func,
    isFormOpen: PropTypes.bool,
};
