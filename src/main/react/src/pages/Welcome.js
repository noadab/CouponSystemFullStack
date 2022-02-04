import React, { useCallback, useEffect, useState } from 'react';
import CouponList from '../components/Coupon/CouponList';
import fetchWrapper from '../helper/fetchWrapper'

const Welcome = () => {

  const [coupons, setCoupons] = useState([]);
  const [isLoading, setIsLoading] = useState(false);
  const [error, setError] = useState(null);

  let content;

  const fetchCouponsHandler = useCallback(async () => {
    setIsLoading(true);
    setError(null);
    try {
      const data = await fetchWrapper.get("/api/all", () => {
        console.log(error)
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
    } catch (error) {
      console.log(error.message);
      setError(error.message);
    }

  }, []);

  if (error) {
    content = <section>{error}</section>;
  }
  useEffect(() => {
    fetchCouponsHandler();
  }, []);


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
