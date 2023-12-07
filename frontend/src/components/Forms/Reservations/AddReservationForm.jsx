// eslint-disable-next-line no-unused-vars
import React, { useState } from "react";
import { newReservation, checkSeatAvailability } from "../../../apis/reservationAPI";
import { message, Select } from "antd";
import SeatPicker from "./SeatPicker";

import useUser from "../../../hooks/useUser";


const AddReservationForm = () => {
    const [seatAvaialability, setSeatAvaialability] = useState([])
    const [reservationData, setReservationData] = useState({
        userName: user,
        movieId: null,
        cinemaId: null,
        seatSelection: null,
        movieDate: null,
    })
    const [messageApi, contextHolder] = message.useMessage();
    const { user } = useUser();

    const handleReservation = async (event) => {
        event.preventDefault();
        try {
            const response = newReservation(reservationData);
            console.log(response);
        } catch (e) {
            console.error(e);
        }
    }

    const checkAvailability = async (event) => {
        event.preventDefault();
        try {
            // TODO: CHECK THE SETA AVAIALABILITY BY SENDING THE MOVIE, CINEMA AND THE TIME SLOT WITH THE DATE
            const availability = checkSeatAvailability();
            if (availability != null) {
                setSeatAvaialability(availability);
                console.log("Avaialable");
            } else {
                console.log("Not available");
                messageApi.open({
                    type: 'warning',
                    content: 'No seats available!',
                });
            }
        } catch (e) {
            console.error(e);
        }
    }

    const handleFormChange = (e) => {
        const { name, value } = e.target;
        setReservationData({ ...reservationData, [name]: value });
    }


    const [selectedItems, setSelectedItems] = useState([]);
    const filteredOptions = seatAvaialability.filter((o) => !selectedItems.includes(o));


    return (
        <>
            {contextHolder}

            <div className="bg-white border text-black border-gray-300 w-96 py-8 flex items-center flex-col mb-3 rounded-md">
                <h1 className="text-black font-serif">Scopie</h1>
                <div className="text-lg">Create new account</div>
                <form onSubmit={handleReservation} className="mt-8 w-10/12 flex flex-col">
                    <div className="grid gap-6 mb-4 lg:grid-cols-2">
                        <div>
                            <input type="text" id="first_name" name="firstName" onChange={handleFormChange} className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 " placeholder="First Name*" />
                        </div>
                        <div>
                            <input type="text" id="last_name" name="lastName" onChange={handleFormChange} className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 " placeholder="Last Name" />
                        </div>
                    </div>
                    <div className="mb-4">
                        <input type="email" id="email" name="email" onChange={handleFormChange} className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 " placeholder="Email Address*" />
                    </div>
                    <div className="mb-4">
                        <input type="password" id="password" name="password" onChange={handleFormChange} className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 " placeholder="Password*" />
                    </div>
                    <div className="mb-4">
                        {/* <input type="password" id="confirm_password" name="confPassword" onChange={handleFormChange} className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 " placeholder="Confirm Password*" /> */}
                        <Select
                            mode="multiple"
                            placeholder="Select Cinema"
                            value={selectedItems}
                            onChange={setSelectedItems}
                            style={{
                                width: '100%',
                            }}
                            options={filteredOptions.map((item) => ({
                                value: item,
                                label: item,
                            }))}
                        />
                    </div>
                    <button onClick={checkAvailability} className=" text-md text-center bg-blue-700 hover:bg-blue-400 text-white hover:text-white py-2 rounded font-semibold cursor-pointer">
                        Select seats
                    </button>

                    <SeatPicker seatSpace={seatAvaialability} />

                </form>
            </div>
        </>
    );
}

export default AddReservationForm;