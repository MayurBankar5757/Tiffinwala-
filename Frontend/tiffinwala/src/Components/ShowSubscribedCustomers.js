import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';

export default function ShowSubscribedCustomers() {
    const { id } = useParams(); // Retrieve 'id' from URL params
    const [subscribedCustomers, setSubscribedCustomers] = useState([]);

    useEffect(() => {
        // Fetch data based on the 'id' (planid) from the URL
        fetch(`http://localhost:8102/api/subscriptions/planid/${id}`)
            .then(response => response.json())
            .then(data => setSubscribedCustomers(data))
            .catch(error => console.error('Error fetching data:', error));
    }, [id]); // Dependency on 'id' so it refetches when 'id' changes

    return (
        <div className="container mt-4">
            <h1 className=''>Subscribed Customers</h1>
            {subscribedCustomers.length === 0 ? (
                <p>No subscribed customers found.</p>
            ) : (
                <table className="table table-bordered table-striped mt-3">
                    <thead>
                        <tr>
                            <th>Customer Name</th>
                            <th>Email</th>
                            <th>Contact</th>
                            <th>Plan Name</th>
                            <th>Plan Price</th>
                            <th>Subscription Dates</th>
                            <th>Address</th> {/* Adding address column */}
                        </tr>
                    </thead>
                    <tbody>
                        {subscribedCustomers.map((subscription, index) => (
                            <tr key={index}>
                                <td>{subscription.user.fname} {subscription.user.lname}</td>
                                <td>{subscription.user.email}</td>
                                <td>{subscription.user.contact}</td>
                                <td>{subscription.vendorSubscriptionPlan.name}</td>
                                <td>{subscription.vendorSubscriptionPlan.price}</td>
                                <td>{subscription.startDate} to {subscription.endDate}</td>
                                <td>
                                    {/* Address section */}
                                    <p><strong>City:</strong> {subscription.user.address.city}</p>
                                    <p><strong>State:</strong> {subscription.user.address.state}</p>
                                    <p><strong>Pincode:</strong> {subscription.user.address.pincode}</p>
                                    <p><strong>Area:</strong> {subscription.user.address.area}</p>
                                </td>
                            </tr>
                        ))}
                    </tbody>
                </table>
            )}
        </div>
    );
}
