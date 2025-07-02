import request from '@/utils/request'

// 查询weather列表
export function listWeather(query) {
  return request({
    url: '/system/weather/list',
    method: 'get',
    params: query
  })
}

// 查询weather详细
export function getWeather(id) {
  return request({
    url: '/system/weather/' + id,
    method: 'get'
  })
}

// 新增weather
export function addWeather(data) {
  return request({
    url: '/system/weather',
    method: 'post',
    data: data
  })
}

// 修改weather
export function updateWeather(data) {
  return request({
    url: '/system/weather',
    method: 'put',
    data: data
  })
}

// 删除weather
export function delWeather(id) {
  return request({
    url: '/system/weather/' + id,
    method: 'delete'
  })
}
