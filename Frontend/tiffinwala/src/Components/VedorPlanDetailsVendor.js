import React, { useEffect, useState } from "react";
import { useParams, Link } from "react-router-dom";

export default function VendorMyPlanDetails() {
  const { id } = useParams();
  const [plan, setPlan] = useState(null);
  const [tiffins, setTiffins] = useState([]);
  const [error, setError] = useState("");
  const jwtToken = localStorage.getItem("jwtToken");

  useEffect(() => {
    console.log("JWT Token:", jwtToken); // Verify token existence
    
    if (!jwtToken) {
      setError("Unauthorized access. Please log in.");
      return;
    }

    const fetchData = async () => {
      try {
        // Fetch plan details
        const planResponse = await fetch(
          `http://localhost:8104/api2/vsp/${id}`,
          {
            headers: {
              Authorization: `Bearer ${jwtToken}`,
            },
          }
        );

        if (!planResponse.ok) {
          const errorData = await planResponse.json();
          throw new Error(errorData.message || "Failed to fetch plan details");
        }

        const planData = await planResponse.json();
        console.log("Plan details:", planData);
        setPlan(planData);

        // Fetch tiffins
        const tiffinsResponse = await fetch(
          `http://localhost:8104/api2/tiffin/plan/${id}`,
          {
            headers: {
              Authorization: `Bearer ${jwtToken}`,
            },
          }
        );

        if (!tiffinsResponse.ok) {
          const errorData = await tiffinsResponse.json();
          throw new Error(errorData.message || "Failed to fetch tiffins");
        }

        const tiffinsData = await tiffinsResponse.json();
        console.log("Tiffins data:", tiffinsData);
        setTiffins(tiffinsData);

        // Set user info
        
      } catch (error) {
        console.error("Fetch error:", error);
        setError(error.message);
      }
    };

    fetchData();
  }, [id, jwtToken]);



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
              
              {/* Fixed Image Rendering */}
              <div className="text-center mb-4">
                {plan.image ? (
                  <img
                    src={
                      plan.image.startsWith("data:image")
                        ? plan.image
                        : `data:image/jpeg;base64,${plan.image}`
                    }
                    className="img-fluid rounded-4"
                    alt={plan.name}
                    style={{ maxHeight: "400px", objectFit: "cover" }}
                  />
                ) : (
                  <div className="no-image-placeholder">
                    No Image Available
                  </div>
                )}
              </div>

              {/* Plan Details */}
              <div className="mt-4 text-center">
                <h5 className="text-muted mb-3">{plan.duration}</h5>
                <p className="lead">{plan.description}</p>
                <p className="h4">
                  <strong className="me-2">Price:</strong>
                  <span className="text-success">₹{plan.price}</span>
                </p>
                <span
                  className={`badge ${plan.isAvailable ? "bg-success" : "bg-danger"} p-3 mt-2`}
                >
                  {plan.isAvailable ? "Available" : "Not Available"}
                </span>
              </div>

              {/* Subscription Button */}
              <div className="text-center mt-5">
                <Link
                  to="/vendorAllPlans"
                  className="btn btn-outline-secondary btn-lg px-5 py-3 ms-3"
                >
                  Back to Plans
                </Link>
              </div>

              {/* Tiffins Section */}
              <div className="mt-6">
                <h3 className="text-center mb-5 display-5 font-weight-bold text-primary">
                  Tiffins Included
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
                            {tiffin.description || "No description available."}
                          </p>
                        </div>
                      </div>
                    </div>
                  ))}
                  {tiffins.length === 0 && (
                    <p className="text-center text-muted">No tiffins available</p>
                  )}
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}