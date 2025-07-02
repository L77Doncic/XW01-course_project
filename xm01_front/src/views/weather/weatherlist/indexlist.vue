<template>
  <div class="app-container">
    <el-form
      :model="queryParams"
      ref="queryRef"
      :inline="true"
      v-show="showSearch"
      label-width="68px"
    >
      <el-form-item label="城市" prop="city">
        <el-input
          v-model="queryParams.city"
          placeholder="请输入城市"
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
          v-hasPermi="['system:weather:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="Edit"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['system:weather:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="Delete"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['system:weather:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="Download"
          @click="handleExport"
          v-hasPermi="['system:weather:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="weatherList" @selection-change="handleSelectionChange" :default-sort="defaultSort"
      @sort-change="handleSortChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="ID" align="center" prop="id" sortable="custom"
        :sort-orders="['descending', 'ascending']" />
      <el-table-column label="城市" align="center" prop="city" />
      <el-table-column label="观测时间" align="center" prop="obsTime" width="180" sortable="custom"
        :sort-orders="['descending', 'ascending']">
        <template #default="scope">
          <span>{{ parseTime(scope.row.obsTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="当前温度" align="center" prop="currentTemp"  sortable="custom"
        :sort-orders="['descending', 'ascending']"/>
      <el-table-column label="最高温度" align="center" prop="tempMax"  sortable="custom"
        :sort-orders="['descending', 'ascending']"/>
      <el-table-column label="最低温度" align="center" prop="tempMin" sortable="custom"
        :sort-orders="['descending', 'ascending']"/>
      <el-table-column label="天气情况" align="center" prop="currentWeatherText" />
      <el-table-column label="未来1天" align="center" prop="day1FxDate" width="180">
        <template #default="scope">
          <span>{{ parseTime(scope.row.day1FxDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="未来1天最高温" align="center" prop="day1TempMax" />
      <el-table-column label="未来1天最低温" align="center" prop="day1TempMin" />
      <el-table-column label="未来2天" align="center" prop="day2FxDate" width="180">
        <template #default="scope">
          <span>{{ parseTime(scope.row.day2FxDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="未来2天最高温" align="center" prop="day2TempMax" />
      <el-table-column label="未来2天最低温" align="center" prop="day2TempMin" />
      <el-table-column label="未来3天" align="center" prop="day3FxDate" width="180">
        <template #default="scope">
          <span>{{ parseTime(scope.row.day3FxDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="未来3天最高温" align="center" prop="day3TempMax" />
      <el-table-column label="未来3天最低温" align="center" prop="day3TempMin" />
      <el-table-column label="未来4天" align="center" prop="day4FxDate" width="180">
        <template #default="scope">
          <span>{{ parseTime(scope.row.day4FxDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="未来4天最高温" align="center" prop="day4TempMax" />
      <el-table-column label="未来4天最低温" align="center" prop="day4TempMin" />
      <el-table-column label="创建时间" align="center" prop="createdAt" width="180">
        <template #default="scope">
          <span>{{ parseTime(scope.row.createdAt, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width" width="250">
        <template #default="scope">
          <el-button
            link
            type="info"
            icon="View"
            @click="handleView(scope.row)"
            v-hasPermi="['system:weather:view']"
          >详情</el-button>
          <el-button
            link
            type="primary"
            icon="Edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:weather:edit']"
          >修改</el-button>
          <el-button
            link
            type="primary"
            icon="Delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:weather:remove']"
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

    <!-- 添加或修改weather对话框 -->
    <el-dialog :title="title" v-model="open" width="500px" append-to-body>
      <el-form ref="weatherRef" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="城市" prop="city">
          <el-input v-model="form.city" placeholder="请输入城市" />
        </el-form-item>
        <el-form-item label="观测时间" prop="obsTime">
          <el-date-picker
            clearable
            v-model="form.obsTime"
            type="date"
            value-format="YYYY-MM-DD"
            placeholder="请选择观测时间"
          ></el-date-picker>
        </el-form-item>
        <el-form-item label="当前温度" prop="currentTemp">
          <el-input v-model="form.currentTemp" placeholder="请输入当前温度" />
        </el-form-item>
        <el-form-item label="最高温度" prop="tempMax">
          <el-input v-model="form.tempMax" placeholder="请输入最高温度" />
        </el-form-item>
        <el-form-item label="最低温度" prop="tempMin">
          <el-input v-model="form.tempMin" placeholder="请输入最低温度" />
        </el-form-item>
        <el-form-item label="天气情况" prop="currentWeatherText">
          <el-input v-model="form.currentWeatherText" placeholder="请输入天气情况" />
        </el-form-item>
        
        <el-form-item label="创建时间" prop="createdAt">
          <el-date-picker
            clearable
            v-model="form.createdAt"
            type="date"
            value-format="YYYY-MM-DD"
            placeholder="请选择创建时间"
          ></el-date-picker>
        </el-form-item>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button type="primary" @click="submitForm">确 定</el-button>
          <el-button @click="cancel">取 消</el-button>
        </div>
      </template>
    </el-dialog>

    
    <!-- 详情weather对话框 -->
    <el-dialog :title="title" v-model="view" width="500px" append-to-body>
      <el-form ref="weatherRef" :model="form"  label-width="80px">
        <el-form-item label="城市" prop="city">
          <el-input v-model="form.city" placeholder="请输入城市" />
        </el-form-item>
        <el-form-item label="观测时间" prop="obsTime">
          <el-date-picker
            clearable
            v-model="form.obsTime"
            type="date"
            value-format="YYYY-MM-DD"
            placeholder="请选择观测时间"
          ></el-date-picker>
        </el-form-item>
        <el-form-item label="当前温度" prop="currentTemp">
          <el-input v-model="form.currentTemp" placeholder="请输入当前温度" />
        </el-form-item>
        <el-form-item label="最高温度" prop="tempMax">
          <el-input v-model="form.tempMax" placeholder="请输入最高温度" />
        </el-form-item>
        <el-form-item label="最低温度" prop="tempMin">
          <el-input v-model="form.tempMin" placeholder="请输入最低温度" />
        </el-form-item>
        <el-form-item label="天气情况" prop="currentWeatherText">
          <el-input v-model="form.currentWeatherText" placeholder="请输入天气情况" />
        </el-form-item>
        
        <el-form-item label="创建时间" prop="createdAt">
          <el-date-picker
            clearable
            v-model="form.createdAt"
            type="date"
            value-format="YYYY-MM-DD"
            placeholder="请选择创建时间"
          ></el-date-picker>
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

<script setup name="Weather">
import {
  listWeather,
  getWeather,
  delWeather,
  addWeather,
  updateWeather
} from "@/api/weather/weather";
import axios from "axios";

const { proxy } = getCurrentInstance();

const weatherList = ref([]);
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
    city: null
  },
  rules: {
    city: [{ required: true, message: "城市不能为空", trigger: "blur" }],
    createdAt: [
      { required: true, message: "创建时间不能为空", trigger: "blur" }
    ]
  }
});

const { queryParams, form, rules } = toRefs(data);
/** 排序触发事件 */
function handleSortChange(column, prop, order) {
  queryParams.value.orderByColumn = column.prop;
  queryParams.value.isAsc = column.order;
  getList();
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
  getWeather(_id).then(response => {
    form.value = response.data;
    view.value = true;
    title.value = "查看天气数据";
  });
}
// 取消按钮
function cancel() {
  open.value = false;
  reset();
}
/** 查询weather列表 */
function getList() {
  loading.value = true;
  listWeather(queryParams.value).then(response => {
    weatherList.value = response.rows;
    total.value = response.total;
    loading.value = false;
  });
}
// 表单重置
function reset() {
  form.value = {
    id: null,
    city: null,
    obsTime: null,
    currentTemp: null,
    tempMax: null,
    tempMin: null,
    currentWeatherText: null,
    day1FxDate: null,
    day1TempMax: null,
    day1TempMin: null,
    day2FxDate: null,
    day2TempMax: null,
    day2TempMin: null,
    day3FxDate: null,
    day3TempMax: null,
    day3TempMin: null,
    day4FxDate: null,
    day4TempMax: null,
    day4TempMin: null,
    createdAt: null
  };
  proxy.resetForm("weatherRef");
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
  title.value = "添加weather";
}

/** 修改按钮操作 */
function handleUpdate(row) {
  reset();
  const _id = row.id || ids.value;
  getWeather(_id).then(response => {
    form.value = response.data;
    open.value = true;
    title.value = "修改weather";
  });
}

/** 提交按钮 */
function submitForm() {
  proxy.$refs["weatherRef"].validate(valid => {
    if (valid) {
      if (form.value.id != null) {
        updateWeather(form.value).then(response => {
          proxy.$modal.msgSuccess("修改成功");
          open.value = false;
          getList();
        });
      } else {
        addWeather(form.value).then(response => {
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
    .confirm('是否确认删除weather编号为"' + _ids + '"的数据项？')
    .then(function() {
      return delWeather(_ids);
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
    "system/weather/export",
    {
      ...queryParams.value
    },
    `weather_${new Date().getTime()}.xlsx`
  );
}

getList();
</script>
