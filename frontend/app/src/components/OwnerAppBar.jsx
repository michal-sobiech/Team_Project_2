import CustomizableAppBar from "./CustomizableAppBar";

export default function OwnerAppBar({ children }) {

    let appBarButtonsData = [
      {name: 'Overview', url: '/home'},
      {name: 'Users', url: '/users'},
      {name: 'Inverters', url: '/inverters'},
      {name: 'Admins', url: '/admins'},
    ];

    return (
      <CustomizableAppBar
      buttonsData={appBarButtonsData}>
        {children}
      </CustomizableAppBar>
    );
}