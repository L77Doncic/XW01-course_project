<template>
  <div ref="chartDom" style="width: 600px; height: 400px;"></div>
</template>

<script setup>
import { onMounted, watch, ref } from "vue";
import * as echarts from "echarts";

const props = defineProps({
  data: {
    type: Array,
    required: true
  }
});

const chartDom = ref(null);
let myChart;

const setChartOptions = () => {
  if (!props.data || props.data.length === 0) {
    console.warn("Data is empty or not an array:", props.data);
    return;
  }

  const option = {
    title: {
      text: "" //bigTitle
    },
    tooltip: {},
    xAxis: {
      type: "category",
      name: "姓名",
      data: props.data.map(item => item.creator)
    },
    yAxis: {
      name: "人数",
      type: "value",
      axisLabel: {
      align: 'right', // 标签右对齐
      verticalAlign: 'middle' // 垂直居中对齐
    }
    },
   series: [
  {
    type: "bar", // 柱状图
    data: props.data.map(item => item.count),
    itemStyle: {
      color: params =>
        `hsl(${(params.dataIndex / props.data.length) * 360}, 70%, 50%)`
    }
  },
  {
    type: "line", // 折线图
    data: props.data.map(item => item.count),
    itemStyle: {
      color: 'blue' // 你可以选择其他颜色
    }
  }
]

  };

  myChart.setOption(option);
};

onMounted(() => {
  console.log("Initial props.data:", props.data);
  myChart = echarts.init(chartDom.value);
  setChartOptions();
});

watch(
  () => props.data,
  () => {
    setChartOptions();
    console.log("Updated props.data:", props.data);
  },
  { deep: true }
);
</script>
