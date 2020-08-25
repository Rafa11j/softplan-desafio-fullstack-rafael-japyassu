import axios from 'axios';

const token = localStorage.getItem('@ADVSoft:token');

const headers = token !== null ? { Authorization: `Bearer ${token}` } : {};

const api = axios.create({
  baseURL: process.env.REACT_APP_AXIOS_URL,
  headers,
});

export default api;
