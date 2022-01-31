import React from "react";
import CreateCoupon from "../components/Coupon/CreateCoupon";
import Card from "../UI/card/Card";
import './Main.module.css';

function AddCouponForm () {
    return (
        <React.Fragment>
        <Card>
            <CreateCoupon/>
        </Card>
    </React.Fragment>
)
}
export default AddCouponForm;