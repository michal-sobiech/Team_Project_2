import { Button } from '@mui/material';

const DeleteButton = ({ onClick }) => {
  return (
    <Button
      variant="contained"
      color="secondary"
      sx={{
        color: 'red',
        '&:hover': {
          color: 'white',
          backgroundColor: 'red',
        },
      }}
      onClick={onClick}>
        Delete
    </Button>
  );
};

export default DeleteButton;