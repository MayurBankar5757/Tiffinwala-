import React, { useEffect, useState } from "react";
import { useParams, Link } from "react-router-dom";

export default function VendorPlanDetails() {
  const { id } = useParams(); // Get the plan ID from the URL
  const [plan, setPlan] = useState(null);
  const [tiffins, setTiffins] = useState([]); // To store tiffin details
  const [error, setError] = useState("");
  const [userInfo, setUserInfo] = useState(null); // Store user info

  // Fetch the plan details
  useEffect(() => {
    fetch(`http://localhost:8102/api/vendor-subscription-plans/${id}`)
      .then((response) => {
        if (!response.ok) {
          throw new Error(`HTTP error! Status: ${response.status}`);
        }
        return response.json();
      })
      .then((data) => {
        setPlan(data);
      })
      .catch((error) => {
        console.error("Error fetching subscription plan details:", error);
        setError("Failed to fetch plan details.");
      });

    // Fetch tiffins related to the plan
    fetch(`http://localhost:8102/api/tiffins/plan/${id}`)
      .then((response) => {
        if (!response.ok) {
          throw new Error(`HTTP error! Status: ${response.status}`);
        }
        return response.json();
      })
      .then((data) => {
        setTiffins(data);
      })
      .catch((error) => {
        console.error("Error fetching tiffins:", error);
        setError("Failed to fetch tiffins.");
      });

    // Fetch user info from localStorage
    const storedUser = JSON.parse(localStorage.getItem("loggedUser"));
    if (storedUser) {
      setUserInfo(storedUser); // Set user info if available
    }
  }, [id]); // Re-run when `id` changes

  // Debugging: Logs whenever `plan` or `tiffins` updates
  useEffect(() => {
    console.log("Updated plan:", plan);
    console.log("Updated tiffins:", tiffins);
  }, [plan, tiffins]);

  // Function to handle subscription
  const handleSubscribe = () => {
    if (!userInfo) {
      alert("Please log in to subscribe.");
      return;
    }

    const subscriptionData = {
      userId: userInfo.uid, // Assuming the user object has a userId field
      subscriptionPlanId: id, // Use the plan ID from the URL
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

  if (error) {
    return <p className="text-danger text-center mt-5">{error}</p>;
  }

  if (!plan) {
    return <p className="text-center mt-5">Loading...</p>;
  }

  return (
    <div className="container mt-5">
      <div className="row justify-content-center">
        <div className="col-md-10">
          <div className="card shadow-lg border-0 rounded-4">
            <div className="card-body p-5">
              <h2 className="text-center mb-4 display-4 font-weight-bold text-primary">
                {plan.name}
              </h2>
              <div className="text-center mb-4">
                {plan.image ? (
                  <img
                    src={`data:image/png;base64,${plan.image}`}
                    className="img-fluid rounded-4"
                    alt={plan.name}
                    style={{ maxHeight: "400px", objectFit: "cover" }}
                  />
                ) : (
                  <div
                    className="d-flex align-items-center justify-content-center"
                    style={{
                      height: "400px",
                      backgroundColor: "#f8f9fa",
                      borderRadius: "10px",
                      fontSize: "1.2rem",
                      color: "#888",
                    }}
                  >
                    No Image Available
                  </div>
                )}
              </div>

              <div className="mt-4 text-center">
                <h5 className="text-muted mb-3">{plan.duration}</h5>
                <p className="lead">{plan.description}</p>
                <p className="h4">
                  <strong className="me-2">Price:</strong>
                  <span className="text-success">₹{plan.price}</span>
                </p>
                <span
                  className={`badge ${
                    plan.isAvailable ? "bg-success" : "bg-danger"
                  } p-3 mt-2`}
                  style={{ fontSize: "1.2rem" }}
                >
                  {plan.isAvailable ? "Available" : "Not Available"}
                </span>
              </div>

              {/* Subscribe Button */}
              <div className="text-center mt-5">
                <button
                  className="btn btn-success btn-lg px-5 py-3"
                  onClick={handleSubscribe}
                  disabled={!plan.isAvailable}
                >
                  Subscribe
                </button>
                <Link
                  to="/customer_home"
                  className="btn btn-outline-secondary btn-lg px-5 py-3 ms-3"
                >
                  Back to Plans
                </Link>
              </div>

              {/* Tiffins Section */}
              <div className="mt-6">
                <h3 className="text-center mb-5 display-5 font-weight-bold text-primary">
                  Tiffins Included in this Plan
                </h3>
                <div className="row">
                  {tiffins.map((tiffin) => (
                    <div key={tiffin.tiffinId} className="col-md-6 col-lg-4 mb-4">
                      <div className="card shadow-sm border-0 rounded-4 h-100">
                        <div className="card-body p-4">
                          <h5 className="card-title text-primary mb-3">
                            {tiffin.name}
                          </h5>
                          <h6 className="card-subtitle mb-3 text-muted">
                            {tiffin.day}
                          </h6>
                          <p className="card-text">
                            <strong>Food Type: </strong>
                            <span className="badge bg-info">
                              {tiffin.foodType || "Not specified"}
                            </span>
                          </p>
                          <p className="card-text">
                            <strong>Description: </strong>
                            {tiffin.description || "No description available."}
                          </p>
                        </div>
                      </div>
                    </div>
                  ))}
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}