// eslint-disable-next-line no-unused-vars
import React, { useState } from "react";
import PropTypes from "prop-types";

import { AiFillCloseCircle } from "react-icons/ai";
import { EyeInvisibleOutlined, EyeTwoTone } from '@ant-design/icons';
import { Input, Button, message } from "antd";

import { resetPassword } from "../../../apis/forgetPasswordAPI";

const ForgotPasswordResetForm = ({ userEmail, isOpen, onClose }) => {
    const [passwords, setPasswords] = useState({
        email: userEmail,
        newPassword: "",
        confirmPassword: ""
    });
    const [messageApi, contextHolder] = message.useMessage();

    const handleChangePassword = async (event) => {
        event.preventDefault();
        if (
            passwords.newPassword === "" || passwords.newPassword === null ||
            passwords.confirmPassword === "" || passwords.confirmPassword === null
        ) {
            messageApi.open({
                type: 'error',
                content: 'Your password must be 8 characters or more long!',
            });
        } else if (passwords.newPassword !== passwords.confirmPassword) {
            messageApi.open({
                type: 'error',
                content: 'Passwords does not match!',
            });
        } else {
            try {
                const response = await resetPassword(passwords);
                console.log(response);
                messageApi.open({
                    type: 'success',
                    content: 'Password changed!!',
                });
            } catch(error) {
                console.error(error);
                messageApi.open({
                    type: 'error',
                    content: 'Password resetting process failed!',
                });
            }
        }

    }
    return (
        <>
            {contextHolder}
            {isOpen} {/* TODO: REMOVE THIS */}

            <div className="flex flex-col p-8 rounded-lg bg-white text-center">

                <div className="absolute left-2 top-2">
                    <AiFillCloseCircle onClick={onClose}/>
                </div>

                <form onSubmit={handleChangePassword}>
                    <div className="w-full text-lg">Add new password</div>
                    <div className="flex justify-center">
                        <Input.Password
                            placeholder="Enter new password"
                            value={passwords.newPassword}
                            onChange={setPasswords}
                            iconRender={(visible) => (visible ? <EyeTwoTone /> : <EyeInvisibleOutlined />)}
                        />
                    </div>
                    <div className="flex justify-center">
                        <Input.Password
                            placeholder="Confirm password"
                            value={passwords.confirmPassword}
                            onChange={setPasswords}
                            iconRender={(visible) => (visible ? <EyeTwoTone /> : <EyeInvisibleOutlined />)}
                        />
                    </div>
                    <div>
                        <Button type="primary" htmlType="submit" block>
                            Change Password
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

export default ForgotPasswordResetForm;

ForgotPasswordResetForm.propTypes = {
    userEmail: PropTypes.string.isRequired,
    isOpen: PropTypes.bool.isRequired,
    onClose: PropTypes.func.isRequired,
}