import React, { useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import './CreateArticle.css';

function CreateArticle() {
  const [formData, setFormData] = useState({
    title: '',
    content: '',
  });

  const [message, setMessage] = useState('');
  const [loading, setLoading] = useState(false);
  const navigate = useNavigate();

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData({
      ...formData,
      [name]: value,
    });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setLoading(true);
    try {
      const token = localStorage.getItem('token');
      const response = await axios.post('http://localhost:8080/articles/submit', formData, {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });
      setMessage('Article created successfully!');
      navigate('/articles');
    } catch (error) {
      setMessage('Failed to create article. Please try again.');
    }
    setLoading(false);
  };

  return (
    <div className="CreateArticle">
      <h2>Create Article</h2>
      <form onSubmit={handleSubmit}>
        <div>
          <label>Title:</label>
          <input
            type="text"
            name="title"
            value={formData.title}
            onChange={handleChange}
            required
          />
        </div>
        <div>
          <label>Content:</label>
          <textarea
            name="content"
            value={formData.content}
            onChange={handleChange}
            required
          />
        </div>
        <button type="submit" disabled={loading}>
          {loading ? 'Creating...' : 'Create'}
        </button>
      </form>
      {message && <p>{message}</p>}
    </div>
  );
}

export default CreateArticle;