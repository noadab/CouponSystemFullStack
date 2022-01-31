import React from "react";

import Client from "./Client";
import classes from './ClientList.module.css'


const ClientList = (props) => {
  return (
    <div className={classes['client-list']}>
      <ul >
        {props.clients.map((c) => (
          <Client
            clientType={c.clientType}
            id={c.id}
            name={c.name}
            firstName={c.firstName}
            lastName={c.lastName}
            password={c.password}
            email={c.email}
          />
        ))}
      </ul>
    </div>
  )
}

export default ClientList;