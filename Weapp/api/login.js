const request = require('../utils/request')
const { setSessionId, getSessionId } = require('../utils/auth')

function login(data) {
  // 创建一个新的请求头
  const header = { isToken: false };
  
  // 如果有Session ID，添加到请求头中
  const sessionId = getSessionId();
  if (sessionId) {
    header['Cookie'] = `JSESSIONID=${sessionId}`;
    console.log('登录请求携带Session ID:', sessionId);
  }
  
  return request({
    url: '/login',
    headers: header,
    method: 'POST',
    data
  })
}

// 微信授权登录
function wechatLogin(data) {
  return new Promise((resolve, reject) => {
    const config = require('../config')
    const header = { 
      isToken: false,
      'Content-Type': 'application/x-www-form-urlencoded'  // 微信登录需要使用表单格式
    }
    const sessionId = getSessionId()
    if (sessionId) {
      header['Cookie'] = `JSESSIONID=${sessionId}`
    }
    
    // 构建表单数据格式的参数
    const formData = Object.keys(data).map(key => {
      return encodeURIComponent(key) + '=' + encodeURIComponent(data[key])
    }).join('&')
    
    wx.request({
      url: config.baseUrl + '/wx/login',
      method: 'POST',
      header,
      data: formData,
      success(res) {
        const code = res.data && res.data.code
        if (code === 200) {
          // 从响应头中获取新的 Session ID
          const setCookie = res.header['Set-Cookie'] || res.header['set-cookie']
          if (setCookie) {
            const sessionIdMatch = setCookie.match(/JSESSIONID=([^;]+)/)
            if (sessionIdMatch) {
              setSessionId(sessionIdMatch[1])
              console.log('登录成功，保存Session ID:', sessionIdMatch[1])
            }
          }
          resolve(res.data)
        } else {
          reject(res.data)
        }
      },
      fail(err) {
        reject(err)
      }
    })
  })
}

function register(data) {
  return request({
    url: '/register',
    headers: { isToken: false },
    method: 'POST',
    data
  })
}

function getInfo(options) {
  return request({
    url: '/getInfo',
    method: 'GET',
    ...options
  })
}

function logout() {
  return request({
    url: '/logout',
    method: 'POST'
  })
}



// 获取验证码
function getCodeImg() {
  return new Promise((resolve, reject) => {
    const config = require('../config')
    // 生成随机数防止缓存
    const random = Math.random().toString(36).substring(2)
    const url = `${config.baseUrl}/captcha/captchaImage?random=${random}`
    
    wx.request({
      url: url,
      method: 'GET',
      responseType: 'arraybuffer',
      header: { isToken: false },
      timeout: 20000,
      success(res) {
        if (res.statusCode === 200) {
          console.log('获取验证码成功，状态码:', res.statusCode)
          console.log('响应头:', res.header)
          
          // 从响应头中获取Set-Cookie
          const setCookie = res.header['Set-Cookie'] || res.header['set-cookie'];
          if (setCookie) {
            console.log('Set-Cookie:', setCookie)
            // 提取JSESSIONID
            const sessionIdMatch = setCookie.match(/JSESSIONID=([^;]+)/);
            if (sessionIdMatch) {
              const sessionId = sessionIdMatch[1];
              setSessionId(sessionId);
              console.log('获取到Session ID:', sessionId);
            }
          }
          
          // 直接转换为base64格式
          const base64 = wx.arrayBufferToBase64(res.data);
          console.log('生成base64数据，长度:', base64.length)
          
          resolve({
            captchaEnabled: true,
            img: base64,
            uuid: ''
          });
        } else {
          console.log('获取验证码失败，状态码:', res.statusCode)
          resolve({
            captchaEnabled: false
          });
        }
      },
      fail(err) {
        console.error('获取验证码网络失败:', JSON.stringify(err))
        reject(err)
      }
    })
  })
}

module.exports = {
  login,
  register,
  getInfo,
  logout,
  getCodeImg,
  wechatLogin
}
