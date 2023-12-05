// eslint-disable-next-line no-unused-vars
import React, { useEffect, useState } from "react";
import { useNavigate, NavLink } from "react-router-dom";

import { message } from "antd";

import { updatePassword } from "../../../apis/profileAPI";
import useUser from "../../../hooks/useUser";

const UpdatePasswordForm = () => {

    const { user } = useUser();

    const [passwords, setPasswords] = useState({
        userName: "",
        oldPassword: "",
        password: "",
        confPassword: ""
    });

    const [messageApi, contextHolder] = message.useMessage();
    const navigate = useNavigate();


    useEffect(() => {
        if (passwords.userName === null || passwords.userName === "") {
            navigate("/login");
        }
    }, [passwords.userName, navigate]);

    const handlePasswordInputChange = (e) => {
        if (passwords.userName === null || passwords.userName === "") {
            setPasswords({ ...passwords, userName: user });
        }
        const { name, value } = e.target;
        setPasswords({ ...passwords, [name]: value });
    };

    const handleChangePassword = async (event) => {
        event.preventDefault();
        if (
            passwords.password === "" || passwords.password === null ||
            passwords.confPassword === "" || passwords.confPassword === null ||
            passwords.password.length < 8
        ) {
            messageApi.open({
                type: 'error',
                content: 'Your password must be 8 characters or more long!',
            });
        } else if (passwords.password !== passwords.confPassword) {
            messageApi.open({
                type: 'error',
                content: 'Passwords do not match!',
            });
        } else {
            try {
                console.log(passwords);
                const response = await updatePassword(passwords);
                if (response.data.error != null) {
                    messageApi.open({
                        type: 'error',
                        content: response.data.error
                    })
                } else {
                    await messageApi.open({
                        type: 'success',
                        content: 'Password changed!!',
                    });
                    sessionStorage.removeItem("email");
                    navigate("/login");
                }
            } catch (error) {
                console.error(error);
                messageApi.open({
                    type: 'error',
                    content: 'Password resetting process failed!',
                });
            }
        }

    }
    return (
        <>
            {contextHolder}

            <div className="bg-white border border-gray-300 w-80 py-8 flex items-center flex-col mb-3 rounded-md">
                <h1 className="text-black font-serif">Scopie</h1>
                <form onSubmit={handleChangePassword} className="mt-4 w-64 flex flex-col">
                    <div className="mb-4">
                        <input
                            type="password"
                            id="old_password"
                            name="oldPassword"
                            onChange={handlePasswordInputChange}
                            className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 "
                            placeholder="Current Password"
                            required />
                    </div>
                    <div className="mb-4">
                        <input
                            type="password"
                            id="new_password"
                            name="newPassword"
                            onChange={handlePasswordInputChange}
                            className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 "
                            placeholder="New Password"
                            required />
                    </div>
                    <div className="mb-4">
                        <input
                            type="password"
                            id="confirm_password"
                            name="confPassword"
                            onChange={handlePasswordInputChange}
                            className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 "
                            placeholder="Confirm New Password"
                            required />
                    </div>
                    <button type="submit" className=" text-md text-center bg-blue-700 hover:bg-blue-400 text-white hover:text-white py-2 rounded-lg font-semibold cursor-pointer">
                        Change Password
                    </button>
                </form>
                <NavLink to="/login" className="text-sm text-blue-900 mt-4 cursor-pointer">Back to login</NavLink>
            </div>
        </>
    );
}

export default UpdatePasswordForm;