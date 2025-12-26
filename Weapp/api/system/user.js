const request = require('../../utils/request')
const upload = require('../../utils/upload')

function getUserProfile() {
  return request({
    url: '/system/user/profile',
    method: 'GET'
  })
}

function updateUserProfile(data) {
  return request({
    url: '/system/user/profile',
    method: 'PUT',
    data
  })
}

function uploadAvatar(data) {
  return upload({
    url: '/system/user/profile/avatar',
    name: data.name,
    filePath: data.filePath
  })
}

module.exports = {
  getUserProfile,
  updateUserProfile,
  uploadAvatar
}
