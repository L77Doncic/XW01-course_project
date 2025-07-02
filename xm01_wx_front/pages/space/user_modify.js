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
    user_id:''
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
  inputemail: function (e) {
    this.setData({
      email: e.detail.value,
    });
  },
  inputphonenumber: function (e) {
    this.setData({
      phonenumber: e.detail.value,
    });
  },
  inputusername: function (e) {
    this.setData({
      user_name: e.detail.value,
    });
  },
  inputnickname: function (e) {
    this.setData({
      nick_name: e.detail.value,
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
          var user_name=that.data.user_name;
          var email=that.data.email;
          var phonenumber=that.data.phonenumber;
          var nick_name=that.data.nick_name;
          console.log("传到后端修改用户的ID为："+user_id);
          wx.request({
            url: 'http://localhost:9999/TeachDemo_war_exploded/device_data_servlet_action?action=modify_user_record',
            data:{"user_id":user_id,"user_name":user_name,"email":email,"phonenumber":phonenumber,"nick_name":nick_name},
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
