<template>
  <div class="app-container">
    <!-- 查询行 -->
    <el-form
      :model="queryParams"
      ref="queryRef"
      :inline="true"
      v-show="showSearch"
      label-width="68px"
    >
      <el-form-item label="标题" prop="title">
        <el-input
          v-model="queryParams.title"
          placeholder="请输入标题"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="类型" prop="type">
        <el-input
          v-model="queryParams.type"
          placeholder="请输入类型"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="创建者" prop="creator">
        <el-input
          v-model="queryParams.creator"
          placeholder="请输入创建者"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="Search" @click="handleQuery">搜索</el-button>
        <el-button icon="Refresh" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>
    <!-- 操作按钮行 -->
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="Plus"
          @click="handleAdd"
          v-hasPermi="['todo:todo:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="info"
          plain
          icon="Printer"
          @click="handlePrint"
          v-hasPermi="['todo:todo:print']"
        >打印</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="Edit"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['todo:todo:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="Delete"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['todo:todo:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="Download"
          @click="handleExport"
          v-hasPermi="['todo:todo:export']"
        >导出</el-button>
      </el-col>
      <!-- v-hasPermi权限控制 -->
      <el-col :span="1.5">
        <el-button
          type="info"
          plain
          icon="TrendCharts"
          @click="handleStats"
          v-hasPermi="['todo:todo:stats']"
        >统计</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="Link"
          @click="handleThirdPartyAPI"
          v-hasPermi="['todo:todo:api']"
        >外部数据</el-button>
      </el-col>

      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table
      v-loading="loading"
      :data="todoList"
      @selection-change="handleSelectionChange"
      :default-sort="defaultSort"
      @sort-change="handleSortChange"
    >
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column
        label="id"
        align="center"
        prop="id"
        sortable="custom"
        :sort-orders="['descending', 'ascending']"
      />
      <el-table-column label="标题" align="center" prop="title" />
      <el-table-column
        label="内容"
        align="center"
        width="100"
        prop="content"
        :show-overflow-tooltip="true"
      />
      <el-table-column label="类型" align="center" width="60" prop="type" />
      <el-table-column label="已截止" align="center" width="80" prop="endTag" />
      <el-table-column
        label="开始时间"
        align="center"
        prop="beginTime"
        width="140"
        sortable="custom"
        :sort-orders="['descending', 'ascending']"
      >
        <template #default="scope">
          <span>{{ parseTime(scope.row.beginTime,'{y}-{m}-{d} ') }}</span>
        </template>
      </el-table-column>
      <el-table-column
        label="结束时间"
        align="center"
        prop="endTime"
        width="140"
        sortable="custom"
        :sort-orders="['descending', 'ascending']"
      >
        <template #default="scope">
          <span>{{ parseTime(scope.row.endTime, '{y}-{m}-{d} ') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="创建者" align="center" width="80" prop="creator" />
      <el-table-column
        label="创建时间"
        align="center"
        prop="createTime"
        width="180"
        sortable="custom"
        :sort-orders="['descending', 'ascending']"
      >
        <template #default="scope">
          <span>{{ parseTime(scope.row.createTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width" width="250">
        <template #default="scope">
          <el-button
            link
            type="info"
            icon="View"
            @click="handleView(scope.row)"
            v-hasPermi="['todo:todo:view']"
          >详情</el-button>
          <el-button
            link
            type="success"
            icon="Edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['todo:todo:edit']"
          >修改</el-button>

          <el-button
            link
            type="primary"
            icon="Delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['todo:todo:remove']"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <!-- 控制分页 -->
    <pagination
      v-show="total>0"
      :total="total"
      v-model:page="queryParams.pageNum"
      v-model:limit="queryParams.pageSize"
      @pagination="getList"
    />
    <!-- 统计功能 -->
    <div v-if="isStatsVisible">
      <h3>统计表</h3>
      <statistics-table :data="statsData"></statistics-table>
    </div>

    <el-dialog :title="title" v-model="isStatsVisible" width="1000px" append-to-body>
      <!-- <h3>待办事项统计表</h3> -->
      <div style="display: flex; justify-content: center; align-items: center; height: 100%;">
        <statistics-table :data="statsData"></statistics-table>
      </div>
      <template #footer>
        <div class="dialog-footer">
          <el-button type="primary" @click="handleStats">关闭</el-button>
        </div>
      </template>
    </el-dialog>

    <!-- 添加或修改待办事项对话框 -->
    <el-dialog :title="title" v-model="open" width="500px" append-to-body>
      <el-form ref="todoRef" :model="form" :rules="rules" label-width="80px">
        <!-- <el-form-item label="${comment}" prop="parentId">
          <el-input v-model="form.parentId" placeholder="请输入${comment}" />
        </el-form-item>-->
        <el-form-item label="标题" prop="title">
          <el-input v-model="form.title" placeholder="请输入标题" />
        </el-form-item>
        <el-form-item label="内容">
          <!-- <editor v-model="form.content" :min-height="192"/> //富文本框-->
          <el-input v-model="form.content" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="类型" prop="type">
          <el-input v-model="form.type" placeholder="请输入类型" />
        </el-form-item>
        <el-form-item label="截止时间" prop="limitTime">
          <el-date-picker
            clearable
            v-model="form.limitTime"
            type="date"
            value-format="YYYY-MM-DD"
            placeholder="请选择截止时间"
          ></el-date-picker>
        </el-form-item>
        <el-form-item label="开始时间" prop="beginTime">
          <el-date-picker
            clearable
            v-model="form.beginTime"
            type="date"
            value-format="YYYY-MM-DD"
            placeholder="请选择开始时间"
          ></el-date-picker>
        </el-form-item>
        <el-form-item label="结束时间" prop="endTime">
          <el-date-picker
            clearable
            v-model="form.endTime"
            type="date"
            value-format="YYYY-MM-DD"
            placeholder="请选择结束时间"
          ></el-date-picker>
        </el-form-item>
        <el-form-item label="创建者" prop="creator">
          <el-input v-model="form.creator" placeholder="请输入创建者" />
        </el-form-item>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button type="primary" @click="submitForm">确 定</el-button>
          <el-button @click="cancel">取 消</el-button>
        </div>
      </template>
    </el-dialog>
    <!-- 详情事项对话框 -->
    <el-dialog :title="title" v-model="view" width="500px" append-to-body>
      <el-form ref="todoRef" :model="form" label-width="80px">
        <el-form-item label="标题" prop="title">
          <el-input v-model="form.title" placeholder="请输入标题" readonly />
        </el-form-item>
        <el-form-item label="内容">
          <el-input v-model="form.content" type="textarea" placeholder="请输入内容" readonly />
        </el-form-item>
        <el-form-item label="类型" prop="type">
          <el-input v-model="form.type" placeholder="请输入类型" readonly />
        </el-form-item>
        <el-form-item label="截止时间" prop="limitTime">
          <el-date-picker
            clearable
            v-model="form.limitTime"
            type="date"
            value-format="YYYY-MM-DD"
            placeholder="请选择截止时间"
            readonly
          ></el-date-picker>
        </el-form-item>
        <el-form-item label="开始时间" prop="beginTime">
          <el-date-picker
            clearable
            v-model="form.beginTime"
            type="date"
            value-format="YYYY-MM-DD"
            placeholder="请选择开始时间"
            readonly
          ></el-date-picker>
        </el-form-item>
        <el-form-item label="结束时间" prop="endTime">
          <el-date-picker
            clearable
            v-model="form.endTime"
            type="date"
            value-format="YYYY-MM-DD"
            placeholder="请选择结束时间"
            readonly
          ></el-date-picker>
        </el-form-item>
        <el-form-item label="创建者" prop="creator">
          <el-input v-model="form.creator" placeholder="请输入创建者" readonly />
        </el-form-item>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button type="primary" @click="cancelview">关闭</el-button>
        </div>
      </template>
    </el-dialog>
  </div>
</template>

<script setup name="Todo">
import {
  listTodo,
  getTodo,
  delTodo,
  addTodo,
  updateTodo
} from "@/api/todo/todo";
import StatisticsTable from "./StatisticsTable.vue";
const { proxy } = getCurrentInstance();

const todoList = ref([]);
const open = ref(false); //控制开启修改和增加的edit对话框
const view = ref(false); //控制开启详情对话框
const loading = ref(true);
const showSearch = ref(true);
const ids = ref([]);
const single = ref(true);
const multiple = ref(true);
const total = ref(0);
const title = ref(""); //对话框标题
const defaultSort = ref({ prop: "operTime", order: "descending" });
const isStatsVisible = ref(false); //控制统计对话框是否显示
const statsData = ref([]); //经处理得到的统计数据

const data = reactive({
  form: {},
  queryParams: {
    pageNum: 1,
    pageSize: 10,
    title: null,
    type: null,
    endTag: null,
    creator: null
  },
  rules: {
    title: [{ required: true, message: "标题不能为空", trigger: "blur" }],
    content: [{ required: true, message: "内容不能为空", trigger: "blur" }],
    creator: [{ required: true, message: "创建者不能为空", trigger: "blur" }],
    createTime: [
      { required: true, message: "创建时间不能为空", trigger: "blur" }
    ]
  }
});

const { queryParams, form, rules } = toRefs(data);

/** 查询待办事项列表 */
function getList() {
  loading.value = true;
  listTodo(queryParams.value).then(response => {
    todoList.value = response.rows; //获取列表数据用于显示列表
    total.value = response.total; //总条数
    loading.value = false;
    console.log("[handleData]todoList=", todoList.value);
  });

  //handleData();//刚刚获取完，就处理数据->好来不及
}

// 取消按钮
function cancel() {
  open.value = false;
  reset();
}
// 关闭查看按钮
function cancelview() {
  view.value = false;
  reset();
}

// 表单重置
function reset() {
  form.value = {
    id: null,
    parentId: null,
    title: null,
    content: null,
    type: null,
    limitTime: null,
    endTag: null,
    beginTime: null,
    endTime: null,
    executor: null,
    status: null,
    topTag: null,
    priority: null,
    userId: null,
    checkTag: null,
    checker: null,
    checkTime: null,
    creator: null,
    creatorId: null,
    createTime: null
  };
  proxy.resetForm("todoRef");
}

/** 搜索按钮操作 */
function handleQuery() {
  queryParams.value.pageNum = 1;
  getList();
}

/** 重置按钮操作 */
function resetQuery() {
  proxy.resetForm("queryRef");
  handleQuery();
}

// 多选框选中数据
function handleSelectionChange(selection) {
  ids.value = selection.map(item => item.id);
  single.value = selection.length != 1;
  multiple.value = !selection.length;
}

/** 新增按钮操作 */
function handleAdd() {
  reset();
  open.value = true; //控制打开添加小窗
  title.value = "添加待办事项";
}

/** 修改按钮操作 */
function handleUpdate(row) {
  reset();
  const _id = row.id || ids.value;
  getTodo(_id).then(response => {
    form.value = response.data;
    open.value = true;
    title.value = "修改待办事项";
  });
}
/** 详情按钮操作 */
function handleView(row) {
  reset();
  const _id = row.id || ids.value;
  getTodo(_id).then(response => {
    form.value = response.data;
    view.value = true;
    title.value = "查看待办事项";
  });
}

/** 提交按钮 */
function submitForm() {
  proxy.$refs["todoRef"].validate(valid => {
    if (valid) {
      if (form.value.id != null) {
        updateTodo(form.value).then(response => {
          proxy.$modal.msgSuccess("修改成功");
          open.value = false;
          getList();
        });
      } else {
        addTodo(form.value).then(response => {
          proxy.$modal.msgSuccess("新增成功");
          open.value = false;
          getList();
        });
      }
    }
  });
}

/** 删除按钮操作 */
function handleDelete(row) {
  const _ids = row.id || ids.value;
  proxy.$modal
    .confirm('是否确认删除待办事项编号为"' + _ids + '"的数据项？')
    .then(function() {
      return delTodo(_ids);
    })
    .then(() => {
      getList();
      proxy.$modal.msgSuccess("删除成功");
    })
    .catch(() => {});
}

/** 导出按钮操作 */
function handleExport() {
  proxy.download(
    "todo/todo/export",
    {
      ...queryParams.value
    },
    `todo_${new Date().getTime()}.xlsx` //文件名
  );
}

/** 打印按钮操作 */
const handlePrint = () => {
  console.log('打印功能被调用');

  if (!todoList.value || todoList.value.length === 0) {
    proxy.$modal.msgWarning('没有数据可打印');
    return;
  }

  // 简单的日期格式化函数
  const formatDate = (dateString) => {
    if (!dateString) return '';
    const date = new Date(dateString);
    return date.toLocaleDateString('zh-CN');
  };

  const printContent = `
    <html>
      <head>
        <title>待办事项列表</title>
        <style>
          body { font-family: Arial, sans-serif; margin: 20px; }
          table { width: 100%; border-collapse: collapse; margin-top: 20px; }
          th, td { border: 1px solid #ddd; padding: 8px; text-align: left; }
          th { background-color: #f2f2f2; }
          h1 { color: #333; }
        </style>
      </head>
      <body>
        <h1>待办事项列表</h1>
        <table>
          <thead>
            <tr>
              <th>ID</th>
              <th>标题</th>
              <th>内容</th>
              <th>类型</th>
              <th>创建者</th>
              <th>创建时间</th>
            </tr>
          </thead>
          <tbody>
            ${todoList.value.map(item => `
              <tr>
                <td>${item.id || ''}</td>
                <td>${item.title || ''}</td>
                <td>${item.content || ''}</td>
                <td>${item.type || ''}</td>
                <td>${item.creator || ''}</td>
                <td>${formatDate(item.createTime)}</td>
              </tr>
            `).join('')}
          </tbody>
        </table>
      </body>
    </html>
  `;

  const printWindow = window.open('', '_blank');
  printWindow.document.write(printContent);
  printWindow.document.close();
  printWindow.print();
};

/** 第三方接口按钮操作 */
const handleThirdPartyAPI = () => {
  console.log('第三方接口被调用');

  // 模拟第三方API调用
  proxy.$modal.msgSuccess('正在调用第三方接口...');

  // 模拟网络延迟
  setTimeout(() => {
    // 模拟第三方API返回的数据
    const mockExternalData = {
      title: '来自第三方系统的待办事项',
      content: '这是一个模拟的第三方API返回的待办事项数据',
      type: '外部系统',
      creator: '第三方系统',
      createTime: new Date().toISOString()
    };

    proxy.$modal.msgSuccess(`成功获取外部数据: ${mockExternalData.title}`);

    // 询问是否导入数据
    proxy.$modal.confirm('是否将外部数据导入到本地？').then(() => {
      const externalTodo = {
        title: `外部导入: ${mockExternalData.title}`,
        content: mockExternalData.content,
        type: '外部导入',
        creator: '系统',
        beginTime: new Date().toISOString().split('T')[0],
        endTime: new Date().toISOString().split('T')[0]
      };

      addTodo(externalTodo).then(response => {
        proxy.$modal.msgSuccess('外部数据导入成功');
        getList();
      }).catch(error => {
        proxy.$modal.msgError('导入失败: ' + error.message);
      });
    }).catch(() => {
      // 用户取消导入
    });
  }, 1000); // 模拟1秒的网络延迟
};

/** 排序触发事件 */
function handleSortChange(column, prop, order) {
  queryParams.value.orderByColumn = column.prop;
  queryParams.value.isAsc = column.order;
  getList();
}

getList();
//统计测试数据
// const statsData = ref([
//   { creator: 'Alice', count: 5 },
//   { creator: 'Bob', count: 8 },
//   { creator: 'Charlie', count: 3 }
// ])
//对数据处理得到用于统计的数组
function handleData() {
  statsData.value = todoList.value.reduce((acc, item) => {
    const existingCreator = acc.find(entry => entry.creator === item.creator);
    if (existingCreator) {
      existingCreator.count += 1;
    } else {
      acc.push({ creator: item.creator, count: 1 });
    }
    return acc;
  }, []);
  console.log("[handleData]statsData=", statsData);
  console.log("[handleData]statsData是数组吗=", Array.isArray(statsData));
}
/** 统计按钮操作 */
function handleStats() {
  isStatsVisible.value = !isStatsVisible.value;
  title.value = "待办事项统计";

  handleData();
}
</script>
