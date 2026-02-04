import React, { useState, useEffect } from 'react';
import { bookService, memberService, borrowService, fineService } from '../services/api';
import '../styles/Dashboard.css';

const Dashboard = ({ userRole }) => {
  const [stats, setStats] = useState({
    totalBooks: 0,
    totalMembers: 0,
    totalBorrows: 0,
    totalFines: 0
  });
  const [recentBorrows, setRecentBorrows] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    fetchDashboardData();
  }, []);

  const fetchDashboardData = async () => {
    try {
      const [booksRes, membersRes, borrowsRes, finesRes] = await Promise.all([
        bookService.getAll(),
        memberService.getAll(),
        borrowService.getAll(),
        fineService.getAll()
      ]);

      setStats({
        totalBooks: booksRes.data.length,
        totalMembers: membersRes.data.length,
        totalBorrows: borrowsRes.data.length,
        totalFines: finesRes.data.length
      });

      setRecentBorrows(borrowsRes.data.slice(0, 5));
    } catch (error) {
      console.error('Error fetching dashboard data:', error);
    } finally {
      setLoading(false);
    }
  };

  if (loading) {
    return <div className="loading">Loading dashboard...</div>;
  }

  return (
    <div className="dashboard">
      <div className="dashboard-header">
        <h1>Dashboard</h1>
        <p>Welcome to the Children Library Management System</p>
      </div>

      <div className="stats-grid">
        <div className="stat-card books">
          <div className="stat-icon">📚</div>
          <div className="stat-info">
            <h3>{stats.totalBooks}</h3>
            <p>Total Books</p>
          </div>
        </div>
        <div className="stat-card members">
          <div className="stat-icon">👥</div>
          <div className="stat-info">
            <h3>{stats.totalMembers}</h3>
            <p>Total Members</p>
          </div>
        </div>
        <div className="stat-card borrows">
          <div className="stat-icon">📖</div>
          <div className="stat-info">
            <h3>{stats.totalBorrows}</h3>
            <p>Active Borrows</p>
          </div>
        </div>
        <div className="stat-card fines">
          <div className="stat-icon">💰</div>
          <div className="stat-info">
            <h3>{stats.totalFines}</h3>
            <p>Pending Fines</p>
          </div>
        </div>
      </div>

      <div className="recent-activity">
        <h2>Recent Borrows</h2>
        <div className="activity-list">
          {recentBorrows.length > 0 ? (
            recentBorrows.map((borrow) => (
              <div key={borrow.borrowId} className="activity-item">
                <div className="activity-info">
                  <h4>{borrow.book?.title || 'Unknown Book'}</h4>
                  <p>Borrowed by: {borrow.child?.name || 'Unknown Member'}</p>
                  <span className="activity-date">
                    {new Date(borrow.borrowDate).toLocaleDateString()}
                  </span>
                </div>
              </div>
            ))
          ) : (
            <p>No recent borrows</p>
          )}
        </div>
      </div>

      <div className="quick-actions">
        <h2>Quick Actions</h2>
        <div className="action-buttons">
          {userRole === 'librarian' && (
            <>
              <button className="action-btn primary">Add New Book</button>
              <button className="action-btn secondary">Add New Member</button>
            </>
          )}
          <button className="action-btn tertiary">View All Books</button>
          <button className="action-btn tertiary">View All Members</button>
        </div>
      </div>
    </div>
  );
};

export default Dashboard;