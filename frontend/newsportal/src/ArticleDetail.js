import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { useParams, useNavigate } from 'react-router-dom';
import './ArticleDetail.css';

function ArticleDetail() {
  const { id } = useParams();
  const [article, setArticle] = useState(null);
  const [message, setMessage] = useState('');
  const navigate = useNavigate();

  useEffect(() => {
    const fetchArticle = async () => {
      try {
        const response = await axios.get(`http://localhost:8080/articles/public/${id}`);
        setArticle(response.data);
      } catch (error) {
        setMessage('Failed to load article. Please try again.');
      }
    };

    fetchArticle();
  }, [id]);

  const handleReturn = () => {
    navigate(-1); // Navigate back to the previous page
  };

  if (!article) {
    return <div className="ArticleDetail">{message || 'Loading...'}</div>;
  }

  return (
    <div className="ArticleDetail">
      <button className="floating-button" onClick={handleReturn}>Return</button>
      <h2>{article.title}</h2>
      <hr />
      <p>{article.content}</p>
      <div className="author">By {article.author}</div>
      <div className="createdAt">Created at: {new Date(article.createdAt).toLocaleString()}</div>
    </div>
  );
}

export default ArticleDetail;