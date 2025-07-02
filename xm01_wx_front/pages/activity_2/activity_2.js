Page({
  data: {
    showsearch:false,   //显示搜索按钮
    searchtext:'',  //搜索文字
    filterdata:{},  //筛选条件数据
    showfilter:false, //是否显示下拉筛选
    showfilterindex:null, //显示哪个筛选类目
    sortindex:0,  //一级分类索引
    sortid:null,  //一级分类id
    subsortindex:0, //二级分类索引
    subsortid:null, //二级分类id
    cityindex:0,  //一级城市索引
    cityid:null,  //一级城市id
    subcityindex:0,  //二级城市索引
    subcityid:null, //二级城市id
    servicelist:[], //服务集市列表
    scrolltop:null, //滚动位置
    page: 0,  //分页
    vehiclelist: [],
    userInfo: {
      id: 1,
      userName: '123',
      nickName: '123',
      email: 'summer_xt17@163.com',
      phone: '110',
      createTime: null,
      isAdmin: true
    }
  },
  onLoad: function (options) { //加载数据渲染页面
    this.fetchServiceData();
    this.fetchFilterData();
    this.getTodoRecord();
  },
  fetchFilterData:function(){ //获取筛选条件
    this.setData({
      filterdata:{
        "sort": [
            {
                "id": 0,
                "title": "全部"
            },
            {
              "id": 1,
              "title": "压线",
            },
            {
              "id": 2,
              "title": "违停",
            },
            {
              "id": 3,
              "title": "超速",
            },
            {
              "id": 4,
              "title": "闯红灯",
              
            },
            {
              "id": 5,
              "title": "逆行",
              
            },
        ],
        "city": [
            {
                "id": 0,
                "title": "全部"
            },
            {
              "id": 1,
              "title": "湖北省",
              "subcity": [
                {
                    "id": 1,
                    "title": "全部"
                },
                {
                    "id": 11,
                    "title": "武汉市"
                },
                {
                    "id": 12,
                    "title": "襄阳市"
                },
                {
                    "id": 13,
                    "title": "孝感市"
                },
                {
                    "id": 14,
                    "title": "随州市"
                },
                {
                    "id": 15,
                    "title": "荆州市"
                },
                {
                    "id": 16,
                    "title": "黄冈市"
                },
                {
                    "id": 17,
                    "title": "天门市"
                },
                {
                    "id": 18,
                    "title": "仙桃市"
                },
                {
                    "id": 19,
                    "title": "潜江市"
                },
                {
                    "id": 20,
                    "title": "十堰市"
                },
                {
                    "id": 21,
                    "title": "宜昌市"
                },
                {
                    "id": 22,
                    "title": "咸宁市"
                },
              ]
            },
            {
              "id": 2,
              "title": "浙江省",
              "subcity": [
                {
                    "id": 2,
                    "title": "全部"
                },
                {
                    "id": 21,
                    "title": "杭州市"
                },
                {
                    "id": 22,
                    "title": "金华市"
                },
                {
                    "id": 23,
                    "title": "义乌市"
                },
              ]
            },
            {
              "id": 3,
              "title": "江苏省",
              "subcity": [
                {
                    "id": 3,
                    "title": "全部"
                },
                {
                    "id": 31,
                    "title": "南京市"
                },
                {
                    "id": 32,
                    "title": "苏州市"
                }
              ]
            }
        ],
      }
    })
  },
  fetchServiceData:function(){  //获取城市列表
    let _this = this;
    wx.showToast({
      title: '加载中',
      icon: 'loading'
    })
    const perpage = 10;
    this.setData({
      page:this.data.page+1
    })
    const page = this.data.page;
    const newlist = [];
    for (var i = (page-1)*perpage; i < page*perpage; i++) {
      newlist.push({
        "id":i+1,
        "name":"上海拜特信息技术有限公司"+(i+1),
        "city":"上海",
        "tag":"法律咨询",
        "imgurl":"http://img.mukewang.com/57fdecf80001fb0406000338-240-135.jpg"
      })
    }
    setTimeout(()=>{
      _this.setData({
        servicelist:_this.data.servicelist.concat(newlist)
      })
    },1500)
  },
  inputSearch:function(e){  //输入搜索文字
    this.setData({
      showsearch:e.detail.cursor>0,
      searchtext:e.detail.value
    })
  },
  submitSearch:function(){  //提交搜索
    console.log(this.data.searchtext);
    this.fetchServiceData();
  },
  setFilterPanel: function(e){ //展开筛选面板
    const d = this.data;
    const i = e.currentTarget.dataset.findex;
    if(d.showfilterindex == i){
      this.setData({
        showfilter: false,
        showfilterindex: null
      })
    }else{    
      this.setData({
        showfilter: true,
        showfilterindex:i,
      })
    }
    console.log(d.showfilterindex);
  },
  getcarRecord1:function(){
    let that=this;
    wx.request({
      url: 'http://localhost:9999/TeachDemo_war_exploded/device_data_servlet_action?action=vehicle_monitor1',
      data:{},
      header:{"content-type":"application/x-www-form-urlencoded","x-requested-with":"XMLHttpRequest"},
      success:function(res){
        that.handleGetTodoRecordResult(res);

      },
      fail:function(res){

      }
    })
  },
  getcarRecord2:function(){
    let that=this;
    wx.request({
      url: 'http://localhost:9999/TeachDemo_war_exploded/device_data_servlet_action?action=vehicle_monitor2',
      data:{},
      header:{"content-type":"application/x-www-form-urlencoded","x-requested-with":"XMLHttpRequest"},
      success:function(res){
        that.handleGetTodoRecordResult(res);

      },
      fail:function(res){

      }
    })
  },
  getcarRecord3:function(){
    let that=this;
    wx.request({
      url: 'http://localhost:9999/TeachDemo_war_exploded/device_data_servlet_action?action=vehicle_monitor3',
      data:{},
      header:{"content-type":"application/x-www-form-urlencoded","x-requested-with":"XMLHttpRequest"},
      success:function(res){
        that.handleGetTodoRecordResult(res);

      },
      fail:function(res){

      }
    })
  },
  getcarRecord4:function(){
    let that=this;
    wx.request({
      url: 'http://localhost:9999/TeachDemo_war_exploded/device_data_servlet_action?action=vehicle_monitor4',
      data:{},
      header:{"content-type":"application/x-www-form-urlencoded","x-requested-with":"XMLHttpRequest"},
      success:function(res){
        that.handleGetTodoRecordResult(res);

      },
      fail:function(res){

      }
    })
  },
  getcarRecord5:function(){
    let that=this;
    wx.request({
      url: 'http://localhost:9999/TeachDemo_war_exploded/device_data_servlet_action?action=vehicle_monitor5',
      data:{},
      header:{"content-type":"application/x-www-form-urlencoded","x-requested-with":"XMLHttpRequest"},
      success:function(res){
        that.handleGetTodoRecordResult(res);

      },
      fail:function(res){

      }
    })
  },
  setSortIndex:function(e){ //服务类别一级索引
    const d= this.data;
    const dataset = e.currentTarget.dataset;
    let that = this;
    this.setData({
      sortindex:dataset.sortindex,
      sortid:dataset.sortid,
      subsortindex: d.sortindex==dataset.sortindex ? d.subsortindex:0
    })
    console.log('违法id：一级--'+this.data.sortid);
    if(this.data.sortid==1) this.getcarRecord1();
    if(this.data.sortid==2) this.getcarRecord2();
    if(this.data.sortid==3) this.getcarRecord3();
    if(this.data.sortid==4) this.getcarRecord4();
    if(this.data.sortid==5) this.getcarRecord5();
  },
  setCityIndex:function(e){ //服务城市一级索引
    const d= this.data;
    const dataset = e.currentTarget.dataset;
    this.setData({
      cityindex:dataset.cityindex,
      cityid:dataset.cityid,
      subcityindex: d.cityindex==dataset.cityindex ? d.subcityindex:0
    })
    console.log('服务城市id：一级--'+this.data.cityid+',二级--'+this.data.subcityid);
  },
  setSubcityIndex:function(e){ //服务城市二级索引
    const dataset = e.currentTarget.dataset;
    this.setData({
      subcityindex:dataset.subcityindex,
      subcityid:dataset.subcityid,
    })
    console.log('服务城市id：一级--'+this.data.cityid+',二级--'+this.data.subcityid);
  },
  hideFilter: function(){ //关闭筛选面板
    this.setData({
      showfilter: false,
      showfilterindex: null
    })
  },
  scrollHandle:function(e){ //滚动事件
    this.setData({
      scrolltop:e.detail.scrollTop
    })
  },
  goToTop:function(){ //回到顶部
    this.setData({
      scrolltop:0
    })
  },
  scrollLoading:function(){ //滚动加载
    this.fetchServiceData();
  },
  onPullDownRefresh:function(){ //下拉刷新
    this.setData({
      page:0,
      servicelist:[]
    })
    this.fetchServiceData();
    this.fetchFilterData();
    setTimeout(()=>{
      wx.stopPullDownRefresh()
    },1000)
  },
  getTodoRecord:function(){
    let that=this;
    wx.request({
      url: 'http://localhost:9999/TeachDemo_war_exploded/device_data_servlet_action?action=vehicle_monitor2',
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
        if (res.confirm) {
          var id=e.currentTarget.dataset.itemid;
        console.log(id);
        let that=this;
        wx.request({
          url: 'http://localhost:9999/TeachDemo_war_exploded/device_data_servlet_action?action=delete_car_record',
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
    
        if (res.cancel) {
          
        }
      }
    })
  },
  onModifyRecord:function(e){
    console.log("123123")
    console.log(JSON.stringify(e));
    let that=this;
    var id=e.currentTarget.dataset.itemid;
    var title=e.currentTarget.dataset.title;
    var record=id;
    wx.navigateTo({
      url: 'modify?record='+record,
    })


  },
  onAddReocrd:function() {
    wx.navigateTo({
      url: 'todo_add',
    })
    
  },
  handleLogout() {
    wx.showModal({
      title: '提示',
      content: '确定要退出登录吗？',
      success: (res) => {
        if (res.confirm) {
          // 清除本地存储的用户信息
          wx.removeStorageSync('userInfo');
          
          // 跳转到登录页面
          wx.reLaunch({
            url: '/pages/activity_1/activity_1'
          });
        }
      }
    });
  }
})