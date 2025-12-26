const { ensureLogin } = require('../../../utils/guard')
const { getProductList, submitQuote } = require('../../../api/product')
const config = require('../../../config')

Page({
  data: {
    merchantId: '',
    merchantName: '',
    batches: [],
    loading: false
  },
  onLoad(options) {
    ensureLogin()
    const { merchantId } = options || {}
    if (merchantId) {
      this.setData({ merchantId })
      this.loadMerchantData()
    }
  },
  loadMerchantData() {
    this.setData({ loading: true })
    getProductList({ merchantId: this.data.merchantId })
      .then(res => {
        const data = (res && res.data) || {}
        const merchant = Array.isArray(res) ? res[0] : data
        const merchantName = merchant.merchantName || ''
        const batches = (merchant.batches || []).map(b => ({ ...b, expanded: false }))
        this.setData({ merchantName, batches })
      })
      .catch(err => {
        console.error('获取商家数据失败:', err)
        wx.showToast({ icon: 'none', title: '获取商家数据失败' })
      })
      .finally(() => {
        this.setData({ loading: false })
      })
  },
  toggleBatch(e) {
    const batchIndex = e.currentTarget.dataset.batchIndex
    const batches = [...this.data.batches]
    batches[batchIndex].expanded = !batches[batchIndex].expanded
    this.setData({ batches })
  },
  onInputQuotePrice(e) {
    const { batchIndex, productIndex } = e.currentTarget.dataset
    const value = e.detail.value
    const batches = [...this.data.batches]
    const products = [...batches[batchIndex].products]
    products[productIndex].quotePriceInput = value
    batches[batchIndex].products = products
    this.setData({ batches })
  },
  submitProductQuote(e) {
    const { batchIndex, productIndex, productId } = e.currentTarget.dataset
    const batches = [...this.data.batches]
    const product = batches[batchIndex].products[productIndex]
    const price = Number(product.quotePriceInput)
    if (!price || price <= 0) {
      wx.showToast({ icon: 'none', title: '请输入有效报价' })
      return
    }
    wx.showLoading({ title: '提交中', mask: true })
    submitQuote({ productId, price })
      .then(() => {
        wx.hideLoading()
        wx.showToast({ icon: 'success', title: '报价已提交' })
        wx.showModal({
          title: '订阅通知',
          content: '是否接受中标结果通知？',
          confirmText: '接受',
          cancelText: '不接受',
          success: (res) => {
            if (res.confirm) {
              const tmplIds = (config && config.subscribeTemplateIds) || []
              if (Array.isArray(tmplIds) && tmplIds.length > 0) {
                wx.requestSubscribeMessage({
                  tmplIds,
                  success: () => {
                    wx.showToast({ icon: 'none', title: '订阅成功' })
                  },
                  fail: () => {
                    wx.showToast({ icon: 'none', title: '订阅失败或已拒绝' })
                  }
                })
              } else {
                wx.showToast({ icon: 'none', title: '未配置订阅模板ID' })
              }
            }
          }
        })
      })
      .catch(err => {
        wx.hideLoading()
        console.error('提交报价失败:', err)
        wx.showToast({ icon: 'none', title: '提交报价失败' })
      })
  }
})
