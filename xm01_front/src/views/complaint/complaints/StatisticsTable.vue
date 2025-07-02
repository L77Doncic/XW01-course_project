<template>
  <div ref="chartDom" style="width: 600px; height: 400px;"></div>
</template>

<script setup>
import { onMounted, watch, ref, nextTick } from "vue";
import * as echarts from "echarts";

const props = defineProps({
  data: {
    type: Array,
    required: true
  }
});

const chartDom = ref(null);
let myChart = null;

const setChartOptions = () => {
  if (!props.data || props.data.length === 0) {
    console.warn("统计数据为空:", props.data);
    return;
  }

  console.log("设置图表选项，数据:", props.data);

  const option = {
    title: {
      text: "投诉人统计",
      textStyle: {
        color: '#333'
      }
    },
    tooltip: {
      trigger: 'axis'
    },
    legend: {
      data: ['投诉次数'],
      textStyle: {
        color: '#333'
      }
    },
    xAxis: {
      type: "category",
      name: "投诉人",
      data: props.data.map(item => item.name),
      axisLabel: {
        color: '#333'
      }
    },
    yAxis: {
      name: "投诉次数",
      type: "value",
      axisLabel: {
        color: '#333'
      }
    },
    series: [
      {
        name: '投诉次数',
        type: "bar",
        data: props.data.map(item => item.count),
        itemStyle: {
          color: params =>
            `hsl(${(params.dataIndex / props.data.length) * 360}, 70%, 50%)`
        }
      },
      {
        name: '投诉次数',
        type: "line",
        data: props.data.map(item => item.count),
        itemStyle: {
          color: 'blue'
        }
      }
    ]
  };

  if (myChart) {
    myChart.setOption(option);
  }
};

onMounted(async () => {
  console.log("组件挂载，初始数据:", props.data);
  await nextTick();

  if (chartDom.value) {
    myChart = echarts.init(chartDom.value);
    setChartOptions();
  }
});

watch(
  () => props.data,
  (newData) => {
    console.log("数据变化:", newData);
    setChartOptions();
  },
  { deep: true, immediate: true }
);
</script>