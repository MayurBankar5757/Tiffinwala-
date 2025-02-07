import { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";

export default function DisabledPlans() {
    const [disabledPlans, setDisabledPlans] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    const navigate = useNavigate();

    useEffect(() => {
        const fetchDisabledPlans = async () => {
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

                const response = await fetch(`http://localhost:8103/api/vendor-subscription-plans/vendor/${vendorId}/disabled`, {
                    headers: {
                        "Content-Type": "application/json",
                        Authorization: `Bearer ${token}`
                    }
                });

                if (response.status === 204) {
                    setDisabledPlans([]);
                } else if (!response.ok) {
                    throw new Error("Failed to fetch disabled plans.");
                } else {
                    const data = await response.json();
                    setDisabledPlans(data);
                }
            } catch (err) {
                setError(err.message);
            } finally {
                setLoading(false);
            }
        };

        fetchDisabledPlans();
    }, []);

    if (loading) return <p>Loading...</p>;
    if (error) return <p className="text-danger">{error}</p>;

    return (
        <div className="container my-4">
            <h3>Disabled Subscription Plans</h3>
            {disabledPlans.length === 0 ? (
                <p>No disabled plans available.</p>
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
                        {disabledPlans.map((plan) => (
                            <tr key={plan.planId}>
                                <td><a href={`/subscription/plan/${plan.planId}`}>{plan.name}</a></td>
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
