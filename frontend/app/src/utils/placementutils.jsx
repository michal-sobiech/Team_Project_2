
import {
  Button, FormLabel, TextField, Grid, AppBar,
  Toolbar, Typography,
  TableBody, TableHead, TableRow, TableCell
} from '@mui/material';
import CenteringGrid from '../components/CenteringGrid';

export function SimpleAppBar() {
  return (
    <AppBar
      sx={{
        color: 'black',
        backgroundColor: 'silver',
        mb: 1
      }}>
      <Toolbar style={{ justifyContent: 'center' }}>
        <Typography variant='h1'>
          ProMan
        </Typography>
      </Toolbar>
    </AppBar>
  )
}

export function placeTextField(label, value, onChange, type = "text", id = null) {
  id = id ? id : `${label.toLowerCase()}-text-field`
  return (
    <TextField
      id={id}
      label={label}
      sx={{ mt: 1 }}
      value={value}
      type={type}
      onChange={onChange}
    />)
}

export function CentredTextFieldGrid(label, value, onChange, type = "text", id = null, xs = 12) {
  return (
    <Grid item xs={xs}>
      <CenteringGrid>
        {placeTextField(label, value, onChange, type, id)}
      </CenteringGrid>
    </Grid>
  )
}

export function CentredPasswordFieldGrid(value, onChange, id = null, xs = 12) {
  return CentredTextFieldGrid("Password", value, onChange, "password", id, xs)
}

export function placeButton(styleExtended, onClick, text) {
  return (
    <Button
      sx={styleExtended}
      onClick={onClick}>
      {text}
    </Button>
  )
}

export function CentredButtonGrid(
  buttonStyle, onClick, buttonText, xs = 12
) {
  return (
    <Grid item xs={xs}>
      <CenteringGrid>
        {placeButton(buttonStyle, onClick, buttonText)}
      </CenteringGrid>
    </Grid>
  )
}

export function CenteredFormLabelGrid(label, xs = 12) {
  return (
    <Grid item xs={xs}>
      <CenteringGrid>
        <FormLabel
          sx={{ mx: 1, mt: 1 }}>
          {label}
        </FormLabel>
      </CenteringGrid>
    </Grid>
  )
}


export function placeTableRow(...cellNames) {
  return (
    <TableRow>
      {
        cellNames.map(
          (cellName) => (<TableCell>{cellName}</TableCell>))
      }
    </TableRow>
  )
}

export function placeIdTableRow(key, ...cellNames) {
  return (
    <TableRow key={key}>
      {
        cellNames.map(
          (cellName) => (<TableCell>{cellName}</TableCell>))
      }
    </TableRow>
  )
}

export function placeTableHeader(...cellNames) {
  return (
    <TableHead>
      {placeTableRow(...cellNames)}
    </TableHead>
  )
}

export function placeUserTableBody(users) {
  return (
    <TableBody>
      {users.map((user) => (
        placeIdTableRow(
          user.id,
          user.username, user.name, user.surname, user.email)
      ))}
    </TableBody>
  )
}

export function placeInverterTableBody(inverters) {
  return (
    <TableBody>
      {inverters.map((inverter) => (
        placeIdTableRow(
          inverter.id,
          inverter.modelName,
          inverter.userUsername,
          inverter.description,
          inverter.login,
          inverter.password,
          inverter.ip
        )))}
    </TableBody>
  )
}


