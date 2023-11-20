import React, { useState } from "react";

import SignupForm from "../../components/Forms/Authentications/SignupForm";
import EmailVerificationForm from "../../components/Forms/Authentications/EmailVerificationForm";

const Login = () => {
    const [isVerifyForm, setVerifyForm] = useState(false);

    return (
        <>
            <div className="flex justify-center w-full h-full">
                <div className="flex flex-col juctify-center w-full h-full">
                    <div>
                        <SignupForm />
                    </div>
                    <div className="absolute z-10">
                        <EmailVerificationForm isOpen={isVerifyForm} onClose={setVerifyForm} />
                    </div>
                </div>
            </div>
        </>
    );
}

export default Login;