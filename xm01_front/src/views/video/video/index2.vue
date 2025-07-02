<template>
  <div id="app" class="app">
    <h1>视频播放器</h1>
    <!-- 文件选择 -->
    <input type="file" @change="onFileChange" accept="video/*" />

    <!-- 成功提示 -->
    <div v-if="uploadSuccess" class="alert alert-success">上传成功！</div>

    <div class="video-section">
      <!-- 左侧视频框 -->
      <div v-if="videoVisible && processedVideoURL" class="video-container">
        <h3>处理后视频</h3>
        <video controls width="400" ref="videoPlayerLeft" key="leftVideoKey">
          <source :src="processedVideoURL" type="video/mp4" />
          您的浏览器不支持 HTML5 视频标签。
        </video>
      </div>

      <!-- 右侧视频框 -->
      <div v-if="videoVisible && originalVideoURL" class="video-container">
        <h3>原始视频</h3>
        <video controls width="400" ref="videoPlayerRight" key="rightVideoKey">
          <source :src="originalVideoURL" type="video/mp4" />
          您的浏览器不支持 HTML5 视频标签。
        </video>
      </div>
    </div>

    <!-- 开始识别按钮 -->
    <button
      v-if="originalVideoURL && !isLoading"
      @click="startRecognition"
      class="recognition-button"
    >
      开始识别
    </button>

    <!-- 缓冲动画 -->
    <div v-if="isLoading" class="loading">
      <img src="./images/loading.gif" alt="loading" />
    </div>

    <!-- 处理完成提示 -->
    <div v-if="recognitionComplete" class="alert alert-complete">
      处理完成！
    </div>

    <!-- 视频播放视图下的车流量图 -->
    <div v-if="videoVisible" class="traffic-chart-container">
      <h3>实时车流量图</h3>
      <traffic-chart
        ref="trafficChart"
        :chart-data="chartData"
        :chart-options="chartOptions"
      ></traffic-chart>
    </div>
  </div>
</template>

<script>
import { Line } from "vue-chartjs"; // 引入vue-chartjs的组件
import {
  Chart as ChartJS, // 引入ChartJS的核心
  Title,
  Tooltip,
  Legend,
  LineElement,
  LinearScale,
  PointElement,
  CategoryScale,
  Filler,
} from "chart.js"; // 注册折线图绘图模块

// 注册Chart.js的必备模块
ChartJS.register(
  Title,
  Tooltip,
  Legend,
  LineElement,
  LinearScale,
  PointElement,
  CategoryScale,
  Filler
);

export default {
  name: "App",
  components: {
    TrafficChart: {
      // 定义流量图表作为子组件
      name: "TrafficChart",
      components: { Line },
      props: {
        chartData: { type: Object, required: true }, // 数据
        chartOptions: { type: Object, required: true }, // 配置
      },
      template: `<Line :chart-data="chartData" :options="chartOptions" />`, // 使用 Line 组件
    },
  },
  data() {
    return {
      // 原有数据
      originalVideoURL: null, // 原始视频URL
      processedVideoURL: null, // 处理后视频URL
      videoVisible: false, // 控制视频框渲染
      leftVideoKey: 0, // 强制刷新左视频框
      rightVideoKey: 0, // 强制刷新右视频框
      isLoading: false, // 开始识别时的加载状态
      uploadSuccess: false, // 上传成功提示
      recognitionComplete: false, // 识别完成提示
      resultVideoURL: "", // 假设结果视频
      previousVideoURL: null, // 上一个视频 URL
      // 新增车流量相关数据
      trafficFlow: 0, // 当前车流量
      chartData: {
        labels: [], // 时间标签
        datasets: [
          {
            label: "车流量",
            data: [], // 车流量动态数据
            borderColor: "#42a5f5",
            backgroundColor: "rgba(66, 165, 245, 0.2)",
            fill: true, // 允许填充颜色
          },
        ],
      },
      chartOptions: {
        responsive: true, // 支持响应式
        maintainAspectRatio: false, // 不保持宽高比
        plugins: {
          legend: { position: "top" }, // 图例位置
          title: { display: true, text: "实时车流量图" }, // 图表标题
        },
        scales: {
          x: {
            ticks: { color: "#666" }, // X轴样式
          },
          y: {
            ticks: { color: "#666" }, // Y轴样式
          },
        },
      },
      trafficUpdateInterval: null, // 刷新流量图表的定时器 ID
    };
  },
  watch: {
    /**
     * 监听原始视频 URL 的变化
     */
    originalVideoURL(newValue) {
      if (newValue) {
        // 视频加载后绑定播放事件监听器
        this.$nextTick(() => {
          this.monitorVideoPlayback();
        });
      }
    },
  },
  beforeDestroy() {
    // 组件销毁前清理定时器
    this.stopTrafficFlowUpdate();
  },
  methods: {
    // ------- 原有方法 ------ //

    /**
     * 上传视频处理
     */
    onFileChange(event) {
      const file = event.target.files[0];
      if (file) {
        if (this.previousVideoURL) {
          URL.revokeObjectURL(this.previousVideoURL); // 清理旧的 URL
        }

        const fileName = file.name; // 获取文件名
        console.log("上传的文件名：", fileName); // 在控制台输出文件名

        if (file.name === "2.mp4") {
          this.resultVideoURL = "/2.mp4";
        } else if (file.name === "1.mp4") {
          this.resultVideoURL = "/1.mp4";
        }

        const newVideoURL = URL.createObjectURL(file); // 创建新的视频URL
        this.originalVideoURL = newVideoURL; // 设置原始视频URL
        this.previousVideoURL = newVideoURL; // 保存 URL 以便后续清理

        this.processedVideoURL = null; // 清空处理后视频URL
        this.uploadSuccess = true; // 显示上传成功提示
        this.videoVisible = false; // 隐藏视频播放框

        this.leftVideoKey += 1;
        this.rightVideoKey += 1;

        this.$nextTick(() => {
          this.videoVisible = true; // 重置视频框
        });

        // 自动隐藏上传成功提示
        setTimeout(() => {
          this.uploadSuccess = false;
        }, 2000);
      }
    },

    /**
     * 模拟识别视频
     */
    startRecognition() {
      if (!this.originalVideoURL) return;

      this.isLoading = true; // 显示加载动画

      setTimeout(() => {
        this.isLoading = false; // 隐藏加载动画
        this.recognitionComplete = true; // 显示处理完成提示
        this.processedVideoURL = this.resultVideoURL; // 识别结果视频

        this.leftVideoKey += 1; // 强制刷新识别视频

        // 自动隐藏处理完成提示
        setTimeout(() => {
          this.recognitionComplete = false;
        }, 2000);
      }, 3000); // 模拟3秒识别延迟
    },

    // ------- 新增方法 ------ //

    /**
     * 启动车流量更新（定时器模拟数据）
     */
    startTrafficFlowUpdate() {
      if (this.trafficUpdateInterval) return; // 如果定时器已经存在则忽略

      this.trafficUpdateInterval = setInterval(() => {
        const videoPlayer = this.$refs.videoPlayerRight; // 获取右侧视频播放框
        if (videoPlayer && !videoPlayer.paused) {
          // 模拟新车流量数据
          const newTrafficFlow = Math.floor(Math.random() * 100);

          // 更新 Chart 数据
          this.chartData.labels.push(new Date().toLocaleTimeString());
          this.chartData.datasets[0].data.push(newTrafficFlow);

          // 保持最多显示 10 个点
          if (this.chartData.labels.length > 10) {
            this.chartData.labels.shift();
            this.chartData.datasets[0].data.shift();
          }
        }
      }, 1000); // 每秒更新一次
    },

    /**
     * 停止车流量更新
     */
    stopTrafficFlowUpdate() {
      clearInterval(this.trafficUpdateInterval); // 停止更新
      this.trafficUpdateInterval = null;
    },

    /**
     * 监控视频播放事件，触发车流量更新或停止
     */
    monitorVideoPlayback() {
      const videoPlayer = this.$refs.videoPlayerRight; // 对右侧视频播放框进行监听
      if (videoPlayer) {
        // 视频播放
        videoPlayer.addEventListener("play", this.startTrafficFlowUpdate);
        // 视频暂停或播放结束
        videoPlayer.addEventListener("pause", this.stopTrafficFlowUpdate);
        videoPlayer.addEventListener("ended", this.stopTrafficFlowUpdate);
      }
    },
  },
};
</script>

<style scoped>
.app {
  text-align: center;
  margin-top: 50px;
}

/* 视频区域布局 */
.video-section {
  display: flex;
  justify-content: space-evenly;
  margin-top: 20px;
}

.traffic-chart-container {
  margin-top: 20px;
  width: 80%;
  max-width: 600px;
  height: 400px;
  margin-left: auto;
  margin-right: auto;
}

.video-container {
  text-align: center;
}

/* 默认按钮样式 */
button {
  margin-top: 20px;
  padding: 10px 20px;
  font-size: 16px;
  background-color: #007bff;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

/* 鼠标悬浮效果 */
button:hover {
  background-color: #0056b3;
}

/* 加载动画样式 */
.loading {
  margin-top: 20px;
}

.loading img {
  width: 50px;
  height: 50px;
}

/* 更好地适配不同分辨率的视频 */
video {
  max-width: 100%;
  height: auto;
}

/* 提示框样式 */
.alert {
  margin-top: 20px;
  padding: 10px;
  font-size: 16px;
  border-radius: 5px;
  display: inline-block;
}

.alert-success {
  background-color: #d4edda;
  color: #155724;
  border: 1px solid #c3e6cb;
}

.alert-complete {
  background-color: #cce5ff;
  color: #004085;
  border: 1px solid #b8daff;
}
</style>
