import { useDispatch } from "react-redux";

import classes from "./Auth.module.css";
import { authActions } from "../store/auth";
import React, { useCallback, useReducer } from "react";

import Select from "react-select";
import AuthButton from "../UI/button/AuthButton";
import { useHistory } from "react-router-dom";

const emailReducer = (state, action) => {
  if (action.type === 'USER_INPUT') {
    return { value: action.value };
    // , isValid: action.val.includes('@') };
  }
  if (action.type === 'INPUT_BLUR') {
    return { value: state.value };
    //, isValid: state.value.includes('@') };
  }
  return { value: '', isValid: false };
};

const passwordReducer = (state, action) => {
  if (action.type === 'USER_INPUT') {
    return { value: action.value };
    //, isValid: action.val.trim().length > 6 };
  }
  if (action.type === 'INPUT_BLUR') {
    return { value: state.value };
    //, isValid: state.value.trim().length > 6 };
  }
  return { value: '' }
  //, isValid: false };
};


const userTypeReducer = (state, action) => {
  if (action.type === 'USER_INPUT') {
    return { value: action.value };
    //, isValid: action.val.trim().length > 6 };
  }
  if (action.type === 'INPUT_BLUR') {
    return { value: state.value };
    //, isValid: state.value.trim().length > 6 };
  }
  return { value: '' }
  //, isValid: false };
};


const Auth = () => {

  const dispatch = useDispatch();
  const history = useHistory();

  const options = [
    { value: "admin", label: "Admin" },
    { value: "company", label: "Company" },
    { value: "customer", label: "Customer" },
  ];

  const emailChangeHandler = (event) => {
    dispatchEmail({ type: 'USER_INPUT', value: event.target.value });
  };

  const passwordChangeHandler = (event) => {
    dispatchPassword({ type: 'USER_INPUT', value: event.target.value });
  };

  const userTypeChangeHandler = (event) => {
    dispatchUserType({ type: 'USER_INPUT', value: event.value });
  };

  const [emailState, dispatchEmail] = useReducer(emailReducer, {
    value: '',
    //isValid: null,
  });
  const [passwordState, dispatchPassword] = useReducer(passwordReducer, {
    value: '',
    //isValid: null,
  });

  const [userTypeState, dispatchUserType] = useReducer(userTypeReducer, {
    value: '',
    //isValid: null,
  });


  const submitHandler =useCallback(async (event) => {
      try {
        event.preventDefault();
        const creds = {
          email: emailState.value,
          password: passwordState.value
        };
  
        // POST request using fetch inside useEffect React hook
        const requestOptions = {
          method: "POST",
          headers: { "Content-Type": "application/json" },
          body: JSON.stringify(creds),
        };
  
        console.log("post: "+JSON.stringify(creds));
        const response = await fetch("/"+userTypeState.value+"/login", requestOptions);

        if (!response.ok) {
          window.alert(await response.text());
          dispatch(authActions.logout());
          throw new Error("Authintication fails!");
        }
        console.log("Okay!");
        const token = await response.text();
        console.log("Got token: "+token);
        dispatch(authActions.userTypeHandler(userTypeState.value));
        dispatch(authActions.login(token));
        history.push("/dashboard")

      } catch (error) {
        console.log(error.message);
      }
    } , [emailState, passwordState, userTypeState, dispatch])
  

  return (
    <main className={classes.auth}>
      <section>
        <form id="loginform" >
          <div className={classes.control}>
            <label htmlFor="email">Email</label>
            <input
              type="email"
              id="email"
              value={emailState.value}
              onChange={emailChangeHandler} />
          </div>
          <div className={classes.control}>
            <label htmlFor="password">Password</label>
            <input
              type="password"
              id="password"
              input={passwordState.value}
              onChange={passwordChangeHandler} />
          </div>
          <div className={classes.control}>
            <label htmlFor="select">Role</label>
            
            <Select
              id="select"
              options={options}
              value={options.find(obj => obj.value === userTypeState)}
              onChange={userTypeChangeHandler} 
            />
            
          </div>
          <AuthButton type="submit" form="loginform" onClick={submitHandler}>Login</AuthButton>
        </form>
      </section>
    </main>
  );
};

export default Auth;
