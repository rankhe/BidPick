const { logout, getInfo } = require('../../api/login')
const config = require('../../config')
const { hasRole } = require('../../utils/permission')

console.log('我的页面JS文件加载成功')
console.log('config.baseUrl:', config.baseUrl)

Page({
  data: {
    avatar: '',
    username: '',
    isAdmin: false
  },
  onShow() {
    console.log('开始加载我的页面')
    
    // 检查是否有Session ID
    const sessionId = wx.getStorageSync('App-SessionId')
    console.log('当前Session ID:', sessionId)
    
    if (sessionId) {
      // 如果有Session ID，获取用户信息
      console.log('已登录，获取用户信息')
      getInfo().then(res => {
        console.log('获取用户信息成功:', res)
        const user = res.user || {}
        const avatar = (!user || !user.avatar) ? (config.baseUrl + '/img/profile.jpg') : (config.baseUrl + user.avatar)
        this.setData({
          avatar,
          username: user.userName || ''
        })
        console.log('设置用户信息成功')
        this.setData({ isAdmin: hasRole(['admin']) })
        console.log('设置管理员权限成功')
      }).catch(err => {
        console.error('获取用户信息失败:', err)
        // 即使获取用户信息失败，也显示默认的用户信息
        this.setData({
          avatar: config.baseUrl + '/img/profile.jpg',
          username: '未登录用户'
        })
      })
    } else {
      // 如果没有Session ID，显示未登录状态
      console.log('未登录，显示未登录状态')
      this.setData({
        avatar: config.baseUrl + '/img/profile.jpg',
        username: '未登录用户'
      })
    }
  },
  logout() {
    logout().then(() => {
      wx.removeStorageSync('App-SessionId')
      wx.reLaunch({ url: '/pages/login/index' })
    })
  }
})
