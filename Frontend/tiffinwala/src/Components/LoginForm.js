import React, { useReducer, useState } from 'react';
import { useDispatch } from 'react-redux';
import { useNavigate } from 'react-router-dom';
import { login } from './slice';

export default function Home() {
    const init = {
        email: "",
        pwd: ""
    };

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
    const [pwdVisible, setPwdVisible] = useState(false); // Password visibility toggle
    const navigate = useNavigate();
    const reducerAction = useDispatch();

    const sendData = (e) => {
        e.preventDefault();
        const reqOptions = {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(info),
        };

        fetch("http://localhost:8102/api/users/login", reqOptions)
            .then(resp => {
                if (resp.ok) {
                    return resp.text();
                } else {
                    throw new Error("Server error");
                }
            })
            .then(text => (text.length ? JSON.parse(text) : {}))
            .then(obj => {
                if (Object.keys(obj).length === 0) {
                    setMsg("Wrong user ID or Password");
                } else {
                    reducerAction(login(obj));
                    localStorage.setItem("loggedUser", JSON.stringify(obj));
                    if (obj.role.roleId === 1) {
                        navigate("/admin_home");
                    } else if (obj.role.roleId === 2) {
                        navigate("/vendor_home");
                    } else if (obj.role.roleId === 3) {
                        navigate("/customer_home");
                    }
                }
            })
            .catch(error => alert("Server error. Try after some time"));
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
                                        name="email"
                                        value={info.email}
                                        onChange={(e) => {
                                            dispatch({ type: 'update', fld: 'email', val: e.target.value });
                                        }}
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
                                            name="pwd"
                                            value={info.pwd}
                                            onChange={(e) => {
                                                dispatch({ type: 'update', fld: 'pwd', val: e.target.value });
                                            }}
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
                                    <button
                                        type="submit"
                                        className="btn btn-primary me-2"
                                    >
                                        Login
                                    </button>
                                    <button
                                        type="reset"
                                        className="btn btn-secondary"
                                        onClick={() => {
                                            dispatch({ type: 'reset' });
                                        }}
                                    >
                                        Clear
                                    </button>
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
