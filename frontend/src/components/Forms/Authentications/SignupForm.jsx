// eslint-disable-next-line no-unused-vars
import React, { useState } from "react";
import PropTypes from "prop-types";

import { message } from "antd";

import { getVerificationCode } from "../../../apis/authAPI";
import { NavLink } from "react-router-dom";

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
        if ( userData.firstName === "" || userData.firstName === null )
        {
            messageApi.open({
                type: 'error',
                content: 'First name cannot be empty!',
            });
        } else if ( userData.email === "" || userData.email === null )
        {
            messageApi.open({
                type: 'error',
                content: 'Email cannot be empty!',
            });
        } else if ( 
            userData.password === "" || 
            userData.password === null ||
            userData.confPassword === "" ||
            userData.confPassword === null
            )
        {
            messageApi.open({
                type: 'error',
                content: 'Password cannot be empty!',
            });
        } else if ( userData.password != userData.confPassword )
        {
            messageApi.open({
                type: 'error',
                content: 'Passwords does not match!',
            });
        } else 
        {
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

            <div className="bg-white border text-black border-gray-300 w-96 py-8 flex items-center flex-col mb-3 rounded-md">
                <h1 className="text-black font-serif">Scopie</h1>
                <div className="text-lg">Create new account</div>
                <form onSubmit={handleSignUp} className="mt-8 w-10/12 flex flex-col">
                    <div className="grid gap-6 mb-4 lg:grid-cols-2">
                        <div>
                            <input type="text" id="first_name" name="firstName" onChange={handleInputChange} className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 " placeholder="First Name" />
                        </div>
                        <div>
                            <input type="text" id="last_name" name="lastName" onChange={handleInputChange} className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 " placeholder="Last Name" />
                        </div>
                    </div>
                    <div className="mb-4">
                        <input type="email" id="email" name="email" onChange={handleInputChange} className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 " placeholder="Email Address" />
                    </div>
                    <div className="mb-4">
                        <input type="password" id="password" name="password" onChange={handleInputChange} className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 " placeholder="Password" />
                    </div>
                    <div className="mb-4">
                        <input type="password" id="confirm_password" name="confPassword" onChange={handleInputChange} className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 " placeholder="Confirm Password" />
                    </div>
                    <button type="submit" className=" text-md text-center bg-blue-700 hover:bg-blue-400 text-white hover:text-white py-2 rounded font-semibold cursor-pointer">
                        Sign Up
                    </button>
                </form>
            </div>
            <div className="bg-white border border-gray-300 text-center w-96 py-4 rounded-md">
                <span className="text-sm text-black">Already have an account? </span>
                <NavLink to="/login" className="text-blue-900 text-sm font-semibold cursor-pointer">Log In</NavLink>
            </div>
        </>
    );
}

export default SignUpForm;

SignUpForm.propTypes = {
    onOpen: PropTypes.func.isRequired,
}