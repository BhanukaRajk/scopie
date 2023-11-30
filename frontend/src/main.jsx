import React from 'react'
import ReactDOM from 'react-dom/client'
import App from './App.jsx'
import './index.css'

import '@ant-design/icons'
import 'antd/dist/reset.css'

import { DrawerProvider } from "./contexts/DrawerContext.jsx";
import UserProvider from "./contexts/UserContext.jsx";


ReactDOM.createRoot(document.getElementById("root")).render(
	<React.StrictMode>
		<UserProvider>
			<DrawerProvider>
				<App />
			</DrawerProvider>
		</UserProvider>
	</React.StrictMode>
);