import { combineReducers } from "@reduxjs/toolkit";

import { authSlice } from "./slices/authSlice";

const reducer = combineReducers({ authSlice });

export default reducer;