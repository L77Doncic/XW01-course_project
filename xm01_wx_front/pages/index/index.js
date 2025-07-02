//index.js
//获取应用实例
var app = getApp()
Page({
  data: {
    indexmenu:[],
    imgUrls: []
  },
  onLoad:function(options){
    //生命周期函数--监听页面加载
    this.fetchData();
    console.log("这里"+app.globalData.markID);

  },
  fetchData:function(){
    this.setData({
      indexmenu:[
        {
          'icon':'./../../images/icon_05.png',
          'text':'用户管理',
          'url':'space'
        },
        {
          'icon':'./../../images/icon_07.png',
          'text':'天气预报',
          'url':'conference'
        },
        {
          'icon':'./../../images/icon_09.png',
          'text':'待办与投诉',
          'url':'resource'
        }
      ],
      imgUrls: [
        '../../images/index_pic01.jpg',
        '../../images/index_pic02.jpg',
        '../../images/index_pic03.jpg'
      ]
    })
  },
  changeRoute:function(url){
    wx.navigateTo({
      url: `../${url}/${url}`
    })
  },
  onReady:function(){
    //生命周期函数--监听页面初次渲染完成
    // console.log('onReady');
  },
  onShow :function(){
    //生命周期函数--监听页面显示
    // console.log('onShow');
  },
  onHide :function(){
    //生命周期函数--监听页面隐藏
    // console.log('onHide');
  },
  onUnload :function(){
    //生命周期函数--监听页面卸载
    // console.log('onUnload');
  },
  onPullDownRefresh:function(){
    //页面相关事件处理函数--监听用户下拉动作
    // console.log('onPullDownRefresh');
  },
  onReachBottom:function(){
    //页面上拉触底事件的处理函数
    // console.log('onReachBottom');
  }
})
