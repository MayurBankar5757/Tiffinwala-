import { configureStore } from "@reduxjs/toolkit";
import loggedReducer from "./slice";

const store = configureStore({
  reducer: {
    logged: loggedReducer, // Ensure this key matches the selector
  },
});

export default store;
