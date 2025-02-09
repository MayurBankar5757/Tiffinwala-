import React, { useState, useEffect } from "react";
import { useNavigate, useParams } from "react-router-dom";

const EditSubscription = () => {
  const navigate = useNavigate();
  const { id } = useParams();
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
  const jwtToken = localStorage.getItem("jwtToken");

  useEffect(() => {
    const fetchVendorAndPlan = async () => {
      try {
        const loggedUser = JSON.parse(localStorage.getItem("loggedUser"));
        if (!loggedUser) {
          alert("Not Authorized");
          navigate("/login");
          return;
        }

        // Fetch vendor details
        const vendorResponse = await fetch(
          `http://localhost:8103/api/vendors/vendor/${loggedUser.uid}`,
          {
            headers: {
              Authorization: `Bearer ${jwtToken}`,
            },
          }
        );
        if (!vendorResponse.ok) {
          alert("Not Authorized");
          navigate("/login");
          return;
        }
        const vendorData = await vendorResponse.json();
        setVendor(vendorData);

        // Fetch subscription plan details
        const planResponse = await fetch(
          `http://localhost:8103/api/vendor-subscription-plans/${id}`,
          {
            headers: {
              Authorization: `Bearer ${jwtToken}`,
            },
          }
        );
        if (!planResponse.ok) {
          alert("Plan not found");
          navigate("/vendor_home");
          return;
        }
        const planData = await planResponse.json();
        setSubPlan({
          vendorId: planData.vendorId.toString(),
          name: planData.name,
          description: planData.description,
          price: planData.price.toString(),
          duration: planData.duration,
        });

        // Fetch tiffins
        const tiffinsResponse = await fetch(
          `http://localhost:8103/api/tiffins/plan/${id}`,
          {
            headers: {
              Authorization: `Bearer ${jwtToken}`,
            },
          }
        );
        if (!tiffinsResponse.ok) throw new Error("Failed to fetch tiffins");
        
        const tiffinsData = await tiffinsResponse.json();
        const tiffinsMap = {};
        tiffinsData.forEach(tiffin => {
          tiffinsMap[tiffin.day] = {
            id: tiffin.tiffinId,
            name: tiffin.name,
            description: tiffin.description,
            foodType: tiffin.foodType,
          };
        });
        setTiffins(tiffinsMap);
        setIncludeDinner(Object.keys(tiffinsMap).some(key => key.includes("DINNER")));
      } catch (error) {
        console.error("Error fetching data:", error);
        alert("Something went wrong. Please try again.");
      }
    };
    fetchVendorAndPlan();
  }, [navigate, id, jwtToken]);

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
    const days = ["SUNDAY", "MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY", "SATURDAY"];
    
    setTiffins(prev => {
      const updated = {...prev};
      if (isChecked) {
        days.forEach(day => {
          const key = `${day}_DINNER`;
          if (!updated[key]) updated[key] = { name: "", description: "", foodType: "" };
        });
      } else {
        Object.keys(updated).forEach(key => {
          if (key.endsWith("_DINNER")) delete updated[key];
        });
      }
      return updated;
    });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (!subPlan.name || !subPlan.description || !subPlan.price || !subPlan.duration ||
      Object.values(tiffins).some(t => !t.name || !t.description || !t.foodType)) {
      alert("Please fill all fields");
      return;
    }

    try {
      // Update subscription plan
      const formData = new FormData();
      formData.append("vendorId", subPlan.vendorId);
      formData.append("name", subPlan.name);
      formData.append("description", subPlan.description);
      formData.append("price", subPlan.price);
      formData.append("duration", subPlan.duration);
      formData.append("isAvaliable", "true");
      if (subPlanImage) formData.append("image", subPlanImage);

      const planResponse = await fetch(
        `http://localhost:8103/api/vendor-subscription-plans/update/${id}`,
        {
          method: "PUT",
          body: formData,
          headers: {
            Authorization: `Bearer ${jwtToken}`,
          },
        }
      );
      if (!planResponse.ok) throw new Error("Failed to update plan");

      // Update tiffins
      for (const [dayMeal, tiffin] of Object.entries(tiffins)) {
        const tiffinBody = {
          tiffinId: tiffin.id,
          v_sub_Id: id,
          day: dayMeal,
          name: tiffin.name,
          foodType: tiffin.foodType,
          description: tiffin.description,
        };

        const tiffinResponse = await fetch(
          `http://localhost:8103/api/tiffins/update`,
          {
            method: "PUT",
            headers: {
              "Content-Type": "application/json",
              Authorization: `Bearer ${jwtToken}`,
            },
            body: JSON.stringify(tiffinBody),
          }
        );
        if (!tiffinResponse.ok) console.error(`Failed to update ${dayMeal}`);
      }

      alert("Subscription updated successfully!");
      navigate("/vendor_home");
    } catch (error) {
      console.error("Update error:", error);
      alert("Failed to update subscription. Please try again.");
    }
  };

  return (
    <div className="container mt-5">
      <div className="card shadow-lg p-4">
        <h2 className="text-center">Edit Subscription Plan</h2>
        <form onSubmit={handleSubmit}>
          <input
            type="text"
            name="name"
            placeholder="Plan Name"
            className="form-control mb-3"
            value={subPlan.name}
            onChange={handleSubPlanChange}
            required
          />
          <textarea
            name="description"
            placeholder="Description"
            className="form-control mb-3"
            value={subPlan.description}
            onChange={handleSubPlanChange}
            required
          />
          <input
            type="number"
            name="price"
            placeholder="Price"
            className="form-control mb-3"
            value={subPlan.price}
            onChange={handleSubPlanChange}
            required
          />
          <select
            name="duration"
            className="form-select mb-3"
            value={subPlan.duration}
            onChange={handleSubPlanChange}
            required
          >
            <option value="">Select Duration</option>
            <option value="SEVEN_DAYS">7 Days</option>
            <option value="THIRTY_DAYS">30 Days</option>
          </select>
          <input
            type="file"
            className="form-control mb-3"
            accept=".png,.jpg,.jpeg"
            onChange={(e) => setSubPlanImage(e.target.files[0])}
          />
          <div className="form-check mb-3">
            <input
              type="checkbox"
              className="form-check-input"
              id="includeDinner"
              checked={includeDinner}
              onChange={handleIncludeDinnerChange}
            />
            <label className="form-check-label" htmlFor="includeDinner">
              Include Dinner
            </label>
          </div>
          <table className="table">
            <thead>
              <tr>
                <th>Day & Meal</th>
                <th>Name</th>
                <th>Description</th>
                <th>Food Type</th>
              </tr>
            </thead>
            <tbody>
              {Object.entries(tiffins).map(([dayMeal, tiffin]) => (
                <tr key={dayMeal}>
                  <td>{dayMeal.replace("_", " ")}</td>
                  <td>
                    <input
                      type="text"
                      name="name"
                      className="form-control"
                      value={tiffin.name}
                      onChange={(e) => handleTiffinChange(dayMeal, e)}
                      required
                    />
                  </td>
                  <td>
                    <textarea
                      name="description"
                      className="form-control"
                      value={tiffin.description}
                      onChange={(e) => handleTiffinChange(dayMeal, e)}
                      required
                    />
                  </td>
                  <td>
                    <input
                      type="text"
                      name="foodType"
                      className="form-control"
                      value={tiffin.foodType}
                      onChange={(e) => handleTiffinChange(dayMeal, e)}
                      required
                    />
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
          <button type="submit" className="btn btn-primary w-100">
            Update Subscription
          </button>
        </form>
      </div>
    </div>
  );
};

export default EditSubscription;