import React, { useState, useEffect } from 'react';
import { bookService, categoryService } from '../services/api';
import '../styles/Books.css';

const Books = ({ userRole }) => {
  const [books, setBooks] = useState([]);
  const [categories, setCategories] = useState([]);
  const [showModal, setShowModal] = useState(false);
  const [editingBook, setEditingBook] = useState(null);
  const [searchTerm, setSearchTerm] = useState('');
  const [loading, setLoading] = useState(true);
  const [formData, setFormData] = useState({
    title: '',
    author: '',
    available: true,
    bookCategory: { categoryId: '' }
  });

  useEffect(() => {
    fetchBooks();
    fetchCategories();
  }, []);

  const fetchBooks = async () => {
    try {
      const response = await bookService.getAll();
      setBooks(response.data);
    } catch (error) {
      console.error('Error fetching books:', error);
    } finally {
      setLoading(false);
    }
  };

  const fetchCategories = async () => {
    try {
      const response = await categoryService.getAll();
      setCategories(response.data);
    } catch (error) {
      console.error('Error fetching categories:', error);
    }
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const bookData = {
        ...formData,
        bookCategory: { categoryId: parseInt(formData.bookCategory.categoryId) }
      };

      if (editingBook) {
        await bookService.update(editingBook.bookId, bookData);
      } else {
        await bookService.create(bookData);
      }
      
      fetchBooks();
      resetForm();
    } catch (error) {
      console.error('Error saving book:', error);
      alert('Error saving book. Please try again.');
    }
  };

  const handleEdit = (book) => {
    setEditingBook(book);
    setFormData({
      title: book.title,
      author: book.author,
      available: book.available,
      bookCategory: { categoryId: book.bookCategory?.categoryId || '' }
    });
    setShowModal(true);
  };

  const handleDelete = async (id) => {
    if (window.confirm('Are you sure you want to delete this book?')) {
      try {
        await bookService.delete(id);
        fetchBooks();
      } catch (error) {
        console.error('Error deleting book:', error);
        alert('Error deleting book. Please try again.');
      }
    }
  };

  const resetForm = () => {
    setFormData({
      title: '',
      author: '',
      available: true,
      bookCategory: { categoryId: '' }
    });
    setEditingBook(null);
    setShowModal(false);
  };

  const filteredBooks = books.filter(book =>
    book.title.toLowerCase().includes(searchTerm.toLowerCase()) ||
    book.author.toLowerCase().includes(searchTerm.toLowerCase())
  );

  if (loading) {
    return <div className="loading">Loading books...</div>;
  }

  return (
    <div className="books-container">
      <div className="books-header">
        <h1>Books Management</h1>
        <div className="books-actions">
          <input
            type="text"
            placeholder="Search books..."
            value={searchTerm}
            onChange={(e) => setSearchTerm(e.target.value)}
            className="search-input"
          />
          {userRole === 'librarian' && (
            <button 
              onClick={() => setShowModal(true)}
              className="add-btn"
            >
              Add Book
            </button>
          )}
        </div>
      </div>

      <div className="books-grid">
        {filteredBooks.map((book) => (
          <div key={book.bookId} className="book-card">
            <div className="book-info">
              <h3>{book.title}</h3>
              <p className="book-author">by {book.author}</p>
              <p className="book-category">
                Category: {book.bookCategory?.categoryName || 'Uncategorized'}
              </p>
              <span className={`availability ${book.available ? 'available' : 'unavailable'}`}>
                {book.available ? 'Available' : 'Not Available'}
              </span>
            </div>
            {userRole === 'librarian' && (
              <div className="book-actions">
                <button 
                  onClick={() => handleEdit(book)}
                  className="edit-btn"
                >
                  Edit
                </button>
                <button 
                  onClick={() => handleDelete(book.bookId)}
                  className="delete-btn"
                >
                  Delete
                </button>
              </div>
            )}
          </div>
        ))}
      </div>

      {showModal && (
        <div className="modal-overlay">
          <div className="modal">
            <div className="modal-header">
              <h2>{editingBook ? 'Edit Book' : 'Add New Book'}</h2>
              <button onClick={resetForm} className="close-btn">×</button>
            </div>
            <form onSubmit={handleSubmit} className="book-form">
              <div className="form-group">
                <label>Title</label>
                <input
                  type="text"
                  value={formData.title}
                  onChange={(e) => setFormData({...formData, title: e.target.value})}
                  required
                />
              </div>
              <div className="form-group">
                <label>Author</label>
                <input
                  type="text"
                  value={formData.author}
                  onChange={(e) => setFormData({...formData, author: e.target.value})}
                  required
                />
              </div>
              <div className="form-group">
                <label>Category</label>
                <select
                  value={formData.bookCategory.categoryId}
                  onChange={(e) => setFormData({
                    ...formData, 
                    bookCategory: { categoryId: e.target.value }
                  })}
                  required
                >
                  <option value="">Select Category</option>
                  {categories.map((category) => (
                    <option key={category.categoryId} value={category.categoryId}>
                      {category.categoryName}
                    </option>
                  ))}
                </select>
              </div>
              <div className="form-group checkbox-group">
                <label>
                  <input
                    type="checkbox"
                    checked={formData.available}
                    onChange={(e) => setFormData({...formData, available: e.target.checked})}
                  />
                  Available
                </label>
              </div>
              <div className="form-actions">
                <button type="button" onClick={resetForm} className="cancel-btn">
                  Cancel
                </button>
                <button type="submit" className="save-btn">
                  {editingBook ? 'Update' : 'Add'} Book
                </button>
              </div>
            </form>
          </div>
        </div>
      )}
    </div>
  );
};

export default Books;