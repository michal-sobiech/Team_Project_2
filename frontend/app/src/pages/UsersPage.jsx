import React, { useContext } from "react";
import { Button, Table, TableHead, TableBody, TableRow, TableCell, Box } from "@mui/material";
import AdminNavigationBar from "../components/AdminNavigationBar";
import OwnerAppBar from "../components/OwnerAppBar";
import MyContext from "../contexts/MyContext";
import { useNavigate } from "react-router-dom";
import EditButton from "../components/EditButton"
import DeleteButton from "../components/DeleteButton"
import useToken from "../hooks/useToken";

const UsersPage = () => {
  let data = useContext(MyContext);
  let [token] = useToken();
  const navigate = useNavigate();
  const handleCreateClick = () => {
    navigate("/users/create");
  };

  const handleEditClick = (userId) => {
    navigate(`/users/edit?userId=${userId}`);
  };

  const handleDeleteClick = async (userId) => {
    // Show confirmation dialog
    const confirmDelete = window.confirm(`Are you sure you want to delete user with id ${userId}?`);
    if (confirmDelete) {
      try {
        // Make the delete request to the API
        const response = await fetch(`http://localhost:8080/users/delete?userId=${userId}`, {
          method: 'DELETE',
          headers: {
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + token,
          },
        });
        if (response.ok) {
          console.log(`User with ID ${userId} deleted successfully`);
          window.location.reload();
        } else {
          console.error(`Error deleting user with ID ${userId}`);
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
          Create new user
        </Button>
        <Table>
          <TableHead>
            <TableRow>
              <TableCell>Id</TableCell>
              <TableCell>Username</TableCell>
              <TableCell>Name</TableCell>
              <TableCell>Surname</TableCell>
              <TableCell>Address</TableCell>
              <TableCell>E-mail</TableCell>
              <TableCell>Phone</TableCell>
              <TableCell>Action</TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {data.users.map((user) => (
                <TableRow key={user.userId}>
                  <TableCell>{user.userId}</TableCell>
                  <TableCell>{user.username}</TableCell>
                  <TableCell>{user.name}</TableCell>
                  <TableCell>{user.surname}</TableCell>
                  <TableCell>{user.address}</TableCell>
                  <TableCell>{user.email}</TableCell>
                  <TableCell>{user.phoneNumber}</TableCell>
                  <TableCell>
                    <EditButton onClick={() => handleEditClick(user.userId)} />
                    <DeleteButton onClick={() => handleDeleteClick(user.userId)} />
                  </TableCell>
                </TableRow>
            ))}
          </TableBody>
        </Table>
      </Box>
  );
};

export default UsersPage;