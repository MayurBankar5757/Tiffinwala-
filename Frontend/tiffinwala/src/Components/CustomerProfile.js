import React, { useState, useEffect } from 'react';

const CustomerOrderHistory = () => {
  const [orders, setOrders] = useState([]);

  // Retrieve uid from localStorage
  const uid = JSON.parse(localStorage.getItem("loggedUser")).uid;

  useEffect(() => {
    if (!uid) {
      console.error('User ID not found in localStorage.');
      return;
    }

    const fetchOrders = async () => {
      try {
        const response = await fetch(`http://localhost:8104/api2/csp/user/${uid}`);
        if (!response.ok) {
          throw new Error('Network response was not ok');
        }
        const data = await response.json();
        setOrders(data);
      } catch (error) {
        console.error('Error fetching order history:', error);
      }
    };

    fetchOrders();
  }, [uid]);

  return (
    <div className="container my-4">
      <h1 className="mb-4">Customer Order History</h1>
      {orders.length > 0 ? (
        orders.map((order, index) => (
          <div key={index} className="card mb-4">
            <div className="card-header">
              <h2 className="h5 mb-0">
                Subscription Plan: {order.vendorSubscriptionPlan.name}
              </h2>
            </div>
            <div className="card-body">
              {/* Subscription Plan Details */}
              <div className="mb-3">
                <p className="mb-1"><strong>Price:</strong> ₹{order.vendorSubscriptionPlan.price}</p>
                <p className="mb-1"><strong>Description:</strong> {order.vendorSubscriptionPlan.description}</p>
              </div>
              
              {/* Optional image display (assuming it's Base64 encoded) */}
              {order.vendorSubscriptionPlan.image && (
                <div className="mb-3">
                  <img
                    src={`data:image/jpeg;base64,${order.vendorSubscriptionPlan.image}`}
                    alt={order.vendorSubscriptionPlan.name}
                    className="img-fluid rounded"
                    style={{ maxWidth: '200px' }}
                  />
                </div>
              )}

              <hr />

              {/* Vendor Details */}
              <div className="mb-3">
                <h3 className="h6">Vendor Details</h3>
                <p className="mb-1">
                  <strong>Name:</strong> {order.vendorSubscriptionPlan.vendor.user.fname} {order.vendorSubscriptionPlan.vendor.user.lname}
                </p>
                <p className="mb-1">
                  <strong>Email:</strong> {order.vendorSubscriptionPlan.vendor.user.email}
                </p>
                <p className="mb-1">
                  <strong>Contact:</strong> {order.vendorSubscriptionPlan.vendor.user.contact}
                </p>
                <p className="mb-1">
                  <strong>Address:</strong> {order.vendorSubscriptionPlan.vendor.user.address.area}, {order.vendorSubscriptionPlan.vendor.user.address.city}, {order.vendorSubscriptionPlan.vendor.user.address.state} - {order.vendorSubscriptionPlan.vendor.user.address.pincode}
                </p>
              </div>

              <hr />

              {/* Order Dates */}
              <div>
                <h3 className="h6">Order Dates</h3>
                <p className="mb-1"><strong>Start Date:</strong> {order.startDate}</p>
                <p className="mb-1"><strong>End Date:</strong> {order.endDate}</p>
                <p className="mb-1"><strong>Ordered Date:</strong> {order.orderedDate}</p>
              </div>
            </div>
          </div>
        ))
      ) : (
        <p>No orders found.</p>
      )}
    </div>
  );
};

export default CustomerOrderHistory;
