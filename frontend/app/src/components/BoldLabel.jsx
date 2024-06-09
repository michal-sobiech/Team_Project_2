import { Typography } from "@mui/material";


export default function BoldLabel({ children, sx }) {
  return (
    <Typography
    variant='h6'
    component='div'
    sx={sx}>
      {children}
    </Typography>
  );
}