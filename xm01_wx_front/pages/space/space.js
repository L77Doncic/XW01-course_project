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
    devicelist: []
  },
  onLoad: function () { //加载数据渲染页面
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
              "title": "人力资源",
              "subsort": [
                {
                    "id": 1,
                    "title": "全部"
                },
                {
                    "id": 11,
                    "title": "社会及商业保险"
                },
                {
                    "id": 12,
                    "title": "招聘、猎头"
                },
                {
                    "id": 13,
                    "title": "薪酬绩效"
                },
              ]
            },
            {
              "id": 2,
              "title": "财务法务",
              "subsort": [
                {
                    "id": 2,
                    "title": "全部"
                },
                {
                    "id": 21,
                    "title": "知识产权保护"
                },
                {
                    "id": 22,
                    "title": "工商注册"
                },
                {
                    "id": 23,
                    "title": "法律咨询"
                },
              ]
            },
            {
              "id": 3,
              "title": "行政办公",
              "subsort": [
                {
                    "id": 3,
                    "title": "全部"
                },
                {
                    "id": 31,
                    "title": "翻译"
                },
                {
                    "id": 32,
                    "title": "速记"
                }
              ]
            },
            {
              "id": 4,
              "title": "创业指导",
              "subsort": [
                {
                    "id": 4,
                    "title": "全部"
                },
                {
                    "id": 41,
                    "title": "创业培训"
                }
              ]
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
  updata:function(ress){
    console.log("需要查询的部分:"+ress)
    let that=this;
    wx.request({
      url: 'http://localhost:9999/TeachDemo_war_exploded/device_data_servlet_action?action=user_info',
      data:{},
      header:{"content-type":"application/x-www-form-urlencoded","x-requested-with":"XMLHttpRequest"},
      success:function(res){
        let matchedItems = res.data.aaData.filter(item => {
          return item.user_id === ress || item.user_name === ress || item.nick_name === ress || item.email === ress || item.phonenumber === ress;
        });
        // 处理匹配到的项
        matchedItems.forEach(matchedItem => {
          console.log(matchedItem); 
        });
        that.setData({
          devicelist:[],
          devicelist:matchedItems
        });
      },
      fail:function(res){
      }
    })
    
  },
  submitSearch:function(){  //提交搜索
    console.log(this.data.searchtext);
    this.updata(this.data.searchtext);
  },
  getTodoRecord:function(){
    let that=this;
    wx.request({
      url: 'http://localhost:9999/TeachDemo_war_exploded/device_data_servlet_action?action=user_info',
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
      devicelist:res.data.aaData,
    })
  },
  onDeleteRecord(e){
    console.log(JSON.stringify(e));
    wx.showModal({
      cancelColor:'cancelColor',
      title: '提示',
      content: '您确定要删除这条记录吗？',
      complete: (res) => {
        if (!res.cancel) {
          var id=e.currentTarget.dataset.itemid;
        console.log(id);
        let that=this;
        wx.request({
          url: 'http://localhost:9999/TeachDemo_war_exploded/device_data_servlet_action?action=delete_user_record',
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
    let that=this;
    var id=e.currentTarget.dataset.itemid;
    console.log(id);
    wx.navigateTo({
      url: '/pages/space/user_modify?record='+id,
    })
  },
  onAddReocrd:function() {
    wx.navigateTo({
      url: '/pages/space/user_add',
    })
  }
})