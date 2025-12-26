const config = require('../config')
const { getToken, getSessionId } = require('./auth')
const errorCode = require('./errorCode')
const { toast, showConfirm, tansParams } = require('./common')

const timeout = 10000
const baseUrl = config.baseUrl

function request(options) {
  const isToken = (options.headers || {}).isToken === false
  options.header = options.header || {}
  if (getToken() && !isToken) {
    options.header['Authorization'] = 'Bearer ' + getToken()
  }
  // 添加Session ID到Cookie头
  const sessionId = getSessionId()
  if (sessionId) {
    options.header['Cookie'] = 'JSESSIONID=' + sessionId
  }
  if (options.params) {
    let url = options.url + '?' + tansParams(options.params)
    url = url.slice(0, -1)
    options.url = url
  }
  return new Promise((resolve, reject) => {
    wx.request({
      method: options.method || 'GET',
      timeout: options.timeout || timeout,
      url: options.baseUrl || (baseUrl + options.url),
      data: options.data,
      header: options.header,
      success(res) {
        console.log('API响应:', res.data);
        let code = res.data.code;
        // 如果没有code，默认成功
        if (code === undefined || code === null) {
            code = 200;
        }
        
        const msg = errorCode[code] || (res.data && res.data.msg) || errorCode['default']
        
        if (code === 200 || code === 0) {
          resolve(res.data)
        } else if (code === 401) {
          showConfirm('登录状态已过期，是否重新登录?').then(r => {
            if (r.confirm) {
              wx.removeStorageSync('App-Token')
              wx.reLaunch({ url: '/pages/login/index' })
            }
          })
          reject('无效的会话，或者会话已过期，请重新登录。')
        } else if (code === 500) {
          toast(msg)
          reject('500')
        } else {
          toast(msg)
          reject(code)
        }
      },
      fail(error) {
        let message = error.errMsg || '网络错误'
        if (message.includes('timeout')) {
          message = '系统接口请求超时'
        }
        toast(message)
        reject(error)
      }
    })
  })
}

module.exports = request
