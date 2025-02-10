import { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";

export default function VendorSubPlanList() {
    const [subPlanList, setSubPlanList] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    const navigate = useNavigate();
    const [vendorId, setVendorId] = useState(null);

    useEffect(() => {
        const fetchVendorAndPlans = async () => {
            try {
                // Retrieve logged-in user ID from localStorage
                const loggedUser = JSON.parse(localStorage.getItem("loggedUser"));
                const token = localStorage.getItem("jwtToken");

                if (!loggedUser || !loggedUser.uid) {
                    throw new Error("User not logged in or UID missing.");
                }

                const userId = parseInt(loggedUser.uid, 10);
                if (isNaN(userId)) {
                    throw new Error("Invalid user ID format.");
                }

                // Fetch vendor details
                const vendorResponse = await fetch(`http://localhost:8104/api2/vendor/vendor/${userId}`, {
                    headers: {
                        "Content-Type": "application/json",
                        Authorization: `Bearer ${token}`
                    }
                });
                if (!vendorResponse.ok) {
                    throw new Error("Failed to fetch vendor details.");
                }

                const vendorData = await vendorResponse.json();
                console.log(vendorData);
                if (!vendorData || !vendorData.vendorId) {
                    throw new Error("Vendor ID not found.");
                }

                const parsedVendorId = parseInt(vendorData.vendorId, 10);
                if (isNaN(parsedVendorId)) {
                    throw new Error("Invalid vendor ID format.");
                }

                setVendorId(parsedVendorId);

                // Fetch subscription plans using the vendor ID
                const plansResponse = await fetch(`http://localhost:8104/api2/vsp/vendor/${parsedVendorId}`, {
                    headers: {
                        "Content-Type": "application/json",
                        Authorization: `Bearer ${token}`
                    }
                });
                if (!plansResponse.ok) {
                    throw new Error("Failed to fetch subscription plans.");
                }

                const plansData = await plansResponse.json();
                setSubPlanList(plansData);
            } catch (err) {
                setError(err.message);
            } finally {
                setLoading(false);
            }
        };

        fetchVendorAndPlans();
    }, []);

    const enablePlan = async (planId) => {
        try {
            const token = localStorage.getItem("jwtToken");

            const response = await fetch(`http://localhost:8104/api2/vsp/${planId}/enabled`, {
                method: "PUT",
                headers: {
                    Authorization: `Bearer ${token}`
                }
            });
            if (!response.ok) {
                throw new Error("Failed to enable subscription plan.");
            }
            setSubPlanList(subPlanList.map(p => p.planId === planId ? { ...p, isAvailable: true } : p));
        } catch (err) {
            setError(err.message);
        }
    };

    const disablePlan = async (planId) => {
        try {
            const token = localStorage.getItem("jwtToken");

            const response = await fetch(`http://localhost:8104/api2/vsp/${planId}/disabled`, {
                method: "PUT",
                headers: {
                    Authorization: `Bearer ${token}`
                }
            });
            if (!response.ok) {
                throw new Error("Failed to disable subscription plan.");
            }
            setSubPlanList(subPlanList.map(p => p.planId === planId ? { ...p, isAvailable: false } : p));
        } catch (err) {
            setError(err.message);
        }
    };

    const deletePlan = async (planId) => {
        try {
            const token = localStorage.getItem("jwtToken");

            const response = await fetch(`http://localhost:8104/api2/vsp/${planId}`, {
                method: "DELETE",
                headers: {
                    Authorization: `Bearer ${token}`
                }
            });
            if (!response.ok) {
                throw new Error("Failed to delete subscription plan.");
            }
            // Remove the deleted plan from the list
            setSubPlanList(subPlanList.filter(p => p.planId !== planId));
        } catch (err) {
            setError(err.message);
        }
    };

    if (loading) return <p>Loading...</p>;
    if (error) return <p className="text-danger">{error}</p>;

    return (
        <div className="container my-4">
            <h3>All Subscription Plans</h3>
            <table className="table table-bordered text-center">
                <thead className="bg-dark text-light">
                    <tr>
                        <th>Name</th>
                        <th style={{ width: 400 }}>Description</th>
                        <th>Plan Type</th>
                        <th>Price</th>
                        <th>Status</th>
                        <th>Customers</th>
                        <th>Edit</th>
                        <th>Availability</th>
                        <th>Delete</th>
                    </tr>
                </thead>
                <tbody>
                    {subPlanList.map((plan) => (
                        <tr key={plan.planId}>
                            <td><a href={`/myplan/${plan.planId}`}>{plan.name}</a></td>
                            <td>{plan.description}</td>
                            <td>{plan.duration}</td>
                            <td>{plan.price} /-</td>
                            <td>{plan.isAvailable ? "Available" : "Not Available"}</td>
                            <td>
                                <button className="btn btn-primary" onClick={() => navigate(`/showSubscribedCustomers/${plan.planId}`)}>Customers</button>
                            </td>
                            <td>
                                <button className="btn btn-warning" onClick={() => navigate(`/editSubscriptionPlan/${plan.planId}`)}>Edit</button>
                            </td>
                            <td>
                                {plan.isAvailable ? (
                                    <button className="btn btn-danger" onClick={() => disablePlan(plan.planId)}>Disable</button>
                                ) : (
                                    <button className="btn btn-success" onClick={() => enablePlan(plan.planId)}>Enable</button>
                                )}
                            </td>
                            <td>
                                <button className="btn btn-danger" onClick={() => deletePlan(plan.planId)}>Delete</button>
                            </td>
                        </tr>
                    ))}
                </tbody>
            </table>
        </div>
    );
}
