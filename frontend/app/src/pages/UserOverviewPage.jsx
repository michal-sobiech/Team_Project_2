import { Grid, FormLabel} from '@mui/material';
import { CustomChart } from '../components/CustomChart';
import { ContentBox } from '../components/ContentBox';
import UserAppBar from './../components/UserAppBar';
import MyContext from './../contexts/MyContext';
import { useContext } from "react";
import NamedList from '../components/NamedList';
import { useTheme } from '@emotion/react';
import BoldLabel from '../components/BoldLabel';


const UserOverviewPage = () => {
  let data = useContext(MyContext);
  const theme = useTheme();

  function TodaysBalanceBox() {
    let recordsData = data.records
    let chartDataType = 'Idc';
    let label = 'Energy balance';
    recordsData.sort((a, b) => a.no_in_order - b.no_in_order);
    let X = recordsData.map(e => e.measureTime);
    let Y = recordsData.map(e => e[chartDataType]);

    let chartData = {
      labels: X,
      datasets: [{
        label: label,
        data: Y,
      }]
    }

    return (
      <ContentBox
        backgroundColor={theme.lightSilver}>
        <Grid
          container>
          <Grid
            item
            xs={12}
            sx={{ mx: 3, mt: 2 }}>
            <FormLabel>
              {'Hello, ' + data.usersName + '!'}
            </FormLabel>
          </Grid>
          <Grid
            item
            xs={12}>
            <CustomChart
              chartData={chartData}
              width={500}
              height={300}
              chartType={'line'} />
          </Grid>
        </Grid>
      </ContentBox>
    );
  }

  function ConnectedDevicesBox() {
    let elements = [];
    for (let device of data.devices) {
      elements.push(
        <ContentBox
          backgroundColor={theme.silver}>
          <Grid
            container>
            <Grid
              item
              xs={12}
              sx={{ ml: 2, mr: 2, mt: 2, mb: 0 }}>
              <FormLabel>
                {'Code: ' + device.code}
              </FormLabel>
            </Grid>
            <Grid
              item
              xs={12}
              sx={{ ml: 2, mr: 2, mt: 2, mb: 2 }}>
              <FormLabel>
                {'Model: ' + device.inverterModel.modelName}
              </FormLabel>
            </Grid>
          </Grid>
        </ContentBox>
      );
    }
    return (
      <ContentBox
        backgroundColor={theme.lightSilver}>
        <Grid
          container>
          <Grid
            item
            sx={{ mx: 2, my: 2 }}>
            <NamedList name={'Connected devices'} elements={elements} />
          </Grid>
        </Grid>
      </ContentBox>
    );
  }

  function LastTipsBox() {
    return (
      <ContentBox
        backgroundColor={theme.lightSilver}>
        <Grid
          container>
          <BoldLabel
            sx={{ mx: 3, my: 2 }}>
            Last tips
          </BoldLabel>
        </Grid>
      </ContentBox>
    );
  }

  function TodaysDateBox() {
    const currentTime = new Date();
    const formattedDate = currentTime.toLocaleDateString();

    return (
      <ContentBox
        backgroundColor={theme.lightSilver}>
        <Grid
          container>
          <Grid
            item
            xs={12}
            sx={{ mx: 3, my: 2 }}>
            <BoldLabel>
              Today is
            </BoldLabel>
          </Grid>
          <Grid
            item
            xs={12}
            sx={{ mx: 3, mb: 2 }}>
            <BoldLabel>
              {formattedDate}
            </BoldLabel>
          </Grid>
        </Grid>
      </ContentBox>
    );
  }

  return (
    <UserAppBar>
      <Grid
        container>
        <Grid
          item
          xs={6}>
          <TodaysBalanceBox />
        </Grid>
        <Grid
          item
          xs={6}>
          <ConnectedDevicesBox />
        </Grid>
        <Grid
          item
          xs={8}>
          <LastTipsBox />
        </Grid>
        <Grid
          item
          xs={4}>
          <TodaysDateBox />
        </Grid>
      </Grid>
    </UserAppBar>
  );
}

export default UserOverviewPage;