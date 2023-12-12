import { Outlet } from "react-router-dom";
import { createBrowserRouter } from "react-router-dom";
import InsideMainLayout from "./layouts/InsideMainLayout.jsx";

import Login from "../src/pages/Auth/Login.jsx";
import Signup from "../src/pages/Auth/Signup.jsx";
import ForgotPassword from "../src/pages/Auth/ForgotPassword.jsx";
import NewPassword from "../src/pages/Auth/NewPassword.jsx";

import Reservations from "./pages/Reservation/Reservations.jsx";
import Movies from "./pages/Movie/Movies.jsx";
import Profile from "./pages/Account/Profile.jsx";
import Dashboard from "./pages/Dashboard/Dashboard.jsx";


const routes = [
	{
		path: "/login",
		element: <Login />,
	},
	{
		path: "/sign-up",
		element: <Signup />,
	},
	{
		path: "/forgot-password/verify-username",
		element: <ForgotPassword />,
	},
	{
		path: "/forgot-password/add-new-password",
		element: <NewPassword />,
	},
	{
		path: "",
		element: <InsideMainLayout />,
		children: [
			{
				path: "/",
				element: <Dashboard />,
			},
			{
				path: "/profile",
				element: <Profile />,
			},
			{
				path: "/movie-services",
				element: <Outlet />,
				children: [
					{
						path: "/movie-services/movies",
						element: <Movies />,
					},
				]
			},
			{
				path: "/reservation-services",
				element: <Outlet />,
				children: [
					{
						path: "/reservation-services/my-reservations",
						element: <Reservations />,
					},
				]
			},
		],
	},
];

const router = createBrowserRouter(routes);

export default router;
