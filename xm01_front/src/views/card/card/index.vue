<template>
  <div id="app" class="app">
    <h1>车牌号检测</h1>
    <!-- 文件选择 -->
    <input type="file" @change="onFileChange" accept="video/*" />

    <!-- 成功提示 -->
    <div v-if="uploadSuccess" class="alert alert-success">上传成功！</div>

    <div class="video-section">
      <!-- 左侧视频框 -->
      <div v-if="videoVisible && processedVideoURL1" class="video-container">
        <h3>处理后视频(车牌号检测)</h3>
        <video controls width="800" ref="videoPlayerLeft" key="leftVideoKey">
          <source :src="processedVideoURL1" type="video/mp4" />
          您的浏览器不支持 HTML5 视频标签。
        </video>
      </div>

      <!-- <div v-if="videoVisible && processedVideoURL2" class="video-container">
        <h3>处理后视频(车牌号检测)</h3>
        <video controls width="400" ref="videoPlayerLeft2" key="leftVideoKey2">
          <source :src="processedVideoURL2" type="video/mp4" />
          您的浏览器不支持 HTML5 视频标签。
        </video>
      </div> -->

      <!-- 右侧视频框 -->
      <div v-if="videoVisible && originalVideoURL" class="video-container">
        <h3>原始视频</h3>
        <video controls width="800" ref="videoPlayerRight" key="rightVideoKey">
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
  </div>
</template>

<script>
export default {
  name: "App",
  data() {
    return {
      originalVideoURL: null, // 原始视频 URL（右侧视频框）
      processedVideoURL1: null, // 处理后视频 URL（左侧视频框）
      // processedVideoURL2: null, // 处理后视频 URL（左侧视频框）
      videoVisible: false, // 控制视频框渲染
      leftVideoKey: 0, // 用于强制刷新左侧视频框
      // leftVideoKey2: 0,
      rightVideoKey: 0, // 用于强制刷新右侧视频框
      isLoading: false, // 是否正在缓冲/识别
      uploadSuccess: false, // 上传成功提示
      recognitionComplete: false, // 识别完成提示
      resultVideoURL1: null,
      // resultVideoURL2: null,
      previousVideoURL: null, // 上一个视频 URL（用于内存释放）
    };
  },
  methods: {
    // 上传新视频时的处理
    onFileChange(event) {
      const file = event.target.files[0]; // 获取选择的文件
      if (file) {
        // 清理之前的视频 URL
        if (this.previousVideoURL) {
          URL.revokeObjectURL(this.previousVideoURL);
        }

        const fileName = file.name; // 获取文件名
        console.log("上传的文件名：", fileName); // 在控制台输出文件名

        if (file.name === "2.mp4") {
          this.resultVideoURL1 = "/card/java2.mp4";
        } else if (file.name === "1.mp4") {
          // this.resultVideoURL1 = "/1.mp4";
          this.resultVideoURL1 = "/card/java1.mp4";
        } else if (file.name === "3.mp4") {
          // this.resultVideoURL1 = "/3.mp4";
          this.resultVideoURL1 = "/card/java3.mp4";
        } else if (file.name === "4.mp4") {
          // this.resultVideoURL1 = "/4.mp4";
          this.resultVideoURL1 = "/card/java4.mp4";
        } else if (file.name === "5.mp4") {
          // this.resultVideoURL1 = "/5.mp4";
          this.resultVideoURL1 = "/card/java5.mp4";
        } else if (file.name === "6.mp4") {
          // this.resultVideoURL1 = "/6.mp4";
          this.resultVideoURL1 = "/card/java6.mp4";
        } else if (file.name === "7.mp4") {
          // this.resultVideoURL1 = "/7.mp4";
          this.resultVideoURL1 = "/card/java7.mp4";
        } else if (file.name === "8.mp4") {
          // this.resultVideoURL1 = "/8.mp4";
          this.resultVideoURL1 = "/card/java8.mp4";
        }
        // console.log(window.location.href); // 当前页面完整的 URL

        // 更新原始视频的 URL
        const newVideoURL = URL.createObjectURL(file);
        this.originalVideoURL = newVideoURL; // 原始视频
        this.previousVideoURL = newVideoURL; // 保存为 previousVideoURL

        // 初始化处理后的视频 URL 为 null
        this.processedVideoURL1 = null;
        // this.processedVideoURL2 = null;

        // 更新状态和强制刷新
        this.uploadSuccess = true;
        this.videoVisible = false;
        this.leftVideoKey += 1; // 刷新左视频框
        // this.leftVideoKey2 += 1; // 刷新左视频框
        this.rightVideoKey += 1; // 刷新右视频框

        this.$nextTick(() => {
          this.videoVisible = true;
        });

        // 自动隐藏上传成功提示
        setTimeout(() => {
          this.uploadSuccess = false;
        }, 2000);
      }
    },

    // 点击“开始识别”按钮时的处理
    startRecognition() {
      if (!this.originalVideoURL) return; // 没有原始视频时不执行

      this.isLoading = true; // 显示缓冲动画

      setTimeout(() => {
        // 模拟识别过程完成
        this.isLoading = false; // 隐藏加载动画
        this.recognitionComplete = true; // 显示完成提示
        console.log("+++", this.originalVideoURL);

        // 将左侧的视频切换为识别后的视频结果
        this.processedVideoURL1 = this.resultVideoURL1; // 模拟识别后返回结果的视频地址
        console.log("+++", this.processedVideoURL1);

        // this.processedVideoURL2 = this.resultVideoURL2;
        // console.log("+++", this.processedVideoURL2);

        this.leftVideoKey += 1; // 强制刷新左侧视频框
        // this.leftVideoKey2 += 1; // 刷新左视频框

        // 右侧视频仍显示原始的上传视频，URL 不变

        // 自动隐藏完成提示
        setTimeout(() => {
          this.recognitionComplete = false;
        }, 2000); // 提示显示 2 秒后隐藏
      }, 3000); // 模拟识别时延 3 秒
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
