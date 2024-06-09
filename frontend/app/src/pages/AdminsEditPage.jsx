import React, {useState, useEffect, useContext} from 'react';
import { TextField, Button, Typography, Grid, Box } from '@mui/material';
import useToken from '../hooks/useToken';
import AdminNavigationBar from '../components/AdminNavigationBar';
import OwnerAppBar from '../components/OwnerAppBar';
import MyContext from "../contexts/MyContext";

const AdminsEditPage = () => {
    let data = useContext(MyContext);
    const [token] = useToken();
    const [admin, setAdmin] = useState({
        adminId: "",
        login: "",
        password: "",
        repeatPassword: "",
        name: "",
        surname: "",
        address: "",
        email: "",
        phoneNumber: "",
    });


    useEffect(() => {
        if (data && data.admin) {
            const { adminId, login, name, surname, address, email, phoneNumber } = data.admin;
            setAdmin((prevAdmin) => ({
                ...prevAdmin,
                adminId,
                login,
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
        setAdmin((prevAdmin) => ({ ...prevAdmin, [name]: value }));
    };

    const handleSubmit = (event) => {
        event.preventDefault();

        if (admin.password !== admin.repeatPassword) {
            console.log('Passwords do not match');
            return;
        }
        console.log(JSON.stringify(admin));
        fetch(`http://localhost:8080/admins/edit`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
                Authorization: 'Bearer ' + token,
            },
            body: JSON.stringify(admin),
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
                Edit Admin
            </Typography>
            <Grid container spacing={2}>
                <Grid item xs={12}>
                    <TextField
                        name="adminId"
                        label="Admin ID"
                        value={admin.adminId}
                        onChange={handleInputChange}
                        required
                        fullWidth
                        disabled
                    />
                </Grid>
                <Grid item xs={12}>
                    <TextField
                        label="Login"
                        name="login"
                        value={admin.login}
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
                        value={admin.password}
                        onChange={handleInputChange}
                        fullWidth
                    />
                </Grid>
                <Grid item xs={12}>
                    <TextField
                        label="Repeat Password"
                        type="password"
                        name="repeatPassword"
                        value={admin.repeatPassword}
                        onChange={handleInputChange}
                        fullWidth
                    />
                </Grid>
                <Grid item xs={12}>
                    <TextField
                        label="Name"
                        name="name"
                        value={admin.name}
                        onChange={handleInputChange}
                        fullWidth
                        required
                    />
                </Grid>
                <Grid item xs={12}>
                    <TextField
                        label="Surname"
                        name="surname"
                        value={admin.surname}
                        onChange={handleInputChange}
                        fullWidth
                        required
                    />
                </Grid>
                <Grid item xs={12}>
                    <TextField
                        label="Address"
                        name="address"
                        value={admin.address}
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
                        value={admin.email}
                        onChange={handleInputChange}
                        fullWidth
                        required
                    />
                </Grid>
                <Grid item xs={12}>
                    <TextField
                        label="Phone Number"
                        name="phoneNumber"
                        value={admin.phoneNumber}
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

export default AdminsEditPage;
