
export default function TextFieldGrid(label, value, onChange) {
  return (
    <Grid item xs={12}>
      <TextField
      label={label}
      value={value}
      onChange={onChange}
      />
    </Grid>
  )
}
