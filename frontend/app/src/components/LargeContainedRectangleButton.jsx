import { standardRectangle, combine } from "../themes/theme"
import { Button, } from '@mui/material';


export default function LargeContainedRectangleButton(label, onClick, modyfiers=null) {
  let givenSx = modyfiers? combine(standardRectangle, modyfiers): standardRectangle
  return (
    <Button
    sx={givenSx}
    size="large"
    variant="contained"
    onClick={onClick}>
      {label}
    </Button>
  )
}