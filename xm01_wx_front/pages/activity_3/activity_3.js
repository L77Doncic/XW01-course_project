const config = require('../../config');

Page({
  data: {
    longitude: 104.06476,
    latitude: 30.5702,
    scale: 15,
    markers: [],
    height: 0,
    showDialog: false,
    markerDeviceId: '未知',
    markerName: '',
    markerLongitude: 0,
    markerLatitude: 0,
    markernumber: 0,
    markerTime: ''
  },

  onLoad: function (options) {
    let that = this;
    wx.getSystemInfo({
      success: function (res) {
        //设置map高度，根据当前设备宽高满屏显示
        let height = res.windowHeight;
        that.setData({
          height: height
        })
      }
    })
    this.getMyLocation();
  },

  getMyLocation(){
    let that = this;
    wx.getLocation({
      type: 'gcj02',
      success(res) {
        that.setData({
          latitude: res.latitude,
          longitude: res.longitude
        });
      },
      fail(res){
        console.log(res);
        wx.showToast({
          title: '获取位置失败',
          icon: 'none',
          duration: 2000
        });
      }
    });
  },

  onQueryTap(e) {
    console.log("点击了查询按钮");
    if(this.data.markers && this.data.markers.length > 0) {
      console.log("清除现有标记点");
      this.setData({
        markers: []
      });
    }
    
    wx.showLoading({
      title: '加载中...',
      mask: true
    });

    this.getDeviceStatus();
  },

  getDeviceStatus(){
    console.log("开始获取设备状态");
    let that = this;
    wx.request({
      url: `${config.serverUrl}${config.apiPath}?action=vehicle_number_record`,
      data:{},
      header:{"content-type":"application/x-www-form-urlencoded","x-requested-with":"XMLHttpRequest"},
      success(res){
        console.log("请求成功，返回数据：", res);
        if (res.statusCode === 200 && res.data && res.data.result_code === 0) {
          that.showMarkers(res);
        } else {
          console.log("服务器请求失败，使用离线数据");
          that.showMarkers({
            data: {
              result_code: 0,
              aaData: config.mockData.aaData
            }
          });
        }
      },
      fail(res){
        console.log("请求失败，错误信息：", res);
        that.showMarkers({
          data: {
            result_code: 0,
            aaData: config.mockData.aaData
          }
        });
      }
    });
  },

  showMarkers(res){
    console.log("开始显示标记点", res);
    wx.hideLoading();
    
    if (!res.data || !res.data.aaData) {
      console.log("数据格式错误", res.data);
      wx.showToast({
        title: '数据格式错误',
        icon: 'none',
        duration: 2000
      });
      return;
    }

    let list = res.data.aaData;
    console.log("解析到的数据列表：", list);
    
    if (list.length === 0) {
      console.log("没有数据");
      wx.showToast({
        title: '暂无数据',
        icon: 'none',
        duration: 2000
      });
      return;
    }

    let markers = [];
    for (var i=0; i<list.length; i++){
      var record = list[i];
      const latitude = parseFloat(record.wid_x1);
      const longitude = parseFloat(record.wid_y1);
      
      if (isNaN(latitude) || isNaN(longitude)) {
        console.error('无效的经纬度数据:', record);
        continue;
      }

      var point = {
        "id": i,
        "deviceId": record.statistic_id,
        "number_vehicle": record.traffic_flow,
        "Time": record.create_time,
        "longitude": longitude,
        "latitude": latitude
      };
      var marker = this.createMarker(point);
      markers.push(marker);
    }

    console.log("准备设置标记点：", markers);
    this.setData({
      markers: markers
    });
    
    // 如果有标记点，将地图中心设置到第一个标记点
    if (markers.length > 0) {
      console.log("设置地图中心点");
      this.setData({
        latitude: markers[0].latitude,
        longitude: markers[0].longitude
      });
    }
  },

  createMarker(point){
    let latitude = point.latitude;
    let longitude = point.longitude;
    let marker = {
      id: point.id || 0,
      deviceId: point.deviceId,
      number_vehicle: point.number_vehicle,
      Time: point.Time,
      name: '设备' + point.deviceId,
      latitude: latitude,
      longitude: longitude,
      width: 32,
      height: 32,
      iconPath: "../../images/camera.png",
      callout: {
        content: '设备' + point.deviceId + '\n车流量: ' + point.number_vehicle,
        color: '#333333',
        fontSize: 12,
        borderRadius: 4,
        borderWidth: 1,
        padding: 4,
        display: 'ALWAYS',
        bgColor: "#ffffff"
      }
    };
    return marker;
  },

  markertap(e) {
    const id = e.detail.markerId;
    const marker = this.data.markers[id];
    
    if (!marker) {
      wx.showToast({
        title: '获取设备信息失败',
        icon: 'none'
      });
      return;
    }

    this.setData({
      markerDeviceId: marker.deviceId,
      markernumber: marker.number_vehicle,
      markerTime: marker.Time,
      markerLongitude: marker.longitude,
      markerLatitude: marker.latitude,
      showDialog: true
    });
  },

  toggleDialog() {
    this.setData({
      showDialog: !this.data.showDialog
    });
  },

  regionchange(e) {
    if (e.type === 'end' && e.causedBy === 'drag') {
      console.log('地图视野已更改');
    }
  }
});