const { listQuote } = require('../../../api/manage')

Page({
  data: {
    list: [],
    total: 0,
    queryParams: {
      pageNum: 1,
      pageSize: 10
    },
    loading: false
  },

  onShow() {
    this.getList()
  },

  getList() {
    this.setData({ loading: true })
    listQuote(this.data.queryParams).then(res => {
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
  }
})
