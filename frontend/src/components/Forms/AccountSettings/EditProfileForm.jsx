// eslint-disable-next-line no-unused-vars
import React, { useState, useEffect } from "react";
import PropTypes from "prop-types";
import { message } from "antd";


import { getUserdata, editProfile } from "../../../apis/profileAPI";

const EditProfileForm = ({ onOpen, thisUser }) => {

    const [userData, setUserData] = useState({
        firstName: "",
        lastName: "",
        userName: "",
    });
    const [messageApi, contextHolder] = message.useMessage();

    useEffect(() => {
        const fetchUser = async () => {
            try {
                const response = await getUserdata({ userName: thisUser });
                setUserData(response.data);
            } catch {
                console.log("error");
            }
        }
        fetchUser();
    }, [thisUser]);

    const handleNameUpdate = async (event) => {
        event.preventDefault();
        if (userData.firstName === "" || userData.firstName === null) {
            messageApi.open({
                type: 'error',
                content: 'First name cannot be empty!',
            });
        } else {
            console.log(userData)
            try {
                const response = await editProfile(userData);
                console.log(response);
                messageApi.open({
                    type: 'success',
                    content: "Profile name successfully updated!",
                });

            } catch (error) {
                console.error(error)
            }
        }
    };

    const handleNameChange = (e) => {
        setUserData({ ...userData, userName: thisUser });

        const { name, value } = e.target;
        setUserData({ ...userData, [name]: value });
    };

    return (
        <>
            {contextHolder}

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
                    <button type="button" onClick={handleNameUpdate} className=" mt-2 text-md text-center bg-yellow-300 hover:bg-yellow-200 text-black hover:text-black py-2 rounded-lg font-semibold cursor-pointer border-none">
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
    thisUser: PropTypes.string.isRequired,
}