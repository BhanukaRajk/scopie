import { createContext, useState } from "react";
import proptypes from "prop-types";

export const UserContext = createContext();

// -eslint-disable-next-line react/prop-types
export default function UserProvider({ children }) {
	const [user, setUser] = useState(null);

	const setUserContext = (user) => {
		setUser(user);
	};

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
