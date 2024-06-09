import React from "react";
import { Link } from "react-router-dom";

const InverterRow = ({ id, modelName, userUsername, ip }) => {
  return (
    <tr>
      <td>{id}</td>
      <td>{modelName}</td>
      <td>{userUsername}</td>
      <td>{ip}</td>
      <td>
        <Link
        to={`/admin_home/inverters/edit/?id=${id}`}
        className="btn btn-primary">
          Edit
        </Link>
      </td>
    </tr>
  );
};

export default InverterRow;
