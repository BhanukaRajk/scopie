// eslint-disable-next-line no-unused-vars
import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import PropTypes from "prop-types";

import { signUp } from "../../../apis/authAPI";

import {
    message,
} from "antd";

// eslint-disable-next-line no-unused-vars
const EmailVerificationForm = ({ isOpen, onClose }) => {

    const [verificationData, setVerificationData] = useState({
        firstName: "",
        lastName: "",
        password: "",
        email: "",
        otp: "",
    });
    const [messageApi, contextHolder] = message.useMessage();
    const navigate = useNavigate();

    const handleInputChange = (e) => {
        setVerificationData(() => ({
            ...verificationData,
            firstName: sessionStorage.getItem("firstName"),
            lastName: sessionStorage.getItem("lastName"),
            password: sessionStorage.getItem("password"),
            email: sessionStorage.getItem("email"),
            otp: e.target.value,
        }));
    };
    
    const handleOneTimePasswordInput = async (event) => {
        event.preventDefault();
        if (verificationData.otp.length != 6) {
            messageApi.open({
                type: 'error',
                content: 'Invalid code length!',
            });
        } else {
            try {
                const response = await signUp(verificationData);
                console.log(response) // TODO: REMOVE THIS LINE
                await messageApi.open({
                    type: 'success',
                    content: 'Your account has been created!',
                });
                sessionStorage.removeItem("user_data");
                onClose();
                navigate("/login");
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
                            <button type="submit" className=" text-md text-center bg-yellow-400 hover:bg-yellow-200 text-black hover:text-black hover:shadow-md border-none py-2 rounded-lg font-semibold cursor-pointer">
                                Verify
                            </button>
                        </form>
                        <a className="text-black mt-4 text-sm cursor-pointer" onClick={onClose}>Cancel</a>
                    </div>

                </div>
            </div>
        </>
    );
}

export default EmailVerificationForm;

EmailVerificationForm.propTypes = {
    isOpen: PropTypes.bool.isRequired,
    onClose: PropTypes.func.isRequired,
}