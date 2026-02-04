import React, { useState, useEffect } from 'react';
import { fineService, memberService } from '../services/api';
import '../styles/Fines.css';

const Fines = ({ userRole }) => {
  const [fines, setFines] = useState([]);
  const [members, setMembers] = useState([]);
  const [showModal, setShowModal] = useState(false);
  const [editingFine, setEditingFine] = useState(null);
  const [loading, setLoading] = useState(true);
  const [formData, setFormData] = useState({
    amount: '',
    reason: '',
    paid: false,
    member: { memberId: '' }
  });

  useEffect(() => {
    fetchFines();
    fetchMembers();
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
      const fineData = {
        ...formData,
        amount: parseFloat(formData.amount),
        member: { memberId: parseInt(formData.member.memberId) }
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
      reason: fine.reason,
      paid: fine.paid,
      member: { memberId: fine.member?.memberId || '' }
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
      reason: '',
      paid: false,
      member: { memberId: '' }
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
              <th>Member</th>
              <th>Amount</th>
              <th>Reason</th>
              <th>Status</th>
              <th>Actions</th>
            </tr>
          </thead>
          <tbody>
            {fines.map((fine) => (
              <tr key={fine.fineId}>
                <td>{fine.fineId}</td>
                <td>{fine.member?.name || 'Unknown Member'}</td>
                <td>${fine.amount}</td>
                <td>{fine.reason}</td>
                <td>
                  <span className={`status ${fine.paid ? 'paid' : 'unpaid'}`}>
                    {fine.paid ? 'Paid' : 'Unpaid'}
                  </span>
                </td>
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
                <label>Member</label>
                <select
                  value={formData.member.memberId}
                  onChange={(e) => setFormData({
                    ...formData, 
                    member: { memberId: e.target.value }
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
                <label>Amount ($)</label>
                <input
                  type="number"
                  step="0.01"
                  value={formData.amount}
                  onChange={(e) => setFormData({...formData, amount: e.target.value})}
                  required
                />
              </div>
              <div className="form-group">
                <label>Reason</label>
                <textarea
                  value={formData.reason}
                  onChange={(e) => setFormData({...formData, reason: e.target.value})}
                  required
                  rows="3"
                />
              </div>
              <div className="form-group checkbox-group">
                <label>
                  <input
                    type="checkbox"
                    checked={formData.paid}
                    onChange={(e) => setFormData({...formData, paid: e.target.checked})}
                  />
                  Paid
                </label>
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