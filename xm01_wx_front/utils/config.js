// 服务器配置
const config = {
  // 开发环境配置
  development: {
    serverUrl: 'http://localhost:9999/TeachDemo_war_exploded',
    uploadUrl: 'http://localhost:9999/TeachDemo_war_exploded/upload',
    feedbackUrl: 'http://localhost:9999/TeachDemo_war_exploded/device_data_servlet_action?action=add_feedback'
  },
  
  // 生产环境配置
  production: {
    serverUrl: 'https://your-production-server.com',
    uploadUrl: 'https://your-production-server.com/upload',
    feedbackUrl: 'https://your-production-server.com/device_data_servlet_action?action=add_feedback'
  },
  
  // 测试环境配置
  test: {
    serverUrl: 'https://your-test-server.com',
    uploadUrl: 'https://your-test-server.com/upload',
    feedbackUrl: 'https://your-test-server.com/device_data_servlet_action?action=add_feedback'
  },
  
  // 本地网络配置（如果在同一局域网内）
  localNetwork: {
    serverUrl: 'http://192.168.1.100:9999/TeachDemo_war_exploded', // 替换为您的电脑IP地址
    uploadUrl: 'http://192.168.1.100:9999/TeachDemo_war_exploded/upload',
    feedbackUrl: 'http://192.168.1.100:9999/TeachDemo_war_exploded/device_data_servlet_action?action=add_feedback'
  }
};

// 当前环境 - 可以根据需要修改
const currentEnv = 'development';

// 导出当前环境的配置
module.exports = config[currentEnv]; 