import React, { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import useFilterSortPagination from "../hooks/useFilterSortPagination"; // Import Hook

export default function Home() {
  const [plans, setPlans] = useState([]);
  const [error, setError] = useState("");

  useEffect(() => {
    fetch("http://localhost:8103/api/vendor-subscription-plans/getAllSubcriptionPlan")
      .then((response) => {
        if (!response.ok) throw new Error(`HTTP error! Status: ${response.status}`);
        return response.json();
      })
      .then((data) => {
        setPlans(data);
      })
      .catch((error) => {
        console.error("Error fetching subscription plans:", error);
        setError("Failed to fetch subscription plans.");
      });
  }, []);

  // Use the custom hook
  const {
    currentItems,
    currentPage,
    setCurrentPage,
    filterByPriceRange,
    sortData,
    selectedFilter,
    sortOption,
    filteredData,
  } = useFilterSortPagination(plans);

  return (
    <div className="container">
      {/* Hero Image Section */}
      <div className="mb-4">
        <img
          src="https://img.freepik.com/free-vector/food-landing-page-template_23-2148379355.jpg?ga=GA1.1.1998892137.1738936510&semt=ais_hybrid"
          alt="Subscription Plans"
          className="img-fluid"
          style={{ width: "100%", height: "250px", objectFit: "cover", borderRadius: "10px" }}
        />
      </div>

      <h1 className="text-center my-4">Subscription Plans</h1>

      {/* Filters & Sorting */}
      <div className="d-flex justify-content-between mb-3">
        {/* Price Range Filter */}
        <select className="form-select w-25" value={selectedFilter} onChange={(e) => filterByPriceRange(e.target.value)}>
          <option value="">Filter by Price</option>
          <option value="0-1000">₹0 - ₹1000</option>
          <option value="1000-5000">₹1000 - ₹5000</option>
          <option value="5000+">₹5000+</option>
        </select>

        {/* Sorting Options */}
        <select className="form-select w-25" value={sortOption} onChange={(e) => sortData(e.target.value)}>
          <option value="">Sort by Price</option>
          <option value="lowToHigh">Low to High</option>
          <option value="highToLow">High to Low</option>
        </select>
      </div>

      {/* Subscription Plans */}
      {error && <p className="text-danger">{error}</p>}
      <div className="row">
        {currentItems.map((plan, index) => (
          <div className="col-md-4 mb-4" key={index}>
            <div className="card shadow-sm h-100">
              <img
                src={
                  plan.image
                    ? `data:image/png;base64,${plan.image}` // Use base64 image if available
                    : "https://via.placeholder.com/150" // Online fallback image
                }
                className="card-img-top"
                alt={plan.name}
                style={{ height: "150px", objectFit: "cover" }}
              />

              <div className="card-body">
                <h5 className="card-title">{plan.name}</h5>
                <p className="card-text">{plan.description}</p>
                <p className="card-text">
                  <strong>Price:</strong> ₹{plan.price}
                </p>
                <div className="d-flex justify-content-between align-items-center">
                  <span className={`badge ${plan.isAvailable ? "bg-success" : "bg-danger"}`}>
                    {plan.isAvailable ? "Available" : "Not Available"}
                  </span>
                  <Link to={`/Login`} className="btn btn-primary btn-sm">
                    Get Details
                  </Link>
                </div>
              </div>
            </div>
          </div>
        ))}
      </div>

      {/* Pagination */}
      <div className="d-flex justify-content-center mt-4">
        {[...Array(Math.ceil(filteredData.length / 10)).keys()].map((num) => (
          <button
            key={num + 1}
            className={`btn ${currentPage === num + 1 ? "btn-primary" : "btn-outline-primary"} mx-1`}
            onClick={() => setCurrentPage(num + 1)}
          >
            {num + 1}
          </button>
        ))}
      </div>
    </div>
  );
}
