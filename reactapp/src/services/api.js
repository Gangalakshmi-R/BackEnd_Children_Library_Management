import axios from 'axios';

const API_BASE_URL = 'http://localhost:8080/api';

const api = axios.create({
  baseURL: API_BASE_URL,
  headers: {
    'Content-Type': 'application/json',
  },
});

api.interceptors.request.use((config) => {
  const token = localStorage.getItem('token');
  if (token) {
    config.headers.Authorization = `Bearer ${token}`;
  }
  return config;
});

export const authService = {
  login: (credentials) => api.post('/auth/signin', credentials),
  signup: (userData) => api.post('/auth/signup', userData),
};

export const bookService = {
  getAll: () => api.get('/books'),
  getById: (id) => api.get(`/books/${id}`),
  create: (book) => api.post('/books', book),
  update: (id, book) => api.put(`/books/${id}`, book),
  delete: (id) => api.delete(`/books/${id}`),
  getByCategory: (category) => api.get(`/books/category/${category}`),
  getByTitle: (title) => api.get(`/books/title/${title}`),
};

export const memberService = {
  getAll: () => api.get('/members'),
  getById: (id) => api.get(`/members/${id}`),
  create: (member) => api.post('/members', member),
  update: (id, member) => api.put(`/members/${id}`, member),
  delete: (id) => api.delete(`/members/${id}`),
  getByPhone: (phone) => api.get(`/members/phone/${phone}`),
  getByEmail: (email) => api.get(`/members/email/${email}`),
};

export const borrowService = {
  getAll: () => api.get('/borrows'),
  getById: (id) => api.get(`/borrows/${id}`),
  create: (borrow) => api.post('/borrows', borrow),
  update: (id, borrow) => api.put(`/borrows/${id}`, borrow),
  delete: (id) => api.delete(`/borrows/${id}`),
  getPages: (pageNo, pageSize) => api.get(`/borrows/page/${pageNo}/${pageSize}`),
  sortByField: (field) => api.get(`/borrows/sort/${field}`),
  filterByField: (field, value) => api.get(`/borrows/filter/${field}/${value}`),
};

export const fineService = {
  getAll: () => api.get('/fines'),
  getById: (id) => api.get(`/fines/${id}`),
  create: (fine) => api.post('/fines', fine),
  update: (id, fine) => api.put(`/fines/${id}`, fine),
  delete: (id) => api.delete(`/fines/${id}`),
  getPages: (pageNo, pageSize) => api.get(`/fines/page/${pageNo}/${pageSize}`),
  sortByField: (field) => api.get(`/fines/sort/${field}`),
  filterByField: (field, value) => api.get(`/fines/filter/${field}/${value}`),
};

export const categoryService = {
  getAll: () => api.get('/book-categories'),
  getById: (id) => api.get(`/book-categories/${id}`),
  create: (category) => api.post('/book-categories', category),
  update: (id, category) => api.put(`/book-categories/${id}`, category),
  delete: (id) => api.delete(`/book-categories/${id}`),
  getPages: (pageNo, pageSize) => api.get(`/book-categories/page/${pageNo}/${pageSize}`),
  sortByField: (field) => api.get(`/book-categories/sort/${field}`),
  filterByField: (field, value) => api.get(`/book-categories/filter/${field}/${value}`),
};

export default api;