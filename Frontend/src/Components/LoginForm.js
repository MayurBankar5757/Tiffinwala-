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
    const navigate = useNavigate();
    const reducerAction = useDispatch();

    const sendData = (e) => {
        e.preventDefault(); // Fixed typo
        const reqOptions = {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(info),
        };

        fetch("http://localhost:8080/api/users/chkLogin", reqOptions) // Replace with actual API endpoint
            .then(resp => {
                if (resp.ok) {
                    console.log(resp.status);
                    return resp.text();
                } else {
                    console.log(resp.statusText);
                    throw new Error("Server error");
                }
            })
            .then(text => (text.length ? JSON.parse(text) : {}))
            .then(obj => {
                if (Object.keys(obj).length === 0) {
                    setMsg("Wrong user ID or Password"); // Fixed
                } else {
                    reducerAction(login());
                    if (obj.role.roleId === 1) {
                        navigate("/admin_home");
                    } else if (obj.Rid.Rid === 2) {
                        navigate("/vendor_home"); // Example path for role 2
                    } else if (obj.Rid.Rid === 3) {
                        navigate("/user_home"); // Example path for role 3
                    }
                }
            })
        .catch(error => alert("Server error. Try after some time"));
    };

    return (
        <div>
            <h1>Login Form</h1>
            <form>
                <div className="form-group">
                    <label htmlFor="email">Email address</label>
                    <input
                        type="email"
                        className="form-control"
                        id="email"
                        aria-describedby="emailHelp"
                        placeholder="Enter email"
                        name="email"
                        value={info.email}
                        onChange={(e) => {
                            dispatch({ type: 'update', fld: 'email', val: e.target.value });
                        }}
                    />
                    <small id="emailHelp" className="form-text text-muted">
                        We'll never share your email with anyone else.
                    </small>
                </div>
                <div className="form-group">
                    <label htmlFor="pwd">Password</label>
                    <input
                        type="password"
                        className="form-control"
                        id="pwd"
                        placeholder="Password"
                        name="pwd"
                        value={info.pwd}
                        onChange={(e) => {
                            dispatch({ type: 'update', fld: 'pwd', val: e.target.value });
                        }}
                    />
                </div>
                <button
                    type="submit"
                    className="btn btn-primary"
                    onClick={(e) => {
                        sendData(e);
                    }}
                >
                    Submit
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
            </form>
            <p>{JSON.stringify(info)}</p>
            <p>{msg}</p>
        </div>
    );
}
