import React from "react";
import { Link } from "react-router-dom";

const UserRow = ({ userId, username, name, surname, address, email, phoneNumber }) => {
  return (
    <tr>
      <td>{userId}</td>
      <td>{username}</td>
      <td>{name}</td>
      <td>{surname}</td>
      <td>{address}</td>
      <td>{email}</td>
      <td>{phoneNumber}</td>
      <td>
        <Link
        to={`/users/edit/?userId=${userId}`}
        className="btn btn-primary">
          Edit
        </Link>
        <Link
        to={`/users/delete/?userId=${userId}`}
        className="btn btn-danger">
          Delete
        </Link>
      </td>
    </tr>
  );
};

export default UserRow;
