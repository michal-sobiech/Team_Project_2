import { FormLabel, Grid, Typography } from '@mui/material';
import { theme, flexGrow } from "../themes/theme"
import { ContentBox } from './ContentBox';

export default function Tip(name, description) {
  return (
    <ContentBox
    backgroundColor={theme.lightSilver}>
      <Grid
      container>
        <Grid
        container
        sx={{m:3}}>
          <Grid
          item
          xs={12}>
            <Typography
              variant='h6'
              component='div'
              sx={flexGrow}>
              {name}
            </Typography>
          </Grid>
          <Grid
          item
          xs={12}>
            <FormLabel
            sx={{m: 3}}>
            {description}
            </FormLabel>
          </Grid>
        </Grid>
      </Grid>
    </ContentBox>
  )
}