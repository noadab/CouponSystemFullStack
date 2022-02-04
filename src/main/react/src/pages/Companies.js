import React, { Fragment } from "react";
import ClientFetch from "../components/Client/ClientFetch";

const Companies = () => {

    return (
        <Fragment>


            <ClientFetch requestClient="company" />
        </Fragment>
    )
}
export default Companies