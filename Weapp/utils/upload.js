const config = require('../config')
const { getSessionId } = require('./auth')
const errorCode = require('./errorCode')
const { toast, showConfirm, tansParams } = require('./common')

const timeout = 10000
const baseUrl = config.baseUrl

function upload(options) {
  const isToken = (options.headers || {}).isToken === false
  options.header = options.header || {}
  
  // 若依使用 Session 机制，通过 Cookie 传递 JSESSIONID
  const sessionId = getSessionId()
  if (sessionId) {
    options.header['Cookie'] = 'JSESSIONID=' + sessionId
    console.log('上传请求携带Session ID:', sessionId)
  }
  if (options.params) {
    let url = options.url + '?' + tansParams(options.params)
    url = url.slice(0, -1)
    options.url = url
  }
  return new Promise((resolve, reject) => {
    wx.uploadFile({
      timeout: options.timeout || timeout,
      url: baseUrl + options.url,
      filePath: options.filePath,
      name: options.name || 'file',
      header: options.header,
      formData: options.formData,
      success(res) {
        const result = JSON.parse(res.data || '{}')
        const code = result.code || 200
        const msg = errorCode[code] || result.msg || errorCode['default']
        if (code === 200) {
          resolve(result)
        } else if (code === 401) {
          showConfirm('登录状态已过期，是否重新登录?').then(r => {
            if (r.confirm) {
              wx.removeStorageSync('App-SessionId')
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

module.exports = upload
