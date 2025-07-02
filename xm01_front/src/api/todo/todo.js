import request from '@/utils/request'

// 查询待办事项列表
export function listTodo(query) {
  return request({
    url: '/todo/todo/list',
    method: 'get',
    params: query
  })
}

// 查询待办事项详细
export function getTodo(id) {
  return request({
    url: '/todo/todo/' + id,
    method: 'get'
  })
}

// 新增待办事项
export function addTodo(data) {
  return request({
    url: '/todo/todo',
    method: 'post',
    data: data
  })
}

// 修改待办事项
export function updateTodo(data) {
  return request({
    url: '/todo/todo',
    method: 'put',
    data: data
  })
}

// 删除待办事项
export function delTodo(id) {
  return request({
    url: '/todo/todo/' + id,
    method: 'delete'
  })
}
