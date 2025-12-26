const { listCategory } = require('../../../api/manage')

Page({
  data: {
    list: [],
    loading: false
  },

  onShow() {
    this.getList()
  },

  getList() {
    this.setData({ loading: true })
    listCategory({ pageNum: 1, pageSize: 100 }).then(res => {
      this.setData({
        list: res.rows || [],
        loading: false
      })
    })
  }
})
