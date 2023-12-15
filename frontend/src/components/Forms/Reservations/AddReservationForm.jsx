// eslint-disable-next-line no-unused-vars
import React, { useEffect, useState } from "react";
import PropTypes from "prop-types";
import { newReservation, checkSeatAvailability } from "../../../apis/reservationAPI";
import { getMovie } from "../../../apis/movieAPI";
import { message, Select, DatePicker, InputNumber } from "antd";
import SeatPicker from "./SeatPicker";
import useUser from "../../../hooks/useUser";
import moment from 'moment';

const AddReservationForm = ({ movie, onReservationFormClose, isFormOpen }) => {
    const { user } = useUser();

    const [messageApi, contextHolder] = message.useMessage();
    const [isSelectionOpen, setSelectionBoxVisible] = useState(false);

    const [selectedCinema, setSelectedCinema] = useState(null);
    const [selectedTimeSlot, setSelectedTimeSlot] = useState(null);

    const [seatAvailability, setSeatAvailability] = useState([]);
    const [movieAvailability, setMovieAvailability] = useState([]);
    // const [seatSelection, setSeatSelection] = useState([]);

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
            if (movie.movieId != null) {
                try {
                    const response = await getMovie({ movieId: movie.movieId });
                    setMovieAvailability(response.data.movieShows);
                } catch (error) {
                    console.error("Error fetching movie:", error);
                }
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

    const handleReservation = async (Selections) => {

        setReservationData({
            ...reservationData,
            seatSelection: Selections,
        });

        console.log(reservationData)
        if (reservationData.seatSelection != null) {
            try {
                const response = await newReservation(reservationData);
                console.log(response);
                messageApi.open({
                    type: "success",
                    content: "Seat reservation success!"
                }) 
                onCancel();
            } catch (error) {
                console.error("Error making reservation:", error);
                messageApi.open({
                    type: "error",
                    content: "Seat resrvation failed!"
                })
            }
        } else {
            messageApi.open({
                type: "warning",
                content: "Could not catch the selection!"
            })
        }
    };

    const checkAvailability = async (event) => {
        console.log(bookingReq)
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
            bookingReq.seatCount == null
        ) {
            messageApi.open({
                type: "warning",
                content: "All the fields must be filled!"
            })
        } else if (
            bookingReq.seatCount == 0
        ) {
            messageApi.open({
                type: "warning",
                content: "Enter atleast one or more seats!"
            })

        } else {
            try {
                const availability = await checkSeatAvailability(bookingReq);
                setSeatAvailability(availability.data);
                await messageApi.open({
                    type: "loading",
                    content: "Please wait..",
                });
                console.log(availability.data)

                if (availability.data != null && availability.data.length > 0) {
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

    const onCancel = () => {
        setSelectedCinema(null);
        setSelectedTimeSlot(null);

        setBookingReq({
            movieId: null,
            cinemaId: null,
            timeSlotId: null,
            seatCount: null,
            movieDate: null,
        });

        setReservationData({
            userName: user,
            movieId: null,
            cinemaId: null,
            timeSlotId: null,
            seatSelection: [],
            movieDate: null,
        });

        // CLOSE THE SEAT PICKER
        onSelectionClose();
        onReservationFormClose();
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
                    <div className="text-lg font-semibold">How many seats?</div>
                    <form className="mt-8 w-10/12 flex flex-col">
                        <div className="mb-4 w-full text-center text-xl italic font-light">
                            {movie != null ? movie.title : "null"}
                        </div>
                        <div className="mb-4">
                            <DatePicker
                                style={{
                                    width: "100%",
                                    paddingBlock: "2.75%",
                                }}
                                disabledDate={(current) => current && current < moment().startOf('day')}
                                onChange={(value) => {
                                    setBookingReq({
                                        ...bookingReq,
                                        movieDate: value ? value.toJSON() : null,
                                    });
                                    setReservationData({
                                        ...reservationData,
                                        movieDate: value ? value.toJSON() : null,
                                    });
                                }}
                                placeholder="Select Movie date (Required)"
                            />
                        </div>
                        <div className="mb-4">
                            <InputNumber
                                size="large"
                                min={1}
                                max={10}
                                value={bookingReq.seatCount}
                                onChange={(value) => {
                                    setBookingReq({
                                        ...bookingReq,
                                        seatCount: value,
                                    });
                                }}
                                placeholder="Seat count (Required)"
                                style={{ width: "100%" }}
                            />
                        </div>
                        <div className="mb-4">
                            <Select
                                placeholder="Select Cinema (Required)"
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
                                placeholder="Select Time (Required)"
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
                                onClick={onCancel}
                            >
                                Cancel
                            </button>
                        </div>
                        <SeatPicker
                            isSelectionOpen={isSelectionOpen}
                            seatAvailability={seatAvailability}
                            onSelectionClose={onSelectionClose}
                            handleReservation={handleReservation}
                        // setSeatSelection={setSeatSelection}
                        />
                    </form>
                </div>
            </div>
        </>
    );
};

export default AddReservationForm;

AddReservationForm.propTypes = {
    movie: PropTypes.any,
    onReservationFormClose: PropTypes.func,
    isFormOpen: PropTypes.bool,
};
