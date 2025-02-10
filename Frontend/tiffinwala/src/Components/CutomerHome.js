import { Link, useNavigate } from "react-router-dom";
import React, { useEffect, useState } from "react";

export default function CustomerHome() {
  const [plans, setPlans] = useState([]);
  const [filteredPlans, setFilteredPlans] = useState([]);
  const [error, setError] = useState("");
  const [userInfo, setUserInfo] = useState(null);
  const [searchTerm, setSearchTerm] = useState("");
  const [sortOption, setSortOption] = useState("");
  const [selectedFilter, setSelectedFilter] = useState("");
  const [currentPage, setCurrentPage] = useState(1);
  const plansPerPage = 6; // Change this for different page sizes
  const navigate = useNavigate();

  console.log(plans);
  useEffect(() => {
    const storedUser = JSON.parse(localStorage.getItem("loggedUser"));
    const jwtToken = localStorage.getItem("jwtToken");

    if (storedUser && jwtToken) {
      setUserInfo(storedUser);
    } else {
      navigate("/login");
      return;
    }

    fetch("http://localhost:8104/api2/vsp/getAllSubcriptionPlan", {
      headers: { Authorization: `Bearer ${jwtToken}` },
    })
      .then((response) => {
        if (!response.ok) throw new Error(`HTTP error! Status: ${response.status}`);
        return response.json();
      })
      .then((data) => {
        setPlans(data);
        setFilteredPlans(data); // Set initial filtered data
      })
      .catch((error) => {
        console.error("Error fetching subscription plans:", error);
        setError("Failed to fetch subscription plans.");
      });
  }, [navigate]);

  // Search Functionality
  useEffect(() => {
    let updatedPlans = plans.filter((plan) =>
      plan.name.toLowerCase().includes(searchTerm.toLowerCase())
    );

    // Apply Sorting
    if (sortOption === "lowToHigh") {
      updatedPlans = updatedPlans.sort((a, b) => a.price - b.price);
    } else if (sortOption === "highToLow") {
      updatedPlans = updatedPlans.sort((a, b) => b.price - a.price);
    }

    // Apply Filtering
    if (selectedFilter === "0-1000") {
      updatedPlans = updatedPlans.filter((plan) => plan.price >= 0 && plan.price <= 1000);
    } else if (selectedFilter === "1000-5000") {
      updatedPlans = updatedPlans.filter((plan) => plan.price > 1000 && plan.price <= 5000);
    } else if (selectedFilter === "5000+") {
      updatedPlans = updatedPlans.filter((plan) => plan.price > 5000);
    }

    setFilteredPlans(updatedPlans);
    setCurrentPage(1);
  }, [searchTerm, sortOption, selectedFilter, plans]);

  // Pagination Logic
  const indexOfLastItem = currentPage * plansPerPage;
  const indexOfFirstItem = indexOfLastItem - plansPerPage;
  const currentItems = filteredPlans.slice(indexOfFirstItem, indexOfLastItem);
  const totalPages = Math.ceil(filteredPlans.length / plansPerPage);

  const handleSubscribe = (subscriptionPlanId) => {
    const jwtToken = localStorage.getItem("jwtToken");
    if (!userInfo || !jwtToken) {
      alert("Please log in to subscribe.");
      navigate("/login");
      return;
    }

    fetch("http://localhost:8104/api2/csp/subscribePlan", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
        Authorization: `Bearer ${jwtToken}`,
      },
      body: JSON.stringify({ userId: userInfo.uid, subscriptionPlanId }),
    })
      .then((response) => {
        if (!response.ok) throw new Error(`HTTP error! Status: ${response.status}`);
        return response.json();
      })
      .then(() => alert("Subscription successful!"))
      .catch((error) => {
        console.error("Error subscribing to plan:", error);
        alert("Failed to subscribe. Please try again.");
      });
  };

  return (
    <div>
      {userInfo && (
        <div className="alert alert-info">
          <h5>
            <strong>Welcome, {userInfo.fname} {userInfo.lname}!</strong>
          </h5>
          <p>
            <strong>Delivering to:</strong> {userInfo.address?.area}, {userInfo.address?.city}, {userInfo.address?.state} - {userInfo.address?.pincode}
          </p>
          <Link to="/updateUser" className="btn btn-warning btn-sm">Update Profile</Link>
        </div>
      )}

      <div className="container mt-4">
        <div className="d-flex justify-content-between mb-3">
          <input
            type="text"
            className="form-control w-50"
            placeholder="Search plans..."
            value={searchTerm}
            onChange={(e) => setSearchTerm(e.target.value)}
          />
          <select className="form-select w-25" value={sortOption} onChange={(e) => setSortOption(e.target.value)}>
            <option value="">Sort By Price</option>
            <option value="lowToHigh">Low to High</option>
            <option value="highToLow">High to Low</option>
          </select>
          <select className="form-select w-25" value={selectedFilter} onChange={(e) => setSelectedFilter(e.target.value)}>
            <option value="">Filter By Price</option>
            <option value="0-1000">₹0 - ₹1000</option>
            <option value="1000-5000">₹1000 - ₹5000</option>
            <option value="5000+">₹5000+</option>
          </select>
        </div>

        {error && <p className="text-danger">{error}</p>}

        <div className="row">
          {currentItems.map((plan) => (
            <div className="col-md-4 mb-4" key={plan.planId}>
              <div className="card shadow-sm h-100">
                {plan.image ? (
                  <img
                    src={`data:image/jpg;base64,${plan.image}`}
                    className="card-img-top"
                    alt={plan.name}
                    style={{ height: "200px", objectFit: "cover" }}
                  />
                ) : (
                  <div className="d-flex align-items-center justify-content-center" style={{ height: "200px", backgroundColor: "#f8f9fa", fontSize: "14px", color: "#888" }}>
                    No Image Available
                  </div>
                )}
                <div className="card-body">
                  <h5 className="card-title">{plan.name}</h5>
                  <h6 className="badge bg-info">{plan.duration}</h6>
                  <p>{plan.description}</p>
                  <p><strong>Price:</strong> ₹{plan.price}</p>
                  <div className="d-flex justify-content-between align-items-center">
                    <span className={`badge ${plan.isAvailable ? "bg-success" : "bg-danger"}`}>
                      {plan.isAvailable ? "Available" : "Not Available"}
                    </span>
                    <Link to={`/VendorPlanDetails/${plan.planId}`} className="btn btn-primary btn-sm">Get Details</Link>
                  </div>
                  <button className="btn btn-success btn-sm mt-2" onClick={() => handleSubscribe(plan.planId)} disabled={!plan.isAvailable}>
                    Subscribe
                  </button>
                </div>
              </div>
            </div>
          ))}
        </div>

        {/* Pagination Controls */}
        {totalPages > 1 && (
          <div className="d-flex justify-content-center mt-4">
            {[...Array(totalPages).keys()].map((num) => (
              <button
                key={num + 1}
                className={`btn ${currentPage === num + 1 ? "btn-primary" : "btn-outline-primary"} mx-1`}
                onClick={() => setCurrentPage(num + 1)}
              >
                {num + 1}
              </button>
            ))}
          </div>
        )}
      </div>
    </div>
  );
}
