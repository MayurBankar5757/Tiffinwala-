import React from 'react';
import { Link } from 'react-router-dom';
import { useSelector } from 'react-redux';

const Navbar = () => {
  const loggedIn = useSelector(state => state.logged.loggedIn);
  const role = useSelector(state => state.logged.role);

  return (
    <nav className="navbar navbar-expand-lg navbar-dark bg-dark shadow">
      <div className="container-fluid">
        <a className="navbar-brand" href="/">Tiffinwala</a>
        <button className="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
          <span className="navbar-toggler-icon"></span>
        </button>
        <div className="collapse navbar-collapse" id="navbarNav">
          <ul className="navbar-nav ms-auto">
            {!loggedIn ? (
              <>
                <li className="nav-item">
                  <Link to="/Login" className="nav-link">Login</Link>
                </li>
                <li className="nav-item">
                  <Link to="/Register" className="nav-link">Register</Link>
                </li>
              </>
            ) : (
              <>
                {role === 1 && (
                  <li className="nav-item">
                    <Link to="/admin_home" className="nav-link">Admin Home</Link>
                  </li>
                )}
                {role === 2 && (
                  <li className="nav-item">
                    <Link to="/vendor_home" className="nav-link">Vendor Home</Link>
                  </li>
                )}
                {role === 3 && (
                  <li className="nav-item">
                    <Link to="/customer_home" className="nav-link">Customer Home</Link>
                  </li>
                )}
                <li className="nav-item">
                  <Link to="/logout" className="nav-link">Logout</Link>
                </li>
              </>
            )}
          </ul>
        </div>
      </div>
    </nav>
  );
};

export default Navbar;
