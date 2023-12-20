// eslint-disable-next-line no-unused-vars
import React, { useState, useEffect } from "react";
import { NavLink, useNavigate } from "react-router-dom";
import { message } from "antd";
import { login } from "../../../apis/authAPI";
import useUser from "../../../hooks/useUser";
import LOGO from "../../../assets/logo_white_nbg.png"

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
            // } else if (!email_regex.test(credentials.password)) {    // CHECK THE EMAIL PATTERN FOR VALIDATION
            //     console.log(email_regex.test(credentials.password))
            //     console.log(email_regex)
            //     messageApi.open({
            //         type: 'error',
            //         content: 'Invalid email format!',
            //     });
        } else {
            try {
                const response = await login(credentials);
                if (response.data.token != null) {

                    sessionStorage.setItem("token", response.data.token);
                    messageApi.open({
                        type: 'success',
                        content: "Login successful!"
                    })
                    setUserContext(credentials.username);
                    
                } else {
                    messageApi.open({
                        type: 'error',
                        content: response.data.error
                    })
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

            <div className="bg-white border text-black border-gray-300 w-80 py-8 flex items-center flex-col mb-3 rounded-lg">
                <img className="rounded-md" style={{ height: "4rem" }} src={LOGO} alt={"Scopie"} />
                <div className="text-md">Login</div>

                <form onSubmit={handleLoginCredentials} className="mt-4 w-64 flex flex-col">
                    <div className="mb-4">
                        <label htmlFor="username" className="text-black text-xs font-semibold">Email <span className=" text-red-600">*</span></label>
                        <input
                            type="email"
                            id="username"
                            name="username"
                            onChange={handleInputChange}
                            className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2"
                            // placeholder="Username*"
                        />
                    </div>
                    <div className="mb-4">
                        <label htmlFor="password" className="text-black text-xs font-semibold">Password <span className=" text-red-600">*</span></label>
                        <input
                            type="password"
                            id="password"
                            name="password"
                            onChange={handleInputChange}
                            className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2"
                            // placeholder="Password*"
                        />
                    </div>
                    <button type="submit" className=" text-md text-center bg-yellow-400 hover:bg-yellow-200 text-black hover:text-black hover:shadow-md border-none py-2 rounded-lg font-semibold transition-colors cursor-pointer">
                        Log In
                    </button>
                </form>
                <NavLink to="/forgot-password/verify-username" className="text-sm text-yellow-700 hover:text-yellow-400 mt-4 cursor-pointer">Forgot password?</NavLink>
            </div>
            <div className="bg-white border border-gray-300 text-center w-80 py-3 rounded-lg">
                <span className="text-sm text-black">Don&apos;t have an account? </span>
                <NavLink to="/sign-up" className="text-yellow-700 hover:text-yellow-400 text-sm font-semibold cursor-pointer">Sign up</NavLink>
            </div>

        </>
    );
}

export default LoginForm;