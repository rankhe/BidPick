const request = require('../utils/request')

/**
 * 获取商品列表（按商家-批次-商品维度）
 * @param {Object} params - 查询参数
 * @returns {Promise}
 */
function getProductList(params) {
  return request({
    url: '/api/product/list',
    method: 'GET',
    params: params
  })
}

/**
 * 获取商品报价历史
 * @param {Object} params - 查询参数
 * @returns {Promise}
 */
function getQuoteHistory(params) {
  return request({
    url: '/api/quote/history',
    method: 'GET',
    params: params
  })
}

/**
 * 提交商品报价
 * @param {Object} data - 报价数据 { productId, price }
 * @returns {Promise}
 */
function submitQuote(data) {
  return request({
    url: '/api/quote/submit',
    method: 'POST',
    data
  })
}

module.exports = {
  getProductList,
  getQuoteHistory,
  submitQuote
}
