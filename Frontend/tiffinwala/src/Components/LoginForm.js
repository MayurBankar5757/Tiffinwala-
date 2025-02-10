import React, { useReducer, useState } from 'react';
import { useDispatch } from 'react-redux';
import { useNavigate } from 'react-router-dom';
import { login } from './slice';
import { jwtDecode } from 'jwt-decode';

export default function Home() {
    const init = { email: "", pwd: "" };

    const reducer = (state, action) => {
        switch (action.type) {
            case 'update':
                return { ...state, [action.fld]: action.val };
            case 'reset':
                return init;
            default:
                return state;
        }
    };

    const [info, dispatch] = useReducer(reducer, init);
    const [msg, setMsg] = useState("");
    const [pwdVisible, setPwdVisible] = useState(false);
    const navigate = useNavigate();
    const reducerAction = useDispatch();

    const sendData = async (e) => {
        e.preventDefault();
        setMsg("");

        try {
            const response = await fetch("http://localhost:8104/auth/login", {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(info),
            });

            if (!response.ok) throw new Error("Invalid email or password.");

            const data = await response.json();
            const token = data.jwt;

            if (!token) throw new Error("Token not received. Login failed.");

            // Store token in localStorage properly
            localStorage.setItem("jwtToken", token);

            const decodedToken = jwtDecode(token);
            if (!decodedToken) {
                setMsg("Invalid token format");
                return;
            }

            const loggedUser = {
                email: decodedToken.sub,
                role: decodedToken.roles?.[0]?.toLowerCase() || "guest",
                uid: decodedToken.uid,
                fname: decodedToken.fname,
                lname: decodedToken.lname,
                contact: decodedToken.contact,
                address: decodedToken.address || {},
                token: token,
            };

            // Store in Redux
            reducerAction(login(loggedUser));

            // Store in Local Storage
            localStorage.setItem("loggedUser", JSON.stringify(loggedUser));

            // Redirect based on role
            const roleRedirects = {
                admin: "/admin_home",
                vendor: "/vendor_home",
                customer: "/customer_home"
            };

            navigate(roleRedirects[loggedUser.role] || "/");

        } catch (error) {
            console.error("Login error:", error);
            setMsg(error.message);
        }
    };

    return (
        <div className="container mt-5">
            <div className="row justify-content-center">
                <div className="col-md-6 col-lg-4">
                    <div className="card shadow">
                        <div className="card-body">
                            <h2 className="text-center mb-4">Login</h2>
                            <form onSubmit={sendData}>
                                <div className="form-group mb-3">
                                    <label htmlFor="email">Email Address</label>
                                    <input
                                        type="email"
                                        className="form-control"
                                        id="email"
                                        placeholder="Enter email"
                                        value={info.email}
                                        onChange={(e) => dispatch({ type: 'update', fld: 'email', val: e.target.value })}
                                        required
                                    />
                                </div>
                                <div className="form-group mb-3">
                                    <label htmlFor="pwd">Password</label>
                                    <div className="input-group">
                                        <input
                                            type={pwdVisible ? "text" : "password"}
                                            className="form-control"
                                            id="pwd"
                                            placeholder="Enter password"
                                            value={info.pwd}
                                            onChange={(e) => dispatch({ type: 'update', fld: 'pwd', val: e.target.value })}
                                            required
                                        />
                                        <button
                                            type="button"
                                            className="btn btn-outline-secondary"
                                            onClick={() => setPwdVisible(!pwdVisible)}
                                        >
                                            <i className={`fa ${pwdVisible ? 'fa-eye-slash' : 'fa-eye'}`}></i>
                                        </button>
                                    </div>
                                </div>
                                <div className="d-flex justify-content-between">
                                    <button type="submit" className="btn btn-primary me-2">Login</button>
                                    <button type="reset" className="btn btn-secondary" onClick={() => dispatch({ type: 'reset' })}>Clear</button>
                                </div>
                            </form>
                            <p className="text-danger text-center mt-3">{msg}</p>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
}
