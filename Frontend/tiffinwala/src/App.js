import './App.css';
import LoginFom from './Components/LoginForm';
import Home from './Components/Home';
import { Route, Routes } from 'react-router';
import { Link } from 'react-router-dom';
import AdminHome from './Components/AdminHome';
import { useSelector } from 'react-redux';
import LogoutComp from './Components/LogoutComponent';
import VendorHome from './Components/VendorHome';
import UserVendorForm from './Components/UserReg';
import CustomerHome from './Components/CutomerHome';
import CreateSubscriptionPlan from './Components/CreateSubscriptionPlan';

function App() {
  const mystate = useSelector((state) => state.logged);
  console.log(mystate);
  return (
    <div className="App">
      {/* Navigation Bar */}
      {!mystate.loggedIn && (
        <nav className="navbar navbar-expand-lg navbar-dark bg-dark shadow">
          <div className="container-fluid">
            <a className="navbar-brand" href="/">
              Tiffinwala
            </a>
            <button
              className="navbar-toggler"
              type="button"
              data-bs-toggle="collapse"
              data-bs-target="#navbarNav"
              aria-controls="navbarNav"
              aria-expanded="false"
              aria-label="Toggle navigation"
            >
              <span className="navbar-toggler-icon"></span>
            </button>
            <div className="collapse navbar-collapse" id="navbarNav">
              <ul className="navbar-nav ms-auto">
                <li className="nav-item">
                  <Link to="/Login" className="nav-link">
                    Login
                  </Link>
                </li>
                <li className="nav-item">
                  <Link to="/Register" className="nav-link">
                    Register
                  </Link>
                </li>
              </ul>
            </div>
          </div>
        </nav>
      )}

      {/* Routing Section */}
      <div className="container mt-4">
        <Routes>
          <Route exact path="/" element={<Home />} />
          <Route exact path="/Login" element={<LoginFom />} />
          <Route exact path="/admin_home" element={<AdminHome />} />
          <Route exact path="/vendor_home" element={<VendorHome />} />
          <Route exact path="/logout" element={<LogoutComp />} />
          <Route exact path="/Register" element={<UserVendorForm />} />
          <Route exact path="/customer_home" element={<CustomerHome />} />
          <Route exact path="/CreatePlan" element={<CreateSubscriptionPlan />} />
         
        </Routes>
      </div>

      {/* Footer */}
      <footer
        style={{
          background: 'linear-gradient(to right, #0f2027, #203a43, #2c5364)',
          color: 'white',
          padding: '10px 0',
        }}
        className="text-center mt-auto"
      >
        <p className="mb-0">
          © {new Date().getFullYear()} <strong>Tiffinwala</strong>. Delivering Happiness.
        </p>
        <small>All Rights Reserved | Privacy Policy</small>
      </footer>
    </div>
  );
}

export default App;
