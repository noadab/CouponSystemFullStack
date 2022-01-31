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
      state.isAuthenticated= false;
      state.token= 0;
      state.userType= null;
      localStorage.setItem('token', null);
      localStorage.setItem('isLoggedIn', false);
      localStorage.setItem('userType', null);

    },
    userTypeHandler (state, action) {
      state.userType=action.payload;
      localStorage.setItem('userType', action.payload);
      console.log(state.userType);
    }
  },
});

export const authActions = authSlice.actions;

export default authSlice.reducer;