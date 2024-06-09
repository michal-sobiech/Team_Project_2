import { Button } from '@mui/material';

const EditButton = ({ onClick }) => {
  return (
    <Button
    variant="contained"
    color="primary"
    sx={{
      color: 'blue',
      '&:hover': {
        color: 'white',
        backgroundColor: 'blue',
      },
    }}
    onClick={onClick}>
      Edit
    </Button>
  );
};

export default EditButton;