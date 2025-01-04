import { Link } from "react-router-dom";

export default function CustomerHome() {
    return (
        <div>
            <header className='nav '>


                <Link to="/admin_home" className="nav-link">Home</Link>


                <Link to="/logout" className="nav-link">Logut</Link>

            </header>
            < h1>Customer Home comp</h1>
        </div>
    )
}