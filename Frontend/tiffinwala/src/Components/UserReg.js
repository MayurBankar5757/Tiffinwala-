import React, { useState } from "react";
import { navigate, useNavigate } from "react-router-dom";

function UserVendorForm() {
  const [formData, setFormData] = useState({
    fname: "",
    lname: "",
    email: "",
    rid: "", // Default to empty for "Select Role"
    area: "",
    city: "",
    pincode: "",
    state: "",
    password: "",
    contact: "",
    isVendor: false,
    foodLicenceNo: "",
    adhar_no: "",
  });

  const navigate = useNavigate();

  const [errors, setErrors] = useState({
    fname: "",
    lname: "",
    email: "",
    password: "",
    contact: "",
    rid: "",
    area: "",
    city: "",
    pincode: "",
    state: "",
  });

  const [pwdVisible, setPwdVisible] = useState(false);

  const validate = (name, value) => {
    let newErrors = { ...errors };

    switch (name) {
      case "fname":
        newErrors.fname = value ? "" : "First Name is required.";
        break;
      case "lname":
        newErrors.lname = value ? "" : "Last Name is required.";
        break;
      case "email":
        const emailPattern = /^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[a-zA-Z]{2,}$/;
        newErrors.email = emailPattern.test(value) ? "" : "Please enter a valid email address.";
        break;
      case "password":
        const passwordPattern = /^(?=.*[A-Za-z])(?=.*\d)(?=.*[!@#$%^&*])[A-Za-z\d!@#$%^&*]{8,}$/;
        newErrors.password = passwordPattern.test(value)
          ? ""
          : "Password must be at least 8 characters long, contain a number and a special character.";
        break;
      case "contact":
        const contactPattern = /^[0-9]{10}$/;
        newErrors.contact = contactPattern.test(value)
          ? ""
          : "Contact number must be 10 digits.";
        break;
      case "rid":
        newErrors.rid = value ? "" : "Please select a role.";
        break;
      case "area":
        newErrors.area = value ? "" : "Area is required.";
        break;
      case "city":
        newErrors.city = value ? "" : "City is required.";
        break;
      case "pincode":
        const pincodePattern = /^[0-9]{6}$/;
        newErrors.pincode = pincodePattern.test(value)
          ? ""
          : "Pincode must be a valid 6-digit number.";
        break;
      case "state":
        newErrors.state = value ? "" : "State is required.";
        break;
      default:
        break;
    }

    setErrors(newErrors);
  };

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData((prev) => ({
      ...prev,
      [name]: value,
    }));

    validate(name, value);
  };

  const handleRoleChange = (e) => {
    const { value } = e.target;
    setFormData((prev) => ({
      ...prev,
      rid: value,
      isVendor: value === "2",
      foodLicenceNo: "",
      adhar_no: "",
    }));

    validate("rid", value);
  };

  const handleSubmit = (e) => {
    e.preventDefault();

    // Perform final validation check
    Object.keys(formData).forEach((key) => validate(key, formData[key]));

    if (Object.values(errors).every((error) => error === "")) {
      fetch("http://localhost:8102/api/vendors/RegUser", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(formData),
      })
        .then((response) => {
          if (response.ok) {
            alert("Data submitted successfully!");
            navigate("/Login");

          } else {
            alert("Error submitting data.");
          }
        })
        .catch((error) => {
          console.error("Error:", error);
          alert("Error submitting data.");
        });
    }
  };
console.log(formData)
  return (
    <div className="container mt-5">
      <div className="row justify-content-center">
        <div className="col-md-6">
          <div className="card shadow-sm">
            <div className="card-body">
              <h1 className="text-center mb-4">Registration Form</h1>
              <form onSubmit={handleSubmit}>
                {/* Existing Fields */}
                {/* First Name */}
                <div className="mb-3">
                  <label className="form-label">First Name:</label>
                  <input
                    type="text"
                    name="fname"
                    className="form-control form-control-sm"
                    value={formData.fname.trim()}
                    onChange={handleChange}
                  />
                  {errors.fname && <small className="text-danger">{errors.fname}</small>}
                </div>

                {/* Last Name */}
                <div className="mb-3">
                  <label className="form-label">Last Name:</label>
                  <input
                    type="text"
                    name="lname"
                    className="form-control form-control-sm"
                    value={formData.lname.trim()}
                    onChange={handleChange}
                  />
                  {errors.lname && <small className="text-danger">{errors.lname}</small>}
                </div>

                {/* Email */}
                <div className="mb-3">
                  <label className="form-label">Email:</label>
                  <input
                    type="email"
                    name="email"
                    className="form-control form-control-sm"
                    value={formData.email}
                    onChange={handleChange}
                  />
                  {errors.email && <small className="text-danger">{errors.email}</small>}
                </div>

                  {/* Password */}
                  <div className="mb-3">
                  <label className="form-label">Password:</label>
                  <div className="input-group input-group-sm">
                    <input
                      type={pwdVisible ? "text" : "password"}
                      name="password"
                      className="form-control"
                      value={formData.password.trim()}
                      onChange={handleChange}
                    />
                    <button
                      type="button"
                      className="btn btn-outline-secondary"
                      onClick={() => setPwdVisible(!pwdVisible)}
                    >
                      <i className={`fa ${pwdVisible ? "fa-eye-slash" : "fa-eye"}`}></i>
                    </button>
                  </div>
                  {errors.password && <small className="text-danger">{errors.password}</small>}
                </div>

                {/* Contact */}
                <div className="mb-3">
                  <label className="form-label">Contact:</label>
                  <input
                    type="text"
                    name="contact"
                    className="form-control form-control-sm"
                    value={formData.contact.trim()}
                    onChange={handleChange}
                  />
                  {errors.contact && <small className="text-danger">{errors.contact}</small>}
                </div>

                {/* Area */}
                <div className="mb-3">
                  <label className="form-label">Area:</label>
                  <input
                    type="text"
                    name="area"
                    className="form-control form-control-sm"
                    value={formData.area.trim()}
                    onChange={handleChange}
                  />
                  {errors.area && <small className="text-danger">{errors.area}</small>}
                </div>

                {/* City */}
                <div className="mb-3">
                  <label className="form-label">City:</label>
                  <input
                    type="text"
                    name="city"
                    className="form-control form-control-sm"
                    value={formData.city.trim()}
                    onChange={handleChange}
                  />
                  {errors.city && <small className="text-danger">{errors.city}</small>}
                </div>

                {/* Pincode */}
                <div className="mb-3">
                  <label className="form-label">Pincode:</label>
                  <input
                    type="text"
                    name="pincode"
                    className="form-control form-control-sm"
                    value={formData.pincode.trim()}
                    onChange={handleChange}
                  />
                  {errors.pincode && <small className="text-danger">{errors.pincode}</small>}
                </div>

                {/* State */}
                <div className="mb-3">
                  <label className="form-label">State:</label>
                  <input
                    type="text"
                    name="state"
                    className="form-control form-control-sm"
                    value={formData.state.trim()}
                    onChange={handleChange}
                  />
                  {errors.state && <small className="text-danger">{errors.state}</small>}
                </div>

                {/* Role Selection */}
                <div className="mb-3">
                  <label className="form-label">Role:</label>
                  <select
                    className="form-control form-control-sm"
                    name="rid"
                    value={formData.rid}
                    onChange={handleRoleChange}
                  >
                    <option value="">Select Role</option>
                    <option value="3">Customer</option>
                    <option value="2">Vendor</option>
                  </select>
                  {errors.rid && <small className="text-danger">{errors.rid}</small>}
                </div>

                {/* Vendor-Specific Fields */}
                {formData.rid === "2" && (
                  <>
                    <div className="mb-3">
                      <label className="form-label">Food Licence No:</label>
                      <input
                        type="text"
                        name="foodLicenceNo"
                        className="form-control form-control-sm"
                        value={formData.foodLicenceNo.trim()}
                        onChange={handleChange}
                      />
                    </div>
                    <div className="mb-3">
                      <label className="form-label">Aadhar Number:</label>
                      <input
                        type="text"
                        name="adhar_no"
                        className="form-control form-control-sm"
                        value={formData.adhar_no.trim()}
                        onChange={handleChange}
                      />
                    </div>
                  </>
                )}

                {/* Buttons */}
                <div className="d-flex justify-content-center mb-3">
                  <button type="submit" className="btn btn-primary btn-sm mr-2">
                    Submit
                  </button>
                  <button
                    type="reset"
                    className="btn btn-secondary btn-sm"
                    onClick={() =>
                      setFormData({
                        fname: "",
                        lname: "",
                        email: "",
                        rid: "",
                        area: "",
                        city: "",
                        pincode: "",
                        state: "",
                        password: "",
                        contact: "",
                        isVendor: false,
                        foodLicenceNo: "",
                        adhar_no: "",
                      })
                    }
                  >
                    Clear
                  </button>
                </div>
              </form>
            </div>
          </div>
        </div>
      </div>
     
    </div>

    
  );
}

export default UserVendorForm;
