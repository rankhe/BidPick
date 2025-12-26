function toast(content) {
  wx.showToast({
    icon: 'none',
    title: content
  })
}

function showConfirm(content) {
  return new Promise((resolve) => {
    wx.showModal({
      title: '提示',
      content: content,
      cancelText: '取消',
      confirmText: '确定',
      success(res) {
        resolve(res)
      }
    })
  })
}

function tansParams(params) {
  let result = ''
  for (const propName of Object.keys(params)) {
    const value = params[propName]
    const part = encodeURIComponent(propName) + "="
    if (value !== null && value !== "" && typeof (value) !== "undefined") {
      if (typeof value === 'object') {
        for (const key of Object.keys(value)) {
          if (value[key] !== null && value[key] !== "" && typeof (value[key]) !== 'undefined') {
            const p = propName + '[' + key + ']'
            const subPart = encodeURIComponent(p) + "="
            result += subPart + encodeURIComponent(value[key]) + "&"
          }
        }
      } else {
        result += part + encodeURIComponent(value) + "&"
      }
    }
  }
  return result
}

module.exports = {
  toast,
  showConfirm,
  tansParams
}
