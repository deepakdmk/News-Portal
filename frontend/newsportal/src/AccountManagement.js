import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import './AccountManagement.css';

function AccountManagement() {
  const [user, setUser] = useState(null);
  const [formData, setFormData] = useState({
    fullName: '',
    password: '',
    confirmPassword: '',
  });
  const [message, setMessage] = useState('');
  const [loading, setLoading] = useState(false);
  const navigate = useNavigate();

  useEffect(() => {
    const fetchUser = async () => {
      try {
        const token = localStorage.getItem('token');
        const response = await axios.get('http://localhost:8080/users/me', {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        });
        setUser(response.data);
        setFormData({
          fullName: response.data.fullName,
          password: '',
          confirmPassword: '',
        });
      } catch (error) {
        setMessage('Failed to load user data. Please try again.');
      }
    };

    fetchUser();
  }, []);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData({
      ...formData,
      [name]: value,
    });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (formData.password !== formData.confirmPassword) {
      setMessage('Passwords do not match.');
      return;
    }
    setLoading(true);
    try {
      const token = localStorage.getItem('token');
      await axios.put('http://localhost:8080/users/me', formData, {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });
      setMessage('Profile updated successfully!');
    } catch (error) {
      setMessage('Failed to update profile. Please try again.');
    }
    setLoading(false);
    setTimeout(() => setMessage(''), 5000); // Clear message after 5 seconds
  };

  const handleCancel = () => {
    navigate(-1); // Navigate back to the previous page
  };

  return (
    <div className="AccountManagement">
      <h2>Account Management</h2>
      {message && <div className="alert">{message}</div>}
      {user && (
        <form onSubmit={handleSubmit}>
          <div className="profile-pic">
            <img src="/default-profile.png" alt="Profile" />
          </div>
          <div>
            <label>Full Name:</label>
            <input
              type="text"
              name="fullName"
              value={formData.fullName}
              onChange={handleChange}
              required
            />
          </div>
          <div>
            <label>Password:</label>
            <input
              type="password"
              name="password"
              value={formData.password}
              onChange={handleChange}
            />
          </div>
          {formData.password && (
            <div>
              <label>Confirm Password:</label>
              <input
                type="password"
                name="confirmPassword"
                value={formData.confirmPassword}
                onChange={handleChange}
                required
              />
            </div>
          )}
          <button type="submit" className="submit-button" disabled={loading}>
            {loading ? 'Updating...' : 'Update'}
          </button>
          <button type="button" className="cancel-button" onClick={handleCancel} disabled={loading}>
            Cancel
          </button>
        </form>
      )}
    </div>
  );
}

export default AccountManagement;