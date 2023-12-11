// eslint-disable-next-line no-unused-vars
import React, { useState } from "react";

import SignupForm from "../../components/Forms/Authentications/SignupForm";
import EmailVerificationForm from "../../components/Forms/Authentications/EmailVerificationForm";

const Signup = () => {
    const [isVerifyForm, setVerifyForm] = useState(false);

    const onClose = () => {
        setVerifyForm(false);
    }

    const onOpen = () => {
        setVerifyForm(true);
    }


    return (
        <>
            <div className="h-screen w-screen login-background flex flex-col justify-center items-center">
                <SignupForm onOpen={onOpen}/>


                <div className={`${isVerifyForm ? "block" : "hidden"}`}>
                    <EmailVerificationForm isOpen={isVerifyForm} onClose={onClose} />
                </div>
            </div>
        </>
    );
}

export default Signup;