import React, { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";

const AddSubscription = () => {
  const navigate = useNavigate();
  const [vendor, setVendor] = useState(null);
  const [subPlan, setSubPlan] = useState({
    vendorId: "",
    name: "",
    description: "",
    price: "",
    duration: "", // Changed from planType to duration
  });
  const [tiffins, setTiffins] = useState({
    SUNDAY: { name: "", description: "", price: "", foodType: "" },
    MONDAY: { name: "", description: "", price: "", foodType: "" },
    TUESDAY: { name: "", description: "", price: "", foodType: "" },
    WEDNESDAY: { name: "", description: "", price: "", foodType: "" },
    THURSDAY: { name: "", description: "", price: "", foodType: "" },
    FRIDAY: { name: "", description: "", price: "", foodType: "" },
    SATURDAY: { name: "", description: "", price: "", foodType: "" },
  });
  const [subPlanImage, setSubPlanImage] = useState(null);

  // Fetch vendor data from session storage
  useEffect(() => {
    const vendorData = JSON.parse(localStorage.getItem("loggedUser"));
    if (!vendorData) {
      alert("Not Authorized");
      navigate("/login"); // Redirect to login if no vendor is found
    } else {
      setVendor(vendorData);
      // Set vendorId in subPlan state
      setSubPlan((prevState) => ({
        ...prevState,
        vendorId: vendorData.uid, // Populate vendorId with vendor.uid
      }));
    }
  }, [navigate]);

  // Handle changes in subscription plan fields
  const handleSubPlanChange = (e) => {
    const { name, value } = e.target;
    setSubPlan({ ...subPlan, [name]: value });
  };

  // Handle changes in tiffin fields
  const handleTiffinChange = (day, e) => {
    const { name, value } = e.target;
    setTiffins({
      ...tiffins,
      [day]: { ...tiffins[day], [name]: value },
    });
  };


  // Handle form submission
  const handleSubmit = async (e) => {
    e.preventDefault();
  
    if (
      !subPlan.name ||
      !subPlan.description ||
      !subPlan.price ||
      !subPlan.duration ||
      Object.values(tiffins).some(
        (tiffin) => !tiffin.name || !tiffin.description || !tiffin.price || !tiffin.foodType
      )
    ) {
      alert("Please fill all fields");
      return;
    }
  
    try {
      // Create FormData for subscription plan
      const formData = new FormData();
      formData.append("vendorId", subPlan.vendorId);
      formData.append("name", subPlan.name);
      formData.append("description", subPlan.description);
      formData.append("price", subPlan.price);
      formData.append("duration", subPlan.duration);
      formData.append("isAvaliable", true); // Assuming available
  
      if (subPlanImage) {
        formData.append("image", subPlanImage);
      }
  
      // Step 1: Create Subscription Plan
      const planResponse = await fetch("http://localhost:8081/api/vendor-subscription-plans/create", {
        method: "POST",
        body: formData,
      });
  
      if (!planResponse.ok) {
        const errorData = await planResponse.json();
        alert(errorData.message || "Failed to create subscription plan");
        return;
      }
  
      const planData = await planResponse.json();
      console.log("the New  plan data is :" , planData);
      const planId = planData.planId; // Ensure correct field name
      console.log("Plan created with ID:", planId);
  
      // Step 2: Add Tiffins with v_sub_Id
      for (const [day, tiffin] of Object.entries(tiffins)) {
        const tiffinBody = {
          v_sub_Id: planId, // Ensure this field is present
          day: `${day.toUpperCase()}_LUNCH`, // Ensure correct format
          name: tiffin.name,
          foodType: tiffin.foodType,
          description: tiffin.description,
        };
  
        console.log("Sending Tiffin:", tiffinBody); // Debugging
  
        const tiffinResponse = await fetch("http://localhost:8081/api/tiffins/createtiffin", {
          method: "POST",
          headers: { "Content-Type": "application/json" },
          body: JSON.stringify(tiffinBody),
        });
  
        if (!tiffinResponse.ok) {
          const error = await tiffinResponse.text();
          console.error(`Failed to add tiffin for ${day}:`, error);
          alert(`Failed to add tiffin for ${day}`);
        }
      }
  
      alert("Subscription Plan and Tiffins Added Successfully");
      navigate("/vendor_home"); // Fixed the route typo
    } catch (error) {
      console.error("Error:", error);
      alert("An error occurred. Please try again.");
    }
  };
  
  

  return (
    <div className="container mt-5">
      <div className="card shadow-lg p-4">
        <div className="text-center mb-4">
          <h2 className="display-4">
            <b>Add New Subscription Plan</b>
          </h2>
          <hr className="my-4" />
        </div>

        <form onSubmit={handleSubmit} className="needs-validation" noValidate>
          {/* Subscription Plan Fields */}
          <div className="mb-3">
            <label htmlFor="planName" className="form-label">
              Plan Name
            </label>
            <input
              type="text"
              id="planName"
              name="name"
              className="form-control"
              value={subPlan.name}
              onChange={handleSubPlanChange}
              required
            />
          </div>

          <div className="mb-3">
            <label htmlFor="description" className="form-label">
              Description
            </label>
            <textarea
              id="description"
              name="description"
              className="form-control"
              value={subPlan.description}
              onChange={handleSubPlanChange}
              required
            />
          </div>

          <div className="mb-3">
            <label htmlFor="price" className="form-label">
              Price
            </label>
            <input
              type="number"
              id="price"
              name="price"
              className="form-control"
              value={subPlan.price}
              onChange={handleSubPlanChange}
              required
            />
          </div>

          <div className="mb-3">
            <label htmlFor="duration" className="form-label">
              Plan Type
            </label>
            <select
              id="duration"
              name="duration"
              className="form-select"
              value={subPlan.duration}
              onChange={handleSubPlanChange}
              required
            >
              <option value="">--- Select Plan Type ---</option>
              <option value="SEVEN_DAYS">SEVEN_DAYS</option>
              <option value="THIRTY_DAYS">THIRTY_DAYS</option>
            </select>
          </div>

          <div className="mb-3">
            <label htmlFor="subPlanImage" className="form-label">
              Subscription Plan Image
            </label>
            <input
              type="file"
              id="subPlanImage"
              className="form-control"
              accept=".png, .jpg, .jpeg"
              onChange={(e) => setSubPlanImage(e.target.files[0])}
            />
          </div>

          {/* Tiffins Table */}
          <div className="table-responsive mb-4">
            <table className="table table-bordered table-hover">
              <thead className="table-dark">
                <tr>
                  <th>Day</th>
                  <th>Name</th>
                  <th>Description</th>
                  <th>Food Type</th>
                  <th>Price</th>
                </tr>
              </thead>
              <tbody>
                {Object.entries(tiffins).map(([day, tiffin]) => (
                  <tr key={day}>
                    <td>{day}</td>
                    <td>
                      <input
                        type="text"
                        name="name"
                        className="form-control"
                        value={tiffin.name}
                        onChange={(e) => handleTiffinChange(day, e)}
                        required
                      />
                    </td>
                    <td>
                      <textarea
                        name="description"
                        className="form-control"
                        value={tiffin.description}
                        onChange={(e) => handleTiffinChange(day, e)}
                        required
                      />
                    </td>
                    <td>
                      <textarea
                        name="foodType"
                        className="form-control"
                        value={tiffin.foodType}
                        onChange={(e) => handleTiffinChange(day, e)}
                        required
                      />
                      
                    </td>
                    <td>
                      <input
                        type="number"
                        name="price"
                        className="form-control"
                        value={tiffin.price}
                        onChange={(e) => handleTiffinChange(day, e)}
                        required
                      />
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>

          <div className="d-grid">
            <button type="submit" className="btn btn-primary btn-lg">
              Submit
            </button>
          </div>
        </form>
      </div>
    </div>
  );
};

export default AddSubscription;