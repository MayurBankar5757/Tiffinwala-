import React, { useState, useEffect } from "react";
import { useNavigate, useParams } from "react-router-dom";

const EditSubscription = () => {
  const navigate = useNavigate();
  const { planId } = useParams();
  const [vendor, setVendor] = useState(null);
  const [subPlan, setSubPlan] = useState({
    vendorId: "",
    name: "",
    description: "",
    price: "",
    duration: "",
  });
  
  const [tiffins, setTiffins] = useState({});
  const [subPlanImage, setSubPlanImage] = useState(null);
  const [includeDinner, setIncludeDinner] = useState(false);
  const { id } = useParams();  // Changed from planId to id
   console.log(id);
  useEffect(() => {
    const fetchVendorAndPlan = async () => {
      try {
        const loggedUser = JSON.parse(localStorage.getItem("loggedUser"))?.uid;
        if (!loggedUser) {
          alert("Not Authorized");
          navigate("/login");
          return;
        }

        const vendorResponse = await fetch(`http://localhost:8081/api/vendors/vendor/${loggedUser}`);
        if (!vendorResponse.ok) {
          alert("Not Authorized");
          navigate("/login");
          return;
        }

        const vendorData = await vendorResponse.json();
        setVendor(vendorData);

        const planResponse = await fetch(`http://localhost:8081/api/vendor-subscription-plans/${id}`);
        if (!planResponse.ok) {
          alert("Plan not found");
          navigate("/vendor_home");
          return;
        }

        const planData = await planResponse.json();
        setSubPlan({
          vendorId: planData.vendorId,
          name: planData.name,
          description: planData.description,
          price: planData.price,
          duration: planData.duration,
        });

        const tiffinsResponse = await fetch(`http://localhost:8081/api/tiffins/plan/${id}`);
        if (!tiffinsResponse.ok) {
          alert("Failed to fetch tiffins");
          return;
        }

        const tiffinsData = await tiffinsResponse.json();
        const tiffinsMap = {};
        tiffinsData.forEach(tiffin => {
            console.log(tiffin)
          tiffinsMap[`${tiffin.day}`] = {
            name: tiffin.name,
            description: tiffin.description,
            foodType: tiffin.foodType,
          };
        });
        setTiffins(tiffinsMap);
        setIncludeDinner(Object.keys(tiffinsMap).some(key => key.endsWith("_DINNER")));
      } catch (error) {
        console.error("Error fetching vendor or plan:", error);
        alert("Something went wrong. Please try again.");
      }
    };
    fetchVendorAndPlan();
  }, [navigate, planId]);

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
      
      const planResponse = await fetch(`http://localhost:8081/api/vendor-subscription-plans/update/${id}`, {
        method: "PUT",
        body: formData,
      });
      if (!planResponse.ok) {
        const errorData = await planResponse.json();
        alert(errorData.message || "Failed to update subscription plan");
        return;
      }
      
      for (const [dayMeal, tiffin] of Object.entries(tiffins)) {
        const [day, meal] = dayMeal.split("_");
        const tiffinBody = {
          v_sub_Id: planId,
          day: `${day}_${meal}`,
          name: tiffin.name,
          foodType: tiffin.foodType,
          description: tiffin.description,
        };

        const tiffinResponse = await fetch(`http://localhost:8081/api/tiffins/${dayMeal}`, {
          method: "PUT",
          headers: { "Content-Type": "application/json" },
          body: JSON.stringify(tiffinBody),
        });

        if (!tiffinResponse.ok) {
          console.error(`Failed to update tiffin for ${dayMeal}`);
          alert(`Failed to update tiffin for ${dayMeal}`);
        }
      }
      alert("Subscription Plan and Tiffins Updated Successfully");
      navigate("/vendor_home");
    } catch (error) {
      console.error("Error:", error);
      alert("An error occurred. Please try again.");
    }
  };

  return (
    <div className="container mt-5">
      <div className="card shadow-lg p-4">
        <h2 className="text-center">Edit Subscription Plan</h2>
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
          <button type="submit" className="btn btn-primary">Update</button>
        </form>
      </div>
    </div>
  );
};

export default EditSubscription;