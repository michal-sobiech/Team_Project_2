import useToken from "../hooks/useToken";
import { useEffect, useState } from "react";
import MyContext from '../contexts/MyContext';
import { useLocation } from "react-router-dom";
import * as restutils from "../utils/restutils";

export default function ProtectedRoute({ backendUrl, successChild, failureChild }) {
  const [token] = useToken();
  const [data, setData] = useState({});
  const [child, setChild] = useState(<div>Loading</div>);
  const location = useLocation();

  useEffect(() => {
    async function getData() {
      console.log('TOKEN: ' + token);

      // Get the search string from the location object
      const searchString = location.search;
      console.log(searchString);
      const response = await restutils.get(backendUrl + searchString, token);
      console.log(backendUrl + searchString);
      if (response.ok) {
        let responseJson;
        if (response.status === 204) {
          responseJson = {};
        } else {
          responseJson = await response.json();
          console.log(responseJson);
        }
        setData(responseJson);
        setChild(successChild);
      } else {
        setChild(failureChild);
      }
    }
    getData();
  }, [location, backendUrl, token, successChild, failureChild]);

  return (
    <MyContext.Provider value={data}>
      <div>{child}</div>
    </MyContext.Provider>
  );
}
