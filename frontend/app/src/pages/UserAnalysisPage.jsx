import { Grid, FormLabel, FormControl, RadioGroup, Radio, FormControlLabel, Select, MenuItem } from '@mui/material';
import { CustomChart } from '../components/CustomChart';
import { ContentBox } from '../components/ContentBox';
import UserAppBar from '../components/UserAppBar';
import {useContext, useEffect, useState} from 'react';
import MyContext from "../contexts/MyContext";
import { useTheme } from '@emotion/react';
import RadioFormControlLabel from '../components/RadioFormControlLabel';
import { getRequestParsedResponse } from '../utils/restutils';
import { backendAddress } from '../utils/pathutils';
import useToken from '../hooks/useToken';

const UserAnalysisPage = () => {
  let data = useContext(MyContext);
  let [chartType, setChartType] = useState('bar');
  let [chartDataType, setChartDataType] = useState('Idc');
  let [inverterId, setInverterId] = useState('');
  let [timeRange, setTimeRange] = useState('15_minutes');
  let [token] = useToken();
  let [recordsData, setRecordsData] = useState([]);
  let theme = useTheme();

  useEffect(() => {
    async function getData() {
      setRecordsData([]);
      let responseJson = await getRequestParsedResponse(
          backendAddress
          + `/inverter_data?inverterId=${inverterId}&range=${timeRange}`,
          token
      );
      responseJson = (responseJson != null) ? responseJson : [];
      setRecordsData(responseJson);
    }
    if (inverterId !== null && inverterId !== undefined) {
      getData();
    }
  }, [inverterId, token, timeRange]);

  useEffect(() => {
    if (data.devices.length > 0) {
      setInverterId(data.devices[0].inverterId);
    }
  }, [data.devices]);

  function chooseLabel(chartDataType) {
    switch (chartDataType) {
      case 'Idc':
        return 'Avg. current [A]';
      case 'Udc1':
        return 'Avg. voltage [V]';
        // case 'generatedEnergy':
        //   return ['Generated Energy [kWh]', getSum];
        // case 'usedEnergy':
        //   return ['Used Energy [kWh]', getSum];
        // case 'energyBalance':
        //   return ['Energy balance [kWh]', getSumThenDiff];
      case 'generatorRotation':
        return "Generator's rotations [1/min]";
      default:
        return;
    }
  }

  const ChartBox = () => {

    console.log('records: ', recordsData);
    recordsData.sort((a, b) => a.no_in_order - b.no_in_order);
    let label = chooseLabel(chartDataType);
    let X = recordsData.map(e => e.measureTime);
    let Y = recordsData.map(e => e[chartDataType]);

    console.log('x axis ', X);
    console.log('y axis ', Y);

    let chartData = {
      labels: X,
      datasets: [{
        label: label,
        data: Y,
        //backgroundColor: 'rgba(10, 10, 250, 1)'
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
                Chart
              </FormLabel>
            </Grid>
            <Grid
                item
                xs={12}
                sx={{ mx: 3, mt: 2 }}>
              <CustomChart
                  chartData={chartData}
                  width={800}
                  height={500}
                  chartType={chartType} />
            </Grid>
          </Grid>
        </ContentBox>
    );
  }

  const handleInverterChange = (event) => {
    const selectedInverterId = event.target.value;
    setInverterId(selectedInverterId);
  };

  const InverterSelectionMenu = () => {
    return (
        <Grid container sx={{mt: 2, mb: 2, mx: 2}}>
          <FormControl fullWidth sx={{ width: '90%' }}>
            <FormLabel>Device Selection</FormLabel>
            <Select
              value={inverterId}
              onChange={handleInverterChange}
              required
            >
              {data.devices.map((device) => (
                <MenuItem key={device.inverterId} value={device.inverterId}>
                  {device.code}
                </MenuItem>
              ))}
            </Select>
          </FormControl>
        </Grid>
    );
  }


  const ChartOptionsBox = () => {
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
                Chart
              </FormLabel>
            </Grid>
            <Grid
                item
                xs={12}>
              <InverterSelectionMenu />
            </Grid>
            <Grid
                item
                xs={12}>
              <ChartDataSelectionButtons />
            </Grid>
            <Grid
                item
                xs={12}>
              <ChartTypeSelectionButtons />
            </Grid>
            <Grid
                item
                xs={12}>
              <TimeRangeSelectionButtons />
            </Grid>
          </Grid>
        </ContentBox>
    );
  }

  const ChartDataSelectionButtons = () => {

    function handleChange(event) {
      setChartDataType(event.target.value);
    }

    return (
        <FormControl
            sx={{ m: 3 }}>
          <FormLabel
              id='chart_data_selection'>
            Parameter
          </FormLabel>
          <RadioGroup
              aria-labelledby='chart_data_label'
              defaultValue={chartDataType}
              name='chart_data_buttons_group'
              onChange={handleChange}>
            {RadioFormControlLabel({ value: 'Idc', label: 'Current' })}
            {RadioFormControlLabel({ value: 'Udc1', label: 'Voltage' })}
            {/* {RadioFormControlLabel({value: 'generatedEnergy', label: 'Generated Energy'})}
          {RadioFormControlLabel({value: 'usedEnergy', label: 'Used Energy'})} */}
            {RadioFormControlLabel({ value: 'generatorRotation', label: 'Generator rotation' })}
            {/* {RadioFormControlLabel({value: 'energyBalance', label: 'Energy Balance'})} */}
          </RadioGroup>
        </FormControl>
    );
  }

  const ChartTypeSelectionButtons = () => {
    function handleChange(event) {
      setChartType(event.target.value);
    }

    return (
        <FormControl
            sx={{ m: 3 }}>
          <FormLabel
              id='chart_type_selection'>
            Chart type
          </FormLabel>
          <RadioGroup
              aria-labelledby='chart_type_label'
              defaultValue={chartType}
              name='chart_type_buttons_group'
              onChange={handleChange}>
            {RadioFormControlLabel({ value: 'bar', label: 'Bar' })}
            {RadioFormControlLabel({ value: 'line', label: 'Line' })}
            {/* {RadioFormControlLabel({value: 'pie', label: 'Pie'})} */}
          </RadioGroup>
        </FormControl>
    );
  }

  const TimeRangeSelectionButtons = () => {
    function handleChange(event) {
      setTimeRange(event.target.value);
    }

    return (
        <FormControl
            sx={{ m: 3 }}>
          <FormLabel>
            Time range
          </FormLabel>
          <RadioGroup
              defaultValue={timeRange}
              onChange={handleChange}>
            <FormControlLabel value='15_minutes' control={<Radio />} label='15 Minutes' />
            <FormControlLabel value='1_hour' control={<Radio />} label='1 Hour' />
            <FormControlLabel value='12_hours' control={<Radio />} label='12 Hours' />
            <FormControlLabel value='1_day' control={<Radio />} label='1 Day' />
            <FormControlLabel value='1_week' control={<Radio />} label='1 Week' />
            <FormControlLabel value='1_month' control={<Radio />} label='1 Month' />
            <FormControlLabel value='1_year' control={<Radio />} label='1 Year' />
            <FormControlLabel value='10_years' control={<Radio />} label='10 Years' />
          </RadioGroup>
        </FormControl>
    );
  }

  return (
      <UserAppBar>
        <Grid
            container>
          <Grid
              item
              xs={7}>
            <ChartBox />
          </Grid>
          <Grid
              item
              xs={5}>
            <ChartOptionsBox />
          </Grid>
        </Grid>
      </UserAppBar>
  )
}
export default UserAnalysisPage;
