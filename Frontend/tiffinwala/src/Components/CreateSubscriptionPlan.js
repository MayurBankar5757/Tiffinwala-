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
    duration: "",
  });
  
  const [tiffins, setTiffins] = useState(() => {
    const days = ["SUNDAY", "MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY", "SATURDAY"];
    const mealTypes = ["LUNCH"];
    let initialTiffins = {};
    days.forEach(day => {
      mealTypes.forEach(meal => {
        initialTiffins[`${day}_${meal}`] = { name: "", description: "", foodType: "" };
      });
    });
    return initialTiffins;
  });

  const [subPlanImage, setSubPlanImage] = useState(null);
  const [includeDinner, setIncludeDinner] = useState(false);

  useEffect(() => {
    const fetchVendor = async () => {
      try {
        const loggedUser = JSON.parse(localStorage.getItem("loggedUser"))?.uid;
        if (!loggedUser) {
          alert("Not Authorized");
          navigate("/login");
          return;
        }

        const response = await fetch(`http://localhost:8102/api/vendors/vendor/${loggedUser}`);
        if (!response.ok) {
          alert("Not Authorized");
          navigate("/login");
          return;
        }

        const vendorData = await response.json();
        setVendor(vendorData);
        setSubPlan(prevState => ({ ...prevState, vendorId: vendorData.vendorId }));
      } catch (error) {
        console.error("Error fetching vendor:", error);
        alert("Something went wrong. Please try again.");
      }
    };
    fetchVendor();
  }, [navigate]);

  const handleSubPlanChange = (e) => {
    const { name, value } = e.target;
    setSubPlan({ ...subPlan, [name]: value });
  };

  const handleTiffinChange = (dayMeal, e) => {
    const { name, value } = e.target;
    setTiffins({
      ...tiffins,
      [dayMeal]: { ...tiffins[dayMeal], [name]: value },
    });
  };

  const handleIncludeDinnerChange = (e) => {
    const isChecked = e.target.checked;
    setIncludeDinner(isChecked);
    if (isChecked) {
      const days = ["SUNDAY", "MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY", "SATURDAY"];
      const updatedTiffins = { ...tiffins };
      days.forEach(day => {
        updatedTiffins[`${day}_DINNER`] = { name: "", description: "", foodType: "" };
      });
      setTiffins(updatedTiffins);
    } else {
      const updatedTiffins = { ...tiffins };
      Object.keys(updatedTiffins).forEach(key => {
        if (key.endsWith("_DINNER")) {
          delete updatedTiffins[key];
        }
      });
      setTiffins(updatedTiffins);
    }
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (!subPlan.name || !subPlan.description || !subPlan.price || !subPlan.duration ||
      Object.values(tiffins).some(tiffin => !tiffin.name || !tiffin.description || !tiffin.foodType)) {
      alert("Please fill all fields");
      return;
    }
    try {
      const formData = new FormData();
      formData.append("vendorId", subPlan.vendorId);
      formData.append("name", subPlan.name);
      formData.append("description", subPlan.description);
      formData.append("price", subPlan.price);
      formData.append("duration", subPlan.duration);
      formData.append("isAvaliable", true);
      if (subPlanImage) {
        formData.append("image", subPlanImage);
      }
      
      const planResponse = await fetch("http://localhost:8102/api/vendor-subscription-plans/create", {
        method: "POST",
        body: formData,
      });
      if (!planResponse.ok) {
        const errorData = await planResponse.json();
        alert(errorData.message || "Failed to create subscription plan");
        return;
      }
      
      const planData = await planResponse.json();
      const planId = planData.planId;

      for (const [dayMeal, tiffin] of Object.entries(tiffins)) {
        const [day, meal] = dayMeal.split("_");
        const tiffinBody = {
          v_sub_Id: planId,
          day: `${day}_${meal}`,
          name: tiffin.name,
          foodType: tiffin.foodType,
          description: tiffin.description,
        };

        const tiffinResponse = await fetch("http://localhost:8102/api/tiffins/createtiffin", {
          method: "POST",
          headers: { "Content-Type": "application/json" },
          body: JSON.stringify(tiffinBody),
        });

        if (!tiffinResponse.ok) {
          console.error(`Failed to add tiffin for ${dayMeal}`);
          alert(`Failed to add tiffin for ${dayMeal}`);
        }
      }
      alert("Subscription Plan and Tiffins Added Successfully");
      navigate("/vendor_home");
    } catch (error) {
      console.error("Error:", error);
      alert("An error occurred. Please try again.");
    }
  };

  return (
    <div className="container mt-5">
      <div className="card shadow-lg p-4">
        <h2 className="text-center">Add New Subscription Plan</h2>
        <form onSubmit={handleSubmit}>
          <input type="text" name="name" placeholder="Plan Name" className="form-control mb-3" value={subPlan.name} onChange={handleSubPlanChange} required />
          <textarea name="description" placeholder="Description" className="form-control mb-3" value={subPlan.description} onChange={handleSubPlanChange} required />
          <input type="number" name="price" placeholder="Price" className="form-control mb-3" value={subPlan.price} onChange={handleSubPlanChange} required />
          <select name="duration" className="form-select mb-3" value={subPlan.duration} onChange={handleSubPlanChange} required>
            <option value="">--- Select Plan Type ---</option>
            <option value="SEVEN_DAYS">SEVEN_DAYS</option>
            <option value="THIRTY_DAYS">THIRTY_DAYS</option>
          </select>
          <input type="file" className="form-control mb-3" accept=".png, .jpg, .jpeg" onChange={(e) => setSubPlanImage(e.target.files[0])} />
          <div className="form-check mb-3">
            <input type="checkbox" className="form-check-input" id="includeDinner" checked={includeDinner} onChange={handleIncludeDinnerChange} />
            <label className="form-check-label" htmlFor="includeDinner">Include Dinner</label>
          </div>
          <table className="table">
            <thead><tr><th>Day & Meal</th><th>Name</th><th>Description</th><th>Food Type</th></tr></thead>
            <tbody>
              {Object.entries(tiffins).map(([dayMeal, tiffin]) => (
                <tr key={dayMeal}>
                  <td>{dayMeal.replace("_", " ")}</td>
                  <td><input type="text" name="name" className="form-control" value={tiffin.name} onChange={(e) => handleTiffinChange(dayMeal, e)} required /></td>
                  <td><textarea name="description" className="form-control" value={tiffin.description} onChange={(e) => handleTiffinChange(dayMeal, e)} required /></td>
                  <td><input type="text" name="foodType" className="form-control" value={tiffin.foodType} onChange={(e) => handleTiffinChange(dayMeal, e)} required /></td>
                </tr>
              ))}
            </tbody>
          </table>
          <button type="submit" className="btn btn-primary">Submit</button>
        </form>
      </div>
    </div>
  );
};

export default AddSubscription;