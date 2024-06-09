import { useCookies } from 'react-cookie'


export default function useToken() {
  const tokenName = 'JWT_token';
  const [cookies, setCookies] = useCookies([tokenName]);

  function setToken(token) {
    setCookies(tokenName, token, { path: '/' });
  }

  return [cookies[tokenName], setToken];
}