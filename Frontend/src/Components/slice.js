import { createSlice } from "@reduxjs/toolkit";

export const loggedSlice = createSlice({
    name: "logged",
    initialState: { // Fixed typo here
        loggedIn: false,
    },
    reducers: {
        login: (state) => {
            console.log("in loggIn action");
            return { loggedIn: true };
        },
        logout: (state) => {
            console.log("in loggedOut action");
            return { loggedIn: false };
        },
    },
});

export const { login, logout } = loggedSlice.actions;
export default loggedSlice.reducer;
