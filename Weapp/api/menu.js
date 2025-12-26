const request = require('../utils/request')

function getRouters() {
  return request({
    url: '/getRouters',
    method: 'GET'
  })
}

module.exports = {
  getRouters
}
