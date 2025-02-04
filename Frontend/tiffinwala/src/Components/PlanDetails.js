import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import { Link } from 'react-router-dom'; 

export default function PlanDetails() {

    const { id } = useParams(); // Get the plan ID from the URL
    const [plan, setPlan] = useState(null);
    const [error, setError] = useState("");
  
    useEffect(() => {
      fetch(`http://localhost:8102/api/vendor-subscription-plans/getSubscriptionPlanById/${id}`)
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
    }, [id]); // Re-run when `id` changes
  
    if (error) {
      return <p className="text-danger">{error}</p>;
    }
  
    if (!plan) {
      return <p>Loading...</p>;
    }
  return (
      <div> 
       <div className="container mt-6">
      <h2>{plan.name}</h2>
      <img
        src={plan.image}
        className="img-fluid"
        alt={plan.name}
        style={{ maxHeight: "300px", objectFit: "cover" }}
      />
      <h5 className="mt-3">{plan.duration}</h5>
      <p>{plan.description}</p>
      <p>
        <strong>Price:</strong> ₹{plan.price}
      </p>
      <span className={`badge ${plan.isAvailable ? "bg-success" : "bg-danger"}`}>
        {plan.isAvailable ? "Available" : "Not Available"}
      </span>
      <br />
      <Link to="/customer_home" className="btn btn-secondary mt-3">
        Back to Plans
      </Link>
    </div>
      </div>
  );

}