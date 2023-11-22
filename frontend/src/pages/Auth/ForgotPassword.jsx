// eslint-disable-next-line no-unused-vars
import React, { useState } from "react";

import EmailVerificationForm from "../../components/Forms/Authentications/EmailVerificationForm";
import ForgotPasswordEmailForm from "../../components/Forms/Authentications/ForgotPasswordEmailForm";

const ForgetPassword = () => {
    const [isVerifyForm, setVerifyForm] = useState(false);

    return (
        <>
            <div className="flex flex-col justify-center w-full h-full bg-gray-300">
                <div className="flex justify-center w-full h-full">
                    <ForgotPasswordEmailForm />
                </div>
            </div>

            <div className={`${isVerifyForm ? "block" : "hidden"} w-full h-full bg-black opacity-25`}>
                <EmailVerificationForm isOpen={isVerifyForm} onClose={setVerifyForm} />
            </div>
        </>
    );
}

export default ForgetPassword;