const request = require('../utils/request')

// --- Product Management ---

// List Products
export function listProduct(query) {
  return request({
    url: '/product/info/list',
    method: 'post',
    data: query
  })
}

// Get Product Detail (via list)
export function getProduct(id) {
  return request({
    url: '/product/info/list',
    method: 'post',
    data: { id: id }
  })
}

// Add Product
export function addProduct(data) {
  return request({
    url: '/product/info/add',
    method: 'post',
    data: data
  })
}

// Update Product
export function updateProduct(data) {
  return request({
    url: '/product/info/edit',
    method: 'post',
    data: data
  })
}

// Delete Product
export function delProduct(ids) {
  return request({
    url: '/product/info/remove',
    method: 'post',
    data: ids // Usually passed as string "1,2,3" or form data
  })
}

// --- Batch Management ---

export function listBatch(query) {
  return request({
    url: '/product/batch/list',
    method: 'post',
    data: query
  })
}

export function addBatch(data) {
  return request({
    url: '/product/batch/add',
    method: 'post',
    data: data
  })
}

// --- Brand Management ---

export function listBrand(query) {
  return request({
    url: '/product/brand/list',
    method: 'post',
    data: query
  })
}

export function addBrand(data) {
  return request({
    url: '/product/brand/add',
    method: 'post',
    data: data
  })
}

// --- Category Management ---

export function listCategory(query) {
  return request({
    url: '/product/category/list',
    method: 'post',
    data: query
  })
}

export function addCategory(data) {
  return request({
    url: '/product/category/add',
    method: 'post',
    data: data
  })
}

// --- Quotation Management ---

export function listQuote(query) {
  return request({
    url: '/product/history/list',
    method: 'post',
    data: query
  })
}

// --- Blacklist Management ---

export function listBlacklist(query) {
  return request({
    url: '/product/blackUser/list',
    method: 'post',
    data: query
  })
}

export function addBlacklist(data) {
  return request({
    url: '/product/blackUser/add',
    method: 'post',
    data: data
  })
}

export function delBlacklist(ids) {
  return request({
    url: '/product/blackUser/remove',
    method: 'post',
    data: ids
  })
}
