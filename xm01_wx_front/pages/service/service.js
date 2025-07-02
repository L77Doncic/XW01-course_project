Page({
    data: {
      showsearch: false,   // 显示搜索按钮
      searchtext: '',      // 搜索文字
      todolist: [],       // 待办事项列表
      scrolltop: null,     // 滚动位置
      page: 0             // 分页
    },
    
    onLoad: function() {  // 加载数据渲染页面
      this.fetchTodoData();
    },
    
    fetchTodoData: function() {  // 获取待办事项数据
      let _this = this;
      wx.showToast({
        title: '加载中',
        icon: 'loading'
      });
      
      this.setData({
        page: this.data.page + 1
      });
      
      const page = this.data.page;
      const perpage = 10;
      const newlist = [];
      
      for (let i = 0; i < perpage; i++) {
        newlist.push({
          id: (page - 1) * perpage + i + 1,
          title: `待办事项 ${(page - 1) * perpage + i + 1}`,
          completed: false
        });
      }
      
      setTimeout(() => {
        _this.setData({
          todolist: _this.data.todolist.concat(newlist)
        });
        wx.hideToast();
      }, 500);
    },
    
    inputSearch: function(e) {  // 输入搜索文字
      this.setData({
        showsearch: e.detail.cursor > 0,
        searchtext: e.detail.value
      });
    },
    
    submitSearch: function() {  // 提交搜索
      console.log('搜索内容:', this.data.searchtext);
      // 这里可以添加搜索逻辑
    },
    
    toggleComplete: function(e) {  // 切换完成状态
      const index = e.currentTarget.dataset.index;
      const todos = this.data.todolist;
      todos[index].completed = !todos[index].completed;
      
      this.setData({
        todolist: todos
      });
    },
    
    scrollHandle: function(e) {  // 滚动事件
      this.setData({
        scrolltop: e.detail.scrollTop
      });
    },
    
    goToTop: function() {  // 回到顶部
      this.setData({
        scrolltop: 0
      });
    },
    
    scrollLoading: function() {  // 滚动加载更多
      this.fetchTodoData();
    },
    
    onPullDownRefresh: function() {  // 下拉刷新
      this.setData({
        page: 0,
        todolist: []
      });
      this.fetchTodoData();
      setTimeout(() => {
        wx.stopPullDownRefresh();
      }, 500);
    }
  });