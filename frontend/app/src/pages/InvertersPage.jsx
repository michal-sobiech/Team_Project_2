import React, { useContext } from "react";
import AdminNavigationBar from "../components/AdminNavigationBar";
import OwnerAppBar from "../components/OwnerAppBar";
import "bootstrap/dist/css/bootstrap.min.css";
import MyContext from "../contexts/MyContext";
import { useNavigate } from 'react-router-dom';
import { Button, Table, TableBody, TableCell, TableHead, TableRow } from '@mui/material';
import { Box } from '@mui/system';
import EditButton from "../components/EditButton"
import DeleteButton from "../components/DeleteButton"
import useToken from "../hooks/useToken";

const InvertersPage = () => {
  let data = useContext(MyContext);
  let [token] = useToken();
  const navigate = useNavigate();

  const handleCreateClick = () => {
    navigate('/inverters/create')
  };

  const handleEditClick = (inverterId) => {
    // Handle edit button click for the specific inverter
    console.log(`Edit clicked for inverter with ID: ${inverterId}`);
    console.log(`/inverters/edit?inverterId=${inverterId}`);
    navigate(`/inverters/edit?inverterId=${inverterId}`);
  };

  const handleDeleteClick = async (inverterId) => {
    // Show confirmation dialog
    const confirmDelete = window.confirm(`Are you sure you want to delete inverter with id ${inverterId}?`);
    if (confirmDelete) {
      try {
        // Make the delete request to the API
        const response = await fetch(`http://localhost:8080/inverters/delete?inverterId=${inverterId}`, {
          method: 'DELETE',
          headers: {
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + token,
          },
        });
        if (response.ok) {
          console.log(`Inverter with ID ${inverterId} deleted successfully`);
          window.location.reload();
        } else {
          console.error(`Error deleting inverter with ID ${inverterId}`);
        }
      } catch (error) {
        console.error('Error:', error);
      }
    } else {
      console.log('Delete canceled');
    }
  };


  return (
    <Box marginTop={2} marginX={20}>
      {data.role === 'admin' ? <AdminNavigationBar /> : <OwnerAppBar />}
      <Button variant="contained" color="primary" onClick={handleCreateClick}>
        Create new inverter
      </Button>
      <Table>
        <TableHead>
          <TableRow>
            <TableCell>Id</TableCell>
            <TableCell>Code</TableCell>
            <TableCell>Model</TableCell>
            <TableCell>Producer</TableCell>
            <TableCell>User</TableCell>
            <TableCell>IP Address</TableCell>
            <TableCell>Action</TableCell>
          </TableRow>
        </TableHead>
        <TableBody>
          {data.inverters.map((inverter) => (
            <TableRow key={inverter.inverterId}>
              <TableCell>{inverter.inverterId}</TableCell>
              <TableCell>{inverter.code}</TableCell>
              <TableCell>{inverter.inverterModel.modelName}</TableCell>
              <TableCell>{inverter.inverterModel.producer}</TableCell>
              <TableCell>{inverter.userId}</TableCell>
              <TableCell>{inverter.ipAddress}</TableCell>
              <TableCell>
                <EditButton onClick={() => handleEditClick(inverter.inverterId)} />
                <DeleteButton onClick={() => handleDeleteClick(inverter.inverterId)} />
              </TableCell>
            </TableRow>
          ))}
        </TableBody>
      </Table>
    </Box>
  );
};

export default InvertersPage;