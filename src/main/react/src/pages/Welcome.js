import React , { useCallback, useEffect, useState } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import reactRouterDom from 'react-router-dom';
import { Route } from 'react-router-dom';
import ClientForm from '../components/Client/ClientForm';
import CouponList from '../components/Coupon/CouponList';
import fetchWrapper from '../helper/fetchWrapper'
import { authActions } from "../store/auth";

const Welcome = () => {
  const isAuth = useSelector(state => state.auth.isAuth);
  const token = useSelector(state => state.auth.token);
  
  const dispatch = useDispatch();

  const [coupons, setCoupons] = useState([]);
  const [isLoading, setIsLoading] = useState(false);
  const [error, setError] = useState(null);

  const fetchCouponsHandler = useCallback(async () => {
    setIsLoading(true);
    setError(null);
    const data = await fetchWrapper.get("/api/all/", () => {
      console.log(error)
      setError(error.message);
  });
  console.log(data);
  console.log("Response Okay!");
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
  setCoupons(transformedCoupons)
  }, []);

  useEffect(() => {
    fetchCouponsHandler();
  }, []);

  let content;
  if (coupons.length > 0) {
    content = <CouponList coupons={coupons} view={"home"} />;
  }

  return (
    <React.Fragment>
      {content}
    </React.Fragment>
      
      
    
  );
};

export default Welcome;
