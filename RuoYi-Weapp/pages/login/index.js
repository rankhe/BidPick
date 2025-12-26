const { wechatLogin, login, getInfo } = require('../../api/login')
const config = require('../../config')

Page({
  data: {
    loading: false,
    loginType: 'wechat', // wechat 或 password
    username: '',
    password: '',
    captchaEnabled: true,
    codeUrl: '',
    code: '',
    uuid: ''
  },
  onLoad() {
    this.checkLoginStatus()
  },
  
  async checkLoginStatus() {
    const sessionId = wx.getStorageSync('App-SessionId')
    if (sessionId) {
      try {
        const res = await getInfo()
        if (res && res.user) {
          wx.reLaunch({ url: '/pages/mine/index' })
          return
        }
      } catch (err) {
        console.log('登录状态验证失败，清理 Session')
        wx.removeStorageSync('App-SessionId')
      }
    }
    this.getCodeImg()
  },
  /**
   * 切换登录方式
   */
  switchLoginType(e) {
    const type = e.currentTarget.dataset.type
    this.setData({
      loginType: type
    })
  },
  
  /**
   * 账号密码登录
   */
  onPasswordLogin() {
    const { username, password, captchaEnabled, code, uuid } = this.data
    
    if (!username) {
      wx.showToast({ icon: 'none', title: '请输入用户名' })
      return
    }
    
    if (!password) {
      wx.showToast({ icon: 'none', title: '请输入密码' })
      return
    }
    
    if (captchaEnabled && !code) {
      wx.showToast({ icon: 'none', title: '请输入验证码' })
      return
    }
    
    this.setData({ loading: true })
    
    const loginData = { username, password }
    if (captchaEnabled) {
      loginData.validateCode = code
    }
    
    login(loginData).then(res => {
      console.log('账号密码登录成功:', res)
      // 获取用户信息
      return getInfo()
    }).then(res => {
      console.log('获取用户信息成功:', res)
      // 获取用户信息并保存
      const app = getApp()
      const user = res.user || {}
      app.globalData.user = user
      app.globalData.roles = res.roles || []
      app.globalData.permissions = res.permissions || []
      wx.setStorageSync('vuex_avatar', user.avatar || (config.baseUrl + '/img/profile.jpg'))
      wx.setStorageSync('vuex_name', user.userName || '')
      wx.setStorageSync('vuex_roles', res.roles || [])
      wx.setStorageSync('vuex_permissions', res.permissions || [])
      wx.switchTab({ url: '/pages/mine/index' })
    }).catch((error) => {
      console.error('账号密码登录失败:', error)
      wx.showToast({ icon: 'none', title: error.msg || '登录失败，请重试' })
      // 登录失败后，重新获取验证码
      if (captchaEnabled) {
        this.getCodeImg()
      }
    }).finally(() => {
      this.setData({ loading: false })
    })
  },
  
  /**
   * 用户名输入
   */
  onUsernameInput(e) {
    this.setData({
      username: e.detail.value
    })
  },
  
  /**
   * 密码输入
   */
  onPasswordInput(e) {
    this.setData({
      password: e.detail.value
    })
  },
  
  /**
   * 验证码输入
   */
  onCodeInput(e) {
    this.setData({ code: e.detail.value })
  },
  
  /**
   * 测试验证码接口
   */
  testCaptcha() {
    const config = require('../../config')
    
    wx.request({
      url: `${config.baseUrl}/captcha/captchaImage?type=math&random=${Math.random()}`,
      method: 'GET',
      responseType: 'arraybuffer',
      header: {
        'Content-Type': 'image/jpeg'
      },
      timeout: 10000,
      success: (res) => {
        // 尝试直接转换并显示
        if (res.data && res.data.byteLength > 0) {
          const base64 = wx.arrayBufferToBase64(res.data);
          const imgUrl = 'data:image/jpeg;base64,' + base64;
        }
      }
    })
  },
  
  /**
   * 获取验证码
   */
  getCodeImg() {
    const config = require('../../config')
    const { setSessionId } = require('../../utils/auth')
    
    // 直接使用wx.request调用验证码接口，添加type=math参数
    wx.request({
      url: `${config.baseUrl}/captcha/captchaImage?type=math&random=${Math.random()}`,
      method: 'GET',
      responseType: 'arraybuffer',
      header: {},
      timeout: 20000,
      success: (res) => {
        // 从响应头中获取Set-Cookie并保存Session ID
        const setCookie = res.header['Set-Cookie'] || res.header['set-cookie'];
        if (setCookie) {
          const sessionIdMatch = setCookie.match(/JSESSIONID=([^;]+)/);
          if (sessionIdMatch) {
            setSessionId(sessionIdMatch[1]);
          }
        }
        
        // 转换为base64并显示
        if (res.data && res.data.byteLength > 0) {
          const base64 = wx.arrayBufferToBase64(res.data);
          const codeUrl = 'data:image/jpeg;base64,' + base64;
          this.setData({
            captchaEnabled: true,
            codeUrl: codeUrl
          });
        } else {
          this.setData({
            captchaEnabled: false
          });
        }
      }
    })
  },
  
  /**
   * 微信登录
   */
  onWechatLogin() {
    console.log('开始微信登录流程')
    this.setData({ loading: true })
    
    // 1. 获取微信登录code
    wx.login({
      success: (loginRes) => {
        console.log('获取微信登录code成功:', loginRes.code)
        if (loginRes.code) {
          // 2. 调用微信授权登录API
          wechatLogin({
            code: loginRes.code
          }).then(res => {
            console.log('后端登录成功:', res)
            // 保存token
            if (res.token) {
              const { setSessionId } = require('../../utils/auth')
              setSessionId(res.token)
            }
            // 3. 获取用户信息
            return getInfo()
          }).then(res => {
            console.log('获取用户信息成功:', res)
            // 4. 获取用户信息并保存
            const app = getApp()
            const user = res.user || {}
            app.globalData.user = user
            app.globalData.roles = res.roles || []
            app.globalData.permissions = res.permissions || []
            wx.setStorageSync('vuex_avatar', user.avatar || (config.baseUrl + '/img/profile.jpg'))
            wx.setStorageSync('vuex_name', user.userName || '')
            wx.setStorageSync('vuex_roles', res.roles || [])
            wx.setStorageSync('vuex_permissions', res.permissions || [])
            wx.switchTab({ url: '/pages/mine/index' })
          }).catch((error) => {
            console.error('微信登录失败:', error)
            wx.showToast({ icon: 'none', title: '登录失败，请重试' })
          }).finally(() => {
            this.setData({ loading: false })
          })
        } else {
          console.error('获取微信登录code失败:', loginRes.errMsg)
          wx.showToast({ icon: 'none', title: '登录失败，请重试' })
          this.setData({ loading: false })
        }
      },
      fail: (err) => {
        console.error('调用wx.login失败:', err)
        wx.showToast({ icon: 'none', title: '登录失败，请重试' })
        this.setData({ loading: false })
      }
    })
  }
})
