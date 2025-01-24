import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { Link } from 'react-router-dom';
import './Home.css';

function Home() {
  const [articles, setArticles] = useState([]);
  const [message, setMessage] = useState('');

  useEffect(() => {
    const fetchArticles = async () => {
      try {
        const response = await axios.get('http://localhost:8080/articles/public');
        setArticles(response.data);
      } catch (error) {
        setMessage('Failed to load articles. Please try again.');
      }
    };

    fetchArticles();
  }, []);

  const formatTimeAgo = (dateString) => {
    const date = new Date(dateString);
    const now = new Date();
    const diff = now - date;

    const seconds = Math.floor(diff / 1000);
    const minutes = Math.floor(seconds / 60);
    const hours = Math.floor(minutes / 60);
    const days = Math.floor(hours / 24);

    if (days > 0) return `${days} day${days > 1 ? 's' : ''} ago`;
    if (hours > 0) return `${hours} hour${hours > 1 ? 's' : ''} ago`;
    if (minutes > 0) return `${minutes} minute${minutes > 1 ? 's' : ''} ago`;
    return `${seconds} second${seconds > 1 ? 's' : ''} ago`;
  };

  return (
    <div className="Home">
      <h2>Welcome to the News Portal</h2>
      {message && <p>{message}</p>}
      <div className="articles">
        {articles.map((article) => (
          <div key={article.titleId} className="article-card">
            <h3>{article.title}</h3>
            <p>{article.content.substring(0, 100)}...</p>
            <p><small>By {article.author}</small></p>
            <p><small>{formatTimeAgo(article.createdAt)}</small></p>
            <Link to={`/articles/${article.titleId}`}>Read more</Link>
          </div>
        ))}
      </div>
    </div>
  );
}

export default Home;