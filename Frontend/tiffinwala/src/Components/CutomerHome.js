import { Link } from "react-router-dom";

export default function CustomerHome() {
    return (
        <div>
            <header className="navbar navbar-expand-lg navbar-dark bg-dark shadow">
                <div className="container">
                    <Link to="/" className="navbar-brand">
                        MyApp
                    </Link>
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
                                <Link to="/admin_home" className="nav-link">
                                    Home
                                </Link>
                            </li>
                            <li className="nav-item">
                                <Link to="/logout" className="nav-link">
                                    Logout
                                </Link>
                            </li>
                        </ul>
                    </div>
                </div>
            </header>
            <main className="container mt-4">
                <h1>Customer Home Component</h1>
            </main>
        </div>
    );
}
