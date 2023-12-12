// eslint-disable-next-line no-unused-vars
import React, { useState } from "react";
import PropTypes from "prop-types";

import { message } from "antd";

import { updatePassword } from "../../../apis/profileAPI";

const UpdatePasswordForm = ({ onClose, user }) => {

    const [passwords, setPasswords] = useState({
        userName: "",
        oldPassword: "",
        newpassword: "",
        confPassword: ""
    });

    const [messageApi, contextHolder] = message.useMessage();

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

            <div className="fixed left-0 right-0 bottom-0 top-0 z-40 opacity-30 bg-black"></div>
            <div className="fixed top-16 bottom-0 right-0 left-0 z-40 flex flex-col items-center justify-center">

                <div className="bg-white border border-gray-300 w-80 py-8 flex items-center flex-col mb-3 rounded-md">
                    <div className="text-black font-semibold text-lg">Change Password</div>
                    <form
                        onSubmit={handleChangePassword}
                        className="mt-4 w-64 flex flex-col">
                        <div className="mb-4">
                            <label htmlFor="old_password" className="text-black text-xs font-semibold">Current password <span className=" text-red-600">*</span></label>
                            <input
                                type="password"
                                id="old_password"
                                name="oldPassword"
                                onChange={handlePasswordInputChange}
                                className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2"
                                // placeholder="Current Password*"
                                required />
                        </div>
                        <div className="mb-3">
                            <label htmlFor="new_password" className="text-black text-xs font-semibold">New password <span className=" text-red-600">*</span></label>
                            <input
                                type="password"
                                id="new_password"
                                name="newPassword"
                                onChange={handlePasswordInputChange}
                                className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2"
                                // placeholder="New Password*"
                                required />
                        </div>
                        <div className="mb-4">
                            <label htmlFor="confirm_password" className="text-black text-xs font-semibold">Confirm new password <span className=" text-red-600">*</span></label>
                            <input
                                type="password"
                                id="confirm_password"
                                name="confPassword"
                                onChange={handlePasswordInputChange}
                                className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2 "
                                // placeholder="Confirm New Password*"
                                required />
                        </div>
                        <button type="submit" className=" text-md text-center bg-yellow-400 hover:bg-yellow-200 border-none text-black hover:text-black py-2 rounded-lg font-semibold cursor-pointer">
                            Change Password
                        </button>
                        <a onClick={onClose} className="text-sm text-center text-black hover:text-yellow-600 font-semibold mt-4 cursor-pointer">Cancel</a>

                    </form>
                </div>
            </div>
        </>
    );
}

export default UpdatePasswordForm;

UpdatePasswordForm.propTypes = {
    user: PropTypes.string.isRequired,
    onClose: PropTypes.func.isRequired
}