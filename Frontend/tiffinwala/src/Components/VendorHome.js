import { useEffect, useState } from "react";
import { Link, useNavigate } from "react-router-dom"; // Make sure useNavigate is imported

export default function VendorHome() {
  const navigate = useNavigate(); // Define navigate
  const [vendor, setVendor] = useState(null);

  useEffect(() => {
    const loginid = JSON.parse(localStorage.getItem("loggedUser")).uid;

    fetch("http://localhost:8081/api/vendors/vendor/" + loginid)
      .then((resp) => resp.json())
      .then((obj) => {
        localStorage.setItem("loggedVendor", JSON.stringify(obj));
        setVendor(obj);
        console.log(vendor)
      });
  }, []);

  return (
    <div>
      {/* Welcome Section */}
      <div style={{ paddingTop: "60px", fontFamily: "Arial, sans-serif", textAlign: "center", backgroundColor: "#f8f9fa", padding: "30px 0" }}>
        <div className="container mt-4">
          <h1 className="text-primary fw-bold">
            Welcome {vendor && vendor.user.fname} {vendor && vendor.user.lname}
          </h1>

          {/* Vendor Details Section */}
          {vendor && (
            <div className="alert alert-info mt-4 p-4 shadow-sm rounded">
              <h2 className="text-secondary">Vendor Details</h2>
              <p><strong>Vendor ID:</strong> {vendor.vendorId}</p>
              <p><strong>Is Verified:</strong> {vendor.isVerified ? "✅ Yes" : "❌ No"}</p>

              <h3 className="mt-4 text-secondary">User Details</h3>
              <p><strong>Name:</strong> {vendor.user.fname} {vendor.user.lname}</p>
              <p><strong>Email:</strong> {vendor.user.email}</p>
              <p><strong>Contact:</strong> {vendor.user.contact}</p>

              <h3 className="mt-4 text-secondary">Address</h3>
              <p><strong>City:</strong> {vendor.user.address.city}</p>
              <p><strong>State:</strong> {vendor.user.address.state}</p>
              <p><strong>Area:</strong> {vendor.user.address.area}</p>
              <p><strong>Pincode:</strong> {vendor.user.address.pincode}</p>
            </div>
          )}
        </div>
      </div>

      {/* Navigation Cards */}
      <div className="container" style={{ marginBottom: "50px", textAlign: "center" }}>
        <div className="row g-4">
          {[
            { title: "Update Profile", text: "Update your account details.", link: "/updateUser" },
            { title: "Add Subscription Plan", text: "Add new Subscription Plan Details", link: "/addSubcriptionPlan" },
            { title: "Display Subscription Plans", text: "Show all Added Subscription Plans", link: "/vendorAllPlans" },
            { title: "Enabled Subscription Plan", text: "Show Enabled Subscription Plans", link: "/enabledPlans" },
            { title: "Disabled Subscription Plans", text: "Show Disabled Subscription Plans", link: "/disabledPlans" },
          ].map((item, index) => (
            <div key={index} className="col-sm-6 col-md-4">
              <div 
                className="card shadow-lg border-0 rounded-3 text-white bg-gradient" 
                style={{ cursor: "pointer", backgroundColor: "#007bff" }} 
                onClick={() => navigate(item.link)}
                onMouseOver={(e) => e.currentTarget.style.backgroundColor = "#0056b3"}
                onMouseOut={(e) => e.currentTarget.style.backgroundColor = "#007bff"}
              >
                <div className="card-body p-4">
                  <h5 className="card-title fw-bold">{item.title}</h5>
                  <p className="card-text">{item.text}</p>
                </div>
              </div>
            </div>
          ))}
        </div>
      </div>
    </div>
  );

}
