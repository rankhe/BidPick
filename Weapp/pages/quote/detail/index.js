const { ensureLogin } = require('../../../utils/guard')

Page({
  data: {
    quoteId: '',
    quoteDetail: {},
    loading: false
  },
  onLoad(options) {
    ensureLogin()
    if (options.quoteId) {
      this.setData({
        quoteId: options.quoteId
      })
      this.getQuoteDetail()
    }
  },
  
  /**
   * 获取报价详情
   */
  getQuoteDetail() {
    this.setData({ loading: true })
    
    // 这里应该调用获取报价详情的API
    // 暂时使用模拟数据
    setTimeout(() => {
      this.setData({
        quoteDetail: {
          quoteId: this.data.quoteId,
          productName: '测试产品',
          batchName: '2023051001',
          merchantName: '测试商家',
          quotePrice: 100.00,
          marketPrice: 120.00,
          quoteTime: '2023-05-10 10:30:00',
          status: '已提交',
          remark: '这是一个测试报价'
        },
        loading: false
      })
    }, 1000)
  }
})