import { ThemeProvider } from '@emotion/react';
import ReactDOM from 'react-dom/client';
import UserOverviewPage from './pages/UserOverviewPage';
import UserAnalysisPage from './pages/UserAnalysisPage';
import UserTipsPage from './pages/UserTipsPage';
import UserDevicesPage from './pages/UserDevicesPage';
import { createBrowserRouter, Outlet, RouterProvider, Navigate } from 'react-router-dom';
import LogInPage from './pages/LogInPage';
import HomePage from './pages/HomePage';
import InvertersPage from './pages/InvertersPage';
import InvertersEditPage from './pages/InvertersEditPage';
import InvertersCreatePage from './pages/InvertersCreatePage'
import UsersPage from './pages/UsersPage';
import UsersCreatePage from './pages/UsersCreatePage';
import UsersEditPage from './pages/UsersEditPage';
import AdminsPage from './pages/AdminsPage';
import AdminsCreatePage from './pages/AdminsCreatePage';
import AdminsEditPage from './pages/AdminsEditPage';
import RouteCreator from './utils/RouteCreator';
import { theme } from './themes/theme';
import {
  textRoot,
  logIn,
  userOverview,
  userAnalysis,
  userTipps,
  userDevices,
  backendAddress,
  homePage,
  invertersPage,
  invertersEdit,
  invertersCreate,
  usersPage,
  usersCreatePage,
  usersEditPage,
  adminsPage,
  adminsCreatePage,
  adminsEditPage
} from './utils/pathutils';



let routeCreator = new RouteCreator(
  backendAddress,
  <Navigate to={logIn}/>
)

const router = createBrowserRouter(
  [{
    path: textRoot,
    element: (
      <ThemeProvider theme={theme}>
        <Outlet/>
      </ThemeProvider>
    ),
    children: [
      routeCreator.createRoute(textRoot, <Navigate to={logIn}/>),
      routeCreator.createRoute(logIn, <LogInPage/>),
      routeCreator.createProtectedRoute(userOverview, <UserOverviewPage/>),
      routeCreator.createProtectedRoute(userAnalysis, <UserAnalysisPage/>),
      routeCreator.createProtectedRoute(userTipps, <UserTipsPage/>),
      routeCreator.createProtectedRoute(userDevices, <UserDevicesPage/>),
      routeCreator.createProtectedRoute(homePage, <HomePage/>),
      routeCreator.createProtectedRoute(invertersPage, <InvertersPage/>),
      routeCreator.createProtectedRoute(invertersEdit, <InvertersEditPage/>),
      routeCreator.createProtectedRoute(invertersCreate, <InvertersCreatePage/>),
      routeCreator.createProtectedRoute(usersPage, <UsersPage/>),
      routeCreator.createProtectedRoute(usersCreatePage, <UsersCreatePage/>),
      routeCreator.createProtectedRoute(usersEditPage, <UsersEditPage/>),
      routeCreator.createProtectedRoute(adminsPage, <AdminsPage/>),
      routeCreator.createProtectedRoute(adminsCreatePage, <AdminsCreatePage/>),
      routeCreator.createProtectedRoute(adminsEditPage, <AdminsEditPage/>),
    ]
  }]
);

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
    <RouterProvider router={router}/>
);
