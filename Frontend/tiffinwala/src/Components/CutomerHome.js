import { Link } from "react-router-dom";
import React, { useEffect, useState } from "react";

export default function CustomerHome() {
  const [plans, setPlans] = useState([]);
  const [error, setError] = useState("");
  const [userInfo, setUserInfo] = useState(null); // Store user info


  useEffect(() => {
    fetch("http://localhost:8081/api/vendor-subscription-plans/getAllSubcriptionPlan")
      .then((response) => {
        if (!response.ok) {
          throw new Error(`HTTP error! Status: ${response.status}`);
        }
        return response.json();
      })
      .then((data) => {
        setPlans(data);
      })
      .catch((error) => {
        console.error("Error fetching subscription plans:", error);
        setError("Failed to fetch subscription plans.");
      });
      const storedUser = JSON.parse(localStorage.getItem("loggedUser"));
     if (storedUser) {
      setUserInfo(storedUser); // Set user info if available
    }
  }, []);

  return (
    <div>
       {/* Display customer info */}
       {userInfo && (
          <div className="alert alert-info">
            <h5><strong>Welcome, {userInfo.fname} {userInfo.lname}!</strong></h5>
            <p> <strong>Delivering to your address : </strong>{userInfo.address.area}, {userInfo.address.city}, {userInfo.address.state} - {userInfo.address.pincode}   </p>
          </div>
        )}
      <main className="container mt-6">
        {error && <p className="text-danger">{error}</p>}
        <div className="container mt-4">
          <div className="row">
            {plans.map((plan, index) => (
              <div className="col-md-4 mb-4" key={index}>
                <div className="card shadow-sm h-100">
                  <img
                    src={plan.image}
                    className="card-img-top"
                    alt={plan.name}
                    style={{ height: "200px", objectFit: "cover" }}
                  />
                  <div className="card-body">
                    <h5 className="card-title">{plan.name}</h5>
                    <h6 className="card-text badge bg-info">{plan.duration}</h6>
                    
                    <p className="card-text">{plan.description}</p>
                    <p className="card-text">
                      <strong>Price:</strong> ₹{plan.price}
                    </p>
                    <div className="d-flex justify-content-between align-items-center">
                      <span className={`badge ${plan.isAvailable ? "bg-success" : "bg-danger"}`}>
                        {plan.isAvailable ? "Available" : "Not Available"}
                      </span>
                      <Link to={`/VendorPlanDetails/${plan.planId}`} className="btn btn-primary btn-sm">
                        Get Details
                      </Link>
                    </div>
                  </div>
                </div>
              </div>
            ))}
          </div>
        </div>
      </main>
    </div>
  );
}
