// eslint-disable-next-line no-unused-vars
import React, { useState } from "react";

import EditProfileForm from "../../components/Forms/AccountSettings/EditProfileForm";
import UpdatePasswordForm from "../../components/Forms/AccountSettings/UpdatePasswordForm";

const Profile = () => {
    const [isNewPasswordForm, toggleNewPasswordFrom] = useState(false);

    const onOpen = () => {
        toggleNewPasswordFrom(true);
    }

    const onClose = () => {
        toggleNewPasswordFrom(false);
    }

    return (
        <>
            <div className="h-screen w-screen bg-gray-50 flex flex-col justify-center items-center">
                <EditProfileForm onOpen = {onOpen} />

                <div className={`${isNewPasswordForm ? "block" : "hidden"}`}>
                    <UpdatePasswordForm isOpen={isNewPasswordForm} onClose={onClose} />
                </div>
            </div>
        </>
    );
}

export default Profile;