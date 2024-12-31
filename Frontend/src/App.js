//import logo from './logo.svg';
import './App.css';
import LoginFom from './Components/LoginForm';
import Home from './Components/Home';
import { Route, Routes } from 'react-router';
import { Link } from 'react-router-dom';
import AdminHome from './Components/AdminHome';
import { useSelector } from 'react-redux';

function App() {
  const mystate = useSelector((state)=>state.logged );
  console.log(mystate);
  return (
    <div className="App">
     <div style={{display: mystate.loggedIn ? "none":"block"}}>
     <header className='nav '>
      
      
        <Link  to="/" className="nav-link">Home</Link>
    
    
        <Link  to="/Login" className="nav-link">Login</Link>
     </header>
     </div>

        <Routes>

        <Route exact path='/' element={<Home/>}/>
        <Route exact path='/Login' element={<LoginFom/>}/>

        <Route exact path='/admin_home' element={<AdminHome/>}/>


        </Routes>

    

     
    </div>
  );
}

export default App;
