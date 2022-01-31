import React from "react";
import classes from "../components/Client/Client.module.css"

const CardClient = (props) => {
  console.log(props)
  const clientType = props.client.clientType;
  
  return (
    <div className={classes['client-card']}>
      {clientType === "customer" &&
        <label>First Name:</label>}
      {clientType === "customer" &&
        <h3> {props.client.firstName}</h3>}

      {clientType === "customer" &&
        <label>Last Name:</label>}
      {clientType === "customer" &&
        <h3>{props.client.lastName}</h3>}

      {clientType === "company" &&
        <label>Company Name:</label>}
      {clientType === "company" &&
        <h3>{props.client.name}</h3>}

      <label>Email:</label>
      <h3>{props.client.email}</h3>

      <label>Password:</label>
      <h3>{props.client.password}</h3>
    </div>
  )
}

export default CardClient; 