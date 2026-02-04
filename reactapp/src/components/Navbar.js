import React from 'react';
import { Link, useLocation } from 'react-router-dom';
import '../styles/Navbar.css';

const Navbar = ({ onLogout, userRole }) => {
  const location = useLocation();

  return (
    <nav className="navbar">
      <div className="nav-brand">
        <h2>📚 Library Management</h2>
      </div>
      <div className="nav-links">
        <Link 
          to="/dashboard" 
          className={location.pathname === '/dashboard' ? 'active' : ''}
        >
          Dashboard
        </Link>
        <Link 
          to="/books" 
          className={location.pathname === '/books' ? 'active' : ''}
        >
          Books
        </Link>
        <Link 
          to="/members" 
          className={location.pathname === '/members' ? 'active' : ''}
        >
          Members
        </Link>
        <Link 
          to="/borrows" 
          className={location.pathname === '/borrows' ? 'active' : ''}
        >
          Borrows
        </Link>
        <Link 
          to="/fines" 
          className={location.pathname === '/fines' ? 'active' : ''}
        >
          Fines
        </Link>
        <Link 
          to="/categories" 
          className={location.pathname === '/categories' ? 'active' : ''}
        >
          Categories
        </Link>
      </div>
      <div className="nav-user">
        <span className="user-role">{userRole}</span>
        <button onClick={onLogout} className="logout-btn">Logout</button>
      </div>
    </nav>
  );
};

export default Navbar;