
import { FormControlLabel, Radio} from '@mui/material';


export default function RadioFormControlLabel({value, label}) {
  return (
    <FormControlLabel
    value={value}
    control={<Radio/>}
    label={label}/>
  );
}


