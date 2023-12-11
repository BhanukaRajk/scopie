// eslint-disable-next-line no-unused-vars
import React, { useState } from "react";
// import { newReservation, checkSeatAvailability } from "../../../apis/reservationAPI";
import { message, Select, DatePicker, InputNumber } from "antd";
// import SeatPicker from "./SeatPicker";

import useUser from "../../../hooks/useUser";


const AddReservationForm = () => {
    const { user } = useUser();
    // const [seatAvaialability, setSeatAvaialability] = useState([])
    const [reservationData, setReservationData] = useState({
        userName: user,
        movieId: null,
        cinemaId: null,
        seatSelection: null,
        movieDate: null,
    })
    // eslint-disable-next-line no-unused-vars
    const [messageApi, contextHolder] = message.useMessage();

    // const handleReservation = async (event) => {
    //     event.preventDefault();
    //     try {
    //         const response = newReservation(reservationData);
    //         console.log(response);
    //     } catch (e) {
    //         console.error(e);
    //     }
    // }

    // const checkAvailability = async (event) => {
    //     event.preventDefault();
    //     try {
    //         // TODO: CHECK THE SETA AVAIALABILITY BY SENDING THE MOVIE, CINEMA AND THE TIME SLOT WITH THE DATE
    //         const availability = checkSeatAvailability();
    //         if (availability != null) {
    //             setSeatAvaialability(availability);
    //             console.log("Avaialable");
    //         } else {
    //             console.log("Not available");
    //             messageApi.open({
    //                 type: 'warning',
    //                 content: 'No seats available!',
    //             });
    //         }
    //     } catch (e) {
    //         console.error(e);
    //     }
    // }

    const handleFormChange = (e) => {
        const { name, value } = e.target;
        setReservationData({ ...reservationData, [name]: value });
    }


    const [selectedItems, setSelectedItems] = useState([]);
    // const filteredOptions = seatAvaialability.filter((o) => !selectedItems.includes(o));


    return (
        <>
            {contextHolder}

            <div className="bg-white border text-black border-gray-300 w-96 py-8 flex items-center flex-col mb-3 rounded-md">
                <div className="text-lg">How many tickets?</div>
                <form className="mt-8 w-10/12 flex flex-col">
                    <div className="grid gap-6 mb-4 lg:grid-cols-2">
                        <div>
                            <DatePicker
                                style={{
                                    width: '100%',
                                    paddingBlock: '2.75%'
                                }}
                            />
                        </div>
                        <div>
                            <InputNumber size="large" min={1} max={10} defaultValue={3} placeholder="No. of seats" style={{ width: '100%' }} />
                        </div>
                    </div>
                    <div className="mb-4">
                        <Select
                            mode="multiple"
                            placeholder="Select Cinema"
                            value={selectedItems}
                            onChange={setSelectedItems}
                            style={{
                                width: '100%',
                            }}
                        // options={filteredOptions.map((item) => ({
                        //     value: item,
                        //     label: item,
                        // }))}
                        />
                    </div>
                    <div className="mb-4">
                        <Select
                            mode="multiple"
                            placeholder="Select Cinema"
                            value={selectedItems}
                            onChange={setSelectedItems}
                            style={{
                                width: '100%',
                            }}
                        // options={filteredOptions.map((item) => ({
                        //     value: item,
                        //     label: item,
                        // }))}
                        />
                    </div>
                    <button className=" text-md text-center bg-blue-700 hover:bg-blue-400 text-white hover:text-white py-2 rounded font-semibold cursor-pointer">
                        {/* <button onClick={checkAvailability} className=" text-md text-center bg-blue-700 hover:bg-blue-400 text-white hover:text-white py-2 rounded font-semibold cursor-pointer"> */}
                        Select seats
                    </button>

                    {/* <SeatPicker seatSpace={seatAvaialability} /> */}

                </form>
            </div>
        </>
    );
}

export default AddReservationForm;