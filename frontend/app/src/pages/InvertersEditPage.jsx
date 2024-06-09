import React, { useContext, useState } from 'react';
import { TextField, Select, MenuItem, Button, FormControl, InputLabel, Grid, Box, Typography } from '@mui/material';
import MyContext from "../contexts/MyContext";
import AdminNavigationBar from "../components/AdminNavigationBar";
import OwnerAppBar from "../components/OwnerAppBar";
import useToken from "../hooks/useToken";

const InvertersEditPage = () => {
  let data = useContext(MyContext);
  let [token] = useToken();
  const [inverter, setInverter] = useState({
    inverterId: '',
    code: '',
    inverterModel: null,
    userId: '',
    ipAddress: '',
    password: '',
    login: '', // Update with the appropriate initial value for the nickname
  });
  const [error, setError] = useState('');

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setInverter((prevState) => ({ ...prevState, [name]: value }));
  };

  const handleInverterModelChange = (e) => {
    const selectedModelId = e.target.value;
    const selectedModel = data.inverterModels.find(
      (model) => model.inverterModelId === selectedModelId
    );

    setInverter((prevState) => ({
      ...prevState,
      inverterModel: selectedModel, // Set the selected inverterModel object
    }));
  };

  const handleSubmit = (e) => {
    e.preventDefault();

    if (isAnyFieldEmpty(inverter)) {
      console.log('All fields must be filled');
      setError('All fields must be filled');
      return;
    }

    fetch('http://localhost:8080/inverters/edit', {
      method: 'PUT',
      headers: {
        'Content-Type': 'application/json',
        'Authorization': 'Bearer ' + token,
      },
      body: JSON.stringify({
        inverterId: inverter.inverterId,
        code: inverter.code,
        userId: inverter.userId,
        ipAddress: inverter.ipAddress,
        login: inverter.login,
        password: inverter.password,
        model: {
          inverterModelId: inverter.inverterModel.inverterModelId
        }
      }),
    })
      .then((response) => {
        // console.log(response);
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
      if (key !== 'password' && obj[key] === '') {
        return true;
      }
    }
    return false;
  };

  React.useEffect(() => {
      if (data) {
          setInverter((prevState) => ({
              ...prevState,
              inverterId: data.inverter.inverterId,
              code: data.inverter.code,
              inverterModel: data.inverter.inverterModel, // Set the initial model object
              userId: data.inverter.userId,
              ipAddress: data.inverter.ipAddress,
              login: data.inverter.login,
              password: '', // Initialize password to an empty string
          }));
      }
  }, [data]);


    return (
        <Box marginTop={2} marginX={20}>
            {data.role === 'admin' ? <AdminNavigationBar /> : <OwnerAppBar />}
            <Typography variant="h4" gutterBottom>
                Inverter Edit
            </Typography>
            <Grid container spacing={2}>
                <Grid item xs={12}>
                    <TextField
                        name="inverterId"
                        label="Inverter ID"
                        value={inverter.inverterId}
                        onChange={handleInputChange}
                        required
                        fullWidth
                        disabled
                    />
                </Grid>
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
                        fullWidth
                    />
                </Grid>
                <Grid item xs={12}>
                    <FormControl fullWidth>
                        <InputLabel>Inverter Model</InputLabel>
                        <Select
                            value={inverter.inverterModel?.inverterModelId || ''}
                            onChange={handleInverterModelChange}
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

export default InvertersEditPage;
