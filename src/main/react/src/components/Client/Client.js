import React, { useCallback, useEffect, useState } from "react";
import { useSelector } from 'react-redux';
import fetchWrapper from '../../helper/fetchWrapper'

import classes from "./Client.module.css"
import CardClient from "../../UI/CardClient"
import ClientForm from "./ClientForm";

const Client = (props) => {
  
  const userType = useSelector(state => state.auth.userType);
  const token = useSelector((state) => state.auth.token);

  const [client,setClient]= useState();
  const [error, setError] = useState(null);
  const [availClient, setAvailClient] = useState(true);
  const [edit, setEdit] = useState(false);

  let content = <div>cant show user details</div>

  if (props.client != []) {
    if (userType === "admin") {
      content = <CardClient client={props} />
    }
    else {
      content = <CardClient client={props.client} />
    }
  }


  const deleteClient = useCallback(async (event) => {
    event.preventDefault();
    setError(null);
    let method = "DELETE";
    let path = "admin/delete/" + client.clientType + "/" + client.id;
    try {
        const data = await fetchWrapper.delete(method, path, token, () => {
        console.log("error");
      })
      console.log("client sent to server: " + path);
      setAvailClient(false);
     
    } catch (error) {
      setError(error.message);
      console.log(error.message);
    }
  }, []);

  const deleteChangeHandler = (event) => {
    if (window.confirm("are you sure you want to delete this client?")) {
      deleteClient(event);
    }
  }
  const updateChangeHandler = () => {
    console.log()
    setEdit(true);
  }

  const onCancel = () => {
    setEdit(false);

  }
  const onSave = (props) => {
    setClient(props);
    setEdit(false);
  }

  if (edit) {
    console.log(edit)
    content = <ClientForm defaultData={client} requestClient={client.clientType} onCancel={onCancel} onSave={onSave} />
  }
  
  if (error){
    <section>
      {error}
    </section>
  }
  
  return (
    <React.Fragment>
      {availClient && <div className={classes.client}>
        {content}
        {userType === "admin" && !edit && <button onClick={updateChangeHandler}>update</button>}
        {userType === "admin" && !edit && <button onClick={deleteChangeHandler}>delete</button>}
      </div>}
    </React.Fragment>
  )
}

export default Client; 