import { Link } from "react-router-dom";

export default function AdminHome() {
    return (
        <div>
            <header className='nav '>


                <Link to="/admin_home" className="nav-link">Home</Link>


                <Link to="/" className="nav-link">Logut</Link>

            </header>
            < h1>Admin Home comp</h1>
        </div>
    )
}