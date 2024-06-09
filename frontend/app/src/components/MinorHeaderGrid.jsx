import { Grid } from '@mui/material';

export default function MinorHeaderGrid({ text, xs=12 }) {
  return (
    <Grid item xs={xs}>
      <h2>{text}</h2>
    </Grid>
  )
}

