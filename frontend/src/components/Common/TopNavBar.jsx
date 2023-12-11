import { FaBars } from "react-icons/fa";
import useDrawer from "../../hooks/useDrawer";
import useUser from "../../hooks/useUser";
import { useNavigate } from "react-router-dom";

const TopNavBar = () => {
	const { openDrawer } = useDrawer();
	const navigate = useNavigate();
	const { setUserContext } = useUser();

	const handleLogout = () => {
		sessionStorage.removeItem("token");
		setUserContext(null);
		navigate("/login");
	};

	return (
		<>
			<nav className='flex sticky left-0 right-0 top-0 z-50 justify-between p-2 bg-white rounded-es-lg rounded-ee-lg text-black shadow-lg' style={{ minWidth: '310px' }}>
				<div
					onClick={openDrawer}
					className="flex flex-col justify-center cursor-pointer p-2 hover:bg-white/10  rounded-xl mx-4 transition-all"
				>
					<FaBars className="text-lg" />
				</div>
				<div className="flex flex-col justify-center py-1 px-6 font-bold text-lg select-none">
					<div>SCOPIE</div>
				</div>
				<div className="flex text-white">
					<button className="py-1 px-4" onClick={handleLogout}>Log out</button>
				</div>
			</nav>
		</>
	);
};

export default TopNavBar;
