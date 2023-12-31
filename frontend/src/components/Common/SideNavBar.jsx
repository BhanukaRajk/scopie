// eslint-disable-next-line no-unused-vars
import React from "react";
// eslint-disable-next-line no-unused-vars
import { useNavigate, NavLink } from "react-router-dom";
import { Drawer } from "antd";
import { FaBars } from "react-icons/fa";

import useDrawer from "../../hooks/useDrawer";
// import useUser from "../../hooks/useUser";

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
			// getContainer={false}
			width={310}
			style={{ position: "fixed", width: 310 }}
		>
			<div className="flex flex-col gap-4">
				{drawerContent.map((item, index) => (
					<NavLink
						key={index}
						to={item.to}
						className={({ isActive }) =>
							`px-4 py-2 rounded-md text-base ${isActive
								? " bg-yellow-300 text-black hover:text-black font-semibold"
								: " hover:bg-yellow-100 text-black hover:text-black transition-color font-semibold"
							}`
						}
						onClick={closeDrawer}
					>
						{item.label}
					</NavLink>
				))}
			</div>
		</Drawer>
	);
};

export default SideNavBar;