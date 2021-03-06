import React, { useCallback, useRef, useState } from 'react';
import Select from "react-select";
import { useSelector } from "react-redux";

import classes from './Coupon.module.css';
import fetchWrapper from '../../helper/fetchWrapper'

const UpdateCoupon = (props) => {

    const token = useSelector((state) => state.auth.token);
    const userType = useSelector((state) => state.auth.userType);
  
    const titleRef = useRef("");
    const descriptionRef = useRef("");
    const releaseDateRef = useRef("");
    const [releaseDateState, setRelealseDate] = useState(props.coupon.startDate);
    const expiredDateRef = useRef("");
    const categoryRef = useRef("");
    const amountRef = useRef("");
    const priceRef = useRef("");
    const imageRef = useRef("");
    const [imageLoadState, setImageLoad] = useState("");

    const categories = [
        { value: "food", label: "Food" },
        { value: "electricity", label: "Electricity" },
        { value: "restaurant", label: "Restaurant" },
        { value: "vacation", label: "Vacation" },
    ];

    const onSubmitHandler = useCallback(async (event) => {
        event.preventDefault();

        const couponToSend = {
            id: props.coupon.id,
            title: titleRef.current.value == "" ? props.coupon.title : titleRef.current.value,
            description: descriptionRef.current.value == "" ? props.coupon.description : titleRef.current.value,
            category: categoryRef.current.state.selectValue[0].label,
            startDate: releaseDateRef.current.value,
            endDate: expiredDateRef.current.value,
            amount: amountRef.current.value == "" ? props.coupon.amount : amountRef.current.value,
            price: priceRef.current.value == "" ? props.coupon.price : priceRef.current.value,
            image: props.coupon.image,
        };
        console.log(couponToSend);

        const method = "PUT";
        const path = "/"+userType+"/update";
        try {
            const data = await fetchWrapper.fetch(method, path, couponToSend, token, () => {
                console.log("error");
            })
            console.log(couponToSend);
            props.onSave(couponToSend)
        } catch (error) {
            console.log(error.message);
        }
    }, []);

    const onCancel = () => {
        if (window.confirm("Are you sure you want to cancel?")) {
            props.onCancel();
        }
    }
    return (
        <div className={classes.coupon}  >
            <form onSubmit={onSubmitHandler}>
                <input
                    id="title"
                    type="text"
                    ref={titleRef}
                    defaultValue={props.coupon.title} />
                
                <textarea
                    id="description"
                    rows="2"
                    ref={descriptionRef}
                    defaultValue={props.coupon.description} />
                
                <p>
                    <img
                        src={props.coupon.image}
                        width="60%" height="70%" />
                </p>
                
                <input id="price" type="number"
                    min="1"
                    step="0.01"
                    ref={priceRef}
                    defaultValue={props.coupon.price} />
                <label htmlFor='price'>???</label>
                
                <label >Coupon available date:</label>
                <input id="releaseDate"
                    type="date"
                    min={props.coupon.startDate}
                    ref={releaseDateRef}
                    onChange={e => setRelealseDate(e.target.value)}
                    defaultValue={props.coupon.startDate} />
                
                <label >Coupon expired date:</label>
                <input id="expiredDate"
                    type="date"
                    min={releaseDateState}
                    ref={expiredDateRef}
                    defaultValue={props.coupon.endDate} />
                
                <label>Category:</label><Select
                    id="select"
                    className="coupon__select"
                    options={categories}
                    ref={categoryRef}
                    defaultValue={categories.find(obj => obj.label === props.coupon.category)} />
                <label>Amount of coupon last:</label>
                <input type="number"
                    min="1"
                    step="1"
                    id="amount"
                    ref={amountRef}
                    defaultValue={props.coupon.amount} />
                <button onClick={onCancel} >Cancel</button>
                <button>Save</button>
            </form>
        </div>

    )
}

export default UpdateCoupon;
