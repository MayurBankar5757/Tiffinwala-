import { useState, useEffect } from 'react';
import { Table, Button, Spinner, Alert, ButtonGroup } from 'react-bootstrap';
import { Link } from 'react-router-dom';

const GetAllVendors = () => {
  const [vendors, setVendors] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [filterStatus, setFilterStatus] = useState('all'); // 'all', 'approved', 'disabled'

  useEffect(() => {
    const fetchVendors = async () => {
      try {
        const response = await fetch('https://localhost:7282/api/Vendor/GetVendors');
        if (!response.ok) {
          throw new Error('Network response was not ok');
        }
        const data = await response.json();
        setVendors(data);
        setLoading(false);
      } catch (err) {
        setError(err.message);
        setLoading(false);
      }
    };

    fetchVendors();
  }, []);

  const handleToggleVerification = async (vendorId) => {
    try {
      // Optimistic UI update
      const updatedVendors = [...vendors];
      const vendorIndex = updatedVendors.findIndex(v => v.vendorId === vendorId);
      updatedVendors[vendorIndex].isVerified = !updatedVendors[vendorIndex].isVerified;
      setVendors(updatedVendors);

      // API call to update verification status
      const response = await fetch(`https://localhost:7282/api/Vendor/ToggleVendorVerification/${vendorId}`, {
        method: 'PUT',
      });

      if (!response.ok) {
        throw new Error('Failed to update vendor status');
      }
    } catch (err) {
      // Revert UI if API call fails
      const revertedVendors = [...vendors];
      setVendors(revertedVendors);
      alert('Failed to update vendor status');
    }
  };

  const filteredVendors = vendors.filter(vendor => {
    if (filterStatus === 'approved') return vendor.isVerified;
    if (filterStatus === 'disabled') return !vendor.isVerified;
    return true; // 'all' filter
  });

  if (loading) {
    return (
      <div className="text-center mt-5">
        <Spinner animation="border" role="status">
          <span className="visually-hidden">Loading...</span>
        </Spinner>
      </div>
    );
  }

  if (error) {
    return (
      <Alert variant="danger" className="m-3">
        Error loading vendors: {error}
      </Alert>
    );
  }

  return (
    <div className="container mt-4">
      <div className="d-flex justify-content-between align-items-center mb-4">
        <h2>All Vendors</h2>
        <ButtonGroup>
          <Button
            variant={filterStatus === 'all' ? 'primary' : 'outline-primary'}
            onClick={() => setFilterStatus('all')}
          >
            All ({vendors.length})
          </Button>
          <Button
            variant={filterStatus === 'approved' ? 'success' : 'outline-success'}
            onClick={() => setFilterStatus('approved')}
          >
            Approved ({vendors.filter(v => v.isVerified).length})
          </Button>
          <Button
            variant={filterStatus === 'disabled' ? 'danger' : 'outline-danger'}
            onClick={() => setFilterStatus('disabled')}
          >
            Disabled ({vendors.filter(v => !v.isVerified).length})
          </Button>
        </ButtonGroup>
      </div>

      <Table striped bordered hover responsive>
        <thead>
          <tr>
            <th>Vendor ID</th>
            <th>Name</th>
            <th>Email</th>
            <th>Contact</th>
            <th>Aadhar No.</th>
            <th>Food License</th>
            <th>Status</th>
            <th>Actions</th>
          </tr>
        </thead>
        <tbody>
          {filteredVendors.map((vendor) => (
            <tr key={vendor.vendorId}>
              <td>{vendor.vendorId}</td>
              <td>{`${vendor.uidNavigation.fname} ${vendor.uidNavigation.lname}`}</td>
              <td>{vendor.uidNavigation.email}</td>
              <td>{vendor.uidNavigation.contact}</td>
              <td>{vendor.adharNo}</td>
              <td>{vendor.foodLicenceNo}</td>
              <td>
                <span className={`badge ${vendor.isVerified ? 'bg-success' : 'bg-warning'}`}>
                  {vendor.isVerified ? 'Approved' : 'Pending'}
                </span>
              </td>
              <td>
                <Button
                  variant={vendor.isVerified ? 'danger' : 'success'}
                  size="sm"
                  onClick={() => handleToggleVerification(vendor.vendorId)}
                >
                  {vendor.isVerified ? 'Disable' : 'Approve'}
                </Button>
              </td>
            </tr>
          ))}
        </tbody>
      </Table>

      <div className="mt-4">
        <Link to="/admin_home" className="btn btn-secondary">
          Back to Admin
        </Link>
      </div>
    </div>
  );
};

export default GetAllVendors;