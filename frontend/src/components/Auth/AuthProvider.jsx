// Your main application file
import React from 'react';
import { AuthProvider } from './AuthContext';
import Router from './Router'; // Assuming your router is in a file named Router.js

const App = () => {
    return (
        <AuthProvider>
            <Router />
        </AuthProvider>
    );
};

export default App;
