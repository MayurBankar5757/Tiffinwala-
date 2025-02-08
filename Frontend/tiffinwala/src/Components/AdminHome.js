import { Link } from "react-router-dom";

export default function AdminHome() {
    return (
        <div className="container-fluid">
           
            <div className="container">
                <div className="row">
                    <div className="col-12 mb-4">
                        <h2 className="text-center">Admin Actions</h2>
                    </div>

                    <div className="col-md-4 mb-4">
                        <Link to="/admin/vendors/all" className="card text-decoration-none text-dark h-100 shadow">
                            <div className="card-body">
                                <h5 className="card-title">All Vendors</h5>
                                <p className="card-text">View complete list of all registered vendors</p>
                            </div>
                        </Link>
                    </div>

 

                    {/* <div className="col-md-4 mb-4">
                        <Link to="/admin/vendors/new" className="card text-decoration-none text-dark h-100 shadow">
                            <div className="card-body">
                                <h5 className="card-title">Blocked Vendors</h5>
                                <p className="card-text">Manage blocked or suspended vendors</p>
                            </div>
                        </Link>
                    </div>

                    <div className="col-md-4 mb-4">
                        <Link to="/admin/vendors/approve-pending" className="card text-decoration-none text-dark h-100 shadow">
                            <div className="card-body">
                                <h5 className="card-title">Approve Vendors</h5>
                                <p className="card-text">Review and approve pending vendor applications</p>
                            </div>
                        </Link>
                    </div> */}

                    {/* <div className="col-md-4 mb-4">
                        <Link to="/admin/vendors/remove" className="card text-decoration-none text-dark h-100 shadow">
                            <div className="card-body">
                                <h5 className="card-title">Remove Vendors</h5>
                                <p className="card-text">Remove vendors from the platform</p>
                            </div>
                        </Link>
                    </div> */}

                    <div className="col-md-4 mb-4">
                        <Link to="/admin/reports" className="card text-decoration-none text-dark h-100 shadow">
                            <div className="card-body">
                                <h5 className="card-title">Generate Reports</h5>
                                <p className="card-text">Generate system reports and analytics</p>
                            </div>
                        </Link>
                    </div>
                </div>
            </div>
        </div>
    )
}