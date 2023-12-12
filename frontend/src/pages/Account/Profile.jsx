// eslint-disable-next-line no-unused-vars
import React, { useState } from "react";

import EditProfileForm from "../../components/Forms/AccountSettings/EditProfileForm";
import UpdatePasswordForm from "../../components/Forms/AccountSettings/UpdatePasswordForm";
import useUser from "../../hooks/useUser";

const Profile = () => {
    const [isNewPasswordForm, toggleNewPasswordFrom] = useState(false);
    const { user } = useUser();

    const onOpen = () => {
        toggleNewPasswordFrom(true);
    }

    const onClose = () => {
        toggleNewPasswordFrom(false);
    }

    return (
        <>
            <div className="h-screen w-screen bg-gray-50 flex flex-col justify-center items-center login-background">
                <EditProfileForm onOpen = {onOpen} nuser={user} />

                <div className={`${isNewPasswordForm ? "block" : "hidden"}`}>
                    <UpdatePasswordForm onClose={onClose} user={user} />
                </div>
            </div>
        </>
    );
}

export default Profile;