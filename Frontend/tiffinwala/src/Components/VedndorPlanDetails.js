import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import { Link } from 'react-router-dom';

export default function VendorPlanDetails() {
    const { id } = useParams(); // Get the plan ID from the URL
    const [plan, setPlan] = useState(null);
    const [tiffins, setTiffins] = useState([]); // To store tiffin details
    const [error, setError] = useState("");

    // Fetch the plan details
    useEffect(() => {
        fetch(`http://localhost:8081/api/vendor-subscription-plans/${id}`)
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
        fetch(`http://localhost:8081/api/tiffins/plan/${id}`)
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
    }, [id]); // Re-run when `id` changes

    if (error) {
        return <p className="text-danger">{error}</p>;
    }

    if (!plan) {
        return <p>Loading...</p>;
    }

    return (
        <div className="container mt-6">
            <div className="row">
                <div className="col-md-8 mx-auto">
                    <h2 className="text-center mb-4">{plan.name}</h2>
                    <div className="text-center mb-4">
                        <img
                            src={plan.image}
                            className="img-fluid"
                            alt={plan.name}
                            style={{ maxHeight: "350px", objectFit: "cover", borderRadius: "10px" }}
                        />
                    </div>
                    <div className="mt-4">
                        <h5 className="text-muted">{plan.duration}</h5>
                        <p>{plan.description}</p>
                        <p>
                            <strong className="me-2">Price:</strong>
                            <span className="text-success">₹{plan.price}</span>
                        </p>
                        <span
                            className={`badge ${plan.isAvailable ? "bg-success" : "bg-danger"}`}
                            style={{ fontSize: "1rem", padding: "8px 15px" }}
                        >
                            {plan.isAvailable ? "Available" : "Not Available"}
                        </span>
                    </div>
                    <div className="text-center mt-5">
                        <Link to="/customer_home" className="btn btn-secondary px-4 py-2">
                            Back to Plans
                        </Link>
                    </div>

                    {/* Tiffins Section */}
                    <div className="mt-6">
                        <h3 className="text-center mb-5 text-primary">Tiffins Included in this Plan</h3>
                        <div className="row">
                            {tiffins.map((tiffin) => (
                                <div key={tiffin.tiffinId} className="col-md-4 mb-4">
                                    <div className="card shadow-lg border-0 rounded-3">
                                        <img
                                            src="https://via.placeholder.com/300x200" // You can use an actual image URL here
                                            className="card-img-top"
                                            alt={tiffin.name}
                                            style={{ borderTopLeftRadius: '0.5rem', borderTopRightRadius: '0.5rem', height: '200px', objectFit: 'cover' }}
                                        />
                                        <div className="card-body">
                                            <h5 className="card-title text-primary">{tiffin.name}</h5>
                                            <h6 className="card-subtitle mb-3 text-muted">{tiffin.day}</h6>
                                            <p className="card-text">
                                                <strong>Food Type: </strong>
                                                <span className="badge bg-info">{tiffin.foodType || "Not specified"}</span>
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
    );
}
