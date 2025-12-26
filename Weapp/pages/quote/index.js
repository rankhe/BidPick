const { ensureLogin } = require('../../utils/guard')
const { getQuoteHistory } = require('../../api/product')

Page({
  data: {
    quoteList: [], // 报价历史列表
    loading: false
  },
  onShow() {
    if (ensureLogin()) {
      this.getQuoteHistory()
    }
  },
  
  /**
   * 获取报价历史
   */
  getQuoteHistory() {
    this.setData({ loading: true })
    
    getQuoteHistory().then(res => {
      this.setData({
        quoteList: res.data || []
      })
    }).catch(err => {
      console.error('获取报价历史失败:', err)
      wx.showToast({ icon: 'none', title: '获取报价历史失败' })
    }).finally(() => {
      this.setData({ loading: false })
    })
  },
  
  /**
   * 查看报价详情
   */
  viewQuoteDetail(e) {
    const quoteId = e.currentTarget.dataset.quoteId
    wx.navigateTo({
      url: `/pages/quote/detail/index?quoteId=${quoteId}`
    })
  }
})