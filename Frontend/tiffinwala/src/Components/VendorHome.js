import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { Alert } from "react-bootstrap";

export default function VendorHome() {
  const navigate = useNavigate();
  const [vendor, setVendor] = useState(null);

  useEffect(() => {
    const loggedUser = JSON.parse(localStorage.getItem("loggedUser"));
    if (!loggedUser) {
      navigate("/");
      return;
    }

    const loginid = loggedUser.uid;
    const token = localStorage.getItem("jwtToken");

    if (!token) {
      console.error("No token found in localStorage");
      navigate("/");
      return;
    }

    fetch(`http://localhost:8104/api2/vendor/vendor/${loginid}`, {
      method: "GET",
      headers: {
        "Content-Type": "application/json",
        Authorization: `Bearer ${token}`,
      },
    })
      .then((resp) => {
        if (!resp.ok) throw new Error("Failed to fetch vendor data");
        return resp.json();
      })
      .then((obj) => {
        localStorage.setItem("loggedVendor", JSON.stringify(obj));
        setVendor(obj);
      })
      .catch((error) => {
        console.error("Error fetching vendor data:", error);
      });
  }, [navigate]);

  const renderNavigationCards = () => {
    if (!vendor?.isVerified) {
      return (
        <Alert variant="warning" className="mt-4">
          <h4 className="alert-heading">Account Under Review</h4>
          <p>
            Your vendor account is not yet verified. Please wait for admin approval 
            before accessing these features. You'll be notified via email once 
            your account is verified.
          </p>
        </Alert>
      );
    }

    return (
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
              style={{ 
                cursor: "pointer", 
                backgroundColor: "#007bff",
                transition: "all 0.3s ease"
              }}
              onClick={() => navigate(item.link)}
              onMouseOver={(e) => (e.currentTarget.style.backgroundColor = "#0056b3")}
              onMouseOut={(e) => (e.currentTarget.style.backgroundColor = "#007bff")}
            >
              <div className="card-body p-4">
                <h5 className="card-title fw-bold">{item.title}</h5>
                <p className="card-text">{item.text}</p>
              </div>
            </div>
          </div>
        ))}
      </div>
    );
  };

  return (
    <div>
      <div
        style={{
          paddingTop: "60px",
          fontFamily: "Arial, sans-serif",
          textAlign: "center",
          backgroundColor: "#f8f9fa",
          padding: "30px 0",
        }}
      >
        <div className="container mt-4">
          <h1 className="text-primary fw-bold">
            Welcome {vendor?.user?.fname} {vendor?.user?.lname}
          </h1>

          {vendor && (
            <div className="alert alert-info mt-4 p-4 shadow-sm rounded">
              <h2 className="text-secondary">Vendor Details</h2>
              <p>
                <strong>Vendor ID:</strong> {vendor.vendorId}
              </p>
              <p>
                <strong>Verification Status:</strong>{" "}
                {vendor.isVerified ? (
                  <span className="text-success">✅ Verified</span>
                ) : (
                  <span className="text-danger">❌ Pending Verification</span>
                )}
              </p>

              <h3 className="mt-4 text-secondary">User Details</h3>
              <p>
                <strong>Name:</strong> {vendor.user.fname} {vendor.user.lname}
              </p>
              <p>
                <strong>Email:</strong> {vendor.user.email}
              </p>
              <p>
                <strong>Contact:</strong> {vendor.user.contact}
              </p>

              <h3 className="mt-4 text-secondary">Address</h3>
              <p>
                <strong>City:</strong> {vendor.user.address.city}
              </p>
              <p>
                <strong>State:</strong> {vendor.user.address.state}
              </p>
              <p>
                <strong>Area:</strong> {vendor.user.address.area}
              </p>
              <p>
                <strong>Pincode:</strong> {vendor.user.address.pincode}
              </p>
            </div>
          )}
        </div>
      </div>

      <div className="container" style={{ marginBottom: "50px", textAlign: "center" }}>
        {renderNavigationCards()}
      </div>
    </div>
  );
}