import { createSlice } from "@reduxjs/toolkit";

export const loggedSlice = createSlice({
    name: "logged",
    initialState: {
        loggedIn: false,
        user: null,  // Store the user data here
    },
    reducers: {
        login: (state, action) => {
            console.log("in loggIn action");
            state.loggedIn = true;
            state.user = action.payload;  // Store the user data passed from the login response
        },
        logout: (state) => {
            console.log("in loggedOut action");
            state.loggedIn = false;
            state.user = null;  // Clear the user data on logout
        },
    },
});

export const { login, logout } = loggedSlice.actions;
export default loggedSlice.reducer;
