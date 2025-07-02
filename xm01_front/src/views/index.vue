<template>
<body style="overflow:-Scroll;overflow-y:hidden">

  <div class="head">
    <!-- <div class="fullscreen-btn" @click="toggleFullscreen">
      <span>全屏</span>
    </div> -->
    <h1>交通视频分析-XM01</h1>
    <div class="weather">
      <span>{{ currentDateTime }}</span>
    </div>
  </div>
  <div class="mainbox">
    <ul class="clearfix">
      <li>
        <div class="boxall" style="height: 65vmin">
          <div class="alltitle">用户大致统计信息</div>

          <div class="sycm">
            <ul class="clearfix">
              <li>
                <h2 id="user_num">{{total}}</h2>
                <span>用户总数</span>
              </li>
              <li>
                <h2 id="login_num">{{logintotal}}</h2>
                <span>总计登录次数</span>
              </li>
              <li>
                <h2 id="ave">{{Math.floor(logintotal/total)}}</h2>
                <span>平均登录次数</span>
              </li>
            </ul>
            <div style="border-bottom: 1px solid rgba(255,255,255,.1)"></div>
            <ul class="clearfix">
              <li>
                <h2 id="max_login">{{loginmax}}</h2>
                <span>最高登录次数</span>
              </li>
              <li>
                <h2 id="max_name">admin</h2>
                <span>最高次数用户名</span>
              </li>
              <li>
                <h2 id="ID_Register">1</h2>
                <span>该用户第几位注册</span>
              </li>
            </ul>
          </div>
          <div class="addnew">
            <div class="tit02">
              <span>用户登录信息图表</span>
            </div>
            <!-- 根据chart插入图表 -->
            <div class="statisuser">
              <div ref="chart1" class="chartuser"></div>
            </div>

            <div class="allnav" style="height: 2.2rem" id="wbzl"></div>
          </div>
          <div class="boxfoot"></div>
        </div>
        <div class="boxall" style="height: 40vmin">
          <div class="alltitle">视频数据总览</div>
          <div class="allnav" style="height: 1.5rem" id="wxyg"></div>
          <div class="addnew">
            <div class="tit02">
              <span>违规种类分布</span>
            </div>
            <div class="statisuser">
              <div ref="chart2" class="chartuser"></div>
            </div>
            <div class="allnav" style="height: 2.0rem" id="wwbzl"></div>
          </div>
          <div class="boxfoot"></div>
        </div>
      </li>
      <li>
        <div class="bar">
          <div class="barbox">
            <ul class="clearfix">
              <li id="pic_num" class="pulll_left counter">10</li>
              <li id="vdo_num" class="pulll_left counter">7</li>
            </ul>
          </div>
          <div class="barbox2">
            <ul class="clearfix">
              <li class="pulll_left">服务器图片数据数量</li>
              <li class="pulll_left">服务器视频数据数量</li>
            </ul>
          </div>
        </div>
        <div class="bar">
          <div class="barbox">
            <ul class="clearfix">
              <li id="flownum" class="pulll_left counter">590</li>
              <li id="vltnum" class="pulll_left counter">24</li>
            </ul>
          </div>
          <div class="barbox2">
            <ul class="clearfix">
              <li class="pulll_left">总车流量</li>
              <li class="pulll_left">总违规量</li>
            </ul>
          </div>
        </div>
        <!-- 中间转动的圆 -->
        <div class="map">
          <div class="map1">
            <img src="../assets/mainimages/lbx.png" />
          </div>
          <div class="map2">
            <img src="../assets/mainimages/jt.png" />
          </div>
          <div class="map3">
            <img src="../assets/mainimages/map.png" />
          </div>
          <div class="map4" id="map_1"></div>
        </div>

        <div class="boxall" style="height: 46vmin;width: auto;margin: 1vmin 2vmin;">
          <div class="alltitle">天气数据统计</div>
          <div class="statisuser3">
            <div ref="chart3" class="chartuser3"></div>
          </div>
          <div
            class="allnav"
            id="dbj"
            style="position: absolute;left: -0.9rem;top:-01vmin;width:122%;height:100%;margin: 0 auto"
          >
            <div id="main" style="width:100%;height:100%;margin: 0 auto;"></div>
          </div>
          <div class="boxfoot"></div>
        </div>
      </li>
      <li>
        <div class="boxall" style="height:40vmin">
          <div class="alltitle">车牌检测情况</div>
          <div class="statisuser3">
            <div ref="chart5" class="chartuser3"></div>
          </div>
          <div class="addnew">
            

            <div class="adduser" style="height: 2.0rem">
              <ul id="adduser"></ul>
            </div>
          </div>
          <div class="boxfoot"></div>
        </div>

        <div class="boxall" style="height: 20vmin">
          <div class="alltitle">成都天气</div>
          <div class="grid">
            <span class="line"></span>
          </div>
          <div>
            <span style="color: #fff;opacity:.5;font-size:2.5vmin">
              最低气温 ：
              <h v-if="mintemp" style="color: #fff;">
                {{
                mintemp }}
              </h>°
            </span>
            <span id="min_tem" style="color: #DC143C;font-size:1.5vmin;    font-weight: bold;"></span>
            <span style="margin-left:15%;color: #fff;opacity:.5; font-size:2.5vmin">
              最高气温 ：
              <h v-if="maxtemp" style="color: #fff;">{{ maxtemp }}</h>°
            </span>
            <span id="max_tem" style="color: #ffeb7b;font-size:1.5vmin;    font-weight: old;"></span>
          </div>
          <div class="boxfoot"></div>
        </div>
        <div class="boxall" style="height: 40vmin;">
          <div class="alltitle">成都近日天气</div>
          <div class="statisuser3">
            <div ref="chart4" class="chartuser3"></div>
          </div>
          <div class="allnav" style="height: 3.0rem;position: relative;top: -0.12rem;" id="sbj"></div>

          <div class="boxfoot"></div>
        </div>
      </li>
    </ul>
  </div>
  <div class="back"></div>
</body>
</template>

<script setup>
import { ref, onMounted } from "vue";

import * as echarts from "echarts";

const toggleFullscreen = async () => {
  try {
    // 添加/移除全屏样式类
    document.documentElement.classList.toggle('fullscreen-mode');
    
    // 同时使用标准全屏API
    if (!document.fullscreenElement) {
      const elem = document.documentElement;
      if (elem.requestFullscreen) {
        await elem.requestFullscreen();
      } else if (elem.webkitRequestFullscreen) { /* Safari */
        await elem.webkitRequestFullscreen();
      } else if (elem.msRequestFullscreen) { /* IE11 */
        await elem.msRequestFullscreen();
      }
    } else {
      if (document.exitFullscreen) {
        await document.exitFullscreen();
      } else if (document.webkitExitFullscreen) { /* Safari */
        await document.webkitExitFullscreen();
      } else if (document.msExitFullscreen) { /* IE11 */
        await document.msExitFullscreen();
      }
    }
  } catch (err) {
    console.error('全屏切换错误:', err);
  }
};
import "echarts-wordcloud";
import {
  listWeather,
  getWeather,
  delWeather,
  addWeather,
  updateWeather
} from "@/api/weather/weather";
import { listCarcolor } from "@/api/carcolor/carcolor"
const currentDateTime = ref("");
const chart2 = ref(null);
const chart1 = ref(null);

function updateDateTime() {
  const now = new Date();
  currentDateTime.value = now.toLocaleString();
}

//用户部分
import {
  changeUserStatus,
  listUser,
  resetUserPwd,
  delUser,
  getUser,
  updateUser,
  addUser,
  deptTreeSelect
} from "@/api/system/user";
import {
  list,
  listmax,
  delLogininfor,
  cleanLogininfor,
  unlockLogininfor
} from "@/api/monitor/logininfor";

const loading = ref(true);
const userList = ref([]);
const loginList = ref([]);

const total = ref(0);
const loginmax = ref(0);
const logintotal = ref(0);

const query = {
  pageNum: 1, // 第 1 页
  pageSize: 10 // 每页 10 条
};
getList();
function getList() {
  //用户表
  loading.value = true;
  listUser(query)
    .then(res => {
      loading.value = false;
      userList.value = res.rows;
      total.value = res.total;
      console.log("total:", total.value);
    })
    .catch(error => {
      loading.value = false;
      console.error("获取用户列表失败:", error);
    });
  //登录表
  loading.value = true;
  list(query)
    .then(res => {
      loading.value = false;
      logintotal.value = res.total;
      console.log("logintotal:", logintotal.value);
    })
    .catch(error => {
      loading.value = false;
      console.error("获取登录列表失败:", error);
    });
  //最高登录次数
  loading.value = true;
  listmax(query)
    .then(res => {
      loading.value = false;
      loginList.value = res.rows;
      // loginmax.value = res.total;
      console.log("loginList:", loginList.value);
      // 对 loginList 按 count 降序排序（从大到小）
      loginList.value.sort((a, b) => b.count - a.count);

      loginmax.value = loginList.value[0].count;
      
    })
    .catch(error => {
      loading.value = false;
      console.error("获取登录列表失败:", error);
    });
}

//用户部分
//天气开始-----------------------------------------------------------------------------------------------


//天气结束-----------------------------------------------------------------------------------------------
onMounted(() => {

  updateDateTime();
  setInterval(updateDateTime, 1000);
  //测试统计1
  const myChart1 = echarts.init(chart1.value);
  const option1 = {
    title: {
      text: ""
    },
    tooltip: {},
    legend: {
      data: ["数据集一"],
      top: "bottom"
    },
    radar: {
      center: ["50%", "50%"], // 调整雷达图中心位置
      radius: "70%", // 调整雷达图的半径
      nameGap: 10,
      indicator: [
        { name:"test1", max: 100 },
        { name: "java", max: 100 },
        { name: "jj", max: 100 },
        { name: "javatest", max: 100 },
        { name: "admin", max: 100 }
      ]
    },
    series: [
      {
        name: "数据集",
        type: "radar",
        lineStyle: {
          color: "#FF0000", // 设置中间连线颜色为红色
          width: 2, // 设置连线的宽度
          type: "solid" // 设置线条类型，可选：'solid'（实线）、'dashed'（虚线）、'dotted'（点线）
        },
        data: [
          {
            value: [18, 20, 50, 60, 95],
            name: ""
          }
        ]
      }
    ]
  };
 myChart1.setOption(option1);

//测试2
const myChart2 = echarts.init(chart2.value);

const option2 = {
  tooltip: {
    trigger: "item",
    formatter: "{a} <br/>{b}: {c} ({d}%)"
  },

  series: [
    {
      name: "违规类型",
      type: "pie",
      radius: ["40%", "70%"],
      center: ["50%", "50%"],
      label: {
        show: true,
        formatter: function(params) {
          const total = option2.series[0].data.reduce((sum, item) => sum + item.value, 0);
          const percentage = (params.value / total * 100).toFixed(1);

          return params.name;
        }
      },
      labelLine: {
        show: function(params) {
          const total = option2.series[0].data.reduce((sum, item) => sum + item.value, 0);
          const percentage = (params.value / total * 100).toFixed(1);
          return percentage >= 10;
        }
      },
      data: [
        { value: 1000, name: "超速驾驶", itemStyle: { color: "#FF6B6B" } },
        { value: 800, name: "酒后驾驶", itemStyle: { color: "#4ECDC4" } },
        { value: 600, name: "闯红灯", itemStyle: { color: "#45B7D1" } },
        { value: 400, name: "违章停车", itemStyle: { color: "#96CEB4" } },
        { value: 300, name: "不按车道行驶", itemStyle: { color: "#FFEAA7" } },
        { value: 200, name: "不系安全带", itemStyle: { color: "#DDA0DD" } },
        { value: 150, name: "驾驶时使用手机", itemStyle: { color: "#98D8C8" } },
        { value: 100, name: "未按规定让行", itemStyle: { color: "#F7DC6F" } }
      ],
      emphasis: {
        itemStyle: {
          shadowBlur: 10,
          shadowOffsetX: 0,
          shadowColor: "rgba(0, 0, 0, 0.5)"
        }
      }
    }
  ]
};

myChart2.setOption(option2);

  fetchDataAndDrawChart();
  fetchDataAndDrawChart4();
  fetchDataAndDrawChart5();

});


const chart3 = ref(null); // 用于绑定图表容器
const maxtemp = ref(null);
const mintemp = ref(null);
const fetchDataAndDrawChart = async () => {
  try {
    // 调用 API 获取数据
    const response = await listWeather({});
    console.log(response);
    // 确保 response.rows 存在且是一个数组
    if (!response.rows || !Array.isArray(response.rows)) {
      console.error("Invalid data format:", response.rows);
      return; // 如果数据无效，直接返回
    }

    // 提取所有记录的日期和温度数据
    const dates = response.rows.map(record => record.obsTime); // 获取所有日期
    const currentTemps = response.rows.map(record => record.currentTemp); // 获取所有当前温度
    const day1Temps = response.rows.map(record => record.day1TempMax); // 获取未来1天最高温
    const day2Temps = response.rows.map(record => record.day2TempMax); // 获取未来2天最高温
    const day3Temps = response.rows.map(record => record.day3TempMax); // 获取未来3天最高温
    const day4Temps = response.rows.map(record => record.day4TempMax); // 获取未来4天最高温

    maxtemp.value = day1Temps[day1Temps.length - 1]; // 最新的未来1天最高温
    mintemp.value = response.rows[response.rows.length - 1].day1TempMin;

    // 初始化图表
    const myChart3 = echarts.init(chart3.value);
    const option3 = {
      title: {
        text: "温度变化趋势",
        left: "center",
        textStyle: {
          color: "white", // 设置标题字体颜色，例如橙色
          fontSize: 16 // 可选：设置字体大小
        }
      },
      tooltip: {
        trigger: "axis"
      },
      xAxis: {
        type: "category",
        data: dates, // 使用多个日期作为 X 轴
        axisLabel: {
      textStyle: {
        color: "#4caf50", // 设置 X 轴标签字体颜色
      },
    },
      },
      
      yAxis: {
        type: "value",
        name: "温度 (°C)",
        nameTextStyle: {
          color: "#4caf50" // 设置 Y 轴名称字体颜色
        },
        axisLabel: {
          textStyle: {
            color: "#4caf50" // 设置 Y 轴标签字体颜色
          }
        }
      },
      series: [
        {
          name: "当前温度",
          type: "line", // 设置图表类型为折线图
          data: currentTemps, // 当前温度数据
          smooth: true // 平滑曲线
        },
        {
          name: "未来1天温度",
          type: "line",
          data: day1Temps, // 未来1天最高温
          smooth: true
        },
        {
          name: "未来2天温度",
          type: "line",
          data: day2Temps, // 未来2天最高温
          smooth: true
        },
        {
          name: "未来3天温度",
          type: "line",
          data: day3Temps, // 未来3天最高温
          smooth: true
        },
        {
          name: "未来4天温度",
          type: "line",
          data: day4Temps, // 未来4天最高温
          smooth: true
        }
      ]
    };

    // 设置图表的选项
    myChart3.setOption(option3);
  } catch (error) {
    console.error("Error fetching weather data:", error);
  }
};


const chart4 = ref(null); // 用于绑定图表容器

// 获取数据并绘制柱状图
const fetchDataAndDrawChart4 = async () => {
  try {
    // 调用 API 获取数据
    const response = await listWeather({});
    console.log(response);

    // 确保 response.rows 存在且是一个数组
    if (!response.rows || !Array.isArray(response.rows)) {
      console.error("Invalid data format:", response.rows);
      return; // 如果数据无效，直接返回
    }

    // 提取所有记录的日期和温度数据
    const dates = response.rows.map(record => record.obsTime); // 获取所有日期
    const day1Temps = response.rows.map(record => record.day1TempMax); // 获取未来1天最高温
    const day2Temps = response.rows.map(record => record.day2TempMax); // 获取未来2天最高温
    const day3Temps = response.rows.map(record => record.day3TempMax); // 获取未来3天最高温
    const day4Temps = response.rows.map(record => record.day4TempMax); // 获取未来4天最高温

    // 初始化图表
    const myChart4 = echarts.init(chart4.value);
    const option4 = {
      title: {
        left: "center"
      },
      tooltip: {
        trigger: "axis"
      },
      legend: {
        data: ["未来1天", "未来2天", "未来3天", "未来4天"], // 图例
        textStyle: {
          color: "#ffffff" // 设置图例文字颜色为白色
        }
      },
      xAxis: {
        type: "category",
        data: dates // 使用多个日期作为 X 轴
      },
      yAxis: {
        type: "value",
        name: "温度 (°C)"
      },
      series: [
        {
          name: "未来1天",
          type: "bar", // 设置图表类型为柱状图
          data: day1Temps // 未来1天最高温
        },
        {
          name: "未来2天",
          type: "bar",
          data: day2Temps // 未来2天最高温
        },
        {
          name: "未来3天",
          type: "bar",
          data: day3Temps // 未来3天最高温
        },
        {
          name: "未来4天",
          type: "bar",
          data: day4Temps // 未来4天最高温
        }
      ]
    };

    // 设置图表的选项
    myChart4.setOption(option4);
  } catch (error) {
    console.error("Error fetching weather data:", error);
  }
};

const chart5 = ref(null); // 用于绑定图表容器
const fetchDataAndDrawChart5 = async () => {
  try {
    // 调用 API 获取数据
    const response = await listCarcolor({});
    console.log(response);

    // 确保 response.rows 存在且是一个数组
    if (!response.rows || !Array.isArray(response.rows)) {
      console.error("Invalid data format:", response.rows);
      return; // 如果数据无效，直接返回
    }

    // 统计车牌颜色的数量
    const colorCount = response.rows.reduce((acc, record) => {
      const color = record.plateColor;  // 从每个记录中提取车牌颜色
      if (color) {
        acc[color] = (acc[color] || 0) + 1;  // 如果颜色存在，增加计数
      }
      return acc;
    }, {});

    // 提取车牌颜色和数量
    const colorNames = Object.keys(colorCount);  // 车牌颜色
    const colorValues = Object.values(colorCount);  // 对应的数量

    // 初始化图表
    const myChart5 = echarts.init(chart5.value);
    const option5 = {
      title: {
        text: "车牌颜色分布",
        left: "center",
        textStyle: {
          color: "#ffffff", // 设置标题文字颜色
        },
      },
      tooltip: {
        trigger: "item",
      },
      legend: {
        orient: "vertical",
        left: "left",
        textStyle: {
          color: "#ffffff", // 设置图例文字颜色为白色
        },
      },
      series: [
        {
          name: "车牌颜色",
          type: "pie", // 设置图表类型为饼图
          radius: "50%",
          data: colorNames.map((color, index) => ({
            name: color,
            value: colorValues[index], // 对应的颜色数量
          })),
          emphasis: {
            itemStyle: {
              shadowBlur: 10,
              shadowOffsetX: 0,
              shadowColor: "rgba(0, 0, 0, 0.5)",
            },
          },
        },
      ],
    };

    // 设置图表的选项
    myChart5.setOption(option5);
  } catch (error) {
    console.error("Error fetching car color data:", error);
  }
};

    // // 获取数据并绘制饼状图
    // const fetchDataAndDrawChart5 = async () => {
    //   try {
    //     // 调用 API 获取数据
    //     const response = await listCarcolor({});
    //     console.log(response);

    //     // 确保 response 是一个有效的数组
    //     if (!response || !Array.isArray(response)) {
    //       console.error("Invalid data format:", response);
    //       return; // 如果数据无效，直接返回
    //     }

    //     // 将数据格式化为 ECharts 饼状图所需的格式
    //     const chartData = response.map((item) => ({
    //       name: item.plateColor,
    //       value: item.count
    //     }));

    //     // 初始化图表
    //     const myChart = echarts.init(chart.value);
    //     const option5 = {
    //       title: {
    //         text: "车牌颜色分布",
    //         left: "center"
    //       },
    //       tooltip: {
    //         trigger: "item"
    //       },
    //       legend: {
    //         bottom: "10%",
    //         left: "center"
    //       },
    //       series: [
    //         {
    //           name: "车牌颜色",
    //           type: "pie",
    //           radius: "50%",
    //           data: chartData, // 使用格式化后的数据
    //           emphasis: {
    //             itemStyle: {
    //               shadowBlur: 10,
    //               shadowOffsetX: 0,
    //               shadowColor: "rgba(0, 0, 0, 0.5)"
    //             }
    //           }
    //         }
    //       ]
    //     };

    //     // 设置图表的选项
    //     myChart.setOption(option5);
    //   } catch (error) {
    //     console.error("Error fetching car color data:", error);
    //   }
    // };
</script>


<style scoped>
/* 1px--0.2vmin 
1rem--16px--3.2vmin

*/
.chartuser3 {
  width: 90%;
  /* 图表宽度占父容器的 90% */
  height: 300px;
  /* 设置图表高度 */
  max-width: 1000px;
  /* 限制最大宽度 */
  min-height: 300px;
  /* 限制最小高度 */
  box-sizing: border-box;
  /* 确保 padding 和 border 不影响宽高 */
}

.statisuser3 {
  width: 100%;
  /* 设置宽度为父容器的 100% */
  height: px;
  /* 设置高度 */
  display: flex;
  justify-content: center;
  /* 水平居中 */
  align-items: center;
  /* 垂直居中 */
  margin: 0 auto;
  /* 自动水平居中 */
}
.grid {
  margin-top: 5%;
  width: 80%;
  height: 10px;
  display: grid;
  grid-template-columns: repeat(20, 1fr);
  grid-column-gap: 14px;
}
.line {
  position: relative;
  width: 90%;
  height: 7px;
}

.line::before,
.line::after {
  content: "";
  position: absolute;
  width: 4.5rem;
  height: 100%;
  border-radius: 7px;
  background-color: #ffffff;
}

.line::before {
  animation: first-line ease-in-out 4s var(--delay) infinite alternate;
  transform-origin: left center;
}

.line::after {
  bottom: 0;
  background-color: #c3bdbd;
  width: calc(80% - 10px);
  animation: second-line ease-in-out 4s var(--delay) infinite alternate;
}

.line:nth-child(1) {
  --delay: calc(-0.1s);
}

@keyframes first-line {
  0% {
    transform: scaleX(0); /* 开始时横向缩放为 0 */
  }
  50% {
    transform: scaleX(1); /* 最大横向长度，恢复原始大小 */
  }
  100% {
    transform: scaleX(0.1); /* 缩回时保持一定的横向长度 */
  }
}
/* 原来的外联进来的 */

* {
  -webkit-box-sizing: border-box;
  -moz-box-sizing: border-box;
  box-sizing: border-box;
}
*,
body {
  padding: 0px;
  margin: 0px;
  color: #222;
  font-family: "微软雅黑";
}
body {
  background: #000d4a url(../assets/mainimages/bg.jpg) center top;
  background-size: cover;
  color: #666;
  padding-bottom: 30px;
  padding-top: 2vmin;
  font-size: 0.32vmin;
}
li {
  list-style-type: none;
}
table {
}
i {
  margin: 0px;
  padding: 0px;
  text-indent: 0px;
}
img {
  border: none;
  max-width: 100%;
}
a {
  text-decoration: none;
  color: #399bff;
}
a.active,
a:focus {
  outline: none !important;
  text-decoration: none;
}
ol,
ul,
p,
h1,
h2,
h3,
h4,
h5,
h6 {
  padding: 0;
  margin: 0;
}
a:hover {
  color: #06c;
  text-decoration: none !important;
}

/* 加载旋转动画 */
#load {
  width: 100%;
  height: 100%;
  position: absolute;
  background: url(@assets/mainimages/data08.png) no-repeat #061537;
  background-size: cover;
  top: 0;
  left: 0;
  z-index: 999;
}
#load .load_img {
  position: absolute;
  left: calc(50% - 182px);
  top: calc(50% - 182px);
}
.load_img img {
  position: absolute;
  left: 0;
  top: 0;
}
.load_img .jzxz1 {
  animation: xz1 8s infinite linear;
}
@keyframes xz1 {
  from {
    transform: rotate(0deg);
  }
  50% {
    transform: rotate(180deg);
  }
  to {
    transform: rotate(360deg);
  }
}
.load_img .jzxz2 {
  animation: xz2 7s infinite linear;
}
@keyframes xz2 {
  from {
    transform: rotate(0deg);
  }
  50% {
    transform: rotate(-180deg);
  }
  to {
    transform: rotate(-360deg);
  }
}

.clearfix:after,
.clearfix:before {
  display: table;
  content: " ";
}
.clearfix:after {
  clear: both;
}

.pulll_left {
  float: left;
}
.pulll_right {
  float: right;
}
/*谷哥滚动条样式*/

::-webkit-scrollbar {
  width: 5px;
  height: 5px;
  position: absolute;
}
::-webkit-scrollbar-thumb {
  background-color: #5bc0de;
}
::-webkit-scrollbar-track {
  background-color: #ddd;
}

/***/

.loading {
  position: fixed;
  left: 0;
  top: 0;
  font-size: 1vmin;
  z-index: 100000000;
  width: 100%;
  height: 100%;
  background: #1a1a1c;
  text-align: center;
}
.loadbox {
  position: absolute;
  width: 160px;
  height: 150px;
  color: #324e93;
  left: 50%;
  top: 50%;
  margin-top: -100px;
  margin-left: -75px;
}
.loadbox img {
  margin: 10px auto;
  display: block;
  width: 40px;
}

.copyright {
  background: rgba(19, 31, 64, 0.32);
  border: 1px solid rgba(255, 255, 255, 0.05);
  line-height: 0.5rem;
  text-align: center;
  padding-right: 15px;
  bottom: 0;
  color: rgba(255, 255, 255, 0.7);
  font-size: 0.16rem;
}
/* 头部 */
.head {
  height: 10vmin;
  background: url(../assets/mainimages/head_bg.png) no-repeat center center;
  background-size: 100% 100%;
  position: relative;
}
.head h1 {
  color: #fff;
  text-align: center;
  font-size: 5vmin;
  line-height: 6vmin;
}
.head h1 img {
  width: 1.5rem;
  display: inline-block;
  vertical-align: middle;
  margin-right: 0.2rem;
}
.weather {
  position: absolute;
  right: 1vmin;
  top: 0;
  line-height: 6vmin;
}
.weather img {
  width: 0.37rem;
  display: inline-block;
  vertical-align: middle;
}
.weather span {
  color: rgba(255, 255, 255, 0.7);
  font-size: 2vmin;
  padding-right: 0.32vmin;
}

.mainbox {
  padding: 0 5vmin 0rem 5vmin;
}
.mainbox > ul {
  margin-left: -0.4rem;
  margin-right: -0.4rem;
}
.mainbox > ul > li {
  float: left;
  padding: 0 0.4rem;
}
.mainbox > ul > li {
  width: 30%;
}
.mainbox > ul > li:nth-child(2) {
  width: 40%;
}

.boxall {
  border: 0.2vmin solid rgba(25, 186, 139, 0.17);
  padding: 0 1vmin 1vmin 1vmin;
  background: rgba(255, 255, 255, 0.04) url(../images/line.png);
  background-size: 100% auto;
  position: relative;
  margin-bottom: 3vmin;
  z-index: 20;
}
.boxall:before,
.boxall:after {
  position: absolute;
  width: 0.32vmin;
  height: 0.32vmin;
  content: "";
  border-top: 2vmin solid #02a6b5;
  top: 0;
}
.boxall:before,
.boxfoot:before {
  border-left: 0.1vmin solid #02a6b5;
  left: 0;
}
.boxall:after,
.boxfoot:after {
  border-right: 0.1vmin solid #02a6b5;
  right: 0;
}

.alltitle {
  font-size: 3vmin;
  color: #fff;
  text-align: center;
  line-height: 6vmin;
  border-bottom: 1px solid rgba(255, 255, 255, 0.2);
}

.boxfoot {
  position: absolute;
  bottom: 0;
  width: 100%;
  left: 0;
}
.boxfoot:before,
.boxfoot:after {
  position: absolute;
  width: 0.32vmin;
  height: 0.32vmin;
  content: "";
  border-bottom: 2vmin solid #02a6b5;
  bottom: 0;
}

.bar {
  background: rgba(101, 132, 226, 0.1);
  padding: 1vmin;
  margin: 0 3vmin;
}
.barbox li,
.barbox2 li {
  width: 50%;
  text-align: center;
  position: relative;
}
.barbox:before,
.barbox:after {
  position: absolute;
  width: 1vmin;
  height: 0.32vmin;
  content: "";
}
.barbox:before {
  border-left: 4vmin solid #02a6b5;
  left: 0;
  border-top: 0.1vmin solid #02a6b5;
}
.barbox:after {
  border-right: 4vmin solid #02a6b5;
  right: 0;
  bottom: 0;
  border-bottom: 0.1vmin solid #02a6b5;
}

.barbox li:first-child:before {
  position: absolute;
  content: "";
  height: 50%;
  width: 1px;
  background: rgba(255, 255, 255, 0.2);
  right: 0;
  top: 25%;
}

.barbox {
  border: 1px solid rgba(25, 186, 139, 0.17);
  position: relative;
}
/* 中间上面数字 */
.barbox li {
  font-size: 5vmin;
  color: #ffeb7b;
  padding: 0.05rem 0;
  font-family: Gotham, "Helvetica Neue", Helvetica, Arial, "sans-serif";
  font-weight: bold;
}
/* 中间上面描述字 */
.barbox2 li {
  font-size: 2vmin;
  color: #637c9f;
  padding-top: 0.32vmin;
}
/* 中间圆 */
.map {
  position: relative;
  height: 40vmin;
  z-index: 9;
}
.map4 {
  width: 100%;
  height: 30vmin;
  position: relative;
  /* left: -50%;
  top: 2%; */
  margin-top: 0.2rem;
  z-index: 5; /* 堆叠顺序 */
}
.map1,
.map2,
.map3 {
  position: absolute;
}
.map1 {
  width: 30vmin;
  z-index: 2;
  top: 50%;
  left: 50%;

  animation: myfirst2 15s infinite linear;
}
.map2 {
  width: 30vmin;
  top: 50%;
  left: 50%;
  z-index: 3;
  opacity: 0.2;
  animation: myfirst 10s infinite linear;
}
.map3 {
  width: 30vmin;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  /* top: 0.4rem;
  left: 2.13rem; */
  z-index: 1;
}

#echarts1,
#echarts2,
#echarts3,
#echarts6,
#echarts7,
#echarts8 {
  position: relative;
}
#echarts1:before,
#echarts2:before,
#echarts3:before,
#echarts6:before,
#echarts7:before,
#echarts8:before {
  position: absolute;
  content: "23124";
  width: 100%;
  text-align: center;
  bottom: 0.15rem;
  color: #fff;
  opacity: 0.7;
  font-size: 0.18rem;
}

#echarts1:before {
  content: "平均单客价";
}
#echarts2:before {
  content: "男消费";
}
#echarts3:before {
  content: "女消费";
}

#echarts6:before {
  content: "新增会员";
}
#echarts7:before {
  content: "新增领卡会员";
}
#echarts8:before {
  content: "新增消费会员";
}

.tabs {
  text-align: center;
  padding: 0.32vmin 0 0 0;
}
.tabs a {
  position: relative;
  display: inline-block;
  margin-left: 1px;
  padding: 0.05rem 0.2rem;
  color: #898989;
  transition: all 0.3s ease-out 0s;
  font-size: 14px;
}
.tabs li {
  display: inline-block;
}
.tabs a:after {
  position: absolute;
  width: 1px;
  height: 10px;
  background-color: rgba(255, 255, 255, 0.1);
  content: "";
  margin-left: 0;
  right: -1px;
  margin-top: 7px;
}
.tabs li a.active {
  border: 1px solid rgba(25, 186, 139, 0.17);
  background: rgba(255, 255, 255, 0.05);
  color: #fff;
}

.tit02 {
  text-align: center;
  margin: 0.32vmin 0;
  position: relative;
}
.tit02 span {
  border: 1px solid rgba(25, 186, 139, 0.17);
  letter-spacing: 2px;
  padding: 0.01rem 0.2rem;
  background: rgba(255, 255, 255, 0.05);
  font-size: 2vmin;
  color: #49bcf7;
}
.tit02:before,
.tit02:after {
  position: absolute;
  width: 26%;
  height: 1px;
  background: rgba(25, 186, 139, 0.2);
  content: "";
  top: 0.12rem;
}
.tit02:after {
  right: 0;
}
.tit02:before {
  left: 0;
}

.wrap {
  height: 2.54rem;
  overflow: hidden;
}
.wrap li {
  line-height: 0.42rem;
  height: 0.42rem;
  font-size: 0.18rem;
  text-indent: 0.24rem;
  margin-bottom: 0.32vmin;
}
.wrap li p {
  border: 1px solid rgba(25, 186, 139, 0.17);
  color: rgba(255, 255, 255, 0.6);
}
.sy {
  float: left;
  width: 33%;
  height: 2.2rem;
  margin-top: -0.25rem;
}

.adduser {
  height: 1.5rem;
  overflow: hidden;
}
.adduser li {
  height: 0.5rem;
}
.adduser img {
  width: 0.4rem;
  border-radius: 0.5rem;
  margin-right: 0.32vmin;
  display: inline-block;
  vertical-align: middle;
}
.adduser span {
  line-height: 0.5rem;
  font-size: 0.18rem;
  color: rgba(255, 255, 255, 0.6);
}
/* clearfix */
.sycm ul {
  margin-left: 1vmin;
  margin-right: 1vmin;
  padding: 1vmin 0;
}
.sycm li {
  float: left;
  width: 33.33%;
  text-align: center;
  position: relative;
}
.sycm li:before {
  position: absolute;
  content: "";
  height: 30%;
  width: 1px;
  background: rgba(255, 255, 255, 0.1);
  right: 0;
  top: 15%;
}
.sycm li:last-child:before {
  width: 0;
}
/* 数字和描述 */
.sycm li h2 {
  font-size: 4vmin;
  color: #ffff;
}
.sycm li span {
  font-size: 2vmin;
  color: #fff;
  opacity: 0.5;
}
/* 用户统计 */
.statisuser {
  width: 100%;
  text-align: center;
}
.chartuser {
  position: relative;
  width: 40vmin;
  height: 25vmin;
  margin: 5vmin auto; /* 使图表容器居中 */
  display: flex;
  justify-content: center;
  align-items: center;
}
@keyframes myfirst2 {
  from {
    transform: translate(-50%, -50%) scale(1.2) rotate(0deg);
  }
  to {
    transform: translate(-50%, -50%) scale(1.2) rotate(359deg);
  }
}

@keyframes myfirst {
  from {
    transform: translate(-50%, -50%) scale(1.2) rotate(0deg);
  }
  to {
    transform: translate(-50%, -50%) scale(1.2) rotate(-359deg);
  }
}

.fullscreen-btn {
  position: absolute;
  left: 1vmin;
  top: 50%;
  transform: translateY(-50%);
  padding: 1vmin 2vmin;
  background: rgba(25, 186, 139, 0.2);
  border: 1px solid rgba(25, 186, 139, 0.5);
  color: white;
  font-size: 2vmin;
  cursor: pointer;
  border-radius: 0.5vmin;
  transition: all 0.3s;
  z-index: 10;
}

.fullscreen-btn:hover {
  background: rgba(25, 186, 139, 0.4);
  transition: all 0.3s;
  z-index: 10;
}

.fullscreen-btn:hover {
  background: rgba(25, 186, 139, 0.4);
  box-shadow: 0 0 5px rgba(25, 186, 139, 0.5);
  color: #fff;
}



</style>