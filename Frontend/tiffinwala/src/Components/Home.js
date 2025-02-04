import React, { useEffect, useState } from "react";
import { Link } from "react-router-dom";

export default function Home() {
  const [plans, setPlans] = useState([]);
  const [error, setError] = useState("");

  useEffect(() => {
    fetch("http://localhost:8102/api/vendor-subscription-plans/getAllSubcriptionPlan")
      .then((response) => {
        if (!response.ok) {
          throw new Error(`HTTP error! Status: ${response.status}`);
        }
        return response.json();
      })
      .then((data) => {
        setPlans(data);
        console.log("Fetched subscription plans:", data);
      })
      .catch((error) => {
        console.error("Error fetching subscription plans:", error);
        setError("Failed to fetch subscription plans.");
      });
  }, []);

  // Debugging: Logs whenever `plans` updates
  useEffect(() => {
    console.log("Updated plans:", plans);
  }, [plans]);

  return (
    <div>
      <h1>Home Component</h1>
      {error && <p className="text-danger">{error}</p>}
      <div className="container mt-4">
        <div className="row">
          {plans.map((plan, index) => (
            <div className="col-md-4 mb-4" key={index}>
              <div className="card shadow-sm h-100">
                {/* ✅ Ensure Base64 Image is Rendered Correctly */}
                {plan.image ? (
                  <img
                    src={`data:image/png;base64,${plan.image}`}
                    className="card-img-top"
                    alt={plan.name}
                    style={{ height: "200px", objectFit: "cover" }}
                  />
                ) : (
                  <div
                    style={{
                      height: "200px",
                      backgroundColor: "#f8f9fa",
                      display: "flex",
                      alignItems: "center",
                      justifyContent: "center",
                      fontSize: "14px",
                      color: "#888",
                    }}
                  >
                    No Image Available
                  </div>
                )}

                <div className="card-body">
                  <h5 className="card-title">{plan.name}</h5>
                  <p className="card-text">{plan.description}</p>
                  <p className="card-text">
                    <strong>Price:</strong> ₹{plan.price}
                  </p>
                  {/* ✅ Status Badge & Button in Same Line */}
                  <div className="d-flex justify-content-between align-items-center">
                    <span className={`badge ${plan.isAvailable ? "bg-success" : "bg-danger"}`}>
                      {plan.isAvailable ? "Available" : "Not Available"}
                    </span>
                    <Link
                      to={`/VendorPlanDetails/${plan.planId}`}
                      className="btn btn-primary btn-sm"
                    >
                      Get Details
                    </Link>
                  </div>
                </div>
              </div>
            </div>
          ))}
        </div>
      </div>
    </div>
  );
}
