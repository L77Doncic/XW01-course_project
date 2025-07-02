
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
  },
  onLoad: function (options) {
    this.setId();
  },
  setId:function(){
    console.log("开始设置ID");
    let that=this
    wx.request({
      url: 'http://localhost:9999/TeachDemo_war_exploded/device_data_servlet_action?action=get_device_record',
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
  inputtitle: function (e) {
    this.setData({
      title: e.detail.value,
    });
  },
  inputcontent: function (e) {
    this.setData({
      content: e.detail.value,
    });
  },
  inputbegintime: function (e) {
    this.setData({
      begintime: e.detail.value,
    });
  },
  inputlimittime: function (e) {
    this.setData({
      limittime: e.detail.value,
    });
  },
  inputcreator: function (e) {
    this.setData({
      creator: e.detail.value,
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
      wx.request({
        url: 'http://localhost:9999/TeachDemo_war_exploded/device_data_servlet_action?action=add_todo_record',
        data: { "id": id,"title":title,"content":content,"limittime":limittime,"begintime":begintime,"creator":creator},
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
    if(this.data.title==undefined || this.data.title==null || this.data.title==""){
      wx.showToast({
        title: '请您输入代办名称！',
        icon:'none',
      })
      ok=false;
    }
    if(this.data.content==undefined || this.data.content==null || this.data.content==""){
      wx.showToast({
        title: '请您输入代办内容！',
        icon:'none',
      })
      ok=false;
    }
    if(this.data.limittime==undefined || this.data.limittime==null || this.data.limittime==""){
      wx.showToast({
        title: '请您输入截止日期！',
        icon:'none',
      })
      ok=false;
    }
    if(this.data.begintime==undefined || this.data.begintime==null || this.data.begintime==""){
      wx.showToast({
        title: '请您输入开始日期！',
        icon:'none',
      })
      ok=false;
    }
    if(this.data.creator==undefined || this.data.creator==null || this.data.creator==""){
      wx.showToast({
        title: '请您输入创始人！',
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