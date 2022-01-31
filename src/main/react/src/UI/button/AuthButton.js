import React from 'react';

import './AuthButton.css';

const AuthButton = (props) => {
const classes = 'button ' + props.className; //'card expense-item'
  return (
    <button
      type={props.type || 'button'}
      className={classes}
      onClick={props.onClick}
      disabled={props.disabled}
    >
      {props.children}
    </button>
  );
};

export default AuthButton;
