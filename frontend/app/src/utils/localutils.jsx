
export function isValidUser(user) {
  return user.name & user.surname & user.username & user.email & user.password
}

export function isInvalidUser(user) {
  return !isValidUser(user)
  // return !user.name || !user.surname || !user.username || !user.email || !user.password
}

export function alertIfInvalidUser(user) {
  if (isInvalidUser(user)){
    window.alert("At least one of the fields is empty");
  }
}