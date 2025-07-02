
Page({
  data: {
    user_id:0,
    type:"sensor",
    title: "",
    limittime:'',
    begintime:'',
    creator:'',
    email:'',
    phonenumber:'',
    number:0,
    car_number:'',
    type:'',
    traffic_type:'',
    time:'',
    speed:''
  },
  onLoad: function (options) {
    this.setId();
  },
  setId:function(){
    console.log("开始设置ID");
    let that=this
    wx.request({
      url: 'http://localhost:9999/TeachDemo_war_exploded/device_data_servlet_action?action=vehicle_car',
      data:{},
      header:{"content-type":"application/x-www-form-urlencoded","x-requested-with":"XMLHttpRequest"},
      success:function(res){
        that.handleId(res);
      },
      fail:function(res){
      }
    })
  },
  handleId:function(res){
    console.log(res.data);
    this.setData({
      number:res.data.aaData.length,
    })
    console.log(this.data.number);
  },
  inputspeed: function (e) {
    this.setData({
      speed: e.detail.value,
    });
  },
  inputcarnumber: function (e) {
    this.setData({
      car_number: e.detail.value,
    });
  },
  inputtype: function (e) {
    this.setData({
      type: e.detail.value,
    });
  },
  inputtraffic_type: function (e) {
    this.setData({
      traffic_type: e.detail.value,
    });
  },
  inputtime: function (e) {
    this.setData({
      time: e.detail.value,
    });
  },
  AddRecord: function () {
    if (this.checkInput()) {
      let that = this;
      var id=this.data.number;
      var title=this.data.title;
      var content=this.data.content;
      var begintime=this.data.begintime;
      var limittime=this.data.limittime;
      var creator=this.data.creator;
      var car_number=this.data.car_number;
      var type=this.data.type;
      var traffic_type=this.data.traffic_type;
      var time=this.data.time;
      var speed=this.data.speed;
      wx.request({
        url: 'http://localhost:9999/TeachDemo_war_exploded/device_data_servlet_action?action=add_car_record',
        data: { "id": id+1,"car_number":car_number,"type":type,"traffic_type":traffic_type,"time":time,"speed":speed},
        header: { "content-type": "application/x-www-form-urlencoded", "x-requested-with": "XMLHttpRequest", },
        success: function (res) {
          //that.handleAddTodoRecordResult(res);
          wx.showToast({
            title: '已经添加完毕！',
            icon: "none",
            duration: 2000,
            success: function (res) {
              setTimeout(function () {
                //要延时执行的代码
                wx.navigateBack({ delta: 1 });
              }, 2000) //延迟时间
            }
          })
        },
        fail: function (res) {
        }
      })
    }
  },
  checkInput:function(){
    var ok=true;
    console.log(this.data.title);
    if(this.data.car_number==undefined || this.data.car_number==null || this.data.car_number==""){
      wx.showToast({
        title: '请您输入车牌号！',
        icon:'none',
      })
      ok=false;
    }
    return ok;
  },
  getCurrentDateTime: function () {
    var dateTimeStr = this.data.dateTimeArray[0][this.data.dateTime[0]] + "-" + this.data.dateTimeArray[1][this.data.dateTime[1]] + "-" + this.data.dateTimeArray[2][this.data.dateTime[2]];
    dateTimeStr = dateTimeStr + " " + this.data.dateTimeArray[3][this.data.dateTime[3]] + "-" + this.data.dateTimeArray[4][this.data.dateTime[4]] + "-" + this.data.dateTimeArray[5][this.data.dateTime[5]];
    return dateTimeStr;
  },
})