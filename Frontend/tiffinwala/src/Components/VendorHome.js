import { useEffect, useState } from "react";
import { Link } from "react-router-dom";

export default function VendorHome() {
  const [vendor, setVendor] = useState(null);

  useEffect(() => {
    const loginid = JSON.parse(localStorage.getItem("loggedUser")).uid;

    fetch("http://localhost:8081/api/vendors/vendor/" + loginid)
      .then((resp) => resp.json())
      .then((obj) => {
        localStorage.setItem("loggedVendor", JSON.stringify(obj));
        setVendor(obj);
      });
  }, []);

  return (
    <div>
      {/* Navigation Bar */}
      <nav
        style={{
          position: "fixed",
          top: 0,
          left: 0,
          right: 0,
          backgroundColor: "#333",
          color: "#fff",
          padding: "10px 20px",
          display: "flex",
          alignItems: "center",
          zIndex: 1000,
        }}
      >
        {/* Extreme Left: Vendor Portal */}
        <div style={{ marginRight: "20px" }}>
          <span
            style={{
              color: "#fff",
              fontSize: "20px",
              fontWeight: "bold",
            }}
          >
            Vendor Portal
          </span>
        </div>

        {/* Left: Home Link */}
        <div>
          <Link
            to="/vendor_home"
            style={{
              color: "#fff",
              textDecoration: "none",
              fontSize: "18px",
              fontWeight: "bold",
            }}
          >
            Home
          </Link>
        </div>
 {/* Left: create subcription plan */}
 <div>
          <Link
            to="/createPlan"
            style={{
              color: "#fff",
              textDecoration: "none",
              fontSize: "18px",
              fontWeight: "bold",
            }}
          >
            Create Subscription Plan
          </Link>
        </div>

        {/* Spacer for Center Alignment */}
        <div style={{ flex: 1 }}></div>

        {/* Right: Logout Link */}
        <div>
          <Link
            to="/logout"
            style={{
              color: "#fff",
              textDecoration: "none",
              fontSize: "16px",
            }}
          >
            Logout
          </Link>
        </div>
      </nav>

      {/* Welcome Section */}
      <div style={{ paddingTop: "60px", fontFamily: "Arial, sans-serif" }}>
        <div className="container mt-4">
          <h1 className="text-center">
            Welcome {vendor && vendor.user.fname} {vendor && vendor.user.lname}
          </h1>

          {/* Vendor Details Section */}
          {vendor && (
            <div className="card mt-4 shadow">
              <div className="card-body">
                <h2 className="card-title">Vendor Details</h2>
                <p>
                  <strong>Vendor ID:</strong> {vendor.vendorId}
                </p>
                <p>
                  <strong>Is Verified:</strong> {vendor.isVerified ? "Yes" : "No"}
                </p>

                <h3 className="mt-4">User Details</h3>
                <p>
                  <strong>Name:</strong> {vendor.user.fname} {vendor.user.lname}
                </p>
                <p>
                  <strong>Email:</strong> {vendor.user.email}
                </p>
                <p>
                  <strong>Contact:</strong> {vendor.user.contact}
                </p>

                <h3 className="mt-4">Address</h3>
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
            </div>
          )}
        </div>
      </div>
    </div>
  );
}
