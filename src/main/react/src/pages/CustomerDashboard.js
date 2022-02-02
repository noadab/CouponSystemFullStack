import React, { useCallback, useEffect, useState } from "react";
import { useSelector } from "react-redux";
import ClientFetch from "../components/Client/ClientFetch";

import CouponFetch from '../components/Coupon/CouponFetch'


function CompanyDashboard() {
  
    return(
        <React.Fragment>
          <ClientFetch requestClient="customer"/>
            
            <CouponFetch/>
        </React.Fragment>
        
    )
}

export default CompanyDashboard;