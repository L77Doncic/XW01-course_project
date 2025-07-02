import util from './../../utils/util.js';
import config from './../../utils/config.js';
import networkHelper from './../../utils/networkHelper.js';

// 是否使用本地测试模式
const isTestMode = true;

// 添加Array.prototype.remove方法
Array.prototype.remove = function(index) {
  if (index < 0 || index >= this.length) {
    return this;
  }
  this.splice(index, 1);
  return this;
};

Page({
  data: {
    showtab:0,  //顶部选项卡索引
    showtabtype:'', //选中类型
    showfootertab:0,  //底部标签页索引
    tabnav:{},  //顶部选项卡数据
    questionsall:[],  //所有问题
    questions:[], //问题列表
    showquestionindex:null, //查看问题索引,
    uploadimgs:[], //上传图片列表
    editable: false, //是否可编辑
    description: '', //问题描述
    contactName: '', //联系人
    contactPhone: '' //手机号码
  },
  onLoad: function () {
    this.setData({
      tabnav:{
        tabnum:5,
        tabitem:[
          {
            "id":0,
            "type":"",
            "text":"全部"
          },
          {
            "id":1,
            "type":"A",
            "text":"用户管理"
          },
          {
            "id":2,
            "type":"B",
            "text":"天气查询"
          },
          {
            "id":3,
            "type":"C",
            "text":"代办查询"
          },
          {
            "id":4,
            "type":"D",
            "text":"车辆信息"
          }
        ]
      },
      uploadimgs:[]
    })
    this.fetchQuestions();
  },
  chooseImage:function() {
    let _this = this;
    wx.showActionSheet({
      itemList: ['从相册中选择', '拍照'],
      itemColor: "#f7982a",
      success: function(res) {
        if (!res.cancel) {
          if(res.tapIndex == 0){
            _this.chooseWxImage('album')
          }else if(res.tapIndex == 1){
            _this.chooseWxImage('camera')
          }
        }
      }
    })
  },
  chooseWxImage:function(type){
    let _this = this;
    wx.chooseImage({
      sizeType: ['original', 'compressed'],
      sourceType: [type],
      success: function (res) {
        _this.setData({
          uploadimgs: _this.data.uploadimgs.concat(res.tempFilePaths)
        })
      }
    })
  },
  editImage:function(){
    this.setData({
      editable: !this.data.editable
    })
  },
  deleteImg:function(e){
    console.log(e.currentTarget.dataset.index);
    const imgs = this.data.uploadimgs
    this.setData({
      uploadimgs: imgs.remove(e.currentTarget.dataset.index)
    })
  },
  inputDescription: function(e) {
    this.setData({
      description: e.detail.value
    })
  },
  inputName: function(e) {
    this.setData({
      contactName: e.detail.value
    })
  },
  inputPhone: function(e) {
    this.setData({
      contactPhone: e.detail.value
    })
  },
  checkInput: function() {
    if(!this.data.description) {
      wx.showToast({
        title: '请输入问题描述',
        icon: 'none'
      })
      return false
    }
    if(!this.data.contactName) {
      wx.showToast({
        title: '请输入联系人姓名',
        icon: 'none'
      })
      return false
    }
    if(!this.data.contactPhone) {
      wx.showToast({
        title: '请输入手机号码',
        icon: 'none'
      })
      return false
    }
    // 验证手机号格式
    if(!/^1[3-9]\d{9}$/.test(this.data.contactPhone)) {
      wx.showToast({
        title: '请输入正确的手机号码',
        icon: 'none'
      })
      return false
    }
    return true
  },
  questionSubmit: function() {
    if(!this.checkInput()) {
      return;
    }

    let that = this;
    
    // 检查网络状态
    wx.getNetworkType({
      success: function(res) {
        if(res.networkType === 'none') {
          wx.showToast({
            title: '网络不可用，请检查网络设置',
            icon: 'none',
            duration: 2000
          });
          return;
        }
        that.submitFeedback();
      }
    });
  },
  submitFeedback: function() {
    let that = this;
    wx.showLoading({
      title: '提交中...',
    });

    // 测试模式直接返回成功
    if(isTestMode) {
      setTimeout(() => {
        wx.hideLoading();
        wx.showToast({
          title: '提交成功',
          icon: 'success',
          duration: 2000,
          success: function() {
            setTimeout(() => {
              wx.navigateBack();
            }, 2000);
          }
        });
      }, 1000);
      return;
    }

    // 配置服务器地址 - 请根据实际情况修改
    const serverUrl = config.serverUrl;
    const uploadUrl = config.uploadUrl;
    const feedbackUrl = config.feedbackUrl;

    // 先上传图片（如果有）
    const uploadImgPromises = this.data.uploadimgs.map((imgPath, index) => {
      return new Promise((resolve, reject) => {
        wx.uploadFile({
          url: uploadUrl,
          filePath: imgPath,
          name: 'file',
          header: {
            'content-type': 'multipart/form-data'
          },
          success: res => {
            try {
              const data = JSON.parse(res.data);
              if (data.success && data.url) {
                resolve(data.url);
              } else {
                console.error(`图片${index + 1}上传失败:`, data);
                resolve('');
              }
            } catch(e) {
              console.error(`解析图片${index + 1}上传响应失败:`, e);
              resolve('');
            }
          },
          fail: err => {
            console.error(`图片${index + 1}上传失败:`, err);
            wx.showToast({
              title: `图片${index + 1}上传失败`,
              icon: 'none',
              duration: 2000
            });
            resolve('');
          }
        });
      });
    });

    // 所有图片上传完成后提交表单
    Promise.all(uploadImgPromises).then(imgUrls => {
      const filteredImgUrls = imgUrls.filter(url => url); // 移除空URL
      
      console.log('准备提交数据:', {
        description: that.data.description,
        contactName: that.data.contactName,
        contactPhone: that.data.contactPhone,
        images: filteredImgUrls,
        type: that.data.showtabtype || 'A'
      });

      wx.request({
        url: feedbackUrl,
        method: 'POST',
        data: {
          description: that.data.description,
          contact_name: that.data.contactName,
          contact_phone: that.data.contactPhone,
          images: filteredImgUrls.join(','),
          type: that.data.showtabtype || 'A'
        },
        header: {
          "content-type": "application/x-www-form-urlencoded",
          "x-requested-with": "XMLHttpRequest"
        },
        timeout: 10000, // 设置超时时间
        success: function(res) {
          console.log('提交响应:', res);
          wx.hideLoading();
          if(res.statusCode === 200 && res.data && res.data.success) {
            wx.showToast({
              title: '提交成功',
              icon: 'success',
              duration: 2000,
              success: function() {
                setTimeout(() => {
                  wx.navigateBack();
                }, 2000);
              }
            });
          } else {
            console.error('提交失败:', res);
            let errorMsg = '提交失败，请稍后重试';
            if (res.data && res.data.message) {
              errorMsg = res.data.message;
            } else if (res.statusCode !== 200) {
              errorMsg = `服务器错误 (${res.statusCode})`;
            }
            wx.showToast({
              title: errorMsg,
              icon: 'none',
              duration: 3000
            });
          }
        },
        fail: function(err) {
          console.error('请求失败:', err);
          wx.hideLoading();
          let errorMsg = '网络错误，请稍后重试';
          if (err.errMsg) {
            if (err.errMsg.includes('timeout')) {
              errorMsg = '请求超时，请检查网络连接';
            } else if (err.errMsg.includes('fail')) {
              errorMsg = '网络连接失败，请检查网络设置';
            }
          }
          wx.showToast({
            title: errorMsg,
            icon: 'none',
            duration: 3000
          });
        },
        complete: function() {
          if(wx.hideLoading) {
            wx.hideLoading();
          }
        }
      });
    }).catch(err => {
      console.error('图片上传失败:', err);
      wx.hideLoading();
      wx.showToast({
        title: '图片上传失败，请重试',
        icon: 'none',
        duration: 3000
      });
    });
  },
  fetchQuestions:function(){  //获取问题列表
    const newquestions = [
      {
        id: 1,
        q: "如何注册新账号？",
        a: "在登录页面点击'去注册'，填写相关信息后提交即可完成注册。"
      },
      {
        id: 2,
        q: "忘记密码怎么办？",
        a: "在登录页面点击'忘记密码'，根据提示重置密码。"
      },
      {
        id: 3,
        q: "如何修改个人信息？",
        a: "登录后进入'我的'页面，点击个人信息进行修改。"
      },
      {
        id: 4,
        q: "如何添加待办事项？",
        a: "在'待办事项'页面点击添加按钮，填写内容后保存即可。"
      },
      {
        id: 5,
        q: "如何反馈问题或建议？",
        a: "在'问题反馈'页面填写描述并提交，我们会尽快处理。"
      }
    ];
    this.setData({
      questions:newquestions,
      questionsall:newquestions
    })
  },
  setTab:function(e){ //设置选项卡选中索引
    const edata = e.currentTarget.dataset;
    this.setData({
      showtab: edata.tabindex,
      showtabtype: edata.type,
      questions: !edata.type ? this.data.questionsall:this.data.questionsall.filter(l=>l.type === edata.type),
      showquestionindex: this.data.showtab==edata.tabindex?this.data.showquestionindex:null
    })
  },
  showTab:function(e){  //切换选项卡
    const eindex = e.currentTarget.dataset.index;
    if(eindex==1&&!this.data.questions){
      console.log("text");
    }
    wx.setNavigationBarTitle({
      title:eindex==1?"常见问题":"问题反馈"
    })
    this.setData({
      showfootertab:eindex
    });
  },
  foldQuestion:function(e){ //展开收起问题
    const eindex = e.currentTarget.dataset.qindex;
    const oldqindex = this.data.showquestionindex;
    this.setData({
      showquestionindex: eindex===oldqindex?null:eindex
    })
  },
  callContact: function(e){  //拨打电话
    wx.makePhoneCall({
      phoneNumber: e.currentTarget.dataset.ponenumber
    })
  },
})
