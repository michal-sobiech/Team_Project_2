import { Grid } from '@mui/material';

export default function HeaderGrid({text, xs=12}) {
  return (
    <Grid item xs={xs}>
      <h1>{text}</h1>
    </Grid>
  )
}