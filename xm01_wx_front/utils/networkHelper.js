// 网络诊断和测试工具
const networkHelper = {
  // 测试网络连接
  testNetworkConnection: function(url, callback) {
    wx.request({
      url: url,
      method: 'GET',
      timeout: 5000,
      success: function(res) {
        console.log('网络连接测试成功:', res);
        callback && callback(true, res);
      },
      fail: function(err) {
        console.error('网络连接测试失败:', err);
        callback && callback(false, err);
      }
    });
  },

  // 获取本机IP地址（需要后端支持）
  getLocalIP: function(callback) {
    wx.request({
      url: 'https://api.ipify.org?format=json',
      method: 'GET',
      success: function(res) {
        console.log('获取IP地址成功:', res.data.ip);
        callback && callback(res.data.ip);
      },
      fail: function(err) {
        console.error('获取IP地址失败:', err);
        callback && callback(null);
      }
    });
  },

  // 检查网络状态
  checkNetworkStatus: function(callback) {
    wx.getNetworkType({
      success: function(res) {
        console.log('网络类型:', res.networkType);
        callback && callback(res.networkType);
      },
      fail: function(err) {
        console.error('获取网络状态失败:', err);
        callback && callback('unknown');
      }
    });
  },

  // 显示网络诊断信息
  showNetworkDiagnosis: function() {
    this.checkNetworkStatus((networkType) => {
      let message = `网络类型: ${networkType}\n`;
      
      if (networkType === 'none') {
        message += '当前无网络连接，请检查网络设置';
      } else if (networkType === 'wifi') {
        message += '当前使用WiFi网络，请确保手机和电脑在同一网络';
      } else {
        message += '当前使用移动网络，建议切换到WiFi网络进行测试';
      }
      
      wx.showModal({
        title: '网络诊断',
        content: message,
        showCancel: false
      });
    });
  }
};

module.exports = networkHelper; 