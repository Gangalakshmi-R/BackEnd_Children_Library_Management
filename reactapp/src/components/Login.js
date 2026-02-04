import React, { useState } from 'react';
import { authService } from '../services/api';
import '../styles/Login.css';

const Login = ({ onLogin }) => {
  const [formData, setFormData] = useState({
    username: '',
    password: ''
  });
  const [isSignup, setIsSignup] = useState(false);
  const [signupData, setSignupData] = useState({
    username: '',
    email: '',
    password: '',
    role: 'member'
  });
  const [error, setError] = useState('');
  const [loading, setLoading] = useState(false);

  const handleLogin = async (e) => {
    e.preventDefault();
    setLoading(true);
    setError('');
    
    try {
      const response = await authService.login(formData);
      const { token, username, roles } = response.data;
      const role = roles[0].replace('ROLE_', '').toLowerCase();
      onLogin(token, role);
    } catch (error) {
      setError('Invalid username or password');
    } finally {
      setLoading(false);
    }
  };

  const handleSignup = async (e) => {
    e.preventDefault();
    setLoading(true);
    setError('');
    
    try {
      await authService.signup(signupData);
      setIsSignup(false);
      setError('');
      alert('Registration successful! Please login.');
    } catch (error) {
      setError('Registration failed. Please try again.');
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="login-container">
      <div className="login-card">
        <div className="login-header">
          <h1>Children Library Management</h1>
          <p>{isSignup ? 'Create Account' : 'Welcome Back'}</p>
        </div>

        {!isSignup ? (
          <form onSubmit={handleLogin} className="login-form">
            <div className="form-group">
              <label>Username</label>
              <input
                type="text"
                value={formData.username}
                onChange={(e) => setFormData({...formData, username: e.target.value})}
                required
              />
            </div>
            <div className="form-group">
              <label>Password</label>
              <input
                type="password"
                value={formData.password}
                onChange={(e) => setFormData({...formData, password: e.target.value})}
                required
              />
            </div>
            {error && <div className="error-message">{error}</div>}
            <button type="submit" disabled={loading} className="login-btn">
              {loading ? 'Signing In...' : 'Sign In'}
            </button>
            <p className="switch-form">
              Don't have an account? 
              <span onClick={() => setIsSignup(true)}> Sign Up</span>
            </p>
          </form>
        ) : (
          <form onSubmit={handleSignup} className="login-form">
            <div className="form-group">
              <label>Username</label>
              <input
                type="text"
                value={signupData.username}
                onChange={(e) => setSignupData({...signupData, username: e.target.value})}
                required
              />
            </div>
            <div className="form-group">
              <label>Email</label>
              <input
                type="email"
                value={signupData.email}
                onChange={(e) => setSignupData({...signupData, email: e.target.value})}
                required
              />
            </div>
            <div className="form-group">
              <label>Password</label>
              <input
                type="password"
                value={signupData.password}
                onChange={(e) => setSignupData({...signupData, password: e.target.value})}
                required
              />
            </div>
            <div className="form-group">
              <label>Role</label>
              <select
                value={signupData.role}
                onChange={(e) => setSignupData({...signupData, role: e.target.value})}
              >
                <option value="member">Member</option>
                <option value="librarian">Librarian</option>
              </select>
            </div>
            {error && <div className="error-message">{error}</div>}
            <button type="submit" disabled={loading} className="login-btn">
              {loading ? 'Creating Account...' : 'Sign Up'}
            </button>
            <p className="switch-form">
              Already have an account? 
              <span onClick={() => setIsSignup(false)}> Sign In</span>
            </p>
          </form>
        )}
      </div>
    </div>
  );
};

export default Login;