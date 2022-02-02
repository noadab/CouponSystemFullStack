import React, { useState, useCallback, useEffect } from 'react';
import { useDispatch, useSelector } from "react-redux";

import fetchWrapper from '../../helper/fetchWrapper'
import CouponList from './CouponList'
import CouponsFilter from './CouponsFilter'

const CouponFetch = () => {

  const userType = useSelector(state => state.auth.userType);
  const token = useSelector(state => state.auth.token);

  const [coupons, setCoupons] = useState([]);
  const [isCouponsFetch, setIsCouponsFetch] = useState(false);

  const [isLoading, setIsLoading] = useState(false);
  const [error, setError] = useState(null);

  const fetchCouponsHandler = useCallback(async (event) => {
    setIsLoading(true);
    setError(null);
    let data;
    try {
      if ((event.value) != ("No filter")) {
        data = await fetchWrapper.get("/" + userType + "/all" + event.type + "/" + event.value + "/" + token)
      }
      else {
        data = await fetchWrapper.get("/" + userType + "/all/" + token)
      }
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
    } catch (error) {
      console.log(error.message);
      setError(error.message);

    }
    setIsCouponsFetch(true);
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

  if (!isCouponsFetch) {
    content = null;
  }

  const closeCouponsListHandler = () => {
    setIsCouponsFetch(false);
  }
  const openCouponsListHandler = ()=> {
    fetchCouponsHandler({value:'',type:''});
    setIsCouponsFetch(true);
  }

  return (
    <div >
      {isCouponsFetch && <button onClick={closeCouponsListHandler} >Close coupons</button>}
      {isCouponsFetch && <CouponsFilter onFilter={fetchCouponsHandler} />}
      {!isCouponsFetch && <button onClick={openCouponsListHandler}>Fetch coupons</button>}
      {content != null && <div>{content}</div>}
    </div>
  )
}

export default CouponFetch;