const request = require('../utils/request')

/**
 * 获取商品列表（按商家-批次-商品维度）
 * @param {Object} params - 查询参数
 * @returns {Promise}
 */
function getProductList(params) {
  return request({
    url: '/product/info/listWeapp',
    method: 'POST',
    data: params || {}
  })
}

/**
 * 获取商品报价历史
 * @param {Object} params - 查询参数
 * @returns {Promise}
 */
function getQuoteHistory(params) {
  return request({
    url: '/product/history/quoter/listWeapp',
    method: 'POST',
    data: params || {}
  })
}

/**
 * 提交商品报价
 * @param {Object} data - 报价数据 { productId, price }
 * @returns {Promise}
 */
function submitQuote(data) {
  return request({
    url: '/product/history/addWeapp',
    method: 'POST',
    header: { 'Content-Type': 'application/x-www-form-urlencoded' },
    data
  })
}

module.exports = {
  getProductList,
  getQuoteHistory,
  submitQuote
}
