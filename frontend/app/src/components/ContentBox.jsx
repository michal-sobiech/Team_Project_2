import { Box, Container } from '@mui/material'


export function ContentBox({ children, backgroundColor }) {

  return (
    <Container
    sx={{p:1}}
    disableGutters
    style={{
      width: '100%',
      height: '100%'
    }}>
      <Box
      sx={{
        color: 'black',
        backgroundColor: backgroundColor,
        width: '100%',
        height: '100%'
      }}>
        {children}
      </Box>
    </Container>
  );
}