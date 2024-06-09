import { Grid, TextField, } from '@mui/material';

export default function TextFieldGrid({ label, value, onChange, type=null, xs=null }) {
  return (
    <Grid item xs={12}>
      <TextField
      label={label}
      value={value}
      onChange={onChange}
      type={type}
      xs={xs}/>
    </Grid>
  )
}