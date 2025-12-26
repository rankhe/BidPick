const TokenKey = 'App-Token'
const SessionIdKey = 'App-SessionId'

function getToken() {
  return wx.getStorageSync(TokenKey) || ""
}

function setToken(token) {
  wx.setStorageSync(TokenKey, token)
}

function removeToken() {
  wx.removeStorageSync(TokenKey)
}

function getSessionId() {
  return wx.getStorageSync(SessionIdKey) || ""
}

function setSessionId(sessionId) {
  wx.setStorageSync(SessionIdKey, sessionId)
}

function removeSessionId() {
  wx.removeStorageSync(SessionIdKey)
}

module.exports = {
  TokenKey,
  getToken,
  setToken,
  removeToken,
  SessionIdKey,
  getSessionId,
  setSessionId,
  removeSessionId
}
