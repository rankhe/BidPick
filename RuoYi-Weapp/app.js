App({
  globalData: {
    sessionId: "",
    user: null,
    roles: [],
    permissions: []
  },
  onLaunch() {
    const sessionId = wx.getStorageSync('App-SessionId') || ""
    if (sessionId) {
      this.globalData.sessionId = sessionId
    }
  }
})
