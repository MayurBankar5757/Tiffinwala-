import React, { useReducer, useEffect } from "react";
import { useNavigate } from "react-router-dom";

const initialState = {
  fname: "",
  lname: "",
  contact: "",
  area: "",
  city: "",
  pincode: "",
  state: "",
};

const reducer = (state, action) => {
  switch (action.type) {
    case "update":
      return { ...state, [action.field]: action.value };
    case "reset":
      return initialState;
    default:
      return state;
  }
};

export default function UpdateUserForm() {
  const navigate = useNavigate();
  const loggedUser = JSON.parse(localStorage.getItem("loggedUser"));
  const uid = loggedUser?.uid;
  const jwtToken = localStorage.getItem("jwtToken");
  const [state, dispatch] = useReducer(reducer, initialState);
  const [errors, setErrors] = React.useState({});
  const [msg, setMsg] = React.useState("");

  useEffect(() => {
    if (!loggedUser || !jwtToken || !uid) {
      navigate("/login");
    }
  }, [uid, jwtToken, loggedUser, navigate]);

  const validate = (name, value) => {
    let message = "";
    const stringValue = value ? String(value).trim() : "";

    switch (name) {
      case "fname":
      case "lname":
        if (!stringValue) message = `${name === "fname" ? "First" : "Last"} name is required.`;
        break;
      case "contact":
        if (!/^[0-9]{10}$/.test(stringValue)) message = "Contact number must be 10 digits.";
        break;
      case "pincode":
        if (!/^[0-9]{6}$/.test(stringValue)) message = "Pincode must be 6 digits.";
        break;
      case "area":
      case "city":
      case "state":
        if (!stringValue) message = `${name.charAt(0).toUpperCase() + name.slice(1)} is required.`;
        break;
      default:
        break;
    }
    setErrors((prev) => ({ ...prev, [name]: message }));
    return message === "";
  };

  const handleChange = (e) => {
    const { name, value } = e.target;
    dispatch({ type: "update", field: name, value });
    validate(name, value);
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    const allValid = Object.keys(state).every((key) => validate(key, state[key]));

    if (!allValid) {
      alert("Please correct the errors before submitting.");
      return;
    }

    try {
      const response = await fetch(`http://localhost:8103/api/users/${uid}`, {
        method: "PUT",
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${jwtToken}`,
        },
        body: JSON.stringify(state),
      });

      if (!response.ok) {
        throw new Error(`Update failed: ${response.status}`);
      }

      setMsg("User updated successfully!");
      setTimeout(() => navigate("/vendor_home"), 500);
    } catch (error) {
      console.error("Error updating user:", error);
      setMsg("Error updating user.");
    }
  };

  return (
    <div className="container mt-5">
      <div className="row justify-content-center">
        <div className="col-md-6">
          <div className="card shadow-sm">
            <div className="card-body">
              <h2 className="text-center mb-4">Update User</h2>
              <form onSubmit={handleSubmit}>
                {Object.keys(initialState).map((field) => (
                  <div className="mb-3" key={field}>
                    <label className="form-label">
                      {field.charAt(0).toUpperCase() + field.slice(1)}:
                    </label>
                    <input
                      type="text"
                      name={field}
                      className="form-control form-control-sm"
                      value={state[field]}
                      onChange={handleChange}
                    />
                    {errors[field] && <small className="text-danger">{errors[field]}</small>}
                  </div>
                ))}
                <div className="d-grid">
                  <button type="submit" className="btn btn-primary btn-sm">Update</button>
                </div>
              </form>
              <div className="d-grid mt-2">
                <button className="btn btn-secondary btn-sm" onClick={() => dispatch({ type: "reset" })}>Clear Form</button>
              </div>
              <p className="mt-3 text-center text-success">{msg}</p>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}
