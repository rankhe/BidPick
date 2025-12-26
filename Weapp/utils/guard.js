const { getSessionId, removeSessionId } = require('./auth')
const { getInfo } = require('../api/login')

async function ensureLogin() {
  const sessionId = getSessionId()
  if (!sessionId) {
    console.log('无Session，跳转登录页')
    wx.reLaunch({ url: '/pages/login/index' })
    return false
  }
  
  try {
    const res = await getInfo()
    if (res && res.user) {
      return true
    }
    throw new Error('用户信息无效')
  } catch (err) {
    console.log('登录状态验证失败，清理 Session 并跳转登录页:', err)
    removeSessionId()
    wx.reLaunch({ url: '/pages/login/index' })
    return false
  }
}

module.exports = {
  ensureLogin
}
