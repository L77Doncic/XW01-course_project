import request from '@/utils/request'


export function rtn(query) {
  return request({
    url: '/weibo/wbhot/getJsonData',
    method: 'get',
    params: query
  })
}

// 查询微博热搜列表
export function listWbhot(query) {
  return request({
    url: '/weibo/wbhot/list',
    method: 'get',
    params: query
  })
}

// 查询微博热搜详细
export function getWbhot(id) {
  return request({
    url: '/weibo/wbhot/' + id,
    method: 'get'
  })
}
export function gethotapi() {
  return request({
    url: '/weibo/wbhot/',
    method: 'get'
  })
}
// 新增微博热搜
export function addWbhot(data) {
  return request({
    url: '/weibo/wbhot',
    method: 'post',
    data: data
  })
}

// 修改微博热搜
export function updateWbhot(data) {
  return request({
    url: '/weibo/wbhot',
    method: 'put',
    data: data
  })
}

// 删除微博热搜
export function delWbhot(id) {
  return request({
    url: '/weibo/wbhot/' + id,
    method: 'delete'
  })
}
