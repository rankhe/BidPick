const { ensureLogin } = require('../../utils/guard')
const { getProductList } = require('../../api/product')

Page({
  data: {
    merchantList: [], // 商家列表
    loading: false
  },
  onShow() {
    if (ensureLogin()) {
      this.getProducts()
    }
  },
  
  /**
   * 获取商品列表
   */
  getProducts() {
    this.setData({ loading: true })
    
    getProductList().then(res => {
      // 假设后端返回的数据已经按商家-批次-商品维度组织
      // 如果不是，需要在这里进行数据处理
      this.setData({
        merchantList: res.data || []
      })
    }).catch(err => {
      console.error('获取商品列表失败:', err)
      wx.showToast({ icon: 'none', title: '获取商品列表失败' })
    }).finally(() => {
      this.setData({ loading: false })
    })
  },
  
  /**
   * 切换商家展开状态
   */
  toggleMerchant(e) {
    const index = e.currentTarget.dataset.index
    const merchantList = [...this.data.merchantList]
    merchantList[index].expanded = !merchantList[index].expanded
    this.setData({ merchantList })
  },
  
  /**
   * 切换批次展开状态
   */
  toggleBatch(e) {
    const { merchantIndex, batchIndex } = e.currentTarget.dataset
    const merchantList = [...this.data.merchantList]
    const batches = [...merchantList[merchantIndex].batches]
    batches[batchIndex].expanded = !batches[batchIndex].expanded
    merchantList[merchantIndex].batches = batches
    this.setData({ merchantList })
  }
})
