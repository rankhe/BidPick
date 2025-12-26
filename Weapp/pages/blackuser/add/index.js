const { addBlacklist } = require('../../../api/manage')

Page({
  data: {
    form: {
      userId: '',
      batchId: '',
      remark: ''
    }
  },

  handleInput(e) {
    const field = e.currentTarget.dataset.field
    this.setData({
      [`form.${field}`]: e.detail.value
    })
  },

  submitForm() {
    if (!this.data.form.userId) {
      wx.showToast({ title: '请输入用户ID', icon: 'none' })
      return
    }
    addBlacklist(this.data.form).then(res => {
      wx.showToast({ title: '添加成功' })
      setTimeout(() => {
        wx.navigateBack()
      }, 1500)
    })
  }
})
