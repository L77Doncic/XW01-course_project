import request from '@/utils/request'

// 查询违法信息列表
export function listCardata(query) {
  return request({
    url: '/cardata/cardata/list',
    method: 'get',
    params: query
  })
}

// 查询违法信息详细
export function getCardata(id) {
  return request({
    url: '/cardata/cardata/' + id,
    method: 'get'
  })
}

// 新增违法信息
export function addCardata(data) {
  return request({
    url: '/cardata/cardata',
    method: 'post',
    data: data
  })
}

// 修改违法信息
export function updateCardata(data) {
  return request({
    url: '/cardata/cardata',
    method: 'put',
    data: data
  })
}

// 删除违法信息
export function delCardata(id) {
  return request({
    url: '/cardata/cardata/' + id,
    method: 'delete'
  })
}
