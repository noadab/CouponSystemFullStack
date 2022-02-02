import { useSelector, useDispatch } from 'react-redux';

import classes from './Header.module.css';
import { authActions } from '../store/auth';
import { NavLink } from 'react-router-dom';

const Header = () => {
  const dispatch = useDispatch();
  const isAuth = useSelector((state) => state.auth.isAuthenticated);
  const userType = useSelector(state => state.auth.userType);

  const logoutHandler = () => {
    dispatch(authActions.logout());
  };

  return (
    <header className={classes.header}>
      <h1>Coupon System</h1>
      <nav>
        <ul>

          <li>
            <NavLink activeClassName={classes.active} to="/HomePage">
              Home
            </NavLink>
          </li>

          {isAuth && userType!="admin" &&
          <li>
            <NavLink activeClassName={classes.active} to="/dashboard">
              Dashboard
            </NavLink>
          </li>}

          {userType === "company" &&
            <li>
              <NavLink activeClassName={classes.active} to="/addCoupon">
                Add Coupon
              </NavLink>
            </li>}

            {userType === "admin" &&
            <li>
              <NavLink activeClassName={classes.active} to="/companies">
                Companies
              </NavLink>
            </li>}

            {userType === "admin" &&
            <li>
              <NavLink activeClassName={classes.active} to="/customers">
                Customers
              </NavLink>
            </li>}

          {!isAuth && 
          <li>
            <NavLink activeClassName={classes.active} className={classes.loginButton} to="/">
            Login
          </NavLink>
          </li>}

          {isAuth && (
            <li>
              <button onClick={logoutHandler}>Logout</button>
            </li>
          )}
        </ul>
      </nav>
    </header>
  );
};

export default Header;
