//app.js
App({
  onLaunch: function () {
    try {
      // 获取本地缓存
      const logs = wx.getStorageSync('logs') || [];
      logs.unshift(Date.now());
      wx.setStorageSync('logs', logs);
      
      // 检查更新
      if (wx.canIUse('getUpdateManager')) {
        const updateManager = wx.getUpdateManager();
        updateManager.onCheckForUpdate(function (res) {
          if (res.hasUpdate) {
            updateManager.onUpdateReady(function () {
              wx.showModal({
                title: '更新提示',
                content: '新版本已经准备好，是否重启应用？',
                success: function (res) {
                  if (res.confirm) {
                    updateManager.applyUpdate();
                  }
                }
              });
            });
          }
        });
      }
    } catch (e) {
      console.error('应用初始化错误：', e);
    }
  },
  onShow: function(){
    //启动小程序或者从后台进入前台
  },
  onHide: function(){
    //从前台进入后台
  },
  getUserProfile: function (cb) {
    const that = this;
    if (this.globalData.userInfo) {
      typeof cb === "function" && cb(this.globalData.userInfo);
    } else {
      // 使用新的getUserProfile接口
      wx.getUserProfile({
        desc: '用于完善用户资料',
        success: (res) => {
          that.globalData.userInfo = res.userInfo;
          typeof cb === "function" && cb(that.globalData.userInfo);
        },
        fail: (err) => {
          console.error('获取用户信息失败：', err);
          wx.showToast({
            title: '获取用户信息失败',
            icon: 'none'
          });
        }
      });
    }
  },
  globalData:{
    //全局信息
    userInfo:null,
    markID:"",
    version: '1.0.0'
  },
})