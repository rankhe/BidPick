const { listBlacklist, delBlacklist } = require('../../../api/manage')

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
    listBlacklist(this.data.queryParams).then(res => {
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

  handleAdd() {
    wx.navigateTo({
      url: '/pages/blackuser/add/index'
    })
  },

  handleRemove(e) {
    const id = e.currentTarget.dataset.id
    const that = this
    wx.showModal({
      title: '提示',
      content: '确定要将该用户移出黑名单吗？',
      success(res) {
        if (res.confirm) {
          delBlacklist(id).then(() => {
            wx.showToast({ title: '移除成功' })
            that.onPullDownRefresh()
          })
        }
      }
    })
  }
})
