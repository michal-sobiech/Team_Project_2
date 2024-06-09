import CustomizableAppBar from "./CustomizableAppBar";

export default function UserAppBar({ children }) {
    let appBarButtonsData = [
        {name: 'Overview', url: '/user_home/overview'},
        {name: 'Analysis', url: '/user_home/analysis'},
        {name: 'Tips', url: '/user_home/tips'},
        {name: 'Devices', url: '/user_home/devices'},
    ];

    return (
      <CustomizableAppBar
      buttonsData={appBarButtonsData}>
        {children}
      </CustomizableAppBar>
    );
}