import React, { useCallback, useEffect, useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import fetchWrapper from '../../helper/fetchWrapper'
import ClientList from "./ClientList";
import ClientForm from "./ClientForm";
import classes from './ClientList.module.css'

const ClientFetch = (props) => {
    const userType = useSelector(state => state.auth.userType);
    const token = useSelector(state => state.auth.token);

    const [clients, setClients] = useState([]);
    const [add,setAdd]= useState(false);
    const [isLoading, setIsLoading] = useState(false);
    const [error, setError] = useState(null);
    const requestClient = props.requestClient;

    const fetchClientHandler = useCallback(async () => {
        setIsLoading(true);
        setError(null);
        const data = await fetchWrapper.get("/"+userType+"/" + requestClient + "/" + token, () => {
            console.log(error)
            setError(error.message);
        });
        console.log(data);
        
        const transformed = data.map((clientData) => {
            return {
            clientType: requestClient == "company" ? "company" : "customer",
            id: clientData.id,
            name: requestClient == "company" ? clientData.name : "",
            firstName: requestClient == "company" ? "" : clientData.firstName,
            lastName: requestClient == "company" ? "" : clientData.lastName,
            email: clientData.email,
            password: clientData.password
        }
    })
        console.log(transformed)
        setClients(transformed)
        setIsLoading(false);
    })
    
    useEffect(() => {
        fetchClientHandler();
    }, []);


    let content = <section>Found no clients.</section>;
    
    if (clients.length > 0) {
        content = <ClientList clients={clients} clientType={requestClient} />;
    }

    if (error) {
        content = <section>{error}</section>;
    }

    if (isLoading) {
        content = <section>Loading...</section>;
    }


    const addClientHandler=()=>{
        setAdd(true);
    }

    const onCancel = () => {
        setAdd(false);
    }
    const onSave = (props)=>{
      setAdd(false); 
      fetchClientHandler();
    }
    return (
        <div >
            {content}
            {!add && <button onClick={addClientHandler}>Add client</button>}
            {add && <ClientForm defaultData={false} requestClient={requestClient} onCancel={onCancel} onSave={onSave}/>}
        </div>
    )

}

export default ClientFetch;