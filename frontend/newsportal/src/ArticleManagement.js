import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import './ArticleManagement.css';

function ArticleManagement() {
  const [articles, setArticles] = useState([]);
  const [message, setMessage] = useState('');
  const navigate = useNavigate();

  useEffect(() => {
    const fetchArticles = async () => {
      try {
        const token = localStorage.getItem('token');
        const response = await axios.get('http://localhost:8080/articles/articles/owned', {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        });
        setArticles(response.data);
      } catch (error) {
        if (error.response && error.response.data && error.response.data.message) {
          setMessage(error.response.data.message);
        } else {
          setMessage('Failed to load articles. Please try again.');
        }
      }
      setTimeout(() => setMessage(''), 5000); // Clear message after 5 seconds
    };

    fetchArticles();
  }, []);

  const handleCreateArticle = () => {
    navigate('/create-article');
  };

  const handleModifyArticle = (articleId) => {
    navigate(`/update-article/${articleId}`);
  };

  return (
    <div className="ArticleManagement">
      <h2>Your Articles</h2>
      {message && <div className="alert">{message}</div>}
      <button onClick={handleCreateArticle}>Create Article</button>
      <ul>
        {articles.map((article) => (
          <li key={article.titleId}>
            <h3>{article.title}</h3>
            <p>{article.content}</p>
            <p><small>Created at: {article.createdAt}</small></p>
            <p><small>Modified at: {article.modifiedAt}</small></p>
            <button onClick={() => handleModifyArticle(article.titleId)}>Modify</button>
          </li>
        ))}
      </ul>
    </div>
  );
}

export default ArticleManagement;