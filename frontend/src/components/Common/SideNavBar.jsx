// eslint-disable-next-line no-unused-vars
import React from "react";
import { NavLink } from "react-router-dom";
import { Drawer } from "antd";
import { FaBars } from "react-icons/fa";

import useDrawer from "../../hooks/useDrawer";

const drawerContent = [
	{
		label: "Home",
		to: "/",
	},
	{
		label: "Movies",
		to: "/movie-services/movies",
	},
	{
		label: "My Reservations",
		to: "/reservation-services/my-reservations",
	},
	{
		label: "Profile",
		to: "/profile",
	},
	{
		label: "Log out",
		to: "/logout",
	},
];

const SideNavBar = () => {
	const { isOpen, closeDrawer } = useDrawer();

	return (
		<Drawer
			title={<div className="cursor-pointer" onClick={closeDrawer} ><FaBars className="text-lg" /></div>}
			placement="left"
			closable={false}
			onClose={closeDrawer}
			open={isOpen}
			getContainer={false}
		>
			<div className="flex flex-col gap-4">
				{drawerContent.map((item, index) => (
					<NavLink
						key={index}
						to={item.to}
						className={({ isActive }) =>
							`px-4 py-2 rounded-md text-base ${isActive
								? "bg-blue-950 text-white"
								: "hover:bg-gray-300 text-black hover:text-black"
							}`
						}
					>
						{item.label}
					</NavLink>
				))}
			</div>
		</Drawer>
	);
};

export default SideNavBar;