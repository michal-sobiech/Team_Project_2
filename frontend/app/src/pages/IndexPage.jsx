import { useNavigate } from 'react-router-dom'
import { Container } from "@mui/system";
import { Box } from '@mui/material';
import { placeButton } from "../utils/placementutils"
import { buttonColorStyle } from "../themes/theme"
import { logIn } from "../utils/pathutils"


const IndexPage = () => {
  let navigate = useNavigate()

  function handleLoginButtonClick() {
    navigate(logIn);
  }

  return (
    <Container>
      <Box>
        {placeButton(buttonColorStyle, handleLoginButtonClick, "Log in")}
      </Box>
    </Container>
  );
}
export default IndexPage;
