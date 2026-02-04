import React, { useState, useEffect } from 'react';
import { fineService, borrowService } from '../services/api';
import '../styles/Fines.css';

const Fines = ({ userRole }) => {
  const [fines, setFines] = useState([]);
  const [borrows, setBorrows] = useState([]);
  const [showModal, setShowModal] = useState(false);
  const [editingFine, setEditingFine] = useState(null);
  const [loading, setLoading] = useState(true);
  const [formData, setFormData] = useState({
    amount: '',
    borrow: { borrowId: '' }
  });

  useEffect(() => {
    fetchFines();
    fetchBorrows();
  }, []);

  const fetchFines = async () => {
    try {
      const response = await fineService.getAll();
      setFines(response.data);
    } catch (error) {
      console.error('Error fetching fines:', error);
    } finally {
      setLoading(false);
    }
  };

  const fetchBorrows = async () => {
    try {
      const response = await borrowService.getAll();
      setBorrows(response.data);
    } catch (error) {
      console.error('Error fetching borrows:', error);
    }
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const fineData = {
        amount: parseFloat(formData.amount),
        borrow: { borrowId: parseInt(formData.borrow.borrowId) }
      };

      if (editingFine) {
        await fineService.update(editingFine.fineId, fineData);
      } else {
        await fineService.create(fineData);
      }
      
      fetchFines();
      resetForm();
    } catch (error) {
      console.error('Error saving fine:', error);
      alert('Error saving fine record. Please try again.');
    }
  };

  const handleEdit = (fine) => {
    setEditingFine(fine);
    setFormData({
      amount: fine.amount,
      borrow: { borrowId: fine.borrow?.borrowId || '' }
    });
    setShowModal(true);
  };

  const handleDelete = async (id) => {
    if (window.confirm('Are you sure you want to delete this fine record?')) {
      try {
        await fineService.delete(id);
        fetchFines();
      } catch (error) {
        console.error('Error deleting fine:', error);
        alert('Error deleting fine record. Please try again.');
      }
    }
  };

  const resetForm = () => {
    setFormData({
      amount: '',
      borrow: { borrowId: '' }
    });
    setEditingFine(null);
    setShowModal(false);
  };

  if (loading) {
    return <div className="loading">Loading fines...</div>;
  }

  return (
    <div className="fines-container">
      <div className="fines-header">
        <h1>Fines Management</h1>
        {userRole === 'librarian' && (
          <button 
            onClick={() => setShowModal(true)}
            className="add-btn"
          >
            Add Fine
          </button>
        )}
      </div>

      <div className="fines-table">
        <table>
          <thead>
            <tr>
              <th>ID</th>
              <th>Amount</th>
              <th>Borrow ID</th>
              <th>Actions</th>
            </tr>
          </thead>
          <tbody>
            {fines.map((fine) => (
              <tr key={fine.fineId}>
                <td>{fine.fineId}</td>
                <td>${fine.amount}</td>
                <td>{fine.borrow?.borrowId || 'N/A'}</td>
                <td>
                  <div className="action-buttons">
                    {userRole === 'librarian' && (
                      <>
                        <button 
                          onClick={() => handleEdit(fine)}
                          className="edit-btn"
                        >
                          Edit
                        </button>
                        <button 
                          onClick={() => handleDelete(fine.fineId)}
                          className="delete-btn"
                        >
                          Delete
                        </button>
                      </>
                    )}
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
              <h2>{editingFine ? 'Edit Fine' : 'Add New Fine'}</h2>
              <button onClick={resetForm} className="close-btn">×</button>
            </div>
            <form onSubmit={handleSubmit} className="fine-form">
              <div className="form-group">
                <label>Borrow Record</label>
                <select
                  value={formData.borrow.borrowId}
                  onChange={(e) => setFormData({
                    ...formData, 
                    borrow: { borrowId: e.target.value }
                  })}
                  required
                >
                  <option value="">Select Borrow Record</option>
                  {borrows.map((borrow) => (
                    <option key={borrow.borrowId} value={borrow.borrowId}>
                      Borrow #{borrow.borrowId} - {borrow.book?.title || 'Unknown Book'}
                    </option>
                  ))}
                </select>
              </div>
              <div className="form-group">
                <label>Amount ($)</label>
                <input
                  type="number"
                  step="0.01"
                  value={formData.amount}
                  onChange={(e) => setFormData({...formData, amount: e.target.value})}
                  required
                />
              </div>
              <div className="form-actions">
                <button type="button" onClick={resetForm} className="cancel-btn">
                  Cancel
                </button>
                <button type="submit" className="save-btn">
                  {editingFine ? 'Update' : 'Add'} Fine
                </button>
              </div>
            </form>
          </div>
        </div>
      )}
    </div>
  );
};

export default Fines;