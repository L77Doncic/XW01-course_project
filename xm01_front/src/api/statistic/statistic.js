import request from '@/utils/request'

// 查询车流量列表
export function listStatistic(query) {
  return request({
    url: '/statistic/statistic/list',
    method: 'get',
    params: query
  })
}

// 查询车流量详细
export function getStatistic(statisticId) {
  return request({
    url: '/statistic/statistic/' + statisticId,
    method: 'get'
  })
}

// 新增车流量
export function addStatistic(data) {
  return request({
    url: '/statistic/statistic',
    method: 'post',
    data: data
  })
}

// 修改车流量
export function updateStatistic(data) {
  return request({
    url: '/statistic/statistic',
    method: 'put',
    data: data
  })
}

// 删除车流量
export function delStatistic(statisticId) {
  return request({
    url: '/statistic/statistic/' + statisticId,
    method: 'delete'
  })
}
