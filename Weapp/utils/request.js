const config = require('../config')
const { getToken, getSessionId, setSessionId } = require('./auth')
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
        // 自动更新Session ID
        const setCookie = res.header['Set-Cookie'] || res.header['set-cookie']
        if (setCookie) {
          // 处理可能的数组情况 (微信小程序某些版本可能返回数组)
          // 如果是字符串，可能包含多个cookie，用逗号分隔，但也可能不分隔
          // 简单匹配 JSESSIONID
          const cookieStr = Array.isArray(setCookie) ? setCookie.join(';') : setCookie;
          const sessionIdMatch = cookieStr.match(/JSESSIONID=([^;]+)/);
          if (sessionIdMatch) {
             const newSessionId = sessionIdMatch[1];
             // 只有当Session ID变化时才更新，或者强制更新
             if (newSessionId !== getSessionId()) {
                setSessionId(newSessionId);
                console.log('更新Session ID:', newSessionId);
             }
          }
        }

        console.log('API响应:', res.data);
        
        // 优先检查 HTTP 状态码
        if (res.statusCode === 200) {
            let code = res.data.code;
            // 如果没有code，默认成功 (兼容非标准结构响应)
            if (code === undefined || code === null) {
                // 如果响应是字符串且包含HTML标签，通常是Session过期后的重定向页面
                if (typeof res.data === 'string' && (res.data.includes('<html>') || res.data.includes('<!DOCTYPE html>'))) {
                    code = 401;
                } else {
                    code = 200;
                }
            }
            
            const msg = errorCode[code] || (res.data && res.data.msg) || errorCode['default']
            
            if (code === 200 || code === 0) {
               resolve(res.data)
             } else if (code === 401) {
               if (!options.skipAuthHandler) {
                 showConfirm('登录状态已过期，是否重新登录?').then(r => {
                   if (r.confirm) {
                     wx.removeStorageSync('App-Token')
                     wx.reLaunch({ url: '/pages/login/index' })
                   }
                 })
               }
               reject('无效的会话，或者会话已过期，请重新登录。')
             } else if (code === 500) {
               toast(msg)
               reject('500')
             } else {
               toast(msg)
               reject(code)
             }
         } else {
             // 处理 HTTP 错误状态码
             let msg = '系统未知错误';
             if (res.statusCode === 404) {
                 msg = '请求地址不存在或接口异常';
                 console.error('请求404:', options.url);
             } else if (res.statusCode === 401) {
                 msg = '未授权，请登录';
                 if (!options.skipAuthHandler) {
                     showConfirm('登录状态已过期，是否重新登录?').then(r => {
                         if (r.confirm) {
                           wx.removeStorageSync('App-Token')
                           wx.reLaunch({ url: '/pages/login/index' })
                         }
                     })
                 }
                 reject('无效的会话，或者会话已过期，请重新登录。')
                 return;
             } else if (res.statusCode === 500) {
                msg = '服务器内部错误';
            } else if (res.statusCode === 502 || res.statusCode === 503) {
                msg = '服务不可用';
            }
            
            toast(msg)
            reject(res.statusCode)
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
