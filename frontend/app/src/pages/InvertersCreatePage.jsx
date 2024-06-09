import React, { useContext, useState } from 'react';
import { TextField, Select, MenuItem, Button, FormControl, InputLabel, Grid, Box, Typography } from '@mui/material';
import MyContext from "../contexts/MyContext";
import AdminNavigationBar from "../components/AdminNavigationBar";
import OwnerAppBar from "../components/OwnerAppBar";
import useToken from "../hooks/useToken";

const InvertersCreatePage = () => {
  let data = useContext(MyContext);
  let [token] = useToken();
  const [inverter, setInverter] = useState({
    code: '',
    inverterModelId: '',
    userId: '',
    ipAddress: '',
    password: '',
    login: '',
    inverterModel: null,
  });
  const [error, setError] = useState('');

  const handleInputChange = (e) => {
    const { name, value } = e.target;

    if (name === 'inverterModelId') {
      const selectedModel = data.inverterModels.find((model) => model.inverterModelId === value);
      setInverter((prevState) => ({
        ...prevState,
        inverterModelId: value,
        inverterModel: selectedModel,
      }));
    } else {
      setInverter((prevState) => ({ ...prevState, [name]: value }));
    }
  };

  const handleSubmit = (e) => {
    e.preventDefault();

    if (isAnyFieldEmpty(inverter)) {
      setError('All fields must be filled');
      return;
    }

    setError('');
    console.log(JSON.stringify({
      code: inverter.code,
      userId: inverter.userId,
      ipAddress: inverter.ipAddress,
      password: inverter.password,
      login: inverter.login,
      model: {
        inverterModelId: inverter.inverterModel.inverterModelId,
      },
    }))
    fetch('http://localhost:8080/inverters/create', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
        'Authorization': 'Bearer ' + token,
      },
      body: JSON.stringify({
        code: inverter.code,
        userId: inverter.userId,
        ipAddress: inverter.ipAddress,
        password: inverter.password,
        login: inverter.login,
        model: {
          inverterModelId: inverter.inverterModel.inverterModelId,
        },
      }),
    })
      .then((response) => {
        return response;
      })
      .then((data) => {
        console.log(data); // Handle the response from the server
      })
      .catch((error) => {
        console.error('Error:', error);
      });
  };

  const isAnyFieldEmpty = (obj) => {
    for (let key in obj) {
      if (obj[key] === '') {
        return true;
      }
    }
    return false;
  };

  return (
    <Box marginTop={2} marginX={20}>
      {data.role === 'admin' ? <AdminNavigationBar /> : <OwnerAppBar />}
      <Typography variant="h4" gutterBottom>
        Inverter Creation
      </Typography>
      <Grid container spacing={2}>
        <Grid item xs={12}>
          <TextField
            name="code"
            label="Code"
            value={inverter.code}
            onChange={handleInputChange}
            required
            fullWidth
          />
        </Grid>
        <Grid item xs={12}>
          <TextField
            name="login"
            label="Login"
            value={inverter.login}
            onChange={handleInputChange}
            required
            fullWidth
          />
        </Grid>
        <Grid item xs={12}>
          <TextField
            name="password"
            label="Password"
            type="password"
            value={inverter.password}
            onChange={handleInputChange}
            required
            fullWidth
          />
        </Grid>
        {data.inverterModels && data.inverterModels.length > 0 && (
          <Grid item xs={12}>
            <FormControl fullWidth>
              <InputLabel>Inverter Model</InputLabel>
              <Select
                value={inverter.inverterModelId}
                onChange={handleInputChange}
                name="inverterModelId"
                required
              >
                {data.inverterModels.map((model) => (
                  <MenuItem
                    key={model.inverterModelId}
                    value={model.inverterModelId}
                  >
                    {model.modelName}
                  </MenuItem>
                ))}
              </Select>
            </FormControl>
          </Grid>
        )}
        <Grid item xs={12}>
          <TextField
            name="userId"
            label="User ID"
            value={inverter.userId}
            onChange={handleInputChange}
            required
            fullWidth
          />
        </Grid>
        <Grid item xs={12}>
          <TextField
            name="ipAddress"
            label="IP Address"
            value={inverter.ipAddress}
            onChange={handleInputChange}
            required
            fullWidth
          />
        </Grid>
        <Grid item xs={12}>
          {error && <p>{error}</p>}
          <Button type="submit" variant="contained" color="primary" onClick={handleSubmit}>
            Submit
          </Button>
        </Grid>
      </Grid>
    </Box>
  );
};

export default InvertersCreatePage;
