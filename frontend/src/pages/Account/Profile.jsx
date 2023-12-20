// eslint-disable-next-line no-unused-vars
import React, { useState, useEffect } from "react";
import { Input } from "antd";
import { getUserdata } from "../../apis/profileAPI";

import EditProfileForm from "../../components/Forms/AccountSettings/EditProfileForm";
import UpdatePasswordForm from "../../components/Forms/AccountSettings/UpdatePasswordForm";
import useUser from "../../hooks/useUser";

const Profile = () => {
    const [isNewPasswordForm, handleNewPasswordFrom] = useState(false);
    const [isNewUpdateForm, handleNewUpdateFrom] = useState(false);
    const [userData, setUserData] = useState({
        firstName: "",
        lastName: "",
        userName: "",
    });
    const { user } = useUser();

    useEffect(() => {
        const fetchUser = async () => {
            try {
                const response = await getUserdata({ userName: user });
                setUserData(response.data);
            } catch {
                console.log("error");
            }
        }
        fetchUser();
    }, [user]);

    const toggleleNewUpdateFrom = (state) => {
        handleNewUpdateFrom(state);
    }

    const toggleNewPasswordFrom = (state) => {
        handleNewPasswordFrom(state);
    }


    return (
        <>
            <div className="fixed top-14 bottom-0 left-0 right-0 flex flex-col justify-center items-center login-background">
                <div className="flex flex-col bg-gray-50 rounded-md border border-gray-500">
                    <div className="text-black text-2xl p-4 w-full text-center">Hi! {userData.firstName + " " + userData.lastName}</div>
                    <div className="flex flex-col md:flex-row">
                        <div className="flex flex-col justify-center items-center w-full p-8">
                            <div className="mb-2 md:mb-4">
                                <label className="block text-sm font-medium text-gray-600">First Name</label>
                                <Input disabled value={userData.firstName} placeholder="First Name" className="mb-4" />
                            </div>
                            <div className="mb-2 md:mb-4">
                                <label className="block text-sm font-medium text-gray-600">Last Name</label>
                                <Input disabled value={userData.lastName} placeholder="Last Name" className="mb-4" />
                            </div>
                            <div className="mb-2 md:mb-4">
                                <label className="block text-sm font-medium text-gray-600">Email Address</label>
                                <Input disabled value={user} placeholder="Email Address" />
                            </div>
                        </div>

                        <div className="flex flex-col justify-center items-center border-t-2 md:border-s-2 md:border-t-0 w-full p-8">
                            <h1 className="text-xl font-semibold mb-4 md:mb-6 text-gray-800">Edit your Profile</h1>
                            <hr className="border-t border-gray-500 mb-4 md:mb-6" />
                            <button type="button" onClick={() => toggleleNewUpdateFrom(true)} className=" mt-3 text-md text-center bg-black hover:bg-gray-800 text-white hover:text-ehite w-48 rounded-lg cursor-pointer border-none">
                                Edit Name
                            </button>
                            <button type="button" onClick={() => toggleNewPasswordFrom(true)} className=" mt-3 text-md text-center bg-black hover:bg-gray-800 text-white hover:text-ehite w-48 rounded-lg cursor-pointer border-none">
                                Change Password
                            </button>
                        </div>

                    </div>
                </div>
            </div>

            <div className={`${isNewUpdateForm ? "fixed" : "hidden"}`}>
                <EditProfileForm closeUpdateForm={() => toggleleNewUpdateFrom(false)} thisUser={user} />
            </div>

            <div className={`${isNewPasswordForm ? "fixed" : "hidden"}`}>
                <UpdatePasswordForm closePasswordForm={() => toggleNewPasswordFrom(false)} user={user} />
            </div>
        </>
    );
}

export default Profile;