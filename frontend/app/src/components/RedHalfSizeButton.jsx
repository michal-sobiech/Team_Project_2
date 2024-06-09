import { Button, } from '@mui/material';


export default function RedHalfSizeButton(label, onClick) {
  return (
    <Button
    sx={{color: "red", height: "50px"}}
    size="large"
    variant="text"
    onClick={onClick}>
      {label}
    </Button>
  )
}
