import React from 'react';

import Coupon from './Coupon';
import classes from './CouponList.module.css'


const CouponList = (props) => {
  return (
    <div className={classes['coupon-list']}>
      {props.coupons.map((coupon) => (
        <Coupon 
        view={props.view}
        id= {coupon.id}
        title= {coupon.title}
        category= {coupon.category}
        description= {coupon.description}
        startDate= {coupon.startDate}
        amount= {coupon.amount}
        endDate= {coupon.endDate}
        price= {coupon.price}
        image= {coupon.image}
        />
      ))}
    </div>
  );
};

export default CouponList;
