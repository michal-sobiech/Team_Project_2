
export async function get(address, token) {
  return await makeRequest(address, token, 'GET');
}

export async function put(address, token) {
  return await makeRequest(address, token, 'PUT');
}

export async function post(address, withBody) {
  return await fetch(address, {
    method: 'POST',
    headers: { 
      'Content-Type': 'application/json',
    },
    body: withBody
  });
}

export async function postJson(address, withBody) {
  const response = await post(address, withBody);
  return await response.json();
}

export async function deleteRequest(address, token) {
  return await makeRequest(address, token, 'DELETE');
}

export async function getJson(address, token) {
  const response = await get(address, token);
  return await response.json();
}


export async function deleteJson(address, token) {
  const response = await deleteRequest(address, token);
  return await response.json();
}

export async function putWithBodyJson(address, token, withBody) {
  const response = await putWithBody(address, token, withBody)
  return await response.json();
}


export async function makeRequest(address, token, method) {
  return await fetch(address, {
    method: method,
    headers: { 
      'Content-Type': 'application/json',
      'Authorization': 'Bearer ' + token
    },
  });
}

export async function makeRequestWithBody(address, token, withBody, method) {
  return await fetch(address, {
    method: method,
    headers: { 
      'Content-Type': 'application/json',
      'Authorization': 'Bearer ' + token
    },
    body: withBody
  });
}

export async function putWithBody(address, token, withBody) {
  return await makeRequestWithBody(address, token, withBody, 'PUT');
}

export async function getRequestParsedResponse(address, token) {
  let response = await get(address, token);
  let responseJson = null;
  if (response.ok) {
    if (response.status === 204) {
      ;
    } else {
      responseJson = await response.json();
    }
  }
  console.log('response: ', responseJson);
  return responseJson;
}
