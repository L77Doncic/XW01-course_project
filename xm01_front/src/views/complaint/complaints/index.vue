import StatisticsTable from "./StatisticsTable.vue";

<template>
  <div class="app-container">
    <el-form
      :model="queryParams"
      ref="queryRef"
      :inline="true"
      v-show="showSearch"
      label-width="68px"
    >
      <el-form-item label="投诉时间" prop="time">
        <el-date-picker
          clearable
          v-model="queryParams.time"
          type="date"
          value-format="YYYY-MM-DD"
          placeholder="请选择投诉时间"
        ></el-date-picker>
      </el-form-item>
      <el-form-item label="投诉人" prop="name">
        <el-input
          v-model="queryParams.name"
          placeholder="请输入投诉人姓名"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="Search" @click="handleQuery">搜索</el-button>
        <el-button icon="Refresh" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="Plus"
          @click="handleAdd"
          v-hasPermi="['complaint:complaints:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="Edit"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['complaint:complaints:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="Delete"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['complaint:complaints:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="Download"
          @click="handleExport"
          v-hasPermi="['complaint:complaints:export']"
        >导出</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="info"
          plain
          icon="Printer"
          @click="handlePrint"
          v-hasPermi="['complaint:complaints:print']"
        >打印</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="info"
          plain
          icon="TrendCharts"
          @click="handleStats"
          v-hasPermi="['complaint:complaints:stats']"
        >统计</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="Link"
          @click="handleThirdPartyAPI"
          v-hasPermi="['complaint:complaints:api']"
        >外部数据</el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table
      v-loading="loading"
      :data="complaintsList"
      @selection-change="handleSelectionChange"
      :default-sort="defaultSort"
      @sort-change="handleSortChange"
    >
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column
        label="ID"
        align="center"
        prop="id"
        sortable="custom"
        :sort-orders="['descending', 'ascending']"  
      />
      <el-table-column label="投诉内容" align="center" prop="content" />
      <el-table-column label="投诉时间" align="center" prop="time" width="180"  sortable="custom"
        :sort-orders="['descending', 'ascending']" >
        <template #default="scope">
          <span>{{ parseTime(scope.row.time, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="投诉人姓名" align="center" prop="name" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template #default="scope">
          <el-button
            link
            type="info"
            icon="View"
            @click="handleView(scope.row)"
            v-hasPermi="['complaint:complaints:view']"
          >详情</el-button>
          <el-button
            link
            type="primary"
            icon="Edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['complaint:complaints:edit']"
          >修改</el-button>
          <el-button
            link
            type="primary"
            icon="Delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['complaint:complaints:remove']"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      v-model:page="queryParams.pageNum"
      v-model:limit="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改投诉管理对话框 -->
    <el-dialog :title="title" v-model="open" width="500px" append-to-body>
      <el-form ref="complaintsRef" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="投诉内容" prop="content">
          <el-input v-model="form.content" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="投诉时间" prop="time">
          <el-date-picker
            clearable
            v-model="form.time"
            type="date"
            value-format="YYYY-MM-DD"
            placeholder="请选择投诉时间"
          ></el-date-picker>
        </el-form-item>
        <el-form-item label="投诉人姓名" prop="name">
          <el-input v-model="form.name" placeholder="请输入投诉人姓名" />
        </el-form-item>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button type="primary" @click="submitForm">确 定</el-button>
          <el-button @click="cancel">取 消</el-button>
        </div>
      </template>
    </el-dialog>

    <!-- 统计功能 -->
    <div v-if="isStatsVisible">
      <h3>统计表</h3>
      <statistics-table :data="statsData"></statistics-table>
    </div>

    <!-- 统计对话框 -->
<!--    <el-dialog :title="title" v-model="isStatsVisible" width="1000px" append-to-body>
      <div style="display: flex; justify-content: center; align-items: center; height: 400px;">
        <div v-if="statsData.length === 0" style="text-align: center; color: #999;">
          暂无数据可统计
        </div>
        <statistics-table v-else :data="statsData"></statistics-table>
      </div>
      <template #footer>
        <div class="dialog-footer">
          <el-button type="primary" @click="isStatsVisible = false">关闭</el-button>
        </div>
      </template>
    </el-dialog>
 -->
    <!-- 临时使用表格显示统计 -->
    <el-dialog :title="title" v-model="isStatsVisible" width="800px" append-to-body>
      <el-table :data="statsData" style="width: 100%">
        <el-table-column prop="name" label="投诉人" />
        <el-table-column prop="count" label="投诉次数" />
      </el-table>
      <template #footer>
        <div class="dialog-footer">
          <el-button type="primary" @click="isStatsVisible = false">关闭</el-button>
        </div>
      </template>
    </el-dialog>

     <!-- 查看详情投诉管理对话框 -->
    <el-dialog :title="title" v-model="view" width="500px" append-to-body>
      <el-form ref="complaintsRef" :model="form"  label-width="80px">
        <el-form-item label="投诉内容" prop="content">
          <el-input v-model="form.content" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="投诉时间" prop="time">
          <el-date-picker
            clearable
            v-model="form.time"
            type="date"
            value-format="YYYY-MM-DD"
            placeholder="请选择投诉时间"
          ></el-date-picker>
        </el-form-item>
        <el-form-item label="投诉人" prop="name"  label-width="80px">
          <el-input v-model="form.name" placeholder="请输入投诉人姓名" />
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

<script setup name="Complaints">
import {
  listComplaints,
  getComplaints,
  delComplaints,
  addComplaints,
  updateComplaints
} from "@/api/complaint/complaints";

const { proxy } = getCurrentInstance();
const defaultSort = ref({ prop: "operTime", order: "descending" });
const complaintsList = ref([]);
const open = ref(false);
const loading = ref(true);
const showSearch = ref(true);
const ids = ref([]);
const single = ref(true);
const multiple = ref(true);
const total = ref(0);
const title = ref("");
const isStatsVisible = ref(false); //控制统计对话框是否显示
const statsData = ref([]); //经处理得到的统计数据

const data = reactive({
  form: {},
  queryParams: {
    pageNum: 1,
    pageSize: 10,
    content: null,
    time: null,
    name: null
  },
  rules: {
    content: [{ required: true, message: "投诉内容不能为空", trigger: "blur" }],
    time: [{ required: true, message: "投诉时间不能为空", trigger: "blur" }],
    name: [{ required: true, message: "投诉人姓名不能为空", trigger: "blur" }]
  }
});

//对数据处理得到用于统计的数组
function handleData() {
  console.log("原始数据:", complaintsList.value);

  if (!complaintsList.value || complaintsList.value.length === 0) {
    statsData.value = [];
    return;
  }

  statsData.value = complaintsList.value.reduce((acc, item) => {
    const existingName = acc.find(entry => entry.name === item.name);
    if (existingName) {
      existingName.count += 1;
    } else {
      acc.push({ name: item.name || '未知用户', count: 1 });
    }
    return acc;
  }, []);

  console.log("处理后的统计数据:", statsData.value);
}


/** 统计按钮操作 */
function handleStats() {
  console.log("=== 统计功能调试 ===");
  console.log("当前投诉列表:", complaintsList.value);
  console.log("列表长度:", complaintsList.value?.length);

  handleData();

  console.log("处理后的统计数据:", statsData.value);
  console.log("统计数据长度:", statsData.value.length);

  isStatsVisible.value = !isStatsVisible.value;
  title.value = "投诉建议统计";

  console.log("对话框是否显示:", isStatsVisible.value);
}
/** 打印按钮操作 */
function handlePrint() {
  console.log('打印功能被调用');

  if (!complaintsList.value || complaintsList.value.length === 0) {
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
        <title>投诉建议列表</title>
        <style>
          body { font-family: Arial, sans-serif; margin: 20px; }
          table { width: 100%; border-collapse: collapse; margin-top: 20px; }
          th, td { border: 1px solid #ddd; padding: 8px; text-align: left; }
          th { background-color: #f2f2f2; }
          h1 { color: #333; }
        </style>
      </head>
      <body>
        <h1>投诉建议列表</h1>
        <table>
          <thead>
            <tr>
              <th>ID</th>
              <th>投诉内容</th>
              <th>投诉时间</th>
              <th>投诉人姓名</th>
            </tr>
          </thead>
          <tbody>
            ${complaintsList.value.map(item => `
              <tr>
                <td>${item.id || ''}</td>
                <td>${item.content || ''}</td>
                <td>${formatDate(item.time)}</td>
                <td>${item.name || ''}</td>
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
}


/** 第三方接口按钮操作 */
function handleThirdPartyAPI() {
  console.log('第三方接口被调用');

  proxy.$modal.msgSuccess('正在调用第三方接口...');

  // 完全前端模拟第三方API调用
  setTimeout(() => {
    // 模拟第三方API返回的数据
    const mockExternalData = {
      content: '来自第三方系统的投诉建议',
      name: '第三方用户',
      time: new Date().toISOString().split('T')[0]
    };

    proxy.$modal.msgSuccess(`成功获取外部数据: ${mockExternalData.content}`);

    // 询问是否导入数据
    proxy.$modal.confirm('是否将外部数据导入到本地？').then(() => {
      const externalComplaint = {
        content: `外部导入: ${mockExternalData.content}`,
        name: mockExternalData.name,
        time: mockExternalData.time
      };

      addComplaints(externalComplaint).then(response => {
        proxy.$modal.msgSuccess('外部数据导入成功');
        getList();
      }).catch(error => {
        proxy.$modal.msgError('导入失败: ' + error.message);
      });
    }).catch(() => {
      // 用户取消导入
    });
  }, 1000); // 模拟1秒的网络延迟
}

const { queryParams, form, rules } = toRefs(data);
/** 排序触发事件 */
function handleSortChange(column, prop, order) {
  queryParams.value.orderByColumn = column.prop;
  queryParams.value.isAsc = column.order;
  getList();
}
/** 查询投诉管理列表 */
function getList() {
  loading.value = true;
  listComplaints(queryParams.value).then(response => {
    complaintsList.value = response.rows;
    total.value = response.total;
    loading.value = false;
  });
}
const view = ref(false);//控制开启详情对话框
// 关闭查看按钮
function cancelview() {
  view.value = false;
  reset();
}
/** 详情按钮操作 */
function handleView(row) {
  reset();
  const _id = row.id || ids.value;
  getComplaints(_id).then(response => {
    form.value = response.data;
    view.value = true;
    title.value = "查看投诉列表";
  });
}
// 取消按钮
function cancel() {
  open.value = false;
  reset();
}

// 表单重置
function reset() {
  form.value = {
    id: null,
    content: null,
    time: null,
    name: null
  };
  proxy.resetForm("complaintsRef");
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
  open.value = true;
  title.value = "添加投诉管理";
}

/** 修改按钮操作 */
function handleUpdate(row) {
  reset();
  const _id = row.id || ids.value;
  getComplaints(_id).then(response => {
    form.value = response.data;
    open.value = true;
    title.value = "修改投诉管理";
  });
}

/** 提交按钮 */
function submitForm() {
  proxy.$refs["complaintsRef"].validate(valid => {
    if (valid) {
      if (form.value.id != null) {
        updateComplaints(form.value).then(response => {
          proxy.$modal.msgSuccess("修改成功");
          open.value = false;
          getList();
        });
      } else {
        addComplaints(form.value).then(response => {
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
    .confirm('是否确认删除投诉管理编号为"' + _ids + '"的数据项？')
    .then(function() {
      return delComplaints(_ids);
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
    "complaint/complaints/export",
    {
      ...queryParams.value
    },
    `complaints_${new Date().getTime()}.xlsx`
  );
}

getList();
</script>