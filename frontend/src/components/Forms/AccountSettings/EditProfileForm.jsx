// eslint-disable-next-line no-unused-vars
import React, { useState, useEffect } from "react";
import PropTypes from "prop-types";

import { getUserdata } from "../../../apis/profileAPI";

const EditProfileForm = ({ onOpen, nuser }) => {

    const [userData, setUserData] = useState({
        firstName: "",
        lastName: "",
        userName: "",
    });

    useEffect(() => {
        const fetchUser = async () => {
            try {
                const response = await getUserdata();
                setUserData(response.data);
            } catch {
                console.log("error");
            }
        }
        fetchUser();
    }, []);

    const handleNameChange = (e) => {
        setUserData({ ...userData, userName: nuser });

        const { name, value } = e.target;
        setUserData({ ...userData, [name]: value });
    };

    return (
        <>
            {/* {contextHolder} */}

            <div className="bg-white border border-gray-300 w-80 py-8 flex items-center flex-col mb-3 rounded-lg">
                <div className="text-black text-lg font-semibold">Edit profile name</div>
                <form className="mt-4 w-64 flex flex-col">
                    <div className="mb-4">
                        <label htmlFor="first_name" className="text-black text-xs font-semibold">First name <span className=" text-red-600">*</span></label>
                        <input
                            type="text"
                            id="first_name"
                            name="firstName"
                            value={userData.firstName}
                            onChange={handleNameChange}
                            className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 "
                            placeholder="Tharanga"
                        />
                    </div>
                    <div className="mb-4">
                        <label htmlFor="last_name" className="text-black text-xs font-semibold">Last name</label>
                        <input
                            type="text"
                            id="last_name"
                            name="lastName"
                            value={userData.lastName}
                            onChange={handleNameChange}
                            className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 "
                            placeholder="Gamage (Optional)"
                        />
                    </div>
                    <button type="button" onClick={onOpen} className=" mt-2 text-md text-center bg-black hover:bg-gray-800 text-white hover:text-ehite py-2 rounded-lg font-semibold cursor-pointer border-none">
                        Change Password
                    </button>
                    <button type="submit" className=" mt-2 text-md text-center bg-yellow-300 hover:bg-yellow-200 text-black hover:text-black py-2 rounded-lg font-semibold cursor-pointer border-none">
                        Update
                    </button>
                </form>
            </div>
        </>
    );
}

export default EditProfileForm;

EditProfileForm.propTypes = {
    onOpen: PropTypes.func.isRequired,
    nuser: PropTypes.string.isRequired,
}