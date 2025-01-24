import React from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import './App.css';
import Navbar from './Navbar';
import Home from './Home';
import Registration from './Registration';
import Login from './Login';
import ArticleManagement from './ArticleManagement';
import CreateArticle from './CreateArticle';
import UpdateArticle from './UpdateArticle';
import ArticleDetail from './ArticleDetail';

function App() {
  return (
    <Router>
      <div className="App">
        <Navbar />
        <Routes>
          <Route exact path="/" element={<Home />} />
          <Route path="/register" element={<Registration />} />
          <Route path="/login" element={<Login />} />
          <Route path="/articles" element={<ArticleManagement />} />
          <Route path="/create-article" element={<CreateArticle />} />
          <Route path="/update-article/:id" element={<UpdateArticle />} />
          <Route path="/articles/:id" element={<ArticleDetail />} />
        </Routes>
      </div>
    </Router>
  );
}

export default App;