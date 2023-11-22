// eslint-disable-next-line no-unused-vars
import React, { useState } from "react";

import ForgotPasswordResetForm from "../../components/Forms/Authentications/ForgotPasswordResetForm";

const ForgetPassword = () => {
    const [isResetForm, setResetForm] = useState(false);

    return (
        <>
            <div className="flex flex-col justify-center w-full h-full bg-gray-300">
                <div className="flex justify-center w-full h-full">
                    <ForgotPasswordResetForm isOpen={isResetForm} onClose={setResetForm} />
                </div>
            </div>
        </>
    );
}

export default ForgetPassword;