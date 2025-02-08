import { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";

export default function EnabledPlans() {
    const [enabledPlans, setEnabledPlans] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    const navigate = useNavigate();

    useEffect(() => {
        const fetchEnabledPlans = async () => {
            try {
                const loggedUser = JSON.parse(localStorage.getItem("loggedUser"));
                const token = localStorage.getItem("jwtToken");

                if (!loggedUser || !loggedUser.uid) {
                    throw new Error("User not logged in or UID missing.");
                }

                const userId = parseInt(loggedUser.uid, 10);
                if (isNaN(userId)) {
                    throw new Error("Invalid user ID format.");
                }

                const vendorResponse = await fetch(`http://localhost:8103/api/vendors/vendor/${userId}`, {
                    headers: {
                        "Content-Type": "application/json",
                        Authorization: `Bearer ${token}`
                    }
                });

                if (!vendorResponse.ok) {
                    throw new Error("Failed to fetch vendor details.");
                }

                const vendorData = await vendorResponse.json();
                if (!vendorData || !vendorData.vendorId) {
                    throw new Error("Vendor ID not found.");
                }

                const vendorId = vendorData.vendorId;

                const response = await fetch(`http://localhost:8103/api/vendor-subscription-plans/vendor/${vendorId}/enabled`, {
                    headers: {
                        "Content-Type": "application/json",
                        Authorization: `Bearer ${token}`
                    }
                });

                if (response.status === 204) {
                    setEnabledPlans([]);
                } else if (!response.ok) {
                    throw new Error("Failed to fetch enabled plans.");
                } else {
                    const data = await response.json();
                    setEnabledPlans(data);
                }
            } catch (err) {
                setError(err.message);
            } finally {
                setLoading(false);
            }
        };

        fetchEnabledPlans();
    }, []);

    if (loading) return <p>Loading...</p>;
    if (error) return <p className="text-danger">{error}</p>;

    return (
        <div className="container my-4">
            <h3>Enabled Subscription Plans</h3>
            {enabledPlans.length === 0 ? (
                <p>No enabled plans available.</p>
            ) : (
                <table className="table table-bordered text-center">
                    <thead className="bg-dark text-light">
                        <tr>
                            <th>Name</th>
                            <th style={{ width: 400 }}>Description</th>
                            <th>Plan Type</th>
                            <th>Price</th>
                            <th>Status</th>
                        </tr>
                    </thead>
                    <tbody>
                        {enabledPlans.map((plan) => (
                            <tr key={plan.planId}>
                                <td><a href={`/myplan/${plan.planId}`}>{plan.name}</a></td>
                                <td>{plan.description}</td>
                                <td>{plan.duration}</td>
                                <td>{plan.price} /-</td>
                                <td>{plan.isAvailable ? "Available" : "Not Available"}</td>
                            </tr>
                        ))}
                    </tbody>
                </table>
            )}
        </div>
    );
}
