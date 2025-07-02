<template>
  <div>
    <div v-show="showChart === 'chart1'" ref="chart1Dom" style="width: 600px; height: 400px;"></div>
    <div v-show="showChart === 'chart2'" ref="chart2Dom" style="width: 600px; height: 400px;"></div>
  </div>
</template>

<script setup>
import { onMounted, watch, ref } from 'vue';
import * as echarts from 'echarts';

const props = defineProps({
  showChart: {
    type: String,
    required: true
  },
  chart1Data: {
    type: Array,
    required: true
  },
  chart2Data: {
    type: Array,
    required: true
  }
});

const chart1Dom = ref(null);
const chart2Dom = ref(null);
let chart1, chart2;

const initCharts = () => {
  if (chart1Dom.value && !chart1) {
    chart1 = echarts.init(chart1Dom.value);
    setChart1Options();
  }
  if (chart2Dom.value && !chart2) {
    chart2 = echarts.init(chart2Dom.value);
    setChart2Options();
  }
};

const setChart1Options = () => {
  const option = {
    xAxis: {
      type: 'category',
      data: props.chart1Data.map(item => item.creator)
    },
    yAxis: {
      type: 'value'
    },
    series: [{
      data: props.chart1Data.map(item => item.count),
      type: 'bar'
    }]
  };

  chart1.setOption(option);
};

const setChart2Options = () => {
  const option = {
    xAxis: {
      type: 'category',
      data: props.chart2Data.map(item => item.creator)
    },
    yAxis: {
      type: 'value'
    },
    series: [{
      data: props.chart2Data.map(item => item.count),
      type: 'bar'
    }]
  };

  chart2.setOption(option);
};

onMounted(() => {
  initCharts();
});

watch(
  () => props.showChart,
  () => {
    if (props.showChart === 'chart1' && chart1) {
      setChart1Options();
    } else if (props.showChart === 'chart2' && chart2) {
      setChart2Options();
    }
  }
);

watch(
  () => props.chart1Data,
  () => {
    if (chart1) {
      setChart1Options();
    }
  },
  { deep: true }
);

watch(
  () => props.chart2Data,
  () => {
    if (chart2) {
      setChart2Options();
    }
  },
  { deep: true }
);
</script>
