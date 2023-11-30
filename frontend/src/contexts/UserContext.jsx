import { createContext, useState, useEffect } from "react";
import proptypes from "prop-types";
import { jwtDecode } from "jwt-decode";

export const UserContext = createContext({ user: null, setUserContext: () => { } });

// -eslint-disable-next-line react/prop-types
export default function UserProvider({ children }) {
	const [user, setUser] = useState(null);

	const setUserContext = (user) => {
		setUser(user);
	};

	const token = sessionStorage.getItem("token");
	
	useEffect(() => {
		if (token) {
			const { sub } = jwtDecode(token);
			setUser(sub);
		}
	}, [token]);

	return (
		<UserContext.Provider value={{ user, setUserContext }}>
			{children}
		</UserContext.Provider>
	);
}

// REMOVE INITIAL `-` BEFORE ESLINT COMMENT BEFORE REMOVE THIS PROPTYPE VALIDATION
UserProvider.propTypes = {
	children: proptypes.object.isRequired,
};
