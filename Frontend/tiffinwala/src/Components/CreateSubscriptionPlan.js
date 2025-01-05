import React, { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";

export default function CreateSubscriptionPlan() {
  const navigate = useNavigate();

  const [formData, setFormData] = useState({
    vid: "",
    name: "",
    description: "",
    price: "",
    isAvaliable: false, // Corrected field name here to match backend
    image: null,
  });

  const [message, setMessage] = useState("");

  // Retrieve vendorId from localStorage and set vid
  useEffect(() => {
    const vendorId = JSON.parse(localStorage.getItem("loggedVendor")).vendorId;
    if (vendorId) {
      setFormData((prev) => ({ ...prev, vid: vendorId }));
      console.log("Vendor ID found:", vendorId);
    } else {
      console.error("No Vendor found in localStorage!");
    }
  }, []);

  const handleChange = (e) => {
    const { name, value, type, checked } = e.target;

    if (type === "file") {
      setFormData({
        ...formData,
        [name]: e.target.files[0],
      });
    } else {
      setFormData({
        ...formData,
        [name]: type === "checkbox" ? checked : value,
      });
    }
  };

  const handleSubmit = (e) => {
    e.preventDefault();

    const { image, ...jsonFormData } = formData; // Exclude image for the first API call

    // First API call: Send form data as JSON
    fetch("http://localhost:8081/api/vendor-subscription-plans", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(jsonFormData),
    })
      .then((response) => {
        if (!response.ok) {
          throw new Error("Failed to create subscription plan.");
        }
        return response.json();
      })
      .then((createdPlan) => {
        console.log("Created Plan:", createdPlan);
        if (createdPlan && createdPlan.planId) {
          if (image) {
            // Second API call: Upload the image as multipart/form-data
            const imageData = new FormData();
            imageData.append("file", image);

            return fetch(
              `http://localhost:8081/api/vendor-subscription-plans/uploadImage/${createdPlan.planId}`,
              {
                method: "POST",
                body: imageData,
              }
            )
              .then((imageResponse) => {
                if (!imageResponse.ok) {
                  throw new Error("Image upload failed.");
                }
                return imageResponse.text(); // Expecting a non-JSON response
              })
              .then(() => {
                alert("Subscription plan created successfully with the image!");
                navigate("/vendor_home");
              })
              .catch((error) => {
                console.error("Image upload error:", error);
                alert(
                  "Plan created successfully, but image upload failed. Please try again later."
                );
                navigate("/vendor_home");
              });
          } else {
            alert("Subscription plan created successfully!");
            navigate("/vendor_home");
          }
        } else {
          throw new Error("Plan ID is missing in the response.");
        }
      })
      .catch((error) => {
        console.error("Error:", error);
        setMessage(error.message || "An error occurred while creating the plan.");
      });
  };

  return (
    <div
      style={{
        display: "flex",
        justifyContent: "center",
        alignItems: "center",
        height: "100vh",
        backgroundColor: "#f8f9fa",
      }}
    >
      <div className="card p-4 shadow" style={{ width: "100%", maxWidth: "400px" }}>
        <h3 className="text-center mb-4">Create Subscription Plan</h3>
        {message && <p className="alert alert-info">{message}</p>}
        <form onSubmit={handleSubmit}>
          <div className="mb-3">
            <label htmlFor="name" className="form-label">
              Plan Name
            </label>
            <input
              type="text"
              id="name"
              name="name"
              className="form-control form-control-sm"
              value={formData.name}
              onChange={handleChange}
              required
            />
          </div>
          <div className="mb-3">
            <label htmlFor="description" className="form-label">
              Description
            </label>
            <textarea
              id="description"
              name="description"
              className="form-control form-control-sm"
              value={formData.description}
              onChange={handleChange}
              required
            />
          </div>
          <div className="mb-3">
            <label htmlFor="price" className="form-label">
              Price
            </label>
            <input
              type="text"
              id="price"
              name="price"
              className="form-control form-control-sm"
              value={formData.price}
              onChange={handleChange}
              required
            />
          </div>
          <div className="mb-3 form-check">
            <input
              type="checkbox"
              id="isAvaliable"
              name="isAvaliable"
              className="form-check-input"
              checked={formData.isAvaliable}
              {...console.log("checked valie "+formData.checked)}
              onChange={handleChange}
            />
            <label htmlFor="isAvaliable" className="form-check-label">
              Is Available
            </label>
          </div>
          <div className="mb-3">
            <label htmlFor="image" className="form-label">
              Image
            </label>
            <input
              type="file"
              id="image"
              name="image"
              className="form-control form-control-sm"
              onChange={handleChange}
            />
          </div>
          <button type="submit" className="btn btn-primary btn-sm w-100">
            Create Plan
          </button>
        </form>
      </div>
    </div>
  );
}
