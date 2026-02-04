import React, { useState, useEffect } from 'react';
import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';
import Login from './components/Login';
import Dashboard from './components/Dashboard';
import Books from './components/Books';
import Members from './components/Members';
import Borrows from './components/Borrows';
import Fines from './components/Fines';
import Categories from './components/Categories';
import Navbar from './components/Navbar';
import './styles/App.css';

function App() {
  const [isAuthenticated, setIsAuthenticated] = useState(false);
  const [userRole, setUserRole] = useState('');

  useEffect(() => {
    const token = localStorage.getItem('token');
    const role = localStorage.getItem('userRole');
    if (token) {
      setIsAuthenticated(true);
      setUserRole(role || '');
    }
  }, []);

  const handleLogin = (token, role) => {
    localStorage.setItem('token', token);
    localStorage.setItem('userRole', role);
    setIsAuthenticated(true);
    setUserRole(role);
  };

  const handleLogout = () => {
    localStorage.removeItem('token');
    localStorage.removeItem('userRole');
    setIsAuthenticated(false);
    setUserRole('');
  };

  return (
    <Router>
      <div className="App">
        {isAuthenticated && <Navbar onLogout={handleLogout} userRole={userRole} />}
        <Routes>
          <Route 
            path="/login" 
            element={
              !isAuthenticated ? 
              <Login onLogin={handleLogin} /> : 
              <Navigate to="/dashboard" />
            } 
          />
          <Route 
            path="/dashboard" 
            element={
              isAuthenticated ? 
              <Dashboard userRole={userRole} /> : 
              <Navigate to="/login" />
            } 
          />
          <Route 
            path="/books" 
            element={
              isAuthenticated ? 
              <Books userRole={userRole} /> : 
              <Navigate to="/login" />
            } 
          />
          <Route 
            path="/members" 
            element={
              isAuthenticated ? 
              <Members userRole={userRole} /> : 
              <Navigate to="/login" />
            } 
          />
          <Route 
            path="/borrows" 
            element={
              isAuthenticated ? 
              <Borrows userRole={userRole} /> : 
              <Navigate to="/login" />
            } 
          />
          <Route 
            path="/fines" 
            element={
              isAuthenticated ? 
              <Fines userRole={userRole} /> : 
              <Navigate to="/login" />
            } 
          />
          <Route 
            path="/categories" 
            element={
              isAuthenticated ? 
              <Categories userRole={userRole} /> : 
              <Navigate to="/login" />
            } 
          />
          <Route path="/" element={<Navigate to="/dashboard" />} />
        </Routes>
      </div>
    </Router>
  );
}

export default App;