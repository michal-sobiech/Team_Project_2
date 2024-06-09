import { Bar, Line, Pie } from 'react-chartjs-2'
import { CategoryScale } from 'chart.js'; 
import Chart from 'chart.js/auto';


Chart.register(CategoryScale);

const options = {
  layout: {
    padding: {
      left: 20,
      right: 40,
      top: 20,
      bottom: 20,
    },
  },
  maintainAspectRatio: false,
};

export function CustomChart({ chartData, width, height, chartType }) {
  let ChartComponent;
  switch (chartType) {
    case 'bar':
    default:
      ChartComponent = Bar;
      break;
    case 'pie':
      ChartComponent = Pie;
      break;
    case 'line':
      ChartComponent = Line;
      break;
  }

  return (
    <ChartComponent
      data={chartData}
      options={options}
      width={width}
      height={height} />
  );
}