
const fetchWrapper = {
  get: async (name)=> getHandler(name),
  fetch: async (method, name, dataToSend, onfail)=> putHandler(method, name, dataToSend, onfail),
  delete: async (method, name, token, onFail)=>deleteHandler(method, name, token, onFail)
}

const logout=()=>{
  localStorage.removeItem('token');
  localStorage.removeItem('isLoggedIn');
  localStorage.removeItem('userType');
}


const OK="Ok";
// Custome exception
const NOT_FOUND="Not found"
// Other exceptions
const METHOD_NOT_ALLOWED="Method not allowed"
// Session end
const BAD_REQUEST="Bad request"

async function getHandler(name)
{
    const response = await fetch(name);
    console.log(response)
    if (response.statusText === BAD_REQUEST) {
      alert(await response.text());
      logout();
      throw new Error(response.statusText);
    } 
    else if (!response.ok) {
      throw new Error(await response.text());
    }
 
    const data = await response.json();
    console.log("fetchWrapper got data, len=" + Object.keys(data).length);
    return data;
  
};

async function putHandler(method, name, dataToSend, token, onFail=()=>{})
{
  
  const requestOptions = {
      method: method,
      headers: { "Content-Type": "application/json",  token: token},
      body: JSON.stringify(dataToSend)
    };
    const response = await fetch(name, requestOptions);
    if (response.statusText === BAD_REQUEST) {
      logout();
      alert(await response.text());
      throw new Error(await response.text());
    } 

    else if (!response.ok) {
      alert(await response.text())
      throw new Error(response.json());
    }

    alert(await response.text());
    return ;
 
};

async function deleteHandler(method, name, token, onFail=()=>{})
{
  
    const requestOptions = {
      method: method,
      headers: { "Content-Type": "application/json",  token: token}
    };
    const response = await fetch(name, requestOptions);
    if (response.statusText === BAD_REQUEST) {
      logout();
      alert(await response.text());
      throw new Error(await response.text());
    }
    
    else if (!response.ok) {
      alert(await response.text())
      throw new Error(await response.text());
    }
    alert(await response.text());
    return;
  
  }


export default fetchWrapper;