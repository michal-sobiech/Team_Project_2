import React, { useContext } from "react";
import { Button, Table, TableHead, TableBody, TableRow, TableCell, Box } from "@mui/material";
import AdminNavigationBar from "../components/AdminNavigationBar";
import OwnerAppBar from "../components/OwnerAppBar";
import MyContext from "../contexts/MyContext";
import { useNavigate } from "react-router-dom";
import EditButton from "../components/EditButton"
import DeleteButton from "../components/DeleteButton"
import useToken from "../hooks/useToken";

const AdminsPage = () => {
    let data = useContext(MyContext);
    let [token] = useToken();
    const navigate = useNavigate();
    const handleCreateClick = () => {
        navigate("/admins/create");
    };

    const handleEditClick = (adminId) => {
        navigate(`/admins/edit?adminId=${adminId}`);
    };

    const handleDeleteClick = async (adminId) => {
        // Show confirmation dialog
        const confirmDelete = window.confirm(`Are you sure you want to delete admin with id ${adminId}?`);
        if (confirmDelete) {
            try {
                // Make the delete request to the API
                const response = await fetch(`http://localhost:8080/admins/delete?adminId=${adminId}`, {
                    method: 'DELETE',
                    headers: {
                        'Content-Type': 'application/json',
                        'Authorization': 'Bearer ' + token,
                    },
                });
                if (response.ok) {
                    console.log(`Admin with ID ${adminId} deleted successfully`);
                    window.location.reload();
                } else {
                    console.error(`Error deleting admin with ID ${adminId}`);
                }
            } catch (error) {
                console.error('Error:', error);
            }
        } else {
            console.log('Delete canceled');
        }
    };



    return (
        <Box marginTop={2}  marginX={20}>
            {data.role === "admin" ? <AdminNavigationBar /> : <OwnerAppBar />}
            <Button variant="contained" color="primary" onClick={handleCreateClick}>
                Create new admin
            </Button>
            <Table>
                <TableHead>
                    <TableRow>
                        <TableCell>Id</TableCell>
                        <TableCell>Login</TableCell>
                        <TableCell>Name</TableCell>
                        <TableCell>Surname</TableCell>
                        <TableCell>Address</TableCell>
                        <TableCell>E-mail</TableCell>
                        <TableCell>Phone</TableCell>
                        <TableCell>Action</TableCell>
                    </TableRow>
                </TableHead>
                <TableBody>
                    {data.admins.map((admin) => (
                        <TableRow key={admin.adminId}>
                            <TableCell>{admin.adminId}</TableCell>
                            <TableCell>{admin.login}</TableCell>
                            <TableCell>{admin.name}</TableCell>
                            <TableCell>{admin.surname}</TableCell>
                            <TableCell>{admin.address}</TableCell>
                            <TableCell>{admin.email}</TableCell>
                            <TableCell>{admin.phoneNumber}</TableCell>
                            <TableCell>
                                <EditButton onClick={() => handleEditClick(admin.adminId)} />
                                <DeleteButton onClick={() => handleDeleteClick(admin.adminId)} />
                            </TableCell>
                        </TableRow>
                    ))}
                </TableBody>
            </Table>
        </Box>
    );
};

export default AdminsPage;