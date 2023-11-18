import React from 'react';
import { Input, Button } from 'antd';

const LoginForm = () => {
    return (
        <>
            <form>
                <div>Login Form</div>
                <Input placeholder="Basic usage" />
                <Input.Password placeholder="input password" />
            </form>
        </>
    );
}

export default LoginForm;