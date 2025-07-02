const config = {
  serverUrl: 'http://10.135.3.146:9999',
  apiPath: '/TeachDemo_war_exploded/device_data_servlet_action',
  mockData: {
    aaData: [
      {
        statistic_id: "CAM001",
        traffic_flow: "156",
        create_time: "2024-03-19 10:30:00",
        wid_x1: "30.5702",
        wid_y1: "104.06476"
      },
      {
        statistic_id: "CAM002",
        traffic_flow: "89",
        create_time: "2024-03-19 10:30:00",
        wid_x1: "30.5722",
        wid_y1: "104.06676"
      },
      {
        statistic_id: "CAM003",
        traffic_flow: "245",
        create_time: "2024-03-19 10:30:00",
        wid_x1: "30.5682",
        wid_y1: "104.06276"
      }
    ]
  }
};

module.exports = config; 