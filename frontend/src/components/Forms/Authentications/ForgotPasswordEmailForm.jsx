// eslint-disable-next-line no-unused-vars
import React, { useState } from "react";
import PropTypes from "prop-types";

import { verifyEmail } from "../../../apis/authAPI";

import { message } from "antd";
import { NavLink } from "react-router-dom";

// eslint-disable-next-line no-unused-vars
const ForgotPasswordEmailForm = ({ onOpen, onClose }) => {
    const [email, setVerifyEmail] = useState("");
    const [messageApi, contextHolder] = message.useMessage();

    const handleEmailInput = async (event) => {
        event.preventDefault();

        if (email === "" || email === null) {
            messageApi.open({
                type: 'error',
                content: 'Please enter a valid email address!',
            });
        } else {
            try {
                messageApi.open({
                    type: 'loading',
                    content: 'Processing...',
                    duration: 2,
                });

                const response = await verifyEmail(email);

                if (response.status === 400) {
                    messageApi.open({
                        type: 'error',
                        content: 'Please enter a valid email address!',
                    });
                } else if (response.status === 404) {
                    messageApi.open({
                        type: 'error',
                        content: 'Username does not exist!',
                    });
                } else if (response.status === 202) {
                    sessionStorage.setItem("email", email);
                    onOpen();
                    messageApi.open({
                        type: 'success',
                        content: 'You will receive an email shortly!',
                    });
                } else {  // VERIFICATION CODE SENDING FAILED
                    messageApi.open({
                        type: 'error',
                        content: response.body, // USE DATA INSTEAD OF BODY
                    });
                }
            } catch (error) {
                console.error(error);
                messageApi.open({
                    type: 'error',
                    content: 'Something went wrong!',
                });
            }
        }
    }

    return (
        <>
            {contextHolder} {/* FOR THE MESSAGE COMPONENT */}

            <div className="bg-white border border-gray-300 w-80 py-8 flex items-center flex-col mb-3 rounded-md">
                <h1 className="text-black font-serif">Scopie</h1>
                <form onSubmit={handleEmailInput} className="mt-4 w-64 flex flex-col">
                    <div className="w-full text-center text-sm mb-3 text-gray-700">
                        Enter your email address. We will send a verification code to your email shortly.<br />
                    </div>
                    <div className="mb-4">
                        <input
                            type="email"
                            id="email"
                            name="userEmail"
                            className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 "
                            placeholder="Email Address"
                            onChange={(event) => setVerifyEmail(event.target.value)}
                            required />
                    </div>
                    <button type="submit" className=" text-md text-center bg-blue-700 hover:bg-blue-400 text-white hover:text-white py-2 rounded-lg font-semibold cursor-pointer">
                        Proceed
                    </button>
                </form>
                <NavLink to="/login" className="text-sm text-blue-900 mt-4 cursor-pointer">Back to login</NavLink>
            </div>

        </>
    );
}

export default ForgotPasswordEmailForm;

ForgotPasswordEmailForm.propTypes = {
    onOpen: PropTypes.func.isRequired,
    onClose: PropTypes.func.isRequired,
}