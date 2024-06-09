import React, {useContext, useState} from "react";
import { TextField, Button, Typography, Grid, Box } from "@mui/material";
import { useNavigate } from "react-router-dom";
import useToken from "../hooks/useToken";
import AdminNavigationBar from "../components/AdminNavigationBar";
import OwnerAppBar from "../components/OwnerAppBar";
import MyContext from "../contexts/MyContext";

const AdminsCreatePage = () => {
    let [token] = useToken();
    let data = useContext(MyContext);
    const navigate = useNavigate();
    const [formData, setFormData] = useState({
        login: "",
        password: "",
        repeatPassword: "",
        name: "",
        surname: "",
        address: "",
        email: "",
        phoneNumber: "",
    });
    const [error, setError] = useState('');

    const isAnyFieldEmpty = (obj) => {
        for (let key in obj) {
            if (obj[key] === '') {
                return true;
            }
        }
        return false;
    };

    const handleInputChange = (event) => {
        const { name, value } = event.target;
        setFormData((prevFormData) => ({ ...prevFormData, [name]: value }));
    };

    const handleSubmit = (event) => {
        event.preventDefault();
        if (isAnyFieldEmpty(formData)) {
            setError('All fields must be filled');
            return;
        }

        if (formData.password !== formData.repeatPassword) {
            console.log("Passwords do not match");
            return;
        }
        console.log(formData);
        fetch("http://localhost:8080/admins/create", {
            method: "POST",
            headers: {
                'Content-Type': 'application/json',
                'Authorization': 'Bearer ' + token,
            },
            body: JSON.stringify(formData),
        })
            .then((response) => {
                if (!response.ok) {
                    throw new Error("Error creating admin");
                }
                return response.json();
            })
            .then((data) => {
                console.log(data);
                navigate("/admins");
            })
            .catch((error) => {
                console.log(error);
            });
    };

    return (
        <Box marginTop={2}  marginX={20}>
            {data.role === "admin" ? <AdminNavigationBar /> : <OwnerAppBar />}
            <Typography variant="h4" gutterBottom>
                Create Admin
            </Typography>
            <Grid container spacing={2}>
                <Grid item xs={12}>
                    <TextField
                        label="Login"
                        name="login"
                        value={formData.login}
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
                        value={formData.password}
                        onChange={handleInputChange}
                        fullWidth
                        required
                    />
                </Grid>
                <Grid item xs={12}>
                    <TextField
                        label="Repeat Password"
                        type="password"
                        name="repeatPassword"
                        value={formData.repeatPassword}
                        onChange={handleInputChange}
                        fullWidth
                        required
                    />
                </Grid>
                <Grid item xs={12}>
                    <TextField
                        label="Name"
                        name="name"
                        value={formData.name}
                        onChange={handleInputChange}
                        fullWidth
                        required
                    />
                </Grid>
                <Grid item xs={12}>
                    <TextField
                        label="Surname"
                        name="surname"
                        value={formData.surname}
                        onChange={handleInputChange}
                        fullWidth
                        required
                    />
                </Grid>
                <Grid item xs={12}>
                    <TextField
                        label="Address"
                        name="address"
                        value={formData.address}
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
                        value={formData.email}
                        onChange={handleInputChange}
                        fullWidth
                        required
                    />
                </Grid>
                <Grid item xs={12}>
                    <TextField
                        label="Phone Number"
                        name="phoneNumber"
                        value={formData.phoneNumber}
                        onChange={handleInputChange}
                        fullWidth
                        required
                    />
                </Grid>
                {error && <p>{error}</p>}
                <Grid item xs={12}>
                    <Button
                        type="submit"
                        variant="contained"
                        color="primary"
                        onClick={handleSubmit}
                    >
                        Create
                    </Button>
                </Grid>
            </Grid>
        </Box>
    );
};

export default AdminsCreatePage;
