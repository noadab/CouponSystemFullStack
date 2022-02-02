import React, { useCallback, useEffect, useState } from "react";
import { useSelector } from 'react-redux';
import fetchWrapper from '../../helper/fetchWrapper'

import classes from "./Client.module.css"
import CardClient from "../../UI/CardClient"
import ClientForm from "./ClientForm";

const Client = (props) => {
  
  const userType = useSelector(state => state.auth.userType);
  const token = useSelector((state) => state.auth.token);

  const [currClient,setCurrClient]= useState(props);
  const [error, setError] = useState(null);
  const [isAvailClient, setAvailClient] = useState(true);
  const [edit, setEdit] = useState(false);

  let content = <div>cant show user details</div>
    
  content = <CardClient client={currClient} />
     

  const deleteClient = useCallback(async (event) => {
    event.preventDefault();
    setError(null);
    let method = "DELETE";
    let path = "admin/delete/" + currClient.clientType + "/" + currClient.id;
    try {
        const data = await fetchWrapper.delete(method, path, token, () => {})
        setAvailClient(false);
     
    }catch (error) {
      console.log(error.message);
      setError(error.message);
  }
  }, []);

  const deleteChangeHandler = (event) => {
    if (window.confirm("are you sure you want to delete this client?")) {
      deleteClient(event);
    }
  }

  const updateClientChangeHandler = () => {
    setEdit(true);
  }

  const onCancel = () => {
    setEdit(false);

  }
  const onSave = (props) => {
    setEdit(false);
    setCurrClient(props);
  }

  if (edit) {
    content = <ClientForm defaultData={currClient} requestClient={currClient.clientType} onCancel={onCancel} onSave={onSave} />
  
  }
  
  if (error){
    <section>
      {error}
    </section>
  }
  
  return (
    <React.Fragment>
      {isAvailClient && 
      <div className={classes.client}>
        {content}
        {userType === "admin" && !edit && <button onClick={updateClientChangeHandler}>update</button>}
        {userType === "admin" && !edit && <button onClick={deleteChangeHandler}>delete</button>}
      </div>}
    </React.Fragment>
  )
}

export default Client; 