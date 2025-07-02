Page({
  data: {
    vehiclelist: []
  },
  onLoad: function () { //加载数据渲染页面
    this.getTodoRecord();
  },
  getTodoRecord:function(){
    let that=this;
    wx.request({
      url: 'http://localhost:9999/TeachDemo_war_exploded/device_data_servlet_action?action=vehicle_car',
      data:{},
      header:{"content-type":"application/x-www-form-urlencoded","x-requested-with":"XMLHttpRequest"},
      success:function(res){
        that.handleGetTodoRecordResult(res);

      },
      fail:function(res){

      }
    })
  },
  handleGetTodoRecordResult:function(res){
    console.log(JSON.stringify(res));
    
    this.setData({
      vehiclelist:res.data.aaData,
    })
  },
  onDeleteRecord(e){
    console.log(JSON.stringify(e));
    wx.showModal({
      cancelColor:'cancelColor',
      title: '提示',
      content: '您确定要删除这条记录吗？',
      complete: (res) => {
        if (res.cancel) {
          var id=e.currentTarget.dataset.itemid;
        console.log(id);
        let that=this;
        wx.request({
          url: 'http://localhost:9999/TeachDemo_war_exploded/device_data_servlet_action?action=delete_car_record2',
          data:{"id":id},
          header:{"content-type":"application/x-www-form-urlencoded","x-requested-with":"XMLHttpRequest"},
         success:function(res){
       // that.handleGetTodoRecordResult(res);
       that.getTodoRecord();
      },
      fail:function(res){
      }
    })
          
        }
    
        if (res.confirm) {
          
        }
      }
    })
  },
  onModifyRecord:function(e){
    console.log(JSON.stringify(e));
    let that=this;
    var id=e.currentTarget.dataset.itemid;
    var title=e.currentTarget.dataset.title;
    var record=id;
    wx.navigateTo({
      url: 'car_modify?record='+record,
    })


  },
  onAddReocrd:function() {
    wx.navigateTo({
      url: 'car_add',
    })
    
  }
})