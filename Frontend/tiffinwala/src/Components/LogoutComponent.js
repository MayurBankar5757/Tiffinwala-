import { useEffect } from "react";
import { useDispatch } from "react-redux";
import { useNavigate } from "react-router-dom";
import { logout } from "./slice";

export default function LogoutComp() {
    const navigate = useNavigate();
    const dispatch = useDispatch();

    useEffect(() => {
        localStorage.clear();  // Clear stored user data
        dispatch(logout());    // Dispatch logout action to Redux
        navigate("/Login");    // Redirect to Login page
    }, [dispatch, navigate]);

    return <div>Logging out...</div>;
}
