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
      await axios.post('http://localhost:8080/articles/articles', formData, {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });
      setMessage('Article created successfully!');
      navigate('/articles');
    } catch (error) {
      if (error.response && error.response.data && error.response.data.message) {
        setMessage(error.response.data.message);
      } else {
        setMessage('Failed to create article. Please try again.');
      }
    }
    setLoading(false);
    setTimeout(() => setMessage(''), 5000); // Clear message after 5 seconds
  };

  const handleCancel = () => {
    navigate('/articles');
  };

  const applyStyle = (style) => {
    document.execCommand(style, false, null);
  };

  return (
    <div className="CreateArticle">
      <h2>Create Article</h2>
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
          <div className="editor-toolbar">
            <button type="button" onClick={() => applyStyle('bold')}><b>B</b></button>
            <button type="button" onClick={() => applyStyle('italic')}><i>I</i></button>
            <button type="button" onClick={() => applyStyle('underline')}><u>U</u></button>
          </div>
          <textarea
            name="content"
            value={formData.content}
            onChange={handleChange}
            required
            className="editor"
          />
        </div>
        <button type="submit" disabled={loading}>
          {loading ? 'Creating...' : 'Create'}
        </button>
        <button type="button" onClick={handleCancel} disabled={loading}>
          Cancel
        </button>
      </form>
    </div>
  );
}

export default CreateArticle;