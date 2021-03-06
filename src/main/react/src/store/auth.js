import { createSlice } from '@reduxjs/toolkit';

const initialAuthState = {
  isAuthenticated: false,
  userType: null,
  token: 0,
};

const authSlice = createSlice({
  name: 'authentication',
  initialState: initialAuthState,
  reducers: {
    login(state, action) {
      state.isAuthenticated = true;
      state.token = action.payload;
      localStorage.setItem('token', action.payload);
      localStorage.setItem('isLoggedIn', true);
      console.log("Save the new token: " + state.token);
    },
    logout(state) {
      let token = localStorage.getItem('token');
      const response = fetch('api/logout/' + token, { method: 'DELETE' });
      console.log(response);
      state.isAuthenticated = false;
      state.token = 0;
      state.userType = null;
      localStorage.removeItem('token');
      localStorage.removeItem('isLoggedIn');
      localStorage.removeItem('userType');

    },
    userTypeHandler(state, action) {
      state.userType = action.payload;
      localStorage.setItem('userType', action.payload);
      console.log(state.userType);
    }
  },
});

export const authActions = authSlice.actions;

export default authSlice.reducer;