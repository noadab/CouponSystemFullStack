
// const urlPrefix = "https://noa-project-f9caa-default-rtdb.firebaseio.com/";

export const FOOD_LIST = "food_list";
export const INSPIRATION_LIST = "inspiration_says";

const fetchWrapper = {
  get: async (name, onfail)=> getHandler(name, onfail),
  fetch: async (method, name, dataToSend, onfail)=> putHandler(method, name, dataToSend, onfail),
  delete: async (method, name, token, onFail)=>deleteHandler(method, name, token, onFail)
}


async function getHandler(name, onFail)
{
  try {
    const response = await fetch(name);
    console.log(response)

    if (response.statusText === "Not Found") {
     throw new Error(await response.text());
    }
    else if (!response.ok) {
      throw new Error(await response.text());
    }
    const data = await response.json();
    console.log("fetchWrapper got data, len=" + Object.keys(data).length);
    return data;
  } catch (error) {
    onFail();
  }
};

async function putHandler(method, name, dataToSend, token, onFail=()=>{})
{
  try {
    const requestOptions = {
      method: method,
      headers: { "Content-Type": "application/json",  token: token},
      body: JSON.stringify(dataToSend)
    };
    const response = await fetch(name, requestOptions);

    if (!response.ok) {
      alert(await response.text())
      throw new Error(await response.text());
    }
    const data = await response.json();
    console.log("fetchWrapper."+method+", "+name+" got data, len=" + Object.keys(data).length);
    return data;
  } catch (error) {
    onFail();
  }
};

async function deleteHandler(method, name, token, onFail=()=>{})
{
  try {
    const requestOptions = {
      method: method,
      headers: { "Content-Type": "application/json",  token: token}
    };
    const response = await fetch(name, requestOptions);
    if (response.statusText === "Bad request") {
      alert(await response.text())
      throw new Error(await response.text());
     }
    else if (!response.ok) {
      alert(await response.text())
      throw new Error(await response.text());
    }
  } catch (error) {
    onFail();
  }
};

export default fetchWrapper;