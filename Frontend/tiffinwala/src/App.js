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
import { useImperativeHandle } from 'react';
import VendorPlanDetails from './Components/VedndorPlanDetails';
import Navbar from './Components/Navbar';

function App() {
  const mystate = useSelector((state) => state.logged);
  console.log(mystate);
  return (
    <div className="App">
       {/* Navbar Component */}
      <Navbar />


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
          <Route path="/VendorPlanDetails/:id" element={<VendorPlanDetails />} />


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
