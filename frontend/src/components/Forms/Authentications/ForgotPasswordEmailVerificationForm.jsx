// eslint-disable-next-line no-unused-vars
import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import PropTypes from "prop-types";

import { verifyUser, resendCode } from "../../../apis/authAPI";

import {
    message,
} from "antd";

// eslint-disable-next-line no-unused-vars
const ForgotPasswordEmailVerificationForm = ({ isOpen, onClose }) => {

    const navigate = useNavigate();

    const [verificationData, setVerificationData] = useState({
        email: "",
        otp: "",
    });

    const [messageApi, contextHolder] = message.useMessage();

    const handleInputChange = (e) => {
        setVerificationData(() => ({
            ...verificationData, email: sessionStorage.getItem("email"), otp: e.target.value,
        }));
    };

    const handleResend = async (resend) => {
        resend.preventDefault();
        messageApi.open({
            type: 'loading',
            content: 'Processing...',
            duration: 2,
        });
        try {
            await resendCode(sessionStorage.getItem("email"));
            messageApi.open({
                type: 'success',
                content: 'You will receive an email shortly!',
            });
        } catch (error) {
            messageApi.open({
                type: 'error',
                content: error.message,
            });
        }
    }

    const handleOneTimePasswordInput = async (event) => {
        event.preventDefault();
        if (verificationData.otp.length != 6) {
            messageApi.open({
                type: 'error',
                content: 'Invalid code length!',
            });
        } else {
            try {
                console.log(verificationData);
                const response = await verifyUser(verificationData);
                if (response.data.error != null) {
                    messageApi.open({
                        type: 'error',
                        content: response.data.error
                    })
                } else {
                    await messageApi.open({
                        type: 'success',
                        content: "Your email has been verified!"
                    })
                    onClose();
                    navigate("/forgot-password/add-new-password")
                }



            } catch (error) {
                console.error(error);
                messageApi.open({
                    type: 'error',
                    content: 'You have entered a wrong code!',
                });
            }
        }

    }

    return (
        <>
            {contextHolder}

            <div className={`absolute  ${isOpen ? "block" : "hidden"}`}>

                <div className="fixed left-0 right-0 bottom-0 top-0 z-40 opacity-30 bg-black"></div>
                <div className="fixed top-16 bottom-0 right-0 left-0 z-40 flex flex-col items-center justify-center">

                    <div className="bg-white border border-gray-300 z-50 w-80 p-8 flex items-center flex-col mb-3 rounded-lg shadow-lg">
                        <form onSubmit={handleOneTimePasswordInput} className=" w-64 flex flex-col text-center">
                            <div className="w-full mb-2 text-xl text-black">Enter verification code</div>
                            <div className="w-full text-sm text-gray-700">
                                We have sent a verification code to your email.
                                Please check your inbox and enter the received verification code here.
                                If you could not find the code check your spam or try to get a new code.
                            </div>
                            <div className="flex justify-center my-4">
                                <input
                                    className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 "
                                    id="otp"
                                    name="otp"
                                    placeholder="One Time Passcode"
                                    type="number"
                                    onChange={handleInputChange}
                                />
                            </div>
                            <button type="submit" className=" text-md text-center bg-blue-700 hover:bg-blue-400 text-white hover:text-white py-2 rounded-lg font-semibold cursor-pointer">
                                Verify
                            </button>
                        </form>
                        <div className=" text-xs text-black my-2">
                            Didn&apos;t get the code? <a onClick={handleResend} className=" text-blue-900 mt-4 cursor-pointer">Resend</a>
                        </div>
                        <a className="text-black mt-4 text-sm cursor-pointer" onClick={onClose}>Cancel</a>
                    </div>

                </div>
            </div>
        </>
    );
}

export default ForgotPasswordEmailVerificationForm;

ForgotPasswordEmailVerificationForm.propTypes = {
    enteredEmail: PropTypes.string,

    isOpen: PropTypes.bool.isRequired,
    onClose: PropTypes.func.isRequired,
}