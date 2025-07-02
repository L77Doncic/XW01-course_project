import request from '@/utils/request'

// 查询投诉管理列表
export function listComplaints(query) {
  return request({
    url: '/complaint/complaints/list',
    method: 'get',
    params: query
  })
}

// 查询投诉管理详细
export function getComplaints(id) {
  return request({
    url: '/complaint/complaints/' + id,
    method: 'get'
  })
}

// 新增投诉管理
export function addComplaints(data) {
  return request({
    url: '/complaint/complaints',
    method: 'post',
    data: data
  })
}

// 修改投诉管理
export function updateComplaints(data) {
  return request({
    url: '/complaint/complaints',
    method: 'put',
    data: data
  })
}

// 删除投诉管理
export function delComplaints(id) {
  return request({
    url: '/complaint/complaints/' + id,
    method: 'delete'
  })
}
