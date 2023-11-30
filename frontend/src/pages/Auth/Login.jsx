// eslint-disable-next-line no-unused-vars
import React from "react";
import LoginForm from "../../components/Forms/Authentications/LoginForm";

const Login = () => {
    return (
        <>
            <div className="h-screen w-screen bg-gray-50 flex flex-col justify-center items-center">
                <LoginForm />
            </div>
        </>
    );
}

export default Login;