// eslint-disable-next-line no-unused-vars
import React, { useState } from "react";

import SignupForm from "../../components/Forms/Authentications/SignupForm";
import EmailVerificationForm from "../../components/Forms/Authentications/EmailVerificationForm";

const Login = () => {
    const [isVerifyForm, setVerifyForm] = useState(false);

    return (
        <>
            <div className="flex justify-center w-full h-full bg-gray-300">
                <div className="flex flex-col juctify-center w-full h-full">
                    <div>
                        <SignupForm />
                    </div>
                    <div className={`${isVerifyForm ? "block" : "hidden"} absolute z-10 bg-black opacity-25`}>
                        <EmailVerificationForm isOpen={isVerifyForm} onClose={setVerifyForm} />
                    </div>
                </div>
            </div>
        </>
    );
}

export default Login;