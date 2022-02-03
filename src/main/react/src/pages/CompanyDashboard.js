import React, { useState, useEffect, useCallback } from "react";
import { useSelector } from "react-redux";

import CouponFetch from '../components/Coupon/CouponFetch'
import ClientFetch from "../components/Client/ClientFetch";

function CompanyDashboard() {
 
  return (
    <React.Fragment>
      <div >
        <ClientFetch requestClient="company"/>
      </div>
      
      <CouponFetch />
      
    </React.Fragment>
  );
}

export default CompanyDashboard;
