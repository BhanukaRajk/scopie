// eslint-disable-next-line no-unused-vars
import React from "react";

const SideNavBar = () => {
    return (
        <>
            <div className="absolute top-0 bottom-0 left-0 bg-white text-black w-16 p-4">
                <ul>
                    <li>
                        <div className={`w-full py-3 rounded-md bg-blue-900 text-white`}>Home</div>
                    </li>
                </ul>
            </div>
        </>
    );
}

export default SideNavBar;