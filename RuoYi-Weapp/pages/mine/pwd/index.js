const { updateUserPwd } = require('../../../api/system/user')

Page({
  data: {
    oldPassword: '',
    newPassword: '',
    loading: false
  },
  onInput(e) {
    const field = e.currentTarget.dataset.field
    const value = e.detail.value
    this.setData({ [field]: value })
  },
  onSubmit() {
    const { oldPassword, newPassword } = this.data
    if (!oldPassword || !newPassword) {
      wx.showToast({ icon: 'none', title: '请输入旧/新密码' })
      return
    }
    this.setData({ loading: true })
    updateUserPwd(oldPassword, newPassword).then(() => {
      wx.showToast({ icon: 'success', title: '修改成功' })
      wx.navigateBack()
    }).finally(() => {
      this.setData({ loading: false })
    })
  }
})
