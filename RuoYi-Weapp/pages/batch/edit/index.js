const { addBatch } = require('../../../api/manage')

Page({
  data: {
    form: {
      batchNo: '',
      startTime: '',
      endTime: '',
      remark: ''
    }
  },

  handleInput(e) {
    const field = e.currentTarget.dataset.field
    this.setData({
      [`form.${field}`]: e.detail.value
    })
  },

  handleDateChange(e) {
    const field = e.currentTarget.dataset.field
    this.setData({
      [`form.${field}`]: e.detail.value
    })
  },

  submitForm() {
    if (!this.data.form.batchNo) {
      wx.showToast({ title: '请输入批次号', icon: 'none' })
      return
    }
    addBatch(this.data.form).then(res => {
      wx.showToast({ title: '发布成功' })
      setTimeout(() => {
        wx.navigateBack()
      }, 1500)
    })
  }
})
