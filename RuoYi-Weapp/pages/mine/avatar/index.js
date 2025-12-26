const { uploadAvatar } = require('../../../api/system/user')
const { getInfo } = require('../../../api/login')
const config = require('../../../config')

Page({
  data: {
    avatar: ''
  },
  onShow() {
    getInfo().then(res => {
      const user = res.user || {}
      const avatar = (!user || !user.avatar) ? (config.baseUrl + '/img/profile.jpg') : (config.baseUrl + user.avatar)
      this.setData({ avatar })
    })
  },
  chooseImage() {
    wx.chooseMedia({
      count: 1,
      mediaType: ['image'],
      sourceType: ['album', 'camera'],
      success: (res) => {
        const filePath = res.tempFiles[0].tempFilePath
        uploadAvatar({ name: 'avatarfile', filePath }).then(() => {
          wx.showToast({ icon: 'success', title: '上传成功' })
          return getInfo()
        }).then(res2 => {
          const user = res2.user || {}
          const avatar = (!user || !user.avatar) ? (config.baseUrl + '/img/profile.jpg') : (config.baseUrl + user.avatar)
          this.setData({ avatar })
        })
      }
    })
  }
})
