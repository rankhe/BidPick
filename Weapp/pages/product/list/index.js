const { listProduct, delProduct } = require('../../../api/manage')

Page({
  data: {
    list: [],
    total: 0,
    queryParams: {
      pageNum: 1,
      pageSize: 10,
      name: undefined,
      categoryName: undefined
    },
    loading: false
  },

  onShow() {
    this.getList()
  },

  getList() {
    this.setData({ loading: true })
    listProduct(this.data.queryParams).then(res => {
      // Backend usually returns rows and total
      const rows = res.rows || []
      this.setData({
        list: this.data.queryParams.pageNum === 1 ? rows : this.data.list.concat(rows),
        total: res.total,
        loading: false
      })
    }).catch(err => {
      this.setData({ loading: false })
    })
  },

  onReachBottom() {
    if (this.data.list.length < this.data.total) {
      this.setData({
        'queryParams.pageNum': this.data.queryParams.pageNum + 1
      })
      this.getList()
    }
  },

  onPullDownRefresh() {
    this.setData({
      'queryParams.pageNum': 1
    })
    this.getList()
    wx.stopPullDownRefresh()
  },

  handleSearch(e) {
    this.setData({
      'queryParams.name': e.detail.value,
      'queryParams.pageNum': 1
    })
    this.getList()
  },

  handleAdd() {
    wx.navigateTo({
      url: '/pages/product/edit/index'
    })
  },

  handleEdit(e) {
    const id = e.currentTarget.dataset.id
    wx.navigateTo({
      url: `/pages/product/edit/index?id=${id}`
    })
  },

  handleDelete(e) {
    const id = e.currentTarget.dataset.id
    const that = this
    wx.showModal({
      title: '提示',
      content: '确定要删除该产品吗？',
      success(res) {
        if (res.confirm) {
          delProduct(id).then(() => {
            wx.showToast({ title: '删除成功' })
            that.onPullDownRefresh()
          })
        }
      }
    })
  }
})
