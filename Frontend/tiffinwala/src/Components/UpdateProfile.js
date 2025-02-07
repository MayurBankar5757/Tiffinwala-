import React, { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";

function UpdateUserForm() {
  const navigate = useNavigate();
  const loggedUser = JSON.parse(localStorage.getItem("loggedUser"));
  const uid = loggedUser?.uid;
  const jwtToken = localStorage.getItem("jwtToken");

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

  const [errors, setErrors] = useState({});
  const [pwdVisible, setPwdVisible] = useState(false);

  useEffect(() => {
    if (!loggedUser || !jwtToken) {
      navigate("/login");
      return;
    }

    const fetchUserData = async () => {
      try {
        const response = await fetch(`http://localhost:8103/api/users/${uid}`, {
          headers: { Authorization: `Bearer ${jwtToken}` },
        });

        if (!response.ok) {
          throw new Error(`Error: ${response.status}`);
        }

        const data = await response.json();
        setFormData({
          fname: data.fname,
          lname: data.lname,
          email: data.email,
          roleId: data.role?.roleId || "",
          area: data.address?.area || "",
          city: data.address?.city || "",
          pincode: data.address?.pincode || "",
          state: data.address?.state || "",
          password: data.password,
          contact: data.contact,
          isVendor: data.role?.roleId === 2,
          foodLicenceNo: "",
          adhar_no: "",
        });
      } catch (error) {
        console.error("Error fetching user data:", error);
      }
    };

    fetchUserData();
  }, [uid, jwtToken, loggedUser, navigate]);

  const validate = (name, value) => {
    let message = "";
    switch (name) {
      case "fname":
      case "lname":
        if (!value.trim()) message = `${name === "fname" ? "First" : "Last"} name is required.`;
        break;
      case "email":
        if (!/^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,}$/.test(value))
          message = "Invalid email address.";
        break;
      case "password":
        if (!/^(?=.*[A-Za-z])(?=.*\d)(?=.*[!@#$%^&*]).{8,}$/.test(value))
          message = "Password must be 8+ characters, include a number & special character.";
        break;
      case "contact":
        if (!/^\d{10}$/.test(value)) message = "Contact number must be 10 digits.";
        break;
      case "pincode":
        if (!/^\d{6}$/.test(value)) message = "Pincode must be 6 digits.";
        break;
      case "area":
      case "city":
      case "state":
      case "roleId":
        if (!value.trim()) message = `${name.charAt(0).toUpperCase() + name.slice(1)} is required.`;
        break;
      default:
        break;
    }

    setErrors((prev) => ({ ...prev, [name]: message }));
  };

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData((prev) => ({ ...prev, [name]: value }));
    validate(name, value);
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    // Validate all fields before submission
    Object.keys(formData).forEach((key) => validate(key, formData[key]));

    if (Object.values(errors).some((msg) => msg !== "")) {
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
        body: JSON.stringify(formData),
      });

      if (!response.ok) {
        throw new Error(`Update failed: ${response.status}`);
      }

      alert("User updated successfully!");
      navigate("/vendor_home");
    } catch (error) {
      console.error("Error updating user:", error);
      alert("Error updating user.");
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
                {["fname", "lname", "email", "password", "contact", "area", "city", "pincode", "state"].map((field) => (
                  <div className="mb-3" key={field}>
                    <label className="form-label">{field.charAt(0).toUpperCase() + field.slice(1)}:</label>
                    <input
                      type={field === "password" && !pwdVisible ? "password" : "text"}
                      name={field}
                      className="form-control form-control-sm"
                      value={formData[field]}
                      onChange={handleChange}
                      readOnly={field === "email"}
                      style={field === "email" ? { backgroundColor: "#f7f7f7", color: "#6c757d" } : {}}
                    />
                    {errors[field] && <small className="text-danger">{errors[field]}</small>}
                  </div>
                ))}

                {/* Password Visibility Toggle */}
                <div className="mb-3">
                  <button type="button" className="btn btn-outline-secondary btn-sm" onClick={() => setPwdVisible(!pwdVisible)}>
                    {pwdVisible ? "Hide Password" : "Show Password"}
                  </button>
                </div>

                <div className="d-grid">
                  <button type="submit" className="btn btn-primary btn-sm">Update</button>
                </div>
              </form>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}

export default UpdateUserForm;
