import TextFieldGrid from "./TextFieldGrid"

export default function PasswordTextFieldGrid({ value, onChange, label="Password", xs=null }) {
  return ( 
    <TextFieldGrid
    label={label} 
    value={value} 
    onChange={onChange} 
    type={"password"} 
    xs={xs}/>
  )
}
