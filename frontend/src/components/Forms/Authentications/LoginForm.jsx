// eslint-disable-next-line no-unused-vars
import React, { useState, useEffect } from "react";
import { NavLink, useNavigate } from "react-router-dom";
import useUser from "../../../hooks/useUser";

import { message } from "antd";

import { login } from "../../../apis/authAPI";

const LoginForm = () => {



    const navigate = useNavigate();
    const { user, setUserContext } = useUser();

    const [credentials, setCredentials] = useState({
        username: "",
        password: "",
    });

    useEffect(() => {
        if (user) {
            navigate("/");
        }
    }, [user, navigate]);

    const [messageApi, contextHolder] = message.useMessage();
    // const email_regex = new RegExp('^[a-z0-9]+@[a-z]+.[a-z0-9]{2,3}$');

    const handleInputChange = (e) => {
        const { name, value } = e.target;
        setCredentials({ ...credentials, [name]: value });
    };

    const handleLoginCredentials = async (event) => {
        event.preventDefault();
        if (credentials.username === "" || credentials.username === null) {
            messageApi.open({
                type: 'error',
                content: 'Username cannot be empty!',
            });
        } else if (credentials.password === "" || credentials.password === null) {
            messageApi.open({
                type: 'error',
                content: 'Password cannot be empty!',
            });
            // } else if (!email_regex.test(credentials.password)) {
            //     console.log(email_regex.test(credentials.password))
            //     console.log(email_regex)
            //     messageApi.open({
            //         type: 'error',
            //         content: 'Invalid email format!',
            //     });
        } else {
            try {
                const response = await login(credentials);
                console.log(response);
                if (response.data.error != null) {
                    
                    messageApi.open({
                        type: 'error',
                        content: response.data.error
                    })
                    
                } else {

                    sessionStorage.setItem("token", response.data.token);
                    setUserContext(credentials.username);

                    await messageApi.open({
                        type: 'success',
                        content: "Login successful!"
                    })
                    navigate("/");

                }
            } catch (error) {
                messageApi.open({
                    type: 'error',
                    content: error.message,
                });
            }
        }

    }

    return (
        <>
            {contextHolder}

            <div className="bg-white border border-gray-300 w-80 py-8 flex items-center flex-col mb-3 rounded-md">
                <h1 className="text-black font-serif">Scopie</h1>
                <form onSubmit={handleLoginCredentials} className="mt-8 w-64 flex flex-col">
                    <div className="mb-4">
                        <input
                            type="email"
                            id="username"
                            name="username"
                            onChange={handleInputChange}
                            className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 "
                            placeholder="Username"
                        />
                    </div>
                    <div className="mb-4">
                        <input
                            type="password"
                            id="password"
                            name="password"
                            onChange={handleInputChange}
                            className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 "
                            placeholder="Password"
                        />
                    </div>
                    <button type="submit" className=" text-md text-center bg-blue-700 hover:bg-blue-400 text-white hover:text-white py-2 rounded-lg font-semibold cursor-pointer">
                        Log In
                    </button>
                </form>
                <NavLink to="/forgot-password/verify-username" className="text-sm text-blue-900 mt-4 cursor-pointer">Forgot password?</NavLink>
            </div>
            <div className="bg-white border border-gray-300 text-center w-80 py-4 rounded-md">
                <span className="text-sm text-black">Don&apos;t have an account? </span>
                <NavLink to="/sign-up" className="text-blue-900 text-sm font-semibold cursor-pointer">Sign up</NavLink>
            </div>

        </>
    );
}

export default LoginForm;