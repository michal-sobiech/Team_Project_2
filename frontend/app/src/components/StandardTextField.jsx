import { combined } from "../themes/theme"
import { TextField } from '@mui/material';

export default function StandardTextField(label, value, onChange, type=null) {
  return (
    <TextField
    sx={combined}
    label={label}
    value={value}
    onChange={onChange}
    type={type}/>
  )
}