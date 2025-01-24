import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { useParams } from 'react-router-dom';
import './ArticleDetail.css';

function ArticleDetail() {
  const { id } = useParams();
  const [article, setArticle] = useState(null);
  const [message, setMessage] = useState('');

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

  if (!article) {
    return <div className="ArticleDetail">{message || 'Loading...'}</div>;
  }

  return (
    <div className="ArticleDetail">
      <h2>{article.title}</h2>
      <p>{article.content}</p>
      <p><small>By {article.author}</small></p>
      <p><small>Created at: {new Date(article.createdAt).toLocaleString()}</small></p>
      <p><small>Modified at: {new Date(article.modifiedAt).toLocaleString()}</small></p>
    </div>
  );
}

export default ArticleDetail;