import { createTheme } from '@mui/material/styles';

export const theme = createTheme({
  white: '#FFFFFF',
  silver: '#C0C0C0',
  black: '#000000',
  lightSilver: '#e8e8e8',
  red: '#ff3300',
  components: {
    MuiButton: {
      styleOverrides: {
        root: {
          borderRadius: '4px',
          color: '#000000', // black
        },
        contained: {
          backgroundColor: '#C0C0C0', // silver
          boxShadow: 'none',
          '&:hover': {
            backgroundColor: '#D3D3D3', // lighter silver on hover
            boxShadow: 'none',
          },
          '& .MuiButton-label': {
            color: '#000000', // black
          },
        },
      },
      defaultProps: {
        variant: 'contained',
        color: 'primary',
      },
    },
  },
  typography: {
    fontFamily: '"Segoe UI"',
    h1: {
      fontSize: '3rem',
      fontWeight: 500,
      letterSpacing: '-0.01562em',
      lineHeight: 1.2,
    },
    body1: {
      fontSize: '1rem',
      fontWeight: 400,
      lineHeight: 1.5,
    },
  },
},);

export function combine(...dictionaries) {
  let combinedDictionary = {};
  for (let dictionary of dictionaries) {
    for (let [key, value] of Object.entries(dictionary)) {
      combinedDictionary[key] = value;
    }
  }
  return combinedDictionary;
}

export const standardHeight = "50px";
export const halfWidth = "100px";
export const standardWidth = "200px";
export const doubleWidth = "400px";
export const oneRem = "1rem";

export const buttonColorStyle = {color: 'black', backgroundColor: 'silver'}
export const rightMargin = { marginRight: oneRem };
export const standardRectangle = { width: standardWidth, height: standardHeight }
export const halfWidthRectangle = { width: halfWidth, height: standardHeight }
export const combined = { width: standardWidth, height: standardHeight, marginRight: oneRem }
export const doubleWidthRectangle = {width: doubleWidth, height: standardHeight}
export const flexGrow = { flexGrow: 1, mr: 5 }


