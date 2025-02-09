import React, { useEffect, useState } from "react";
import { useParams, Link } from 'react-router-dom';

export default function PlanDetails() {
  const { id } = useParams();
  const [plan, setPlan] = useState(null);
  const [error, setError] = useState("");
  const jwtToken = localStorage.getItem("jwtToken");

  useEffect(() => {
    if (!jwtToken) {
      setError("Authentication required");
      return;
    }

    fetch(`http://localhost:8103/api/vendor-subscription-plans/getSubscriptionPlanById/${id}`, {
      headers: {
        Authorization: `Bearer ${jwtToken}`,
        "Content-Type": "application/json"
      }
    })
    .then((response) => {
      if (!response.ok) {
        return response.json().then(err => {
          throw new Error(err.message || 'Failed to fetch plan');
        });
      }
      return response.json();
    })
    .then((data) => {
      setPlan(data);
    })
    .catch((error) => {
      console.error("Fetch error:", error);
      setError(error.message);
    });
  }, [id, jwtToken]);

  if (error) {
    return (
      <div className="container mt-5 text-center">
        <p className="text-danger">{error}</p>
        <Link to="/customer_home" className="btn btn-secondary mt-3">
          Back to Plans
        </Link>
      </div>
    );
  }

  if (!plan) {
    return <div className="text-center mt-5">Loading...</div>;
  }

  return (
    <div className="container mt-5">
      <h2 className="mb-4">{plan.name}</h2>
      <img
        src={`data:image/png;base64,${plan.image}`}
        className="img-fluid rounded shadow"
        alt={plan.name}
        style={{ maxHeight: "300px", objectFit: "cover" }}
      />
      <div className="mt-4">
        <h5 className="text-muted">{plan.duration.replace('_', ' ').toLowerCase()}</h5>
        <p className="lead">{plan.description}</p>
        <p className="h4">
          <strong>Price:</strong> ₹{plan.price}
        </p>
        <span className={`badge ${plan.isAvailable ? "bg-success" : "bg-danger"} fs-6`}>
          {plan.isAvailable ? "Available" : "Not Available"}
        </span>
      </div>
      <div className="mt-4">
        <Link to="/customer_home" className="btn btn-secondary">
          Back to Plans
        </Link>
      </div>
    </div>
  );
}