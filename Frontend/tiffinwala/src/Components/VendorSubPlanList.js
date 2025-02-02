import { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";

export default function VendorSubPlanList() {
    const [subPlanList, setSubPlanList] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    const navigate = useNavigate();
    const loginid = JSON.parse(localStorage.getItem("loggedUser")).uid;

    useEffect(() => {
        const fetchPlans = async () => {
            try {
                const response = await fetch(`http://localhost:8081/api/vendor-subscription-plans/vendor/${loginid}`);
                if (!response.ok) {
                    throw new Error("Failed to fetch subscription plans.");
                }
                const data = await response.json();
                setSubPlanList(data);
            } catch (err) {
                setError(err.message);
            } finally {
                setLoading(false);
            }
        };
        fetchPlans();
    }, [loginid]);

    const enablePlan = async (planId) => {
        try {
            const response = await fetch(`http://localhost:8081/api/vendor-subscription-plans/${planId}/enabled`, {
                method: "PUT",
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
            const response = await fetch(`http://localhost:8081/api/vendor-subscription-plans/${planId}/disabled`, {
                method: "PUT",
            });
            if (!response.ok) {
                throw new Error("Failed to disable subscription plan.");
            }
            setSubPlanList(subPlanList.map(p => p.planId === planId ? { ...p, isAvailable: false } : p));
        } catch (err) {
            setError(err.message);
        }
    };

    if (loading) return <p>Loading...</p>;
    if (error) return <p className="text-danger">{error}</p>;

    return (
        <div className="container my-4">
            <h3>All Subscription Plans</h3>
            <table className="table table-bordered" style={{ textAlign: "center" }}>
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
                    </tr>
                </thead>
                <tbody>
                    {subPlanList.map((plan) => (
                        <tr key={plan.planId}>
                            <td><a href={`/subscription/plan/${plan.planId}`}>{plan.name}</a></td>
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
                        </tr>
                    ))}
                </tbody>
            </table>
        </div>
    );
}
