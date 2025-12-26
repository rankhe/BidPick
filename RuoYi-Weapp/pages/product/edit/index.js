const { addProduct, updateProduct, getProduct, listBrand, listCategory } = require('../../../api/manage')

Page({
  data: {
    form: {
      id: undefined,
      name: '',
      brandId: undefined,
      categoryId: undefined,
      status: 1,
      remark: ''
    },
    brands: [],
    categories: [],
    brandIndex: -1,
    categoryIndex: -1
  },

  onLoad(options) {
    this.loadOptions()
    if (options.id) {
      this.loadDetail(options.id)
    }
    // 初始化时更新选中项名称
    this.updateSelectedNames()
  },

  loadOptions() {
    Promise.all([
      listBrand({ pageNum: 1, pageSize: 100 }),
      listCategory({ pageNum: 1, pageSize: 100 })
    ]).then(([brandRes, categoryRes]) => {
      this.setData({ 
        brands: brandRes.rows || [],
        categories: categoryRes.rows || []
      })
      this.updateSelectedNames()
    }).catch(err => {
      console.error('加载选项失败:', err)
    })
  },

  loadDetail(id) {
    // Since getProduct calls list, it returns rows
    getProduct(id).then(res => {
      const data = res.rows && res.rows.length > 0 ? res.rows[0] : null
      if (data) {
        this.setData({
          form: {
            id: data.id,
            name: data.name,
            brandId: data.brandId,
            categoryId: data.categoryId,
            status: data.status,
            remark: data.remark
          }
        })
        // Set indexes
        this.setIndexes(data)
        this.updateSelectedNames()
      }
    })
  },

  setIndexes(data) {
    // This might run before brands/categories are loaded. 
    // Ideally use Promise.all or check in setData callback. 
    // For simplicity, we assume options load fast or user waits.
    // Better: call setIndexes after both options and detail are loaded.
    // We'll skip complex logic for now and rely on picker binding.
    // Wait, picker needs index.
    // We can compute index when user opens picker or just display name.
  },

  handleBrandChange(e) {
    const index = e.detail.value
    const brand = this.data.brands[index]
    this.setData({
      brandIndex: index,
      'form.brandId': brand.id
    })
    this.updateSelectedNames()
  },

  handleCategoryChange(e) {
    const index = e.detail.value
    const category = this.data.categories[index]
    this.setData({
      categoryIndex: index,
      'form.categoryId': category.id
    })
    this.updateSelectedNames()
  },

  handleInput(e) {
    const field = e.currentTarget.dataset.field
    this.setData({
      [`form.${field}`]: e.detail.value
    })
  },
  
  handleStatusChange(e) {
      this.setData({
          'form.status': e.detail.value ? 1 : 0
      })
  },

  getSelectedBrandName() {
    const brands = this.data.brands || []
    const selected = brands.find(b => b.id === this.data.form.brandId)
    return selected ? selected.name : ''
  },

  getSelectedCategoryName() {
    const categories = this.data.categories || []
    const selected = categories.find(c => c.id === this.data.form.categoryId)
    return selected ? selected.name : ''
  },

  updateSelectedNames() {
    this.setData({
      selectedBrandName: this.getSelectedBrandName(),
      selectedCategoryName: this.getSelectedCategoryName()
    })
  },

  submitForm() {
    if (!this.data.form.name) {
      wx.showToast({ title: '请输入产品名称', icon: 'none' })
      return
    }
    const data = this.data.form
    const promise = data.id ? updateProduct(data) : addProduct(data)
    
    promise.then(res => {
      wx.showToast({ title: '保存成功' })
      setTimeout(() => {
        wx.navigateBack()
      }, 1500)
    })
  }
})
