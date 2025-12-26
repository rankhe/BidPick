function getRoles() {
  const app = getApp()
  return app?.globalData?.roles && app.globalData.roles.length ? app.globalData.roles : (wx.getStorageSync('vuex_roles') || [])
}

function getPermissions() {
  const app = getApp()
  return app?.globalData?.permissions && app.globalData.permissions.length ? app.globalData.permissions : (wx.getStorageSync('vuex_permissions') || [])
}

function hasPermi(value) {
  if (value && Array.isArray(value) && value.length > 0) {
    const permissions = getPermissions()
    const allPermission = "*:*:*"
    return permissions.some(p => allPermission === p || value.includes(p))
  }
  return false
}

function hasRole(value) {
  if (value && Array.isArray(value) && value.length > 0) {
    const roles = getRoles()
    const superAdmin = "admin"
    return roles.some(r => superAdmin === r || value.includes(r))
  }
  return false
}

module.exports = {
  hasPermi,
  hasRole
}
