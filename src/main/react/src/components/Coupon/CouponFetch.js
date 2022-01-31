import React, { useState, useCallback, useEffect } from 'react';
import { useDispatch, useSelector } from "react-redux";
import { authActions } from "../../store/auth";

import CouponList from './CouponList'
import CouponsFilter from './CouponsFilter'
import classes from './CouponList.module.css'

const CouponFetch = () => {

  const userType = useSelector(state => state.auth.userType);
  const token = useSelector(state => state.auth.token);
  const dispatch = useDispatch();

  const [fetchCoupons, setFetchCoupons] = useState(false);
  const [coupons, setCoupons] = useState([]);
  const [isLoading, setIsLoading] = useState(false);
  const [error, setError] = useState(null);

  const fetchCouponsHandler = useCallback(async (event) => {
    setIsLoading(true);
    setError(null);
    let response;
    try {
      if ((event.value) != ("No filter")) {
        response = await fetch("/" + userType + "/all" + event.type + "/" + event.value + "/" + token);
      }
      else {
        response = await fetch("/" + userType + "/all/" + token);
        console.log(response)
      }

      if (response.statusText === "Not Found") {
        setError(await response.text());
        console.log(error)
      }
      else if (!response.ok) {
        window.alert("Session timeout!");
        setError(await response.text());
        dispatch(authActions.logout());
        throw new Error("Something went wrong!");
      }
      else {
        console.log("Response Okay!");
        const data = await response.json();
        const transformedCoupons = data.map((couponData) => {
          return {
            id: couponData.id,
            title: couponData.title,
            category: couponData.category,
            description: couponData.description,
            startDate: couponData.startDate,
            endDate: couponData.endDate,
            amount: couponData.amount,
            price: couponData.price,
            image: couponData.image
          };
        });
        setCoupons(transformedCoupons);
      }
    } catch (error) {
      setError(error.message);
    }
    setFetchCoupons(true);
    setIsLoading(false);
  }, []);

  useEffect(() => {
    fetchCouponsHandler({value:'',type:''});
  }, []);

  let content = <section>Found no coupons.</section>;

  if (coupons.length > 0) {
    content = <CouponList coupons={coupons} />;
  }

  if (error) {
    content = <section>{error}</section>;
  }

  if (isLoading) {
    content = <section>Loading...</section>;
  }

  if (!fetchCoupons) {
    content = null;
  }

  const closeCouponsListHandler = () => {
    setFetchCoupons(false);
  }
  const openCouponsListHandler = ()=> {
    fetchCouponsHandler({value:'',type:''});
    setFetchCoupons(true);
  }

  return (
    <div >
      {fetchCoupons && <button onClick={closeCouponsListHandler} >Close coupons</button>}
      {fetchCoupons && <CouponsFilter onFilter={fetchCouponsHandler} />}
      {!fetchCoupons && <button onClick={openCouponsListHandler}>Fetch coupons</button>}
      {content != null && <div>{content}</div>}
    </div>
  )
}

export default CouponFetch;