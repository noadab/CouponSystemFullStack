import React from 'react';
import { useSelector } from "react-redux";

import classes from '../components/Coupon/Coupon.module.css';

const CardCoupon = (props)=>{
    const userType = useSelector((state) => state.auth.userType);

      
    return (
      <div className={classes['card-coupon']}>
        <h2>{props.card.title}</h2>
        <h4>{props.card.description}</h4>
        <p>
          <img
            src={props.card.image}
            width="60%" height="70%" />
        </p>
        <h3>{props.card.price}₪</h3>
        <h4 >Coupon available: </h4>
        <h4 >{props.card.startDate} - {props.card.endDate}</h4>
        <h4>Category: {props.card.category}</h4>
        <p>
          {userType === "company" &&
            <h4>Amount of coupon last: {props.card.amount}</h4>}
        </p>
      </div>
    )
}

export default CardCoupon;