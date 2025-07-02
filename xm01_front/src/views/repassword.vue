<template>
  <div class="recover-password">
    <el-form ref="recoverRef" :model="recoverForm" :rules="recoverRules" class="recover-form">
      <h3 class="title">交通视频识别分析系统</h3>
      <el-form-item prop="username">
        <el-input
          v-model="recoverForm.username"
          type="text"
          size="large"
          auto-complete="off"
          placeholder="用户名"
        >
          <template #prefix>
            <svg-icon icon-class="user" class="el-input__icon input-icon" />
          </template>
        </el-input>
      </el-form-item>
      <el-form-item prop="email">
        <el-input
          v-model="recoverForm.email"
          type="email"
          size="large"
          auto-complete="off"
          placeholder="邮箱"
        >
          <template #prefix>
            <svg-icon icon-class="email" class="el-input__icon input-icon" />
          </template>
        </el-input>
      </el-form-item>
      <el-form-item prop="code">
        <el-input
          v-model="recoverForm.code"
          size="large"
          auto-complete="off"
          placeholder="验证码"
          style="width: 63%"
        >
          <template #prefix>
            <svg-icon icon-class="validCode" class="el-input__icon input-icon" />
          </template>
        </el-input>

        <div class="recover-code">
          <el-button
            :loading="loading"
            size="large"
            type="warning"
            style="width:80%; margin-left:20px"
            @click.prevent="getEmailCode"
            :disabled="countdown > 0"
          >
            <span v-if="countdown === 0">获取验证码</span>
            <span v-else>{{ countdown }}秒后重发</span>
          </el-button>
        </div>
      </el-form-item>
      <el-form-item prop="newPassword">
        <el-input
          v-model="recoverForm.newPassword"
          type="password"
          size="large"
          auto-complete="off"
          placeholder="新密码"
        >
          <template #prefix>
            <svg-icon icon-class="password" class="el-input__icon input-icon" />
          </template>
        </el-input>
      </el-form-item>
      <el-form-item prop="confirmPassword">
        <el-input
          v-model="recoverForm.confirmPassword"
          type="password"
          size="large"
          auto-complete="off"
          placeholder="确认新密码"
        >
          <template #prefix>
            <svg-icon icon-class="password" class="el-input__icon input-icon" />
          </template>
        </el-input>
      </el-form-item>
      <el-form-item style="width:100%;">
        <el-button
          :loading="loading"
          size="large"
          type="text"
         style="width:100%;background-color: #0288D1; color: white;"
          @click.prevent="handleRecover"
        >
          <span v-if="!loading">确认修改</span>
          <span v-else>处理中...</span>
        </el-button>
        <div style="float: right;">
          <router-link class="link-type" :to="'/login'">返回</router-link>
        </div>
      </el-form-item>
    </el-form>
     <!--  底部  -->
    <div class="el-repassword-footer">
      <span>XM-01</span>
    </div>
  </div>
</template>

<script setup>
import { getCodeImg, repassword, sendcode, sendEmail } from "@/api/login";
import { ref } from "vue";
import { email } from "@/api/tool/gen";

import { ElMessageBox } from "element-plus"; //用于密码修改成功弹框
const { proxy } = getCurrentInstance(); //获取当前实例对象
import useUserStore from "@/store/modules/user";
const router = useRouter();//获取路由对象
const userStore = useUserStore(); //获取用户状态对象
const recoverForm = ref({
  username: "",
  email: "",
  code: "",
  newPassword: "",
  confirmPassword: ""
});

const recoverRules = {
  username: [{ required: true, trigger: "blur", message: "请输入用户名" }],
  email: [{ required: true, trigger: "blur", message: "请输入邮箱" }],
  code: [{ required: true, trigger: "blur", message: "请输入验证码" }],
  newPassword: [{ required: true, trigger: "blur", message: "请输入新密码" }],
  confirmPassword: [
    { required: true, trigger: "blur", message: "请确认新密码" },
    { validator: validateConfirmPassword, trigger: "blur" }
  ]
};

const loading = ref(false);
const countdown = ref(0);
let generatedCode = "";
let codeTimeout;
////确认修改密码
function handleRecover() {
  proxy.$refs.recoverRef.validate(valid => {
    if (valid) {
      // 判断验证码
      if (recoverForm.value.code !== generatedCode || generatedCode === "") {
        console.error("验证码不正确或已过期");
          ElMessageBox.alert(
        "<font color='red'>验证码不正确或已过期！</font>",
        "错误提示",
        {
          dangerouslyUseHTMLString: true,
          type: "error"
        }
      )
        return; // 验证码不正确，退出函数
      }

      // 判断两次密码是否相同
      if (recoverForm.value.newPassword !== recoverForm.value.confirmPassword) {
        console.error("两次输入的密码不一致");
        return; // 密码不一致，退出函数
      }
      //发送请求
      sendPassword(recoverForm.value.username, recoverForm.value.newPassword);
    }
  });
}

function sendPassword(username, password) {
  const formValue = {};
  formValue.username = username;
  formValue.password = password;
  repassword(formValue)
    .then(res => {
      console.log("修改成功");
      console.log("准备显示 ElMessageBox");
      const name = username;
      ElMessageBox.alert(
        "<font color='red'>恭喜你，您的账号 " +
          name +
          " 密码修改成功！</font>",
        "系统提示",
        {
          dangerouslyUseHTMLString: true,
          type: "success"
        }
      )
        .then(() => {
          console.log("ElMessageBox 显示成功，准备跳转");
          router.push("/login");
        })
        .catch(err => {
          console.error("ElMessageBox 显示失败", err);
        });
    })
    .catch(err => {
      console.error("修改失败", err);
      loading.value = false;
    });
}
function validateConfirmPassword(rule, value, callback) {
  const { newPassword } = recoverForm.value;
  if (value !== newPassword) {
    callback(new Error("两次输入的密码不一致"));
  } else {
    callback();
  }
}

//发送验证码
function getEmailCode() {
  const formRef = proxy.$refs.recoverRef;

  // 验证用户名和邮箱
  Promise.all([
    new Promise(resolve => {
      formRef.validateField("username", valid => {
        if (!valid) {
          console.log("验证用户名失败");
          resolve(false);
        } else {
          resolve(true);
        }
      });
    }),
    new Promise(resolve => {
      formRef.validateField("email", valid => {
        if (!valid) {
          console.log("验证邮箱失败");
          resolve(false);
        } else {
          resolve(true);
        }
      });
    })
  ]).then(results => {
    if (!results[0] || !results[1]) {
      return; // 如果任意一个验证失败，停止执行
    }

    // 检查用户名和邮箱的有效性
    isEmailMatched(recoverForm.value)
      .then(isMatched => {
        if (!isMatched) {
          console.log("邮箱与用户名不匹配");
          return;
        } else {
          // console.log("邮箱与用户名匹配");
          // 生成验证码
          generatedCode = getNumCode();
          console.log("generatedCode", generatedCode);
          // 发送验证码
          SendEmailFun(recoverForm.value.email, generatedCode);
          // 设置验证码有效期
          clearTimeout(codeTimeout);
          codeTimeout = setTimeout(() => {
            generatedCode = "";
          }, 5 * 60 * 1000);

          // 启动倒计时
          countdown.value = 60;
          const interval = setInterval(() => {
            countdown.value -= 1;
            if (countdown.value <= 0) {
              clearInterval(interval);
            }
          }, 1000);
        }
      })
      .catch(error => {
        console.error("检查邮箱时出错:", error);
      });
  });
}

function getNumCode() {
  return Math.floor(100000 + Math.random() * 900000).toString();
}

function SendEmailFun(email, code) {
  console.log("SendEmailFun", email, code);
  const formValue = {};
  formValue.email = email;
  formValue.code = code;
  return sendEmail(formValue)
    .then(res => {
      return true;
    })
    .catch(() => {
      return false;
    });
}
function isEmailMatched(formValue) {
  console.log("Checking email match for:", formValue.username, formValue.email);
  return sendcode(formValue)
    .then(res => {
      return true; // 表示邮箱匹配
    })
    .catch(() => {
      return false; // 表示邮箱不匹配
    });
}
</script>

<style lang='scss' scoped>
.recover-password {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100%;
   background-image: url("../assets/images/bg.jpg");
  
  background-repeat: no-repeat;
  background-size: cover;
}

.title {
  margin: 0px auto 30px auto;
  text-align: center;
  // color: #707070;
  color: #fff;
}

.recover-form {
  border-radius: 6px;
  background: rgb(12, 111, 160);
  opacity: 0.9;
  width: 400px;
  padding: 25px 25px 5px 25px;
  .el-input {
    height: 40px;
    input {
      height: 40px;
    }
  }
  .input-icon {
    height: 39px;
    width: 14px;
    margin-left: 0px;
  }
}

.recover-code {
  width: 33%;
  height: 40px;
  float: right;
  img {
    cursor: pointer;
    vertical-align: middle;
  }
}

.recover-code-img {
  height: 40px;
  padding-left: 12px;
}

.el-repassword-footer {
  height: 40px;
  line-height: 40px;
  position: fixed;
  bottom: 0;
  width: 100%;
  text-align: center;
  color: #fff;
  font-family: Arial;
  font-size: 12px;
  letter-spacing: 1px;
}
</style>
