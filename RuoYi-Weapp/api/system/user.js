const request = require('../../utils/request')
const upload = require('../../utils/upload')

function updateUserPwd(oldPassword, newPassword) {
  const data = { oldPassword, newPassword }
  return request({
    url: '/system/user/profile/updatePwd',
    method: 'PUT',
    params: data
  })
}

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
  updateUserPwd,
  getUserProfile,
  updateUserProfile,
  uploadAvatar
}
