import React, { useState } from "react";

import EmailVerificationForm from "../../components/Forms/Authentications/EmailVerificationForm";
import ForgotPasswordEmailForm from "../../components/Forms/Authentications/ForgotPasswordEmailForm";
import ForgotPasswordResetForm from "../../components/Forms/Authentications/ForgotPasswordResetForm";

const ForgetPassword = () => {
    const [isVerifyForm, setVerifyForm] = useState(false);
    const [isResetForm, setResetForm] = useState(false);

    return (
        <>
            <div className="flex flex-col justify-center w-full h-full">
                <div className="flex justify-center w-full h-full">
                    <ForgotPasswordEmailForm />
                </div>
            </div>

            <div className={`${isVerifyForm ? "block" : "hidden"}`}>
                <EmailVerificationForm isOpen={isVerifyForm} onClose={setVerifyForm} />
            </div>

            <div className={`${isResetForm ? "block" : "hidden"}`}>
                <ForgotPasswordResetForm isOpen={isResetForm} onClose={setResetForm} />
            </div>


        </>
    );
}

export default ForgetPassword;