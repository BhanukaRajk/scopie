// eslint-disable-next-line no-unused-vars
import React, { useState } from "react";
import { NavLink } from "react-router-dom";
import PropTypes from "prop-types";
import { message } from "antd";
import { getVerificationCode } from "../../../apis/authAPI";
import LOGO from "../../../assets/logo_white_nbg.png"

const SignUpForm = ({ onOpen }) => {

    const [userData, setUserData] = useState({
        firstName: "",
        lastName: "",
        email: "",
        password: "",
        confPassword: "",
    });

    const [messageApi, contextHolder] = message.useMessage();

    const handleInputChange = (e) => {
        const { name, value } = e.target;
        setUserData({ ...userData, [name]: value });
    };

    const handleSignUp = async (event) => {
        event.preventDefault();
        if (userData.firstName === "" || userData.firstName === null) {
            messageApi.open({
                type: 'error',
                content: 'First name cannot be empty!',
            });
        } else if (userData.email === "" || userData.email === null) {
            messageApi.open({
                type: 'error',
                content: 'Email cannot be empty!',
            });
        } else if (
            userData.password === "" ||
            userData.password === null ||
            userData.confPassword === "" ||
            userData.confPassword === null
        ) {
            messageApi.open({
                type: 'error',
                content: 'Password cannot be empty!',
            });
        } else if (userData.password != userData.confPassword) {
            messageApi.open({
                type: 'error',
                content: 'Passwords does not match!',
            });
        } else {
            try {
                messageApi.open({
                    type: 'loading',
                    content: "Processing...",
                });
                const response = await getVerificationCode(userData);
                console.log(response);

                sessionStorage.setItem("firstName", userData.firstName);
                sessionStorage.setItem("lastName", userData.lastName);
                sessionStorage.setItem("email", userData.email);
                sessionStorage.setItem("password", userData.password);

                onOpen();

            } catch (error) {
                messageApi.open({
                    type: 'error',
                    content: error.response.data,
                });
            }
        }

    }

    return (
        <>
            {contextHolder}

            <div className="bg-white border text-black border-gray-300 w-96 py-5 flex items-center flex-col mb-3 rounded-lg">
                <img className="rounded-md" style={{ height: "4rem" }} src={LOGO} alt={"Scopie"} />
                <div className="text-md">Sign up</div>
                <form onSubmit={handleSignUp} className="mt-3 w-10/12 flex flex-col">
                    <div className="grid sm:gap-6 sm:grid-cols-2">
                        <div>
                            <label htmlFor="first_name" className="text-black text-xs font-semibold">First name <span className=" text-red-600">*</span></label>
                            <input type="text" id="first_name" name="firstName" onChange={handleInputChange} className="bg-gray-50 mb-3 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-1.5" />
                        </div>
                        <div>
                            <label htmlFor="last_name" className="text-black text-xs font-semibold">Last name</label>
                            <input type="text" id="last_name" name="lastName" onChange={handleInputChange} className="bg-gray-50 mb-3 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-1.5" />
                        </div>
                    </div>
                    <div className="mb-3">
                        <label htmlFor="email" className="text-black text-xs font-semibold">Email <span className=" text-red-600">*</span></label>
                        <input type="email" id="email" name="email" onChange={handleInputChange} className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-1.5" />
                    </div>
                    <div className="mb-3">
                        <label htmlFor="password" className="text-black text-xs font-semibold">Password <span className=" text-red-600">*</span></label>
                        <input type="password" id="password" name="password" onChange={handleInputChange} className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-1.5" />
                    </div>
                    <div className="mb-3">
                        <label htmlFor="confirm_password" className="text-black text-xs font-semibold">Confirm password <span className=" text-red-600">*</span></label>
                        <input type="password" id="confirm_password" name="confPassword" onChange={handleInputChange} className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-1.5" />
                    </div>
                    <button type="submit" className="mt-2 text-md text-center bg-yellow-400 hover:bg-yellow-200 text-black hover:text-black hover:shadow-md transition-colors border-none py-2 rounded font-semibold cursor-pointer">
                        Sign Up
                    </button>
                </form>
            </div>
            <div className="bg-white border border-gray-300 text-center w-96 py-3 rounded-lg">
                <span className="text-sm text-black">Already have an account? </span>
                <NavLink to="/login" className="text-yellow-700 hover:text-yellow-400 text-sm font-semibold cursor-pointer">Log In</NavLink>
            </div>
        </>
    );
}

export default SignUpForm;

SignUpForm.propTypes = {
    onOpen: PropTypes.func.isRequired,
}