import { FormLabel, Grid} from '@mui/material';
import { ContentBox } from '../components/ContentBox';
import NamedList from './../components/NamedList';
import UserAppBar from '../components/UserAppBar';
import MyContext from '../contexts/MyContext';
import { useContext} from "react";
import { useTheme } from '@emotion/react';


const DevicesList = () => {
  let data = useContext(MyContext);
  let theme = useTheme();

  let elems = []
  for (let dataSet of data) {
    elems.push(
      <ContentBox
        backgroundColor={theme.lightSilver}>
        <Grid
          container>
          <Grid
            container>
            <Grid
              item
              xs={12}
              sx={{ ml: 2, mt: 2 }}>
              <FormLabel>
                Code: {dataSet.code}
              </FormLabel>
            </Grid>
            <Grid
              item
              xs={12}
              sx={{ ml: 2, mt: 2 }}>
              <FormLabel>
                Model: {dataSet.inverterModel.modelName}
              </FormLabel>
            </Grid>
            <Grid
              item
              xs={12}
              sx={{ ml: 2, mt: 2 }}>
              <FormLabel>
                Producer: {dataSet.inverterModel.producer}
              </FormLabel>
            </Grid>
            <Grid
              item
              xs={12}
              sx={{ ml: 2, mt: 2, mb: 2 }}>
              <FormLabel>
                IP: {dataSet.ipAddress}
              </FormLabel>
            </Grid>
          </Grid>
        </Grid>
      </ContentBox>
    );
  }
  return (
    <NamedList name={"User's devices"} elements={elems} />
  );
}

const UserDevicesPage = () => {
  return (
    <UserAppBar>
      <Grid
        container
        sx={{ m: 2 }}>
        <DevicesList />
      </Grid>
    </UserAppBar>
  );
}

export default UserDevicesPage;
