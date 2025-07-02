//index.js
//获取应用实例
var app = getApp()
 console.log("这里"+app.globalData.markID);
 let username='';
 let password='';
Page({
  data: {
    username: '',
    password: '',
    clientHeight:'',
    qrcodeUrl:'', //二维码图片地址
    showQrcode: false, //是否显示二维码
    scanStatus: false,   // 扫码状态
    timer: null,         // 轮询定时器
    wxLoginConfig: {
      appId: 'wxe665f52060db5e0b', // 请替换为您的小程序 AppID
      scope: 'snsapi_login',
      state: 'wxlogin'
    }
  },

  onLoad(){
    var that=this
    wx.getSystemInfo({ 
      success: function (res) { 
        console.log(res.windowHeight)
          that.setData({ 
              clientHeight:res.windowHeight
        }); 
      } 
    }) 
  },

  onUnload() {
    // 页面卸载时清除定时器
    if (this.data.timer) {
      clearInterval(this.data.timer)
    }
  },

  // 初始化微信二维码
  initWechatQrcode() {
    const that = this
    wx.showLoading({ title: '生成二维码中' })

    // 这里需要替换为您的后端服务地址
    wx.request({
      url: 'http://localhost:9999/TeachDemo_war_exploded/wx/qrcode',
      method: 'POST',
      data: {
        appid: that.data.wxLoginConfig.appId,
        scope: that.data.wxLoginConfig.scope,
        state: that.data.wxLoginConfig.state
      },
      success(res) {
        if (res.data.code === 0) {
          that.setData({
            qrcodeUrl: res.data.data.qrcode_url,
            showQrcode: true,
            scanId: res.data.data.scan_id
          })
          that.startPolling() // 开始轮询
        } else {
          wx.showToast({
            title: '获取二维码失败',
            icon: 'none'
          })
        }
      },
      fail() {
        wx.showToast({
          title: '网络错误',
          icon: 'none'
        })
      },
      complete() {
        wx.hideLoading()
      }
    })
  },
  
  // 开始轮询扫码状态
  startPolling() {
    const that = this
    this.data.timer = setInterval(() => {
      wx.request({
        url: `http://localhost:9999/TeachDemo_war_exploded/wx/check_scan`,
        method: 'POST',
        data: {
          scan_id: that.data.scanId
        },
        success(res) {
          if (res.data.code === 0) {
            if (res.data.data.status === 'CONFIRMED') {
              clearInterval(that.data.timer)
              that.handleLoginSuccess(res.data.data.user_info)
            } else if (res.data.data.status === 'EXPIRED') {
              clearInterval(that.data.timer)
              wx.showToast({ 
                title: '二维码已过期，请重新获取', 
                icon: 'none' 
              })
              that.setData({
                showQrcode: false
              })
            }
          }
        },
        fail() {
          clearInterval(that.data.timer)
          wx.showToast({
            title: '网络错误',
            icon: 'none'
          })
        }
      })
    }, 3000) // 每3秒轮询一次
  },
  
  // 处理登录成功
  handleLoginSuccess(userInfo) {
    app.globalData.markID = userInfo.username
    wx.showToast({
      title: '登录成功',
      icon: 'success',
      duration: 1500
    })
    setTimeout(() => {
      wx.switchTab({
        url: '/pages/index/index'
      })
    }, 1500)
  },

  //获取输入款内容
  account(e){
    username=e.detail.value
  },
  password(e){
    password=e.detail.value
  },
  //登录事件
  goadmin(){
    let that=this;
    if(username=='')
    {
      wx.showToast({
        icon:'none',
        title: '账号不能为空',
      })
    }else if(password==''){
      wx.showToast({
        icon:'none',
        title: '密码不能为空',
      });
      console.log("输入的用户账号是："+username);
    }else{
      console.log("输入的用户账号是："+username);
      console.log("输入的用户密码是："+password);
      wx.request({
        url: 'http://localhost:9999/TeachDemo_war_exploded/device_data_servlet_action?action=user_info',
        data:{},
        header:{"content-type":"application/x-www-form-urlencoded","x-requested-with":"XMLHttpRequest"},
        success:function(res){
          that.handle_login(res);
        },
        fail:function(res){

        }
      })
    }
  },
  Go_register:function(){
    wx.navigateTo({
      url: '/pages/register/register',
    })
  },
  Go_password:function(){
    wx.navigateTo({
      url: '/pages/password/forget_password',
    })
  }
  ,
  handle_login(res){
    let flag=false;
    console.log(JSON.stringify(res));
    let admin=res.data.aaData
    console.log(admin);
          for (let i = 0; i < admin.length; i++) {  //遍历数据库对象集合
            if (username === admin[i].user_name) { //账户已存在
              flag=true;
              if (password !== admin[i].password) {  //判断密码正确与否
                wx.showToast({  //显示密码错误信息
                  title: '密码错误！！',
                  icon: 'error',
                  duration: 2500
                });
               break;
              } else {
                app.globalData.markID=username;
                //app.globalData.markID
                wx.showToast({  //显示登录成功信息
                  title: '登陆成功！！',
                  icon: 'success',
                  duration: 2500
                })
                flag=true;
                wx.setStorageSync('admin', password);
                wx.switchTab({
                  url: '/pages/index/index',
                })
                break;
              }
            }
          };
          if(flag==false)//遍历完数据后发现没有该账户
          {
            wx.showToast({
              title: '该用户不存在',
              icon: 'error',
              duration: 2500
            })
          }
  }
})
 
