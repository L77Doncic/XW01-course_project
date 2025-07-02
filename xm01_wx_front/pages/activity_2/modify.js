Page({
  data: {
    industryarr:[],
    industryindex:0,
    statusarr:[],
    statusindex:0,
    jobarr:[],
    jobindex:0,
    user_name:'',
    nick_name:'',
    email:'',
    phonenumber:'',
    user_id:'',
    title:'',
    content:'',
    limittime:'',
    begintime:'',
    car_number:'',
    location:'',
    type:'',
    time:''
  },
  onLoad: function (options) {
    this.fetchData()
    console.log(options.record)
    this.setData({
      user_id:options.record
    })
  },
  fetchData: function(){
    this.setData({
      statusarr:["请选择","普通用户","管理员"],
    })
  },
  inputcarnumber: function (e) {
    this.setData({
      car_number: e.detail.value,
    });
  },
  inputlocation: function (e) {
    this.setData({
      location: e.detail.value,
    });
  },
  inputtype: function (e) {
    this.setData({
      type: e.detail.value,
    });
  },
  inputtime: function (e) {
    this.setData({
      time: e.detail.value,
    });
  },
  bindPickerChange: function(e){ //下拉选择
    const eindex = e.detail.value;
    const name = e.currentTarget.dataset.pickername;
    // this.setData(Object.assign({},this.data,{name:eindex}))
    switch(name) {
      case 'industry':
        this.setData({
          industryindex: eindex
        })
        break;
      case 'status':
        this.setData({
          statusindex: eindex
        })
        break;
      case 'job':
        this.setData({
          jobindex: eindex
        })
        break;
      default:
        return
    }
  },
  applySubmit:function(){
    let that= this;
    wx.showModal({
      cancelColor:'cancelColor',
      title: '提示',
      content: '确定要修改吗？',
      complete: (res) => {
        if (res.cancel) {    
        }
        if (res.confirm) {
          var user_id=that.data.user_id;
          var car_number=that.data.car_number;
          var location=that.data.location;
          var type=that.data.type;
          var time=that.data.time;
          console.log("传到后端修改事项的ID为："+user_id);
          wx.request({
            url: 'http://localhost:9999/TeachDemo_war_exploded/device_data_servlet_action?action=modify_car_record',
            data:{"id":user_id,"car_number":car_number,"location":location,"time":time},
            header:{"content-type":"application/x-www-form-urlencoded","x-requested-with":"XMLHttpRequest"},
            success:function(res){
              wx.navigateBack({

              });
            },
            fail:function(res){
            }
          })
        }
      }
    })
  },
})
