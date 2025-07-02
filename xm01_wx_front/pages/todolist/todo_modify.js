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
  inputlimittime: function (e) {
    this.setData({
      limittime: e.detail.value,
    });
  },
  inputbegintime: function (e) {
    this.setData({
      begintime: e.detail.value,
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
          var title=that.data.title;
          var content=that.data.content;
          var limittime=that.data.limittime;
          var begintime=that.data.begintime;
          console.log("传到后端修改事项的ID为："+user_id);
          wx.request({
            url: 'http://localhost:9999/TeachDemo_war_exploded/device_data_servlet_action?action=modify_todo_record',
            data:{"id":user_id,"title":title,"content":content,"limittime":limittime,"begintime":begintime},
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
