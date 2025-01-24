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
      <hr />
      <p>{article.content}</p>
      <div className="author">By {article.author}</div>
      <div className="createdAt">Created at: {new Date(article.createdAt).toLocaleString()}</div>
    </div>
  );
}

export default ArticleDetail;