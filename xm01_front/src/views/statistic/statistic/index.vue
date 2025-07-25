<template>
  <div class="app-container">
    <el-form
      :model="queryParams"
      ref="queryRef"
      :inline="true"
      v-show="showSearch"
      label-width="68px"
    >
      <el-form-item label="车流量" prop="trafficFlow">
        <el-input
          v-model="queryParams.trafficFlow"
          placeholder="请输入车流量"
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
          v-hasPermi="['statistic:statistic:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="Edit"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['statistic:statistic:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="Delete"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['statistic:statistic:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="Download"
          @click="handleExport"
          v-hasPermi="['statistic:statistic:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table
      v-loading="loading"
      :data="statisticList"
      @selection-change="handleSelectionChange"
      :default-sort="defaultSort"
      @sort-change="handleSortChange"
    >
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column
        label="ID"
        align="center"
        prop="statisticId"
        sortable="custom"
        :sort-orders="['descending', 'ascending']"
      />
      <el-table-column
        label="车流量"
        align="center"
        prop="trafficFlow"
        sortable="custom"
        :sort-orders="['descending', 'ascending']"
      />
      <el-table-column label="纬度" align="center" prop="widX1" />
      <el-table-column label="经度" align="center" prop="widY1" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template #default="scope">
         

          <el-button
            link
            type="primary"
            icon="Edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['statistic:statistic:edit']"
          >修改</el-button>
          <el-button
            link
            type="primary"
            icon="Delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['statistic:statistic:remove']"
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

    <!-- 添加或修改车流量对话框 -->
    <el-dialog :title="title" v-model="open" width="500px" append-to-body>
      <el-form ref="statisticRef" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="车流量" prop="trafficFlow">
          <el-input v-model="form.trafficFlow" placeholder="请输入车流量" />
        </el-form-item>
        <el-form-item label="纬度" prop="widX1">
          <el-input v-model="form.widX1" placeholder="请输入纬度" />
        </el-form-item>
        <el-form-item label="经度" prop="widY1">
          <el-input v-model="form.widY1" placeholder="请输入经度" />
        </el-form-item>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button type="primary" @click="submitForm">确 定</el-button>
          <el-button @click="cancel">取 消</el-button>
        </div>
      </template>
    </el-dialog>

    <!-- 详情车流量对话框 -->
    <el-dialog :title="title" v-model="view" width="500px" append-to-body>
      <el-form ref="statisticRef" :model="form" label-width="80px">
        <el-form-item label="车流量" prop="trafficFlow">
          <el-input v-model="form.trafficFlow" placeholder="请输入车流量" />
        </el-form-item>
        <el-form-item label="纬度" prop="widX1">
          <el-input v-model="form.widX1" placeholder="请输入纬度" />
        </el-form-item>
        <el-form-item label="经度" prop="widY1">
          <el-input v-model="form.widY1" placeholder="请输入经度" />
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

<script setup name="Statistic">
import {
  listStatistic,
  getStatistic,
  delStatistic,
  addStatistic,
  updateStatistic
} from "@/api/statistic/statistic";

const { proxy } = getCurrentInstance();

const statisticList = ref([]);
const open = ref(false);
const loading = ref(true);
const showSearch = ref(true);
const ids = ref([]);
const single = ref(true);
const multiple = ref(true);
const total = ref(0);
const title = ref("");

const data = reactive({
  form: {},
  queryParams: {
    pageNum: 1,
    pageSize: 10,
    trafficFlow: null
  },
  rules: {}
});

const { queryParams, form, rules } = toRefs(data);
/** 排序触发事件 */
function handleSortChange(column, prop, order) {
  queryParams.value.orderByColumn = column.prop;
  queryParams.value.isAsc = column.order;
  getList();
}
const view = ref(false); //控制开启详情对话框
// 关闭查看按钮
function cancelview() {
  view.value = false;
  reset();
}
/** 详情按钮操作 */
function handleView(row) {
  reset();
  const _id = row.id || ids.value;
  getStatistic(_id).then(response => {
    form.value = response.data;
    view.value = true;
    title.value = "查看详细信息";
  });
}

/** 查询车流量列表 */
function getList() {
  loading.value = true;
  listStatistic(queryParams.value).then(response => {
    statisticList.value = response.rows;
    total.value = response.total;
    loading.value = false;
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
    statisticId: null,
    trafficFlow: null,
    widX1: null,
    widY1: null,
    widX2: null,
    widY2: null,
    createTime: null
  };
  proxy.resetForm("statisticRef");
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
  ids.value = selection.map(item => item.statisticId);
  single.value = selection.length != 1;
  multiple.value = !selection.length;
}

/** 新增按钮操作 */
function handleAdd() {
  reset();
  open.value = true;
  title.value = "添加车流量";
}

/** 修改按钮操作 */
function handleUpdate(row) {
  reset();
  const _statisticId = row.statisticId || ids.value;
  getStatistic(_statisticId).then(response => {
    form.value = response.data;
    open.value = true;
    title.value = "修改车流量";
  });
}

/** 提交按钮 */
function submitForm() {
  proxy.$refs["statisticRef"].validate(valid => {
    if (valid) {
      if (form.value.statisticId != null) {
        updateStatistic(form.value).then(response => {
          proxy.$modal.msgSuccess("修改成功");
          open.value = false;
          getList();
        });
      } else {
        addStatistic(form.value).then(response => {
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
  const _statisticIds = row.statisticId || ids.value;
  proxy.$modal
    .confirm('是否确认删除车流量编号为"' + _statisticIds + '"的数据项？')
    .then(function() {
      return delStatistic(_statisticIds);
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
    "statistic/statistic/export",
    {
      ...queryParams.value
    },
    `statistic_${new Date().getTime()}.xlsx`
  );
}

getList();
</script>
