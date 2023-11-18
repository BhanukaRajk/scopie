import React from 'react';
import { Input, Button } from 'antd';

const SignupForm = () => {
    return (
        <>
            <form>
                <div>Login Form</div>
                <Input placeholder="First name" type="text" />
                <Input placeholder="Last name" type="text"/>
                <Input placeholder="Mobile number" type="text"/>
                <Input placeholder="Email" type="text"/>
                <Input.Password placeholder="input password" />
            </form>
        </>
    );
}

export default SignupForm;