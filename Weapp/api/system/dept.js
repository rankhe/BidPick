const request = require('../../utils/request')

// 查询部门列表
function listDept(query) {
  return request({
    url: '/system/dept/merchant/list',
    method: 'POST',
    data: query
  })
}

// 查询部门列表（排除节点）
function listDeptExcludeChild(excludeId) {
  return request({
    url: '/system/dept/treeData/' + excludeId,
    method: 'GET'
  })
}

module.exports = {
  listDept,
  listDeptExcludeChild
}
