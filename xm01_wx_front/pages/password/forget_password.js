
Page({
  data: {
    user_id:0,
    type:"sensor",
    title: "",
    user_name:'',
    nick_name:'',
    password:'',
    email:'',
    phonenumber:'',
    number:0,
  },
  onLoad: function (options) {
    this.setId();
  },
  setId:function(){
    console.log("开始设置用户ID");
    let that=this
    wx.request({
      url: 'http://localhost:9999/TeachDemo_war_exploded/device_data_servlet_action?action=user_info',
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
  inputphonenumber: function (e) {
    this.setData({
      phonenumber: e.detail.value,
    });
  },
  inputemail: function (e) {
    this.setData({
      email: e.detail.value,
    });
  },
  inputusername: function (e) {
    this.setData({
      user_name: e.detail.value,
    });
  },
  inputpassword: function (e) {
    this.setData({
      password: e.detail.value,
    });
  },
  inputnickname: function (e) {
    this.setData({
      nick_name: e.detail.value,
    });
  },
  AddRecord: function () {
    if (this.checkInput()) {
      let that = this;
      var id=this.data.number;
      var user_name=this.data.user_name;
      var nick_name=this.data.nick_name;
      var password=this.data.password;
      var email=this.data.email;
      var phonenumber=this.data.phonenumber;
      wx.request({
        url: 'http://localhost:9999/TeachDemo_war_exploded/device_data_servlet_action?action=add_user_record',
        data: { "id": id,"user_name":user_name,"nick_name":nick_name,"password":password,"email":email,"phonenumber":phonenumber},
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
    if(this.data.user_name==undefined || this.data.user_name==null || this.data.user_name==""){
      wx.showToast({
        title: '请您输入用户名称！',
        icon:'none',
      })
      ok=false;
    }
    if(this.data.password==undefined || this.data.password==null || this.data.password==""){
      wx.showToast({
        title: '请您输入密码！',
        icon:'none',
      })
      ok=false;
    }
    if(this.data.nick_name==undefined || this.data.nick_name==null || this.data.nick_name==""){
      wx.showToast({
        title: '请您输入用户昵称！',
        icon:'none',
      })
      ok=false;
    }
    if(this.data.email==undefined || this.data.email==null || this.data.email==""){
      wx.showToast({
        title: '请您输入邮箱！',
        icon:'none',
      })
      ok=false;
    }
    if(this.data.phonenumber==undefined || this.data.phonenumber==null || this.data.phonenumber==""){
      wx.showToast({
        title: '请您输入电话号码！',
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