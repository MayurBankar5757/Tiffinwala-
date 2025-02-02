import React, { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";

const AddSubscription = () => {
  const navigate = useNavigate();
  const [vendor, setVendor] = useState(null);
  const [subPlan, setSubPlan] = useState({
    vendorId: "", // Add vendorId to the initial state
    name: "",
    description: "",
    price: "",
    planType: "",
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

  // Handle image upload for tiffins
  const handleTiffinImageUpload = (day, e) => {
    setTiffins({
      ...tiffins,
      [day]: { ...tiffins[day], image: e.target.files[0] },
    });
  };

  // Handle form submission
  const handleSubmit = async (e) => {
    e.preventDefault();

    // Validate all fields
    if (
      !subPlan.name ||
      !subPlan.description ||
      !subPlan.price ||
      !subPlan.planType ||
      Object.values(tiffins).some(
        (tiffin) => !tiffin.name || !tiffin.description || !tiffin.price || !tiffin.foodType
      )
    ) {
      alert("Please fill all fields");
      return;
    }

    try {
      // Step 1: Create the subscription plan
      const planResponse = await fetch(
        `http://localhost:8081/api/vendor-subscription-plans/create`,
        {
          method: "POST",
          headers: { "Content-Type": "application/json" },
          body: JSON.stringify(subPlan), // This now includes vendorId
        }
      );

      if (!planResponse.ok) {
        throw new Error("Failed to create subscription plan");
      }

      const planData = await planResponse.json();
      const planId = planData.id;

      // Step 2: Upload subscription plan image (if provided)
      if (subPlanImage) {
        const formData = new FormData();
        formData.append("subPlanImage", subPlanImage);

        const imageResponse = await fetch(
          `http://localhost:8081/api/subscription/${planId}/subPlanImage`,
          {
            method: "POST",
            body: formData,
          }
        );

        if (!imageResponse.ok) {
          throw new Error("Failed to upload subscription plan image");
        }
      }

      // Step 3: Add tiffins for each day
      for (const [day, tiffin] of Object.entries(tiffins)) {
        const tiffinResponse = await fetch(
          `http://localhost:8081/api/tiffins/addTiffin/${planId}`,
          {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ ...tiffin, day: `${day.toUpperCase()}_LUNCH` }),
          }
        );

        if (!tiffinResponse.ok) {
          throw new Error(`Failed to add tiffin for ${day}`);
        }

        const tiffinData = await tiffinResponse.json();
        const tiffinId = tiffinData.id;

        // Step 4: Upload tiffin image (if provided)
        if (tiffin.image) {
          const formData = new FormData();
          formData.append("tiffinImage", tiffin.image);

          const tiffinImageResponse = await fetch(
            `http://localhost:8081/api/tiffins/${tiffinId}/tiffinImage`,
            {
              method: "POST",
              body: formData,
            }
          );

          if (!tiffinImageResponse.ok) {
            throw new Error(`Failed to upload tiffin image for ${day}`);
          }
        }
      }

      alert("Subscription Plan Added Successfully");
      navigate("/vendor"); // Redirect to vendor dashboard
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
            <label htmlFor="planType" className="form-label">
              Plan Type
            </label>
            <select
              id="planType"
              name="planType"
              className="form-select"
              value={subPlan.planType}
              onChange={handleSubPlanChange}
              required
            >
              <option value="">--- Select Plan Type ---</option>
              <option value="WEEKLY">Weekly</option>
              <option value="MONTHLY">Monthly</option>
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
                      <select
                        name="foodType"
                        className="form-select"
                        value={tiffin.foodType}
                        onChange={(e) => handleTiffinChange(day, e)}
                        required
                      >
                        <option value="">Select Food Type</option>
                        <option value="VEG">Veg</option>
                        <option value="NONVEG">Non-Veg</option>
                      </select>
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