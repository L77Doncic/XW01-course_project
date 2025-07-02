import request from '@/utils/request'

// 查询carcolor列表
export function listCarcolor(query) {
  return request({
    url: '/carcolor/carcolor/list',
    method: 'get',
    params: query
  })
}

// 查询carcolor详细
export function getCarcolor(id) {
  return request({
    url: '/carcolor/carcolor/' + id,
    method: 'get'
  })
}

// 新增carcolor
export function addCarcolor(data) {
  return request({
    url: '/carcolor/carcolor',
    method: 'post',
    data: data
  })
}

// 修改carcolor
export function updateCarcolor(data) {
  return request({
    url: '/carcolor/carcolor',
    method: 'put',
    data: data
  })
}

// 删除carcolor
export function delCarcolor(id) {
  return request({
    url: '/carcolor/carcolor/' + id,
    method: 'delete'
  })
}
