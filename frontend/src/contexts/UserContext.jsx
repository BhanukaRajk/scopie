// THIS IS USER CONTEXT FILE
import { createContext, useState, useEffect } from "react";
import proptypes from "prop-types";
import { jwtDecode } from "jwt-decode";

export const UserContext = createContext({ user: null, setUserContext: () => {} });

// -eslint-disable-next-line react/prop-types
export default function UserProvider({ children }) {
    const [user, setUser] = useState(null);
    const [loading, setLoading] = useState(true);

    const setUserContext = (user) => {
        setUser(user);
    };

    const token = sessionStorage.getItem("token");

    useEffect(() => {
        if (token) {
            const { sub } = jwtDecode(token);
            setUser(sub);
        }
        setLoading(false); // Set loading to false once user context is determined
    }, [token]);

    return (
        <UserContext.Provider value={{ user, setUserContext }}>
            {!loading && children}
        </UserContext.Provider>
    );
}

// REMOVE INITIAL `-` BEFORE ESLINT COMMENT BEFORE REMOVE THIS PROPTYPE VALIDATION
UserProvider.propTypes = {
    children: proptypes.object.isRequired,
};
