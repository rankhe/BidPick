const { ensureLogin } = require('../../utils/guard')
const { listDept } = require('../../api/system/dept')
const { hasPermi } = require('../../utils/permission')

Page({
  data: {
    merchantList: [],
    loading: false,
    isOperationUser: false
  },
  onShow() {
    if (ensureLogin()) {
      this.checkPermission()
      // Only load merchant list if NOT operation user, or load both?
      // User might be both. Let's load both.
      this.loadMerchants()
    }
  },
  checkPermission() {
    // Check if user has product management permission
    const canManageProduct = hasPermi(['product:info:list'])
    const canManageBatch = hasPermi(['product:batch:list'])
    
    this.setData({
      isOperationUser: canManageProduct || canManageBatch
    })
  },
  loadMerchants() {
    this.setData({ loading: true })
    listDept({ parentId: 200 }).then(res => {
      this.setData({ merchantList: res.data || [] })
    }).catch(err => {
      console.error('获取商家列表失败:', err)
      // wx.showToast({ icon: 'none', title: '获取商家列表失败' })
    }).finally(() => {
      this.setData({ loading: false })
    })
  },
  viewMerchantDetail(e) {
    const merchantId = e.currentTarget.dataset.merchantId
    wx.navigateTo({
      url: `/pages/merchant/detail/index?merchantId=${merchantId}`
    })
  },
  
  // Navigation Methods
  navTo(e) {
    const url = e.currentTarget.dataset.url
    wx.navigateTo({ url })
  }
})
