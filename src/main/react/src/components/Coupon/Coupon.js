import React, { useCallback, useState } from 'react';
import { useSelector } from "react-redux";
import { useHistory } from "react-router-dom";

import fetchWrapper from '../../helper/fetchWrapper'
import classes from './Coupon.module.css';
import Card from '../../UI/card/Card';
import CardCoupon from '../../UI/CardCoupon'
import UpdateCoupon from './UpdateCoupon'



const Coupon = (props) => {

  const token = useSelector((state) => state.auth.token);
  const userType = useSelector((state) => state.auth.userType);
  const isAuth = useSelector((state) => state.auth.isAuth);
  
  const history = useHistory();

  const id = props.id;
  const viewCall= props.view;
  
  const [coupon, setCoupon]= useState(props);
  const [availCoupon, setAvailCoupon] = useState(true);
  const [edit, setEdit] = useState(false);

  
  let viewCoupon = <CardCoupon card={coupon} />

  const deleteCoupon = useCallback(async (event) => {
    
    event.preventDefault();
    let method = "DELETE";
    let path ="/company/delete/" + id;
    try {
      const data = await fetchWrapper.delete(method, path, token, () => {
      
    })
      setAvailCoupon(false);
    }
    
     catch (error) {
      console.log(error.message);
    }
  }, []);

  const deleteCouponChangeHandler = (event) => {
    if (window.confirm("are you sure you want to delete this coupon?")) {
      deleteCoupon(event);
    }
  }


  const purchaseCouponChangeHandler = () => {
    if (userType==="customer"){
      if (window.confirm("Do you wan't to buy this coupon?")) {
        onPurchase();
      }
    }
    else if(userType===null){
      if (window.confirm("You can't purchase this coupon beacuse you are logout, do you wan't to login?")) {
        history.push("/login");
      }
    }
    else if ({isAuth}){
      alert( userType+" user can't purchase coupons")
    }
    else {
      
    }
  }

  const updateCouponChangeHandler = () => {
    setEdit(true)
    
  }

  const onCancel = () => {
    setEdit(false)
  }

  const onSave = (props)=>{
    setEdit(false)
    setCoupon(props)
  }

  const onPurchase=  useCallback(async () => {
        let couponToSend = coupon;
        let method = "PUT"
        let path = userType + "/purchasesCoupon"
        try {
            const data = await fetchWrapper.fetch(method, path, couponToSend, token, () => {
                console.log("error");
            })
            alert("Enjoy your new coupon! ");
        } catch (error) {
            console.log(error.message);
        }
  }, [])
  
  
  if (edit) {
    viewCoupon = <UpdateCoupon coupon={coupon} onCancel={onCancel} onSave={onSave} ></UpdateCoupon>
  }

  return (
    <div >
      {availCoupon && 
      <Card className={classes.coupon}>
        {viewCoupon}
        {viewCall!="home" && userType === "company" && !edit &&
          <button onClick={deleteCouponChangeHandler}>Delete coupon</button>}
        {viewCall!="home" && userType === "company" && !edit &&
          <button onClick={updateCouponChangeHandler}>Update coupon</button>}
        
        {viewCall==="home" && <button onClick={purchaseCouponChangeHandler} >BUY COUPUN</button>}
      </Card>}
    </div>
  );
};

export default Coupon;