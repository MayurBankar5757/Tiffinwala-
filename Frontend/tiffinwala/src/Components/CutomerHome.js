import { Link } from "react-router-dom";
import React, { useEffect, useState } from "react";

export default function CustomerHome() {
  const [plans, setPlans] = useState([]);
  const [error, setError] = useState("");
  const [userInfo, setUserInfo] = useState(null); // Store user info
  console.log(userInfo)

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

  // Function to handle subscription
  const handleSubscribe = (subscriptionPlanId) => {
    if (!userInfo) {
      alert("Please log in to subscribe.");
      return;
    }

    const subscriptionData = {
      userId: userInfo.uid, 
      subscriptionPlanId: subscriptionPlanId,
    };

    fetch("http://localhost:8102/api/subscriptions/subscribePlan", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(subscriptionData),
    })
      .then((response) => {
        if (!response.ok) {
          throw new Error(`HTTP error! Status: ${response.status}`);
        }
        return response.json();
      })
      .then((data) => {
        alert("Subscription successful!");
        console.log("Subscription response:", data);
      })
      .catch((error) => {
        console.error("Error subscribing to plan:", error);
        alert("Failed to subscribe. Please try again.");
      });
  };

  return (
    <div>
      {/* Display customer info */}
      {userInfo && (
        <div className="alert alert-info">
          <h5><strong>Welcome, {userInfo.fname} {userInfo.lname}!</strong></h5>
          <p><strong>Delivering to your address : </strong>{userInfo.address.area}, {userInfo.address.city}, {userInfo.address.state} - {userInfo.address.pincode}</p>
          <div className="d-flex justify-content-between">
            {/* Update Profile Button */}
            <Link to="/UpdateCustomerProfile" className="btn btn-warning btn-sm">
              Update Profile
            </Link>
          </div>
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
                    {/* Subscribe Button */}
                    <button
                      className="btn btn-success btn-sm mt-2"
                      onClick={() => handleSubscribe(plan.planId)}
                      disabled={!plan.isAvailable}
                    >
                      Subscribe
                    </button>
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