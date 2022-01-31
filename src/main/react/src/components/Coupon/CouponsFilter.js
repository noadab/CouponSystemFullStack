import React, {useReducer, useRef} from 'react';
import Select from "react-select";

import classes from './CouponsFilter.module.css'

const categories = [
  { value: "No filter", label:"No filter"},
  { value: "food", label: "Food" },
  { value: "Electricity", label: "Electricity" },
  { value: "restaurant", label: "Restaurant" },
  { value: "vacation", label: "Vacation" },
];

const CouponsFilter = (props) =>
{
  const categoryRef = useRef("");
  const maxPriceRef= useRef("");

  const filterCategorySubmitHandler =()=>{
    if (categoryRef.current.state.selectValue[0].value!=''){
      maxPriceRef.current.value='';
      props.onFilter({type:"ByCategory", value:categoryRef.current.state.selectValue[0].label});
    }

  }
  const filterMaxPriceSubmitHandler =()=>{
    if (maxPriceRef.current.value!=''){
      categoryRef.current.state.selectValue[0].label='No filter'
      props.onFilter({type:"ByMaxPrice", value:maxPriceRef.current.value});
    }
  }
  return (
      
        <div className={classes["coupons-filter"]}>
        <p className={classes["coupons-filter-box"]}>
          <label className="coupons-filter__label" htmlFor='coupons-filter__select'>Filter by category</label>
          <Select className="coupons-filter__select"  
                  id="select"
                  options={categories}
                  ref={categoryRef}
                  >
          </Select>
          <button type="submit" onClick= {filterCategorySubmitHandler} >filter</button>
          </p>

          <p className={classes["coupons-filter-box"]} >
          <label className="coupons-filter__label" htmlFor="maxPrice">Filter by max price</label>
          <input className="coupons-filter__input" id="maxPrice"
                 type="number"
                 step="1"
                 min="1"
                 ref={maxPriceRef}>
          </input>
          <button type="submit" onClick= {filterMaxPriceSubmitHandler} >filter</button>
          </p>
        </div>
      
  );

}

export default CouponsFilter

