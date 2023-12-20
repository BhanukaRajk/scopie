// eslint-disable-next-line no-unused-vars
import React, { useState, useEffect } from "react";
import PropTypes from "prop-types";

const SeatPicker = ({
    isSelectionOpen,
    seatAvailability,
    closeSeatPicker,
    setSeatSelection,
}) => {
    const seatsPerRow = 10;
    const rows = Math.ceil(seatAvailability.length / seatsPerRow);

    const initialSeatsData = Array.from({ length: rows }, (_, rowIndex) =>
        Array.from({ length: seatsPerRow }, (_, seatIndex) => ({
            id: rowIndex * seatsPerRow + seatIndex + 1,
            status: seatAvailability[rowIndex * seatsPerRow + seatIndex] ? "available" : "picked",
        }))
    );

    const [seatsData, setSeatsData] = useState(initialSeatsData);

    useEffect(() => {
        const updatedSeatsData = Array.from({ length: rows }, (_, rowIndex) =>
            Array.from({ length: seatsPerRow }, (_, seatIndex) => ({
                id: rowIndex * seatsPerRow + seatIndex + 1,
                status: seatAvailability[rowIndex * seatsPerRow + seatIndex] ? "available" : "picked",
            }))
        );
        setSeatsData(updatedSeatsData);
    }, [seatAvailability, rows, seatsPerRow]);

    const handleSeatClick = (seat) => {
        if (seat.status === "available") {
            const updatedSeatsData = seatsData.map((row) =>
                row.map((s) => (s.id === seat.id ? { ...s, status: "selected" } : s))
            );
            setSeatsData(updatedSeatsData);
        } else if (seat.status === "selected") {
            const updatedSeatsData = seatsData.map((row) =>
                row.map((s) => (s.id === seat.id ? { ...s, status: "available" } : s))
            );
            setSeatsData(updatedSeatsData);
        }
    };

    const pickSelection = () => {

        let i = 1;
        let Selections = [];
        seatsData.map((row) => {
            row.map((s) => {
                if (s.status == "selected") {
                    Selections.push(i);
                }
                i++;
            })
        });
        setSeatSelection(Selections);
    }

    return (
        <div
            className={`top-14 bottom-0 left-0 right-0 ${isSelectionOpen ? "fixed" : "hidden"
                } flex flex-col justify-center items-center bg-black bg-opacity-70`}
        >
            <div className={` ${isSelectionOpen ? "fixed" : "hidden"} top-1/2 left-1/2 transform -translate-x-1/2 -translate-y-1/2 bg-white p-8 border border-gray-300 shadow-md rounded-md`}>
                <div className="w-full text-black text-center mb-4">
                    <h2 className="text-lg font-bold">Select Your Seats</h2>
                </div>
                <div className="py-1 mb-10 text-black text-center border-b-2 border-b-black w-full">
                    Screen
                </div>
                <div className="flex flex-col">
                    {seatsData.map((row, rowIndex) => (
                        <div key={`row-${rowIndex}`} className="flex mb-2">
                            {row.map((seat) => (
                                <div
                                    key={seat.id}
                                    className={`w-8 h-8 mr-2 flex items-center justify-center cursor-pointer text-gray-700 border-x-4 border-b-4 border-gray-500 rounded ${seat.status === "picked" ? "bg-red-300" : seat.status === "selected" ? "bg-green-300" : "bg-gray-200"
                                        }`}
                                    onClick={() => handleSeatClick(seat)}
                                >
                                    {seat.status !== "picked" && seat.id}
                                </div>
                            ))}
                        </div>
                    ))}
                </div>
                <div className="flex justify-between items-center gap-3 mt-3">
                    <button
                        type="button"
                        className="bg-black text-white w-24 py-1 rounded border-none hover:bg-gray-700"
                        onClick={closeSeatPicker}
                    >
                        Cancel
                    </button>
                    <button
                        type="button"
                        className="text-black bg-yellow-400 w-24 py-1 rounded border-none hover:bg-yellow-200 hover:shadow-md"
                        onClick={pickSelection}
                    >
                        Reserve
                    </button>
                </div>
            </div>
        </div>
    );
};

export default SeatPicker;

SeatPicker.propTypes = {
    seatAvailability: PropTypes.arrayOf(PropTypes.bool),
    closeSeatPicker: PropTypes.func,
    setSeatSelection: PropTypes.func,
    isSelectionOpen: PropTypes.bool,
};