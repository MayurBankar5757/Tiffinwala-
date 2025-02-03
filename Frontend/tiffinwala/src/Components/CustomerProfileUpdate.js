import React, { useState, useEffect } from "react";
import { useParams, useNavigate } from "react-router-dom";

function UpdateCustomerForm() {
    const navigate = useNavigate();
    const uid = JSON.parse(localStorage.getItem("loggedUser")).uid;

    const [formData, setFormData] = useState({
        fname: "",
        lname: "",
        email: "",
        roleId: "",
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

    const [errors, setErrors] = useState({
        fname: "",
        lname: "",
        email: "",
        password: "",
        contact: "",
        roleId: "",
        area: "",
        city: "",
        pincode: "",
        state: "",
    });

    const [pwdVisible, setPwdVisible] = useState(false);

    // Fetch the existing user data when the component mounts
    useEffect(() => {
        fetch(`http://localhost:8081/api/users/${uid}`)
            .then((response) => response.json())
            .then((data) => {
                setFormData({
                    fname: data.fname,
                    lname: data.lname,
                    email: data.email,
                    roleId: data.role.roleId, // Set roleId to rid
                    area: data.address.area,
                    city: data.address.city,
                    pincode: data.address.pincode,
                    state: data.address.state,
                    password: data.password,
                    contact: data.contact,
                    isVendor: data.role.roleId === 1,
                    foodLicenceNo: "", // Assuming this field is empty initially
                    adhar_no: "", // Assuming this field is empty initially
                });
            })
            .catch((error) => {
                console.error("Error fetching user data:", error);
            });
    }, [uid]);

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
            case "roleId":
                newErrors.roleId = value ? "" : "Please select a role.";
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

    const handleSubmit = (e) => {
        e.preventDefault();

        // Perform final validation check
        Object.keys(formData).forEach((key) => validate(key, formData[key]));

        if (Object.values(errors).every((error) => error === "")) {
            console.log("submitted : ", formData)
            fetch(`http://localhost:8081/api/users/${uid}`, {
                method: "PUT",
                headers: {
                    "Content-Type": "application/json",
                },
                body: JSON.stringify(formData),
            })
                .then((response) => {
                    if (response.ok) {
                        alert("User updated successfully! Login again ");
                        navigate("/Login"); // Redirect to the users list or another page
                    } else {
                        alert("Error updating user.");
                    }
                })
                .catch((error) => {
                    console.error("Error:", error);
                    alert("Error updating user.");
                });
        }
    };

    return (
        <div className="container mt-5">
            <div className="row justify-content-center">
                <div className="col-md-6">
                    <div className="card shadow-sm">
                        <div className="card-body">
                            <h1 className="text-center mb-4">Update User</h1>
                            <form onSubmit={handleSubmit}>
                                {/* First Name */}
                                <div className="mb-3">
                                    <label className="form-label">First Name:</label>
                                    <input
                                        type="text"
                                        name="fname"
                                        className="form-control form-control-sm"
                                        value={formData.fname}
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
                                        value={formData.lname}
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
                                        className="form-control form-control-sm info"
                                        value={formData.email}
                                        readOnly
                                        style={{
                                            backgroundColor: "#f7f7f7",  // Light grey background to indicate it's read-only
                                            color: "#6c757d",             // Gray text color to signify it's not editable
                                            borderColor: "#ced4da",       // Lighter border color
                                        }}
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
                                            value={formData.password}
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
                                        value={formData.contact}
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
                                        value={formData.area}
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
                                        value={formData.city}
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
                                        value={formData.pincode}
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
                                        value={formData.state}
                                        onChange={handleChange}
                                    />
                                    {errors.state && <small className="text-danger">{errors.state}</small>}
                                </div>

                                {/* Role (Non-editable) */}
                                <div className="mb-3">
                                    <label className="form-label">Role:</label>
                                    <input
                                        type="text"
                                        className="form-control form-control-sm"
                                        value={formData.roleId === 2 ? "Vendor" : "Customer"} // Display role as text
                                        readOnly // Disable editing the role
                                        style={{
                                            backgroundColor: "#f7f7f7",  // Light grey background to indicate it's read-only
                                            color: "#6c757d",             // Gray text color to signify it's not editable
                                            borderColor: "#ced4da",       // Lighter border color
                                        }}
                                    />
                                    {errors.roleId && <small className="text-danger">{errors.roleId}</small>}
                                </div>

                                {/* Buttons */}
                                <div className="d-flex justify-content-center mb-3">
                                    <button type="submit" className="btn btn-primary btn-sm mr-2">
                                        Update
                                    </button>
                                    <button
                                        type="reset"
                                        className="btn btn-secondary btn-sm"
                                        onClick={() =>
                                            setFormData({
                                                fname: "",
                                                lname: "",
                                                email: "",
                                                roleId: "",
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

export default UpdateCustomerForm;
