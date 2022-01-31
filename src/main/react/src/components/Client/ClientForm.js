import React, { useCallback, useRef } from "react";

import classes from './Client.module.css'
import fetchWrapper from "../../helper/fetchWrapper"
import { useSelector } from "react-redux";

const ClientForm = (props) => {

    const userType = useSelector(state => state.auth.userType);
    const token = useSelector(state => state.auth.token);
    
    const requestClient = props.requestClient;
    const emailRef = useRef("");
    const passwordRef = useRef("");
    const nameRef = useRef("");
    const firstNameRef = useRef("");
    const lastNameRef = useRef("");

    const submitHandler = useCallback(async (event) => {
        event.preventDefault();
        let clientToSend;
        if (requestClient === "company") {
            clientToSend = {
                name: nameRef.current.value,
                password: passwordRef.current.value,
                email: emailRef.current.value
            }
        }
        if (requestClient === "customer") {
            clientToSend = {
                firstName: firstNameRef.current.value ,
                lastName: lastNameRef.current.value,
                password: passwordRef.current.value,
                email: emailRef.current.value,

            }

        }
        let method;
        let path;
        if (!props.defaultData) {
            method = "POST";
            path = userType+"/add/" + requestClient
        }
        else {
            clientToSend = { id: props.defaultData.id, ...clientToSend }
            method = "PUT";
            path = userType+"/update/" + requestClient

        }
        try {
            const data = await fetchWrapper.fetch(method, path, clientToSend,token, () => {
                console.log("error");
            })
            clientToSend = { clientType: props.defaultData.clientType, ...clientToSend }
            console.log(clientToSend)
            props.onSave(clientToSend);
            console.log("client sent to server: " + clientToSend);
            console.log("Got Response: " + JSON.stringify(data));
           
        } catch (error) {
            console.log(error.message);
        }
    })
    
    const onCancel = () => {
        if (window.confirm("are you sure you want to cancel?")) {
            props.onCancel();
        }
    }

    return (
        <form onSubmit={submitHandler} className={classes['client-form']}>
            {requestClient === "company" && <div >
                <label htmlFor="name">Name</label>
                <input
                    type="name"
                    id="name"
                    ref={nameRef}
                    required={true}
                    defaultValue={!props.defaultData? "":props.defaultData.name}
                />
            </div>}
            {requestClient === "customer" && <div >
                <label htmlFor="firstName">First name</label>
                <input
                    type="firstName"
                    id="firstName"
                    ref={firstNameRef}
                    required={true}
                    defaultValue={!props.defaultData? "":props.defaultData.firstName}
                />
            </div>}
            {requestClient === "customer" && <div >
                <label htmlFor="lastName">Last Name</label>
                <input
                    type="lastName"
                    id="lastName"
                    ref={lastNameRef}
                    required={true}
                    defaultValue={!props.defaultData? "":props.defaultData.lastName}

                />
            </div>}

            <div >
                <label htmlFor="email">Email</label>
                <input
                    type="email"
                    id="email"
                    ref={emailRef}   
                    defaultValue={!props.defaultData? "":props.defaultData.email}
                />
            </div>
            <div >
                <label htmlFor="password">Password</label>
                <input
                    type="password"
                    id="password"
                    ref={passwordRef}
                    required={true}
                    defaultValue={!props.defaultData? "":props.defaultData.password}
                />
            </div>
            <div>
                <button onClick={onCancel} type="reset" >Cancel</button>
                <button type="submit">Save</button>
            </div>

        </form>
    )
}

export default ClientForm