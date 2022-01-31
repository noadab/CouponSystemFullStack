import React, { Fragment, useCallback, useRef, useState } from "react";

import { useSelector } from "react-redux";
import Select from "react-select";

import classes from './CreateCoupon.module.css';
import Card from '../../UI/card/Card'

function CreateCoupon(props) {

  const token = useSelector((state) => state.auth.token);

  const titleRef = useRef("");
  const descriptionRef = useRef("");
  const releaseDateRef= useRef("");
  const [releaseDateState, setRelealseDate] = useState("");
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

  let required = true;
  const todayDate = new Date().toISOString().substring(0, 10);

  const submitHandler = useCallback(async (event) => {
    event.preventDefault();
    // could add validation here...

    const coupon = {
      title: titleRef.current.value,
      description: descriptionRef.current.value,
      category: categoryRef.current.state.selectValue[0].label,
      startDate: releaseDateState,
      endDate: expiredDateRef.current.value,
      amount: amountRef.current.value,
      price: priceRef.current.value,
      image: imageRef.current.value,
    };

    console.log(coupon);

    // POST request using fetch inside useEffect React hook
    const requestOptions = {
      method: "POST",
      headers: { "Content-Type": "application/json", token: token },
      body: JSON.stringify(coupon),
    };
    try {
      const response = await fetch("/company/add", requestOptions);
      const text = await response.text();
      if (response.statusText==="Method Not Allowed"){
        window.alert(text);
    }
    else if (!response.ok) {
      window.alert(text);
      //dispatch(authActions.logout());
      throw new Error("Something went wrong!");
    }

      console.log("Response Okay!");

      console.log(text);
    } catch (error) {
      console.log(error.message);
    }
    //setIsLoading(false);
  }, []);


  return (
    <section>
    <form onSubmit={submitHandler} className={classes.control} >
      <div >
        <label htmlFor="title">Title</label>
        <input
          type="text"
          id="title"
          ref={titleRef}
          required={required}
        />
      </div>
      <div >
        <label htmlFor="description">Coupon description</label>
        <textarea
          id="description"
          rows="3"
          ref={descriptionRef}
          required={required} />
      </div>
      <div >
        <label htmlFor="releaseDate">Release Date</label>
        <input
          id="releaseDate"
          type="date"
          min={todayDate}
          ref= {releaseDateRef}
          onChange={e => setRelealseDate(e.target.value)}
          required={{ required }} />
      </div>
      <div >
        <label htmlFor="expiredDate">Expired Date</label>
        <input
          id="expiredDate"
          type="date"
          min={releaseDateState}
          ref={expiredDateRef}
          required={required} />
      </div>
      <div >
        <label htmlFor="select">Category</label>
        <Select
          id="select"
          options={categories}
          ref={categoryRef}
          required={required}
        />
      </div>
      <div>
        <label htmlFor="price">price</label>
        <input
          id="price"
          type="number"
          min="1"
          step="0.01"
          ref={priceRef}
          required={required} />
      </div>
      <div>
        <label htmlFor="amount">Amount of available coupons</label>
        <input
          type="number"
          min="1"
          step="1"
          id="amount"
          ref={amountRef}
          required={required} />
      </div>
      <div >
        <div className={classes['control-image']} onChange={e => setImageLoad(e.target.value)} required={required}>
          <label htmlFor="imageLoad">Image</label>
          <div><input type="radio" value="URL" name="imageLoad" />URL</div>
          <div><input type="radio" value="File" name="imageLoad" />File</div>
        </div>
        {imageLoadState === "URL" && <input type="url" id="image" ref={imageRef} required={required} />}
        {imageLoadState === "File" && <input type="file" id="image" ref={imageRef} required={required} />}
      </div>
      <button>Add Coupon</button>
    </form>
    </section>
  );
}

// {ReactDOM.createPortal(<Popup show={showPopup} popuptext="wrong movie details!"/>
// , document.getElementById("popup-place-holder"))}

export default CreateCoupon;
