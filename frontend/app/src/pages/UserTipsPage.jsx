import NamedList from './../components/NamedList';
import UserAppBar from '../components/UserAppBar';
import { useContext } from 'react';
import MyContext from '../contexts/MyContext';
import Tip from '../components/Tip';
import { Grid } from '@mui/material';


const UserTipsPage = () => {
  let data = useContext(MyContext);

  const TipsList = () => {
    let elems = []
    for (let dataSet of data) {
      elems.push(
        Tip(dataSet.title, dataSet.description)
      );
    }
    return (
      <NamedList name={'Tips'} elements={elems} />
    );
  }

  return (
    <UserAppBar>
      <Grid
        container
        sx={{ m: 3 }}>
          <TipsList />
      </Grid>
    </UserAppBar>
  );
}

export default UserTipsPage;