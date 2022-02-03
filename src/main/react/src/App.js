import { Fragment, useEffect } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { Route, Switch } from "react-router-dom";

import Header from './components/Header';
import CompanyDashboard from './pages/CompanyDashboard';
import CustomerDashboard from './pages/CustomerDashboard'
import Welcome from './pages/Welcome'
import LoginPage from './pages/LoginPage';
import AddCouponForm from './pages/AddCouponForm';
import Companies from './pages/Companies';
import Customers from './pages/Customers';
import { authActions } from "./store/auth"


function App() {
 
  const dispatch = useDispatch();

  useEffect(() => {
    const storedUser_LoggedIn_Information = localStorage.getItem('isLoggedIn');
    const storedUser_Token_Information = localStorage.getItem('token');
    const storedUser_UserType_Information = localStorage.getItem('userType');
    if (storedUser_LoggedIn_Information === "true") {
      dispatch(authActions.login(storedUser_Token_Information));
      dispatch(authActions.userTypeHandler(storedUser_UserType_Information));
    }
    else {
      dispatch(authActions.logout());
    }
  }, [dispatch]);
   
  const isAuth = useSelector(state => state.auth.isAuthenticated);
  const userType = useSelector(state => state.auth.userType);

  return (
    <Fragment>
      <Header />
      
      <Switch>
        
        <Route path="/" exact>
         {!isAuth && <LoginPage />}
         {isAuth && <Welcome/>}
        </Route>

        <Route path="/login" exact>
          <LoginPage />
        </Route>

        <Route path="/HomePage" exact>
          <Welcome/>
        </Route>

        {userType==="admin" && <Route path="/companies" exact>
          <Companies />
        </Route>}

        {userType==="admin" && <Route path="/customers" exact>
          <Customers />
        </Route>}

        {userType==="company" && <Route path="/addCoupon" exact>
          <AddCouponForm />
        </Route>}

        <Route path="/dashboard" exact>
          {!isAuth && <Welcome/>}
          {userType==="company" && <CompanyDashboard/>}
          {userType==="customer" && <CustomerDashboard/>}
          {userType==="admin" && <Welcome/>}
        </Route>
        
        <Route path="/">
          <Welcome/> 
        </Route>
      </Switch>
       
    </Fragment>
  );
}

export default App;