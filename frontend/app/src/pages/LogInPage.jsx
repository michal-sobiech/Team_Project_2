import { useNavigate } from 'react-router-dom'
import { Container } from '@mui/system';
import { Box, Grid, Toolbar } from '@mui/material';
import { useState } from 'react';
import useToken from '../hooks/useToken';
import {
  SimpleAppBar,
  CentredTextFieldGrid,
  CentredPasswordFieldGrid,
  CenteredFormLabelGrid,
  CentredButtonGrid
} from  '../utils/placementutils';
import { buttonColorStyle } from "../themes/theme"
import {backendAddress, logIn} from "../utils/pathutils"
import * as restutils from "../utils/restutils"

const LogInPage = () => {
  let navigate = useNavigate()
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  let [, setToken] = useToken();
  let [error, setError] = useState(false);

  async function handleLogInButtonClick() {
    console.log('Trying to log in');
    let response = await restutils.post(
      backendAddress + logIn,
      JSON.stringify({
        'username': email,
        'password': password
      })
    )
    if (response.ok) {
      let responseJson = await response.json();
      logSuccess(responseJson);
      handleSuccess(responseJson);
    } else {
      logFailure();
      setError(true)
    }
  }
  function pickOverviewPath(role) {
    if (role === 'admin' || role === 'owner') {
      return `/home`
    }
    return `/${role}_home/overview`
  }

  function handleSuccess(responseJson) {
    setToken(responseJson.token);
    let url = pickOverviewPath(responseJson.role.toLowerCase());
    navigate(url);
  }

  function logSuccess(responseJson) {
    console.log('Logged in');
    console.log('Role: ' + responseJson.role.toLowerCase());
  }

  function logFailure() {
    console.log('Failed to log in');
  }

  function handleEmailTextFieldChange(event) {
    setEmail(event.target.value);
  }

  function handlePasswordTextFieldChange(event) {
    setPassword(event.target.value);
  }

  return (
    <Container>
      <SimpleAppBar/>
      <Toolbar/>
      <Box>
        <Grid
        container
        spacing={2}>
          {CentredTextFieldGrid("Username", email, handleEmailTextFieldChange)}
          {CentredPasswordFieldGrid(password, handlePasswordTextFieldChange)}
          {CentredButtonGrid(buttonColorStyle, handleLogInButtonClick, "Log in")}
          {error && CenteredFormLabelGrid("Invalid username or password")}
          {CenteredFormLabelGrid("Not a member yet?")}
          {CenteredFormLabelGrid("Contact us at proman@gmail.com to sign up!")}
        </Grid>
      </Box>
    </Container>
  );
}
export default LogInPage;
