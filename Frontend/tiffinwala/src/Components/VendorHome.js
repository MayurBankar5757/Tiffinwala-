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
            <header className="nav">
                <Link to="/vendor_home" className="nav-link">Home</Link>
                <Link to="/logout" className="nav-link">Logout</Link>
            </header>

            <h1>
                Welcome {vendor && vendor.user.fname} {vendor && vendor.user.lname}
            </h1>

            {/* Display specific vendor details */}
            {vendor && (
                <div>
                    <h2>Vendor Details</h2>
                    <p>Vendor ID: {vendor.vendorId}</p>
                    <p>Is Verified: {vendor.isVerified ? "Yes" : "No"}</p>
                    <h3>User Details</h3>
                    <p>Name: {vendor.user.fname} {vendor.user.lname}</p>
                    <p>Email: {vendor.user.email}</p>
                    <p>Contact: {vendor.user.contact}</p>
                    <h3>Address</h3>
                    <p>City: {vendor.user.address.city}</p>
                    <p>State: {vendor.user.address.state}</p>
                    <p>Area: {vendor.user.address.area}</p>
                    <p>Pincode: {vendor.user.address.pincode}</p>
                </div>
            )}
        </div>
    );
}
