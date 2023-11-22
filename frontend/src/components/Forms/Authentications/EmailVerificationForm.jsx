// eslint-disable-next-line no-unused-vars
import React, { useState } from "react";
import PropTypes from "prop-types";

import { AiFillCloseCircle } from "react-icons/ai";
import { verifyEmail } from "../../../apis/authAPI";

import {
    Button,
    message,
} from "antd";

// eslint-disable-next-line no-unused-vars
const EmailVerificationForm = ({ enteredEmail, isOpen, onClose }) => {
    const [verificationData, setVerificationData] = useState({
        email: enteredEmail,
        otp: "",
    });

    const [messageApi, contextHolder] = message.useMessage();
    const otp_regex = /^\+\d{6}$/;

    const handleOneTimePasswordInput = async (event) => {
        event.preventDefault();
        if (!otp_regex.test(verificationData.otp)) {
            messageApi.open({
                type: 'error',
                content: 'Invalid code!',
            });
        } else {
            try {
                const response = await verifyEmail(verificationData);
                console.log(response) // TODO: REMOVE THIS LINE
                onClose(); // CLOSE THE FORM
                messageApi.open({
                    type: 'success',
                    content: 'Your email has been verified!',
                });
            } catch(error) {
                console.error(error);
                messageApi.open({
                    type: 'error',
                    content: 'You have entered a wrong code!',
                });
            }
        }

    }

    return (
        <>
            {contextHolder}

            <div className="flex flex-col p-8 rounded-lg bg-white text-center">
                
                <div className="absolute left-2 top-2">
                    <AiFillCloseCircle onClick={onClose}/>
                </div>
                
                <form onSubmit={handleOneTimePasswordInput}>
                    <div className="w-full text-lg">Enter verification code</div>
                    <div className="w-full text-sm text-gray-700">
                        We have sent an verification code to your your email shortly.<br />
                        Please check your inbox and enter the received verification code here.<br />
                        If you could not find the code check your spam or try to get a new code.<br />
                    </div>
                    <div className="flex justify-center">
                        <input
                            type="number"
                            placeholder="One Time Passcode"
                            value={verificationData.otp}
                            onChange={setVerificationData}
                        />
                    </div>
                    <div>
                        <Button type="primary" htmlType="submit" block>
                            Submit
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

export default EmailVerificationForm;

EmailVerificationForm.propTypes = {
    enteredEmail: PropTypes.string.isRequired,
    isOpen: PropTypes.bool.isRequired,
    onClose: PropTypes.func.isRequired,
}