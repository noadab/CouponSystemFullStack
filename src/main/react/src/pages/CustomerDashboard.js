import React, { useCallback, useEffect, useState } from "react";
import { useSelector } from "react-redux";
import Client from "../components/Client/Client";

import CouponFetch from '../components/Coupon/CouponFetch'
import fetchWrapper from '../helper/fetchWrapper'

function CompanyDashboard() {
    const token = useSelector(state => state.auth.token);
  //const dispatch = useDispatch();
  const [customer, setCustomer] = useState([]);
  const [isLoading, setIsLoading] = useState(false);
  const [error, setError] = useState(null);

  const fetchClientHandler = useCallback(async ()=>{
    setIsLoading(true);
    setError(null);
    const data = await fetchWrapper.get("/customer/customerDetails/"+token, () => {
      setError(data.text());
    });
    console.log(data);
    const transformed= {
      clientType:"customer",
      id: data.id,
      firstName: data.firstName,
      lastName: data.lastName,
      email: data.email,
      password: data.password
    }
    setCustomer(transformed)
  })
  useEffect(() => {
    fetchClientHandler();
  }, []);

    return(
        <React.Fragment>
            <Client client={customer}/>
            <CouponFetch/>
        </React.Fragment>
        
    )
}

export default CompanyDashboard;