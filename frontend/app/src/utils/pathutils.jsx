import { useNavigate } from "react-router-dom"


export const backendAddress = 'http://localhost:8080'

export const textRoot = "/";
export const logIn = makePath("", "log_in");
export const userHome = makePath("", "user_home");
export const adminHome = makePath("", "admin_home");
export const ownerHome = makePath("", "owner_home");
export const userOverview = makePath(userHome, "overview");
export const userAnalysis = makePath(userHome, "analysis");
export const userTipps = makePath(userHome, "tips");
export const userDevices = makePath(userHome, "devices");
export const homePage = makePath("", "home");
export const invertersPage = makePath("", "inverters");
export const invertersEdit = makePath(invertersPage, "edit");
export const invertersCreate = makePath(invertersPage, "create");
export const usersPage = makePath("", "users");
export const usersCreatePage = makePath(usersPage, "create");
export const usersEditPage = makePath(usersPage, "edit");
export const adminsPage = makePath("", "admins");
export const adminsCreatePage = makePath(adminsPage, "create")
export const adminsEditPage = makePath(adminsPage, "edit");

export function navigateToLogin() {
  let navigate = useNavigate;
  navigate(logIn);
}

export function makePath(...segments) {
  return segments.join("/");
}


export function pickOverviewPath(role) {
  return `/${role}_home/overview`
}

