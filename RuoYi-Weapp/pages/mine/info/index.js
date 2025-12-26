const { getUserProfile, updateUserProfile } = require('../../../api/system/user')

Page({
  data: {
    form: {
      nickName: '',
      phonenumber: '',
      email: ''
    },
    loading: false
  },
  onShow() {
    getUserProfile().then(res => {
      const user = res.data || {}
      this.setData({
        form: {
          nickName: user.nickName || '',
          phonenumber: user.phonenumber || '',
          email: user.email || ''
        }
      })
    })
  },
  onInput(e) {
    const field = e.currentTarget.dataset.field
    const value = e.detail.value
    const form = this.data.form
    form[field] = value
    this.setData({ form })
  },
  onSubmit() {
    this.setData({ loading: true })
    updateUserProfile(this.data.form).then(() => {
      wx.showToast({ icon: 'success', title: '保存成功' })
      wx.navigateBack()
    }).finally(() => {
      this.setData({ loading: false })
    })
  }
})
