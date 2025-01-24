import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { Link } from 'react-router-dom';
import './Home.css';

function Home() {
  const [articles, setArticles] = useState([]);
  const [filteredArticles, setFilteredArticles] = useState([]);
  const [message, setMessage] = useState('');
  const [searchTerm, setSearchTerm] = useState('');

  useEffect(() => {
    const fetchArticles = async () => {
      try {
        const response = await axios.get('http://localhost:8080/articles/public');
        console.log('Fetched articles:', response.data); // Debugging line
        setArticles(response.data);
        setFilteredArticles(response.data);
      } catch (error) {
        setMessage('Failed to load articles. Please try again.');
      }
    };

    fetchArticles();
  }, []);

  const handleSearchChange = (e) => {
    const term = e.target.value;
    setSearchTerm(term);
    if (term === '') {
      setFilteredArticles(articles);
    } else {
      const filtered = articles.filter(article =>
        article.title.toLowerCase().includes(term.toLowerCase()) ||
        article.content.toLowerCase().includes(term.toLowerCase())
      );
      setFilteredArticles(filtered);
    }
  };

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
      <h2>Articles</h2>
      {message && <div className="alert">{message}</div>}
      <form className="search-form">
        <input
          type="text"
          placeholder="Search articles..."
          value={searchTerm}
          onChange={handleSearchChange}
        />
      </form>
      <div className="articles-grid">
        {filteredArticles.map((article) => (
          <div key={article.titleId} className="article-card">
            <h3>{article.title}</h3>
            <p>{article.content}</p>
            <p><small>Created at: {formatTimeAgo(article.createdAt)}</small></p>
            <Link to={`/articles/${article.titleId}`}>Read more</Link>
          </div>
        ))}
      </div>
    </div>
  );
}

export default Home;