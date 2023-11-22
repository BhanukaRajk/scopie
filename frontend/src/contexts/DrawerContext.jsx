/* eslint-disable react/prop-types */
import { createContext, useState } from "react";

export const DrawerContext = createContext();

export function DrawerProvider({ children }) {
	const [isOpen, setIsOpen] = useState(false);

	const openDrawer = () => {
		setIsOpen(true);
	};

	const closeDrawer = () => {
		setIsOpen(false);
	};

	return (
		<DrawerContext.Provider value={{ isOpen, openDrawer, closeDrawer }}>
			{children}
		</DrawerContext.Provider>
	);
}
