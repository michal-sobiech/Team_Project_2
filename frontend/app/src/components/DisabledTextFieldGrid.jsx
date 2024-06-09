import { Grid, TextField, } from '@mui/material';


export default function DisabledTextFieldGrid(label, value, onChange, type=null, sx=null) {
  return (
    <Grid item xs={12}>
      <TextField
      disabled
      label={label}
      value={value}
      onChange={onChange}
      type={type}
      sx={sx}
      />
    </Grid>
  )
}