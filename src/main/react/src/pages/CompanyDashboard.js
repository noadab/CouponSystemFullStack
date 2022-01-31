import React, { useState, useEffect, useCallback } from "react";
import { useSelector } from "react-redux";

import classes from './Main.module.css';
import CouponFetch from '../components/Coupon/CouponFetch'
import Client from "../components/Client/Client";
import fetchWrapper from '../helper/fetchWrapper'

function CompanyDashboard() {
  const token = useSelector(state => state.auth.token);
  const [company, setCompany] = useState([]);
  const [isLoading, setIsLoading] = useState(false);
  const [error, setError] = useState(null);

  const fetchClientHandler = useCallback(async ()=>{
    setIsLoading(true);
    setError(null);
    const data = await fetchWrapper.get("/company/companyDetails/"+token, () => {
      setError(data.text());
    });
    const transformed= {
      clientType:"company",
      id: data.id,
      name: data.name,
      email: data.email,
      password: data.password
    }
    setCompany(transformed)
  })
  useEffect(() => {
    fetchClientHandler();
  }, []);

  return (
    <React.Fragment>
      <div >
        <Client client={company}/>
      </div>
      
      <CouponFetch/>
      
    </React.Fragment>
  );
}

export default CompanyDashboard;
