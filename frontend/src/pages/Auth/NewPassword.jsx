// eslint-disable-next-line no-unused-vars
import React, { useState } from "react";

import ForgotPasswordResetForm from "../../components/Forms/Authentications/ForgotPasswordResetForm";

const ForgetPassword = () => {
    const [isResetForm, setResetForm] = useState(false);

    return (
        <>
            <div className="h-screen w-screen bg-gray-50 flex flex-col justify-center items-center">
                <ForgotPasswordResetForm isOpen={isResetForm} onClose={setResetForm} />
            </div>
        </>
    );
}

export default ForgetPassword;