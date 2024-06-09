import { useContext } from "react";
import MyContext from '../contexts/MyContext';
import AdminNavigationBar from './../components/AdminNavigationBar';
import OwnerAppBar from "../components/OwnerAppBar";

export default function HomePage() {
  let data = useContext(MyContext);

  return data.role === 'owner' ? (
    <OwnerAppBar>
      <h1
      style={{ 
        display: "flex", 
        justifyContent: "center",
        alignItems: "center",
        height: "80vh",
        color: "grey"
      }}>
        Welcome {data.name}!
      </h1>
    </OwnerAppBar>
    ) : (
    <AdminNavigationBar>
      <h1
      style={{
        display: "flex",
        justifyContent: "center",
        alignItems: "center",
        height: "80vh",
        color: "grey"
      }}>
        Welcome {data.name}!
      </h1>
    </AdminNavigationBar>
  );
}
