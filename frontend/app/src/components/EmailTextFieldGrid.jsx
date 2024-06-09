import TextFieldGrid from "./TextFieldGrid"


export default function EmailTextFieldGrid({ value, onChange, label="Email", xs=null}) {
  return ( 
    <TextFieldGrid
    label={label} 
    value={value} 
    onChange={onChange} 
    type={"email"} 
    xs={xs}/>
  )
}
