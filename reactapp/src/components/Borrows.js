import React, { useState, useEffect } from 'react';
import { borrowService, bookService, memberService } from '../services/api';
import '../styles/Borrows.css';

const Borrows = ({ userRole }) => {
  const [borrows, setBorrows] = useState([]);
  const [books, setBooks] = useState([]);
  const [members, setMembers] = useState([]);
  const [showModal, setShowModal] = useState(false);
  const [editingBorrow, setEditingBorrow] = useState(null);
  const [loading, setLoading] = useState(true);
  const [formData, setFormData] = useState({
    borrowDate: '',
    returnDate: '',
    book: { bookId: '' },
    child: { memberId: '' }
  });

  useEffect(() => {
    fetchBorrows();
    fetchBooks();
    fetchMembers();
  }, []);

  const fetchBorrows = async () => {
    try {
      const response = await borrowService.getAll();
      setBorrows(response.data);
    } catch (error) {
      console.error('Error fetching borrows:', error);
    } finally {
      setLoading(false);
    }
  };

  const fetchBooks = async () => {
    try {
      const response = await bookService.getAll();
      setBooks(response.data.filter(book => book.available));
    } catch (error) {
      console.error('Error fetching books:', error);
    }
  };

  const fetchMembers = async () => {
    try {
      const response = await memberService.getAll();
      setMembers(response.data);
    } catch (error) {
      console.error('Error fetching members:', error);
    }
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const borrowData = {
        ...formData,
        book: { bookId: parseInt(formData.book.bookId) },
        child: { memberId: parseInt(formData.child.memberId) }
      };

      if (editingBorrow) {
        await borrowService.update(editingBorrow.borrowId, borrowData);
      } else {
        await borrowService.create(borrowData);
      }
      
      fetchBorrows();
      resetForm();
    } catch (error) {
      console.error('Error saving borrow:', error);
      alert('Error saving borrow record. Please try again.');
    }
  };

  const handleEdit = (borrow) => {
    setEditingBorrow(borrow);
    setFormData({
      borrowDate: borrow.borrowDate,
      returnDate: borrow.returnDate,
      book: { bookId: borrow.book?.bookId || '' },
      child: { memberId: borrow.child?.memberId || '' }
    });
    setShowModal(true);
  };

  const handleDelete = async (id) => {
    if (window.confirm('Are you sure you want to delete this borrow record?')) {
      try {
        await borrowService.delete(id);
        fetchBorrows();
      } catch (error) {
        console.error('Error deleting borrow:', error);
        alert('Error deleting borrow record. Please try again.');
      }
    }
  };

  const resetForm = () => {
    setFormData({
      borrowDate: '',
      returnDate: '',
      book: { bookId: '' },
      child: { memberId: '' }
    });
    setEditingBorrow(null);
    setShowModal(false);
  };

  if (loading) {
    return <div className="loading">Loading borrows...</div>;
  }

  return (
    <div className="borrows-container">
      <div className="borrows-header">
        <h1>Borrow Management</h1>
        <button 
          onClick={() => setShowModal(true)}
          className="add-btn"
        >
          New Borrow
        </button>
      </div>

      <div className="borrows-table">
        <table>
          <thead>
            <tr>
              <th>ID</th>
              <th>Book</th>
              <th>Member</th>
              <th>Borrow Date</th>
              <th>Return Date</th>
              <th>Actions</th>
            </tr>
          </thead>
          <tbody>
            {borrows.map((borrow) => (
              <tr key={borrow.borrowId}>
                <td>{borrow.borrowId}</td>
                <td>{borrow.book?.title || 'Unknown Book'}</td>
                <td>{borrow.child?.name || 'Unknown Member'}</td>
                <td>{new Date(borrow.borrowDate).toLocaleDateString()}</td>
                <td>{new Date(borrow.returnDate).toLocaleDateString()}</td>
                <td>
                  <div className="action-buttons">
                    <button 
                      onClick={() => handleEdit(borrow)}
                      className="edit-btn"
                    >
                      Edit
                    </button>
                    <button 
                      onClick={() => handleDelete(borrow.borrowId)}
                      className="delete-btn"
                    >
                      Delete
                    </button>
                  </div>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>

      {showModal && (
        <div className="modal-overlay">
          <div className="modal">
            <div className="modal-header">
              <h2>{editingBorrow ? 'Edit Borrow' : 'New Borrow Record'}</h2>
              <button onClick={resetForm} className="close-btn">×</button>
            </div>
            <form onSubmit={handleSubmit} className="borrow-form">
              <div className="form-group">
                <label>Book</label>
                <select
                  value={formData.book.bookId}
                  onChange={(e) => setFormData({
                    ...formData, 
                    book: { bookId: e.target.value }
                  })}
                  required
                >
                  <option value="">Select Book</option>
                  {books.map((book) => (
                    <option key={book.bookId} value={book.bookId}>
                      {book.title} - {book.author}
                    </option>
                  ))}
                </select>
              </div>
              <div className="form-group">
                <label>Member</label>
                <select
                  value={formData.child.memberId}
                  onChange={(e) => setFormData({
                    ...formData, 
                    child: { memberId: e.target.value }
                  })}
                  required
                >
                  <option value="">Select Member</option>
                  {members.map((member) => (
                    <option key={member.memberId} value={member.memberId}>
                      {member.name} - {member.email}
                    </option>
                  ))}
                </select>
              </div>
              <div className="form-group">
                <label>Borrow Date</label>
                <input
                  type="date"
                  value={formData.borrowDate}
                  onChange={(e) => setFormData({...formData, borrowDate: e.target.value})}
                  required
                />
              </div>
              <div className="form-group">
                <label>Return Date</label>
                <input
                  type="date"
                  value={formData.returnDate}
                  onChange={(e) => setFormData({...formData, returnDate: e.target.value})}
                  required
                />
              </div>
              <div className="form-actions">
                <button type="button" onClick={resetForm} className="cancel-btn">
                  Cancel
                </button>
                <button type="submit" className="save-btn">
                  {editingBorrow ? 'Update' : 'Create'} Borrow
                </button>
              </div>
            </form>
          </div>
        </div>
      )}
    </div>
  );
};

export default Borrows;