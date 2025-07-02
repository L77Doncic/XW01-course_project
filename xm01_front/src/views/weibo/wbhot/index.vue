<template>
  <div class="app-container" style="margin: 0 50px 0 50px;">
    <div class="title">微博热搜</div>
    <div style="text-align: right;margin:0 100px  0 0;">
      <el-form
        :model="queryParams"
        ref="queryRef"
        :inline="true"
        v-show="showSearch"
        label-width="68px"
        
      >
        <el-form-item>
          <el-button icon="Refresh" @click="resetQuery">刷新</el-button>

          <el-button
            type="warning"
            plain
            icon="Download"
            @click="handleExport"
            v-hasPermi="['weibo:wbhot:export']"
          >导出</el-button>
        </el-form-item>
      </el-form>
    </div>
    <div style="margin: 0 50px 0 50px;padding: 0 50px 0 50px">
      
      <el-table v-loading="loading" :data="wbhotList" @selection-change="handleSelectionChange">
        <el-table-column label="" align="center" prop="realpos" width="90px"  >

          <template #default="scope">
            <!-- 判断 realpos 是否为空 -->
            <span class="rank"
              :id="
            scope.row.realpos === 1
              ? 'st1'
              : scope.row.realpos === 2
              ? 'st2'
              : scope.row.realpos === 3
              ? 'st3'
              : scope.row.realpos > 3
              ? 'st'
              : 'st0' "
            >{{ scope.row.realpos ? scope.row.realpos : '●' }}</span>
          </template>
        </el-table-column>

        <el-table-column label="内容" align="left" prop="note" width="200px"  />
        <el-table-column label="话题类型" align="center" prop="iconDesc" width="80px" >
            <template #default="scope">
            
            <span 
              :id="
                scope.row.iconDesc === '爆'
              ? 'icon1'
              : scope.row.iconDesc === '新'
              ? 'icon2'
               : scope.row.iconDesc === '暖'
              ? 'icon2'
              : scope.row.iconDesc === '热'
              ? 'icon3'
              : scope.row.iconDesc ==='荐'
              ? 'icon4'
               : scope.row.iconDesc ==='沸'
              ? 'icon5'
                :scope.row.iconDesc === null
              ? 'icon' 
              :'icon0' "
            >{{ scope.row.iconDesc ? scope.row.iconDesc : '' }}
            </span>
          </template>
        </el-table-column>  
        <el-table-column label="关注人数     " align="right" prop="num" />
      </el-table>
    </div>
    <div style="margin: 0 100px 0 0px;">
    <pagination
      v-show="total>0"
      :total="total"
      v-model:page="queryParams.pageNum"
      v-model:limit="queryParams.pageSize"
      @pagination="getList"
    />
    </div>
  </div>
</template>

<script setup name="Wbhot">
import {
  listWbhot,
  getWbhot,
  delWbhot,
  addWbhot,
  updateWbhot,
  rtn
} from "@/api/weibo/wbhot";

const { proxy } = getCurrentInstance();

const wbhotList = ref([]);
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
    pageSize: 30,
    realpos: null,
    note: null,
    num: null,
    iconDesc: null
  },
  rules: {
    realpos: [{ required: true, message: "排名不能为空", trigger: "blur" }],
    note: [{ required: true, message: "内容不能为空", trigger: "blur" }]
  }
});

const { queryParams, form, rules } = toRefs(data);


/** 查询微博热搜列表 */
function getjson() {
  loading.value = true;
  rtn(queryParams.value).then(response => {
    wbhotList.value = response.rows;
    total.value = response.total;
    loading.value = false;
  });
}


/** 查询微博热搜列表 */
function getList() {

  loading.value = true;
  listWbhot(queryParams.value).then(response => {
    wbhotList.value = response.rows;
    total.value = response.total;
    loading.value = false;
  });
}

// 表单重置
function reset() {
  form.value = {
    id: null,
    realpos: null,
    iconDescColor: null,
    note: null,
    num: null,
    topicFlag: null,
    emoticon: null,
    wordScheme: null,
    smallIconDescColor: null,
    icon: null,
    iconWidth: null,
    iconHeight: null,
    labelName: null,
    flag: null,
    smallIconDesc: null,
    word: null,
    iconDesc: null,
    rank: null
  };
  proxy.resetForm("wbhotRef");
}

/** 搜索按钮操作 */
function handleQuery() {
  queryParams.value.pageNum = 1;
  getList();
}

/** 重置按钮操作 */
function resetQuery() {
    getjson();
  proxy.resetForm("queryRef");
  handleQuery();
}



/** 导出按钮操作 */
function handleExport() {
  proxy.download(
    "weibo/wbhot/export",
    {
      ...queryParams.value
    },
    `wbhot_${new Date().getTime()}.xlsx`
  );
}

getList();
</script>

<style scoped>
.title {
  text-align: center;
  font-size: 32px; /* 字体更大一些 */
  font-family: "Courier New", "Arial Black", sans-serif; /* 增加艺术字体感 */
  font-weight: 500;
  letter-spacing: 8px; /* 增加字间距 */
  color: rgb(48, 65, 86); /* 浅橙色 */
  letter-spacing: 30px; /* 增加字间距 */
  margin-top: 30px; /* 增加顶部间距 */
  line-height: 1.5; /* 改善行高，视觉更舒适 */
  
}

.rank {
  display: inline-flex;
  align-items: center;
  justify-content: center; /* 水平居中 */
  padding: 4px 8px;
  font-size: 14px;
  font-weight: bold;
  border-radius: 5px;
  transform: skewX(-10deg);
  height: 20px;
  width: 35px;
  text-align: center; /* 确保文字居中对齐 */
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.2);
  color: white; /* 默认文字颜色 */
}

#st1 {
  background: linear-gradient(45deg, #ff512f, #dd2476); /* 红色渐变背景 */
}
#st2{
  background: linear-gradient(45deg,rgb(253, 166, 21) ,rgb( 249, 105, 29));
 
}
#st0{
  background: linear-gradient(45deg,rgb(249, 250, 252) ,rgb(249, 250, 252));
  color:#ffa07a;

}

#st3{
background: linear-gradient(45deg,rgb(251, 204, 74) ,rgb( 239, 166, 0));
}
#st{
  background: linear-gradient(45deg,rgb(175, 181, 197) ,rgb(175, 181, 197));

}
#icon1{
   display: inline-flex;
  align-items: center;
  justify-content: center; /* 水平居中 */
  padding: 4px 8px;
  font-size: 14px;
  font-weight: bold;
  border-radius: 7px;
  height: 25px;
  width: 25px;
  text-align: center; /* 确保文字居中对齐 */
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.2);
  color: white; /* 默认文字颜色 */
   background: rgb(176, 1, 1);
}

#icon2{
   display: inline-flex;
  align-items: center;
  justify-content: center; /* 水平居中 */
  padding: 4px 8px;
  font-size: 14px;
  font-weight: bold;
  border-radius: 7px;
  height: 25px;
  width: 25px;
  text-align: center; /* 确保文字居中对齐 */
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.2);
  color: white; /* 默认文字颜色 */
   background: rgb(255, 56, 82);
}
#icon3{
   display: inline-flex;
  align-items: center;
  justify-content: center; /* 水平居中 */
  padding: 4px 8px;
  font-size: 14px;
  font-weight: bold;
  border-radius: 7px;
  height: 25px;
  width: 25px;
  text-align: center; /* 确保文字居中对齐 */
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.2);
  color: white; /* 默认文字颜色 */
  background: rgb(255, 177, 31);
}
 #icon4{/*荐 */
   display: inline-flex;
  align-items: center;
  justify-content: center; /* 水平居中 */
  padding: 4px 8px;
  font-size: 14px;
  font-weight: bold;
  border-radius: 7px;
  height: 25px;
  width: 25px;
  text-align: center; /* 确保文字居中对齐 */
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.2);
  color: white; /* 默认文字颜色 */
  background: #ffa07a;
}
 #icon5{/*荐 */
   display: inline-flex;
  align-items: center;
  justify-content: center; /* 水平居中 */
  padding: 4px 8px;
  font-size: 14px;
  font-weight: bold;
  border-radius: 7px;
  height: 25px;
  width: 25px;
  text-align: center; /* 确保文字居中对齐 */
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.2);
  color: white; /* 默认文字颜色 */
  background: rgb(255, 130, 0);
}
 #icon0{/*其他 */
   display: inline-flex;
  align-items: center;
  justify-content: center; /* 水平居中 */
  padding: 4px 8px;
  font-size: 14px;
  font-weight: bold;
  border-radius: 7px;
  height: 25px;
  min-width: 25px;
  text-align: center; /* 确保文字居中对齐 */
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.2);
  color: white; /* 默认文字颜色 */
  background: rgb(79, 176, 255);
}
</style>
