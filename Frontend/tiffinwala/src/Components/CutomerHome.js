import { Link, useNavigate } from "react-router-dom";
import React, { useEffect, useState } from "react";

export default function CustomerHome() {
  const [plans, setPlans] = useState([]);
  const [error, setError] = useState("");
  const [userInfo, setUserInfo] = useState(null);
  const navigate = useNavigate();

  useEffect(() => {
    // Retrieve user info and JWT token from localStorage.
    const storedUser = JSON.parse(localStorage.getItem("loggedUser"));
    const jwtToken = localStorage.getItem("jwtToken");
    if (storedUser && jwtToken) {
      setUserInfo(storedUser);
    } else {
      // Redirect to login if no user info or token is found.
      navigate("/login");
      return;
    }

    // Fetch subscription plans using the JWT token.
    fetch("http://localhost:8103/api/vendor-subscription-plans/getAllSubcriptionPlan", {
      headers: {
        Authorization: `Bearer ${jwtToken}`,
      },
    })
      .then((response) => {
        if (!response.ok) {
          throw new Error(`HTTP error! Status: ${response.status}`);
        }
        return response.json();
      })
      .then((data) => setPlans(data))
      .catch((error) => {
        console.error("Error fetching subscription plans:", error);
        setError("Failed to fetch subscription plans.");
      });
  }, [navigate]);

  // Function to handle subscription.
  const handleSubscribe = (subscriptionPlanId) => {
    const jwtToken = localStorage.getItem("jwtToken");

    if (!userInfo || !jwtToken) {
      alert("Please log in to subscribe.");
      navigate("/login");
      return;
    }


    const subscriptionData = {
      userId: userInfo.uid,
      subscriptionPlanId: subscriptionPlanId,
    };

    fetch("http://localhost:8103/api/subscriptions/subscribePlan", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
        Authorization: `Bearer ${jwtToken}`,
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
          <h5>
            <strong>
              Welcome, {userInfo.fname} {userInfo.lname}!
            </strong>
          </h5>
          <p>
            <strong>Delivering to your address:</strong>{" "}
            {userInfo.address?.area}, {userInfo.address?.city},{" "}
            {userInfo.address?.state} - {userInfo.address?.pincode}
          </p>
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
            {plans.map((plan) => {
              // Log the beginning of the image data for debugging.
              if (plan.image) {
              }
              return (
                <div className="col-md-4 mb-4" key={plan.planId}>
                  <div className="card shadow-sm h-100">
                    {/* Adjust the MIME type here: use "image/png" if the image is PNG */}
                    {plan.image ? (
                      <img
                        src={`data:image/jpg;base64,${plan.image}`}
                        className="card-img-top"
                        alt={plan.name}
                        style={{ height: "200px", objectFit: "cover" }}
                        onError={(e) => {
                          // Optionally, set a fallback image if desired:
                          // e.target.src = "/path/to/fallback-image.png";
                        }}
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
              );
            })}
          </div>
        </div>
      </main>
    </div>
  );
}
