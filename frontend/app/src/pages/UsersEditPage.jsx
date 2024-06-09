import React, {useState, useEffect, useContext} from 'react';
import { TextField, Button, Typography, Grid, Box } from '@mui/material';
import useToken from '../hooks/useToken';
import AdminNavigationBar from '../components/AdminNavigationBar';
import OwnerAppBar from '../components/OwnerAppBar';
import MyContext from "../contexts/MyContext";

const UsersEditPage = () => {
  let data = useContext(MyContext);
  const [token] = useToken();
  const [user, setUser] = useState({
    userId: "",
    username: "",
    password: "",
    repeatPassword: "",
    name: "",
    surname: "",
    address: "",
    email: "",
    phoneNumber: "",
  });


  useEffect(() => {
    if (data && data.user) {
      const { userId, username, name, surname, address, email, phoneNumber } = data.user;
      setUser((prevUser) => ({
        ...prevUser,
        userId,
        username,
        name,
        surname,
        address,
        email,
        phoneNumber,
      }));
    }
  }, [data]);

  const handleInputChange = (event) => {
    const { name, value } = event.target;
    setUser((prevUser) => ({ ...prevUser, [name]: value }));
  };

  const handleSubmit = (event) => {
    event.preventDefault();

    if (user.password !== user.repeatPassword) {
      console.log('Passwords do not match');
      return;
    }
    console.log(JSON.stringify(user));
    fetch(`http://localhost:8080/users/edit`, {
      method: 'PUT',
      headers: {
        'Content-Type': 'application/json',
        Authorization: 'Bearer ' + token,
      },
      body: JSON.stringify(user),
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


  return (
      <Box marginTop={2} marginX={20}>
        {data.role === "admin" ? <AdminNavigationBar /> : <OwnerAppBar />}
        <Typography variant="h4" gutterBottom>
          Edit User
        </Typography>
        <Grid container spacing={2}>
          <Grid item xs={12}>
            <TextField
                name="userId"
                label="User ID"
                value={user.userId}
                onChange={handleInputChange}
                required
                fullWidth
                disabled
            />
          </Grid>
          <Grid item xs={12}>
            <TextField
                label="Username"
                name="username"
                value={user.username}
                onChange={handleInputChange}
                fullWidth
                required
            />
          </Grid>
          <Grid item xs={12}>
            <TextField
                label="Password"
                type="password"
                name="password"
                value={user.password}
                onChange={handleInputChange}
                fullWidth
            />
          </Grid>
          <Grid item xs={12}>
            <TextField
                label="Repeat Password"
                type="password"
                name="repeatPassword"
                value={user.repeatPassword}
                onChange={handleInputChange}
                fullWidth
            />
          </Grid>
          <Grid item xs={12}>
            <TextField
                label="Name"
                name="name"
                value={user.name}
                onChange={handleInputChange}
                fullWidth
                required
            />
          </Grid>
          <Grid item xs={12}>
            <TextField
                label="Surname"
                name="surname"
                value={user.surname}
                onChange={handleInputChange}
                fullWidth
                required
            />
          </Grid>
          <Grid item xs={12}>
            <TextField
                label="Address"
                name="address"
                value={user.address}
                onChange={handleInputChange}
                fullWidth
                required
            />
          </Grid>
          <Grid item xs={12}>
            <TextField
                label="Email"
                type="email"
                name="email"
                value={user.email}
                onChange={handleInputChange}
                fullWidth
                required
            />
          </Grid>
          <Grid item xs={12}>
            <TextField
                label="Phone Number"
                name="phoneNumber"
                value={user.phoneNumber}
                onChange={handleInputChange}
                fullWidth
                required
            />
          </Grid>
          <Grid item xs={12}>
            <Button
                type="submit"
                variant="contained"
                color="primary"
                onClick={handleSubmit}
            >
              Update
            </Button>
          </Grid>
        </Grid>
      </Box>
  );
};

export default UsersEditPage;
