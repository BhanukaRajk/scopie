import { useContext } from "react";

import { UserContext } from "../contexts/UserContext";

const useUser = () => {
	return useContext(UserContext);
};

export default useUser;

// import { useContext } from "react";
// import { UserContext } from "../contexts/UserContext";

// const useUser = () => {
// 	const { user, setUser } = useContext(UserContext);

// 	if (user === undefined || setUser === undefined) {
// 		throw new Error("useUser must be used within a UserProvider");
// 	}

// 	return { user, setUser };
// };

// export default useUser;
