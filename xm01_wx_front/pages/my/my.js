Page({
  data: {
    navBarHeight: wx.getSystemInfoSync()['statusBarHeight'],
    userlist: [],
    user_name:"",
    user_ID:"",
    user_email:"",
    user_level:"",
    user_time:"",
    nick_name:''
  },
  onLoad: function () { //加载数据渲染页面
    this.getTodoRecord();
  },
  getTodoRecord:function(){
    let that=this;
    wx.request({
      url: 'http://10.133.224.121:9999/user/info',
      method: 'GET',
      header: {
        'token': wx.getStorageSync('token')
      },
      success: function (res) {
        if (res.data.code === 200) {
          that.setData({
            user_ID: res.data.data.id,
            user_name: res.data.data.userName,
            user_email: res.data.data.email,
            user_level: res.data.data.phone,
            user_time: res.data.data.createTime,
            nick_name: res.data.data.nickName
          })
        }
      }
    })
  },
  onCopy: function () {
    var that = this;
    wx.setClipboardData({
      data: that.data.user_ID,
      success: function (res) {
        wx.showToast({
          title: '复制成功',
        })
      }
    })
  },
  detailClick: function () {
    wx.showModal({
      title: '提示',
      content: '确认支付￥99成为管理员？',
      success: function (res) {
        if (res.confirm) {
            wx.showToast({
            title: '支付成功',
            icon: 'success',
            duration: 2000
            })
          }
      }
    })
  },
  Go_Modify: function () {
    var id = this.data.user_ID;
    wx.navigateTo({
      url: '/pages/space/user_modify?record='+id,
    })
  }
})