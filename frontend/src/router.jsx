import InsideMainLayout from "./layouts/InsideMainLayout.jsx";
import { Outlet } from "react-router-dom";
import { createBrowserRouter } from "react-router-dom";

import Login from "../src/pages/Auth/Login.jsx";
import Signup from "../src/pages/Auth/Signup.jsx";
import ForgotPassword from "../src/pages/Auth/ForgotPassword.jsx";
import NewPassword from "../src/pages/Auth/NewPassword.jsx";

import Dashboard from "./pages/Dashboard/Dashboard.jsx";

import Reservations from "./pages/Reservation/Reservation.jsx";
import Movies from "./pages/Movie/Movie.jsx";
import Profile from "./pages/Account/Profile.jsx";


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
		path: "/forgot-password/verify-email",
		element: <ForgotPassword />,
	},
	{
		path: "/forgot-password/add-new-password",
		element: <NewPassword />,
	},
	{
		path: "/",
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
				element: <><Outlet /></>,
				children: [
					{
						path: "/movie-services/movies",
						element: <Movies />,
					},
					// {
					// 	path: "/movie-services/watch-movies",
					// 	element: <WatchMovies />,
					// },
				]
			},
			{
				path: "/reservation-services",
				element: <><Outlet /></>,
				children: [
					{
						path: "/reservation-services/my-reservations",
						element: <Reservations />,
					},
					// {
					// 	path: "/reservation-services/watched",
					// 	element: <Watched />,
					// },
				]
			},
		],
	},
];

const router = createBrowserRouter(routes);

export default router;
