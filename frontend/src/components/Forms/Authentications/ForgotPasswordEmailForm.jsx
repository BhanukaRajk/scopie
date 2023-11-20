import React, { useState } from "react";
import { AiFillCloseCircle } from "react-icons/ai";

import {
    Button,
    message,
} from "antd";

const ForgotPasswordEmailForm = ({ isOpen, onClose }) => {
    const [verifyEmail, setVerifyEmail] = useState("");
    const [messageApi, contextHolder] = message.useMessage();

    const handleEmailInput = async (event) => {
        event.preventDefault();
        if (verifyEmail === "" || verifyEmail === null) {
            messageApi.open({
                type: 'error',
                content: 'Please enter valid email address!',
            });
        } else {
            // SET THE EMAIL VERIFICATION FORM TO OPEN
        }
    }

    return (
        <>
            {contextHolder}

            <div className="flex flex-col p-8 rounded-lg bg-white text-center">
                
                <div className="absolute left-2 top-2">
                    <AiFillCloseCircle />
                </div>
                
                <form onSubmit={handleEmailInput}>
                    <div className="w-full text-lg">Enter your email</div>
                    <div className="w-full text-sm text-gray-700">
                        We will sent an verification code to your your email shortly.<br />
                    </div>
                    <div className="flex justify-center">
                        <input
                            type="text"
                            placeholder="Email address"
                            value={verifyEmail}
                            onChange={setVerifyEmail}
                        />
                    </div>
                    <div>
                        <Button type="primary" htmlType="submit" block>
                            Proceed
                        </Button>
                    </div>
                    <div>
                        <Button type="link" block>
                            Back to login
                        </Button>
                    </div>
                </form>
            </div>
        </>
    );
}

export default ForgotPasswordEmailForm;