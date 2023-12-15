// eslint-disable-next-line no-unused-vars
import React, { useState } from "react";
import PropTypes from "prop-types";
import { verifyEmail } from "../../../apis/authAPI";
import { message } from "antd";
import { NavLink } from "react-router-dom";
import LOGO from "../../../assets/logo_white_nbg.png"

const ForgotPasswordEmailForm = ({ onOpen }) => {
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
                if (response.data.success != null) {
                    messageApi.open({
                        type: 'success',
                        content: 'You will receive an email shortly!',
                    });
                    sessionStorage.setItem("email", email);
                    onOpen();
                } else if (response.data.error != null) {
                    messageApi.open({
                        type: 'error',
                        content: response.data.error,
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

            <div className="bg-white border border-gray-300 w-80 py-8 flex items-center flex-col mb-3 rounded-lg">
                <img className="rounded-md" style={{ height: "4rem" }} src={LOGO} alt={"Scopie"} />
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
                    <button type="submit" className=" text-md text-center bg-yellow-400 hover:bg-yellow-200 text-black hover:text-black hover:shadow-md border-none py-2 rounded-lg font-semibold cursor-pointer">
                        Proceed
                    </button>
                </form>
                <NavLink to="/login" className="text-sm text-yellow-700 hover:text-yellow-400 mt-4 cursor-pointer">Back to login</NavLink>
            </div>

        </>
    );
}

export default ForgotPasswordEmailForm;

ForgotPasswordEmailForm.propTypes = {
    onOpen: PropTypes.func.isRequired,
}