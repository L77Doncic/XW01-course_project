Page({

  /**
   * 页面的初始数据
   */
  data: {
    sheng:'wz',
    shi:'wz',
    qu:'wz',
    time:'2024-1-1',
    temp:'00',
    tianqi:'wz',
    fengxiang:'wz',
    dengji:'2',
    humi:'22',
    icon:'999',
    jiangshuiliang:'wz',
    yunliang:'wz',
    jiance:'wz',
    pa:'111110',
    pm:'33333',
    see:'44444',
    yundong:'',
    chuanyi:'',
    diaoyu:'',
    xiche:''
  },
  //获取天气
  getWeather(e){
    let that=this;
    wx.getLocation({
      type: 'wgs84',
      success (res) {
        console.log(res)
        const latitude = res.latitude
        const longitude = res.longitude
        const key='07f71319745340cab1054b93bda109ed'
        wx.request({
          url: `https://geoapi.qweather.com/v2/city/lookup?location=${longitude},${latitude}&key=${key}`,
          success(res){
            console.log(res.data.location[0])
            that.setData({
              sheng:res.data.location[0].adm1,
              shi:res.data.location[0].adm2,
              qu:res.data.location[0].name,
            })
            wx.request({
              url: `https://devapi.qweather.com/v7/weather/now?location=${longitude},${latitude}&key=${key}`,
              success(res){
                console.log(res.data.now)
                that.setData({
                  temp:res.data.now.temp,
                  tianqi:res.data.now.text,
                  fengxiang:res.data.now.windDir,
                  dengji:res.data.now.windScale,
                  humi:res.data.now.humidity,
                  jiangshuiliang:res.data.now.precip,
                  yunliang:res.data.now.cloud,
                  pa:res.data.now.pressure,
                  see:res.data.now.vis,
                  icon:res.data.now.icon,
                  time:res.data.updateTime,
                })
                wx.request({
                  url: `https://devapi.qweather.com/v7/indices/1d?type=0&location=${longitude},${latitude}&key=${key}`,
                  success(res){
                    console.log(res);
                    that.setData({
                      jiance:res.data.daily[4].category,
                      pm:res.data.daily[7].category,
                      yundong:res.data.daily[0].category,
                      chuanyi:res.data.daily[2].category,
                      diaoyu:res.data.daily[3].category,
                      xiche:res.data.daily[1].category,
                    })
                  }
                })
              }
            })
          }
        })
      },
      fail:function(res){
          console.log(res)
      }
      
      
     })


  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    this.getWeather()
  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {
    
  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {
    
  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function () {
    
  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload: function () {
    
  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function () {
    
  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () {
    
  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {
    
  }
})