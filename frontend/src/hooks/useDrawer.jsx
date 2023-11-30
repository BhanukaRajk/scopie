import { useContext } from "react";
import { DrawerContext } from "../contexts/DrawerContext";

const useDrawer = () => {
	return useContext(DrawerContext);
};

export default useDrawer;