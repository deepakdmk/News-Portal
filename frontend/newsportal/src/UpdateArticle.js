import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { useParams, useNavigate } from 'react-router-dom';
import './UpdateArticle.css';

function UpdateArticle() {
  const { id } = useParams();
  const [formData, setFormData] = useState({
    title: '',
    content: '',
  });
  const [message, setMessage] = useState('');
  const [loading, setLoading] = useState(false);
  const navigate = useNavigate();

  useEffect(() => {
    const fetchArticle = async () => {
      try {
        const response = await axios.get(`http://localhost:8080/articles/public/${id}`);
        setFormData({
          title: response.data.title,
          content: response.data.content,
        });
      } catch (error) {
        if (error.response && error.response.data && error.response.data.message) {
          setMessage(error.response.data.message);
        } else {
          setMessage('Failed to load article. Please try again.');
        }
      }
      setTimeout(() => setMessage(''), 5000); // Clear message after 5 seconds
    };

    fetchArticle();
  }, [id]);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData({
      ...formData,
      [name]: value,
    });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (window.confirm('Are you sure you want to update this article?')) {
      setLoading(true);
      try {
        const token = localStorage.getItem('token');
        await axios.put('http://localhost:8080/articles/articles', { titleId: id, ...formData }, {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        });
        setMessage('Article updated successfully!');
        navigate('/articles');
      } catch (error) {
        if (error.response && error.response.data && error.response.data.message) {
          setMessage(error.response.data.message);
        } else {
          setMessage('Failed to update article. Please try again.');
        }
      }
      setLoading(false);
      setTimeout(() => setMessage(''), 5000); // Clear message after 5 seconds
    }
  };

  const handleCancel = () => {
    navigate('/articles');
  };

  const handleDelete = async () => {
    if (window.confirm('Are you sure you want to delete this article?')) {
      setLoading(true);
      try {
        const token = localStorage.getItem('token');
        await axios.delete(`http://localhost:8080/articles/articles/${id}`, {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        });
        setMessage('Article deleted successfully!');
        navigate('/articles');
      } catch (error) {
        if (error.response && error.response.data && error.response.data.message) {
          setMessage(error.response.data.message);
        } else {
          setMessage('Failed to delete article. Please try again.');
        }
      }
      setLoading(false);
      setTimeout(() => setMessage(''), 5000); // Clear message after 5 seconds
    }
  };

  return (
    <div className="UpdateArticle">
      <h2>Update Article</h2>
      {message && <div className="alert">{message}</div>}
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
          {loading ? 'Updating...' : 'Update'}
        </button>
        <button type="button" onClick={handleCancel} disabled={loading}>
          Cancel
        </button>
        <button type="button" className="delete-button" onClick={handleDelete} disabled={loading}>
          Delete
        </button>
      </form>
    </div>
  );
}

export default UpdateArticle;