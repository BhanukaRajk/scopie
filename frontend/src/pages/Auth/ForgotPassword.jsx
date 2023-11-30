// eslint-disable-next-line no-unused-vars
import React, { useState } from "react";

import ForgetPasswordEmailVerificationForm from "../../components/Forms/Authentications/ForgotPasswordEmailVerificationForm";
import ForgotPasswordEmailForm from "../../components/Forms/Authentications/ForgotPasswordEmailForm";

const ForgetPassword = () => {
    const [isVerifyForm, setVerifyForm] = useState(false);

    const onClose = () => {
        setVerifyForm(false);
    }

    const onOpen = () => {
        setVerifyForm(true);
    }

    return (
        <>
            <div className="h-screen w-screen bg-gray-50 flex flex-col justify-center items-center">                
                <ForgotPasswordEmailForm onOpen={onOpen} onClose={onClose} />


                <div className={`${isVerifyForm ? "block" : "block"}`}>
                    <ForgetPasswordEmailVerificationForm isOpen={isVerifyForm} onClose={onClose} />
                </div>
            </div>
        </>
    );
}

export default ForgetPassword;