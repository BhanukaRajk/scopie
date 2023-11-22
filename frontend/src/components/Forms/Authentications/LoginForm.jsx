// eslint-disable-next-line no-unused-vars
import React, { useState } from "react";

import { AiFillCloseCircle } from "react-icons/ai";
import { EyeInvisibleOutlined, EyeTwoTone } from '@ant-design/icons';
import { Input, Button, message } from "antd";

import { login } from "../../../apis/authAPI";

const LoginForm = () => {
    const [credentials, setCredentials] = useState({
        email: "",
        password: "",
    });

    const [messageApi, contextHolder] = message.useMessage();

    const onChange = (e) => {
        console.log(e);
    };

    const handleLoginCredentials = async (event) => {
        event.preventDefault();
        if (
            credentials.email === "" || credentials.email === null ||
            credentials.password === "" || credentials.password === null
        ) {
            messageApi.open({
                type: 'error',
                content: 'Invalid username or password!',
            });
        } else {
            try {
                const response = await login(credentials);
                console.log(response);
                messageApi.open({
                    type: 'success',
                    content: 'Successfully logged in!'
                })
            } catch {
                messageApi.open({
                    type: 'error',
                    content: 'Login failed!',
                });
            }
        }

    }
    return (
        <>
            {contextHolder}

            <div className="flex flex-col p-8 rounded-lg bg-white text-center">

                <div className="absolute left-2 top-2">
                    <AiFillCloseCircle />
                </div>

                <form onSubmit={handleLoginCredentials}>
                    <div className="w-full text-lg">Login</div>
                    <div>
                        <Input placeholder="input with clear icon" allowClear onChange={onChange} />
                    </div>
                    <div className="flex justify-center">
                        <Input.Password
                            placeholder="Password"
                            value={credentials.password}
                            onChange={setCredentials}
                            iconRender={(visible) => (visible ? <EyeTwoTone /> : <EyeInvisibleOutlined />)}
                        />
                    </div>
                    <div>
                        <Button type="primary" htmlType="submit" block>
                            Login
                        </Button>
                    </div>
                    <div>
                        <Button type="link" block>
                            Forgot password?
                        </Button>
                    </div>
                </form>
            </div>
        </>
    );
}

export default LoginForm;