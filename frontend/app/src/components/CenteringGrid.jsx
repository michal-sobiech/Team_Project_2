import { Grid } from "@mui/material";


export default function CenteringGrid({ children }) {
  return (
    <Grid
    container
    spacing={0}
    direction='column'
    alignItems='center'
    justifyContent='center'>
      {children}
    </Grid>
  );
}